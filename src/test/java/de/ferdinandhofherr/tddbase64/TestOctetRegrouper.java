package de.ferdinandhofherr.tddbase64;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class TestOctetRegrouper {

    @Test
    public void converts3octetsInto4groupsOf6Bits() {
        byte[] inputOctets = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        byte[] output6BitGroups = {(byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F};

        assertThat(OctetRegrouper.regroup(inputOctets)).isEqualTo(output6BitGroups);
    }

    @Test
    public void convertsMultiplesOf3OctetsIntoMultiplesOf4groupsOf6Bits() throws Exception {
        byte[] inputOctets = new byte[3 * 2];
        byte[] output6BitGroups = new byte[4 * 2];

        Arrays.fill(inputOctets, (byte) 0xFF);
        Arrays.fill(output6BitGroups, (byte) 0x3F);

        assertThat(OctetRegrouper.regroup(inputOctets)).isEqualTo(output6BitGroups);
    }

    @Test
    public void conversionFillsRemainderOfOneOctetWithTwoGroupsOf0x40() throws Exception {
        byte[] inputOctets = { (byte) 0xFF };
        byte[] output6BitGroups = {(byte) 0x3F, (byte) 0x03, (byte) 0x40, (byte) 0x40};

        assertThat(OctetRegrouper.regroup(inputOctets)).isEqualTo(output6BitGroups);
    }
}