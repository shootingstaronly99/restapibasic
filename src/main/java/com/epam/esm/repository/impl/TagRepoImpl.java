package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagException;
import com.epam.esm.repository.TagRepo;
import com.epam.esm.repository.mapper.TagRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepoImpl implements TagRepo {
    /*
    Queries for following methods
     */
    private final JdbcTemplate jdbcTemplate;
    public static final String CREATE_TAG = "INSERT INTO tag  (tag_name) VALUES(?)";
    public static final String SELECT_TAG_BY_ID = " SELECT tag_id, tag_name  FROM tag WHERE tag_id = ?";
    public static final String SELECT_ALL_TAGS = "SELECT tag_id, tag_name  FROM tag";
    public static final String SELECT_TAG_BY_NAME = "SELECT gc.id," +
            "           gc.name," +
            "            gc.description," +
            "             gc.price," +
            "            gc.duration," +
            "            gc.create_date," +
            "            gc.last_update_date," +
            "            t.tag_id," +
            "            t.tag_name" +
            "            FROM gift_tags gt" +
            "            JOIN gift_certificates gc ON gt.gift_id = gc.id" +
            "            JOIN tag t ON gt.tag_id = t.tag_id  WHERE t.tag_name = ?";
    public static final String DELETE_GIFT_TAG = "DELETE FROM gift_tags  WHERE tag_id= ?";
    public static final String DELETE_TAG = "DELETE FROM tag  WHERE tag_id= ?";


    public TagRepoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    //findAll method for getting all tags
    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_ALL_TAGS, new TagRowMapper());
    }

    //FindById getting tag by id
    @Override
    public Tag findById(Integer id) {
        return jdbcTemplate.query(SELECT_TAG_BY_ID, new TagRowMapper(), id).stream().findFirst().orElseThrow(
                () -> new TagException("Can't find tag with id " + id));
    }

    //create method  for  creating new tag
    @Override
    public void create(Tag data) {
        try {
            jdbcTemplate.update(CREATE_TAG, data.getName());
        } catch (Exception x) {
            throw new TagException("Problem with creating tag , Please check again! ");
        }
    }

    //Delete method for remove tag by id
    @Override
    public boolean delete(Integer id) {
        int giftTag = jdbcTemplate.update(DELETE_GIFT_TAG, id);
        int tag = jdbcTemplate.update(DELETE_TAG, id);
        if (giftTag == 0 && tag != 0)
            return (giftTag | tag) == 1;
        return (giftTag & tag) == 1;

    }

    //Find By name for tag
    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> list = jdbcTemplate.query(SELECT_TAG_BY_NAME, new TagRowMapper(), name);
        return !list.isEmpty() ?
                Optional.of(list.get(0)) :
                Optional.empty();
    }


}
