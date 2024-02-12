package com.example.studentAccounting;

import com.example.studentAccounting.repository.StudentRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class Commander {
    final StudentRepository studentRepository;

    public Commander(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @ShellMethod(key = "add")
    public void addStudent(String firstName, String lastName, Integer age) {
        studentRepository.addStudent(firstName, lastName, age);
    }

    @ShellMethod(key = "list")
    public void listStudent() {
        studentRepository.listStudent();
    }

    @ShellMethod(key = "del")
    public void deleteStudent(String id) {
        studentRepository.deleteStudent(id);
    }

    @ShellMethod(key = "clr")
    public void deleteStudents() {
        studentRepository.deleteStudents();
    }
}
