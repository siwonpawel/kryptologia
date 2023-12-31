package com.zut.lab04;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.probablePrime;
import static java.math.BigInteger.valueOf;

public class Zad4_2
{

    public static void main(String[] args)
    {
        SecureRandom random = new SecureRandom();

        BigInteger secret;
        BigInteger n;
        BigInteger m;
        try (Scanner s = new Scanner(System.in))
        {
            System.out.print("What secret you want to encode? ");
            secret = read(s, null, null);
            System.out.print("How many shares needs to be created? ");
            n = read(s, valueOf(2), valueOf(20));
            System.out.print("How many shares are needed to recover secret? ");
            m = read(s, valueOf(2), n);
        }

        var a = new BigInteger[m.subtract(ONE).intValue()];
        for (var i = ZERO; i.compareTo(m.subtract(ONE)) < 0; i = i.add(ONE))
        {
            a[i.intValue()] = probablePrime(secret.bitLength(), random);
        }

        var p = BigInteger.probablePrime(secret.bitLength() + 1, random); // liczba pierwsza wieksza od secretu i n

        System.out.println("M = " + secret);
        System.out.println("n = " + n);
        System.out.println("m = " + m);

        for (var i = ZERO; i.compareTo(valueOf(a.length)) < 0; i = i.add(ONE))
        {
            System.out.printf("a%-2s = %s%n", valueOf(a.length).subtract(i), a[i.intValue()]);
        }
        System.out.println("p = " + p);

        var xs = new BigInteger[n.intValue() + 1];
        var ms = new BigInteger[n.intValue() + 1];
        xs[0] = ZERO;
        ms[0] = secret;
        for (var i = ONE; n.compareTo(i) >= 0; i = i.add(ONE))
        {
            xs[i.intValue()] = i;
            ms[i.intValue()] = wx(m, a, i, p, secret);

            System.out.printf("x%-2s = %-2s\t m%-2s = %s%n", i, xs[i.intValue()], i, ms[i.intValue()]);
        }

        BigInteger[] participants = getParticipants(n, m.intValue(), random);
        var decodedSecret = decode(participants, ms, xs, p);

        System.out.println("Decoded secret: " + decodedSecret);
        System.out.printf("is valid %b%n", secret.equals(decodedSecret));
    }

    private static BigInteger[] getParticipants(BigInteger shares, int numberOfShares, Random random)
    {
        List<Integer> possibleShareNumbers = IntStream.rangeClosed(1, shares.intValue()).boxed().collect(Collectors.toList());
        Collections.shuffle(possibleShareNumbers, random);
        List<BigInteger> selectedParticipants = possibleShareNumbers.stream()
                .limit(numberOfShares)
                .map(BigInteger::valueOf)
                .toList();
        BigInteger[] participants = selectedParticipants.toArray(new BigInteger[0]);
        System.out.println("Selected participants:");
        for (var participant : participants)
        {
            System.out.println("x" + participant);
        }
        return participants;
    }

    private static BigInteger decode(BigInteger[] participants, BigInteger[] ms, BigInteger[] xs, BigInteger p)
    {
        var decodedSecret = ZERO;
        for (var i : participants)
        {
            var tmpValue = ms[i.intValue()];
            for (var j : participants)
            {
                if (i.compareTo(j) != 0)
                {
                    BigInteger mod = xs[j.intValue()].multiply(valueOf(-1));
                    BigInteger mod1 = xs[i.intValue()].add(xs[j.intValue()].multiply(valueOf(-1))).modInverse(p);

                    BigInteger mod2 = mod.multiply(mod1);
                    tmpValue = tmpValue.multiply(mod2);
                }
            }

            decodedSecret = decodedSecret.add(tmpValue);
        }

        decodedSecret = decodedSecret.mod(p);
        return decodedSecret;
    }

    public static BigInteger wx(BigInteger m, BigInteger[] a, BigInteger x, BigInteger p, BigInteger secret)
    {
        if (m.subtract(ONE).compareTo(valueOf(a.length)) != 0)
        {
            throw new IllegalArgumentException("not enough a");
        }

        var sum = BigInteger.ZERO;

        for (int i = 0; i < a.length; i++)
        {
            BigInteger partial = x.pow(a.length - i)
                    .multiply(a[i]);

            sum = sum.add(partial);
        }

        return sum.add(secret).mod(p);
    }

    private static BigInteger read(Scanner s, BigInteger lowerThreshold, BigInteger upperThreshold)
    {
        String downThreshold = Optional.ofNullable(lowerThreshold)
                .map(BigInteger::toString)
                .orElse("1");
        String upThreshold = Optional.ofNullable(upperThreshold)
                .map(BigInteger::toString)
                .orElse("inf+");

        while (true)
        {
            System.out.print("Give a number between " + downThreshold + " and " + upThreshold + " : ");
            s.reset();
            if (!s.hasNextBigInteger())
            {
                System.out.println("Given value is not a number, try again...");
                continue;
            }

            BigInteger val = s.nextBigInteger();

            if (val.compareTo(Objects.requireNonNullElse(lowerThreshold, valueOf(1))) < 0 || (upperThreshold != null && val.compareTo(upperThreshold) > 0))
            {
                System.out.println("Given number needs to be between <" + lowerThreshold + ", " + upperThreshold + ">");
                continue;
            }

            return val;
        }
    }
}