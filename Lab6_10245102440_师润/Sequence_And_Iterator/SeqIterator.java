package Sequence_And_Iterator;

public interface SeqIterator {
    boolean hasNext();
    SequenceItem next();
    void remove();
}