package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepo;
import com.epam.esm.repository.mapper.TagRowMapper;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
@Repository
public class TagRepoImpl implements TagRepo {
    private final JdbcTemplate jdbcTemplate;
    public static final String CREATE_TAG = "INSERT INTO tag  (tag_name) VALUES(?)";
    public static final String SELECT_TAG_BY_ID = " SELECT tag_id, tag_name  FROM tag WHERE tag_id = ?";
    public static final String SELECT_ALL_TAGS = "SELECT tag_id, tag_name  FROM tag";
    public static final String DELETE_TAG = " DELETE  FROM tag  WHERE tag_id = ?";
    public static final String SELECT_TAG_BY_NAME = "SELECT tag_id,  tag_name    FROM tag   WHERE tag_name = ?";


    public TagRepoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_ALL_TAGS, new TagRowMapper());
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        List<Tag> list = jdbcTemplate.query(SELECT_TAG_BY_ID, new TagRowMapper(), id);
               return !list.isEmpty() ? Optional.of(list.get(0)) :
                Optional.empty();
    }

    @Override
    public void create(Tag data) {
        jdbcTemplate.update(CREATE_TAG, data.getName());
    }



    @Override
    public boolean delete(Integer id) {
        return jdbcTemplate.update(DELETE_TAG, id) != 0;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.query(DELETE_TAG, new TagRowMapper());
    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> list = jdbcTemplate.query(SELECT_TAG_BY_NAME, new TagRowMapper(), name);
        return !list.isEmpty() ?
                Optional.of(list.get(0)) :
                Optional.empty();
    }
}
