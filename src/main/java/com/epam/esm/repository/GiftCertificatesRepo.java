package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateException;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface GiftCertificatesRepo extends CommonRepo<GiftCertificate> {
    List<GiftCertificate> getWithFilters(Map<String, String> fields);

    Optional<GiftCertificate> findById(Integer id) throws GiftCertificateException;

    boolean update(GiftCertificate giftCertificate);


}
