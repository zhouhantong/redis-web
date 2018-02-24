package com.zy.zht.spring.test;

import com.zy.zht.spring.config.SpringConfig;
import com.zy.zht.spring.service.UserInfoService;
import com.zy.zht.spring.vo.UserInfo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */
public class UserInfoTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
        UserInfoService userInfoService=context.getBean(UserInfoService.class);
        List<UserInfo>result=userInfoService.queryUserInfo();
        for (UserInfo user:result) {
            System.out.println("用户名:"+user.getUsername());
            System.out.println("密码："+user.getPassword());
        }
        context.destroy();//销毁容器
    }
}
