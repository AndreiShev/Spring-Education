package com.example.rest.web.model;

import com.example.rest.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse {

    private List<UserResponse> users = new ArrayList<>();
}
