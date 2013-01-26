package de.ferdinandhofherr.tddbase64;

public class OctetRegrouper {

    private static final int FIRST_6_BIT  = 0xFC0000;
    private static final int SECOND_6_BIT = 0x03F000;
    private static final int THIRD_6_BIT  = 0x000FC0;
    private static final int FOURTH_6_BIT = 0x00003F;

    public static byte[] regroup(byte[] inputOctets) {
        byte[] sixBitGroups = new byte[4];

        int joinedOctets = 0;
        for (byte b : inputOctets) {
            joinedOctets <<= 8;
            joinedOctets |= (b & 0xFF);
        }

        sixBitGroups[0] = (byte) (joinedOctets & FIRST_6_BIT >> 18);
        sixBitGroups[1] = (byte) (joinedOctets & SECOND_6_BIT >> 12);
        sixBitGroups[2] = (byte) (joinedOctets & THIRD_6_BIT >> 6);
        sixBitGroups[3] = (byte) (joinedOctets & FOURTH_6_BIT);

        return sixBitGroups;
    }

}
