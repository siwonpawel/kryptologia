package com.siwonpawel.zut;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class Oracle
{

    private final Cipher decryptor;

    public Oracle(Cipher decryptor)
    {
        this.decryptor = decryptor;
    }

    public DecryptionStatus tryDecrypt(byte[] values)
    {
        try
        {
            decryptor.doFinal(values);
        }
        catch (IllegalBlockSizeException e)
        {
            return DecryptionStatus.WRONG_BLOCK_SIZE;
        }
        catch (BadPaddingException e)
        {
            return DecryptionStatus.WRONG_PADDING;
        }

        return DecryptionStatus.OK;
    }

}
