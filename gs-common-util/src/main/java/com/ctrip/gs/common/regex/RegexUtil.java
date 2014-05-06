package com.ctrip.gs.common.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author: wgji
 * @date：2014年4月1日 上午11:37:54
 * @comment:
 */
public class RegexUtil {
    /**
     * 判断字符串是不是数字
     * 
     * @param str
     * @return
     */
    public static Boolean checkNumber(String str) {
        return !StringUtils.isBlank(str) && str.matches("^\\d+$");
    }

    /**
     * 正则验证是否为英文单词，也就是纯大小写A-z字母组成
     * 
     * @param word
     * @return
     */
    public static Boolean checkEnglishWord(String word) {
        if (StringUtils.isBlank(word)) {
            return false;
        }
        return checkRegex("[a-zA-Z]+", word);
    }

    /**
     * 根据指定的正则表达式验证字符串
     * 
     * @param regex
     *            正则表达式
     * @param str
     *            检验内容
     * @return
     */
    public static Boolean checkRegex(String regex, String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 验证正整数
     * 
     * @param str
     *            检验内容
     * @param min
     *            最小长度
     * @param max
     *            最大长度
     * @return
     */
    public static Boolean checkPositive(String str, int min, int max) {
        return checkRegex("^\\d{" + min + "," + max + "}$", str);
    }

    /**
     * 验证正整数
     * 
     * @param str
     *            检验内容
     * @param length
     *            整数的长度
     * @return
     */
    public static Boolean checkPositive(String str, int length) {
        return checkRegex("^\\d{" + length + "}$", str);
    }

    /**
     * 目的地的英文明处理，去除里面的所有其他字符，只保证a-z字母，同时全转小写
     * 
     * @param name
     * @return
     */
    public static String districtENameHandler(String enName) {
        String result = "";
        if (!StringUtils.isBlank(enName)) {
            enName = enName.replace(" ", "");// 空格需转成
            // 仅保留允许的字符
            result = Pattern.compile("[^a-zA-Z]+").matcher(enName).replaceAll("");
        }
        return result.toLowerCase();
    }
}
