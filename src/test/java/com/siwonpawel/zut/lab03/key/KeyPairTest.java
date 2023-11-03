package com.siwonpawel.zut.lab03.key;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class KeyPairTest
{

    @Test
    void shouldGenerateCorrectPublicAndPrivateExponents()
    {
        // given
        BigInteger primeP = BigInteger.valueOf(3);
        BigInteger primeQ = BigInteger.valueOf(11);

        // when
        KeyPair keyPair = new KeyPair(primeP, primeQ);

        // then
        assertThat(keyPair)
                .returns(BigInteger.valueOf(33), KeyPair::getModulus)
                .returns(BigInteger.valueOf(7), KeyPair::getPublicExponent)
                .returns(BigInteger.valueOf(19), KeyPair::getPrivateExponent);

    }

}