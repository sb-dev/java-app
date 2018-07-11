package com.bjss.basket;

import com.bjss.basket.api.Item;
import com.bjss.basket.domain.Apple;
import com.bjss.basket.domain.Bread;
import com.bjss.basket.domain.Milk;
import com.bjss.basket.domain.Soup;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemTest {

    @Test
    public void testBreadName() {
        Item bread = new Bread("bread");
        assertEquals("Expect bread item name", "bread", bread.getName());
    }

    @Test
    public void testBreadPrice() {
        Item bread = new Bread("bread");
        assertEquals("Expect bread item price", new BigDecimal("0.80"), bread.getPrice());
    }

    @Test
    public void testBreadQuantity() {
        Item bread = new Bread("bread");
        assertEquals("Expect bread item quantity", 1, bread.getQuantity());

        assertEquals("Expect bread item quantity", 3, bread.add(2));
        assertEquals("Expect bread item quantity", 3, bread.getQuantity());

        assertEquals("Expect bread item quantity", 0, bread.add(-5));
    }

    @Test
    public void testApplyDiscountOnBread() {
        Item bread = new Bread("bread");
        assertEquals("Expect bread item price", new BigDecimal("0.40"), bread.applyDiscount(50.00));
    }

    @Test
    public void testIsEqualWithEqualItems() {
        Item item1 = new Bread("bread");
        Item item2 = new Bread("bread");

        assertTrue("Expect equal items", item1.equals(item2));
    }

    @Test
    public void testIsEqualWithNotUnequalItems() {
        Item item1 = new Bread("bread");
        Item item2 = new Bread("apple");

        assertFalse("Expect unequal items", item1.equals(item2));
    }

    @Test
    public void testHashcode() {
        Item bread = new Bread("bread");

        assertEquals("Expect hashCode to be performed on the Name field", "bread".hashCode(), bread.hashCode());
    }

    @Test
    public void testApple() {
        Item bread = new Apple("apple");
        assertEquals("Expect bread item name", "apple", bread.getName());
        assertEquals("Expect bread item price", new BigDecimal("1.00"), bread.getPrice());
    }

    @Test
    public void testMilk() {
        Item bread = new Milk("milk");
        assertEquals("Expect bread item name", "milk", bread.getName());
        assertEquals("Expect bread item price", new BigDecimal("1.30"), bread.getPrice());
    }

    @Test
    public void testSoup() {
        Item bread = new Soup("soup");
        assertEquals("Expect bread item name", "soup", bread.getName());
        assertEquals("Expect bread item price", new BigDecimal("0.65"), bread.getPrice());
    }
}
