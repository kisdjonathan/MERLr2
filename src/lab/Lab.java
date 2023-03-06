package lab;

import interpreter.Entry;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Scanner;


public class Lab {
    @Test (timeout = 2000)
    public void lab1aTest() throws Exception{
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
    public void lab1bTest() throws Exception{
        String input = "65 8";
        String output = "17";

        File solutionOutput = File.createTempFile("temp", "merl");

        Entry.scanner = new Scanner(input);
        Entry.writer = new PrintStream(solutionOutput);

        Entry.interpret("test files/lab1.merl");

        Scanner outputScanner = new Scanner(solutionOutput);
        assertEquals(outputScanner.nextLine().strip(), output);

        solutionOutput.deleteOnExit();
    }

    @Test (timeout = 2000)
    public void lab2aTest() throws Exception{
        String input = "6\n" +
                "8 0 2 9 7 12\n" +
                "3 2 3 7 4 2";
        String output = "8 8 8 0 0 2 2 2 9 9 9 9 9 9 9 7 7 7 7 12 12";

        File solutionOutput = File.createTempFile("temp", "merl");

        Entry.scanner = new Scanner(input);
        Entry.writer = new PrintStream(solutionOutput);

        Entry.interpret("test files/lab2.merl");

        Scanner outputScanner = new Scanner(solutionOutput);
        assertEquals(outputScanner.nextLine().strip(), output);

        solutionOutput.deleteOnExit();
    }

    @Test (timeout = 2000)
    public void lab2bTest() throws Exception{
        String input = "12\n" +
                "0 9 3 9 2 7 1 8 4 5 6 9\n" +
                "2 3 5 7 4 6 9 12 6 13 8 7";
        String output = "0 0 9 9 9 3 3 3 3 3 9 9 9 9 9 9 9 2 2 2 2 7 7 7 7 7 7 1 1 1 1 1 1 1 1 1 8 8 8 8 8 8 8 8 8 8 8 8 4 4 4 4 4 4 5 5 5 5 5 5 5 5 5 5 5 5 5 6 6 6 6 6 6 6 6 9 9 9 9 9 9 9";

        File solutionOutput = File.createTempFile("temp", "merl");

        Entry.scanner = new Scanner(input);
        Entry.writer = new PrintStream(solutionOutput);

        Entry.interpret("test files/lab2.merl");

        Scanner outputScanner = new Scanner(solutionOutput);
        assertEquals(outputScanner.nextLine().strip(), output);

        solutionOutput.deleteOnExit();
    }

    @Test (timeout = 2000)
    public void lab3aTest() throws Exception{
        String input = "Four score and seven years ago our fathers brought forth on this continent...\n" +
                "RRRRRRRRRRRRRRRRRRRRLLRRRRRRLRRLRLLRLLLLLLLLLLLLL";
        String output = "our score and seven nen yeararsrsraraey neves dna";

        File solutionOutput = File.createTempFile("temp", "merl");

        Entry.scanner = new Scanner(input);
        Entry.writer = new PrintStream(solutionOutput);

        Entry.interpret("test files/lab3.merl");

        Scanner outputScanner = new Scanner(solutionOutput);
        assertEquals(outputScanner.nextLine().strip(), output);

        solutionOutput.deleteOnExit();
    }

    @Test (timeout = 2000)
    public void lab3bTest() throws Exception{
        String input = "SCENE I. Rome. A street. Enter FLAVIUS, MARULLUS, and certain Commoners. " +
                "FLAVIUS: Hence! home, you idle creatures get you home: " +
                "Is this a holiday? what! know you not, Being mechanical, you ought not walk " +
                "Upon a labouring day without the sign " +
                "Of your profession? Speak, what trade art thou? " +
                "First Commoner: " +
                "Why, sir, a carpenter.\n" +
                "RRRRRRRRRRRRRRRRRRRLRRRRRRRLLRRRRRRLLLRRRRLLRRLRLLRLLLLRRRRRRRLLRRRRRRLLLRRRRLLRRRLLRRLRLLRLLLLRRRRRRRLLRRRRRLLRLLLLRRRRRRRLLRRRRRRLLLRRRRLLRRRLLRR";
        String output = "CENE I. Rome. A strtreet. E . Enteretnter rer r reretnEnter FLF FLAVIUIVAVIUSUIUS,SUS,S,SUSUIVAVIUS, M , MARURARAM , MARULLLULLUS, ,SUS, a , ana an";

        File solutionOutput = File.createTempFile("temp", "merl");

        Entry.scanner = new Scanner(input);
        Entry.writer = new PrintStream(solutionOutput);

        Entry.interpret("test files/lab3.merl");

        Scanner outputScanner = new Scanner(solutionOutput);
        assertEquals(outputScanner.nextLine().strip(), output);

        solutionOutput.deleteOnExit();
    }

    @Test (timeout = 2000)
    public void lab4aTest() throws Exception{
        String input = "14\n" +
                "2 3 3 3 5 6 6 6 7 7 8 8 8 8 8 9 9 10 10 11 12 13 14 14";
        String output = "NO";

        File solutionOutput = File.createTempFile("temp", "merl");

        Entry.scanner = new Scanner(input);
        Entry.writer = new PrintStream(solutionOutput);

        Entry.interpret("test files/lab4.merl");

        Scanner outputScanner = new Scanner(solutionOutput);
        assertEquals(outputScanner.nextLine().strip(), output);

        solutionOutput.deleteOnExit();
    }

    @Test (timeout = 2000)
    public void lab4bTest() throws Exception{
        String input = "20\n" +
                "3 4 4 4 5 6 6 6 7 8 8 8 9 10 10 10 11 12 12 12 13 14 14 14 15 16 16 16";
        String output = "YES";

        File solutionOutput = File.createTempFile("temp", "merl");

        Entry.scanner = new Scanner(input);
        Entry.writer = new PrintStream(solutionOutput);

        Entry.interpret("test files/lab4.merl");

        Scanner outputScanner = new Scanner(solutionOutput);
        assertEquals(outputScanner.nextLine().strip(), output);

        solutionOutput.deleteOnExit();
    }
}
