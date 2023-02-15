package com.epam.esm.validator;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParameterException;
import lombok.experimental.UtilityClass;
/*
Validator Class for Tag entity
 */


@UtilityClass
public class TagValidator {
    private final int MAX_LENGTH_NAME = 50;
    private final int MIN_LENGTH_NAME = 2;

    //validator for create process in Tag
    public Tag validateForCreate(Tag tag) throws IncorrectParameterException {
        validateName(tag.getName());
        return tag;
    }

    //validate tag name
    private void validateName(String name) throws IncorrectParameterException {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterException("INCORRECT  GIFT CERTIFICATE NAME");
        }
    }


}
