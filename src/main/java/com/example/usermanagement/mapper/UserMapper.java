package com.example.usermanagement.mapper;

import com.example.usermanagement.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    //插入用户
    int insert(User user);

    //根据id删除
    int deleteById(@Param("id") Long id);

    //根据id更新
    int updateById(User user);

    //根据id查询
    User selectById(@Param("id") Long id);

    //根据用户名查询
    User selectByUsername(@Param("username") String username);

    //查询全部
    List<User> selectAll();

    //分页查询
    List<User> selectByPage(@Param("keyword")String keyword);

    //批量插入
    int batchInsert(@Param("users")List<User> users);

    //统计用户
    Long countUsers();
}
