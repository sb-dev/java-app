package com.bjss.basket;

import com.bjss.basket.domain.Basket;
import com.bjss.support.Program;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ProgramTest {
    @Test
    public void testProgramWithoutArguments() {
        Program program = new Program();

        program
            .command("Command")
            .action((String[] parameters) -> {
                return "Test output";
            });

            assertEquals("Should have expected empty program output", "", program.getOutput());
    }

    @Test
    public void testProgramWithOneArgument() {
        Program program = new Program("Command");

        program
            .command("Command")
            .action((String[] parameters) -> {
                return "Test output";
            });

        assertEquals("Should have expected program output", "Test output", program.getOutput());
    }

    @Test
    public void testProgramWithTwoActions() {
        Program program = new Program("Command2");

        program
            .command("Command1")
            .action((String[] parameters) -> {
                return "Test output 1";
            });

        program
            .command("Command2")
            .action((String[] parameters) -> {
                return "Test output 2";
            });

        assertEquals("Should have expected program output", "Test output 2", program.getOutput());
    }
}
