package com.siwonpawel.zut.lab03;

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

        KeyPair keyPair = keyGenerator.generate();

    }

}
