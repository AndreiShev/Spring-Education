package com.example.tasktracker.configuration;


import com.example.tasktracker.handler.TaskHandler;
import com.example.tasktracker.handler.TaskUpdateHandler;
import com.example.tasktracker.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class Routers {

    @Bean
    public RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {

        return RouterFunctions.route()
                .GET("api/users", userHandler::getAllItem)
                .GET("api/users/{id}", userHandler::findById)
                .POST("api/users",  RequestPredicates.queryParam("roleType", t -> true), userHandler::createUser)
                .PUT("api/users/{id}", userHandler::updateUser)
                .DELETE("api/users/{id}", userHandler::deleteUser)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> taskRouter(TaskHandler taskHandler, TaskUpdateHandler taskUpdateHandler) {
        return RouterFunctions.route()
                .GET("api/tasks", taskHandler::getAllTask)
                .GET("api/tasks/{id}", taskHandler::getTaskById)
                .POST("api/tasks", taskHandler::createTask)
                .PUT("api/tasks/{id}", taskUpdateHandler::updateTask)
                .PUT("api/tasks/{id}/author/{authorId}/change", taskHandler::changeAuthor)
                .PUT("api/tasks/{id}/assignee/{assigneeId}/change", taskHandler::changeAssignee)
                .PUT("api/tasks/{id}/observers/{observerId}/add", taskHandler::addObserver)
                .DELETE("api/tasks/{id}", taskHandler::deleteTask).build();
    }
}
