package com.visual.nuts.exercises.ex1;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.visual.nuts.exercises.ex1.IntegerPrinter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IntegerPrinterTest {

    private IntegerPrinter integerPrinter;

    private ByteArrayOutputStream out;

    @Before
    public void setUp(){
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        integerPrinter = new IntegerPrinter();
    }

    @Test
    public void shouldPrintUntilDefaultLimit(){
        integerPrinter.executeDefault();

        String[] output= out.toString().split("\n");

        for (int i = 3; i <= DEFAULT_LIMIT; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                assertEquals(TEXT_FOR_DIVISIBLE_BY_3_AND_5, output[i-1]);
            }
            else if (i % 3 == 0) {
                assertEquals(TEXT_FOR_DIVISIBLE_BY_3, output[i-1]);
            }
            else if (i % 5 == 0) {
                assertEquals(TEXT_FOR_DIVISIBLE_BY_5, output[i-1]);
            }
            else {
                assertEquals(i, Integer.valueOf(output[i-1]));
            }
        }

        assertEquals(DEFAULT_LIMIT, out.toString().split("\n").length);
    }

}
