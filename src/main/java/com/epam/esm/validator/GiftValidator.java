package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificates;
import com.epam.esm.exception.IncorrectParameterException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import static com.epam.esm.exception.ExceptionIncorrectParameterMessageCodes.*;


@UtilityClass
public class GiftValidator {
    private final int MAX_LENGTH_NAME = 50;
    private final int MIN_LENGTH_NAME = 4;
    private final int MAX_LENGTH_DESCRIPTION = 300;
    private final int MAX_DURATION = 366;
    private final int MIN_DURATION = 1;

    public GiftCertificates validateForUpdate(GiftCertificates initialGift,
                                              GiftCertificates giftForUpdate) throws IncorrectParameterException {
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

    private void validateName(String name) throws IncorrectParameterException {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME || StringUtils.isNumeric(name)) {
            throw new IncorrectParameterException(String.valueOf(BAD_GIFT_CERTIFICATE_NAME));
        }
    }

    private void validateDescription(String description) throws IncorrectParameterException {
        if (description == null || description.length() > MAX_LENGTH_DESCRIPTION || StringUtils.isNumeric(description)) {
            throw new IncorrectParameterException(String.valueOf(BAD_GIFT_CERTIFICATE_DESCRIPTION));
        }
    }

    private void validatePrice(Double price) throws IncorrectParameterException {
        if (price == null || price < 0) {
            throw new IncorrectParameterException(String.valueOf(BAD_GIFT_CERTIFICATE_PRICE));
        }
    }

    private void validateDuration(int duration) throws IncorrectParameterException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new IncorrectParameterException(String.valueOf(BAD_GIFT_CERTIFICATE_DURATION));
        }
    }
}