package com.bjss.basket;

import com.bjss.basket.api.Item;
import com.bjss.basket.domain.Apple;
import com.bjss.basket.domain.Bread;
import com.bjss.basket.domain.Milk;
import com.bjss.basket.domain.Soup;
import com.bjss.basket.support.ItemFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ItemFactoryTest {
    @Test
    public void testItemFactoryWithNoName() {
        ItemFactory itemFactory = new ItemFactory();
        Item item = itemFactory.getItem(null);
        assertTrue("Expect null", item == null);
    }

    @Test
    public void testItemFactoryWithUnknownItem() {
        ItemFactory itemFactory = new ItemFactory();
        Item item = itemFactory.getItem("cheese");
        assertTrue("Expect null", item == null);
    }

    @Test
    public void testItemFactoryWithDifferentCasing() {
        ItemFactory itemFactory = new ItemFactory();
        Item item = itemFactory.getItem("aPPle");
        assertTrue("Expect Apple instance", item instanceof Apple);
    }

    @Test
    public void testItemFactoryWithApple() {
        ItemFactory itemFactory = new ItemFactory();
        Item item = itemFactory.getItem("apple");
        assertTrue("Expect Apple instance", item instanceof Apple);
    }

    @Test
    public void testItemFactoryWithBread() {
        ItemFactory itemFactory = new ItemFactory();
        Item item = itemFactory.getItem("bread");
        assertTrue("Expect Bread instance ", item instanceof Bread);
    }

    @Test
    public void testItemFactoryWithMilk() {
        ItemFactory itemFactory = new ItemFactory();
        Item item = itemFactory.getItem("milk");
        assertTrue("Expect Milk instance", item instanceof Milk);
    }

    @Test
    public void testItemFactoryWithSoup() {
        ItemFactory itemFactory = new ItemFactory();
        Item item = itemFactory.getItem("soup");
        assertTrue("Expect Soup instance", item instanceof Soup);
    }
}
