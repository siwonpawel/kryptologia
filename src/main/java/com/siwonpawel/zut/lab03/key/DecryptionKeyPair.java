package com.siwonpawel.zut.lab03.key;

import java.math.BigInteger;

public interface DecryptionKeyPair
{

    BigInteger getModulus();

    BigInteger getPrivateExponent();

}
