package com.siwonpawel.zut.lab03.prime;

import java.math.BigInteger;
import java.util.Random;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrimeGenerator
{

    private final int keySize;
    private final Random random;

    public BigInteger generate()
    {
        return BigInteger.probablePrime(keySize / 2, random);
    }
}
