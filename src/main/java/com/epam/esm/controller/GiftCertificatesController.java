package com.epam.esm.controller;


import com.epam.esm.common.ResponseModel;
import com.epam.esm.common.ResultMessage;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.service.GiftCertificatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "localhost:8080")
@RequiredArgsConstructor
@RequestMapping(value = "/api/gift", produces = "application/json")
public class GiftCertificatesController {
    private final GiftCertificatesService giftCertificateService;


    @GetMapping
    public ResponseEntity<ResponseModel<List<GiftCertificate>>> getAll() {

        return ResponseEntity.ok(giftCertificateService.getAll());
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseModel<List<GiftCertificate>>> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams) {
        return ResponseEntity.ok(giftCertificateService.searchWithFilter(allRequestParams));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseModel<GiftCertificate>> getById(@PathVariable int id) {
        return ResponseEntity.ok(giftCertificateService.getById(id));
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseModel<ResultMessage>> create(@RequestBody GiftCertificate gift) {
        return ResponseEntity.ok(giftCertificateService.create(gift));

    }

    @PatchMapping(path = "{id}", consumes = "application/json")
    public ResponseEntity<ResponseModel<ResultMessage>> updateGiftCertificate(@PathVariable("id") int id, @RequestBody GiftCertificate giftCertificate) throws IncorrectParameterException, GiftCertificateException {
        return ResponseEntity.ok(giftCertificateService.update(id, giftCertificate));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel<ResultMessage>> delete(@PathVariable("id") Integer id) throws NoSuchFieldException {
        return ResponseEntity.ok(giftCertificateService.delete(id));
    }

}
