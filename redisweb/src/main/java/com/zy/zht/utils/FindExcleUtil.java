package com.zy.zht.utils;

/**
 * Created by Administrator on 2017/9/18.
 * 工具类验证Excel文档
 */
public class FindExcleUtil {
    /**
     * 是否是2003的excel，返回true则表示是2003
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath){
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 是否是2007的excel,返回true是2007
     * @param filePath
     * @return
     */
    public static  boolean isExcel2007(String filePath){
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证是否是excel文件
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath)) || !(isExcel2007(filePath))) {
            return false;
        }
        return true;
    }
}
