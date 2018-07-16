package com.bjss.support;

import com.bjss.api.Actionable;
import com.bjss.basket.support.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Program is a class to handle user input and call the relevant action.
 *
 * It tries to match a user input with an action by following this pattern:
 *
 * <Command Name> [Parameters...]
 *
 */
public class Program {

    private String commandAction = "";
    private String commandAlias = "";

    private String action;
    private String[] parameters;
    private Configuration config;

    private String output;
    private String errorOutput;

    /**
     *  Create a Program object
     *
     * @param args User input
     */
    public Program(String... args) {
        if(args.length > 0 ) {
            this.action = args[0];
            this.parameters = new String[args.length - 1];
            System.arraycopy(args, 1, parameters, 0, args.length - 1);
        }

        // Retrieve the program configuration from the resources directory
        Yaml yaml = new Yaml();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream in = classLoader.getResourceAsStream("config.yml")) {
            this.config = yaml.loadAs( in, Configuration.class );
        } catch (IOException ex) {
            this.errorOutput = "Could not load program configuration (src/main/resources/config.yml)";
        }
    }

    /**
     * Get the most recent action output
     *
     * @return String action output
     */
    public String getOutput() {
        return this.output;
    }

    /**
     * Set the program error output
     *
     * @param errorOutput
     */
    public void setErrorOutput(String errorOutput) {
        this.errorOutput = errorOutput;
    }

    /**
     * Set a command name for an action
     *
     * @param action
     * @return return Program to enable chaining
     */
    public Program command(String action) {
        this.commandAction = action;
        return this;
    }

    /**
     * Set a command alias for an action
     *
     * @param alias
     * @return return Program to enable chaining
     */
    public Program alias(String alias) {
        this.commandAlias = alias;
        return this;
    }

    /**
     * Set a lambda (action) to be called when the user input matches the command name or command alias
     *
     * @param actionable a lambda function returning its output
     */
    public void action(Actionable actionable) {
        if(this.commandAction.equalsIgnoreCase(this.action) || this.commandAlias.equalsIgnoreCase(this.action)) {
            this.output = actionable.execute(this.config, this.parameters);
            this.displayOutput();
        } else {
            this.resetCommand();
        }
    }

    /**
     * Check if an action could be matched with the user input before exiting the program
     */
    public void exit() {
        if("".equalsIgnoreCase(this.commandAction)) {
            this.output = "Action not found";

            if (this.action != null) {
                this.output += ": " + this.action;
            }

            this.displayOutput();
        }
    }

    /**
     * Resets the program
     */
    private void resetCommand() {
        this.commandAction = "";
        this.commandAlias = "";
        this.output = "";
    }

    /**
     * Displays the output of the most recent action
     */
    private void displayOutput() {
        if(errorOutput == null) {
            System.out.print(this.output);
        } else {
            System.err.print(this.errorOutput);
        }
    }
}
