package com.siwonpawel.zut.lab03;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.siwonpawel.zut.lab03.key.KeyGenerator;
import com.siwonpawel.zut.lab03.key.KeyPair;
import com.siwonpawel.zut.lab03.prime.PrimeGenerator;

public class Zad_1_2_a
{

    private static final byte[] MESSAGE = "secure!".getBytes();

    public static void main(String[] args)
    {
        PrimeGenerator primeGenerator = new PrimeGenerator(PrimeGenerator.KEY_SIZE_4096, new SecureRandom());
        KeyGenerator keyGenerator = new KeyGenerator(primeGenerator);
        KeyPair keyPair = keyGenerator.generate();

        BigInteger plaintext = new BigInteger(MESSAGE);

        Encryptor encryptor = new Encryptor(keyPair);
        BigInteger s = encryptor.doFinal(plaintext);
        BigInteger m = s.modPow(keyPair.getPublicExponent(), keyPair.getModulus());

        System.out.println("signature");
        Utils.prettyPrint(m.toByteArray(), 32);
        System.out.println("message");
        Utils.prettyPrint(s.toByteArray(), 32);

        Decryptor decryptor = new Decryptor(keyPair);
        BigInteger bigInteger = decryptor.doFinal(m);
        System.out.printf("is valid = %b", s.compareTo(bigInteger) == 0);
    }

}
