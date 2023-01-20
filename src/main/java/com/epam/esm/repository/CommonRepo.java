package com.epam.esm.repository;


import com.epam.esm.exception.NullPointerException;

import java.util.List;
import java.util.Optional;

public interface CommonRepo<T> {
    List<T> findAll();

    Optional<T> findById(Integer id) throws NullPointerException;

    void create(T data) throws NullPointerException;

    boolean delete(Integer id);


}
