package com.siwonpawel.zut.lab03.key;

import java.math.BigInteger;

import lombok.Getter;

@Getter
public class KeyPair implements DecryptionKeyPair, EncryptionKeyPair
{
    private final BigInteger primeP;
    private final BigInteger primeQ;
    private final BigInteger modulus;
    private final BigInteger privateExponent;
    private final BigInteger publicExponent;

    public KeyPair(BigInteger primeP, BigInteger primeQ)
    {
        this.primeP = primeP;
        this.primeQ = primeQ;

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
