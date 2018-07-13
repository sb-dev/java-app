package com.bjss.support;

import com.bjss.api.Actionable;

public class Program {

    private String commandAction = "";
    private String commandAlias = "";

    private String action;
    private String[] parameters;

    public Program(String... args) {
        if(args.length > 0 ) {
            this.action = args[0];
            this.parameters = new String[args.length - 1];
            System.arraycopy(args, 1, parameters, 0, args.length - 1);
        }
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
            String output = actionable.execute(this.parameters);
            System.out.println(output);
        } else {
            this.resetCommand();
        }
    }

    public void exit() {
        if("".equalsIgnoreCase(this.commandAction)) {
            System.out.println("Action no recognised");
        }

        System.exit(0);
    }

    private void resetCommand() {
        this.commandAction = "";
        this.commandAlias = "";
    }
}
