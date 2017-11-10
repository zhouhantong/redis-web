package com.zy.zht.controller;

import com.zy.zht.bean.UserInfo;
import com.zy.zht.redis.RedisServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/31.
 */
@Controller
public class UserController {
    @Autowired
    private RedisServices redisServices;
    @RequestMapping("selectUserForPage.do")
    @ResponseBody
    public Map selectForUserPage(@RequestParam("page") int page,@RequestParam("rows") int rows){
        List<UserInfo> list=redisServices.readUserForPage("userInfo",(page-1)*rows,rows);
        int total=(int)redisServices.readUserCount("userInfo");
        Map map=new HashMap();
        map.put("rows",list);
        map.put("total",total);
        return map;
    }
    @RequestMapping("insertUser.do")
    @ResponseBody
    public boolean insertUser(UserInfo userInfo){
        return redisServices.writeUser("userInfo",userInfo);
    }
    @RequestMapping("updateUser.do")
    @ResponseBody
    public boolean editUser(UserInfo userInfo){
        return redisServices.editUser("userInfo",userInfo);
    }
    @RequestMapping("deleteUser.do")
    @ResponseBody
    public boolean removeUser(String ids){
        String[]idss=ids.split(",");
        return  redisServices.removeUser("userInfo",idss);
    }
}
