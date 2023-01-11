package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.Optional;

public interface  TagRepo extends CommonRepo<Tag, Long> {
    Optional<Tag> findByName(String name);
      void deleteById(Long id);

}
