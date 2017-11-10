package com.zy.zht.bean;

import com.zy.zht.util.Excels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {
    @Excels(exportName = "编号",exportFieldWidth = 4,exportConvertSign = 0,importCovertSign = 0)
    private Integer stuId;
    @Excels(exportName = "姓名",exportFieldWidth = 18,exportConvertSign = 0,importCovertSign = 0)
    private String stuName;
    @Excels(exportName = "年龄",exportFieldWidth = 4,exportConvertSign = 0,importCovertSign = 0)
    private Integer stuAge;
    @Excels(exportName = "性别",exportFieldWidth = 4,exportConvertSign = 1,importCovertSign = 1)
    private Integer stuSex;
    @Excels(exportName = "出生日期",exportFieldWidth = 40,exportConvertSign = 1,importCovertSign = 1)
    private Date stuBirsday;
    @Excels(exportName = "描述",exportFieldWidth = 30,exportConvertSign = 0,importCovertSign = 0)
    private String stuComment;
    @Excels(exportName = "是否VIP",exportFieldWidth = 7,exportConvertSign = 1,importCovertSign = 1)
    private Integer stuIsVip;

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName == null ? null : stuName.trim();
    }

    public Integer getStuAge() {
        return stuAge;
    }

    public void setStuAge(Integer stuAge) {
        this.stuAge = stuAge;
    }

    public Integer getStuSex() {
        return stuSex;
    }

    public void setStuSex(Integer stuSex) {
        this.stuSex = stuSex;
    }

    public Date getStuBirsday() {
        return stuBirsday;
    }

    public void setStuBirsday(Date stuBirsday) {
        this.stuBirsday = stuBirsday;
    }

    public String getStuComment() {
        return stuComment;
    }

    public void setStuComment(String stuComment) {
        this.stuComment = stuComment == null ? null : stuComment.trim();
    }

    public Integer getStuIsVip() {
        return stuIsVip;
    }

    public void setStuIsVip(Integer stuIsVip) {
        this.stuIsVip = stuIsVip;
    }

    /**
     * 性别转换
     * @return
     */
    public String getStuSexConvert(){
        if (stuSex==1){
            return "男";
        }else if(stuSex==2){
            return "女";
        }else {
            return "";
        }
    }
    public void setStuSexConvert(String text){
        if ("男".equals(text)){
            stuSex=1;
        }else if("女".equals(text)){
            stuSex=2;
        }
    }
    /**
     * 生日转换
     */
    public String getStuBirsdayConvert(){
        if (stuBirsday==null){
            return "";
        }
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sf.format(stuBirsday);
    }
    public void  setStuBirsdayConvert(String text){
        if (text!=null){
          SimpleDateFormat sf=new  SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                stuBirsday= sf.parse(text);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(stuBirsday);
        }
    }

    public String getStuIsVipConvert(){
        if (stuIsVip==1){
            return "是";
        }else if (stuIsVip==0){
            return "否";
        }else {
            return "";
        }
    }
    public void setStuIsVipConvert(String text){
        if ("是".equals(text)){
            stuIsVip=1;
        }else if ("否".equals(text)){
            stuIsVip=0;
        }
    }

    public static void main(String[] args) {
        Student stu=new Student();
        stu.setStuBirsday(new Date());
        System.out.println(stu.getStuBirsdayConvert());
    }

}