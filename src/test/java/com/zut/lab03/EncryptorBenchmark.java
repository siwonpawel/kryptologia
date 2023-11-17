package com.zut.lab03;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.zut.lab03.key.KeyPair;
import com.zut.lab03.prime.PrimeGenerator;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 2)
public class EncryptorBenchmark
{

    static private final byte[] MESSAGE = "This is a test message!".getBytes();

    private static Encryptor e2048;
    private static Encryptor e3072;
    private static Encryptor e4096;
    private static Encryptor e7680;

    static
    {
        Random random = new SecureRandom();

        PrimeGenerator pg2048 = new PrimeGenerator(PrimeGenerator.KEY_SIZE_2048, random);
        PrimeGenerator pg3072 = new PrimeGenerator(PrimeGenerator.KEY_SIZE_3072, random);
        PrimeGenerator pg4096 = new PrimeGenerator(PrimeGenerator.KEY_SIZE_4096, random);
        PrimeGenerator pg7680 = new PrimeGenerator(PrimeGenerator.KEY_SIZE_7680, random);

        KeyPair kp2048 = new KeyPair(pg2048.generate(), pg2048.generate());
        KeyPair kp3078 = new KeyPair(pg3072.generate(), pg3072.generate());
        KeyPair kp4096 = new KeyPair(pg4096.generate(), pg4096.generate());
        KeyPair kp7680 = new KeyPair(pg7680.generate(), pg7680.generate());

        e2048 = new Encryptor(kp2048);
        e3072 = new Encryptor(kp3078);
        e4096 = new Encryptor(kp4096);
        e7680 = new Encryptor(kp7680);
    }

    public static void main(String[] args) throws RunnerException
    {
        Options opt = new OptionsBuilder()
                .include(EncryptorBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void test2048()
    {
        byte[] bytes = e2048.doFinal(MESSAGE);
        byte[] bytes1 = e2048.doFinal(MESSAGE);

        Arrays.equals(bytes1, bytes);
    }

    @Benchmark
    public void test3072()
    {
        byte[] bytes = e3072.doFinal(MESSAGE);
        byte[] bytes1 = e3072.doFinal(bytes);

        Arrays.equals(bytes1, bytes);
    }

    @Benchmark
    public void test4096()
    {
        byte[] bytes = e4096.doFinal(MESSAGE);
        byte[] bytes1 = e4096.doFinal(bytes);

        Arrays.equals(bytes1, bytes);
    }

    @Benchmark
    public void test7680()
    {
        byte[] bytes = e7680.doFinal(MESSAGE);
        byte[] bytes1 = e7680.doFinal(bytes);

        Arrays.equals(bytes1, bytes);
    }

}
