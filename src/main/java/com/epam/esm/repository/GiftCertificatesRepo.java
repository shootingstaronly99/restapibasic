package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateException;

import java.util.List;
import java.util.Map;


public interface GiftCertificatesRepo extends CommonRepo<GiftCertificate> {
    //method for Filter and Sort
    List<GiftCertificate> getWithFilters(Map<String, String> fields);

    //FindBYId
    GiftCertificate findById(Integer id) throws GiftCertificateException;

    //update
    boolean update(GiftCertificate giftCertificate);


}
