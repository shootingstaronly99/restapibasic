package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NullPointerException;


import java.util.List;
import java.util.Optional;

/*
 * @author Otabek Javqochdiyev
 * */
public interface TagService {
    void create(Tag data) ;

    Optional<Tag> findById(Integer id)throws NullPointerException;

    boolean delete(Integer id)throws NullPointerException;

    void deleteById(Integer id)throws NullPointerException;

    Optional<Tag> findByName(String name)throws NullPointerException;
    List<Tag> getAll()throws NullPointerException;

}
