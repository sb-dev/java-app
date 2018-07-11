package com.bjss.basket;

import com.bjss.basket.domain.Basket;
import com.bjss.basket.api.Item;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasketTest {
    @Test
    public void testPopulateItemsWithOneItem() {
        String[] itemParameters = {"apple"};
        Basket basket = new Basket(itemParameters);

        List<Item> items = basket.getItems();
        Map<String,Item> itemMap = basket.getItemMap();

        assertEquals("Expected number of items", 1, items.size());

        assertEquals("Expected apple item name", "apple", itemMap.get("apple").getName());
        assertEquals("Expected apple item price", new BigDecimal("1.00"), itemMap.get("apple").getPrice());
    }

    @Test
    public void testPopulateItemsWithMultipleItems() {
        String[] itemParameters = {"apple", "soup", "bread", "milk"};
        Basket basket = new Basket(itemParameters);

        List<Item> items = basket.getItems();
        Map<String,Item> itemMap = basket.getItemMap();

        assertEquals("Expected number of items", 4, items.size());

        assertEquals("Expected apple item name", "apple", itemMap.get("apple").getName());
        assertEquals("Expected apple item price", new BigDecimal("1.00"), itemMap.get("apple").getPrice());
        assertEquals("Expected apple item quantity", 1, itemMap.get("apple").getQuantity());

        assertEquals("Expected soup item name", "soup", itemMap.get("soup").getName());
        assertEquals("Expected soup item price", new BigDecimal("0.65"), itemMap.get("soup").getPrice());
        assertEquals("Expected soup item quantity", 1, itemMap.get("soup").getQuantity());

        assertEquals("Expected bread item name", "bread", itemMap.get("bread").getName());
        assertEquals("Expected bread item price", new BigDecimal("0.80"), itemMap.get("bread").getPrice());
        assertEquals("Expected bread item quantity", 1, itemMap.get("bread").getQuantity());

        assertEquals("Expected milk item name", "milk", itemMap.get("milk").getName());
        assertEquals("Expected milk item price", new BigDecimal("1.30"), itemMap.get("milk").getPrice());
        assertEquals("Expected milk item quantity", 1, itemMap.get("milk").getQuantity());
    }

    @Test
    public void testPopulateItemsWithMultipleItemsIncludingDucplicates() {
        String[] itemParameters = {"apple", "soup", "bread", "milk", "bread", "soup"};
        Basket basket = new Basket(itemParameters);

        List<Item> items = basket.getItems();
        Map<String,Item> itemMap = basket.getItemMap();

        assertEquals("Expected number of items", 4, items.size());

        assertEquals("Expected apple item name", "apple", itemMap.get("apple").getName());
        assertEquals("Expected apple item price", new BigDecimal("1.00"), itemMap.get("apple").getPrice());
        assertEquals("Expected apple item quantity", 1, itemMap.get("apple").getQuantity());

        assertEquals("Expected soup item name", "soup", itemMap.get("soup").getName());
        assertEquals("Expected soup item price", new BigDecimal("0.65"), itemMap.get("soup").getPrice());
        assertEquals("Expected soup item quantity", 2, itemMap.get("soup").getQuantity());

        assertEquals("Expected bread item name", "bread", itemMap.get("bread").getName());
        assertEquals("Expected bread item price", new BigDecimal("0.80"), itemMap.get("bread").getPrice());
        assertEquals("Expected bread item quantity", 2, itemMap.get("bread").getQuantity());

        assertEquals("Expected milk item name", "milk", itemMap.get("milk").getName());
        assertEquals("Expected milk item price", new BigDecimal("1.30"), itemMap.get("milk").getPrice());
        assertEquals("Expected milk item quantity", 1, itemMap.get("milk").getQuantity());
    }

    @Test
    public void testPopulateItemsWithMultipleItemsIncludingDuplicatesAndUnknownItems() {
        String[] itemParameters = {"apple", "soup", "bread", "milk", "bread", "soup", "unknownItem1", "unknownItem2"};
        Basket basket = new Basket(itemParameters);

        List<Item> items = basket.getItems();
        Map<String,Item> itemMap = basket.getItemMap();

        assertEquals("Expected number of items", 4, items.size());

        assertEquals("Expected apple item name", "apple", itemMap.get("apple").getName());
        assertEquals("Expected apple item price", new BigDecimal("1.00"), itemMap.get("apple").getPrice());
        assertEquals("Expected apple item quantity", 1, itemMap.get("apple").getQuantity());

        assertEquals("Expected soup item name", "soup", itemMap.get("soup").getName());
        assertEquals("Expected soup item price", new BigDecimal("0.65"), itemMap.get("soup").getPrice());
        assertEquals("Expected soup item quantity", 2, itemMap.get("soup").getQuantity());

        assertEquals("Expected bread item name", "bread", itemMap.get("bread").getName());
        assertEquals("Expected bread item price", new BigDecimal("0.80"), itemMap.get("bread").getPrice());
        assertEquals("Expected bread item quantity", 2, itemMap.get("bread").getQuantity());

        assertEquals("Expected milk item name", "milk", itemMap.get("milk").getName());
        assertEquals("Expected milk item price", new BigDecimal("1.30"), itemMap.get("milk").getPrice());
        assertEquals("Expected milk item quantity", 1, itemMap.get("milk").getQuantity());
    }

    @Test
    public void testPriceBasketWithMultipleItemsAndNoDiscount() {
        String[] itemParameters = {"soup", "bread", "milk"};
        Basket basket = new Basket(itemParameters, this.getDiscounts());

        String result = basket.priceBasket();
        String expectedResult = "Subtotal: £2.75 \n " +
                "(no offers available) \n " +
                "Total: £2.75";
        assertEquals("Expected price basket result", expectedResult, result);
    }

    @Test
    public void testPriceBasketWithMultipleItemsAndNoDiscountIncludingItemDuplicates() {
        String[] itemParameters = {"soup", "bread", "milk", "bread"};
        Basket basket = new Basket(itemParameters, this.getDiscounts());

        String result = basket.priceBasket();
        String expectedResult = "Subtotal: £3.55 \n " +
                "(no offers available) \n " +
                "Total: £3.55";
        assertEquals("Expected price basket result", expectedResult, result);
    }

    @Test
    public void testPriceBasketWithMultipleItemsAndExpiredDiscountIncludingItemDuplicates() {
        String[] itemParameters = {"apple", "soup", "bread", "milk", "bread"};
        Basket basket = new Basket(itemParameters, this.getExpiredDiscounts());

        String result = basket.priceBasket();
        String expectedResult = "Subtotal: £4.55 \n " +
                "(no offers available) \n " +
                "Total: £4.55";
        assertEquals("Expected price basket result", expectedResult, result);
    }

    @Test
    public void testPriceBasketWithMultipleItemsAndOneDiscountIncludingItemDuplicates() {
        String[] itemParameters = {"apple", "soup", "bread", "milk", "bread"};
        Basket basket = new Basket(itemParameters, this.getDiscounts());

        String result = basket.priceBasket();
        assertTrue("Expected price basket result", result.contains("Subtotal: £4.55 \n "));
        assertTrue("Expected price basket result", result.contains("Apple 10% off: -10p \n"));
        assertTrue("Expected price basket result", result.contains("Total: £4.45"));
    }

    @Test
    public void testPriceBasketWithMultipleItemsAndMultipleDiscountsIncludingItemDuplicates() {
        String[] itemParameters = {"apple", "soup", "bread", "milk", "bread", "apple"};
        Basket basket = new Basket(itemParameters, this.getDiscounts());

        String result = basket.priceBasket();
        assertTrue("Expected price basket result", result.contains("Subtotal: £5.55 \n "));
        assertTrue("Expected price basket result", result.contains("Apple 10% off: -10p \n"));
        assertTrue("Expected price basket result", result.contains("Total: £5.35"));
    }

    @Test
    public void testPriceBasketWithMultipleItemsAndMultipleDiscountTypesIncludingItemDuplicates() {
        String[] itemParameters = {"apple", "soup", "bread", "milk", "bread", "apple", "soup"};
        Basket basket = new Basket(itemParameters, this.getDiscounts());

        String result = basket.priceBasket();
        assertTrue("Expected price basket result", result.contains("Subtotal: £6.20 \n "));
        assertTrue("Expected price basket result", result.contains("Bread 50% off: -40p \n"));
        assertTrue("Expected price basket result", result.contains("Apple 10% off: -10p \nApple 10% off: -10p \n"));
        assertTrue("Expected price basket result", result.contains("Total: £5.60"));
    }

    @Test
    public void testGetDiscount10OnAppleWithNoAppleItem() {
        String[] itemParameters = {"soup"};
        Basket basket = new Basket(itemParameters);

        assertEquals("Expected price basket result", new BigDecimal("0.00"), basket.getDiscount(1, "apple", 10.00, "apple"));
    }

    @Test
    public void testGetDiscount10OnAppleWithOneAppleItem() {
        String[] itemParameters = {"apple"};
        Basket basket = new Basket(itemParameters);

        assertEquals("Expected price basket result", new BigDecimal("0.10"), basket.getDiscount(1, "apple", 10.00, "apple"));
    }

    @Test
    public void testGetDiscount10OnAppleWithTwoAppleItems() {
        String[] itemParameters = {"apple","apple"};
        Basket basket = new Basket(itemParameters);

        assertEquals("Expected price basket result", new BigDecimal("0.20"), basket.getDiscount(1, "apple", 10.00, "apple"));
    }

    @Test
    public void testGetDiscountTwoSoup50OnBreadWithOneBreadItem() {
        String[] itemParameters = {"soup", "soup", "bread"};
        Basket basket = new Basket(itemParameters);

        assertEquals("Expected price basket result", new BigDecimal("0.40"), basket.getDiscount(2, "soup", 50.00, "bread"));
    }

    @Test
    public void testGetDiscounttwoSoup50OnBreadWithTwoBreadItems() {
        String[] itemParameters = {"soup", "soup", "bread", "bread"};
        Basket basket = new Basket(itemParameters);

        assertEquals("Expected price basket result", new BigDecimal("0.40"), basket.getDiscount(2, "soup", 50.00, "bread"));
    }

    @Test
    public void testGetDiscountTwoSoup50OnBreadWithTwoBreadItemAndFourSoupItems() {
        String[] itemParameters = {"soup", "soup", "soup", "soup", "bread", "bread"};
        Basket basket = new Basket(itemParameters);

        assertEquals("Expected price basket result", new BigDecimal("0.80"), basket.getDiscount(2, "soup", 50.00, "bread"));
    }

    @Test
    public void testGetDiscountTwoSoup50OnBreadWithTwoBreadItemAndNoSoupItem() {
        String[] itemParameters = {"bread", "bread"};
        Basket basket = new Basket(itemParameters);

        assertEquals("Expected price basket result", new BigDecimal("0.00"), basket.getDiscount(2, "soup", 50.00, "bread"));
    }

    public List<Map<String,String>> getDiscounts() {
        List<Map<String,String>> availableDiscounts =  new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");

        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        Map<String,String> appleDiscount = new HashMap<>();
        appleDiscount.put("startDate", startDate.format(formatter));
        appleDiscount.put("endDate", endDate.format(formatter));
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

        return availableDiscounts;
    }

    public List<Map<String,String>> getExpiredDiscounts() {
        List<Map<String,String>> availableDiscounts =  new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");

        LocalDate startDate = LocalDate.now().minusDays(2);
        LocalDate endDate = LocalDate.now().minusDays(1);

        Map<String,String> appleDiscount = new HashMap<>();
        appleDiscount.put("startDate", startDate.format(formatter));
        appleDiscount.put("endDate", endDate.format(formatter));
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

        return availableDiscounts;
    }

}
