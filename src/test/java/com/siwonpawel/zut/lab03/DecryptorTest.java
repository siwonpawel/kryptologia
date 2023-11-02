package com.siwonpawel.zut.lab03;

import java.math.BigInteger;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class DecryptorTest
{

    @ParameterizedTest
    @CsvSource(value = {
            "1,1",
            "8,2",
            "27,3",
            "31,4",
            "26,5",
            "18,6",
            "13,7",
            "17,8",
            "3,9",
            "10,10",
            "11,11",
            "12,12",
            "19,13",
            "5,14",
            "9,15",
            "4,16",
    })
    void shouldEncryptCorrectly(BigInteger msg, BigInteger expectedResult)
    {
        // given
        TestKeyProvider testKeyProvider = new TestKeyProvider(
                BigInteger.valueOf(33),
                BigInteger.valueOf(7),
                BigInteger.valueOf(3)
        );

        Decryptor encryptor = new Decryptor(testKeyProvider);

        // when
        BigInteger result = encryptor.doFinal(msg);

        // then
        assertThat(result)
                .isEqualTo(expectedResult);
    }

}