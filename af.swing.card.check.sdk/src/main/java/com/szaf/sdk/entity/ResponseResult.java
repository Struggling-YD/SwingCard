package com.szaf.sdk.entity;

/**
 * @author yd
 *         返回公共响应结果        <p>
 *         Created by dell on 2017/10/27.
 */

public class ResponseResult {


    /**
     * result : 成功
     * detail : 6212251102001336333D260322089399
     */

    private String result;
    private String detail;

    public ResponseResult(String result, String detail) {
        this.result = result;
        this.detail = detail;
    }

    public String getResult() {
        return result;
    }


    public String getDetail() {
        return detail;
    }


}
