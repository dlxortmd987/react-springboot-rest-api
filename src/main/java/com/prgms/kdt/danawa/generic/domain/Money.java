package com.prgms.kdt.danawa.generic.domain;

import org.springframework.util.Assert;

import java.math.BigDecimal;

public class Money {

    private final BigDecimal amount;

    private Money(BigDecimal amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    private static void validateAmount(BigDecimal amount) {
        Assert.isTrue(isPositiveAmount(amount), "Money amount should not be negative.");
    }

    private static boolean isPositiveAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public static Money of(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }


}
