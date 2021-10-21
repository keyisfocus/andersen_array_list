package ru.keyisfocus.andersen_array_list;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class ListArrayTest {

    @Test
    void sort() {
        var strings = new ListArray<String>();
        strings.add("dsa");
        strings.add("asd");

        strings.sort(Comparator.naturalOrder());
        assertEquals("asd", strings.get(0));

        var integers = new ListArray<Integer>();
        integers.add(2);
        integers.add(1);

        integers.sort(Comparator.naturalOrder());
    }

    @Test
    void constructorWithCapacity() {
        assertEquals(0, new ListArray<String>(0).capacity());
        assertEquals(100, new ListArray<String>(100).capacity());
        assertThrows(IllegalArgumentException.class, () -> new ListArray<String>(-1));
    }

    @Test
    void constructorFromCollection() {
        var l = new ArrayList<String>();
        l.add("asd");
        l.add("dsa");

        var list = new ListArray<>(l);

        var expected = new ListArray<String>();
        expected.add("asd");
        expected.add("dsa");
        assertEquals(expected, list);
    }

    @Test
    void trimToSize() {
        var l = new ListArray<String>();
        assertEquals(10, l.capacity());

        l.add("asd");
        l.trimToSize();

        assertEquals(1, l.capacity());
    }

    @Test
    void ensureCapacity() {
        var l = new ListArray<Integer>();
        l.ensureCapacity(156);

        assertTrue(l.capacity() >= 156);
    }

    @Test
    void length() {
        var l = new ListArray<Integer>();
        l.add(1);
        l.add(1);
        l.add(1);

        assertEquals(3, l.length());
    }

    @Test
    void isEmpty() {
        var l = new ListArray<Integer>();
        assertTrue(l.isEmpty());
    }

    @Test
    void contains() {
        var l = new ListArray<String>();
        l.add("qwe");
        l.add("value");

        assertTrue(l.contains("value"));
    }

    @Test
    void indexOf() {
        var l = new ListArray<String>();
        l.add("qwe");
        l.add("value");

        assertEquals(1, l.indexOf("value"));
    }

    @Test
    void lastIndexOf() {
        var l = new ListArray<String>();
        l.add("value");
        l.add("qwe");
        l.add("value");

        assertEquals(2, l.lastIndexOf("value"));
    }

    @Test
    void get() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("qwe");

        assertEquals("asd", list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(100));
    }

    @Test
    void add() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("dsa");

        assertEquals("asd", list.get(0));
        assertEquals("dsa", list.get(1));
    }

    @Test
    void addAt() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("dsa");
        list.add("qwe");

        list.add(2, "new_val");
        assertEquals("new_val", list.get(2));
        assertEquals("qwe", list.get(3));
    }

    @Test
    void set() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("dsa");

        list.set(1, "new_val");
        assertEquals("new_val", list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(100, ""));
    }

    @Test
    void remove() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("dsa");
        list.add("qwe");

        list.remove(1);

        assertEquals("qwe", list.get(1));
        assertEquals(2, list.length());
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(100));
    }

    @Test
    void toObjectArray() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("dsa");
        var arr = list.toArray();
        assertArrayEquals(new String[]{"asd", "dsa"}, arr);
    }

    @Test
    void toArray() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("dsa");

        var fitToSizeArray = list.toArray(new String[]{});
        assertArrayEquals(new String[]{"asd", "dsa"}, fitToSizeArray);

        var nullPaddedArray = list.toArray(new String[4]);
        assertArrayEquals(new String[]{"asd", "dsa", null, null}, nullPaddedArray);
    }

    @Test
    void testRemove() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("dsa");
        list.add("qwe");

        list.remove(1);
        assertEquals("qwe", list.get(1));

        list.remove("asd");
        assertEquals("qwe", list.get(0));
    }

    @Test
    void clear() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("dsa");

        list.clear();

        assertEquals(0, list.length());
        assertEquals("[]", list.toString());

    }

//    @Test
//    void iterator() {
//        var list = new ListArray<String>();
//        list.add("asd");
//        list.add("dsa");
//
//        for (String s : list) {
//            assert
//        }
//    }

    @Test
    void testToString() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("dsa");

        assertEquals("[asd, dsa]", list.toString());
    }

    @Test
    void testEquals() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("dsa");
        assertTrue(list.equals(list));

        var l = new ListArray<String>();
        l.add("asd");
        l.add("dsa");

        assertTrue(list.equals(l));

        assertFalse(list.equals(null));
        assertFalse(list.equals(new Object()));

        l.remove(1);
        assertFalse(list.equals(l));
    }

    @Test
    void testHashCode() {
        var list = new ListArray<String>();
        list.add("asd");
        list.add("dsa");

        var l = new ListArray<String>();
        l.add("asd");
        l.add("dsa");

        assertEquals(list.hashCode(), l.hashCode());

        var li = new ListArray<String>();
        li.add("dsa");
        li.add("asd");

        assertNotEquals(list.hashCode(), li.hashCode());
    }
}