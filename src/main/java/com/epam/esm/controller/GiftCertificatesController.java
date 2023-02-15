package com.epam.esm.controller;


import com.epam.esm.common.ResponseModel;
import com.epam.esm.common.ResultMessage;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.exception.IncorrectParameterException;
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

/**
 * @author Otabek Javqochdiyev
 * Controller for Gift Certificates
 */
@RestController
@CrossOrigin(origins = "localhost:8080")
@RequiredArgsConstructor
@RequestMapping(value = "/api/gift", produces = "application/json")
public class GiftCertificatesController {
    private final GiftCertificatesService giftCertificateService;

    /**
     * <p>API for getting all Gift certificates</p>
     *
     * @return ResponseModel's data include List of Certificates.
     */
    @GetMapping
    public ResponseEntity<ResponseModel<List<GiftCertificate>>> getAll() {

        return ResponseEntity.ok(giftCertificateService.getAll());
    }

    /**
     * <p>API for Filter Gift Certificates. Search , sort by fields.</p>
     *
     * @param allRequestParams This param for Sorting and Searching by filter, It can take all fields
     *                         and only one field in same time.
     * @return Response Model and the Response model's data include List of filtered Gift Certificates.
     */
    @GetMapping("/filter")
    public ResponseEntity<ResponseModel<List<GiftCertificate>>> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams) {
        return ResponseEntity.ok(giftCertificateService.searchWithFilter(allRequestParams));
    }

    /**
     * <p>API  for finding one gift Certificate by id </p>
     *
     * @param id giftCertificate id which in database
     * @return A gift Certificate by entering id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseModel<GiftCertificate>> getById(@PathVariable int id) {
        return ResponseEntity.ok(giftCertificateService.getById(id));
    }

    /**
     * <p> API   for creating new Gift Certificate  </p>
     *
     * @param gift request body for creating object type should be gift Certificate
     *             and list of Tags.
     * @return Response Model success or error message.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseModel<ResultMessage>> create(@RequestBody GiftCertificate gift) {
        return ResponseEntity.ok(giftCertificateService.create(gift));

    }

    /**
     * <p> API for changing Gift Certificate which following id. </p>
     *
     * @param id-Required      number for Update
     * @param giftCertificate- new data  for update existing object.
     * @return -Response Model success or error message.
     * @throws IncorrectParameterException - Handling for incorrect exception parameters.
     * @throws GiftCertificateException-   Handling  exceptions when data changing process.
     */
    @PatchMapping(path = "{id}", consumes = "application/json")
    public ResponseEntity<ResponseModel<ResultMessage>> updateGiftCertificate(@PathVariable("id") int id, @RequestBody GiftCertificate giftCertificate) throws IncorrectParameterException, GiftCertificateException {
        return ResponseEntity.ok(giftCertificateService.update(id, giftCertificate));

    }

    /**
     * <p>API for Remove Gift Certificate by id </p>
     *
     * @param id- Id for Deleted object.
     * @return - Should Return Success or failure messages.
     * @throws NoSuchFieldException - Handling when occur some exceptions during the wrong field.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel<ResultMessage>> delete(@PathVariable("id") Integer id) throws NoSuchFieldException {
        return ResponseEntity.ok(giftCertificateService.delete(id));
    }

}
