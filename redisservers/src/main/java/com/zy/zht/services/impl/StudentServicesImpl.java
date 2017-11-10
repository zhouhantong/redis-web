package com.zy.zht.services.impl;

import com.zy.zht.bean.Student;
import com.zy.zht.dao.StudentMapper;
import com.zy.zht.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/19.
 */
@Service
public class StudentServicesImpl implements StudentServices {
    @Autowired
    private StudentMapper dao;
    public int deleteByPrimaryKey(Integer stuId) {
        return dao.deleteByPrimaryKey(stuId);
    }

    public int insert(Student record) {
        return dao.insert(record);
    }

    public int insertSelective(Student record) {
        return dao.insertSelective(record);
    }

    public Student selectByPrimaryKey(Integer stuId) {
        return dao.selectByPrimaryKey(stuId);
    }

    public int updateByPrimaryKeySelective(Student record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Student record) {
        return dao.updateByPrimaryKey(record);
    }

    public List<Student> selectAll() {
        return dao.selectAll();
    }

    public int insertAll(List<Student>list) {
        return dao.insertAll(list);
    }
}
