package com.bjss.basket;

import com.bjss.basket.support.PriceUtil;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PriceUtilTest {
    @Test
    public void testDisplayValueWithZeroValue() {
        BigDecimal price = new BigDecimal("0.00");
        assertEquals("Expect formatted price", "£0.00", PriceUtil.displayValue(price));
    }

    @Test
    public void testDisplayValueWithLongDecimal() {
        BigDecimal price = new BigDecimal("1.0000000000000354647373646453");
        assertEquals("Expect formatted price", "£1.00", PriceUtil.displayValue(price));
    }

    @Test
    public void testDisplayValueWithDecimalRoundUp() {
        BigDecimal price = new BigDecimal("1.6666666");
        assertEquals("Expect formatted price", "£1.67", PriceUtil.displayValue(price));
    }

    @Test
    public void testDisplayValueWithDecimalRoundDown() {
        BigDecimal price = new BigDecimal("1.644444444");
        assertEquals("Expect formatted price", "£1.64", PriceUtil.displayValue(price));
    }

    @Test
    public void testDisplayValueWithPennies() {
        BigDecimal price = new BigDecimal("0.60");
        assertEquals("Expect formatted price", "60p", PriceUtil.displayValue(price));
    }

    @Test
    public void displayPercentageValue() {
        assertEquals("Expect formatted price", "50%", PriceUtil.displayPercentageValue(50.00));
    }
}
