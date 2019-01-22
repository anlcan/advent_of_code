package com.advent.four;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created on 2019-01-22.
 */
public class GuardWatcher {

    private static final DateTimeFormatter isoDateParser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Pattern PATTERN_GUARD = Pattern.compile("\\[.+\\] Guard #([0-9]+) begins shift");
    private static final Pattern PATTERN_MINUTE = Pattern.compile("\\[[0-9]+-[0-9]+-[0-9]+ [0-9]+:(.+)\\] .*");
    private static final Pattern PATTERN_DATE = Pattern.compile("\\[(.+)\\] .*");


    public static Integer findId(final String log) {
        Matcher matcher = PATTERN_GUARD.matcher(log);
        matcher.find();
        return Integer.valueOf(matcher.group(1));
    }

    public static Integer findMinute(final String log) {
        Matcher matcher = PATTERN_MINUTE.matcher(log);
        matcher.find();
        return Integer.parseInt(matcher.group(1));
    }

    public static LocalDateTime findDate(final String log) {
        Matcher matcher = PATTERN_DATE.matcher(log);
        matcher.find();
        return LocalDateTime.parse(matcher.group(1), isoDateParser);

    }

    public static List<String> sortLogs(List<String> logs) {
        Map<LocalDateTime, String> mapped = logs.stream().collect(Collectors.toMap(GuardWatcher::findDate, c -> c));
        List<String> sorted = new ArrayList<>();
        SortedSet<LocalDateTime> keys = new TreeSet<>(mapped.keySet());
        for (LocalDateTime ldt : keys) {
            sorted.add(mapped.get(ldt));
        }
        return sorted;
    }

    public static int strategyOne(List<String> logsRaw) {

        List<String> logs = sortLogs(logsRaw);

        int start = 0;
        GuardLog currentGuard = null;
        List<GuardLog> guardLogs = new ArrayList<>();

        for (String log : logs) {

            if (log.contains("Guard")) {
                if (currentGuard != null && !guardLogs.contains(currentGuard)) {
                    guardLogs.add(currentGuard);
                }
                Integer id = findId(log);

                currentGuard = guardLogs.stream()
                        .filter(guardLog -> guardLog.getId().equals(id))
                        .findAny()
                        .orElse(new GuardLog(id));


                start = 0;

            } else if (log.contains("wakes")) {
                int finish = findMinute(log);
                currentGuard.addSleep(finish, start);
                start = 0;
            } else if (log.contains("falls")) {
                assert start == 0;
                start = findMinute(log);
            }

        }

        Collections.sort(guardLogs);
        return guardLogs.get(0).sleepId();
    }

    static final class GuardLog implements Comparable {
        Integer id;
        List<Integer> sleeps;
        List<IntStream> wasSleep;

        public GuardLog(Integer id) {
            this.id = id;
            sleeps = new ArrayList<>();
            wasSleep = new ArrayList<>();
        }

        public void addSleep(final int finish, final int start) {
            sleeps.add(finish - start);
            wasSleep.add(IntStream.range(start, finish));
        }

        public Integer sumSleep() {
            return sleeps.stream().mapToInt(Integer::intValue).sum();
        }

        public Integer sleepId() {
            return id * mostSleptMinute();
        }

        public Integer mostSleptMinute() {

            Map<Integer, Integer> map = new HashMap<>();
            wasSleep.forEach(intStream -> intStream.forEach(i -> map.merge(i, 1, (a, b) -> a + b)));

            return map.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .findFirst()
                    .get()
                    .getKey();


        }

        @Override
        public int compareTo(Object o) {
            return ((GuardLog) o).sumSleep().compareTo(this.sumSleep());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            GuardLog guardLog = (GuardLog) o;

            return id.equals(guardLog.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        public Integer getId() {
            return id;
        }
    }
}
