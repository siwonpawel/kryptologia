package com.zut.lab04;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;

public class Lab04
{

    public static void main(String[] args)
    {
        var secret = valueOf(11);
        var n = valueOf(5); // ilosc cieni
        var m = valueOf(3); // minimalna wartosc udzialow do odtworzenia sekretu

        var a = new BigInteger[] { valueOf(7), valueOf(8) }; // randomowe a, b, c ... ax^2+bx etc

        var p = valueOf(13); // liczba pierwsza wieksza od secretu i n

        System.out.println("M = " + secret);
        System.out.println("n = " + n);
        System.out.println("m = " + m);
        System.out.println("a2= " + a[0]);
        System.out.println("a1= " + a[1]);
        System.out.println("p = " + p);

        var xs = new BigInteger[n.intValue() + 1];
        var ms = new BigInteger[n.intValue() + 1];
        xs[0] = ZERO;
        ms[0] = secret;
        for (var i = ONE; n.compareTo(i) >= 0; i = i.add(ONE))
        {
            xs[i.intValue()] = i;
            ms[i.intValue()] = wx(m, a, i, p, secret);
        }

        for (var i = ZERO; n.compareTo(i) >= 0; i = i.add(ONE))
        {
            System.out.printf("x%d = %s\t m%d = %s%n", i, xs[i.intValue()], i, ms[i.intValue()]);
        }

        var participants = new BigInteger[m.intValue()];
        participants[0] = valueOf(2);
        participants[1] = valueOf(3);
        participants[2] = valueOf(5);

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

        System.out.println("Decoded secret: " + decodedSecret);
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

}