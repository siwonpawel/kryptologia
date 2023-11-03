package com.siwonpawel.zut.lab03;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.siwonpawel.zut.lab03.key.KeyGenerator;
import com.siwonpawel.zut.lab03.key.KeyPair;
import com.siwonpawel.zut.lab03.prime.PrimeGenerator;

public class Zad_1_2_b
{

    private static byte[] M1 = "very".getBytes();
    private static byte[] M2 = "secure!".getBytes();

    public static void main(String[] args)
    {
        SecureRandom random = new SecureRandom();
        PrimeGenerator primeGenerator = new PrimeGenerator(PrimeGenerator.KEY_SIZE_4096, random);

        KeyGenerator keyGenerator = new KeyGenerator(primeGenerator);
        KeyPair keyPair = keyGenerator.generate();
        Encryptor encryptor = new Encryptor(keyPair);

        BigInteger m1 = new BigInteger(M1);
        BigInteger m2 = new BigInteger(M2);

        BigInteger m = m1.multiply(m2).mod(keyPair.getModulus());

        BigInteger s1 = encryptor.doFinal(m1);
        BigInteger s2 = encryptor.doFinal(m2);
        BigInteger s = s1.multiply(s2).mod(keyPair.getModulus());

        Decryptor decryptor = new Decryptor(keyPair);
        BigInteger bigInteger = decryptor.doFinal(s);

        System.out.printf("valid = %b", bigInteger.compareTo(m) == 0);
    }

}
