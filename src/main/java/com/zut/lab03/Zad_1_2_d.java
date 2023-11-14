package com.zut.lab03;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import com.zut.lab03.key.KeyGenerator;
import com.zut.lab03.key.KeyPair;
import com.zut.lab03.prime.PrimeGenerator;

public class Zad_1_2_d
{

    private static final BigInteger M = new BigInteger("private message!".getBytes(StandardCharsets.UTF_8));
    private static final BigInteger E = BigInteger.valueOf(3);

    public static void main(String[] args)
    {
        PrimeGenerator primeGenerator = new PrimeGenerator(PrimeGenerator.KEY_SIZE_4096, new SecureRandom());
        KeyGenerator keyGenerator = new KeyGenerator(primeGenerator);

        BigInteger[] ns = new BigInteger[3];
        BigInteger[] cs = new BigInteger[3];

        int i = 0;
        while (i < 3)
        {
            KeyPair keyPair = keyGenerator.generate();
            if (keyPair.getPublicExponent().compareTo(E) == 0)
            {
                ns[i] = keyPair.getModulus();
                cs[i] = new Encryptor(keyPair).doFinal(M);
                i++;
            }
        }

        BigInteger bigN = ns[0]
                .multiply(ns[1])
                .multiply(ns[2]);

        BigInteger result = BigInteger.ZERO;
        for (i = 0; i < 3; i++)
        {
            var m = bigN.divide(ns[i]);
            var gcd = extendedGCD(ns[i], m);
            if (!gcd[2].equals(BigInteger.ONE))
            {
                throw new IllegalArgumentException("cannot process!");
            }

            result = result.add(cs[i].multiply(gcd[1]).multiply(m));
        }

        BigInteger finalResult = Ith_Root(result, BigInteger.valueOf(3));

        System.out.printf("M = %d%n", M);
        System.out.printf("R = %d%n", finalResult);
        System.out.printf("messages are equal = %b%n", M.equals(finalResult));
    }

    public static BigInteger[] extendedGCD(BigInteger a, BigInteger b)
    {
        BigInteger x = BigInteger.ZERO;
        BigInteger y = BigInteger.ONE;
        BigInteger lastX = BigInteger.ONE;
        BigInteger lastY = BigInteger.ZERO;

        while (!b.equals(BigInteger.ZERO))
        {
            BigInteger[] temp = a.divideAndRemainder(b);
            a = b;
            b = temp[1];

            BigInteger tempX = x;
            x = lastX.subtract(temp[0].multiply(x));
            lastX = tempX;

            BigInteger tempY = y;
            y = lastY.subtract(temp[0].multiply(y));
            lastY = tempY;
        }

        return new BigInteger[] { lastX, lastY, a };
    }

    public static BigInteger Ith_Root(BigInteger N, BigInteger K)
    {

        BigInteger K1 = K.subtract(BigInteger.ONE);
        BigInteger S = N.add(BigInteger.ONE);
        BigInteger U = N;
        while (U.compareTo(S) == -1)
        {
            S = U;
            U = (U.multiply(K1).add(N.divide(pow(U, K1)))).divide(K);
        }
        return S;
    }

    public static BigInteger pow(BigInteger base, BigInteger exponent)
    {
        BigInteger result = BigInteger.ONE;
        while (exponent.signum() > 0)
        {
            if (exponent.testBit(0))
                result = result.multiply(base);
            base = base.multiply(base);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }
}
