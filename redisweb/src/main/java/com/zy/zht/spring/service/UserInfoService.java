package com.zy.zht.spring.service;

import com.zy.zht.spring.dao.UserInfoDAO;
import com.zy.zht.spring.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */
@Service
public class UserInfoService {
    @Autowired
    private UserInfoDAO userInfoDAO;
    public List<UserInfo> queryUserInfo(){
    List<UserInfo>result=userInfoDAO.queryUserInfo();
    return result;
    }
}
