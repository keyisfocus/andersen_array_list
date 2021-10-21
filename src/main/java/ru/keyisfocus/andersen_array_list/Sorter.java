package ru.keyisfocus.andersen_array_list;

import java.util.Comparator;

public class Sorter {
    public static <E> void quickSort(E[] arr, int start, int end, Comparator<? super E> comparator) {
        int leftMarker = start;
        int rightMarker = end;
        E pivot = arr[(leftMarker + rightMarker) / 2];

        do {
            while (comparator.compare(arr[leftMarker], pivot) < 0) {
                leftMarker++;
            }
            while (comparator.compare(arr[rightMarker], pivot) > 0) {
                rightMarker--;
            }
            if (leftMarker <= rightMarker) {
                if (leftMarker < rightMarker) {
                    E tmp = arr[leftMarker];
                    arr[leftMarker] = arr[rightMarker];
                    arr[rightMarker] = tmp;
                }

                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        if (leftMarker < end) {
            quickSort(arr, leftMarker, end, comparator);
        }
        if (start < rightMarker) {
            quickSort(arr, start, rightMarker, comparator);
        }
    }
}
