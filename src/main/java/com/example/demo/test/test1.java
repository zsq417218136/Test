package com.example.demo.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class test1 {
    public static void main(String[] args) {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            String ip2=ip.getHostAddress();
            System.out.println("第二种方式"+ip2);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
