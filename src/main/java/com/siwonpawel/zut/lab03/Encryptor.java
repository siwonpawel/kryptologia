package com.siwonpawel.zut.lab03;

import java.math.BigInteger;

import com.siwonpawel.zut.lab03.key.EncryptionKeyPair;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Encryptor
{
    private final EncryptionKeyPair keyPair;

    public BigInteger doFinal(BigInteger bigInteger)
    {
        return bigInteger.modPow(keyPair.getPublicExponent(), keyPair.getModulus());
    }

}
