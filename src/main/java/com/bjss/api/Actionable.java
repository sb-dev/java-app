package com.bjss.api;

import com.bjss.basket.support.Configuration;

/**
 * Actionable is an interface to indicate that a Program class can run this class as a action.
 */
public interface Actionable {
    /**
     * Run action
     *
     * @param config program configuration
     * @param parameters action parameters
     * @return String returns action output
     */
    String execute(Configuration config, String[] parameters);
}
