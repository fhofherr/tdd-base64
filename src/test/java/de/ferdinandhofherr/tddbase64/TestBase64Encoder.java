package de.ferdinandhofherr.tddbase64;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestBase64Encoder {

    @Test
    public void encodeWithNoPaddingCharacters() throws Exception {
        String input = "Hello Wor"; // No trailing 'ld'! Thats right :-)
        assertThat(Base64Encoder.encode(input)).isEqualTo("SGVsbG8gV29y");
    }

    @Test
    public void encodeWithOnePaddingCharacter() throws Exception {
        String input = "Hello World";
        assertThat(Base64Encoder.encode(input)).isEqualTo("SGVsbG8gV29ybGQ=");
    }

    @Test
    public void encodeWithTwoPaddingCharacters() throws Exception {
        String input = "Hello Worl"; // No trailing 'd'! Thats right :-)
        assertThat(Base64Encoder.encode(input)).isEqualTo("SGVsbG8gV29ybA==");
    }

}
