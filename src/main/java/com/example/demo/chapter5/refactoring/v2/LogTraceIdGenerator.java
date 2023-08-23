package com.example.demo.chapter5.refactoring.v2;

import com.example.demo.chapter5.refactoring.IdGenerator;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;


/**
 * 5.5.4 가독성 향상을 위한 리팩터링
 */
@Slf4j
public class LogTraceIdGenerator implements IdGenerator {

    @Override
    public String generate() {
        String subStrOfHostName = getLastFieldOfHostName();
        long currentTimeMillis = System.currentTimeMillis();
        String randomString = generateRandomAlphanumeric(8);
        String id = String.format("%s-%d-%s",
                subStrOfHostName, currentTimeMillis, randomString);
        return id;
    }

    private String getLastFieldOfHostName() {
        String subStrOfHostName = null;
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String[] tokens = hostName.split("\\.");
            subStrOfHostName = tokens[tokens.length - 1];
            return subStrOfHostName;
        } catch (UnknownHostException e) {
            log.warn("Failed to get the hostname.", e);
        }
        return subStrOfHostName;
    }

    private String generateRandomAlphanumeric(int length) {
        char[] randomChars = new char[length];
        int count = 0;
        Random random = new Random();
        while (count < length) {
            int maxAscii = 'z';
            int randomAscii = random.nextInt(maxAscii);
            boolean isDigit = randomAscii >= '0' && randomAscii <= '9';
            boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
            boolean isLowercase = randomAscii >= 'a' && randomAscii <= 'z';
            if (isDigit || isUppercase || isLowercase) {
                randomChars[count] = (char) (randomAscii);
                ++ count;
            }
        }
        return new String(randomChars);
    }
}
