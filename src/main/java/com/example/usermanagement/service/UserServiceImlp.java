package com.example.usermanagement.service;

import com.example.usermanagement.dto.UserCreateRequest;
import com.example.usermanagement.dto.UserResponse;
import com.example.usermanagement.dto.UserUpdateRequest;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor    //自动生成构造函数注入
public class UserServiceImlp implements UserService {
    private final UserMapper userMapper;

    @Override//添加用户
    @Transactional(rollbackFor =  Exception.class)
    public Long createUser(UserCreateRequest request) {
        //检查用户名是否存在
        User existUser = userMapper.selectByUsername(request.getUsername());
        if (existUser != null) {
            throw new RuntimeException("该用户名已存在");
        }
        //创建实体用户
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        // 密码加密
        // user.setPassword(passwordEncoder.encode(request.getPassword()));
        userMapper.insert(user);
        log.info("创建用户成功，用户id：{}", user.getId());
        return user.getId();
    }

    @Override//更新用户
    @Transactional(rollbackFor =  Exception.class)
    public void updateUser(UserUpdateRequest request) {
        //检查用户是否存在
        User existUser = userMapper.selectById(request.getId());
        if (existUser == null) {
            throw new RuntimeException("该用户不存在");
        }
        //更新用户信息
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setUpdateTime(LocalDateTime.now());
        int row = userMapper.updateById(user);
        if (row == 0) {
            throw new RuntimeException("更新用户失败");
        }
        log.info("更新用户成功，用户id：{}", user.getId());
    }

    @Override//删除用户
    @Transactional(rollbackFor =  Exception.class)
    public void deleteUser(Long id) {
        //检查用户是否存在
        User existUser = userMapper.selectById(id);
        if (existUser == null) {
            throw new RuntimeException("用户不存在");
        }
        int row = userMapper.deleteById(id);
        if (row == 0) {
            throw new RuntimeException("删除用户失败");
        }
        log.info("删除用户成功，用户id:{}",id);
    }

    @Override//根据id查询
    public UserResponse getUserById(Long id) {
        //判断用户是否存在
        User existUser = userMapper.selectById(id);
        if (existUser == null) {
            throw new RuntimeException("用户不存在");
        }
        return convertToResponse(existUser);
    }

    @Override//查询全部
    public List<UserResponse> getAllUsers() {
        List<User> users = userMapper.selectAll();
        return users.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override//分页查询
    public List<UserResponse> getUserByPage(String keyword, Integer pageNum, Integer pageSize) {
        //参数校验
        if (pageNum == null  || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        // 使用PageHelper进行分页
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectByPage(keyword);
        return users.stream().map(this::convertToResponse).collect(Collectors.toList());
    }


    //转换为响应对象
    private UserResponse convertToResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }
}
