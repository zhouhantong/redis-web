package com.zy.zht.dao;

import com.zy.zht.bean.Excel;

public interface ExcelMapper {
    int deleteByPrimaryKey(Integer pkid);

    int insert(Excel record);

    int insertSelective(Excel record);

    Excel selectByPrimaryKey(Integer pkid);

    int updateByPrimaryKeySelective(Excel record);

    int updateByPrimaryKey(Excel record);
}