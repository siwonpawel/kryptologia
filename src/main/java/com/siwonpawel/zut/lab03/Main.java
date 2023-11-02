package com.siwonpawel.zut.lab03;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.siwonpawel.zut.lab03.key.KeyGenerator;
import com.siwonpawel.zut.lab03.key.KeyPair;
import com.siwonpawel.zut.lab03.prime.PrimeGenerator;

public class Main
{

    public static void main(String[] args)
    {
        SecureRandom random = new SecureRandom();
        PrimeGenerator primeGenerator = new PrimeGenerator(16, random);

        KeyGenerator keyGenerator = new KeyGenerator(primeGenerator);

        KeyPair keyPair = keyGenerator.generate(random);

        Encryptor encryptor = new Encryptor(keyPair);
        Decryptor decryptor = new Decryptor(keyPair);

        BigInteger orgValue = BigInteger.valueOf(5000);
        BigInteger encrypted = encryptor.doFinal(orgValue);
        BigInteger decrypted = decryptor.doFinal(encrypted);

        System.out.println(orgValue);
        System.out.println(encrypted);
        System.out.println(decrypted);
    }

}
