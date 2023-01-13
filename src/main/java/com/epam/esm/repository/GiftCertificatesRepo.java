package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificates;

import java.util.List;
import java.util.Map;


public interface GiftCertificatesRepo extends CommonRepo<GiftCertificates, Long> {
    List<GiftCertificates> getWithFilters(Map<String, String> fields);

    boolean update(GiftCertificates giftCertificate);

    boolean updateGiftTag(int id);


}
