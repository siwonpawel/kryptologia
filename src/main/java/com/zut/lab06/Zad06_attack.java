package com.zut.lab06;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.ECPoint;

import iaik.security.ec.common.ECParameterSpec;
import iaik.security.ec.common.ECStandardizedParameterFactory;
import iaik.security.ec.common.EllipticCurve;
import iaik.security.ec.common.SecurityStrength;

public class Zad06_attack
{

    public static void main(String[] args) throws Exception
    {
        System.out.println("\t---------- implementacja ataku ----------");

        com.zut.lab06.Encryptor encryptor = new com.zut.lab06.Encryptor();
        BigInteger n = encryptor.getN();

        // ---------- podpisywanie

        com.zut.lab06.SigningData sd1 = encryptor.sign("Kryptologia jest całkiem super");
        com.zut.lab06.SigningData sd2 = encryptor.sign("Kryptologia jest całkiem fajna");

        // ---------- atak

        BigInteger zz = sd1.computeZ(sd2, n);
        BigInteger ss = sd1.computeS(sd2, n);
        BigInteger ss1 = ss.modInverse(n);

        var k_prim = zz.multiply(ss1).mod(n);
        BigInteger dA_prim = sd1.s().multiply(k_prim).subtract(sd1.z()).multiply(sd1.r().modInverse(n)).mod(n);
        System.out.println("dA_prim => " + dA_prim);

        System.out.println("dA_prim == dA  ===> " + encryptor.compareDA(dA_prim));
    }
}

class Encryptor
{
    private final MessageDigest sha;

    private final EllipticCurve ec;
    private final BigInteger n;
    private final int size;
    private final SecureRandom random;
    private final BigInteger dA; // prywatny
    private final ECPoint QA; // publiczny

    BigInteger k; // zła implementacja powinno generowac za kazdym razem!

    public Encryptor() throws Exception
    {
        ECParameterSpec ec_params = ECStandardizedParameterFactory.getParametersByName("secp521r1");

        ec = ec_params.getCurve();

        var ec2 = ec.getIAIKCurve();
        n = ec2.getOrder();
        size = ec2.getField().getFieldSize();

        random = SecurityStrength.getSecureRandom(SecurityStrength.getSecurityStrength(size));

        dA = new BigInteger(size - 1, random);
        System.out.println("dA => " + dA);
        QA = ec.multiplyGenerator(dA); // Q_A=d_A*G

        sha = MessageDigest.getInstance("SHA-512");
        k = new BigInteger(size - 1, random);
    }

    public com.zut.lab06.SigningData sign(String message)
    {
        BigInteger z = new BigInteger(1, sha.digest(message.getBytes()));

        BigInteger r;
        BigInteger s;
        do
        {
            var kG = ec.multiplyGenerator(k);
            r = kG.getAffineX().mod(n);
            s = k.modInverse(n).multiply(z.add(r.multiply(dA))).mod(n);
        }
        while (r.equals(BigInteger.ZERO) || s.equals(BigInteger.ZERO));

        return new com.zut.lab06.SigningData(z, r, s);
    }

    public BigInteger getN()
    {
        return n;
    }

    public boolean compareDA(BigInteger dAPrim)
    {
        return dA.equals(dAPrim);
    }
}

record SigningData(BigInteger z, BigInteger r, BigInteger s)
{
    BigInteger computeZ(com.zut.lab06.SigningData sd, BigInteger n)
    {
        var tmp = z.subtract(sd.z());
        if (tmp.signum() == 1)
        {
            tmp = tmp.mod(n);
        }
        else
        {
            tmp = tmp.add(n);
        }

        return tmp;
    }

    public BigInteger computeS(com.zut.lab06.SigningData sd2, BigInteger n)
    {
        var ss = s.subtract(sd2.s());
        if (ss.signum() == 1)
        {
            ss = ss.mod(n);
        }
        else
        {
            ss = ss.add(n);
        }
        return ss;
    }
}