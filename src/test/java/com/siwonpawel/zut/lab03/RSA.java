package com.siwonpawel.zut.lab03;

import java.math.BigDecimal;
import java.math.BigInteger;

class RSA
{
    public static void main(String args[])
    {
        int p, q, n, z, privateExponent = 0, publicExponent, i;

        // The number to be encrypted and decrypted
        int msg = 12;
        double c;
        BigInteger msgback;

        // 1st prime number p
        p = 3;

        // 2nd prime number q
        q = 11;
        n = p * q;
        z = (p - 1) * (q - 1);
        System.out.println("the value of z = " + z);

        for (publicExponent = 2; publicExponent < z; publicExponent++)
        {

            // publicExponent is for public key exponent
            if (gcd(publicExponent, z) == 1)
            {
                break;
            }
        }
        System.out.println("the value of publicExponent = " + publicExponent);
        for (i = 0; i <= 9; i++)
        {
            int x = 1 + (i * z);

            // privateExponent is for private key exponent
            if (x % publicExponent == 0)
            {
                privateExponent = x / publicExponent;
                break;
            }
        }
        System.out.println("the value of privateExponent = " + privateExponent);
        c = (Math.pow(msg, publicExponent)) % n;
        System.out.println("Encrypted message is : " + c);

        // converting int value of n to BigInteger
        BigInteger N = BigInteger.valueOf(n);

        // converting float value of c to BigInteger
        BigInteger C = BigDecimal.valueOf(c).toBigInteger();
        msgback = (C.pow(privateExponent)).mod(N);
        System.out.println("Decrypted message is : "
                + msgback);
    }

    static int gcd(int e, int z)
    {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }
}