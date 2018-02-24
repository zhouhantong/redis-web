package com.zy.zht.spring.dao;

import com.zy.zht.spring.vo.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */
public class UserInfoDAO {
    public List<UserInfo>queryUserInfo(){
        List<UserInfo>result=new ArrayList<UserInfo>();
        for (int i=0;i<10;i++){
            UserInfo userInfo=new UserInfo();
            userInfo.setUsername("zhangsan"+i);
            userInfo.setPassword("bbb"+i);
            result.add(userInfo);
        }
        return result;
    }
}
