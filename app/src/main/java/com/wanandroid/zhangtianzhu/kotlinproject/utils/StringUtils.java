package com.wanandroid.zhangtianzhu.kotlinproject.utils;

public class StringUtils {
    /**
     * 将带万的转换成数字字符串<br>
     *
     * @param data
     * @return String<br>
     */
    public static String getNumFromFormat(String data) {
        String endData = "0";
        if (data != null) {
            if (data.contains("万")) {
                endData = data.substring(0, data.length() - 1);
                endData = String.valueOf(Float.parseFloat(endData) * 10000);
            }  else {
                endData = data;
            }
            if (endData.contains(".")) {
                endData = endData.substring(0, endData.indexOf("."));
            }
        }
        return endData;
    }
}
