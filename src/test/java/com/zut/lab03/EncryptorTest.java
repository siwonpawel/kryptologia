package com.zut.lab03;

import java.math.BigInteger;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.zut.lab03.key.KeyPair;

import static org.assertj.core.api.Assertions.*;

class EncryptorTest
{

    @ParameterizedTest
    @CsvSource(value = {
            "1,1",
            "2,8",
            "3,27",
            "4,31",
            "5,26",
            "6,18",
            "7,13",
            "8,17",
            "9,3",
            "10,10",
            "11,11",
            "12,12",
            "13,19",
            "14,5",
            "15,9",
            "16,4",
    })
    void shouldEncryptCorrectly(BigInteger msg, BigInteger expectedResult)
    {
        // given
        TestKeyProvider testKeyProvider = new TestKeyProvider(
                BigInteger.valueOf(33),
                BigInteger.valueOf(7),
                BigInteger.valueOf(3)
        );

        Encryptor encryptor = new Encryptor(testKeyProvider);

        // when
        BigInteger result = encryptor.doFinal(msg);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,1",
            "2,8",
            "3,27",
            "4,31",
            "5,26",
            "6,18",
            "7,13",
            "8,17",
            "9,3",
            "10,10",
            "11,11",
            "12,12",
            "13,19",
            "14,5",
            "15,9",
            "16,4",
    })
    void shouldEncryptCorrectlyWhenKeyPairUsed(BigInteger msg, BigInteger expectedResult)
    {
        // given
        BigInteger primeP = BigInteger.valueOf(3);
        BigInteger primeQ = BigInteger.valueOf(11);
        KeyPair keyPair = new KeyPair(primeP, primeQ);

        Encryptor encryptor = new Encryptor(keyPair);

        // when
        BigInteger result = encryptor.doFinal(msg);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

}