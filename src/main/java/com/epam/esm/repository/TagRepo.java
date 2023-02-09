package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagException;

import java.util.Optional;

public interface TagRepo extends CommonRepo<Tag> {
    Optional<Tag> findById(Integer id) throws TagException;

    Optional<Tag> findByName(String name);

}
