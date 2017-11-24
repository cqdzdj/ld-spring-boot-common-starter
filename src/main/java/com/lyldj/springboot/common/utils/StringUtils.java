package com.lyldj.springboot.common.utils;

import org.joda.time.DateTime;

import java.util.Random;

/**
 * @author: duanjian
 * @date: 17-11-21 上午10:31
 * @description: 字符串工具类
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

    public static final String DEFAULT_CHARSET = "utf-8";

    private static final String LETTER_AND_NUMBER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    private static final String NUMBER = "1234567890";

    /**
     * 获取随机字符串(包含字母和数字)
     * @param length 长度
     * @return
     */
    public static String getRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        int len = LETTER_AND_NUMBER.length() - 1;
        for (int i = 0; i < length; i++) {
            int index = new Random(len).nextInt();
            builder.append(LETTER_AND_NUMBER.charAt(index));
        }
        return builder.toString();
    }

    /**
     * 获取随机字符串(仅包含数字)
     * @param length 长度(大于0)
     * @return
     */
    public static String getRandomNumberString(int length) {
        StringBuilder builder = new StringBuilder();
        int len = NUMBER.length() - 1;
        for (int i = 0; i < length; i++) {
            int index = new Random(len).nextInt();
            builder.append(NUMBER.charAt(index));
        }
        return builder.toString();
    }

    /**
     * 将null转换成空串
     * @param str
     * @return
     */
    public static String nullToEmpty(String str) {
        if(str == null) {
            return "";
        }
        return str;
    }

    /**
     * 获取流水号(前缀+时间串+随机字符串)
     * @param prefix 流水号前缀
     * @param randomLength 随机串长度
     * @return
     */
    public static String getSerialNo(String prefix, int randomLength) {
        StringBuilder builder = new StringBuilder();
        builder.append(nullToEmpty(prefix))
                .append(new DateTime().toString(DateUtils.SHORT_DATE_TIME_MILLISECOND_NO_PATTERN))
                .append(randomLength > 0 ? getRandomNumberString(randomLength) : "");
        return builder.toString();
    }

}
