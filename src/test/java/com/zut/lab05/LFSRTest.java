package com.zut.lab05;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class LFSRTest
{

    @ParameterizedTest
    @CsvSource(value = {
            "1,0",
            "0,1"
    })
    void shouldReturnFreeAfterInitialization(String free, String doesNotContainString)
    {
        // given
        int shiftNo = 20;

        String initialStatus = "0000";
        String taps = "0000";

        LFSR lfsr = new LFSR(free, initialStatus, taps);

        // when
        StringBuilder sb = new StringBuilder(shiftNo);
        for (int i = 0; i < shiftNo; i++)
        {
            sb.append(lfsr.shift(false) ? 1 : 0);
        }
        String result = sb.toString();

        // then
        assertThat(result)
                .doesNotContain(doesNotContainString);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,1101,0010,10,0111010111",
            "1,11010000000,00010010101,32,11011100011001010001011111110010",
    })
    void shouldGenerateCorrectPattern(String free, String initialStatus, String taps, int shiftNo, String expectedResult)
    {
        // given
        LFSR lfsr = new LFSR(free, initialStatus, taps);

        // when
        StringBuilder sb1 = new StringBuilder(shiftNo);
        for (int i = 0; i < shiftNo; i++)
        {
            sb1.append(lfsr.shift() ? "1" : "0");
        }
        lfsr.reset();

        StringBuilder sb2 = new StringBuilder(shiftNo);
        for (int i = 0; i < shiftNo; i++)
        {
            sb2.append(lfsr.shift() ? "1" : "0");
        }

        String result1 = sb1.toString();
        String result2 = sb2.toString();

        // then
        assertThat(result1)
                .isEqualTo(result2)
                .isEqualTo(expectedResult);
    }

}