package com.hello.jpatest;

public class ValueMain {

    /**
     * equals 복습 차원
     *
     * @param args
     */
    public static void main(String[] args) {
        int n1 = 10;
        int n2 = 10;

        double d1 = 1.0;
        double d2 = 1.0;

        float f1 = 1.0f;
        float f2 = 1.0f;

        Long l1 = 1000000000000L;
        Long l2 = 1000000000000L;

        System.out.println("n1 == n2 => " + (n1 == n2));
        System.out.println("d1 == d2 => " + (d1 == d2));
        System.out.println("f1 == f2 => " + (f1 == f2));
        System.out.println("l1 == l2 => " + (l1.equals(l2))); // object equals

        Address address1 = new Address("seoul", "bundang", "zipcode");
        Address address2 = new Address("seoul", "bundang", "zipcode");

        System.out.println("address1 == address ==> " + (address1.equals(address2)));
    }
}
