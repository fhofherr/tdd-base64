package de.ferdinandhofherr.tddbase64;

import java.util.Arrays;

public class OctetRegrouper {

    private static final int LAST_4_BIT_OF_16 = 0x000F;
    private static final int SECOND_6_BIT_OF_16 = 0x03F0;
    private static final int FIRST_6_BIT_OF_16 = 0xFC00;
    private static final int FIRST_6_BIT_OF_24  = 0xFC0000;
    private static final int SECOND_6_BIT_OF_24 = 0x03F000;
    private static final int THIRD_6_BIT_OF_24  = 0x000FC0;
    private static final int FOURTH_6_BIT_OF_24 = 0x00003F;
    private static final int FIRST_6_BIT_OF_8 = 0xFC;
    private static final int LAST_2_BIT_OF_8  = 0x03;

    public static byte[] regroup(byte[] inputOctets) {
        byte[] firstThreeOctets = getFirstThreeOctets(inputOctets);
        byte[] remainingOctets = getRemainingOctets(inputOctets);
        byte[] sixBitGroups = new byte[0];

        while (firstThreeOctets.length > 0) {
            int joinedOctets = joinOctets(firstThreeOctets);
            byte[] extractedGroups = extract6BitGroups(joinedOctets);

            sixBitGroups = concat(sixBitGroups, extractedGroups);
            firstThreeOctets = getFirstThreeOctets(remainingOctets);
            remainingOctets = getRemainingOctets(remainingOctets);
        };
        if (remainingOctets.length > 0) {
            sixBitGroups = addPadding(remainingOctets, sixBitGroups);
        }

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
        if (3 <= octets.length) {
            return Arrays.copyOfRange(octets, 3, octets.length);
        } else {
            return octets;
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
        sixBitGroups[0] = (byte) ((joinedOctets & FIRST_6_BIT_OF_24) >> 18);
        sixBitGroups[1] = (byte) ((joinedOctets & SECOND_6_BIT_OF_24) >> 12);
        sixBitGroups[2] = (byte) ((joinedOctets & THIRD_6_BIT_OF_24) >> 6);
        sixBitGroups[3] = (byte) (joinedOctets & FOURTH_6_BIT_OF_24);
        return sixBitGroups;
    }

    private static byte[] concat(byte[] sixBitGroups, byte[] extractedGroups) {
        sixBitGroups = Arrays.copyOf(sixBitGroups, sixBitGroups.length + extractedGroups.length);
        System.arraycopy(
                extractedGroups,
                0,
                sixBitGroups,
                sixBitGroups.length - extractedGroups.length,
                extractedGroups.length);
        return sixBitGroups;
    }

    private static byte[] addPadding(byte[] remainingOctets, byte[] sixBitGroups) {
        int joinedOctets = joinOctets(remainingOctets);
        byte[] extractedGroups = new byte[0];
        switch (remainingOctets.length) {
            case 1:
                extractedGroups = new byte[] {
                        (byte) ((joinedOctets & FIRST_6_BIT_OF_8) >> 2),
                        (byte) ((joinedOctets & LAST_2_BIT_OF_8) << 4),
                        (byte) 0x40,
                        (byte) 0x40
                };
                break;
            case 2:
                extractedGroups = new byte[] {
                    (byte) ((joinedOctets & FIRST_6_BIT_OF_16) >> 10),
                    (byte) ((joinedOctets & SECOND_6_BIT_OF_16) >> 4),
                    (byte) ((joinedOctets & LAST_4_BIT_OF_16) << 2),
                    (byte) 0x40
                };
                break;
            default: throw new IllegalArgumentException("Only one or two octets may remain!");
        }
        sixBitGroups = concat(sixBitGroups, extractedGroups);
        return sixBitGroups;
    }

}
