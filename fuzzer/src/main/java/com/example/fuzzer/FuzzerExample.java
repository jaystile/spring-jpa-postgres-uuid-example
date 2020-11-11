package com.example.fuzzer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gitlab.javafuzz.core.AbstractFuzzTarget;

public class FuzzerExample extends AbstractFuzzTarget {
    public void fuzz(byte[] data) {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
        } catch (IOException e) {
            // ignore as we expect this exception
        }
    }
}
