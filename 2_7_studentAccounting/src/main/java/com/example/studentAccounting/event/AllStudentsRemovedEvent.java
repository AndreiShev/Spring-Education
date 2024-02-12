package com.example.studentAccounting.event;

import org.springframework.context.ApplicationEvent;

public class AllStudentsRemovedEvent extends ApplicationEvent {

    public AllStudentsRemovedEvent(Object source) {
        super(source);
    }
}
