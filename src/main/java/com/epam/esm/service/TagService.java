package com.epam.esm.service;

import com.epam.esm.entity.Tag;


import java.util.List;
import java.util.Optional;

/*
 * @author Otabek Javqochdiyev
 * */
public interface TagService {
    void create(Tag data) ;

    Optional<Tag> findById(Integer id);

    boolean delete(Integer id);

    Optional<Tag> findByName(String name);
    void deleteById(Long id);
    List<Tag> getAll();

}
