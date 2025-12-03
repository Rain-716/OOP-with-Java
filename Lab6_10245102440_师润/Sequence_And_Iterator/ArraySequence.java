package Sequence_And_Iterator;

public class ArraySequence implements Sequence {
    private SequenceItem[] elements;
    private int count;

    public ArraySequence() {
        elements = new SequenceItem[4];
        count = 0;
    }

    @Override
    public void add(SequenceItem item) {
        ensureCapacity(count + 1);
        elements[count++] = item;
    }

    @Override
    public SequenceItem get(int i) {
        if (i < 0 || i >= count) throw new IndexOutOfBoundsException();
        return elements[i];
    }

    @Override
    public void remove(SequenceItem item) {
        for (int i = 0; i < count; i++) {
            if (elements[i].equals(item)) {
                System.arraycopy(elements, i+1, elements, i, count - i - 1);
                elements[--count] = null;
                shrinkCapacity();
                return;
            }
        }
    }

    @Override
    public boolean contains(SequenceItem item) {
        for (int i = 0; i < count; i++) if (elements[i].equals(item)) return true;
        return false;
    }

    @Override
    public int size() { return count; }

    @Override
    public boolean isEmpty() { return count == 0; }

    @Override
    public SeqIterator iterator() {
        return new ArrayIterator();
    }

    @Override
    public SeqIterator reverseIterator() {
        return new ReverseArrayIterator();
    }

    @Override
    public SeqBiIterator biIterator() {
        return new BiArrayIterator();
    }

    @Override
    public SequenceItem[] toArray() {
        SequenceItem[] arr = new SequenceItem[count];
        System.arraycopy(elements, 0, arr, 0, count);
        return arr;
    }

    @Override
    public boolean equals(Sequence seq) {
        if (seq.size() != count) return false;
        for (int i = 0; i < count; i++) {
            if (!elements[i].equals(seq.get(i))) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < count; i++) {
            sb.append(elements[i]);
            if (i < count - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCap = elements.length * 2;
            SequenceItem[] newArr = new SequenceItem[newCap];
            System.arraycopy(elements, 0, newArr, 0, count);
            elements = newArr;
        }
    }

    private void shrinkCapacity() {
        if (elements.length > 4 && count < elements.length / 4) {
            int newCap = elements.length / 2;
            SequenceItem[] newArr = new SequenceItem[newCap];
            System.arraycopy(elements, 0, newArr, 0, count);
            elements = newArr;
        }
    }

    // 内部类：正序迭代器
    private class ArrayIterator implements SeqIterator {
        int cursor = 0;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor < count;
        }

        @Override
        public SequenceItem next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            lastRet = cursor;
            return elements[cursor++];
        }

        @Override
        public void remove() {
            if (lastRet < 0) throw new IllegalStateException();
            ArraySequence.this.remove(elements[lastRet]);
            cursor = lastRet;
            lastRet = -1;
        }
    }

    // 内部类：逆序迭代器
    private class ReverseArrayIterator implements SeqIterator {
        int cursor = count - 1;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor >= 0;
        }

        @Override
        public SequenceItem next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            lastRet = cursor;
            return elements[cursor--];
        }

        @Override
        public void remove() {
            if (lastRet < 0) throw new IllegalStateException();
            ArraySequence.this.remove(elements[lastRet]);
            cursor = lastRet - 1;
            lastRet = -1;
        }
    }

    // 内部类：双向迭代器
    private class BiArrayIterator implements SeqBiIterator {
        int cursor = 0;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor < count;
        }

        @Override
        public SequenceItem next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            lastRet = cursor;
            return elements[cursor++];
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public SequenceItem previous() {
            if (!hasPrevious()) throw new java.util.NoSuchElementException();
            lastRet = --cursor;
            return elements[cursor];
        }

        @Override
        public void remove() {
            if (lastRet < 0) throw new IllegalStateException();
            ArraySequence.this.remove(elements[lastRet]);
            cursor = lastRet;
            lastRet = -1;
        }
    }
}