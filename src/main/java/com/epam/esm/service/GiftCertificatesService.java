package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificates;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NullPointerException;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

/*
 * @author Otabek Javqochdiyev
 * */
public interface GiftCertificatesService {
    Optional<GiftCertificates> findById(Integer id)throws NullPointerException;

    void create(GiftCertificates giftCertificate)throws NullPointerException;

    boolean delete(Integer id)throws NullPointerException;

    boolean update(int id, GiftCertificates giftCertificate) throws IncorrectParameterException,NullPointerException;

    boolean updateGiftTag(int id);

    List<GiftCertificates> doFilter(MultiValueMap<String, String> requestParams);
    List<GiftCertificates> getAll()throws NullPointerException;


}
