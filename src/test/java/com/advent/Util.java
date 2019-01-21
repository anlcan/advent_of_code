package com.advent;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2019-01-21.
 */
public class Util {


    public static List<Integer> readFrequency(final String path) {


        try {
            java.net.URL url = Util.class.getResource(path);
            String content = new String(java.nio.file.Files.readAllBytes(Paths.get(url.toURI())), "UTF8");
            return Arrays.stream(content.split("\n")).map(Integer::valueOf).collect(Collectors.toList());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readStrings(final String path) {


        try {
            java.net.URL url = Util.class.getResource(path);
            String content = new String(java.nio.file.Files.readAllBytes(Paths.get(url.toURI())), "UTF8");
            return Arrays.stream(content.split("\n")).collect(Collectors.toList());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
