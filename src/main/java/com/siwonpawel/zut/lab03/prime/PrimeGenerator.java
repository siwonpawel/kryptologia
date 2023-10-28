package com.siwonpawel.zut.lab03.prime;

import java.math.BigInteger;
import java.util.Random;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrimeGenerator
{

    private final MillerRabinPrimalityTester primeTester;

    public BigInteger generate()
    {
        Random random = new Random(System.currentTimeMillis());

        BigInteger bigInteger = BigInteger.probablePrime(512, random);
        while (primeTester.test(bigInteger))
        {
            bigInteger = bigInteger.add(BigInteger.ONE);
        }

        return bigInteger;
    }
}
