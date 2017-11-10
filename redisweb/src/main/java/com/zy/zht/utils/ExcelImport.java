package com.zy.zht.utils;

import com.zy.zht.bean.Student;
import com.zy.zht.util.Excels;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/9/18.
 */
public class ExcelImport {
    public Collection importExcel(File file , Class pojoClass, String...  pattern) {

        Collection dist = new ArrayList();
        try {
            // 得到目标目标类的所有的字段列表
            Field filed[] = pojoClass.getDeclaredFields();
            // 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中
            Map<String,Method> fieldSetMap = new HashMap<String,Method>();

            Map<String,Method> fieldSetConvertMap = new HashMap<String,Method>();


            // 循环读取所有字段
            for (int i = 0; i < filed.length; i++) {
                Field f = filed[i];

                // 得到单个字段上的Annotation
                Excels excel = f.getAnnotation(Excels.class);

                // 如果标识了Annotationd的话
                if (excel != null) {
                    // 构造设置了Annotation的字段的Setter方法
                    String fieldname = f.getName();
                    String setMethodName = "set"
                            + fieldname.substring(0, 1).toUpperCase()
                            + fieldname.substring(1);
                    // 构造调用的method，
                    Method setMethod = pojoClass.getMethod(setMethodName,
                            new Class[] { f.getType() });
                    // 将这个method以Annotaion的名字为key来存入。

                    //对于重名将导致 覆盖失败，对于此处的限制需要
                    fieldSetMap.put(excel.exportName(), setMethod);

                    if(excel.importCovertSign()==1)
                    {
                        StringBuffer setConvertMethodName = new StringBuffer("set");
                        setConvertMethodName.append(fieldname.substring(0, 1)
                                .toUpperCase());
                        setConvertMethodName.append(fieldname.substring(1));
                        setConvertMethodName.append("Convert");
                        Method getConvertMethod = pojoClass.getMethod(setConvertMethodName.toString(),
                                new Class[] {String.class});
                        fieldSetConvertMap.put(excel.exportName(), getConvertMethod);
                    }

                }
            }





            // 将传入的File构造为FileInputStream;
            FileInputStream in = new FileInputStream(file);
            // // 得到工作表
            HSSFWorkbook book = new HSSFWorkbook(in);
            // // 得到第一页
            HSSFSheet sheet = book.getSheetAt(0);
            // // 得到第一面的所有行
            Iterator<Row> row = sheet.rowIterator();


            // 得到第一行，也就是标题行
            Row title = row.next();
            // 得到第一行的所有列
            Iterator<Cell> cellTitle = title.cellIterator();
            // 将标题的文字内容放入到一个map中。
            Map titlemap = new HashMap();
            // 从标题第一列开始
            int i = 0;
            // 循环标题所有的列
            while (cellTitle.hasNext()) {
                Cell cell = cellTitle.next();
                String value = cell.getStringCellValue();
                titlemap.put(i, value);
                i = i + 1;
            }


            //用来格式化日期的DateFormat
            SimpleDateFormat sf;
            if(pattern.length<1)
            {
                sf=new SimpleDateFormat("yyyy-MM-dd");
            }
            else
                sf=new SimpleDateFormat(pattern[0]);


            while (row.hasNext()) {
                // 标题下的第一行
                Row rown = row.next();

                // 行的所有列
                Iterator<Cell> cellbody = rown.cellIterator();

                // 得到传入类的实例
                Object tObject = pojoClass.newInstance();

                int k = 0;
                // 遍历一行的列
                while (cellbody.hasNext()) {
                    Cell cell = cellbody.next();
                    // 这里得到此列的对应的标题
                    String titleString = (String) titlemap.get(k);
                    // 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
                    if (fieldSetMap.containsKey(titleString)) {

                        Method setMethod = (Method) fieldSetMap.get(titleString);
                        //得到setter方法的参数
                        Type[] ts = setMethod.getGenericParameterTypes();
                        //只要一个参数
                        String xclass = ts[0].toString();
                        //判断参数类型
                        System.out.println("类型: "+xclass);

                        if (fieldSetConvertMap.containsKey(titleString)) {


                            fieldSetConvertMap.get(titleString).invoke(tObject,
                                    cell.getStringCellValue());


                        } else {
                            if (xclass.equals("class java.lang.String")) {
                                setMethod.invoke(tObject, cell
                                        .getStringCellValue());
                            }
                            else if (xclass.equals("class java.util.Date")) {
                                setMethod.invoke(tObject, cell
                                        .getDateCellValue());
                            }
                            else if (xclass.equals("class java.lang.Boolean")) {
                                setMethod.invoke(tObject, cell
                                        .getBooleanCellValue());
                            }
                            else if (xclass.equals("class java.lang.Integer")) {
                                Double n=cell.getNumericCellValue();
                                Integer num=new Double(n).intValue();
                               // System.out.println(num);
                                setMethod.invoke(tObject,num);
                            }else if(xclass. equals("class java.lang.Long"))
                            {
                                setMethod.invoke(tObject,new Long( cell.getStringCellValue()));
                            }
                        }
                    }
                    // 下一列
                    k = k + 1;
                }
                dist.add(tObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return dist;
    }

    public static void main(String[] args) {
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

        System.out.println("共转化为List的行数为：" + result.size());
    }

}
