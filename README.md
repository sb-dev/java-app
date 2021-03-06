# Coding Assignment

Basket for grocery shopping.

## Prerequisites

Built with Maven for dependency management and JUnit for testing

## Installing

```
mvn package 
```

## Running the tests

```
mvn test 
```

## Running the program

The program allows to run actions on a basket.

__List of actions__:

* __PriceBasket__: Accepts a list of items (duplicates allowed) in the basket and outputs the subtotal, 
the special offer discounts and the final price (available items: Apple, Bread, Milk and Soup).

```
java -jar target/coding-assignment-1.0-SNAPSHOT.jar <action> [items...]
```

The item prices and discounts can be updated by changing the configuration file (src/main/resources/config.yml).