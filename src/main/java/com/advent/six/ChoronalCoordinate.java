package com.advent.six;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

/**
 * Created on 2019-01-22.
 */
public class ChoronalCoordinate {


    private final List<Point> points;
    private final String[][] map;
    private final int max_y;
    private final int max_x;

    public ChoronalCoordinate(List<String> input) {
        this.points = new ArrayList<>();

        int size = 10;

        //Arrays.fill(map, "");

        for (int i = 0, c = 'A'; i < input.size(); i++, c++) {

            String s = input.get(i);
            s = s.replaceAll(" ", "");
            String[] xy = s.split(",");
            assert xy.length == 2;
            Point e = new Point(String.valueOf((char) c), Integer.valueOf(xy[0]), Integer.valueOf(xy[1]));
            points.add(e);

        }

        max_x = points.stream().mapToInt(Point::getX).max().getAsInt()+3;
        max_y = points.stream().mapToInt(Point::getY).max().getAsInt()+3;
        map = new String[max_x][max_y];
        points.forEach(e-> map[e.x][e.y] = e.id);
    }

    public int area() {

        Set<Point> inf = new HashSet<>();
        for (int i = 0; i < max_x; i++) {
            for (int j = 0; j < max_y; j++) {
                if (map[i][j] != null ){
                    continue;
                }
                Point p = new Point(i, j);


                Map<Point, Integer> distanceMap = points.stream().collect(Collectors.toMap(point -> point, p::manhattanDistance));
                TreeMap<Point, Integer> sorted = distanceMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, TreeMap::new));

                Iterator<Integer> iterator = sorted.values().iterator();
                if (iterator.next().equals(iterator.next())) {
                    map[i][j] = ".";
                } else {
                    Point point = sorted.firstKey();
                    map[i][j] = point.id.toLowerCase();
                    point.tick();
                    if (i == 0 || i == max_x - 1 || j == 0 || j == max_y - 1) {
                        inf.add(point);
                    }
                }
            }
        }

//        for (int i = 0; i < map.length; i++) {
//            System.out.println();
//            for (int j = 0; j < map.length; j++) {
//                System.out.print(map[j][i]);
//            }
//        }

        points.removeAll(inf);
        return points.stream().mapToInt(Point::getCount).max().getAsInt() + 1/*the point itself*/;
    }

    static final class Point implements Comparable {
        String id;
        int x;
        int y;
        int count;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(String id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public void tick(){
            count++;
        }

        public int getCount() {
            return count;
        }

        public int manhattanDistance(final Point other) {
            return abs(other.x - this.x) + abs(other.y - this.y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Point point = (Point) o;

            if (x != point.x) {
                return false;
            }
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public int compareTo(Object o) {
            return ((Point) o).manhattanDistance(this);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

}
