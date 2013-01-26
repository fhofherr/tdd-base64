package de.ferdinandhofherr.tddbase64;

public class OctetRegrouper {

    private static final int FIRST_6_BIT  = 0xFC0000;
    private static final int SECOND_6_BIT = 0x03F000;
    private static final int THIRD_6_BIT  = 0x000FC0;
    private static final int FOURTH_6_BIT = 0x00003F;

    public static byte[] regroup(byte[] inputOctets) {
        int joinedOctets = joinOctets(inputOctets);
        byte[] sixBitGroups = extract6BitGroups(joinedOctets);

        return sixBitGroups;
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
