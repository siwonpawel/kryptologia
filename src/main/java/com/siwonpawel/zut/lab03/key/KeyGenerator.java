package com.siwonpawel.zut.lab03.key;

import java.util.Random;

import com.siwonpawel.zut.lab03.prime.PrimeGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyGenerator
{

    private final PrimeGenerator primeGenerator;

    public KeyPair generate(Random random)
    {
        return new KeyPair(primeGenerator.generate(), primeGenerator.generate(), random);
    }
}
