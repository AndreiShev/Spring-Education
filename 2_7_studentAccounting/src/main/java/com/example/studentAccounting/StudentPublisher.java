package com.example.studentAccounting;

import com.example.studentAccounting.event.AllStudentsRemovedEvent;
import com.example.studentAccounting.event.StudentCreatedEvent;
import com.example.studentAccounting.event.StudentDeletedEvent;
import com.example.studentAccounting.model.Student;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class StudentPublisher {
    private final ApplicationEventPublisher publisher;

    StudentPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishCreatedEvent(Student student) {
        publisher.publishEvent(new StudentCreatedEvent(this, student));
    }

    public void publishDeletedEvent(String id) {
        publisher.publishEvent(new StudentDeletedEvent(this, id));
    }

    public void publishAllRemovedEvent() {
        publisher.publishEvent(new AllStudentsRemovedEvent(this));
    }
}
