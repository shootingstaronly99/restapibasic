package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.IncorrectParameterException;
import lombok.experimental.UtilityClass;
/*
Validator Class for Gift Certificate entity
 */

@UtilityClass
public class GiftValidator {
    private final int MAX_LENGTH_NAME = 50;
    private final int MIN_LENGTH_NAME = 4;
    private final int MAX_LENGTH_DESCRIPTION = 300;
    private final int MAX_DURATION = 366;
    private final int MIN_DURATION = 1;

    //Validator for Update Gift certificate
    public GiftCertificate validateForUpdate(GiftCertificate initialGift,
                                             GiftCertificate giftForUpdate) throws IncorrectParameterException {
        String name = giftForUpdate.getName();
        validateName(name);
        initialGift.setName(name);

        String description = giftForUpdate.getDescription();
        validateDescription(description);
        initialGift.setDescription(description);

        Double price = giftForUpdate.getPrice();
        validatePrice(price);
        initialGift.setPrice(price);

        int duration = giftForUpdate.getDuration();
        validateDuration(duration);
        initialGift.setDuration(duration);

        return initialGift;
    }

    //validator method create GiftCertificate
    public GiftCertificate validForCreate(GiftCertificate giftCertificate) throws IncorrectParameterException {
        validateName(giftCertificate.getName());
        validateDescription(giftCertificate.getDescription());
        validateDuration(giftCertificate.getDuration());
        validatePrice(giftCertificate.getPrice());
        return giftCertificate;
    }

    //validate name
    private void validateName(String name) throws IncorrectParameterException {
        if (name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterException("INCORRECT  GIFT CERTIFICATE NAME");
        }
    }

    //validate Description
    private void validateDescription(String description) throws IncorrectParameterException {
        if (description.length() > MAX_LENGTH_DESCRIPTION) {
            throw new IncorrectParameterException("INCORRECT GIFT CERTIFICATE DESCRIPTION");
        }
    }

    //Validate Price
    private void validatePrice(Double price) throws IncorrectParameterException {
        if (price < 0) {
            throw new IncorrectParameterException("INCORRECT GIFT CERTIFICATE PRICE");
        }
    }

    //validate Duration
    private void validateDuration(int duration) throws IncorrectParameterException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new IncorrectParameterException("INCORRECT GIFT CERTIFICATE DURATION");
        }
    }
}