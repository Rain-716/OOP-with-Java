public class Student extends Person {
    public Student(int age, String gender, Name name) {
        super(age, gender, name);
    }

    @Override
    public void talk() {
        System.out.println("Hi, how is your homework going?");
    }

    public static void main(String[] args) {
        Person p = new Student(18, "male", new Name("Bob", "Li"));
        p.talk();
    }
}