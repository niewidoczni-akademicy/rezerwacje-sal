package org.niewidoczniakademicy.rezerwacje.core.csv;

import com.univocity.parsers.annotations.HeaderTransformer;

import java.lang.reflect.Field;

public final class ContactPersonHeaderTransformer extends HeaderTransformer {

    private String prefix;

    public ContactPersonHeaderTransformer(String... args) {
        prefix = args[0];
    }

    @Override
    public String transformName(Field field, String name) {
        return prefix + " " + name;
    }
}
