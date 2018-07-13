package com.bjss.basket;

import com.bjss.basket.domain.Basket;
import com.bjss.basket.support.Configuration;
import com.bjss.support.Program;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URISyntaxException;

public class App {
    public static void main( String[] args ) throws IOException, URISyntaxException {
        Program program = new Program(args);

        program
            .command("PriceBasket")
            .alias("pb")
            .action((String[] parameters) -> {
                Basket basket = new Basket(parameters);
                return basket.priceBasket();
            });

        program.exit();

//        Yaml yaml = new Yaml();
//        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//        InputStream in = classLoader.getResourceAsStream("config.yml");
//        Configuration config = yaml.loadAs( in, Configuration.class );
//        System.out.println( config.toString() );

    }
}
