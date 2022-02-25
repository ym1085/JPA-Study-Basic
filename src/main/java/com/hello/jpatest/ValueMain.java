package com.hello.jpatest;

public class ValueMain {

    public static void main(String[] args) {
        Integer a = new Integer(10);
        Integer b = a;

        a = 20;

        /*int a = 10;
        int b = a;
        b = 20;*/

        System.out.println(a);
        System.out.println(b);
    }
}
