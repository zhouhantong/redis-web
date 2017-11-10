package com.zy.zht.utils;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.util.StringUtil;


import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */
public class ImportExcelUtil {
    final static String notnullerror="请填入第{0}行的{1},{2}不能为空";
    final static String errormsg="第{0}行的{1}数据导入错误";
    /**
     * 导入Excel
     */
    public  static List importExcel(Class<?>clazz, InputStream xls)throws Exception{
        try {
            //取得Excel
            HSSFWorkbook wb=new HSSFWorkbook(xls);
            HSSFSheet sheet=wb.getSheetAt(0);
            Field [] fields=clazz.getDeclaredFields();
            List<Field> fieldList=new ArrayList<Field>(fields.length);
            for (Field field:fields) {
                if (field.isAnnotationPresent(ModelProp.class)){
                    ModelProp modelProp=field.getAnnotation(ModelProp.class);
                    if (modelProp.colIndex()!=-1){
                        fieldList.add(field);
                    }
                }
            }
            List<ImportModel> modelList=new ArrayList<ImportModel>(sheet.getPhysicalNumberOfRows()*2);
            for (int i=2;i<sheet.getPhysicalNumberOfRows();i++){
                //数据模型
                ImportModel model=(ImportModel)clazz.newInstance();
                int nullCount=0;
                Exception nullError=null;
                for (Field field:fieldList) {
                    ModelProp modelProp=field.getAnnotation(ModelProp.class);
                    HSSFCell cell=sheet.getRow(i).getCell(modelProp.colIndex());
                   try {
                       if (cell==null||cell.toString().length()==0){
                           nullCount++;
                           if (!modelProp.nullable()){
                               nullError = new Exception(StringUtil.format(notnullerror,
                                       new String[]{"" + (1 + i), modelProp.name(),
                                               modelProp.name()}));
                           }
                       }else if (field.getType().equals(Date.class)){
                           if (Cell.CELL_TYPE_STRING==cell.getCellType()){
                               BeanUtils.setProperty(model,field.getName(),new Date(Date.parse(cell.toString())));
                           }else {
                               BeanUtils.setProperty(model,field.getName(),new Date(cell.getDateCellValue().getTime()));
                           }
                       }else if (field.getType().equals(Timestamp.class)){
                           if(Cell.CELL_TYPE_STRING==cell.getCellType()){
                               BeanUtils.setProperty(model,field.getName(),new Timestamp(Date.parse(cell.toString())));
                           }else {
                              BeanUtils.setProperty(model, field.getName(),
                                new Timestamp(cell.getDateCellValue().getTime()));

                           }
                       }else if (field.getType().equals(java.sql.Date.class)) {
                          if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                               BeanUtils.setProperty(model, field.getName(),
                                new java.sql.Date(Date.parse(cell.toString())));
                          } else {
                               BeanUtils.setProperty(model, field.getName(),
                                new java.sql.Date(cell.getDateCellValue().getTime()));
                               }
                       } else if (field.getType().equals(java.lang.Integer.class)) {
                            if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                            BeanUtils.setProperty(model, field.getName(), (int) cell.getNumericCellValue());
                       } else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                               BeanUtils.setProperty(model, field.getName(), Integer.parseInt(cell.toString()));
                                }
                       } else if (field.getType().equals(java.math.BigDecimal.class)) {
                            if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                                BeanUtils.setProperty(model, field.getName(),
                                 new BigDecimal(cell.getNumericCellValue()));
                       } else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                             BeanUtils.setProperty(model, field.getName(), new BigDecimal(cell.toString()));
                              }
                        } else {
                           if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                             BeanUtils.setProperty(model, field.getName(),
                                  new BigDecimal(cell.getNumericCellValue()));
                               } else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                                BeanUtils.setProperty(model, field.getName(), cell.toString());
                              }
                            }

                   }catch (Exception e){
                       e.printStackTrace();
                       throw new Exception(StringUtil.format(errormsg, new String[] { "" + (1 + i), modelProp.name() })
                               + "," + e.getMessage());
                   }
                }
                if (nullCount==fieldList.size()){
                    break;
                }
                if (nullError!=null){
                    throw nullError;
                }
                modelList.add(model);
            }
            return modelList;
        }finally {
            xls.close();
        }
    }
}
