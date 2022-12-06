package com.eleventh.list;

import com.eleventh.list.exceptions.IllegalArgumentException;
import com.eleventh.list.exceptions.IllegalListItemException;
import com.eleventh.list.exceptions.IndexOutOfListException;
import com.eleventh.list.exceptions.ItemNotFoundException;
import com.eleventh.list.integer.IntegerList;
import com.eleventh.list.integer.IntegerListImpl;
import com.eleventh.list.string.StringListImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class IntegerListImplTest {
    private static final int INITIAL_CAPACITY = 4;
    private static final int ZERO_CAPACITY = 0;
    private static final Integer ITEM_0 = 0;
    private static final Integer ITEM_1 = 1;
    private static final Integer ITEM_2 = 2;
    private static final Integer ITEM_3 = 3;
    private static final Integer ITEM_4 = 4;
    private static final int INDEX_0 = 0;
    private static final int INDEX_1 = 1;
    private static final int INDEX_2 = 2;
    private static final int INDEX_3 = 3;
    private static final int INDEX_4 = 4;
    private static final int SIZE_0 = 0;
    private static final int SIZE_2 = 2;
    private static final int SIZE_3 = 3;
    private static final String TO_STRING = "[ 0, 1, 2 ]";
    private static final String TO_STRING_EMPTY = "[  ]";

    private IntegerList out;

    @BeforeEach
    void setUp() {
        out = new IntegerListImpl(INITIAL_CAPACITY);
        out.add(ITEM_0);
        out.add(ITEM_1);
        out.add(ITEM_2);
    }

    @Test
    void createListWithZeroCapacity() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    new StringListImpl(ZERO_CAPACITY);
                });
    }

    @Test
    void addToEndOfList() {
        Assertions.assertThat(out.add(ITEM_3)).isEqualTo(ITEM_3);
        Assertions.assertThat(out.contains(ITEM_3)).isTrue();
        Assertions.assertThat(out.indexOf(ITEM_3)).isEqualTo(INDEX_3);
    }

    @Test
    void addToEndOfListWithIndex() {
        Assertions.assertThat(out.add(INDEX_3, ITEM_3)).isEqualTo(ITEM_3);
        Assertions.assertThat(out.contains(ITEM_3)).isTrue();
        Assertions.assertThat(out.indexOf(ITEM_3)).isEqualTo(INDEX_3);
    }

    @Test
    void addNullToEndOfList() {
        Assertions.assertThatExceptionOfType(IllegalListItemException.class)
                .isThrownBy(() -> {
                    out.add(null);
                });
    }

    @Test
    void addToMiddleOfList() {
        Assertions.assertThat(out.add(INDEX_1, ITEM_3)).isEqualTo(ITEM_3);
        Assertions.assertThat(out.get(INDEX_1)).isEqualTo(ITEM_3);
    }

    @Test
    void addToOutOfList() {
        Assertions.assertThatExceptionOfType(IndexOutOfListException.class)
                .isThrownBy(() -> {
                    out.add(INDEX_4, ITEM_3);
                });
    }

    @Test
    void addWithGrow() {
        Assertions.assertThat(out.add(ITEM_3)).isEqualTo(ITEM_3);
        Assertions.assertThat(out.indexOf(ITEM_3)).isEqualTo(INDEX_3);
        Assertions.assertThat(out.add(ITEM_4)).isEqualTo(ITEM_4);
        Assertions.assertThat(out.indexOf(ITEM_4)).isEqualTo(INDEX_4);
    }

    @Test
    void setValidItem() {
        Assertions.assertThat(out.set(INDEX_0, ITEM_3)).isEqualTo(ITEM_3);
        Assertions.assertThat(out.get(INDEX_0)).isEqualTo(ITEM_3);
    }

    @Test
    void setNullItem() {
        Assertions.assertThatExceptionOfType(IllegalListItemException.class)
                .isThrownBy(() -> {
                    out.set(INDEX_0, null);
                });
    }

    @Test
    void setOutOfList() {
        Assertions.assertThatExceptionOfType(IndexOutOfListException.class)
                .isThrownBy(() -> {
                    out.set(INDEX_4, ITEM_3);
                });
    }

    @Test
    void removeLastItemByIndex() {
        Assertions.assertThat(out.remove(INDEX_2)).isEqualTo(ITEM_2);
        Assertions.assertThat(out.size()).isEqualTo(SIZE_2);
        Assertions.assertThat(out.contains(ITEM_2)).isFalse();
    }

    @Test
    void removeOutOfListItemByIndex() {
        Assertions.assertThatExceptionOfType(IndexOutOfListException.class)
                .isThrownBy(() -> {
                    out.remove(INDEX_4);
                });
    }

    @Test
    void removeMiddleItem() {
        Assertions.assertThat(out.remove(ITEM_1)).isEqualTo(ITEM_1);
        Assertions.assertThat(out.size()).isEqualTo(SIZE_2);
        Assertions.assertThat(out.contains(ITEM_1)).isFalse();
    }

    @Test
    void removeNotExistItem() {
        Assertions.assertThatExceptionOfType(ItemNotFoundException.class)
                .isThrownBy(() -> {
                    out.remove(ITEM_3);
                });
    }

    @Test
    void containsValidItem() {
        Assertions.assertThat(out.contains(ITEM_0)).isTrue();
        Assertions.assertThat(out.contains(ITEM_1)).isTrue();
        Assertions.assertThat(out.contains(ITEM_2)).isTrue();
    }

    @Test
    void containsInvalidItem() {
        Assertions.assertThat(out.contains(ITEM_3)).isFalse();
        Assertions.assertThat(out.contains(null)).isFalse();
    }

    @Test
    void indexOfValidItem() {
        Assertions.assertThat(out.indexOf(ITEM_0)).isEqualTo(INDEX_0);
        Assertions.assertThat(out.indexOf(ITEM_1)).isEqualTo(INDEX_1);
        Assertions.assertThat(out.indexOf(ITEM_2)).isEqualTo(INDEX_2);
    }

    @Test
    void indexOfInvalidItem() {
        Assertions.assertThat(out.indexOf(ITEM_3)).isEqualTo(StringListImpl.NOT_EXIST_INDEX);
        Assertions.assertThat(out.indexOf(null)).isEqualTo(StringListImpl.NOT_EXIST_INDEX);
    }

    @Test
    void lastIndexOfValidItem() {
        Assertions.assertThat(out.lastIndexOf(ITEM_0)).isEqualTo(INDEX_0);
        out.set(INDEX_2, ITEM_0);
        Assertions.assertThat(out.lastIndexOf(ITEM_0)).isEqualTo(INDEX_2);
    }

    @Test
    void lastIndexOfInvalidItem() {
        Assertions.assertThat(out.lastIndexOf(ITEM_3)).isEqualTo(StringListImpl.NOT_EXIST_INDEX);
        Assertions.assertThat(out.lastIndexOf(null)).isEqualTo(StringListImpl.NOT_EXIST_INDEX);
    }

    @Test
    void getValidItem() {
        Assertions.assertThat(out.get(INDEX_0)).isEqualTo(ITEM_0);
        Assertions.assertThat(out.get(INDEX_1)).isEqualTo(ITEM_1);
        Assertions.assertThat(out.get(INDEX_2)).isEqualTo(ITEM_2);
    }

    @Test
    void getOutOfListItem() {
        Assertions.assertThatExceptionOfType(IndexOutOfListException.class)
                .isThrownBy(() -> {
                    out.get(INDEX_3);
                });
    }

    @Test
    void equalsWithValidList() {
        IntegerList expected = new IntegerListImpl(INITIAL_CAPACITY);
        expected.add(ITEM_0);
        expected.add(ITEM_1);
        expected.add(ITEM_2);
        Assertions.assertThat(out.equals(expected)).isTrue();
    }

    @Test
    void equalsWithNull() {
        Assertions.assertThat(out.equals(null)).isFalse();
    }

    @Test
    void equalsWithArrayList() {
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(ITEM_0);
        arrayList.add(ITEM_1);
        arrayList.add(ITEM_2);
        Assertions.assertThat(out.equals(arrayList)).isFalse();
    }

    @Test
    void sizeAfterRemove() {
        Assertions.assertThat(out.size()).isEqualTo(SIZE_3);
        out.remove(INDEX_0);
        Assertions.assertThat(out.size()).isEqualTo(SIZE_2);
    }

    @Test
    void sizeAfterClear() {
        out.clear();
        Assertions.assertThat(out.size()).isEqualTo(SIZE_0);
    }

    @Test
    void isEmptyForListWithItems() {
        Assertions.assertThat(out.isEmpty()).isFalse();
    }

    @Test
    void isEmptyForEmplyList() {
        out.clear();
        Assertions.assertThat(out.isEmpty()).isTrue();
    }

    @Test
    void clear() {
        out.clear();
        Assertions.assertThat(out.isEmpty()).isTrue();
    }

    @Test
    void toArrayListWithItems() {
        Integer[] expected = new Integer[]{ITEM_0, ITEM_1, ITEM_2};
        Assertions.assertThat(out.toArray()).isEqualTo(expected);
    }

    @Test
    void toArrayEmptyList() {
        out.clear();
        Assertions.assertThat(out.toArray()).isEqualTo(new String[0]);
    }

    @Test
    void toStringListWithItems() {
        Assertions.assertThat(out.toString()).isEqualTo(TO_STRING);
    }

    @Test
    void toStringEmptyList() {
        out.clear();
        Assertions.assertThat(out.toString()).isEqualTo(TO_STRING_EMPTY);
    }

    @Test
    void hashCodeForEqualLists() {
        IntegerList expected = new IntegerListImpl(INITIAL_CAPACITY);
        expected.add(ITEM_0);
        expected.add(ITEM_1);
        expected.add(ITEM_2);
        Assertions.assertThat(out.equals(expected)).isTrue();
        Assertions.assertThat(out.hashCode()).isEqualTo(expected.hashCode());
    }

    @Test
    void hashCodeForDifferentLists() {
        IntegerList expected = new IntegerListImpl(INITIAL_CAPACITY);
        expected.add(ITEM_0);
        Assertions.assertThat(out.equals(expected)).isFalse();
        Assertions.assertThat(out.hashCode()).isNotEqualTo(expected.hashCode());
    }
}