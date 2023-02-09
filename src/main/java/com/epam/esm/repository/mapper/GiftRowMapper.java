package com.epam.esm.repository.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Component
public class GiftRowMapper implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {

        List<GiftCertificate> giftCertificates = new ArrayList<>();
        rs.next();
        try {
            while (!rs.isAfterLast()) {
                GiftCertificate giftCertificate = GiftCertificate.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .price(rs.getDouble("price"))
                        .duration(rs.getInt("duration"))
                        .createDate(rs.getTimestamp("create_date").toLocalDateTime())
                        .build();
                String lastUpdateDate = rs.getString("last_update_date");
                if (lastUpdateDate != null) {
                    giftCertificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
                }
                List<Tag> tags = new ArrayList<>();
                while (!rs.isAfterLast() && rs.getInt("id") == giftCertificate.getId()) {
                    Tag tag = Tag.builder()
                            .id(rs.getLong("tag_id"))
                            .name(rs.getString("tag_name"))
                            .build();
                    tags.add(tag);
                    rs.next();
                }
                giftCertificate.setTags(tags);
                giftCertificates.add(giftCertificate);
            }

            return giftCertificates;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }
}
