package com.e.commerce.domain.e.commerce.domain.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String > {

    @Override
    public boolean test(String s) {

        //todo regex for email validator
        return true;
    }
}
