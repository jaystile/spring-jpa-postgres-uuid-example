package com.example.fuzzer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.gitlab.javafuzz.core.AbstractFuzzTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebFuzzer extends AbstractFuzzTarget {

    public static final Logger LOG = LoggerFactory.getLogger(WebFuzzer.class);

    @Override
    public void fuzz(byte[] bytes) {

        String s = null;
        HttpURLConnection con = null;
        BufferedReader in = null;
        try {
            s = new String(bytes, StandardCharsets.UTF_8);
            String request = "http://server:8080/group/v1/?displayName=" + s;
            LOG.info("Sending request : [{}]", request);
            URL url = new URL(request);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();

            Reader streamReader = null;
            if (status > 299) {
                streamReader = new InputStreamReader(con.getErrorStream());
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
            }
            in = new BufferedReader(streamReader);
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            LOG.info("Response output: {} ", content);

        } catch (Exception e) {
            LOG.error("Failed on input: {}", s, e.getMessage());
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                // noop
            }
            try {
                con.disconnect();
            } catch (Exception e) {
                // noop
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // noop
            }
        }
    }      
}