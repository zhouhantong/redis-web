package com.zy.zht.services;

import com.zy.zht.bean.Student;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
public interface StudentServices {
    int deleteByPrimaryKey(Integer stuId);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer stuId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    List<Student> selectAll();

    int insertAll(List<Student>list);
}
