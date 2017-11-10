package com.zy.zht.utils;

import com.zy.zht.bean.UserInfo;
import com.zy.zht.dao.UserInfoMapper;
import com.zy.zht.redis.RedisServices;
import com.zy.zht.services.UserInfoServices;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
public class RedisListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent sce) {
        /**
         * 先从数据库中查询User表中的所有数据
         * UserInfoMapper【iod容器】提供的selectAllUser方法
         * 再将这些数据存储到redis中
         * RedisService中提供的向redis中存储数据的方法
         */
        ApplicationContext ac= WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        UserInfoServices services=ac.getBean(UserInfoServices.class);
        List<UserInfo> list=services.selectAllUser();
        System.out.println("----------"+list.size());
        RedisServices redisServices=ac.getBean(RedisServices.class);
        redisServices.writeAll("userInfo",list);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
