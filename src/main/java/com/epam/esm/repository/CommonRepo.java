package com.epam.esm.repository;


import java.util.List;
import java.util.Optional;

public interface CommonRepo<T, Long> {
    List<T> findAll();

    Optional<T> findById(Integer id);

    void create(T data);

    boolean delete(Integer id);


}
