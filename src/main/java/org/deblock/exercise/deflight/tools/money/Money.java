package org.deblock.exercise.deflight.tools.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount, Currency currency) {

    public Money(BigDecimal amount) {
        this(amount, Currency.USD);
    }

    public Money add(Money other) {
        if (currency != other.currency) {
            throw new IllegalArgumentException();
        }
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        if (currency != other.currency) {
            throw new IllegalArgumentException();
        }
        return new Money(amount.subtract(other.amount), currency);
    }

    public Money setScale(int newScale, RoundingMode roundingMode) {
        return new Money(amount.setScale(newScale, roundingMode), currency);
    }
}
