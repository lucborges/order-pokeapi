package com.looqbox.backendchallenge.utils;

import com.looqbox.backendchallenge.entity.SortType;


public abstract class QuickSort {
    private static void swap(String[] arr, int i, int j)
    {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static int partition(String[] arr, int low, int high, SortType sortType)
    {
        String pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {
            Boolean sortCondition = sortType == SortType.asc
                    ?
                    arr[j].compareTo(pivot) < 0
                    : arr[j].compareTo(pivot) > 0;
            if (sortCondition) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    public static void quickSort(String[] arr, int low, int high, SortType sortType)
    {
        if (low < high) {
            int pi = partition(arr, low, high, sortType);
            quickSort(arr, low, pi - 1, sortType);
            quickSort(arr, pi + 1, high, sortType);
        }
    }
}
