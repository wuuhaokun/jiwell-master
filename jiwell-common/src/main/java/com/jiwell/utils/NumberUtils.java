package com.jiwell.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 98050
 * @create: 2018-04-25 09:13
 **/
public class NumberUtils {

    public static boolean isInt(Double num) {
        return num.intValue() == num;
    }

    /**
     * 判斷字符串是否是數值格式
     * @param str
     * @return
     */
    public static boolean isDigit(String str){
        if(str == null || str.trim().equals("")){
            return false;
        }
        return str.matches("^\\d+$");
    }

    /**
     * 將一個小數精確到指定位數
     * @param num
     * @param scale
     * @return
     */
    public static double scale(double num, int scale) {
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 從字符串中根據正則表達式尋找，返回找到的數字數組
     * @param value
     * @param regex
     * @return
     */
    public static Double[] searchNumber(String value, String regex){
        List<Double> doubles = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if(matcher.find()) {
            MatchResult result = matcher.toMatchResult();
            for (int i = 1; i <= result.groupCount(); i++) {
                doubles.add(Double.valueOf(result.group(i)));
            }
        }
        return doubles.toArray(new Double[doubles.size()]);
    }

    /**
     * 生成指定位數的隨機數字
     * @param len
     * @return
     */
    public static String generateCode(int len){
        len = Math.min(len, 8);
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, len + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0,len);
    }
}
