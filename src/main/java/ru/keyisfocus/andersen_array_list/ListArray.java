package ru.keyisfocus.andersen_array_list;

import java.util.*;

public class ListArray<E> implements Iterable<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_DATA = {};

    /**
     * Internal data holding array
     */
    private Object[] data;

    /**
     * Value representing the size of the underlying data,
     * does not equal to the length (capacity) of the internal array most of the time
     */
    private int size;

    /**
     * Constructs empty list with default capacity
     */
    public ListArray() {
        data = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Constructs empty list with given capacity
     *
     * @param initialCapacity capacity to ensure
     * @throws IllegalArgumentException if given capacity is negative
     */
    public ListArray(int initialCapacity) {
        if (initialCapacity > 0) {
            data = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            data = EMPTY_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    /**
     * Constructs a list containing all the elements of given collection
     *
     * @param c collection elements of which will be in a new list
     * @throws NullPointerException if given collection is null
     */
    public ListArray(Collection<? extends E> c) {
        var a = c.toArray();
        size = a.length;
        data = size == 0 ? EMPTY_DATA : Arrays.copyOf(a, size, Object[].class);
    }

    /**
     * Trim internal array to save space
     */
    public void trimToSize() {
        if (size < data.length) {
            data = size == 0 ? EMPTY_DATA : Arrays.copyOf(data, size);
        }
    }

    /**
     * Ensure the internal array is at least of given size
     *
     * @param minCapacity minimal required capacity
     */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            grow(minCapacity);
        }
    }

    /**
     * Sorts the list using quicksort
     */
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> comparator) {
        var array = Arrays.copyOf(data, size);
        Sorter.quickSort((E[]) array, 0, size - 1, comparator);
        System.arraycopy(array, 0, data, 0, size);
    }



    private void grow(int minCapacity) {
        int oldCapacity = data.length;
        if (oldCapacity > 0 || data != EMPTY_DATA) {
            data = Arrays.copyOf(data, Math.max(minCapacity, oldCapacity * 2));
        } else {
            data = new Object[minCapacity];
        }
    }

    private void grow() {
        grow(size + 1);
    }

    /**
     * @return amount of elements currently stored
     */
    public int length() {
        return size;
    }

    /**
     * @return is list empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param o element to check
     * @return whether given element is in the list
     */
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * @param o element to find index of
     * @return index of given element, or -1 if not present
     */
    public int indexOf(Object o) {
        return indexOfInternal(o, false);
    }

    /**
     * @param o element to find last index of
     * @return last index of given element, or -1 if not present
     */
    public int lastIndexOf(Object o) {
        return indexOfInternal(o, true);
    }

    private int indexOfInternal(Object o, boolean reverse) {
        if (reverse) {
            for (int i = size - 1; i >= 0; i--) {
                if (o == null) {
                    if (data[i] == null) {
                        return i;
                    }
                } else {
                    if (o.equals(data[i])) {
                        return i;
                    }
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o == null) {
                    if (data[i] == null) {
                        return i;
                    }
                } else {
                    if (o.equals(data[i])) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * @return an array containing all the elements in this list
     * in proper sequence
     */
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    /**
     * Returns an array containing all the elements in this list in proper
     * sequence. The runtime type of the returned array is that of the
     * specified array.  If the list fits in the specified array,
     * it is returned therein.  Otherwise, a new array is allocated with
     * the runtime type of the specified array and the size of this list.
     *
     * @param t the array into which the elements of the list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of the list
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in
     *                              this list
     * @throws NullPointerException if the specified array is null
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] t) {
        if (t.length < size) {
            return (T[]) Arrays.copyOf(data, size, t.getClass());
        }
        System.arraycopy(data, 0, t, 0, size);
        return t;
    }

    /**
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) data[index];
    }

    /**
     * Appends the specified element to the end of this list.
     */
    public void add(E e) {
        if (size == data.length) {
            grow();
        }
        data[size] = e;
        size++;
    }

    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, E element) {
        checkIndex(index);
        if (size == data.length) {
            grow();
        }
        var toCopy = Arrays.copyOfRange(data, index, size);
        System.arraycopy(toCopy, 0, data, index + 1, toCopy.length);
        data[index] = element;
        size++;
    }

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        checkIndex(index);
        var oldValue = (E) data[index];
        data[index] = element;
        return oldValue;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param i the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public E remove(int i) {
        checkIndex(i);

        E oldValue = (E) data[i];
        size--;
        if (size > i) {
            System.arraycopy(data, i + 1, data, i, size - i);
        }
        data[size] = null;

        return oldValue;
    }

    /**
     * @param o element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     */
    public boolean remove(Object o) {
        if (indexOf(o) >= 0) {
            remove(indexOf(o));
            return true;
        }
        return false;
    }

    /**
     * Removes all the elements from this list.
     */
    public void clear() {
        Arrays.fill(data, null);
        size = 0;
    }

    private void checkIndex(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    // Iteration and such
    private class ListArrayIterator implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        ListArrayIterator() {
        }

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            if (cursor >= data.length) {
                throw new ConcurrentModificationException();
            }
            lastRet = cursor;
            cursor++;
            return (E) data[lastRet];
        }
    }

    /**
     * @return an iterator over the elements in this list in proper sequence
     * @throws UnsupportedOperationException on remove() call
     */
    public Iterator<E> iterator() {
        return new ListArrayIterator();
    }

    /**
     * Returns a string representation of this collection.  The string
     * representation consists of a list of the collection's elements in the
     * order they are returned by its iterator, enclosed in square brackets
     * ({@code "[]"}).  Adjacent elements are separated by the characters
     * {@code ", "} (comma and space).  Elements are converted to strings as
     * by {@link String#valueOf(Object)}.
     *
     * @return a string representation of this collection
     */
    public String toString() {
        var it = iterator();
        if (!it.hasNext()) {
            return "[]";
        }

        var sb = new StringBuilder("[");
        for (; ; ) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext()) {
                return sb.append(']').toString();
            }
            sb.append(',').append(' ');
        }
    }

    /**
     * @param o list to compare with this list
     * @return true if lists are equal
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof ListArray other)) {
            return false;
        }

        var oit = other.iterator();
        for (int i = 0; i < size; i++) {
            if (!oit.hasNext() || !Objects.equals(data[i], oit.next())) {
                return false;
            }
        }
        return !oit.hasNext();
    }

    /**
     * @return hash of the list based on the elements of the list
     */
    public int hashCode() {
        return Objects.hash(data);
    }

    // For tests
    int capacity() {
        return data.length;
    }
}
