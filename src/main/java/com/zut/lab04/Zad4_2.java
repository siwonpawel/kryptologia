package com.zut.lab04;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
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
            secret = read(s, valueOf(Long.MAX_VALUE));
            n = read(s, null); // ilosc cieni
            m = read(s, n); // minimalna wartosc udzialow do odtworzenia sekretu
        }

        var a = new BigInteger[m.subtract(ONE).intValue()]; // randomowe a, b, c ... ax^2+bx etc
        for (var i = ZERO; i.compareTo(m.subtract(ONE)) < 0; i = i.add(ONE))
        {
            a[i.intValue()] = valueOf(random.nextInt(Integer.MAX_VALUE));
        }

        var p = BigInteger.probablePrime(secret.bitLength() + 1, random); // liczba pierwsza wieksza od secretu i n

        System.out.println("M = " + secret);
        System.out.println("n = " + n);
        System.out.println("m = " + m);
        for (var i = ZERO; i.compareTo(valueOf(a.length)) < 0; i = i.add(ONE))
        {
            System.out.println("a[" + valueOf(a.length).subtract(i) + "] = " + a[i.intValue()]);
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

            System.out.printf("x%d = %s\t m%d = %s%n", i, xs[i.intValue()], i, ms[i.intValue()]);
        }

        BigInteger[] participants = getParticipants(n, m.intValue(), random);
        var decodedSecret = decde(participants, ms, xs, p);

        System.out.println("Decoded secret: " + decodedSecret);
        System.out.printf("Valid %b%n", secret.equals(decodedSecret));
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

    private static BigInteger decde(BigInteger[] participants, BigInteger[] ms, BigInteger[] xs, BigInteger p)
    {
        var decodedSecret = ZERO;
        for (var i : participants)
        {
            var ii = i.intValue();
            var tmpValue = ms[i.intValue()];

            for (var j : participants)
            {
                var ij = j.intValue();

                if (ii != ij)
                {
                    BigInteger mod = xs[ij].multiply(valueOf(-1)).mod(p);
                    BigInteger mod1 = xs[ii].add(xs[ij].multiply(valueOf(-1))).modInverse(p);

                    BigInteger mod2 = mod.multiply(mod1).mod(p);
                    tmpValue = tmpValue.multiply(mod2);
                }
            }

            decodedSecret = decodedSecret.add(tmpValue.mod(p));
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

    private static BigInteger read(Scanner s, BigInteger lessThan)
    {
        var upperThreshold = Objects.requireNonNullElse(lessThan, valueOf(20));

        while (true)
        {
            System.out.print("Give a number between <2, " + upperThreshold + "> : ");
            s.reset();
            if (!s.hasNextBigInteger())
            {
                System.out.println("Given value is not a number, try again...");
                continue;
            }

            BigInteger val = s.nextBigInteger();

            if (val.compareTo(valueOf(2)) < 0 || val.compareTo(upperThreshold) > 0)
            {
                System.out.println("Given number needs to be between <2, " + upperThreshold + ">");
                continue;
            }

            return val;
        }
    }
}