package com.university.crm.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/**
 * date: 2019/3/15
 * description :
 *
 * @author : zhencai.cheng
 */
public class HashKit {

    /**
     * 随机字母数字
     *
     * @param length 长度
     * @return 随机字母数字
     */
    public static String getCharAndNum(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) { // 字符串
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                val.append((char) (choice + random.nextInt(26)));
            } else { // 数字
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    /**
     * 两次md5加salt散列
     *
     * @param source 源
     * @param salt   salt
     * @return 散列值
     */
    public static String md5HashAddSalt(String source, String salt) {
        return md5Hash(md5Hash(source) + salt).toUpperCase();
    }

    /**
     * md5散列，使用apache commons包实现，返回散列后的大写字母
     *
     * @param source 源
     * @return 散列值
     */
    public static String md5Hash(String source) {
        return DigestUtils.md5Hex(source).toUpperCase();
    }


    private static Random random = new Random();

    public static String generateRandom(int len) {

        String s = "0123456789";
        if (len <= 0) {
            return "";
        }
        char[] randomChars = new char[len];
        for (int i = 0; i < len; i++) {
            randomChars[i] = s.charAt(random.nextInt(s.length()));
        }
        return String.valueOf(randomChars);
    }
}