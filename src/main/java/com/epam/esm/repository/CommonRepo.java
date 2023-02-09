package com.epam.esm.repository;


import com.epam.esm.exception.GiftCertificateException;

import java.util.List;

public interface CommonRepo<T> {
    List<T> findAll();

    void create(T data) throws GiftCertificateException;

    boolean delete(Integer id) throws NoSuchFieldException;


}
