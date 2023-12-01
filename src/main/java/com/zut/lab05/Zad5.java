package com.zut.lab05;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.common.base.Splitter;

public class Zad5
{

    private static final int OUTPUT_LENGTH = 20_000;
    // 10_277 - 15_641

    public static void main(String[] args)
    {
        LFSR lfsr1 = new LFSR("1", "101001101110101", "010101000110101");
        LFSR lfsr2 = new LFSR("1", "01010101000", "01010000100");
        LFSR lfsr3 = new LFSR("1", "111010100101", "010101101001");

        GeffeGenerator geffeGenerator = new GeffeGenerator(lfsr1, lfsr2, lfsr3);
        String generatedValue = geffeGenerator.gen(OUTPUT_LENGTH);

        pokerTest(generatedValue);
        longRunsTest(generatedValue);
        runsTest(generatedValue);

    }

    private static void pokerTest(String value)
    {
        Iterable<String> split = Splitter.fixedLength(4).split(value);

        Map<String, Long> collect = StreamSupport.stream(split.spliterator(), false)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long sum = collect.values()
                .stream().mapToLong(l -> l)
                .map(l -> l * l)
                .sum();

        var x = (16.0 / 5000) * sum - 5000;
        if (x > 2.16)
        {
            if (x < 46.17)
            {
                System.out.printf("Poker test passed! 2.16 < %f < 46.17%n", x);
                return;
            }
        }

        System.out.printf("Poker test not passed! 2.16 < %f < 46.17%n", x);
    }

    private static void longRunsTest(String generatedValue)
    {
        int cnt = 0;
        int cntMax = 0;
        char tmp = 'X';
        for (int i = 0; i < generatedValue.length(); i++)
        {
            char c = generatedValue.charAt(i);
            if (c == tmp)
            {
                cnt++;
            }
            else
            {
                if (cntMax < cnt)
                {
                    cntMax = cnt;
                }
                tmp = c;
                cnt = 1;
            }

            if (cnt >= 26)
            {
                System.out.printf("Long run test failed! X = %d%n", cnt);
                return;
            }
        }

        System.out.printf("Long run test passed! Xmax = %d%n", cntMax);
    }

    private static void runsTest(String value)
    {
        int[] cnt = new int[6];

        char tmp = value.charAt(0);
        int cntTmp = 1;
        for (int i = 1; i < value.length(); i++)
        {
            char c = value.charAt(i);

            if (c != tmp)
            {
                if (cntTmp >= 6)
                {
                    cnt[5]++;
                }
                else
                {
                    cnt[cntTmp - 1]++;
                }

                cntTmp = 1;
                tmp = c;
            }
            else
            {
                cntTmp++;
            }
        }

        boolean testResult = between(1, cnt[0], 2315, 2685) &
                between(2, cnt[1], 1114, 1386) &
                between(3, cnt[2], 527, 723) &
                between(4, cnt[3], 240, 384) &
                between(5, cnt[4], 103, 209) &
                between(6, cnt[5], 103, 209);

        if (testResult)
        {
            System.out.println("runs test passed!");
        }
        else
        {
            System.out.println("runs test not passed!");
        }
    }

    private static boolean between(int n, int value, int lowerLimit, int upperLimit)
    {
        if (value >= lowerLimit && value <= upperLimit)
        {
            System.out.printf("\t%d passed %d < %d < %d%n", n, lowerLimit, value, upperLimit);
            return true;
        }

        System.out.printf("\t%d not passed %d < %d < %d%n", n, lowerLimit, value, upperLimit);
        return false;
    }

}

