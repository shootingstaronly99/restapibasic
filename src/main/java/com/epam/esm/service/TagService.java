package com.epam.esm.service;

import com.epam.esm.common.ResponseModel;
import com.epam.esm.common.ResultMessage;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagException;

import java.util.List;

/*
 * @author Otabek Javqochdiyev
 * */
public interface TagService {
    ResponseModel<ResultMessage> create(Tag data);

    ResponseModel<Tag> getById(Integer id);

    ResponseModel<Tag> findByName(String name);

    ResponseModel<List<Tag>> getAll() throws TagException;

    ResponseModel<ResultMessage> delete(Integer id);
}
