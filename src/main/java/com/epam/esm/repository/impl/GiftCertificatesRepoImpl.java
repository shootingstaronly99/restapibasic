package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificates;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificatesRepo;
import com.epam.esm.repository.TagRepo;
import com.epam.esm.repository.mapper.GiftRowMapper;

import com.epam.esm.repository.query.QueryCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class GiftCertificatesRepoImpl implements GiftCertificatesRepo {
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
    public static final String UPDATE_GIFT_CERTIFICATE_TAG = "UPDATE tag SET tag_name= ? WHERE tag_id= ?";
    public static final String CREATE_TAG = "INSERT INTO tag (tag_name) VALUES(?)";
    public static final String CREATE_GIFT_WITH_TAG = " INSERT INTO gift_tags  (gift_id , tag_id) VALUES(?,?)";


    private final JdbcTemplate jdbcTemplate;
    private final TagRepo tagRepo;


    public GiftCertificatesRepoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        tagRepo = new TagRepoImpl(dataSource);
    }

    @Override
    public List<GiftCertificates> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, new GiftRowMapper());
    }

    @Override
    public Optional<GiftCertificates> findById(Integer id) {
        List<GiftCertificates> results = jdbcTemplate.query(SELECT_BY_CERTIFICATES_ID, new GiftRowMapper(), id);
        return !results.isEmpty() ?
                Optional.of(results.get(0)) :
                Optional.empty();
    }

    @Override
    public List<GiftCertificates> getWithFilters(Map<String, String> fields) {
        QueryCreator queryCreator = new QueryCreator();
        String query = queryCreator.createGetQuery(fields);
        return jdbcTemplate.query(query, new GiftRowMapper());
    }

    @Override
    public void create(GiftCertificates giftCertificate) {
        giftCertificate.setCreate_date(LocalDateTime.now());
        int giftCertificateId = createGiftCertificate(giftCertificate);
        List<Tag> list = giftCertificate.getTags();
        createTags(giftCertificateId, list);
    }

    @Override
    public boolean delete(Integer id) {
        int giftTag = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAG_BY_ID, id);
        int giftCertificate = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID, id);

        return (giftCertificate & giftTag) == 1;
    }

    @Override
    public boolean update(GiftCertificates giftCertificate) {
        giftCertificate.setLast_update_date(LocalDateTime.now());

        return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                LocalDateTime.now(),
                giftCertificate.getId()) != 0;
    }

    @Override
    public boolean updateGiftTag(int id) {
        return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_TAG, id) != 0;
    }


    private int createTag(Tag tag) {
        PreparedStatementCreatorFactory pscfTag = new PreparedStatementCreatorFactory(CREATE_TAG, Types.VARCHAR);
        pscfTag.setReturnGeneratedKeys(true);

        PreparedStatementCreator pscTag = pscfTag.newPreparedStatementCreator(
                Arrays.asList(
                        tag.getName()));


        GeneratedKeyHolder tagKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(pscTag, tagKeyHolder);

        int newId;
        if (tagKeyHolder.getKeys().size() > 1) {
            newId = (int) tagKeyHolder.getKeys().get("tag_id");
        } else {
            newId= tagKeyHolder.getKey().intValue();
        }
        return newId;
    }

    private int createGiftCertificate(GiftCertificates giftCertificate) {
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
                        giftCertificate.getCreate_date(),
                        giftCertificate.getLast_update_date()));

        GeneratedKeyHolder giftKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(pscGift, giftKeyHolder);
        int newId;
        if (giftKeyHolder.getKeys().size() > 1) {
            newId = (int) giftKeyHolder.getKeys().get("id");
        } else {
            newId= giftKeyHolder.getKey().intValue();
        }
        return newId;
    }

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
