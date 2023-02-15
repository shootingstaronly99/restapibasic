package com.epam.esm.controller;


import com.epam.esm.common.ResponseModel;
import com.epam.esm.common.ResultMessage;
import com.epam.esm.entity.Tag;
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

/**
 * @author Otabek Javqochdiyev
 * <h4>Controller for Tag entity</h4>
 */
@RestController
@CrossOrigin(origins = "localhost:8080")
@RequiredArgsConstructor
@RequestMapping(value = "/api/tag", produces = "application/json")
public class TagController {
    private final TagService tagService;

    /**
     * <p>Api for Getting all Tags</p>
     *
     * @return - ResponseModel's data field include List of Tag.
     */
    @GetMapping
    public ResponseEntity<ResponseModel<List<Tag>>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    /**
     * <p>Api for getting Tag by tagID </p>
     *
     * @param id-tagId
     * @return - One Tag  by existing tagId
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<Tag>> findTagById(@PathVariable int id) {
        return ResponseEntity.ok(tagService.getById(id));
    }

    /**
     * <p>API for finding tag by name</p>
     *
     * @param tagName- required tag's name
     * @return an Object type is Tag
     */
    @GetMapping(value = "/name/{tagName}")
    public ResponseEntity<ResponseModel<Tag>> findByTagName(@PathVariable String tagName) {
        return ResponseEntity.ok(tagService.findByName(tagName));
    }

    /**
     * <p>API for Creating Tag</p>
     *
     * @param tag-response body type Tag Json
     * @return - Success or failure message
     */
    @PostMapping()
    public ResponseEntity<ResponseModel<ResultMessage>> create(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.create(tag));
    }

    /**
     * <p>API for Removing Tag from database</p>
     *
     * @param id-deleting element's id
     * @return - Success or failure message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel<ResultMessage>> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(tagService.delete(id));
    }

}
