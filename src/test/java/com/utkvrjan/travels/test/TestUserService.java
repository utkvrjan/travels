package com.utkvrjan.travels.test;

import com.utkvrjan.travels.TravelsApplication;
import com.utkvrjan.travels.entity.User;
import com.utkvrjan.travels.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TravelsApplication.class)
//@RunWith(Suite.class)
//其作用是使用JUnit执行一个测试套件。Suite类是JUnit自带的，意为套件，顾名思义，就是一套东西。
// 通过它，可以把多个相关的测试类看做一个测试套件一起测试。
@RunWith(SpringRunner.class)
public class TestUserService {

    @Autowired
    private UserService userService;

    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("xiaochen");
        user.setPassword("123");
        user.setEmail("xiaochen@qq.com");
        userService.register(user);
    }
}
