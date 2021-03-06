package com.advent;

import com.advent.fourteen.Recipes;
import kotlin.ranges.IntRange;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2019-02-10.
 */
public class FourteenTests {


    @Test
    void testOne() {
        List<Integer> input = new ArrayList<>();
        input.add(3);
        input.add(7);

        Recipes r = new Recipes(input);
        new IntRange(0, 90000000).forEach(it -> {
            //System.out.println(r);
            r.tick();
        });

        assertEquals("5158916779", r.getScore(9,10));
        assertEquals("0124515891", r.getScore(5,10));
        assertEquals("9251071085", r.getScore(18,10));
        assertEquals("5941429882", r.getScore(2018,10));
        //System.out.println(r.getScore(286051,10));

        //51589 first appears after 9 recipes.
        //01245 first appears after 5 recipes.
        //92510 first appears after 18 recipes.
        //59414 first appears after 2018 recipes.
        assertEquals(9, r.appearsBefore("51589"));
        assertEquals(5, r.appearsBefore("01245"));
        assertEquals(18, r.appearsBefore("92510"));
        assertEquals(2018, r.appearsBefore("59414"));

        System.out.println(r.appearsBefore("286051"));

    }

    @Test
    void testChallengeOne(){
        Recipes r = new Recipes(Recipes.Companion.parse(286051));
        new IntRange(0, 286052).forEach(it -> {
            //System.out.println(r);
            r.tick();
        });

        System.out.println(r.getScore(286051, 10));
    }


}
