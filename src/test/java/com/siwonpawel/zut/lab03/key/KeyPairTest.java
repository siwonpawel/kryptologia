package com.siwonpawel.zut.lab03.key;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class KeyPairTest
{

    @Test
    void shouldGenerateCorrectKeyValues()
    {
        // given
        var p = BigInteger.valueOf(7);
        var q = BigInteger.valueOf(11);

        // when
        KeyPair keyPair = new KeyPair(p, q);

        // then
        assertThat(keyPair)
                .returns(BigInteger.valueOf(77), KeyPair::getModulus)
                .returns(BigInteger.valueOf(7), KeyPair::getPublicExponent)
                .returns(BigInteger.valueOf(43), KeyPair::getPrivateExponent);
    }

}