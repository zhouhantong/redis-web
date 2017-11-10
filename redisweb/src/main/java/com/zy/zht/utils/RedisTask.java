package com.zy.zht.utils;

import com.zy.zht.bean.UserInfo;
import com.zy.zht.redis.RedisServices;
import com.zy.zht.services.UserInfoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */
@Component("rTask")
public class RedisTask {
    @Autowired
    private RedisServices rs;
    @Autowired
    private UserInfoServices us;

    public void redisTask(){
        //将redis中的数据同步到数据库中
        System.out.println("每隔一分钟同步一次.........");
        //数据库中存储的所有User数据
        List<UserInfo> users=us.selectAllUser();
        for (UserInfo user:users) {
            //去redis中检索是否有这个数据，如果没有，表示这个数据已经从redis中移除
            if (!rs.isExistsUser("userInfo",user.getUserId())){
                System.out.println("需要同步删除-->"+user.getUserId());
                us.deleteByPrimaryKey(user.getUserId());
            }
        }
        //redis中存储的所有User数据
        List<UserInfo>redisUsers=rs.readAllUser("userInfo");
        //新录入的数据特点：id==null,uuid！=null
        for (UserInfo redisUser: redisUsers) {
            if(redisUser.getUserId()==null){
                System.out.println("需要同步添加的数据-->"+redisUser.getUuid());
                int result=us.insertSelective(redisUser);
                System.out.println("============"+result);
                //从redis中移除这条临时数据 ,再将数据库中新增的数据录入到redis中
                rs.delForRedisUser("userInfo",redisUser.getUuid());
                rs.writeUserExistsId("userInfo",redisUser);
            }else if(redisUser.isEdit()){
                System.out.println("需要同步修改的数据-->"+redisUser.getUserId());
                int n=us.updateByPrimaryKeySelective(redisUser);
                System.out.println("============"+n);
                redisUser.setEdit(false);
                rs.writeUserExistsId("userInfo",redisUser);

            }
        }
    }
}
