package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificates;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.repository.GiftCertificatesRepo;
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
import java.util.Optional;

import static com.epam.esm.repository.query.QueryParam.*;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificatesService {
    private final GiftCertificatesRepo giftCertificatesRepo;


    @Override
    public List<GiftCertificates> getAll() throws NullPointerException {

        return giftCertificatesRepo.findAll();
    }

    @Override
    public Optional<GiftCertificates> findById(Integer id) {
        return giftCertificatesRepo.findById(id);
    }

    @Override
    @Transactional
    public void create(GiftCertificates giftCertificate) {
        giftCertificatesRepo.create(giftCertificate);
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        return giftCertificatesRepo.delete(id);
    }

    @Override
    public boolean update(int id, GiftCertificates giftCertificate) throws IncorrectParameterException {
         Optional<GiftCertificates> certificate = giftCertificatesRepo.findById(id);
         GiftCertificates gift = new GiftCertificates();
        if (certificate.isPresent()) {
            gift = certificate.get();
        }
         GiftCertificates validatedGift = GiftValidator.validateForUpdate(gift, giftCertificate);

        validatedGift.setLast_update_date(LocalDateTime.now());
         return giftCertificatesRepo.update(gift);
    }

    @Override
    public boolean updateGiftTag(int id) {
        return giftCertificatesRepo.updateGiftTag(id);
    }

    @Override
    public List<GiftCertificates> doFilter(MultiValueMap<String, String> requestParams) {
        Map<String, String> map = new HashMap<>();
        map.put(TAG_NAME, getSingleRequestParameter(requestParams, TAG_NAME));
        map.put(PART_NAME, getSingleRequestParameter(requestParams, PART_NAME));
        map.put(PART_DESCRIPTION, getSingleRequestParameter(requestParams, PART_DESCRIPTION));
        map.put(SORT_BY_NAME, getSingleRequestParameter(requestParams, SORT_BY_NAME));
        map.put(SORT_BY_DATE, getSingleRequestParameter(requestParams, SORT_BY_DATE));
        return giftCertificatesRepo.getWithFilters(map);
    }

    private String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
        if (requestParams.containsKey(parameter)) {
            return requestParams.get(parameter).get(0);
        } else {
            return null;
        }
    }
}
