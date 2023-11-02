package com.siwonpawel.zut.lab03;

import java.math.BigInteger;

import com.siwonpawel.zut.lab03.key.DecryptionKeyPair;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Decryptor
{
    private final DecryptionKeyPair key;

    public BigInteger doFinal(BigInteger encrypted)
    {
        return encrypted.modPow(key.getPrivateExponent(), key.getModulus());
    }

}
