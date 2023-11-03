package com.siwonpawel.zut.lab03;

import java.math.BigInteger;

import com.siwonpawel.zut.lab03.key.EncryptionKeyPair;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Encryptor
{
    private final EncryptionKeyPair keyPair;

    public byte[] doFinal(byte[] value)
    {
        BigInteger plaintext = new BigInteger(value);
        BigInteger encrypted = doFinal(plaintext);
        return encrypted.toByteArray();
    }

    public BigInteger doFinal(BigInteger bigInteger)
    {
        return bigInteger.modPow(keyPair.getPublicExponent(), keyPair.getModulus());
    }

}
