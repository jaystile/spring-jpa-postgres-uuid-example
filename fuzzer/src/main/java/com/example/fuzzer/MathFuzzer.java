package com.example.fuzzer;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import com.gitlab.javafuzz.core.AbstractFuzzTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathFuzzer extends AbstractFuzzTarget {

    public static final Logger LOG = LoggerFactory.getLogger(MathFuzzer.class);

    @Override
    public void fuzz(byte[] bytes) {

        Float a = null;
        Float b = null;

        try {
            ByteBuffer wrapped = ByteBuffer.wrap(bytes); // big-endian by default
            a = wrapped.getFloat(); // 1
            b = wrapped.getFloat(); // 1
        } 
        catch (BufferUnderflowException e) { 
            // System.out.println("\nNot enough bytes to create two floats."); 
        }         

        try {
            float result = Math.divide(a, b);
        }
        catch (Exception e) {
            LOG.error("Failed to divide {} by {}", a, b);
        }
    }      
}
