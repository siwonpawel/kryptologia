package com.siwonpawel.zut.lab03.prime;

import java.math.BigInteger;
import java.util.Random;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrimeGenerator
{
    public static final int KEY_SIZE_2048 = 2048;
    public static final int KEY_SIZE_3072 = 3072;
    public static final int KEY_SIZE_4096 = 4096;
    public static final int KEY_SIZE_7680 = 7680;

    private final int keySize;
    private final Random random;

    public BigInteger generate()
    {
        return BigInteger.probablePrime(keySize / 2, random);
    }
}
