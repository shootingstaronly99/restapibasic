package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificates;
import com.epam.esm.exception.IncorrectParameterException;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

/*
 * @author Otabek Javqochdiyev
 * */
public interface GiftCertificatesService {
    Optional<GiftCertificates> findById(Integer id);

    void create(GiftCertificates giftCertificate);

    boolean delete(Integer id);

    boolean update(int id, GiftCertificates giftCertificate) throws IncorrectParameterException;

    boolean updateGiftTag(int id);

    List<GiftCertificates> doFilter(MultiValueMap<String, String> requestParams);
    List<GiftCertificates> getAll();


}
