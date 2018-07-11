package com.bjss.basket.domain;

import com.bjss.basket.api.Item;

import java.math.BigDecimal;

/**
 * Represents milk (a Priceable item)
 */
public class Milk extends Item {
    public Milk(String name) {
        super(name, "1.30");
    }
}
