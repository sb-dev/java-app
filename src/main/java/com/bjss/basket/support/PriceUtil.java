package com.bjss.basket.support;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * PriceUtil is a utility class to handle prices
 */
public class PriceUtil {
    /**
     * Formats a price to string version
     *
     * @param price
     * @return String price string version
     */
    public static String displayValue(BigDecimal price) {
        if(price == null) {
            return "£0.00";
        }

        if( price.compareTo( BigDecimal.valueOf( 0 ) ) > 0
                && price.compareTo( BigDecimal.valueOf( 1 ) ) < 0 ) {
            String result = price.setScale(2, RoundingMode.HALF_UP).toString();
            return result.split("\\.")[1] + "p";
        } {
            return "£" + price.setScale(2, RoundingMode.HALF_UP);
        }

    }

    /**
     * Format percentage
     * @param percentage
     * @return Formated percentage
     */
    public static String displayPercentageValue(double percentage) {
        return Double.toString(percentage).split("\\.")[0] + "%";
    }
}
