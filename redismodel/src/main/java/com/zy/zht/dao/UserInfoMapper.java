package com.zy.zht.dao;

import com.zy.zht.bean.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    List<UserInfo> selectAllUser();
}