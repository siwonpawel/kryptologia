package com.zut.lab03;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import com.zut.lab03.key.KeyGenerator;
import com.zut.lab03.key.KeyPair;
import com.zut.lab03.prime.PrimeGenerator;

public class Zad_1_3
{

    private static final String M = "private message!";

    public static void main(String[] args) throws Exception
    {
        SecureRandom random = new SecureRandom();
        PrimeGenerator primeGenerator = new PrimeGenerator(PrimeGenerator.KEY_SIZE_4096, random);

        KeyGenerator keyGenerator = new KeyGenerator(primeGenerator);
        KeyPair keyPair = keyGenerator.generate();
        Encryptor encryptor = new Encryptor(keyPair);

        byte[] checksum = checksum(M);
        byte[] signature = encryptor.doFinal(checksum);

        Decryptor decryptor = new Decryptor(keyPair);
        byte[] decryptedSignature = decryptor.doFinal(signature);

        System.out.printf("valid = %b%n", Arrays.compare(decryptedSignature, checksum));
    }

    public static byte[] checksum(String value) throws Exception
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(value.getBytes(StandardCharsets.UTF_8));
    }

}
