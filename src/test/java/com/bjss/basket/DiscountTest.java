package com.bjss.basket;

import com.bjss.basket.api.Item;
import com.bjss.basket.domain.Apple;
import com.bjss.basket.domain.Bread;
import com.bjss.basket.domain.Discount;
import com.bjss.basket.domain.Soup;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DiscountTest {

    @Test
    public void testGetDiscountValue10OnAppleWithNoAppleItem() {
        Discount discount = new Discount("bread", 2, "soup", 50.00);

        Map<String, Item> itemMap = new HashMap<>();
        itemMap.put("soup", new Soup("soup", 1));
        List<String> discounts = new ArrayList<>();

        assertEquals("Expected price basket result", new BigDecimal("0.00"), discount.getDiscountValue(itemMap, discounts));
    }

    @Test
    public void testGetDiscountValue10OnAppleWithOneAppleItem() {
        Discount discount = new Discount("apple", 1, "apple", 10.00);

        Map<String, Item> itemMap = new HashMap<>();
        itemMap.put("apple", new Apple("apple", 1));
        List<String> discounts = new ArrayList<>();

        assertEquals("Expected price basket result", new BigDecimal("0.10"), discount.getDiscountValue(itemMap, discounts));
    }

    @Test
    public void testGetDiscountValue10OnAppleWithTwoAppleItems() {
        Discount discount = new Discount("apple", 1, "apple", 10.00);

        Map<String, Item> itemMap = new HashMap<>();
        itemMap.put("apple", new Apple("apple", 2));
        List<String> discounts = new ArrayList<>();

        assertEquals("Expected price basket result", new BigDecimal("0.20"), discount.getDiscountValue(itemMap, discounts));
    }

    @Test
    public void testGetDiscountValueTwoSoup50OnBreadWithOneBreadItem() {
        Discount discount = new Discount("bread", 2, "soup", 50.00);

        Map<String, Item> itemMap = new HashMap<>();
        itemMap.put("soup", new Soup("soup", 2));
        itemMap.put("bread", new Bread("bread"));
        List<String> discounts = new ArrayList<>();

        assertEquals("Expected price basket result", new BigDecimal("0.40"), discount.getDiscountValue(itemMap, discounts));
    }

    @Test
    public void testGetDiscountValuetwoSoup50OnBreadWithTwoBreadItems() {
        Discount discount = new Discount("bread", 2, "soup", 50.00);

        Map<String, Item> itemMap = new HashMap<>();
        itemMap.put("soup", new Soup("soup", 2));
        itemMap.put("bread", new Bread("bread", 2));
        List<String> discounts = new ArrayList<>();

        assertEquals("Expected price basket result", new BigDecimal("0.40"), discount.getDiscountValue(itemMap, discounts));
    }

    @Test
    public void testGetDiscountValueTwoSoup50OnBreadWithTwoBreadItemAndFourSoupItems() {
        Discount discount = new Discount("bread", 2, "soup", 50.00);

        Map<String, Item> itemMap = new HashMap<>();
        itemMap.put("soup", new Soup("soup", 4));
        itemMap.put("bread", new Bread("bread", 2));
        List<String> discounts = new ArrayList<>();

        assertEquals("Expected price basket result", new BigDecimal("0.80"), discount.getDiscountValue(itemMap, discounts));
    }

    @Test
    public void testGetDiscountValueTwoSoup50OnBreadWithTwoBreadItemAndNoSoupItem() {
        Discount discount = new Discount("bread", 2, "soup", 50.00);

        Map<String, Item> itemMap = new HashMap<>();
        itemMap.put("bread", new Bread("bread", 2));
        List<String> discounts = new ArrayList<>();

        assertEquals("Expected price basket result", new BigDecimal("0.00"), discount.getDiscountValue(itemMap, discounts));
    }
}
