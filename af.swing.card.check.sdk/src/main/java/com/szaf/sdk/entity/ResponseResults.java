package com.szaf.sdk.entity;

/**
 * 银嘉/即富刷卡效验结果
 *
 * @author yd
 *         Created by yd on 2017/10/12.
 */

public class ResponseResults extends ResponseResult {


    /**
     * result : 失败
     * mtk : B87DA44E3811D9853B542C179F083720
     * detail : 534ee2c3f14231a77bc472e90b58b2f933041b7d8e8e9cb5
     */
    private String mtk;

    public ResponseResults(String result, String detail, String mtk) {
        super(result, detail);
        this.mtk = mtk;
    }

    public String getMtk() {
        return mtk;
    }

}
