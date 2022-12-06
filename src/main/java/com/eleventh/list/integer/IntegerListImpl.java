package com.eleventh.list.integer;

import com.eleventh.list.exceptions.IllegalArgumentException;
import com.eleventh.list.exceptions.IllegalListItemException;
import com.eleventh.list.exceptions.IndexOutOfListException;
import com.eleventh.list.exceptions.ItemNotFoundException;

import java.util.Arrays;
import java.util.Objects;

public class IntegerListImpl implements IntegerList {
    public static final int NOT_EXIST_INDEX = -1;
    private static final float GROW_CAPACITY_INDEX = 1.5f;

    private int size;
    private int capacity;
    private Integer[] items;

    public IntegerListImpl(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Illegal capacity");
        }
        this.capacity = initialCapacity;
        items = new Integer[initialCapacity];
    }

    @Override
    public Integer add(Integer item) {
        checkItem(item);
        if (size == capacity) {
            grow();
        }
        items[size] = item;
        size++;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfListException("Index out of elements");
        } else if (index < size) {
            shiftRight(index);
            set(index, item);
            size++;
        } else {
            add(item);
        }
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        checkIndex(index);
        checkItem(item);
        items[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
        var index = indexOf(item);
        if (index == NOT_EXIST_INDEX) {
            throw new ItemNotFoundException("Not found");
        }
        return remove(index);
    }

    @Override
    public Integer remove(int index) {
        checkIndex(index);
        var item = items[index];
        shiftLeft(index);
        size--;
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        if (item == null){
            return false;
        }
        var copy = Arrays.copyOf(items, size);
        sort(copy, 0, copy.length - 1);
        return binarySearch(copy, item);
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < size; i++) {
            if (items[i].equals(item)) {
                return i;
            }
        }
        return NOT_EXIST_INDEX;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = size - 1; i >= 0; i--) {
            if (items[i].equals(item)) {
                return i;
            }
        }
        return NOT_EXIST_INDEX;
    }

    @Override
    public Integer get(int index) {
        checkIndex(index);
        return items[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        if (this == otherList) return true;
        if (otherList == null || getClass() != otherList.getClass()) return false;
        IntegerListImpl that = (IntegerListImpl) otherList;
        return size == that.size && Arrays.equals(items, that.items);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        items = new Integer[capacity];
    }

    @Override
    public Integer[] toArray() {
        Integer[] result = new Integer[size];
        System.arraycopy(items, 0, result, 0, size);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                break;
            }
            stringBuilder.append(items[i]);
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size, capacity);
        result = 31 * result + Arrays.hashCode(items);
        return result;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfListException("Index out of elements");
        }
    }

    private void checkItem(Integer item) {
        if (item == null) {
            throw new IllegalListItemException("List can't contain null");
        }
    }

    private void shiftLeft(int index) {
        for (int i = index; i <= size - 1; i++) {
            if (i == size - 1) {
                items[i] = null;
                break;
            }
            var item = items[i + 1];
            items[i] = item;
            items[i + 1] = null;
        }
    }

    private void shiftRight(int index) {
        if (size == capacity || size + 1 == capacity) {
            grow();
        }
        for (int i = size; i >= index; i--) {
            items[i + 1] = items[i];
            items[i] = null;
        }
    }

    private void grow() {
        capacity = (int) (capacity * GROW_CAPACITY_INDEX);
        var copy = toArray();
        items = new Integer[capacity];
        System.arraycopy(copy, 0, items, 0, copy.length);
    }

    private void sort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            sort(arr, begin, partitionIndex - 1);
            sort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }

    private static void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    private boolean binarySearch(Integer[] arr, Integer element) {
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
