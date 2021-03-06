package com.lyldj.springboot.common.utils;

/**
 * @author: duanjian
 * @date: 17-11-21 下午4:55
 * @description: 文件操作工具类
 */
public class FileUtils {

    /**
     * 根据文件绝对路径获取目录
     * @param filePath 文件路径
     * @return
     */
    public static String getDir(String filePath) {
        if (StringUtils.isNotBlank(filePath)) {
            return filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        return "";
    }

    /**
     * 根据文件绝对路径获取文件
     * @param filePath 文件路径
     * @return
     */
    public static String getFileName(String filePath) {
        String file = "";
        if (StringUtils.isNotBlank(filePath)) {
            file = filePath.substring(filePath.lastIndexOf("/") + 1);
        }
        return file;
    }

    /**
     * 获取文件扩展名
     * @param filePath 文件路径
     * @return
     */
    public static String getExtension(String filePath) {
        String extend = "";
        if (StringUtils.isNotBlank(filePath)) {
            extend = filePath.substring(filePath.lastIndexOf(".") + 1);
        }
        return extend;
    }
}
