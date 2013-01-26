package de.ferdinandhofherr.tddbase64;

import java.io.UnsupportedEncodingException;

public class Base64Encoder {

    private static final char[] ALPHABET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();

    public static String encode(String input) throws UnsupportedEncodingException {
        byte[] octets = input.getBytes("US-ASCII");
        byte[] sixBitGroups = OctetRegrouper.regroup(octets);
        char[] encodedChars = new char[sixBitGroups.length];

        for (int i = 0; i < sixBitGroups.length; i++) {
            encodedChars[i] = ALPHABET[sixBitGroups[i] & 0xFF];
        }

        return new String(encodedChars);
    }

}
