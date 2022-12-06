package com.eleventh.list.integer;

import com.eleventh.list.exceptions.IllegalArgumentException;
import com.eleventh.list.exceptions.IllegalListItemException;
import com.eleventh.list.exceptions.IndexOutOfListException;
import com.eleventh.list.exceptions.ItemNotFoundException;

import java.util.Arrays;
import java.util.Objects;

public class IntegerListImpl implements IntegerList {
    public static final int NOT_EXIST_INDEX = -1;
    private static final int GROW_CAPACITY = 5;

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
        Sorter.sortInsertion(copy);
        return Searcher.binarySearch(copy, item);
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
        capacity = capacity + GROW_CAPACITY;
        var copy = toArray();
        items = new Integer[capacity];
        System.arraycopy(copy, 0, items, 0, copy.length);
    }
}
