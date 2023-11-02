package com.siwonpawel.zut.lab03.key;

import java.math.BigInteger;
import java.util.Random;

import lombok.Getter;

@Getter
public class KeyPair implements DecryptionKeyPair, EncryptionKeyPair
{
    private final BigInteger primeP;
    private final BigInteger primeQ;
    private final BigInteger modulus;
    private final BigInteger phiN;
    private final BigInteger privateExponent;
    private final BigInteger publicExponent;

    public KeyPair(BigInteger primeP, BigInteger primeQ, Random random)
    {
        this.primeP = primeP;
        this.primeQ = primeQ;

        modulus = computeModulus(primeP, primeQ);

        phiN = computePhi(primeP, primeQ);
        publicExponent = getCoprime(phiN, random);
        privateExponent = publicExponent.modInverse(modulus);
    }

    private static BigInteger computeModulus(BigInteger primeP, BigInteger primeQ)
    {
        return primeP.multiply(primeQ);
    }

    private static BigInteger computePhi(BigInteger primeP, BigInteger primeQ)
    {
        return primeP.subtract(BigInteger.ONE).multiply(primeQ.subtract(BigInteger.ONE));
    }

    private static BigInteger getCoprime(BigInteger phiN, Random random)
    {
        int length = phiN.bitLength() - 2;

        BigInteger e = BigInteger.probablePrime(length, random);
        while (!phiN.gcd(e).equals(BigInteger.ONE))
        {
            e = BigInteger.probablePrime(length, random);
        }

        return e;
    }
}
