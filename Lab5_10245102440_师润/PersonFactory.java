import java.util.Random;

public class PersonFactory {
    private static Random rand = new Random();

    public Person next() {
        if (rand.nextBoolean()) {
            return new Student(18, "female", new Name("Std", "One"));
        } else {
            return new Teacher(35, "male", new Name("Tch", "One"));
        }
    }
}