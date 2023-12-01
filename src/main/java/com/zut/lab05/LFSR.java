package com.zut.lab05;

import java.util.Arrays;

public class LFSR
{

    private final boolean free;
    private final boolean[] initialStatus;
    private final boolean[] taps;

    private boolean[] register;
    private boolean lastResult;

    public LFSR(String free, String initialStatus, String taps)
    {
        this.free = "1".equals(free);
        this.initialStatus = toArr(initialStatus);
        this.taps = toArr(taps);

        reset();
    }

    private static boolean[] toArr(String value)
    {
        boolean[] booleans = new boolean[value.length()];
        for (int i = 0; i < value.length(); i++)
        {
            booleans[i] = Character.getNumericValue(value.charAt(i)) == 1;
        }

        return booleans;
    }

    public boolean shift()
    {
        return shift(true);
    }

    public boolean shift(boolean clk)
    {
        if (!clk)
        {
            return lastResult;
        }

        lastResult = register[0] ^ free;
        for (int i = 0; i < taps.length; i++)
        {
            if (taps[i])
            {
                lastResult ^= register[i];
            }
        }

        System.arraycopy(register, 1, register, 0, register.length - 1);
        register[register.length - 1] = lastResult;
        return lastResult;
    }

    public void reset()
    {
        this.register = Arrays.copyOf(this.initialStatus, this.initialStatus.length);
        this.lastResult = this.free;
    }
}
