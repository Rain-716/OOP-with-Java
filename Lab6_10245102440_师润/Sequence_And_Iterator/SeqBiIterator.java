package Sequence_And_Iterator;

public interface SeqBiIterator extends SeqIterator {
    boolean hasPrevious();
    SequenceItem previous();
}