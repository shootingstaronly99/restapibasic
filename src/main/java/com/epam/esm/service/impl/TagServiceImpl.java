package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
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
    public void create(Tag data) throws NullPointerException {
        tagRepo.create(data);
    }

    @Override
    public boolean delete(Integer id) {
        tagRepo.delete(id);
        return true;
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        return tagRepo.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        tagRepo.deleteById(id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepo.findByName(name);
    }
}
