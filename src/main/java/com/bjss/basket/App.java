package com.bjss.basket;

import com.bjss.basket.domain.Basket;

public class App {
    public static void main( String[] args )
    {
        if(args[0].equalsIgnoreCase("PriceBasket")) {
            String[] items = new String[args.length - 1];
            System.arraycopy(args, 1, items, 0, args.length - 1);

            Basket basket = new Basket(items);
            System.out.println(basket.priceBasket());
        } else {
            System.out.println("Action no recognised");
        }
    }
}
