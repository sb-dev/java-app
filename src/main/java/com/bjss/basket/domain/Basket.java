package com.bjss.basket.domain;

import com.bjss.basket.api.Item;
import com.bjss.basket.support.Configuration;
import com.bjss.basket.support.ItemFactory;
import com.bjss.basket.support.PriceUtil;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a basket which can contain items for sales
 */
public class Basket {
    private Map<String,Item> itemMap;
    private List<String> discounts;

    private List<Discount> availableDiscounts;

    private BigDecimal total = new BigDecimal("0.00");
    private BigDecimal subTotal = new BigDecimal("0.00");

    /**
     * Default constructor: initialising all Collections
     */
    public Basket() {
        this.itemMap = new HashMap<>();
        this.discounts = new ArrayList<>();
        this.availableDiscounts = new ArrayList<>();
    }

    /**
     * Create a Basket object and add items to it
     *
     * @param items array of String items
     */
    public Basket(String[] items) {
        this();
        this.populateItems(items);
    }

    /**
     * Create a Basket object, add items to it and available discount
     *
     * @param items array of String items
     * @param config contain item prices and available discounts
     */
    public Basket(String[] items, Configuration config) {
        this(items);
        this.availableDiscounts = config.getDiscounts();

        if(config.getItems() != null) {
            config.getItems().forEach(item -> {
                String itemName = item.get("name");
                if(itemMap.containsKey(itemName)){
                    itemMap.get(itemName).setPrice(item.get("price"));
                }
            });
        }
    }

    /**
     * Get itemsMap values
     *
     * @return List<Item> Items
     */
    public List<Item> getItems() {
        return new ArrayList(this.itemMap.values());
    }

    /**
     * Get items map
     *
     * @return Map<String,Item> items
     */
    public Map<String,Item> getItemMap() {
        return this.itemMap;
    }

    /**
     * Gets a receipt for the basket contents by applying available discounts
     *
     * @return String
     */
    public String priceBasket() {
        for(String itemMapKey: this.itemMap.keySet()) {
            Item item = itemMap.get(itemMapKey);
            BigDecimal itemCost = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
            this.subTotal = this.subTotal.add(itemCost);
        }

        BigDecimal discountValue = new BigDecimal("0.00");
        for (Discount discount : this.availableDiscounts) {
            discountValue = discountValue.add(discount.getDiscountValue(this.itemMap, this.discounts));
        }
        this.total = subTotal.subtract(discountValue);

        return this.getReceipt();
    }

    /**
     * Generates a receipt for the basket contents
     *
     * @return String returns basket receipt
     */
    public String getReceipt() {
        String message = "Subtotal: {0} \n {1} Total: {2}";
        MessageFormat mf = new MessageFormat(message);
        return mf.format(new Object[] {PriceUtil.displayValue(this.subTotal), this.getDiscountSummary(), PriceUtil.displayValue(this.total)});
    }

    /**
     * Adds items to basket
     *
     * @param items an array of string (item names)
     */
    private void populateItems(String[] items) {
        ItemFactory itemFactory = new ItemFactory();
        for(int i = 0; i < items.length; i++) {
            String itemName = items[i].toLowerCase();
            Item item = itemFactory.getItem(itemName);

            if(item == null) {
                continue;
            }

            if(!this.itemMap.containsKey(itemName)) {
                this.itemMap.put(itemName, item);
            } else {
                this.itemMap.get(itemName).add(1);
            }
        }
    }

    /**
     * Generates a summary for a discount
     *
     * @return String
     */
    private String getDiscountSummary() {
        String discountSummary = "(no offers available) \n";

        if(discounts.size() > 0) {
            discountSummary = "";

            for(String discountSummaryRow: discounts) {
                discountSummary += discountSummaryRow;
            }
        }

        return discountSummary;
    }
}
