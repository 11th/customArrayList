package com.eleventh.list.integer;

public class Searcher {
    //Линейный поиск
    public static boolean linearSearch(int[] arr, int element) {
        for (int i : arr) {
            if (i == element) {
                return true;
            }
        }
        return false;
    }

    //Бинарный поиск
    public static boolean binarySearch(Integer[] arr, Integer element) {
        int min = 0;
        int max = arr.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (element == arr[mid]) {
                return true;
            }

            if (element < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }
}
