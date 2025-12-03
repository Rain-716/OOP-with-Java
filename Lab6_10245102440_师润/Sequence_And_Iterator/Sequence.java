package Sequence_And_Iterator;

public interface Sequence {
    void add(SequenceItem item);
    SequenceItem get(int i);
    void remove(SequenceItem item);
    boolean contains(SequenceItem item);
    int size();
    boolean isEmpty();
    SeqIterator iterator();
    SeqIterator reverseIterator();
    SeqBiIterator biIterator();
    SequenceItem[] toArray();
    boolean equals(Sequence seq);
    String toString();
}