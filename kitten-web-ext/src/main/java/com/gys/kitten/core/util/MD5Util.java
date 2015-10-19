package com.gys.kitten.core.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Description MD5加密类
 *
 * @author kitten
 * @CreateData 20120401
 * @modifyList
 */
public class MD5Util {

    public static String toMd5(String sg) {
        return strTO(sg);
    }

    public static String toMd5(String sg1, String sg2) {
        return strTo(sg1, sg2);
    }

    public static String toMd5(Integer itg) {
        return intTo(itg);
    }

    public static String toMd5(Double dble) {
        return doubleTo(dble);
    }

    public static String toMd5(Float flt) {
        return floatTo(flt);
    }

    public static String toMd5(Long lng) {
        return longTo(lng);
    }

    private static String _md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    private static String strTO(String sg) {
        return _md5(sg);
    }

    private static String strTo(String sg1, String sg2) {
        return _md5((new StringBuilder()).append(sg1).append(sg2).toString().toString());
    }

    private static String intTo(Integer itg) {
        return _md5(itg.toString());
    }

    private static String doubleTo(Double dble) {
        return _md5(dble.toString());
    }

    private static String floatTo(Float flt) {
        return _md5(flt.toString());
    }

    private static String longTo(Long lng) {
        return _md5(lng.toString());
    }

    public static void main(String[] args) {
        String aaa = "E99A18C428CB38D5F260853678922E03";
        String bbb = DigestUtils.md5Hex(aaa);
        System.out.println(bbb);
    }
}
