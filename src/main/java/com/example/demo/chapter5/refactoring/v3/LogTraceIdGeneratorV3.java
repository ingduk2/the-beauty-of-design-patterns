package com.example.demo.chapter5.refactoring.v3;

import com.example.demo.chapter5.refactoring.IdGenerator;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;


/**
 * 5.5.5 코드 테스트 용이성 향상을 위한 리팩터링
 * 예외 리팩터링
 */
@Slf4j
public class LogTraceIdGeneratorV3 implements IdGenerator {

    @Override
    public String generate() {
        String subStrOfHostName = null;
        try {
            subStrOfHostName = getLastFieldOfHostName();
        } catch (UnknownHostException e) {
            throw new IdGenerationFailureException("host name is empty.");
        }

        long currentTimeMillis = System.currentTimeMillis();
        String randomString = generateRandomAlphanumeric(8);
        String id = String.format("%s-%d-%s",
                subStrOfHostName, currentTimeMillis, randomString);
        return id;
    }

    private String getLastFieldOfHostName() throws UnknownHostException {
        String subStrOfHostName = null;
        String hostName = InetAddress.getLocalHost().getHostName();
        if (hostName == null || hostName.isEmpty()) {
            throw new UnknownHostException();
        }
        subStrOfHostName = getLastSubStrSplittedByDot(hostName);
        return subStrOfHostName;
    }

    @VisibleForTesting
    protected String getLastSubStrSplittedByDot(String hostName) {
        if (hostName == null || hostName.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String[] tokens = hostName.split("\\.");
        String subStrOfHostName = tokens[tokens.length - 1];
        return subStrOfHostName;
    }

    @VisibleForTesting
    protected String generateRandomAlphanumeric(int length) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }

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
                ++count;
            }
        }
        return new String(randomChars);
    }
}
