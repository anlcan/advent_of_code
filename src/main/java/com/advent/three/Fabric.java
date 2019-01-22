package com.advent.three;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created on 2019-01-21.
 */
public class Fabric {

    static final String REGEX = "#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)";
    static final Pattern PATTERN = Pattern.compile(REGEX);

    public List<Claim> claims;

    public Fabric(List<String> claims) {
        this.claims = claims.stream().map(Fabric::parseSingle).collect(Collectors.toList());
    }

    public static Claim parseSingle(final String input) {
        Matcher matcher = PATTERN.matcher(input);
        matcher.find();
        String id = matcher.group(1);
        Integer x = Integer.valueOf(matcher.group(2));
        Integer y = Integer.valueOf(matcher.group(3));
        Integer wide = Integer.valueOf(matcher.group(4));
        Integer tall = Integer.valueOf(matcher.group(5));

        return new Claim(id, x, y, wide, tall);
    }


    /*
    How many square inches of fabric are within two or more claims?
     */
    public int overlapArea() {

        int size = 1000;

        int[][] fabric = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(fabric[i], 0);
        }

        int count = 0;
        for (Claim claim : claims) {
            for (int i = claim.getX(); i < claim.getX() + claim.getWidth(); i++) {

                for (int j = claim.getY(); j < claim.getY() + claim.getHeight(); j++) {
                    int a = fabric[i][j];
                    fabric[i][j] = a+1;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (fabric[i][j] >= 2) count++;
            }
        }


        if (size < 10) {

            for (int i = 0; i < size; i++) {
                System.out.println();
                for (int j = 0; j < size; j++) {
                    System.out.print(fabric[i][j]);
                }
            }
        }


        return count;
    }

    public int overlapCount() {
        int sum = 0;

        for (int i = 0; i < claims.size(); i++) {
            Claim claim = claims.get(i);
            for (int j = i + 1; j < claims.size(); j++) {
                Claim claim1 = claims.get(j);
                if (claim1.isOverlap(claim)) {
                    sum += claim.overlap(claim1);

                }
            }

        }
        return sum;
    }


    public static final class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }


    }

    /**
     * A claim like #123 @ 3,2: 5x4
     * means that claim ID 123 specifies a rectangle 3 inches from the left edge,
     * 2 inches from the top edge, 5 inches wide, and 4 inches tall.
     */
    public static final class Claim {
        Point bottomLeft;
        Point topRight;
        private String id;
        private int x;
        private int y;
        private int width;
        private int height;


        public Claim(String id, int x, int y, int width, int height) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

            this.bottomLeft = new Point(x, y);
            this.topRight = new Point(x + width, y + height);

        }

        public boolean isOverlap(Claim other) {

            if (this.bottomLeft.x >= other.topRight.x ||
                    this.topRight.x <= other.bottomLeft.x ||
                    this.bottomLeft.y >= other.topRight.y || // other is over this
                    this.topRight.y <= other.bottomLeft.y) { //this is over other
                return false;
            } else {
                return true;
            }
        }

        public int overlap(Claim r) {

            int tx1 = this.x;
            int ty1 = this.y;
            int rx1 = r.x;
            int ry1 = r.y;
            int tx2 = tx1;
            tx2 += this.width;
            int ty2 = ty1;
            ty2 += this.height;
            int rx2 = rx1;
            rx2 += r.width;
            int ry2 = ry1;
            ry2 += r.height;
            if (tx1 < rx1) {
                tx1 = rx1;
            }
            if (ty1 < ry1) {
                ty1 = ry1;
            }
            if (tx2 > rx2) {
                tx2 = rx2;
            }
            if (ty2 > ry2) {
                ty2 = ry2;
            }


            return (tx2 - tx1) * (ty2 - ty1);
        }

        public String getId() {
            return id;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}

