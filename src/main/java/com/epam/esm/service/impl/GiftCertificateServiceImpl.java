package com.epam.esm.service.impl;

import com.epam.esm.common.ResponseModel;
import com.epam.esm.common.ResultMessage;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.repository.impl.GiftCertificatesRepoImpl;
import com.epam.esm.service.GiftCertificatesService;
import com.epam.esm.validator.GiftValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Service for Gift Certificate
 */
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificatesService {
    private final GiftCertificatesRepoImpl giftCertificatesRepo;

    //getAll method for GiftCertificate
    @Override
    public ResponseModel<List<GiftCertificate>> getAll() {
        var gifts = giftCertificatesRepo.findAll();
        return new ResponseModel<>(gifts);
    }

    //GetById method for
    @Override
    public ResponseModel<GiftCertificate> getById(Integer id) throws GiftCertificateException {
        try {
            var giftCertificate = giftCertificatesRepo.findById(id);
            return new ResponseModel<>(giftCertificate);
        } catch (Exception x) {
            throw new GiftCertificateException(x.getMessage());
        }
    }

    //Create method for GiftCertificate
    @Override
    @Transactional
    public ResponseModel<ResultMessage> create(GiftCertificate giftCertificate) {
        try {
            GiftValidator.validForCreate(giftCertificate);
            giftCertificatesRepo.create(giftCertificate);
            var res = ResultMessage.builder()
                    .message("Gift Certificate Successfully created!")
                    .build();
            return new ResponseModel<>(res);

        } catch (Exception e) {
            throw new GiftCertificateException(e.getMessage());
        }

    }

    //Delete method service for Gift Certificate
    @Override
    @Transactional
    public ResponseModel<ResultMessage> delete(Integer id) throws NoSuchFieldException {

        if (giftCertificatesRepo.findById(id) != null) {
            giftCertificatesRepo.delete(id);
            return new ResponseModel<>(new ResultMessage("Successfully deleted !"));
        }

        return new ResponseModel<>(new ResultMessage("This is gift certificates not found!"));
    }

    //Update method for
    @Override
    public ResponseModel<ResultMessage> update(int id, GiftCertificate giftCertificate) throws IncorrectParameterException, GiftCertificateException {
        var gift = getById(id).getData();
        GiftCertificate validatedGift = GiftValidator.validateForUpdate(gift, giftCertificate);
        validatedGift.setLastUpdateDate(LocalDateTime.now());
        giftCertificatesRepo.update(gift);
        return new ResponseModel<>(new ResultMessage("Gift Certificate successfully updated!"));
    }

    //Filter and Search method
    @Override
    public ResponseModel<List<GiftCertificate>> searchWithFilter(MultiValueMap<String, String> requestParams) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("tagName", getSingleRequestParameter(requestParams, "tagName"));
            map.put("giftName", getSingleRequestParameter(requestParams, "giftName"));
            map.put("giftDescription", getSingleRequestParameter(requestParams, "giftDescription"));
            map.put("sortByName", getSingleRequestParameter(requestParams, "sorByName"));
            map.put("sortByDate", getSingleRequestParameter(requestParams, "sortByDate"));
            return new ResponseModel<>(giftCertificatesRepo.getWithFilters(map));
        } catch (Exception e) {
            throw new GiftCertificateException(e.getMessage());
        }
    }

    // additional method for filter and search
    private String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
        if (requestParams.containsKey(parameter)) {
            return requestParams.get(parameter).get(0);
        } else {
            return null;
        }
    }
}
