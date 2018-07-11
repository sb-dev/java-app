package com.bjss.basket.support;

import com.bjss.basket.api.Item;
import com.bjss.basket.domain.Apple;
import com.bjss.basket.domain.Bread;
import com.bjss.basket.domain.Milk;
import com.bjss.basket.domain.Soup;

/**
 * ItemFactory is a factory used to create new items according to items' names
 */
public class ItemFactory {
    /**
     * Returns the correct instance of an Item child class
     * Returns null if the Item child class doesn't exist
     *
     * @param itemName
     * @return Item child class
     */
    public Item getItem(String itemName) {
        if(itemName == null){
            return null;
        }

        if(itemName.equalsIgnoreCase("APPLE")){
            return new Apple(itemName);
        } else if(itemName.equalsIgnoreCase("BREAD")){
            return new Bread(itemName);
        } else if(itemName.equalsIgnoreCase("MILK")){
            return new Milk(itemName);
        } else if(itemName.equalsIgnoreCase("SOUP")){
            return new Soup(itemName);
        }

        return null;
    }
}
