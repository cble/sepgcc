package com.sepgcc.site.service;

import com.sepgcc.site.dto.User;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:config/spring/appcontext-*.xml"
})
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testLoadUser() throws Exception {
        User user = userService.loadUser("test", "test");
        Assert.notNull(user, "oops...");
    }


    private String[][] importUserList = {
            {"FXQJYJ", "奉贤区教育局", "1", "44087579"},
            {"SHKFDX", "上海开放大学", "1", "20342229"},
            {"SHJRXY", "上海金融学院", "1", "49022435"},
    };
    @Test
    public void testBatchImportUser() {
        List<User> userList = new ArrayList<User>();
        for (String[] items : importUserList) {
            User user = new User();
            user.setUsername(items[0]);
            user.setNickname(items[1]);
            user.setUserGroup(NumberUtils.toInt(items[2]));
            user.setPassword(items[3]);
            userList.add(user);
        }
        int successNumber = userService.batchCreateUser(userList);
        System.out.println("successNumber=" + successNumber);
    }
}
