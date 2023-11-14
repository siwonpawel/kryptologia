package com.zut.lab03;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Zad_1_2_c
{

    private static final BigInteger S = new BigInteger("2829246759667430901779973875");
    private static final BigInteger E = BigInteger.valueOf(3);
    private static final BigInteger N = new BigInteger(
            "7486374846663627918089811394557316880016731434900733973466" +
                    "4557033677222985045895878321130196223760783214379338040678" +
                    "2339080107477732640032376205901411740283301540121395970682" +
                    "3612154294544242607436701783834990586691512046997836198600" +
                    "2240362282392181726265023378796284600697013635003150020012" +
                    "763665368297013349");

    public static void main(String[] args)
    {
        BigInteger result = Ith_Root(S, E);

        Utils.prettyPrint(result.toByteArray(), 32);

        // 544D454B
        // T M E K

        System.out.println(new String(result.toByteArray(), StandardCharsets.US_ASCII));
    }

    public static BigInteger Ith_Root(BigInteger N, BigInteger K)
    {

        BigInteger K1 = K.subtract(BigInteger.ONE);
        BigInteger S = N.add(BigInteger.ONE);
        BigInteger U = N;
        while (U.compareTo(S) == -1)
        {
            S = U;
            U = (U.multiply(K1).add(N.divide(pow(U, K1)))).divide(K);
        }
        return S;
    }

    public static BigInteger pow(BigInteger base, BigInteger exponent)
    {
        BigInteger result = BigInteger.ONE;
        while (exponent.signum() > 0)
        {
            if (exponent.testBit(0))
                result = result.multiply(base);
            base = base.multiply(base);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }

}
