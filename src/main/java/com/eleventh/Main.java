package com.eleventh;

import com.eleventh.list.integer.Sorter;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var arr1 = generateIntArray(100_000);
        var arr2 = Arrays.copyOf(arr1, arr1.length);
        var arr3 = Arrays.copyOf(arr1, arr1.length);

        System.out.println("Сортировка методом пузырька");
        long start = System.currentTimeMillis();
        Sorter.sortBubble(arr1);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println("Сортировка выбором");
        start = System.currentTimeMillis();
        Sorter.sortSelection(arr2);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println("Сортировка вставками");
        start = System.currentTimeMillis();
        Sorter.sortInsertion(arr3);
        System.out.println(System.currentTimeMillis() - start);
    }

    private static Integer[] generateIntArray(int size) {
        Integer[] result = new Integer[size];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) ((Math.random() * (1000 - 0)) + 0);
        }
        return result;
    }
}