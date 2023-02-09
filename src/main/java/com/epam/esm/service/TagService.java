package com.epam.esm.service;

import com.epam.esm.common.ResponseModel;
import com.epam.esm.common.ResultMessage;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateException;

import java.util.List;

/*
 * @author Otabek Javqochdiyev
 * */
public interface TagService {
    ResponseModel<ResultMessage> create(Tag data);

    ResponseModel<Tag> getById(Integer id);

    ResponseModel<Tag> findByName(String name);

    ResponseModel<List<Tag>> getAll() throws GiftCertificateException;

    ResponseModel<ResultMessage> delete(Integer id);
}
