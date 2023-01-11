package com.epam.esm.repository.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagRowMapper implements RowMapper<Tag> {
   @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Tag.builder()
                .id(resultSet.getLong("tag_id"))
                .name(resultSet.getString("tag_name"))
                .build();

    }
}
