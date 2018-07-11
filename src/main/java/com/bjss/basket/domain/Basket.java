package com.bjss.basket.domain;

import com.bjss.basket.api.Item;
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

/**
 * Represents a basket which can contain items for sales
 */
public class Basket {
    private Map<String,Item> itemMap;
    private List<String> discounts;

    private List<Map<String,String>> availableDiscounts;

    private BigDecimal total = new BigDecimal("0.00");
    private BigDecimal subTotal = new BigDecimal("0.00");

    private DateTimeFormatter formatter;

    /**
     * Default constructor: initialising all Collections
     */
    public Basket() {
        this.itemMap = new HashMap<>();
        this.discounts = new ArrayList<>();

        this.availableDiscounts =  new ArrayList<>();

        this.formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        Map<String,String> appleDiscount = new HashMap<>();
        appleDiscount.put("startDate", "2018 07 09");
        appleDiscount.put("endDate", "2018 07 16");
        appleDiscount.put("discountQuantityCondition", "1");
        appleDiscount.put("discountItemCondition", "apple");
        appleDiscount.put("discountPercentage", "10.00");
        appleDiscount.put("targetItem", "apple");

        Map<String,String> breadDiscount = new HashMap<>();
        breadDiscount.put("discountQuantityCondition", "2");
        breadDiscount.put("discountItemCondition", "soup");
        breadDiscount.put("discountPercentage", "50.00");
        breadDiscount.put("targetItem", "bread");

        availableDiscounts.add(appleDiscount);
        availableDiscounts.add(breadDiscount);

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
     * @param availableDiscounts available discount
     */
    public Basket(String[] items, List<Map<String,String>> availableDiscounts) {
        this(items);
        this.availableDiscounts = availableDiscounts;
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
        for(Map<String, String> discountItem: this.availableDiscounts) {


            if (discountItem.containsKey("startDate") && discountItem.containsKey("endDate")) {
                LocalDate startDate = LocalDate.parse(discountItem.get("startDate"), formatter);
                LocalDate endDate = LocalDate.parse(discountItem.get("endDate"), formatter);

                if(LocalDate.now().isAfter(startDate) && LocalDate.now().isBefore(endDate)) {
                    discountValue = discountValue.add(this.getDiscount(
                            Integer.valueOf(discountItem.get("discountQuantityCondition")),
                            discountItem.get("discountItemCondition"),
                            Double.valueOf(discountItem.get("discountPercentage")),
                            discountItem.get("targetItem")
                    ));
                }
            } else {
                discountValue = discountValue.add(this.getDiscount(
                        Integer.valueOf(discountItem.get("discountQuantityCondition")),
                        discountItem.get("discountItemCondition"),
                        Double.valueOf(discountItem.get("discountPercentage")),
                        discountItem.get("targetItem")
                ));
            }
        }

        this.total = subTotal.subtract(discountValue);

        return this.getReceipt();
    }

    /**
     * Get total discount for an item
     *
     * @param discountQuantityCondition
     * @param discountItemCondition
     * @param discountPercentage
     * @param targetItem
     * @return total discount for an item
     */
    public BigDecimal getDiscount(int discountQuantityCondition, String discountItemCondition, double discountPercentage, String targetItem) {
        BigDecimal discountValue = new BigDecimal("0.00");

        if(!this.itemMap.containsKey(targetItem) || !this.itemMap.containsKey(discountItemCondition)) {
            return discountValue;
        }

        if(this.itemMap.get(targetItem).getQuantity() == 0 || this.itemMap.get(discountItemCondition).getQuantity() == 0) {
            return discountValue;
        }

        String message = "{0} {1} off: -{2} \n";
        MessageFormat mf = new MessageFormat(message);

        int discountItemConditionCount = this.itemMap.get(discountItemCondition).getQuantity();
        int discountsAvailableToApply = discountItemConditionCount >= discountQuantityCondition ? (discountItemConditionCount/discountQuantityCondition): 0;

        int discountsToApply = this.itemMap.get(targetItem).getQuantity() < discountsAvailableToApply ? this.itemMap.get(targetItem).getQuantity() : discountsAvailableToApply;
        BigDecimal itemDiscountValue = this.itemMap.get(targetItem).applyDiscount(discountPercentage);
        for(int i = 1; i <= discountsToApply; i++) {
            discountValue = discountValue.add(itemDiscountValue);

            String discountMessage = mf.format(new Object[] {this.capitalize(targetItem), PriceUtil.displayPercentageValue(discountPercentage), PriceUtil.displayValue(itemDiscountValue)});
            discounts.add(discountMessage);
        }

        return discountValue;
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

    /**
     * Capitalize String
     * @param input
     * @return Capitalized String
     */
    private String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
