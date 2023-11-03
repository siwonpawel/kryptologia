package com.siwonpawel.zut.lab03;

import java.math.BigInteger;

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
        BigInteger bigInteger = S.modPow(E, N);

        Utils.prettyPrint(bigInteger.toByteArray(), 32);
    }

}
