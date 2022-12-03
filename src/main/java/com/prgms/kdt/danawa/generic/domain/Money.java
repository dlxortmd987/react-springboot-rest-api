package com.prgms.kdt.danawa.generic.domain;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public class Money {

    @DecimalMin(value = "0.0")
    private final BigDecimal amount;

    private Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money of(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public static Money of(double amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

}