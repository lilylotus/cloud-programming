package cn.nihility.cloud.common.result;

import java.io.Serializable;

public class CommonResult implements Serializable {

    private static final long serialVersionUID = -850308185839480940L;

    private int code;
    private Object data;
    private String message;

    public CommonResult() {
    }

    public CommonResult(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static CommonResult success(Object data, String message) {
        return new CommonResult(200, data, message);
    }

    public static CommonResult failure(int code, String message) {
        return new CommonResult(code, null, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
