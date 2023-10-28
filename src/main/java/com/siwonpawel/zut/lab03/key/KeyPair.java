package com.siwonpawel.zut.lab03.key;

import java.math.BigInteger;

import lombok.Getter;

public class KeyPair implements DecryptionKeyPair, EncryptionKeyPair
{
    @Getter
    private final BigInteger modulus;
    @Getter
    private final BigInteger privateExponent; //private
    @Getter
    private final BigInteger publicExponent; //public

    public KeyPair(BigInteger primeP, BigInteger primeQ)
    {
        modulus = findN(primeP, primeQ);

        BigInteger phiN = primeP.min(BigInteger.ONE).multiply(primeQ.min(BigInteger.ONE));
        publicExponent = findPublicExponent(phiN);
        privateExponent = findPrivateExponent(publicExponent, phiN);
    }

    private BigInteger findPublicExponent(BigInteger phiN)
    {
        BigInteger e = BigInteger.TWO;
        while (e.compareTo(phiN) < 0 && phiN.gcd(e).compareTo(BigInteger.ONE) >= 0)
        {
            e = e.add(BigInteger.ONE);
        }
        return e;
    }

    private BigInteger findPrivateExponent(BigInteger e, BigInteger phiN)
    {
        return e.modInverse(phiN);
    }

    private static BigInteger findN(BigInteger primeP, BigInteger primeQ)
    {
        return primeP.multiply(primeQ);
    }
}
