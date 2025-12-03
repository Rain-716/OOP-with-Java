public class TestDynamic {
    public static void main(String[] args) {
        Person[] group = {
            new Student(18, "female", new Name("Amy", "Li")),
            new Teacher(40, "male", new Name("Bob", "Wang"))
        };
        for (Person p : group) {
            p.talk();
        }
    }
}