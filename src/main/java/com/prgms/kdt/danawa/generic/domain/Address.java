package com.prgms.kdt.danawa.generic.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class Address {

    private static final String ADDRESS_REGEX = "((([가-힣A-Za-z·\\d~\\-\\.]{2,}(로|길).[\\d]+)|([가-힣A-Za-z·\\d~\\-\\.]+(읍|동)\\s)[\\d]+))";

    @Size(min = 4, max = 100)
    @NotNull
    @Pattern(regexp = ADDRESS_REGEX)
    private final String value;

    public Address(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(value, address.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
