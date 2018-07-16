package com.bjss.basket.support;

import com.bjss.basket.domain.Discount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.lang.String.format;

/**
 * Configuration is a JavaBean class to store prices and discounts retrieved from the config.yml file
 */
public final class Configuration implements Serializable {
    private List<Map< String, String >> items;
    private List<Discount> discounts;

    /**
     * JavaBean constructor
     */
    public Configuration() {
        this.discounts = new ArrayList<>();
    }

    public List<Map<String, String>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, String>> items) {
        this.items = items;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append( format( "Items: %s\n", items ) )
            .append( format( "Discounts: %s\n", discounts ) )
            .toString();
    }
}
