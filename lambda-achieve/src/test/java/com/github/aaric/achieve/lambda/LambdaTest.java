package com.github.aaric.achieve.lambda;

import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LambdaTest
 *
 * @author Aaric, created on 2017-08-15T17:49.
 * @since 1.0-SNAPSHOT
 */
public class LambdaTest {

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

        // Java8 After
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

        // Java8 After
        new Thread(() ->
            System.out.println("Lambda")
        ).start();
    }
}
