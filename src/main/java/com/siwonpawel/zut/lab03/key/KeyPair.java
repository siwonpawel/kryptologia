package com.siwonpawel.zut.lab03.key;

import java.math.BigInteger;

import lombok.Getter;

public class KeyPair implements DecryptionKeyPair, EncryptionKeyPair
{
    @Getter
    private final BigInteger modulus;
    @Getter
    private final BigInteger privateExponent;
    @Getter
    private final BigInteger publicExponent;

    public KeyPair(BigInteger primeP, BigInteger primeQ)
    {
        modulus = findN(primeP, primeQ);

        BigInteger phiN = primeP.subtract(BigInteger.ONE).multiply(primeQ.subtract(BigInteger.ONE));
        publicExponent = findPublicExponent(phiN);
        privateExponent = findPrivateExponent(publicExponent, phiN);
    }

    private BigInteger findPublicExponent(BigInteger phiN)
    {
        BigInteger e = BigInteger.TWO;
        while (e.compareTo(phiN) < 0 && phiN.gcd(e).intValue() > 1)
        {
            e = e.add(BigInteger.ONE);
        }
        return e;
    }

    private BigInteger findPrivateExponent(BigInteger e, BigInteger phiN)
    {
        BigInteger bigInteger = e.modInverse(phiN);
        if (bigInteger.compareTo(e) == 0)
        {
            e = e.add(BigInteger.ONE);
        }
        return e.modInverse(phiN);
    }

    private static BigInteger findN(BigInteger primeP, BigInteger primeQ)
    {
        return primeP.multiply(primeQ);
    }
}
