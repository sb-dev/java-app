package com.bjss.basket.domain;

import com.bjss.basket.api.Item;
import com.bjss.basket.support.PriceUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Configuration is a JavaBean class to store discounts retrieved from the config.yml file
 */
public class Discount implements Serializable {
    private String target;
    private LocalDate startDate;
    private LocalDate endDate;
    private int discountQuantityCondition;
    private String discountItemCondition;
    private double discountPercentage;
    private DateTimeFormatter formatter;

    /**
     * JavaBean constructor
     */
    public Discount() {
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    /**
     * Create a discount
     *
     * @param target
     * @param discountQuantityCondition
     * @param discountItemCondition
     * @param discountPercentage
     */
    public Discount(String target, int discountQuantityCondition, String discountItemCondition, double discountPercentage) {
        this();
        this.target = target;
        this.discountQuantityCondition = discountQuantityCondition;
        this.discountItemCondition = discountItemCondition;
        this.discountPercentage = discountPercentage;
    }

    /**
     * Create a discount with start date and end date
     *
     * @param target
     * @param discountQuantityCondition
     * @param discountItemCondition
     * @param discountPercentage
     */
    public Discount(String target, int discountQuantityCondition, String discountItemCondition, double discountPercentage, LocalDate startDate, LocalDate endDate) {
        this(target, discountQuantityCondition, discountItemCondition, discountPercentage);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /*
     * Getters and Setters
     */

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStartDate() {
        return this.startDate.format(this.formatter);
    }

    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, this.formatter);
    }

    public String getEndDate() {
        return this.endDate.format(this.formatter);
    }

    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, this.formatter);
    }

    public String getDiscountQuantityCondition() {
        return String.valueOf(this.discountQuantityCondition);
    }

    public void setDiscountQuantityCondition(String discountQuantityCondition) {
        this.discountQuantityCondition = Integer.valueOf(discountQuantityCondition);
    }

    public String getDiscountItemCondition() {
        return this.discountItemCondition;
    }

    public void setDiscountItemCondition(String discountItemCondition) {
        this.discountItemCondition = discountItemCondition;
    }

    public String getDiscountPercentage() {
        return String.valueOf(this.discountPercentage);
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = Double.valueOf(discountPercentage);
    }

    /**
     * Check if the discount is still valid
     *
     * @param date
     * @return Boolean
     */
    public Boolean isValid(LocalDate date) {
        if(this.startDate == null && this.endDate == null) {
            return true;
        }

        return date.isAfter(this.startDate) && date.isBefore(this.endDate);
    }

    /**
     * Get the discount value for an item
     *
     * @param itemMap items in basket
     * @param discounts discount descriptions
     * @return BigDecimal returns discount value
     */
    public BigDecimal getDiscountValue(Map<String,Item> itemMap, List<String> discounts) {
        BigDecimal discountValue = new BigDecimal("0.00");

        if(!this.isValid(LocalDate.now())) {
            return discountValue;
        }

        if(!itemMap.containsKey(target) || !itemMap.containsKey(discountItemCondition)) {
            return discountValue;
        }

        if(itemMap.get(target).getQuantity() == 0 || itemMap.get(discountItemCondition).getQuantity() == 0) {
            return discountValue;
        }

        String message = "{0} {1} off: -{2} \n";
        MessageFormat mf = new MessageFormat(message);

        int discountItemConditionCount = itemMap.get(discountItemCondition).getQuantity();
        int discountsAvailableToApply = discountItemConditionCount >= discountQuantityCondition ? (discountItemConditionCount/discountQuantityCondition): 0;

        int discountsToApply = itemMap.get(target).getQuantity() < discountsAvailableToApply ? itemMap.get(target).getQuantity() : discountsAvailableToApply;
        BigDecimal itemDiscountValue = itemMap.get(target).applyDiscount(discountPercentage);
        for(int i = 1; i <= discountsToApply; i++) {
            discountValue = discountValue.add(itemDiscountValue);

            String discountMessage = mf.format(new Object[] {capitalize(target), PriceUtil.displayPercentageValue(discountPercentage), PriceUtil.displayValue(itemDiscountValue)});
            discounts.add(discountMessage);
        }

        return discountValue;
    }

    /**
     * Capitalize item name
     *
     * @param input String to capitalize
     * @return Capitalized String
     */
    private String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
