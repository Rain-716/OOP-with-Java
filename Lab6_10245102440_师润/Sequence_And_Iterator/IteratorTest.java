package Sequence_And_Iterator;

public class IteratorTest {
    public static void display(SeqIterator it) {
        while (it.hasNext()) {
            System.out.print(it.next().getData() + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArraySequence seq = new ArraySequence();
        seq.add(new SequenceItem("A"));
        seq.add(new SequenceItem("B"));
        seq.add(new SequenceItem("C"));
        System.out.println("Sequence: " + seq);

        System.out.print("Forward: "); display(seq.iterator());
        System.out.print("Backward: "); display(seq.reverseIterator());

        System.out.print("Bi-directional: ");
        SeqBiIterator bi = seq.biIterator();
        while (bi.hasNext()) {
            System.out.print(bi.next().getData() + " ");
        }
        while (bi.hasPrevious()) {
            System.out.print(bi.previous().getData() + " ");
        }
        System.out.println();
    }
}