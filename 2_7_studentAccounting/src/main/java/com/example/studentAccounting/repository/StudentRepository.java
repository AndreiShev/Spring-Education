package com.example.studentAccounting.repository;


import com.example.studentAccounting.StudentPublisher;
import com.example.studentAccounting.model.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
@Scope("singleton")
public class StudentRepository {
    private Map<String, Student> studentMap = new HashMap<>();
    @Value("${app.generate.students}")
    private boolean generateStudents;

    @Value("${app.generate.min}")
    private int min;

    @Value("${app.generate.max}")
    private int max;

    final StudentPublisher studentPublisher;

    @Autowired
    public StudentRepository(StudentPublisher studentPublisher) {
        this.studentPublisher = studentPublisher;
    }

    @PostConstruct
    private void initStudents() {
        if(generateStudents) {
            int random = (int) ((Math.random() * (max - min)) + min);
            for (int i = 0; i < random; i++) {
                Student student = new Student("StudentName" + i, "StudentLastName" + i, (int) ((Math.random() * (100 - 17)) + 17));
                studentMap.put(student.getId(), student);
            }
        }
    }

    public void addStudent(String firstName, String lastName, Integer age) {
        Student student = new Student(firstName, lastName, age);
        studentMap.put(student.getId(), student);
        studentPublisher.publishCreatedEvent(student);
    }

    public void listStudent() {
        studentMap.values().stream().forEach(student -> System.out.println(student));
    }

    public void deleteStudent(String id) {
        if(studentMap.remove(id) != null){
            studentPublisher.publishDeletedEvent(id);
        } else {
            System.out.println("Not found student " + id);
        }
    }

    public void deleteStudents() {
        studentMap.clear();
        studentPublisher.publishAllRemovedEvent();
    }
}
