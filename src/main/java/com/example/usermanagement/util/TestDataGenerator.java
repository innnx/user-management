package com.example.usermanagement.util;

import com.example.usermanagement.entity.User;
import com.example.usermanagement.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class TestDataGenerator implements CommandLineRunner {
    private final UserMapper userMapper;
    @Override
    public void run(String... args) throws Exception {
        Long userCount = userMapper.countUsers();
        if (userCount == null || userCount <20){
            System.out.println("生成测试数据...");
            generateTestUsers(50);
            System.out.println("生成测试数据完成");
        }
    }

    private void generateTestUsers(int i) {
        List<User> users = new ArrayList<>();
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "163.com"};
        String[] firstNames = {"张", "李", "王", "赵", "刘", "陈", "杨", "黄", "周", "吴"};
        String[] lastNames = {"伟", "芳", "娜", "秀英", "敏", "静", "丽", "强", "磊", "洋"};
        for (int j = 1; j <= i; j++) {
            User user = new User();
            //生成用户名
            String First = firstNames[random.nextInt(firstNames.length)];
            String Last = lastNames[random.nextInt(lastNames.length)];
            user.setUsername(First+Last+String.format("%03d", j));
            //生成邮箱
            String domain = domains[random.nextInt(domains.length)];
            user.setEmail(user.getUsername().toLowerCase()+"@"+ domain);
            //生成手机号
            user.setPhone("1"+ (3 + random.nextInt(7))+String.format("%09d", 100000000 + random.nextInt(900000000)));
            // 其他字段
            user.setPassword("$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVYsUi");
            user.setStatus(random.nextBoolean() ? 1 : 0);
            //创建时间
            LocalDateTime createTime = now.minusDays(random.nextInt(30))
                    .minusHours(random.nextInt(24))
                    .minusMinutes(random.nextInt(59));
            user.setCreateTime(createTime);
            user.setUpdateTime(createTime.plusDays(random.nextInt(5)));
            users.add(user);

        }
        //批量插入
        if (!users.isEmpty()){
            userMapper.batchInsert(users);
        }
    }
}
