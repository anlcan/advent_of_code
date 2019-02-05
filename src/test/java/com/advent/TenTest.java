package com.advent;

import com.advent.ten.Manager;
import com.advent.ten.Point;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2019-02-04.
 */
public class TenTest {

    @Test
    void parseTest(){

        List<String> singleton = Collections.singletonList("position=< 9,-1> velocity=<-1,  2>");
        Manager manager = new Manager(singleton);
        assertEquals(1,manager.getPoints().size());
        Point p = manager.getPoints().get(0);
        assertEquals(9, p.getX());
        assertEquals(-1, p.getY());

        p.tick();
        assertEquals(8, p.getX());
        assertEquals(1, p.getY());

    }

    @Test
    void test1(){
        List<String> input = Util.readStrings("/ten/input.txt");
        Manager manager = new Manager(input);
        manager.print();

        System.out.println();
        manager.tick();
        manager.print();
        System.out.println();

        manager.tick();
        manager.print();
        System.out.println();

        manager.tick();
        manager.print();
        System.out.println();

        manager.tick();
        manager.print();
        System.out.println();

    }

    @Test
    void challengeOne() throws InterruptedException {
        List<String> input = Util.readStrings("/ten/challenge1.txt");
        Manager manager = new Manager(input);

        while(true) {
            manager.tick();
            manager.print();
            System.out.println();
            Thread.sleep(100);
        }
    }
}
