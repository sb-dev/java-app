package com.bjss.basket.domain;

import com.bjss.basket.api.Item;

import java.math.BigDecimal;

/**
 * Represents an apple (a Priceable item)
 */
public class Apple extends Item {
    public Apple(String name) {
        super(name, "1.00");
    }
}
