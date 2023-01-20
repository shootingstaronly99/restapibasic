package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NullPointerException;
import com.epam.esm.repository.impl.TagRepoImpl;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepoImpl tagRepo;


    @Override
    @Transactional
    public List<Tag> getAll() {
        return tagRepo.findAll();
    }

    @Override
    public void create(Tag data) {
        tagRepo.create(data);
    }


    @Override
    public Optional<Tag> findById(Integer id) throws NullPointerException{
        return tagRepo.findById(id);
    }

    @Override
    public boolean delete(Integer id) throws NullPointerException {
        deleteById(id);
        return true;
    }

    @Override
    public void deleteById(Integer id) throws NullPointerException {
        tagRepo.deleteById(id);
    }

    @Override
    public Optional<Tag> findByName(String name)throws NullPointerException {
        return tagRepo.findByName(name);
    }


}
