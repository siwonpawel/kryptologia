package com.siwonpawel.zut.lab03.key;

import java.math.BigInteger;

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

    public KeyPair(BigInteger primeP, BigInteger primeQ)
    {
        this.primeP = primeP;
        this.primeQ = primeQ;

        modulus = computeModulus(primeP, primeQ);

        phiN = computePhi(primeP, primeQ);
        publicExponent = getCoprime(phiN);
        privateExponent = computePrivateExponent(publicExponent);
    }

    private BigInteger computePrivateExponent(BigInteger publicExponent)
    {
        return publicExponent.modInverse(phiN);
    }

    private static BigInteger computeModulus(BigInteger primeP, BigInteger primeQ)
    {
        return primeP.multiply(primeQ);
    }

    private static BigInteger computePhi(BigInteger primeP, BigInteger primeQ)
    {
        return primeP.subtract(BigInteger.ONE).multiply(primeQ.subtract(BigInteger.ONE));
    }

    private static BigInteger getCoprime(BigInteger phiN)
    {
        BigInteger e = BigInteger.valueOf(3);
        while (!phiN.gcd(e).equals(BigInteger.ONE))
        {
            if (e.compareTo(phiN) >= 0)
            {
                throw new CannotFindCoprimeException("cannot compute e [public exponent]");
            }
            e = e.add(BigInteger.ONE);
        }

        return e;
    }
}
