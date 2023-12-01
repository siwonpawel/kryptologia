package com.zut.lab05;

public class GeffeGenerator
{

    private final LFSR lfsr1;
    private final LFSR lfsr2;
    private final LFSR lfsr3;

    public GeffeGenerator(LFSR lfsr1, LFSR lfsr2, LFSR lfsr3)
    {
        this.lfsr1 = lfsr1;
        this.lfsr2 = lfsr2;
        this.lfsr3 = lfsr3;
    }

    public String gen(int n)
    {
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++)
        {
            sb.append(gen() ? 1 : 0);
        }

        return sb.toString();
    }

    private boolean gen()
    {
        boolean x1 = lfsr1.shift();

        boolean x2 = lfsr2.shift(x1);
        boolean x3 = lfsr3.shift(!x1);

        return x2 ^ x3;
    }
}
