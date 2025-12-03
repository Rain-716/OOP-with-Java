public class Teacher extends Person {
    public Teacher(int age, String gender, Name name) {
        super(age, gender, name);
    }

    @Override
    public void talk() {
        System.out.println("Hi, how is your paper going?");
    }

    public static void main(String[] args) {
        Person p = new Teacher(30, "male", new Name("Dr.", "Wu"));
        p.talk();
    }
}