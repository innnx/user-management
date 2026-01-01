package com.example.usermanagement.service;

import com.example.usermanagement.dto.UserCreateRequest;
import com.example.usermanagement.dto.UserResponse;
import com.example.usermanagement.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {
    //创建用户
    Long createUser(UserCreateRequest request);

    //更新用户
    void updateUser(UserUpdateRequest request);

    //删除用户
    void deleteUser(Long id);

    //根据id查询用户
    UserResponse getUserById(Long id);

    //查询全部
    List<UserResponse> getAllUsers();

    //分页查询
    List<UserResponse> getUserByPage(String keyword,Integer page,Integer pageSize);
}
