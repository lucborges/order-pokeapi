package com.looqbox.backendchallenge.utils;

import com.looqbox.backendchallenge.entity.SortType;


public abstract class QuickSort {
    private static void swap(String[] arr, int i, int j)
    {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /*
    * Este é o algoritmo de ordenação por ordem alfabética.
    * Foi escolhido o QuickSort como algoritmo de ordenação, pensando que no caso
    * teremos uma quantidade pequena no retorno desta aplicação e pela sua facilidade de
    * implementação.
    *
    * A notação Big-Ω deste é algoritmo é de (N log (N))
    *
    * O coração desse algoritmo é o momento em que utilizamos o compareTo para comparar
    * o arr e o pivot.
    * O método compareTo compara lexicamente duas strings de acordo com a tabela ASCII, retornando
    * os valores negativos e positivos (ex: -1 e + 1) caso retorne -1 aquele valor antecede
    * com o que foi comparado e caso retorne um +1 positivo aquele valor sucede, por exemplo.
    *
    * Com essa comparação podemos realizar até uma comparação e definir por qual tipo de ordenação
    * o usuário deseja realizar desc ou asc.
    * */
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
