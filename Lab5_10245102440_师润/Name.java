public class Name {
    // 私有数据成员
    private String firstName;
    private String lastName;

    // 无参构造
    public Name() {
        this.firstName = "";
        this.lastName = "";
    }

    // 带参构造
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // 访问器与修改器
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // 转为 String
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    // 测试 main
    public static void main(String[] args) {
        Name n = new Name("John", "S");
        System.out.println(n.getFirstName());
    }
}