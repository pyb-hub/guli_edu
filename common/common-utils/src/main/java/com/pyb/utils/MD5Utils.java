package com.pyb.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/*服务端的密码加密存储到数据库*/
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

    /*把输入的密码转化为数据库密码*/
    public static String transferInputToDb(String password){

        String formPass = transferInputToForm(password);
        String dbPass = transferFormToDb(formPass);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(transferInputToDb("1997"));
    }

}
