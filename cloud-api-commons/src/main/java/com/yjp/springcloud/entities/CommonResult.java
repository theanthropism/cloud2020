package com.yjp.springcloud.entities;

import com.yjp.springcloud.Enums.JsonResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: CommonResult
 * Description:
 * date: 2020/12/17 13:57
 *
 * @author yan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private String code;
    private String message;
    private T data;

    public CommonResult(String code,String message){
        this(code,message,null);
    }

    public static CommonResult seccess() {
        return seccess(JsonResultEnum.A00000.getMessage());
    }

    public static CommonResult seccess(String message) {
        return seccess(JsonResultEnum.A00000.getCode(), message);
    }

    public static CommonResult seccess(String code, String message) {
        return new CommonResult(code, message);
    }

    public static CommonResult error() {
        return error(JsonResultEnum.A1006.getMessage());
    }

    public static CommonResult error(String message) {
        return error(JsonResultEnum.A1006.getCode(), message);
    }

    public static CommonResult error(String code, String message) {
        return new CommonResult(code, message);
    }

    public static CommonResult error(String code, String message, Object data) {
        return new CommonResult(code, message, data);
    }
}
