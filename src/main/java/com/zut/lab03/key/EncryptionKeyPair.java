package com.zut.lab03.key;

import java.math.BigInteger;

public interface EncryptionKeyPair
{

    BigInteger getModulus();

    BigInteger getPublicExponent();

}
