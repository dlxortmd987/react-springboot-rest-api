package com.prgms.kdt.danawa.order.domain;

import com.prgms.kdt.danawa.generic.domain.Money;

public record OrderItem(long productId, Money price, int quantity) {
}
