package com.zy.zht.controller;

import com.zy.zht.bean.Student;
import com.zy.zht.services.StudentServices;
import com.zy.zht.utils.ExcelImport;
import com.zy.zht.utils.ExcleExportUtil;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */
@Controller
public class StudentController {
    @Autowired
    private StudentServices services;
    @RequestMapping("upload.do")
    public String uploadStudent()throws Exception{
            OutputStream out = new FileOutputStream("D://testOne.xls");
            List<Student>list=services.selectAll();
            // 开始时间
            Long l = System.currentTimeMillis();
            ExcleExportUtil.exportExcel("学生信息",Student.class,list,out);
            out.close();
            // 结束时间
            Long s = System.currentTimeMillis();
            System.out.println("excel导出成功");
            System.out.println("总共耗时：" + (s - l));
            out.flush();
            out.close();
        return "";
    }
    @RequestMapping("importStudent.do")
    public String importStudent(){
        ExcelImport test = new ExcelImport();
        File file = new File("D://testOne.xls");
        Long befor = System.currentTimeMillis();
        List<Student> result = (ArrayList) test.importExcel(file,Student.class);

        Long after = System.currentTimeMillis();
        System.out.println("此次操作共耗时：" + (after - befor) + "毫秒");

        for (int i = 0; i < result.size(); i++) {
            Student testpojo=result.get(i);
            System.out.println("导入的信息为："+testpojo.getStuName()+
                    "--"+testpojo.getStuAge()+"-"+testpojo.getStuSex()+"--"+testpojo.getStuBirsday()+"--"+testpojo.getStuComment()+"--"+testpojo.getStuIsVip());
        }

        int n=services.insertAll(result);
        if(n>0) {
            System.out.println("共转化为List的行数为：" + result.size() + ",添加成功！===" + ",影响行数" + n);
        }
        return "";
    }
}
