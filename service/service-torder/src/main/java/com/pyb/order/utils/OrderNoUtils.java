package com.pyb.order.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNoUtils {

    public static String OrderNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String time = format.format(date);
        StringBuffer result = new StringBuffer(time);
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int j = random.nextInt(10);
            result.append(j);
        }
        return result.toString();
    }
}
