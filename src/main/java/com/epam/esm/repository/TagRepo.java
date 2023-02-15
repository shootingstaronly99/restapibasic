package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagException;

import java.util.Optional;

public interface TagRepo extends CommonRepo<Tag> {
    //findById
    Tag findById(Integer id) throws TagException;

    //FindByName
    Optional<Tag> findByName(String name);

}
