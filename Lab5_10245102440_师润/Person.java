public class Person {
    // 公开成员
    public Name name;
    public final int age;
    // 私有成员
    private String gender;

    // 无参构造
    public Person() {
        this.name = new Name();
        this.age = 0;
        this.gender = "unknown";
    }

    // 带参构造
    public Person(int age, String gender, Name name) {
        this.age = age;
        this.gender = gender;
        this.name = name;
    }

    // 性别访问器与修改器
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // talk 方法
    public void talk() {
        System.out.println("Hi, how is it going");
    }

    public void talk(String s) {
        System.out.println(s);
    }

    // chatWith 方法
    public void chatWith(Person p, String s) {
        System.out.printf("%s to %s: %s\n", this.name.toString(), p.name.toString(), s);
    }

    // 测试 main
    public static void main(String[] args) {
        Person a = new Person(20, "female", new Name("Alice", "Wang"));
        Person b = new Person(22, "male", new Name("Bob", "Li"));
        a.chatWith(b, "Hello!");
        System.out.println(a.name.getFirstName()); // 正确
    }
}