package com.example.tasktracker.configuration;


import com.example.tasktracker.handler.TaskHandler;
import com.example.tasktracker.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Routers {
    /**
     * Для сущности User необходимо создать API, который предоставляет возможность:
     *
     * найти всех пользователей,
     * найти пользователя по ID,
     * создать пользователя,
     * обновить информацию о пользователе,
     * удалить пользователя по ID.*/

    @Bean
    public RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
        return RouterFunctions.route()
                .GET("api/users", userHandler::getAllItem)
                .GET("api/users/{id}", userHandler::findById)
                .POST("api/users", userHandler::createUser)
                .PUT("api/users/{id}", userHandler::updateUser)
                .DELETE("api/users/{id}", userHandler::deleteUser)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> taskRouter(TaskHandler taskHandler) {
        return RouterFunctions.route()
                .GET("api/tasks", taskHandler::getAllTask)
                .GET("api/tasks/{id}", taskHandler::getTaskById)
                .POST("api/tasks", taskHandler::createTask)
                .PUT("api/tasks/{id}", taskHandler::updateTask)
                .PUT("api/tasks/{id}/author/{authorId}/change", taskHandler::changeAuthor)
                .PUT("api/tasks/{id}/assignee/{assigneeId}/change", taskHandler::changeAssignee)
                .PUT("api/tasks/{id}/observers/{observerId}/add", taskHandler::addObserver)
                .DELETE("api/tasks/{id}", taskHandler::deleteTask).build();
    }

}
