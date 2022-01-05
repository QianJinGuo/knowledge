package jinguo.tech.util.threads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReference<Student> ar = new AtomicReference<>();
        Student student = new Student("SnailClimb", 22);
        //设置ar的值为student
        ar.set(student);
        Student updateStudent = new Student("Daisy", 20);
        //如果 ar 的值为 student 的话，则将其设置为 updateStudent。
        ar.compareAndSet(student, updateStudent);
        System.out.println(ar.get().getName());
        System.out.println(ar.get().getAge());
    }
}

@Setter
@Getter
@AllArgsConstructor
class Student {
    private String name;
    private int age;
}




