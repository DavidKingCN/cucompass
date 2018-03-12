package cn.com.davidking.poi;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Student
 * 
 * @author daikai
 * @created 
 */
public class Student {
    /**
     * id   
     */
    private Integer id;
    /**
     * 学号
     */
    private String no;
    /**
     * 姓名
     */
    private String name;
    /**
     * 学院
     */
    private String age;
    /**
     * 成绩
     */
    private float score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
	public String toString() {
		return "Student [id=" + id + ", no=" + no + ", name=" + name + ", age=" + age + ", score=" + score + "]";
	}

	public static void main(String[] args)throws Exception {
		Class<Student> cs = Student.class;
		Field f = cs.getDeclaredField("name");
		Type type = f.getGenericType();
		
		System.out.println(type.getTypeName());
	}
}