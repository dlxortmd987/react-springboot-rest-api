package com.prgms.kdt.danawa.generic.domain;

import org.springframework.util.Assert;

import static com.prgms.kdt.danawa.generic.utils.RegexUtils.checkRegex;

public record Email(String address) {
    public static final String EMAIL_REGEX = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";

    public Email(String address) {
        this.address = address;
        validateAddress(address);
    }

    private static void validateAddress(String address) {
        Assert.notNull(address, "address should not be null");
        Assert.isTrue(address.length() >= 4 && address.length() <= 50, "address length must be between 4 and 50 characters.");
        Assert.isTrue(checkRegex(EMAIL_REGEX, address), "Invalid email address");
    }
}