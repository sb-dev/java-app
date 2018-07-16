package com.bjss.basket;

import com.bjss.basket.App;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test the App class.
 */
public class AppTest
{
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testAppWithoutArgs() {
        App.main();

        assertEquals("Expected not found message", "Action not found", outContent.toString());
    }

    @Test
    public void testAppWithUnknownAction() {
        App.main("unknown");

        assertEquals("Expected not found message including unknown command", "Action not found: unknown", outContent.toString());
    }

    @Test
    public void testAppWithCommandName() {
        App.main("PriceBasket");

        String result = outContent.toString();
        assertTrue("Expected price basket result", result.contains("Subtotal: £0.00 \n "));
        assertTrue("Expected price basket result", result.contains("(no offers available)"));
        assertTrue("Expected price basket result", result.contains("Total: £0.00"));
    }

    @Test
    public void testAppWithCommandAlias() {
        App.main("pb");

        String result = outContent.toString();
        assertTrue("Expected price basket result", result.contains("Subtotal: £0.00 \n "));
        assertTrue("Expected price basket result", result.contains("(no offers available)"));
        assertTrue("Expected price basket result", result.contains("Total: £0.00"));
    }

    @Test
    public void testAppWithCommandAliasAndItems() {
        App.main("pb", "Soup", "Soup", "Bread");

        String result = outContent.toString();
        assertTrue("Expected Subtotal in result", result.contains("Subtotal: £2.10 \n "));
        assertTrue("Expected Discount in result", result.contains("Bread 50% off: -40p"));
        assertTrue("Expected Total in result", result.contains("Total: £1.70"));
    }
}
