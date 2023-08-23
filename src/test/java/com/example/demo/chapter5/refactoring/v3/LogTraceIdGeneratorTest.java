package com.example.demo.chapter5.refactoring.v3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 5.5.6 단위 테스트 코드 작성을 위한 리팩터링
 * 5.5.7 예외 처리를 위한 리팩터링
 */
public class LogTraceIdGeneratorTest {

    LogTraceIdGeneratorV3 idGenerator = new LogTraceIdGeneratorV3();

    @Test
    void testGetLastSubStrSplittedByDot() {
        String actualSubStr = idGenerator.getLastSubStrSplittedByDot("field1.field2.field3");
        assertThat(actualSubStr).isEqualTo("field3");

        actualSubStr = idGenerator.getLastSubStrSplittedByDot("field1");
        assertThat(actualSubStr).isEqualTo("field1");

        actualSubStr = idGenerator.getLastSubStrSplittedByDot("field1#field2#field3");
        assertThat(actualSubStr).isEqualTo("field1#field2#field3");
    }

    @Test
    void testGetLastSubstrSplittedByDot_nullOrEmpty() {
        assertThatThrownBy(() -> idGenerator.getLastSubStrSplittedByDot(null))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> idGenerator.getLastSubStrSplittedByDot(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testGenerateRandomAlphanumeric() {
        String actualRandomString = idGenerator.generateRandomAlphanumeric(6);
        assertThat(actualRandomString).isNotNull();
        assertThat(actualRandomString.length()).isEqualTo(6);
        for (char c : actualRandomString.toCharArray()) {
            assertThat(
                    ('0' <= c && c <= '9') ||
                    ('a' <= c && c <= 'z') ||
                    ('A' <= c && c <= 'Z')).isTrue();
        }
    }

    @Test
    void testGenerateRandomAlphanumeric_lengthEqualsOrLessThanZero() {
        String actualRandomString = idGenerator.generateRandomAlphanumeric(0);
        assertThat(actualRandomString).isEqualTo("");

        assertThatThrownBy(() -> idGenerator.generateRandomAlphanumeric(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
