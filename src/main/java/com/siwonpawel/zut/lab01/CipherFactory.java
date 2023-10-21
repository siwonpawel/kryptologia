package com.siwonpawel.zut.lab01;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CipherFactory
{

    private static final String INITIALIZATION_VECTOR = "AAAAAAAAAAAAAAAA";
    private static final String SECRET_KEY = "BBBBBBBBBBBBBBBB";
    private static final String TRANSFORMATION = "AES/CBC/PKCS7Padding";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public Cipher encryptor() throws Exception {
        return create(Cipher.ENCRYPT_MODE);
    }

    public Cipher decryptor() throws Exception {
        return create(Cipher.DECRYPT_MODE);
    }

    private Cipher create(int encryptMode) throws Exception
    {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(INITIALIZATION_VECTOR.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        Cipher instance = Cipher.getInstance(TRANSFORMATION);
        instance.init(encryptMode, secretKeySpec, ivParameterSpec);

        return instance;
    }

}
