package com.example.studentAccounting.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StudentDeletedEvent extends ApplicationEvent {
    String id;
    public StudentDeletedEvent(Object source, String id) {
        super(source);
        this.id = id;
    }
}
