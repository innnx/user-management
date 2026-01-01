package com.example.usermanagement.controller;

import com.example.usermanagement.dto.UserCreateRequest;
import com.example.usermanagement.dto.UserResponse;
import com.example.usermanagement.dto.UserUpdateRequest;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    //创建用户
    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody UserCreateRequest request){
        Long userId = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    //更新用户
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable long id, @Valid @RequestBody UserUpdateRequest request){
        request.setId(id);
        userService.updateUser(request);
        return ResponseEntity.ok().build();
    }

    //删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    //获取用户详情
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long id){
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    //获取用户列表
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize){
        List<UserResponse> userByPage = userService.getUserByPage(keyword, pageNum, pageSize);
        return ResponseEntity.ok(userByPage);
    }

}
