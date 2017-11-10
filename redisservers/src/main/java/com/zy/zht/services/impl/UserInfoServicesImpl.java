package com.zy.zht.services.impl;

import com.zy.zht.bean.UserInfo;
import com.zy.zht.dao.UserInfoMapper;
import com.zy.zht.services.UserInfoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@Service
public class UserInfoServicesImpl implements UserInfoServices {
    @Autowired
    private UserInfoMapper dao;

    public int deleteByPrimaryKey(Integer userId) {
        return dao.deleteByPrimaryKey(userId);
    }

    public int insert(UserInfo record) {
        return dao.insert(record);
    }

    public int insertSelective(UserInfo record) {
        return dao.insertSelective(record);
    }

    public UserInfo selectByPrimaryKey(Integer userId) {
        return dao.selectByPrimaryKey(userId);
    }

    public int updateByPrimaryKeySelective(UserInfo record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(UserInfo record) {
        return dao.updateByPrimaryKey(record);
    }

    public List<UserInfo> selectAllUser() {
        return dao.selectAllUser();
    }
}
