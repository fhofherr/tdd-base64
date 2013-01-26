package de.ferdinandhofherr.tddbase64;

import java.util.Arrays;

public class OctetRegrouper {

    private static final int FIRST_6_BIT  = 0xFC0000;
    private static final int SECOND_6_BIT = 0x03F000;
    private static final int THIRD_6_BIT  = 0x000FC0;
    private static final int FOURTH_6_BIT = 0x00003F;

    public static byte[] regroup(byte[] inputOctets) {
        byte[] firstThreeOctets = getFirstThreeOctets(inputOctets);
        byte[] remainingOctets = getRemainingOctets(inputOctets);
        byte[] sixBitGroups = new byte[0];

        while (firstThreeOctets.length > 0) {
            int joinedOctets = joinOctets(firstThreeOctets);
            byte[] extractedGroups = extract6BitGroups(joinedOctets);

            sixBitGroups = Arrays.copyOf(sixBitGroups, sixBitGroups.length + extractedGroups.length);
            System.arraycopy(
                    extractedGroups,
                    0,
                    sixBitGroups,
                    sixBitGroups.length - extractedGroups.length,
                    extractedGroups.length);

            firstThreeOctets = getFirstThreeOctets(remainingOctets);
            remainingOctets = getRemainingOctets(remainingOctets);
        };

        return sixBitGroups;
    }

    private static byte[] getFirstThreeOctets(byte[] octets) {
        if (octets.length >= 3) {
            return Arrays.copyOfRange(octets, 0, 3);
        } else {
            return new byte[0];
        }
    }

    private static byte[] getRemainingOctets(byte[] octets) {
        if (3 < octets.length) {
            return Arrays.copyOfRange(octets, 3, octets.length);
        } else {
            return new byte[0];
        }
    }

    private static int joinOctets(byte[] octets) {
        int joinedOctets = 0;
        for (byte b : octets) {
            joinedOctets <<= 8;
            joinedOctets |= (b & 0xFF);
        }
        return joinedOctets;
    }

    private static byte[] extract6BitGroups(int joinedOctets) {
        byte[] sixBitGroups = new byte[4];
        sixBitGroups[0] = (byte) (joinedOctets & FIRST_6_BIT >> 18);
        sixBitGroups[1] = (byte) (joinedOctets & SECOND_6_BIT >> 12);
        sixBitGroups[2] = (byte) (joinedOctets & THIRD_6_BIT >> 6);
        sixBitGroups[3] = (byte) (joinedOctets & FOURTH_6_BIT);
        return sixBitGroups;
    }
}
