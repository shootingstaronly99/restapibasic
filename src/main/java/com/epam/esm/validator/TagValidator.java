package com.epam.esm.validator;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParameterException;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import static com.epam.esm.exception.ExceptionIncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_NAME;
@UtilityClass
public class TagValidator {
    private final int MAX_LENGTH_NAME = 50;
    private final int MIN_LENGTH_NAME = 2;
    public Tag validateForUpdate(Tag initialGift,
                                 Tag giftForUpdate) throws IncorrectParameterException {
        String name = giftForUpdate.getName();
        validateName(name);
        initialGift.setName(name);
        return initialGift;
    }
    private void validateName(String name) throws IncorrectParameterException {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME || StringUtils.isNumeric(name)) {
            throw new IncorrectParameterException(BAD_GIFT_CERTIFICATE_NAME);
        }
    }





}
