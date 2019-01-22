package com.advent;

import com.advent.three.Fabric;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created on 2019-01-21.
 */
public class ThreeTests {

    @Test
    void parseTest(){
        Fabric.Claim c = Fabric.parseSingle("#123 @ 3,2: 5x4");
        assertEquals("123", c.getId());
        assertEquals(3, c.getX());
        assertEquals(2, c.getY());

        assertEquals(5, c.getWidth());
        assertEquals(4, c.getHeight());
    }

    @Test
    void overlapTest(){
        String[] input = {
                "#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4" ,
                "#3 @ 5,5: 2x2"};

        Fabric f = new Fabric(Arrays.asList(input));

        assertTrue(f.claims.get(1).isOverlap(f.claims.get(0)));
        assertTrue(f.claims.get(0).isOverlap(f.claims.get(1)));
        assertFalse(f.claims.get(0).isOverlap(f.claims.get(2)));
        assertFalse(f.claims.get(1).isOverlap(f.claims.get(2)));

//        assertEquals(4,f.claims.get(1).overlap(f.claims.get(0)));
        assertEquals(4,f.overlapArea());
    }

    @Test
    void overlapIntput1(){
        List<String> claims = Util.readStrings("/three/input.txt");
        Fabric f = new Fabric(claims);
        System.out.println(f.overlapArea()); // not 238
    }
}
