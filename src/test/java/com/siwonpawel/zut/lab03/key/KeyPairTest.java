package com.siwonpawel.zut.lab03.key;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import com.siwonpawel.zut.lab03.Decryptor;
import com.siwonpawel.zut.lab03.Encryptor;
import com.siwonpawel.zut.lab03.prime.MillerRabinPrimalityTester;
import com.siwonpawel.zut.lab03.prime.PrimeGenerator;

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

    @Test
    void shouldGenerateCorrectKeyValues2()
    {
        // given
        String plaintext = "Cześć!";

        PrimeGenerator primeGenerator = new PrimeGenerator(new MillerRabinPrimalityTester());
        var p = primeGenerator.generate();
        var q = primeGenerator.generate();

        // when
        KeyPair keyPair = new KeyPair(p, q);

        Encryptor encryptor = new Encryptor(keyPair);
        Decryptor decryptor = new Decryptor(keyPair);

        byte[] encrypted = encryptor.doFinal(plaintext);
        String decryptionResult = decryptor.doFinal(encrypted);

        assertThat(decryptionResult)
                .isEqualTo(plaintext);
        // then
        //        assertThat(keyPair)
        //                .returns(BigInteger.valueOf(77), KeyPair::getModulus)
        //                .returns(BigInteger.valueOf(7), KeyPair::getPublicExponent)
        //                .returns(BigInteger.valueOf(43), KeyPair::getPrivateExponent);
    }

}