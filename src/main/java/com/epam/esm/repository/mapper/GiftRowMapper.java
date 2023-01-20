package com.epam.esm.repository.mapper;

import com.epam.esm.entity.GiftCertificates;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.repository.mapper.ColumnName.*;


@Component
public class GiftRowMapper implements ResultSetExtractor<List<GiftCertificates>>  {
    @Override
    public List<GiftCertificates> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificates> giftCertificates = new ArrayList<>();
        rs.next();
        while (!rs.isAfterLast()) {

            GiftCertificates giftCertificate = GiftCertificates.builder()
                    .id(rs.getLong(GIFT_ID))
                    .name(rs.getString(GIFT_NAME))
                    .description(rs.getString(GIFT_DESCRIPTION))
                    .price(rs.getDouble(GIFT_PRICE))
                    .duration(rs.getInt(GIFT_DURATION))
                    .createDate(rs.getTimestamp(GIFT_CREATE_DATE).toLocalDateTime())
                    .build();
            String lastUpdateDate = rs.getString(GIFT_LAST_UPDATE_DATE);
            if (lastUpdateDate != null) {
                giftCertificate.setLastUpdateDate(rs.getTimestamp(GIFT_LAST_UPDATE_DATE).toLocalDateTime());
            }

            List<Tag> tags = new ArrayList<>();
            while (!rs.isAfterLast() && rs.getInt(GIFT_ID) == giftCertificate.getId()) {
                Tag tag = Tag.builder()
                        .id(rs.getLong(TAG_ID))
                        .name(rs.getString(TAG_NAME))
                        .build();
                tags.add(tag);
                rs.next();
            }
            giftCertificate.setTags(tags);
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }
}
