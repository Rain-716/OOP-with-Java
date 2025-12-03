public class ElderTeacher extends Teacher {
    // 私有静态实例
    private static ElderTeacher instance = null;

    // 私有构造
    private ElderTeacher(int age, String gender, Name name) {
        super(age, gender, name);
    }

    // 全局访问点
    public static ElderTeacher getInstance(int age, String gender, Name name) {
        if (instance == null) {
            instance = new ElderTeacher(age, gender, name);
        }
        return instance;
    }
}