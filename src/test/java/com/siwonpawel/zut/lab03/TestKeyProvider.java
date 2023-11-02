package com.siwonpawel.zut.lab03;

import java.math.BigInteger;

import com.siwonpawel.zut.lab03.key.DecryptionKeyPair;
import com.siwonpawel.zut.lab03.key.EncryptionKeyPair;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestKeyProvider implements EncryptionKeyPair, DecryptionKeyPair
{

    private final BigInteger modulus;
    private final BigInteger privateExponent;
    private final BigInteger publicExponent;

    @Override
    public BigInteger getModulus()
    {
        return modulus;
    }

    @Override
    public BigInteger getPrivateExponent()
    {
        return privateExponent;
    }

    @Override
    public BigInteger getPublicExponent()
    {
        return publicExponent;
    }
}
