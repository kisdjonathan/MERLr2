package lab;

import interpreter.Entry;
import org.junit.Test;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class Lab {
    @Test (timeout = 200000)
    public void labTest1a() throws Exception {
        String input = "7 3";
        String output = "5";

        File solutionOutput = File.createTempFile("temp", "merl");

        Entry.scanner = new Scanner(input);
        Entry.writer = new PrintStream(solutionOutput);

        Entry.interpret("test files/lab1.merl");

        Scanner outputScanner = new Scanner(solutionOutput);
        assertEquals(outputScanner.nextLine().strip(), output);

        solutionOutput.deleteOnExit();
    }

    @Test (timeout = 2000)
    public void labTest1b() throws Exception {
        String input = "65 8";
        String output = "5";

        File solutionOutput = File.createTempFile("temp", "merl");

        Entry.scanner = new Scanner(input);
        Entry.writer = new PrintStream(solutionOutput);

        Entry.interpret("test files/lab1.merl");

        Scanner outputScanner = new Scanner(solutionOutput);
        assertEquals(outputScanner.nextLine().strip(), output);

        solutionOutput.deleteOnExit();
    }
}
