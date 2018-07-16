package com.bjss.basket;

import com.bjss.basket.domain.Basket;
import com.bjss.basket.support.Configuration;
import com.bjss.support.Program;

public class App {
    public static void main( String... args ) {
        Program program = new Program(args);

        program
            .command("PriceBasket")
            .alias("pb")
            .action((Configuration config, String[] parameters) -> {
                Basket basket = new Basket(parameters, config);
                return basket.priceBasket();
            });

        program.exit();
    }
}
