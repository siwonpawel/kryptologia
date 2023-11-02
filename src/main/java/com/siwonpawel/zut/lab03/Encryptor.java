package com.siwonpawel.zut.lab03;

import java.math.BigInteger;

import com.siwonpawel.zut.lab03.key.EncryptionKeyPair;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Encryptor
{
    private final EncryptionKeyPair keyPair;

    public byte[] doFinal(String message)
    {
        byte[] bytes = message.getBytes();
        BigInteger plaintext = new BigInteger(bytes);

        byte[] byteArray = keyPair.getPublicExponent().toByteArray();

        BigInteger encrypted = plaintext.modPow(keyPair.getPublicExponent(), keyPair.getModulus());

        return encrypted.toByteArray();
    }

}
