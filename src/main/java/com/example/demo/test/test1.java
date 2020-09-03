package com.example.demo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class test1 {
    public static void main(String[] args) {
        List<String> a=new ArrayList<>();
        a.add("gfgd");
        a.add("g大大");
        Vector c=new Vector();
        c.add(1,"123");
        c.add(2,a);
        c.add(3,190797);
        System.out.println(c.get(2));

    }
}
