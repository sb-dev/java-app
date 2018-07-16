package com.bjss.basket.domain;

import com.bjss.basket.api.Item;

import java.math.BigDecimal;

/**
 * Represents bread (a Priceable item)
 */
public class Bread extends Item {
    public Bread(String name) {
        super(name, "0.80");
    }
    public Bread(String name, int quantity) {
        super(name, "0.80", quantity);
    }

}
