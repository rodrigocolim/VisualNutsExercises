package com.visual.nuts.exercises.ex1;

public class IntegerPrinter {

    public static final int DEFAULT_LIMIT = 100;
    public static String TEXT_FOR_DIVISIBLE_BY_3 = "Visual";
    public static String TEXT_FOR_DIVISIBLE_BY_5 = "Nuts";
    public static String TEXT_FOR_DIVISIBLE_BY_3_AND_5 = TEXT_FOR_DIVISIBLE_BY_3 + " " + TEXT_FOR_DIVISIBLE_BY_5;

    public void executeDefault(){
        execute(1, DEFAULT_LIMIT);
    }

    private void execute(int start, int limit) {
        for (int integerNumber = start; integerNumber < start + limit; integerNumber++) {
            execute(integerNumber);
        }

    }

    private void execute(int integerNumber) {
        if (isDivisibleBy3(integerNumber) && isDivisibleBy5(integerNumber)) {
            System.out.println(TEXT_FOR_DIVISIBLE_BY_3_AND_5);
        }
        else if (isDivisibleBy3(integerNumber)) {
            System.out.println(TEXT_FOR_DIVISIBLE_BY_3);
        }
        else if (isDivisibleBy5(integerNumber)) {
            System.out.println(TEXT_FOR_DIVISIBLE_BY_5);
        }
        else {
            System.out.println(integerNumber);
        }
    }

    private boolean isDivisibleBy3(int number) {
        return number % 3 == 0;
    }

    private boolean isDivisibleBy5(int number) {
        return number % 5 == 0;
    }

}
