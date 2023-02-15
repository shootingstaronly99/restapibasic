package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.repository.GiftCertificatesRepo;
import com.epam.esm.repository.TagRepo;
import com.epam.esm.repository.mapper.GiftRowMapper;
import com.epam.esm.repository.query.QueryBuilder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class GiftCertificatesRepoImpl implements GiftCertificatesRepo {
    //Queries for following methods
    public static final String CREATE_GIFT_CERTIFICATE = "INSERT INTO gift_certificates  (name, description, price, duration, create_date, last_update_date)" +
            " VALUES(?,?,?,?,?,?)";
    public static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT gc.id, gc.name, gc.description,gc.price,gc.duration," +
            "gc.create_date,gc.last_update_date, t.tag_id,t.tag_name  FROM gift_tags gt JOIN gift_certificates gc ON gt.gift_id = gc.id" +
            "   JOIN tag t ON gt.tag_id = t.tag_id";
    public static final String SELECT_BY_CERTIFICATES_ID = "SELECT gc.id, gc.name, gc.description, gc.price,  gc.duration,  gc.create_date,  gc.last_update_date," +
            "   t.tag_id,  t.tag_name FROM gift_tags gt   JOIN gift_certificates gc ON gt.gift_id = gc.id   JOIN tag t ON gt.tag_id = t.tag_id WHERE gc.id = ?";
    public static final String DELETE_GIFT_CERTIFICATE_TAG_BY_ID = " DELETE FROM gift_tags WHERE gift_id = ?";
    public static final String DELETE_GIFT_CERTIFICATE_BY_ID = " DELETE FROM gift_certificates WHERE id = ?";
    public static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificates SET name=?,  description=?, price=?,  duration=?, last_update_date=?  WHERE id =?";
    public static final String CREATE_TAG = "INSERT INTO tag (tag_name) VALUES(?)";
    public static final String CREATE_GIFT_WITH_TAG = " INSERT INTO gift_tags  (gift_id , tag_id) VALUES(?,?)";
    private final JdbcTemplate jdbcTemplate;
    private final TagRepo tagRepo;


    public GiftCertificatesRepoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        tagRepo = new TagRepoImpl(dataSource);
    }

    //FindAll method for find all gift certificates
    @Override
    public List<GiftCertificate> findAll() {
        try {
            return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, new GiftRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new GiftCertificateException("Empty database!");
        }
    }

    //FindById for getting  gift by id
    @Override
    public GiftCertificate findById(Integer id) {
        return Objects.requireNonNull(jdbcTemplate.query(SELECT_BY_CERTIFICATES_ID, new GiftRowMapper(), id)).stream().findFirst().
                orElseThrow(() -> new GiftCertificateException("Can't find gift with  id " + id));

    }

    //Method for filter and search by fields
    @Override
    public List<GiftCertificate> getWithFilters(Map<String, String> fields) {
        try {
            QueryBuilder queryCreator = new QueryBuilder();
            String query = queryCreator.createGetQuery(fields);
            return jdbcTemplate.query(query, new GiftRowMapper());
        } catch (Exception x) {
            throw new GiftCertificateException("Error with creating filter");
        }
    }

    // Create method which create gift with tag  this method include createGiftCertificate and createTags
    @Override
    public void create(GiftCertificate giftCertificate) {
        try {
            giftCertificate.setCreateDate(LocalDateTime.now());
            int giftCertificateId = createGiftCertificate(giftCertificate);
            List<Tag> list = giftCertificate.getTags();
            createTags(giftCertificateId, list);
        } catch (Exception e) {
            throw new GiftCertificateException("Problem with creating  certificate! Please check again!.");
        }
    }

    //Method Delete for remove Gift certificate
    @Override
    public boolean delete(Integer id) throws NoSuchFieldException {
        int giftTag = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAG_BY_ID, id);
        int giftCertificate = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID, id);
        if (giftCertificate == 0 && giftTag != 0) {
            return (giftTag | giftCertificate) == 1;
        }
        return (giftCertificate & giftTag) == 1;
    }

    //Update method for changing GiftCertificates
    @Override
    public boolean update(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(LocalDateTime.now());

        return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                LocalDateTime.now(),
                giftCertificate.getId()) != 0;
    }

    //  Method for creates tags
    private int createTag(Tag tag) {
        PreparedStatementCreatorFactory pscfTag = new PreparedStatementCreatorFactory(CREATE_TAG, Types.VARCHAR);
        pscfTag.setReturnGeneratedKeys(true);

        PreparedStatementCreator pscTag = pscfTag.newPreparedStatementCreator(
                Arrays.asList(
                        tag.getName()));
        GeneratedKeyHolder tagKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(pscTag, tagKeyHolder);
        int newId;
        if (Objects.requireNonNull(tagKeyHolder.getKeys()).size() > 1) {
            newId = (int) Objects.requireNonNull(tagKeyHolder.getKeys()).get("tag_id");

        } else {
            newId = Objects.requireNonNull(tagKeyHolder.getKey()).intValue();
        }
        return newId;
    }

    //Method for create certificates
    private int createGiftCertificate(GiftCertificate giftCertificate) {
        PreparedStatementCreatorFactory pscfGift = new PreparedStatementCreatorFactory(
                CREATE_GIFT_CERTIFICATE,
                Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP
        );
        pscfGift.setReturnGeneratedKeys(true);

        PreparedStatementCreator pscGift = pscfGift.newPreparedStatementCreator(
                Arrays.asList(
                        giftCertificate.getName(),
                        giftCertificate.getDescription(),
                        giftCertificate.getPrice(),
                        giftCertificate.getDuration(),
                        giftCertificate.getCreateDate(),
                        giftCertificate.getLastUpdateDate()));

        GeneratedKeyHolder giftKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(pscGift, giftKeyHolder);
        int newId;
        if (Objects.requireNonNull(giftKeyHolder.getKeys()).size() > 1) {
            newId = (int) Objects.requireNonNull(giftKeyHolder.getKeys()).get("id");
        } else {
            newId = Objects.requireNonNull(giftKeyHolder.getKey()).intValue();
        }
        return newId;
    }

    //Method for creates tag for gift
    private void createTags(int giftCertificateId, List<Tag> tags) {
        for (Tag tag : tags) {
            String name = tag.getName();
            Optional<Tag> optTag = tagRepo.findByName(name);
            if (optTag.isPresent()) {
                if (Objects.equals(tag.getName(), name)) {
                    Long tagId = tag.getId();
                    jdbcTemplate.update(CREATE_GIFT_WITH_TAG, giftCertificateId, tagId);
                }
            } else {
                int tagId = createTag(tag);
                jdbcTemplate.update(CREATE_GIFT_WITH_TAG, giftCertificateId, tagId);
            }
        }
    }

}
