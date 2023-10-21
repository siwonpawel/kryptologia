package com.siwonpawel.zut;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import javax.crypto.Cipher;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        CipherFactory cipherFactory = new CipherFactory();

        String plaintext = readText();
        System.out.println("Podany tekst: " + plaintext);

        Cipher encryptor = cipherFactory.encryptor();
        byte[] encryptedText = encryptor.doFinal(plaintext.getBytes());
        prettyPrint("Zaszyfrowany tekst:", encryptedText);

        Instant started = Instant.now();
        BlindDecryptor blindDecryptor = getBlindDecryptor(cipherFactory);
        Duration between = Duration.between(started, Instant.now());
//        System.out.println("Execution time" + between.getSeconds() + "," + between.getNano() + " s");

        byte[] decrypt = blindDecryptor.decrypt(encryptedText);
        System.out.println("Tekst w wersji jawnej: --[16B]--" + new String(decrypt));
    }

    private static BlindDecryptor getBlindDecryptor(CipherFactory cipherFactory) throws Exception
    {
        Cipher decryptor = cipherFactory.decryptor();
        Oracle oracle = new Oracle(decryptor);
        BlindDecryptor blindDecryptor = new BlindDecryptor(oracle);
        return blindDecryptor;
    }

    private static String readText() {
        try(Scanner scanner = new Scanner(System.in)) {
            System.out.print("Prosze podac tekst do zaszyfrowania: ");
            return scanner.nextLine();
        }
    }

    private static void prettyPrint(String message, byte[] values) {
        System.out.println(message);
        for(int i = 0; i < values.length; i++) {
            if(i % 16 == 0 && i != 0) {
                System.out.println();
            }
            System.out.printf("%02X", values[i]);
        }
        System.out.println();
    }
}