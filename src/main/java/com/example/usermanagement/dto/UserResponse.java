package com.example.usermanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserResponse {
    private long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
