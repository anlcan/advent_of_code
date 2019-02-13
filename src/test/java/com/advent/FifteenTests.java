package com.advent;

import com.advent.fifteen.Combat;
import com.advent.fifteen.Point;
import com.advent.fifteen.Unit;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2019-02-13.
 */
public class FifteenTests {


    @Test
    void testInput(){
        List<String> input = Util.readStrings("/fifteen/input1.txt");
        Combat c = new Combat(input);
        c.print();
        c.game();

        c.print();
        c.game();

        c.print();
        c.game();
    }

    @Test
    void testTraj(){
        Combat c = new Combat(Collections.emptyList());
        Unit e1 = new Unit(2, 4, 'E');
        Unit e2 = new Unit(6, 3, 'E');
        Unit e3 = new Unit(6, 3, 'E');

        List<List<Point>> traj1 = c.traj(e1.getPoint(), e2.getPoint());
        List<List<Point>> traj2 = c.traj(e1.getPoint(), e3.getPoint());
        List<List<Point>> traj3 = c.traj(e2.getPoint(), e2.getPoint());

        assertEquals(3, traj1.get(0).size());
        assertEquals(3, traj1.get(0).size());
    }

}
