package com.zy.zht.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 * 工具类读取excel类中的内容
 */
public class ReadExcel {
    //总行数
    private int totalRows=0;
    //总列数
    private int totalCells=0;
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcel(){}
    //获取总行数
    public int getTotalRows(){
        return  totalRows;
    }
    //获取总列数
    public int getTotalCells() {
        return totalCells;
    }
    //获取错误信息
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 读excel文件，获取客户信息集合
     */
    public List<Object> getExcelInfo(MultipartFile mfile,String path){
        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
        CommonsMultipartFile cf=(CommonsMultipartFile)mfile;
        //获取本地存储路径
        File file=new File(path);
        //创建一个目录(它的路径由当前File对象指定，包括任一必须的父路径。)
        if (!(file.exists())){
            file.mkdirs();
        }
        //新建一个文件
        File file1=new File(path+new Date().getTime()+".xls");
        //将上传文件写入新建的文件中
        try{
            cf.getFileItem().write(file1);
        }catch (Exception e){
            e.printStackTrace();
        }
        //初始化客户信息的集合

        return  null;
    }
}
