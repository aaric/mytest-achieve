package com.github.aaric.achieve.lambda;

import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * LambdaTest
 *
 * @author Aaric, created on 2017-08-15T17:49.
 * @since 1.0-SNAPSHOT
 */
public class LambdaTest {

    @Test
    public void test13() {
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 10);
        map.put("B", 20);
        map.put("C", 30);
        map.put("D", 40);
        map.put("E", 50);
        BiConsumer<String, Integer> consumer = (key, value) -> System.out.println(String.format("%s-%d", key, value));
        map.forEach(consumer);
    }

    @Test
    public void test12() {
        String[] list = {"A", "D", "B", "C"};
        Arrays.sort(list, (x, y) -> x.compareTo(y));
        Arrays.asList(list).forEach(str -> System.out.println(str));
    }

    @Test
    public void test11() {
        List<Integer> list = Arrays.asList(1, 5, 2, 3, 1, 4);
        IntSummaryStatistics statistics = list.stream().mapToInt(i -> i).summaryStatistics();
        System.out.println(statistics.getMax());
        System.out.println(statistics.getMin());
        System.out.println(statistics.getSum());
        System.out.println(statistics.getAverage());
    }

    @Test
    public void test10() {
        List<Integer> list1 = Arrays.asList(1, 5, 2, 3, 1, 4);
        System.out.println(list1);
        List<Integer> list2 = list1.stream().map(i -> i*i).distinct().collect(Collectors.toList());
        System.out.println(list2);
    }

    @Test
    public void test9() {
        List<String> list = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        System.out.println(list.stream().map(str -> str.toUpperCase()).collect(Collectors.joining(",")));
    }

    @Test
    public void test8() {
        List<String> list1 = Arrays.asList("A", "AA", "AAA", "AAAA", "AAAA");
        System.out.println(list1);
        List<String> list2 = list1.stream().filter(str -> 2 < str.length()).collect(Collectors.toList());
        System.out.println(list2);
    }

    @Test
    public void test7() {
        // Java8 Before
        List<Integer> list = Arrays.asList(10, 20, 30, 40, 50);
        double bill = 0;
        for (Integer cost: list) {
            System.out.println(cost + .12*cost);
            bill += cost + .12*cost;
        }
        System.out.println(bill);

        // Lambda
        list.stream().map(cost -> cost + .12*cost).forEach(cost ->{
            System.out.println(cost);
        });
        bill = list.stream().map(cost -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println(bill);
    }

    @Test
    public void test6() {
        // Lambda
        List<String> list = Arrays.asList("C", "Cpp", "Java", "Python");

        Predicate<String> startWithJ = str -> str.startsWith("J");
        Predicate<String> lengthEqual4 = str -> 4 == str.length();

        list.stream()
                .filter(startWithJ.and(lengthEqual4))
                .forEach(str -> {
                    System.out.println(str);
        });
    }

    private void filter(List<String> list, Predicate<String> condition) {
        list.forEach(str -> {
            if(condition.test(str)) {
                System.out.println(str);
            }
        });
    }

    @Test
    public void test5() {
        // Lambda
        List<String> list = Arrays.asList("C", "Cpp", "Java", "Python");

        filter(list, str -> str.startsWith("J"));
        filter(list, str -> 4 < str.length());
    }

    @Test
    public void test4() {
        List<String> list = Arrays.asList("A", "B", "C", "Hello");

        // Java8 Before
        for(String str: list) {
            System.out.println(str);
        }

        // Lambda
        list.forEach(string -> System.out.println(string));
        list.forEach(System.out::println);
    }

    @Test
    @Ignore
    public void test3() {
        // Java8 Before
        JButton button = new JButton("Show");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Java8 Before");
            }
        });

        // Lambda
        button.addActionListener((e) -> {
            System.out.println("Lambda");
        });
    }

    @Test
    public void test2() {
        //() -> System.out.println("Hello World!");
        //(int x, int y) -> x + y;
    }

    @Test
    public void test1() {
        // Java8 Before
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Java8 Before");
            }
        }).start();

        // Lambda
        new Thread(() ->
            System.out.println("Lambda")
        ).start();
    }
}
