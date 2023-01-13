package com.epam.esm.controller;


import com.epam.esm.controller.response.ResponseHandler;
import com.epam.esm.controller.response.ResponseMessage;
import com.epam.esm.entity.GiftCertificates;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.service.GiftCertificatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "localhost:8080")

@RequestMapping(value = "/api/gift", produces = "application/json")
public class GiftCertificatesController {
    private final GiftCertificatesService giftCertificateService;

    @Autowired
    public GiftCertificatesController(GiftCertificatesService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    Logger logger = LoggerFactory.getLogger(GiftCertificatesController.class.getName());

    @GetMapping
    public ResponseEntity<List<GiftCertificates>> getAll() {
        return ResponseEntity.ok(giftCertificateService.getAll());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<GiftCertificates>> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams) {
        return ResponseEntity.ok(giftCertificateService.doFilter(allRequestParams));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        Optional<GiftCertificates> giftCertificateOptional = giftCertificateService.findById(id);
        GiftCertificates giftCertificate;
        if (giftCertificateOptional.isPresent()) {
            giftCertificate = giftCertificateOptional.get();
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, giftCertificate);
        } else {
            return ResponseHandler.generateResponse("Gift with id ( " + id + " ) was not found", HttpStatus.NOT_FOUND, "[]");
        }
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestBody GiftCertificates entity) {
        giftCertificateService.create(entity);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_CREATED, HttpStatus.OK);
    }

    @PatchMapping(path = "{id}", consumes = "application/json")
    public ResponseEntity<Object> updateGiftCertificate(@PathVariable("id") int id, @RequestBody GiftCertificates giftCertificate) throws IncorrectParameterException {
        boolean check = giftCertificateService.update(id, giftCertificate);
        if (check) {
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_UPDATED + id, HttpStatus.OK);
        } else {
            return ResponseHandler.generateResponse(ResponseMessage.UPDATE_ERROR + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        if (giftCertificateService.delete(id)) {
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_DELETED + id, HttpStatus.OK);
        }
        return ResponseHandler.generateResponse(ResponseMessage.DELETE_ERROR + id, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
