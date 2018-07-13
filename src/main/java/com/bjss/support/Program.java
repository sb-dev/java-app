package com.bjss.support;

import com.bjss.api.Actionable;

public class Program {

    private String commandAction = "";
    private String commandAlias = "";

    private String action;
    private String[] parameters;

    private String output;

    public Program(String... args) {
        if(args.length > 0 ) {
            this.action = args[0];
            this.parameters = new String[args.length - 1];
            System.arraycopy(args, 1, parameters, 0, args.length - 1);
        }
    }

    public String getOutput() {
        return this.output;
    }

    public Program command(String action) {
        this.commandAction = action;
        return this;
    }

    public Program alias(String alias) {
        this.commandAlias = alias;
        return this;
    }

    public void action(Actionable actionable) {
        if(this.commandAction.equalsIgnoreCase(this.action) || this.commandAlias.equalsIgnoreCase(this.action)) {
            this.output = actionable.execute(this.parameters);
            this.displayOutput();
        } else {
            this.resetCommand();
        }
    }

    public void exit() {
        if("".equalsIgnoreCase(this.commandAction)) {
            this.output = "Action no recognised";
            this.displayOutput();
        }

        System.exit(0);
    }

    private void resetCommand() {
        this.commandAction = "";
        this.commandAlias = "";
        this.output = "";
    }

    private void displayOutput() {
        System.out.println(this.output);
    }
}
