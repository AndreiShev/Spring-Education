package com.example.studentAccounting.event;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventListener {

    @EventListener
    void createStudentListener(StudentCreatedEvent event) {
        System.out.println("Student created: " + event.getStudent().getFullName());
    }

    @EventListener
    void deleteStudentListener(StudentDeletedEvent event) {
        System.out.println("Student removed: " + event.getId());
    }

    @EventListener
    void deleteAllStudentListener(AllStudentsRemovedEvent event) {
        System.out.println("All students have been removed.");
    }
}
