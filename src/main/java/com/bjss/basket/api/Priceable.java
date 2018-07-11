package com.bjss.basket.api;

import java.math.BigDecimal;

/**
 * Priceable is an interface to indicate that a class can have a price; and as a result receive a discount.
 */
public interface Priceable {
    /**
     * Apply a discount on a Priceable class
     *
     * @param percentage Discount value in percentage
     * @return BigDecimal Discount value in pounds
     */
    BigDecimal applyDiscount(double percentage);
}
