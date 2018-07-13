package com.bjss.basket.support;

import java.util.Map;
import static java.lang.String.format;

public final class Configuration {
    private Map< String, String > apple;

    public Map< String, String > getApple() {
        return apple;
    }

    public void setApple(Map< String, String > apple) {
        this.apple = apple;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append( format( "Apple: %s\n", apple ) )
                .toString();
    }
}
