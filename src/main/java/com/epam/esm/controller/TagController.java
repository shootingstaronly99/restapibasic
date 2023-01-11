package com.epam.esm.controller;


import com.epam.esm.controller.response.ResponseHandler;
import com.epam.esm.controller.response.ResponseMessage;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "localhost:8090")
@RequiredArgsConstructor
@RequestMapping(value = "/api/tag",produces = "application/json")
public class TagController {
    Logger logger = LoggerFactory.getLogger(TagController.class.getName());
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTag() {
        return ResponseEntity.ok(tagService.getAll());
    }


    @GetMapping(value = "/test")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findTagById(@PathVariable int id) {
        Optional<Tag> optTag = tagService.findById(id);
        Tag tag;
        if (optTag.isPresent()) {
            tag = optTag.get();
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, tag);
        } else {
            return ResponseHandler.generateResponse("Tag with id (" + id + ") was not found", HttpStatus.NOT_FOUND, "[]");
        }
    }

    @GetMapping(value = "/name/{tagName}")
    public ResponseEntity<Object> findByTagName(@PathVariable String tagName) {
        Optional<Tag> optTag = tagService.findByName(tagName);
        Tag tag;
        if (optTag.isPresent()) {
            tag = optTag.get();
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, tag);
        } else {
            return ResponseHandler.generateResponse("Tag with name (" + tagName + ") was not found", HttpStatus.NOT_FOUND, "[]");
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Tag entity) throws Exception {
        tagService.create(entity);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_CREATED, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        boolean check = tagService.delete(id);
        if (check) {
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_DELETED_TAG + id, HttpStatus.OK);
        } else {
            return ResponseHandler.generateResponse(ResponseMessage.DELETE_ERROR + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
