package com.example.rest.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertCommentRequest {
    @NotNull(message = "Комментируемая новость должна быть заполнена")
    @Positive(message = "ID комментируемой новости должно быть положительно")
    private Long newsId;

    @NotBlank(message = "Содержание комментария должно быть заполнено")
    @Size(min = 2, max = 50, message = "Содержание коммментария должно содержать от {min} до {max} слов")
    private String content;
}
