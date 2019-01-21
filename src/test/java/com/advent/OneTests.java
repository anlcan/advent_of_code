package com.advent;

import com.advent.one.Frequency;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 2019-01-21.
 */
public class OneTests {

    @Test
    void frequencyTest() {
        Frequency f = new Frequency();
        Integer[] t = {1,-2, -3};
        assertEquals(-6, f.apply(Stream.of(t).collect(Collectors.toList())));
    }


    public  List<Integer> readFrequency(final String path) {


        try {
            java.net.URL url = this.getClass().getResource(path);
            String content = new String(java.nio.file.Files.readAllBytes(Paths.get(url.toURI())), "UTF8");
            return Arrays.stream(content.split("\n")).map(Integer::valueOf).collect(Collectors.toList());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void input1(){
        Frequency f = new Frequency();
        java.util.List<Integer> integers = readFrequency("/one/input_1.txt");
        int apply = f.apply(integers);
        System.out.println(apply);
    }

}
