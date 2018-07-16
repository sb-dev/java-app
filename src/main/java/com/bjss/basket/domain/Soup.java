package com.bjss.basket.domain;

import com.bjss.basket.api.Item;

import java.math.BigDecimal;

/**
 * Represents a soup (a Priceable item)
 */
public class Soup extends Item {
    public Soup(String name) {
        super(name, "0.65");
    }
    public Soup(String name, int quantity) {
        super(name, "0.65", quantity);
    }
}
