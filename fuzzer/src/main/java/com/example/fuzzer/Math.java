package com.example.fuzzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Math {


    public static final Logger LOG = LoggerFactory.getLogger(Math.class);

    public static int add(int a, int b) {
        
        int c = a + b;

        LOG.info("Added {}, {} and made {}", a ,b, c);
        
        return c;
    }


    public static float divide(float a, float b) {
        
        float c = a / b;

        LOG.info("Divided {} by {} and made {}", a, b, c);
        
        return c;
    }

}
