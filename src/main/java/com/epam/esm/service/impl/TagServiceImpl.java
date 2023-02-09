package com.epam.esm.service.impl;

import com.epam.esm.common.ResponseModel;
import com.epam.esm.common.ResultMessage;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ObjectNotFoundException;
import com.epam.esm.exception.TagException;
import com.epam.esm.repository.impl.TagRepoImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
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
    public ResponseModel<List<Tag>> getAll() {

        var tags = tagRepo.findAll();
        return new ResponseModel<>(tags);
    }

    @Override
    public ResponseModel<ResultMessage> delete(Integer id) {
        if (tagRepo.findById(id).isPresent()) {
            tagRepo.delete(id);
            return new ResponseModel<>(new ResultMessage("Successfully deleted !"));

        }
        return new ResponseModel<>(new ResultMessage("Tag not found!"));

    }

    @Override
    @Transactional
    public ResponseModel<ResultMessage> create(Tag data) {
        try {
            TagValidator.validateForCreate(data);
            tagRepo.create(data);
            var res = ResultMessage.builder()
                    .message("Tag Successfully created!")
                    .build();
            return new ResponseModel<>(res);
        } catch (Exception e) {
            throw new TagException(e.getMessage());
        }
    }


    @Override
    public ResponseModel<Tag> getById(Integer id) {
        try {
            Optional<Tag> tag = tagRepo.findById(id);
            return new ResponseModel<>(tag.get());
        } catch (Exception x) {
            throw new TagException(x.getMessage());
        }
    }

    @Override
    public ResponseModel<Tag> findByName(String name) {
        var tag = tagRepo.findByName(name).orElseThrow(() ->
                new ObjectNotFoundException("This named tage can't find! "));
        return new ResponseModel<>(tag);
    }


}
