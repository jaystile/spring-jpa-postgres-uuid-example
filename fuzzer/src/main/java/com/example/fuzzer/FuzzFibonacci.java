package com.example.fuzzer;

import java.math.BigInteger;

import com.gitlab.javafuzz.core.AbstractFuzzTarget;

public class FuzzFibonacci extends AbstractFuzzTarget {

    @Override
    public void fuzz(byte[] bytes) {
        try {
            int nthNumber = Fibonacci.fibonacciLoop(new BigInteger(bytes).intValue());
        } catch (NumberFormatException e) {
            // ignore as we expect this exception
        }
    }

    
}
