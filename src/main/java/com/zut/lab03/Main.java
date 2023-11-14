package com.zut.lab03;

import java.security.SecureRandom;
import java.util.Arrays;

import com.zut.lab03.key.KeyGenerator;
import com.zut.lab03.key.KeyPair;
import com.zut.lab03.prime.PrimeGenerator;

public class Main
{

    public static void main(String[] args)
    {
        SecureRandom random = new SecureRandom();
        PrimeGenerator primeGenerator = new PrimeGenerator(PrimeGenerator.KEY_SIZE_4096, random);

        KeyGenerator keyGenerator = new KeyGenerator(primeGenerator);

        KeyPair keyPair = keyGenerator.generate();

        Encryptor encryptor = new Encryptor(keyPair);
        Decryptor decryptor = new Decryptor(keyPair);

        System.out.println("Public key  (e) = " + keyPair.getPublicExponent());
        System.out.println("Private key (d) = " + keyPair.getPrivateExponent());

        String orgValue = "Cześć!";
        byte[] orgValueBytes = orgValue.getBytes();
        var encrypted = encryptor.doFinal(orgValueBytes);
        var decrypted = decryptor.doFinal(encrypted);

        System.out.printf("msg = %s%n", orgValue);
        System.out.println("message in bytes");
        Utils.prettyPrint(orgValueBytes, 64);
        System.out.println("signature");
        Utils.prettyPrint(encrypted, 64);
        System.out.printf("message is valid: %b", Arrays.equals(orgValueBytes, decrypted));
    }

}
