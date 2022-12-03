package com.prgms.kdt.danawa.generic.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class PostCode {

    private static final String POSTCODE_REGEX = "[0-6][0-3]\\d{3}";

    @Size(min = 5, max = 5)
    @NotNull
    @Pattern(regexp = POSTCODE_REGEX)
    private final String number;

    public PostCode(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostCode postCode = (PostCode) o;
        return Objects.equals(number, postCode.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
