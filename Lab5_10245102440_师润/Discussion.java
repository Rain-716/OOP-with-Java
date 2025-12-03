import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Discussion {
    private List<Person> members = new ArrayList<>();
    private Random rand = new Random();

    public Discussion(int numStudents, int numTeachers) {
        // 强制生成指定数量
        for (int i = 0; i < numStudents; i++)
            members.add(new Student(18 + i, "female", new Name("Stu", String.valueOf(i))));
        for (int i = 0; i < numTeachers; i++)
            members.add(new Teacher(30 + i, "male", new Name("Tch", String.valueOf(i))));
    }

    public void broadcast() {
        int idx = rand.nextInt(members.size());
        Person sender = members.get(idx);
        for (Person p : members) {
            if (p != sender) {
                sender.chatWith(p, "Hello everyone!");
            }
        }
    }

    public static void main(String[] args) {
        Discussion d = new Discussion(2, 2);
        d.broadcast();
    }
}