package com.zy.zht.redis;

import com.zy.zht.bean.UserInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
public interface RedisServices {
    public void writeAll(String key, List<UserInfo> list);
    public List<UserInfo> readUserForPage(String key,int start,int max);
    public  long readUserCount(String key);
    public  boolean writeUser(String key,UserInfo userInfo);
    public boolean editUser(String key,UserInfo userInfo);
    public boolean removeUser(String key,String[]ids);
    public List<UserInfo> readAllUser(String key);
    public  boolean delForRedisUser(String key,String uuid);
    public boolean isExistsUser(String key,Integer id);
    public boolean writeUserExistsId(String key,UserInfo userInfo);
}
