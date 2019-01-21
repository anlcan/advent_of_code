package com.advent;

import com.advent.two.IdentifierManager;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created on 2019-01-21.
 */
public class TwoTests {

    @Test
    void multiplesTest() {
        /*
        abcdef contains no letters that appear exactly two or three times.
        bababc contains two a and three b, so it counts for both.
        abbcde contains two b, but no letter appears exactly three times.
        abcccd contains three c, but no letter appears exactly two times.
        aabcdd contains two a and two d, but it only counts once.
        abcdee contains two e.
        ababab contains three a and three b, but it only counts once.
         */
        IdentifierManager im = new IdentifierManager(Collections.emptyList());
        assertFalse(im.multipleOfThree("abcdef"));
        assertFalse(im.multipleOfTwo("abcdef"));

        assertTrue(im.multipleOfTwo("bababc"));
        assertTrue(im.multipleOfThree("bababc"));


        assertFalse(im.multipleOfThree("abbcde"));
        assertTrue(im.multipleOfTwo("abbcde"));

        assertTrue(im.multipleOfThree("abcccd"));
        assertFalse(im.multipleOfTwo("abcccd"));

        assertTrue(im.multipleOfThree("ababab"));
        assertFalse(im.multipleOfTwo("ababab"));
    }

    @Test
    void input1() {
        List<String> integers = Util.readStrings("/two/input_1.txt");
        int v = new IdentifierManager(integers).checksum();
        assertEquals(8892, v);
    }

    @Test
    void input2() {
        List<String> integers = Util.readStrings("/two/input_1.txt");
        String v = new IdentifierManager(integers).commonLetters();
        assertEquals("zihwtxagifpbsnwleydukjmqv", v);
    }

    @Test
    void commonLetters() {
        String[] strs = {"abcde",
                "fghij",
                "klmno",
                "pqrst",
                "fguij",
                "axcye",
                "wvxyz"};
        List<String> input = Arrays.asList(strs);

        IdentifierManager id = new IdentifierManager(input);
        assertEquals("fgij", id.commonLetters());

    }

    @Test
    void diffTest(){
        IdentifierManager id = new IdentifierManager(Collections.emptyList());
        assertTrue(id.diff("fghij", "fguij",1));
    }

    @Test
    void commonTest(){
        IdentifierManager id = new IdentifierManager(Collections.emptyList());
        assertEquals("fgij",id.common("fghij", "fguij"));
    }
}
