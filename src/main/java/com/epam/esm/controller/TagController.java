package com.epam.esm.controller;


import com.epam.esm.common.ResponseModel;
import com.epam.esm.common.ResultMessage;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "localhost:8080")
@RequiredArgsConstructor
@RequestMapping(value = "/api/tag", produces = "application/json")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<ResponseModel<List<Tag>>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<Tag>> findTagById(@PathVariable int id) throws GiftCertificateException {
        return ResponseEntity.ok(tagService.getById(id));
    }

    @GetMapping(value = "/name/{tagName}")
    public ResponseEntity<ResponseModel<Tag>> findByTagName(@PathVariable String tagName) {
        return ResponseEntity.ok(tagService.findByName(tagName));
    }

    @PostMapping()
    public ResponseEntity<ResponseModel<ResultMessage>> create(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.create(tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel<ResultMessage>> delete(@PathVariable("id") Integer id) throws NoSuchFieldException {
        return ResponseEntity.ok(tagService.delete(id));
    }

}
