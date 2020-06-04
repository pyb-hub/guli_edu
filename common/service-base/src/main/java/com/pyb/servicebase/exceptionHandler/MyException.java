package com.pyb.servicebase.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*自定义异常类*/
@Data/*生成set，get，equals，hashcode，toString方法*/
@AllArgsConstructor/*有参数的构造方法*/
@NoArgsConstructor/*无参数构造方法*/
public class MyException extends RuntimeException{

    /*异常的二个属性，状态码和异常信息*/
    private Integer code;
    private String msg;
}
