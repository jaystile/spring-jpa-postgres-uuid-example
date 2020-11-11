package com.uuid_guide.server.v1;

public class Fibonacci {

    public static int fibonacciLoop(int nthNumber) {
        //use loop
        int previouspreviousNumber, previousNumber = 0, currentNumber = 1;

        for (int i = 1; i < nthNumber ; i++) {
            previouspreviousNumber = previousNumber;
            previousNumber = currentNumber;
            currentNumber = previouspreviousNumber + previousNumber;
        }

        return currentNumber;
    }
}
