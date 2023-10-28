package com.siwonpawel.zut.lab03;

import java.math.BigInteger;

import com.siwonpawel.zut.lab03.key.KeyGenerator;
import com.siwonpawel.zut.lab03.key.KeyPair;
import com.siwonpawel.zut.lab03.prime.MillerRabinPrimalityTester;
import com.siwonpawel.zut.lab03.prime.PrimeGenerator;

public class Main
{

    public static void main(String[] args)
    {
        MillerRabinPrimalityTester primeTester = new MillerRabinPrimalityTester();
        PrimeGenerator primeGenerator = new PrimeGenerator(primeTester);
        KeyGenerator keyGenerator = new KeyGenerator(primeGenerator);

        //        KeyPair keyPair = keyGenerator.generate();
        KeyPair keyPair = new KeyPair(BigInteger.valueOf(252097800623L), BigInteger.valueOf(22801763489L));

        Encryptor encryptor = new Encryptor(keyPair);
        Decryptor decryptor = new Decryptor(keyPair);

        String msg = "Cześć!";
        byte[] bytes = encryptor.doFinal(msg);
        System.out.println("Encrypted: " + new String(bytes));
        String s = decryptor.doFinal(bytes);

        System.out.println("Decrypted: " + s);
    }

}
