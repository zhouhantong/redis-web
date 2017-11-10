package com.zy.zht.redis.impl;

import com.zy.zht.bean.UserInfo;
import com.zy.zht.redis.RedisServices;
import com.zy.zht.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@Service
public class RedisServicesImpl implements RedisServices {

    @Autowired
    private RedisTemplate redisTemplate;
    public void writeAll(String key, List<UserInfo> list) {
        HashOperations ho=redisTemplate.opsForHash();
        for (UserInfo userInfo:list) {
            ho.put(key,userInfo.getUserId(),userInfo);
        }
    }

    public List<UserInfo> readUserForPage(String key, int start, int max) {
        HashOperations ho=redisTemplate.opsForHash();
        List<UserInfo>list=ho.values(key);
        Collections.sort(list, new Comparator<UserInfo>() {
            public int compare(UserInfo o1, UserInfo o2) {
                if (o1.getUserId()!=null&&o2.getUserId()!=null)
                    return o2.getUserId()-o1.getUserId();
                else
                    return 1;
            }
        });
        List<UserInfo>result=new ArrayList<UserInfo>();
        for (int i=start;i<start+max-1;i++){
            if (i<list.size()){
                result.add(list.get(i));
            }else {
                break;
            }
        }
        return result;
    }

    public long readUserCount(String key) {
        HashOperations ho=redisTemplate.opsForHash();

        return ho.size(key);
    }
    /**
     * redis中存储User数据 必须使用一个唯一的id值作为map的key
     * 使用这个id和原有的数据进行区分 (数字 - 数据库) (uuid - 新增)
     */
    public boolean writeUser(String key, UserInfo userInfo) {
        try {
            HashOperations ho= redisTemplate.opsForHash();
            String uuid=new UUIDGenerator().generate().toString();
            System.out.println("=================="+uuid);
            userInfo.setUuid(uuid);
            ho.put(key,uuid,userInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean editUser(String key, UserInfo userInfo) {
        try {
            HashOperations ho= redisTemplate.opsForHash();
            userInfo.setEdit(true);
            if(userInfo.getUserId()!=null){
                ho.put(key,userInfo.getUserId(),userInfo);
            }else {
                ho.put(key,userInfo.getUuid(),userInfo);
            }
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeUser(String key, String[] ids) {
        try {
            HashOperations ho=redisTemplate.opsForHash();
            for (int i=0;i<ids.length;i++){
                long n=ho.delete(key,ids[i]);
                if(n==0){
                    ho.delete(key,Integer.parseInt(ids[i]));
                }
            }
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UserInfo> readAllUser(String key){
        HashOperations ho=redisTemplate.opsForHash();
        return ho.values(key);
    }

    public boolean delForRedisUser(String key,String uuid){
        HashOperations ho=redisTemplate.opsForHash();
        long result=ho.delete(key,uuid);
        return  result==1;
    }
    public boolean isExistsUser(String key, Integer id) {
        HashOperations ho=redisTemplate.opsForHash();
        return ho.hasKey(key,id);
    }

    public boolean writeUserExistsId(String key, UserInfo userInfo) {
        try {
            HashOperations ho=redisTemplate.opsForHash();
            ho.put(key,userInfo.getUserId(),userInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
