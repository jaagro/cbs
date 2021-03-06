package com.jaagro.cbs.api.exception;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 业务异常
 * @author yj
 * @date 2019/3/27 9:29
 */
@Data
@Accessors(chain = true)
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 9045237586542073761L;
    public static final int FAIL = 500;
    private int code;
    private String message;
    public BusinessException(){
        super();
    }
    public BusinessException(String message){
        this.code = FAIL;
        this.message = message;
    }
    public BusinessException(int code,String message){
        this.code = code;
        this.message = message;
    }

}
