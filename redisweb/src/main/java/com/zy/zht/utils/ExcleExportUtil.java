package com.zy.zht.utils;

import com.zy.zht.bean.Student;
import com.zy.zht.util.Excels;
import jdk.internal.dynalink.beans.StaticClass;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Administrator on 2017/9/18.
 */
public class ExcleExportUtil {
    public static void exportExcel(String title, Class pojoClass, Collection dataSet, OutputStream out){
        //使用userModel模式实现的，当excel文档出现10万级别的大数据文件可能导致OOM内存溢出
        exportExcelInModel(title,pojoClass,dataSet,out);
        //使用eventModel实现，可以一边读一边处理，效率较高，但是实现复杂，暂时未实现
    }
    private static void exportExcelInModel(String title,Class pojoClass,Collection dataSet,OutputStream out){
        try{
            //首先检查数据看是否是正确的
            if(dataSet==null||dataSet.size()==0){
                throw new Exception("导出数据为空！");
            }
            if(title == null || out == null || pojoClass == null) {
                throw new Exception("传入参数不能为空！");
            }
            // 声明一个工作薄
            Workbook workbook = new HSSFWorkbook();
// 生成一个表格
            Sheet sheet = workbook.createSheet(title);


// 标题
            List<String> exportFieldTitle = new ArrayList<String>();
            List<Integer> exportFieldWidth = new ArrayList<Integer>();
// 拿到所有列名，以及导出的字段的get方法
            List<Method> methodObj = new ArrayList<Method>();
            Map<String,Method> convertMethod = new HashMap<String,Method>();
// 得到所有字段
            Field fileds[] = pojoClass.getDeclaredFields();
// 遍历整个filed
            for (int i = 0; i < fileds.length; i++) {
                Field field = fileds[i];
                Excels excel = field.getAnnotation(Excels.class);
// 如果设置了annottion
                if (excel != null) {
// 添加到标题
                    exportFieldTitle.add(excel.exportName());
//添加标题的列宽
                    exportFieldWidth.add(excel.exportFieldWidth());
// 添加到需要导出的字段的方法
                    String fieldname = field.getName();
//System.out.println(i+"列宽"+excel.exportName()+" "+excel.exportFieldWidth());
                    StringBuffer getMethodName = new StringBuffer("get");
                    getMethodName.append(fieldname.substring(0, 1)
                            .toUpperCase());
                    getMethodName.append(fieldname.substring(1));


                    Method getMethod = pojoClass.getMethod(getMethodName.toString(),
                            new Class[] {});


                    methodObj.add(getMethod);
                    if(excel.exportConvertSign()==1)
                    {
                        StringBuffer getConvertMethodName = new StringBuffer("get");
                        getConvertMethodName.append(fieldname.substring(0, 1)
                                .toUpperCase());
                        getConvertMethodName.append(fieldname.substring(1));
                        getConvertMethodName.append("Convert");
//System.out.println("convert: "+getConvertMethodName.toString());
                        Method getConvertMethod = pojoClass.getMethod(getConvertMethodName.toString(),
                                new Class[] {});
                        convertMethod.put(getMethodName.toString(), getConvertMethod);
                    }
                }
            }
            int index = 0;
// 产生表格标题行
            Row row = sheet.createRow(index);
            for (int i = 0,exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
                Cell cell = row.createCell(i);
                //  cell.setCellStyle(style);
                RichTextString text = new HSSFRichTextString(
                        exportFieldTitle.get(i));
                cell.setCellValue(text);
            }


//设置每行的列宽
            for (int i = 0; i < exportFieldWidth.size(); i++) {
//256=65280/255
                sheet.setColumnWidth(i, 256*exportFieldWidth.get(i));
            }
            Iterator its = dataSet.iterator();

// 循环插入剩下的集合
            while (its.hasNext()) {
// 从第二行开始写，第一行是标题
                index++;
                row = sheet.createRow(index);
                Object t = its.next();
                for (int k = 0, methodObjSize = methodObj.size(); k < methodObjSize; k++) {
                    CellStyle contextstyle = workbook.createCellStyle();
                    int n=0;
                    Cell  cell = row.createCell(k);
                    Method getMethod = methodObj.get(k);
                    Object value = null;
                        if (convertMethod.containsKey(getMethod.getName())) {
                            Method cm = convertMethod.get(getMethod.getName());
                            value = cm.invoke(t, new Object[]{});
                            n=isDate(value);
                            System.out.println("参数名称："+cm.getName()+",参数类型："+cm.getReturnType()+",操作码："+n);
                        } else {
                            value = getMethod.invoke(t, new Object[]{});
                            n=isDate(value);
                            System.out.println("参数名称："+getMethod.getName()+",参数类型："+getMethod.getReturnType()+",操作码："+n);
                        }
                    //如果单元格内容是数值类型，涉及到金钱（金额、本、利），则设置cell的类型为数值型，设置data的类型为数值类型
                    if (n==1||n==2) {
                        DataFormat df = workbook.createDataFormat(); // 此处设置数据格式
                        if (n==1) {
                            contextstyle.setDataFormat(df.getFormat("#,#0"));//数据格式只显示整数
                        }else{
                            contextstyle.setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点
                        }
                        // 设置单元格格式
                        cell.setCellStyle(contextstyle);
                        // 设置单元格内容为double类型
                        cell.setCellValue(Double.parseDouble(value.toString()));
                    } else if(n==3){
                        cell.setCellStyle(contextstyle);
                        // 设置单元格内容为字符型
                        cell.setCellValue(value.toString());
                    }

                        //cell.setCellValue(value.toString());
                    }

            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int isDate(Object value){
        int n=0;
        boolean isNum=false;//data是否为数值型
        boolean isInterger=false;//data是否为整数
        boolean isPercent=false;//data是否为百分数

        if (value!=null||"".equals(value)){
            //判断data是否为数值型
            isNum = value.toString().matches("^(-?\\d+)(\\.\\d+)?$");
            //判断data是否为整数（小数部分是否为0）
            isInterger=value.toString().matches("^[-\\+]?[\\d]*$");
            //判断data是否为百分数（是否包含“%”）
            isPercent=value.toString().contains("%");

        }
        if(isNum&isInterger){
            if(isInterger){
                return n+1;
            }
            return n+2;
        }
        return n+3;
    }

    public static void main(String[] args) throws Exception {


// 构造一个模拟的List来测试，实际使用时，这个集合用从数据库中查出来
        Student pojo2 = new Student();
        pojo2.setStuName("第一行数据");
        pojo2.setStuAge(28);
        pojo2.setStuSex(2);
        pojo2.setStuComment("abcdefghijklmnop");
        pojo2.setStuBirsday((new Date()));
        pojo2.setStuIsVip(1);
        List list = new ArrayList();
        list.add(pojo2);
        for (int i = 0; i < 50000; i++) {
            Student pojo = new Student();
            pojo.setStuName("一二三四五六七八九");
            pojo.setStuAge(22);
            pojo.setStuSex(1);
            pojo.setStuComment("abcdefghijklmnop");
            pojo.setStuBirsday((new Date()));
            pojo.setStuIsVip(0);
            list.add(pojo);
        }
// 构造输出对象，可以从response输出，直接向用户提供下载
        OutputStream out = new FileOutputStream("D://testOne.xls");
// 开始时间
        Long l = System.currentTimeMillis();
// 注意
        ExcleExportUtil ex = new ExcleExportUtil();
//
        ex.exportExcel("测试",Student.class,list, out);
        out.close();
// 结束时间
        Long s = System.currentTimeMillis();
        System.out.println("excel导出成功");
        System.out.println("总共耗时：" + (s - l));


    }
}
