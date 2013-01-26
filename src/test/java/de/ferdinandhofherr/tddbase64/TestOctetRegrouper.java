package de.ferdinandhofherr.tddbase64;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestOctetRegrouper {

    @Test
    public void converts3octetsInto4groupsOf6Bits() {
        byte[] inputOctets = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        byte[] output6BitGroups = {(byte) 0x3F, (byte) 0x3F, (byte) 0x3F, (byte) 0x3F};

        assertThat(OctetRegrouper.regroup(inputOctets)).isEqualTo(output6BitGroups);
    }

}