package com.siwonpawel.zut;

import java.util.Arrays;

public class BlindDecryptor
{

    private final Oracle oracle;

    public BlindDecryptor(Oracle oracle)
    {
        this.oracle = oracle;
    }

    public byte[] decrypt(byte[] encryptedText)
    {
        if(encryptedText.length <= 16) {
            System.out.println("2 or more blocks required");
            return new byte[]{};
        }

        int blockSize = findBlockSize(encryptedText);
        byte[][] split = split(encryptedText, blockSize);

        for (int i = split.length - 1; i >= 1; i--)
        {
            byte[] ultimateBlock = split[i];
            byte[] penultimateBlock = split[i - 1];

            byte[] decrytedBlock = new byte[blockSize];
            byte[] penultimatePrime = new byte[blockSize];
            byte[] penultimateOriginals = new byte[blockSize];

            for (int currIdx = blockSize - 1; currIdx >= 0; currIdx--)
            {
                penultimateOriginals[currIdx] = penultimateBlock[currIdx];

                byte padding = (byte) (blockSize - currIdx);
                if (currIdx < blockSize - 1)
                {
                    for (int corrIndex = currIdx + 1; corrIndex < blockSize; corrIndex++)
                    {
                        penultimateBlock[corrIndex] = xor(
                                penultimateOriginals[corrIndex],
                                decrytedBlock[corrIndex],
                                padding
                        );
                    }
                }

                penultimatePrime[currIdx] = findPrime(penultimateBlock, ultimateBlock, currIdx);
                decrytedBlock[currIdx] = xor(penultimateBlock[currIdx], penultimatePrime[currIdx], padding);
            }

            split[i] = decrytedBlock;
            split[i-1] = penultimateOriginals;
        }

        return cleanse(join(split), blockSize);
    }

    private byte findPrime(byte[] penultimateBlock, byte[] ultimateBlock, int idx)
    {
        byte[] joined = join(penultimateBlock, ultimateBlock);
        byte orgValue = joined[idx];

        for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++)
        {
            if (orgValue == b)
            {
                continue;
            }

            joined[idx] = b;
            DecryptionStatus decryptionStatus = oracle.tryDecrypt(joined);
            if(decryptionStatus != DecryptionStatus.OK) {
                continue;
            }

            return b;
        }

        return orgValue;
    }

    private byte[][] split(byte[] encryptedText, int blockSize)
    {
        int numberOfSplits = encryptedText.length / blockSize;

        byte[][] tmp = new byte[numberOfSplits][blockSize];
        for (int i = 0; i < numberOfSplits; i++)
        {
            tmp[i] = Arrays.copyOfRange(encryptedText, i * blockSize, (i + 1) * blockSize);
        }

        return tmp;
    }

    private byte[] cleanse(byte[] decryptedBlock, int blockSize)
    {
        byte last = decryptedBlock[decryptedBlock.length - 1];
        int upperIdx = decryptedBlock.length - 1;

        for (int i = decryptedBlock.length - 1; i >= decryptedBlock.length - blockSize; i--)
        {
            if (last == decryptedBlock[i])
            {
                upperIdx = i;
            }
            else
            {
                break;
            }
        }

        return Arrays.copyOfRange(decryptedBlock, blockSize, upperIdx);
    }

    private byte xor(byte... bytes)
    {
        if (bytes == null)
        {
            return '\000';
        }

        if (bytes.length < 2)
        {
            return bytes[0];
        }

        byte result = bytes[0];
        for (int i = 1; i < bytes.length; i++)
        {
            result ^= bytes[i];
        }

        return result;
    }

    private byte[] join(byte[]... values)
    {
        int size = 0;
        for (var v : values)
        {
            size += v.length;
        }

        byte[] joined = new byte[size];

        int idx = 0;
        for (int i = 0; i < values.length; i++)
        {
            for (int j = 0; j < values[i].length; j++)
            {
                joined[idx++] = values[i][j];
            }
        }

        return joined;
    }

    private int findBlockSize(byte[] encryptedText)
    {
        for (int i = 0; i < encryptedText.length; i++)
        {
            DecryptionStatus decryptionStatus = oracle.tryDecrypt(Arrays.copyOf(encryptedText, i));
            if (decryptionStatus != DecryptionStatus.WRONG_BLOCK_SIZE)
            {
                return i;
            }
        }

        throw new IllegalArgumentException("cannot determine block size");
    }

}
