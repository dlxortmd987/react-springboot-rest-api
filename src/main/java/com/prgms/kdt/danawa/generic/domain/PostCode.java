package com.prgms.kdt.danawa.generic.domain;

import org.springframework.util.Assert;

import static com.prgms.kdt.danawa.generic.utils.RegexUtils.checkRegex;

public record PostCode(String number) {
    public static final String POSTCODE_REGEX = "[0-6][0-3]\\d{3}";

    public PostCode(String number) {
        this.number = number;
        validateNumber(number);
    }

    private static void validateNumber(String number) {
        Assert.notNull(number, "postcode should not be null");
        Assert.isTrue(checkRegex(POSTCODE_REGEX, number), "Invalid address value.");
    }
}
