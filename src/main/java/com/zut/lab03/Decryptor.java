package com.zut.lab03;

import java.math.BigInteger;

import com.zut.lab03.key.DecryptionKeyPair;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Decryptor
{
    private final DecryptionKeyPair key;

    public byte[] doFinal(byte[] value)
    {
        BigInteger encrypted = new BigInteger(value);
        BigInteger decrypted = doFinal(encrypted);
        return decrypted.toByteArray();
    }

    public BigInteger doFinal(BigInteger encrypted)
    {
        return encrypted.modPow(key.getPrivateExponent(), key.getModulus());
    }

}
