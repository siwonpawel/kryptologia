package com.siwonpawel.zut.lab03.key;

import com.siwonpawel.zut.lab03.prime.PrimeGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyGenerator
{

    private final PrimeGenerator primeGenerator;

    public KeyPair generate()
    {
        return new KeyPair(primeGenerator.generate(), primeGenerator.generate());
    }
}
