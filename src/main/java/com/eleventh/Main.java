package com.eleventh;

import com.eleventh.list.StringList;
import com.eleventh.list.StringListImpl;

public class Main {
    public static void main(String[] args) {
        try {
            StringList list = new StringListImpl(4);
            list.add("00");
            list.add("11");
            list.add("22");
            list.add("33");

            list.add(1, "77");
            list.add(1, "88");

            System.out.println(list.size() + " -> " + list);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}