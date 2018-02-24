package com.zy.zht.spring.config;

import com.zy.zht.spring.dao.UserInfoDAO;
import com.zy.zht.spring.vo.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018/2/24.
 */
@Configuration//通过该注解来表名该类是一个spring的配置，相当于一个xml文件
@ComponentScan(basePackages = "com.zy.zht.spring")//配置扫描包
public class SpringConfig {
    @Bean//通过该注解来表名是一个bean对象，相当于xml中的<bean>
    public UserInfoDAO getUserInfoDAO(){
        return new UserInfoDAO();
    }
}
