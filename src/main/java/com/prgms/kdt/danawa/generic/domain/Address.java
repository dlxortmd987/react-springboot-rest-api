package com.prgms.kdt.danawa.generic.domain;

import org.springframework.util.Assert;

import static com.prgms.kdt.danawa.generic.utils.RegexUtils.checkRegex;

public record Address(String value) {
    public static final String ADDRESS_REGEX = "((([가-힣A-Za-z·\\d~\\-\\.]{2,}(로|길).[\\d]+)|([가-힣A-Za-z·\\d~\\-\\.]+(읍|동)\\s)[\\d]+))";

    public Address(String value) {
        validateValue(value);
        this.value = value;
    }

    private static void validateValue(String value) {
        Assert.notNull(value, "address should not be null");
        Assert.isTrue(checkRegex(ADDRESS_REGEX, value), "Invalid address value.");
    }
}
