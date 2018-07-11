package com.bjss.basket.api;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Item is an abstract class meant to be extended in order to be create Priceable items which can be added
 * to Basket (com.bjss.basket.domain.Basket)
 */
public abstract class Item implements Priceable {
    private String name;
    private BigDecimal price;
    private int quantity = 1;

    /**
     * Create new item and set name and price
     *
     * @param name Item's name
     * @param price Item's price
     */
    public Item(String name, String price) {
        this.name = name;
        this.price = new BigDecimal(price);
    }

    /**
     * Get Item's name
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get Item's price
     * @return BigDecimal
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * Get Item's quantity
     * @return int
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Adds an Item
     *
     * @param quantity number of Items to add
     * @return int the new quantity after adding new Item(s)
     */
    public int add(int quantity) {
        if (quantity < 0 && Math.abs(quantity) > this.quantity) {
            this.quantity = 0;
        } else {
            this.quantity += quantity;
        }
        return this.quantity;
    }

    public BigDecimal applyDiscount(double percentage) {
        return this.price.multiply(new BigDecimal(percentage/100).setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Use the Item's name to make the difference between items
     *
     * @param o Item to compare
     * @return boolean comparison result
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Item)) {
            return false;
        }

        Item thatItem = (Item) o;
        return thatItem.name == this.name;
    }

    /**
     * Computes the hashCode of an Item's name
     *
     * @return int Computed hashCode
     */
    public int hashCode() {
        return this.name.hashCode();
    }
}
