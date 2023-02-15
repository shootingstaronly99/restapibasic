package com.epam.esm.repository;


import com.epam.esm.exception.GiftCertificateException;

import java.util.List;

/*
@author Otabek Javqochdiyev
 */
//Interface for common methods in each entity
public interface CommonRepo<T> {
    //FindAll
    List<T> findAll();

    //CREATE method
    void create(T data) throws GiftCertificateException;

    //Delete
    boolean delete(Integer id) throws NoSuchFieldException;
}
