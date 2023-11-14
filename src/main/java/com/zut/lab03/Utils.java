package com.zut.lab03;

public class Utils
{

    public static void prettyPrint(byte[] values, int split)
    {
        for (int i = 0; i < values.length; i++)
        {
            if (i % split == 0 && i != 0)
            {
                System.out.println();
            }
            System.out.printf("%02X", values[i]);
        }
        System.out.println();
    }

}
