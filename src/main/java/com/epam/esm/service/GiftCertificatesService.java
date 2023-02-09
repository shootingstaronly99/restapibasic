package com.epam.esm.service;

import com.epam.esm.common.ResponseModel;
import com.epam.esm.common.ResultMessage;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.IncorrectParameterException;
import org.springframework.util.MultiValueMap;

import java.util.List;

/*
 * @author Otabek Javqochdiyev
 * */
public interface GiftCertificatesService {
    ResponseModel<GiftCertificate> getById(Integer id);

    ResponseModel<ResultMessage> create(GiftCertificate giftCertificate);

    ResponseModel<ResultMessage> delete(Integer id) throws NoSuchFieldException;

    ResponseModel<ResultMessage> update(int id, GiftCertificate giftCertificate) throws IncorrectParameterException;


    ResponseModel<List<GiftCertificate>> searchWithFilter(MultiValueMap<String, String> requestParams);

    ResponseModel<List<GiftCertificate>> getAll();


}
