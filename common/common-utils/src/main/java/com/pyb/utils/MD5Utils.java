package com.pyb.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class MD5Utils {

    /**
     * MD5加密类
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String code(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*和用户密码拼接，提交安全性*/
    private static final String salt = "1a2b3c4d";
    /*对客户端的密码进行MD5加密*/
    private static String transferInputToForm(String password){
        /*拼接*/
        String formPass = ""+salt.charAt(1)+password+salt.charAt(3)+salt.charAt(6);
        return code(formPass);
    }

    /*对数据库端的密码进行二次MD5加密*/
    private static String transferFormToDb(String formPass){
        /*拼接*/
        String dbPass = ""+salt.charAt(1)+formPass+salt.charAt(3)+salt.charAt(6);
        return code(dbPass);
    }

    /*把登录模块表单输入的密码加密转化为数据库密码*/
    public static String transferInputToDb(String password){

        String formPass = transferInputToForm(password);
        String dbPass = transferFormToDb(formPass);
        return dbPass;
    }


    /*SpringSecurity加密的方案*/
    public static String encrypt(String strSrc) {
        try {
            char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            byte[] bytes = strSrc.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);
        }
    }

    public static void main(String[] args) {
        System.out.println(transferInputToDb("1997"));
    }

}
