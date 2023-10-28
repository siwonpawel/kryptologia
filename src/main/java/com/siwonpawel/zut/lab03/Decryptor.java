package com.siwonpawel.zut.lab03;

import java.math.BigInteger;

import com.siwonpawel.zut.lab03.key.DecryptionKeyPair;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Decryptor
{
    private final DecryptionKeyPair key;

    public String doFinal(byte[] val)
    {
        BigInteger encrypted = new BigInteger(1, val);

        BigInteger bigInteger = encrypted.modPow(key.getPrivateExponent(), key.getModulus());
        return new String(bigInteger.toByteArray());
    }

}
