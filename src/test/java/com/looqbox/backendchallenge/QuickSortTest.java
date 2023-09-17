package com.looqbox.backendchallenge;

import com.looqbox.backendchallenge.entity.SortType;
import com.looqbox.backendchallenge.utils.QuickSort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class QuickSortTest {
    @Test
    public void testQuickSortAsc() {
        List<String> list = Arrays.asList("pidgey", "pidgeotto", "pidgeot");
        String[] arr = list.toArray(new String[0]);
        QuickSort.quickSort(arr, 0, list.size() - 1, SortType.asc);

        Assertions.assertEquals(
                Arrays.asList("pidgeot", "pidgeotto", "pidgey"), Arrays.stream(arr).toList());
    }

    @Test
    public void testQuickSortDesc() {
        List<String> list = Arrays.asList("pidgey", "pidgeotto", "pidgeot");
        String[] arr = list.toArray(new String[0]);
        QuickSort.quickSort(arr, 0, list.size() - 1, SortType.desc);

        Assertions.assertEquals(
                Arrays.asList("pidgey", "pidgeotto", "pidgeot"), Arrays.stream(arr).toList());
    }
}
