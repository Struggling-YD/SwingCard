package com.szaf.sdk.listener;

import com.szaf.sdk.entity.ResponseResult;

/**
 * @author yd
 *         Created by yd on 2017/10/16.
 *         欧达   网络访问接口监听
 */

public interface OdSwingCardListener extends BaseSwingCardListener {
    /**
     * 访问接口成功并返回数据
     *
     * @param successResult 成功数据
     */
    void requestSuccess(ResponseResult successResult);
}
