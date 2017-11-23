package com.szaf.sdk.listener;

import com.szaf.sdk.entity.ResponseResults;

/**
 * @author yd
 *         Created by yd on 2017/10/16.
 *         银嘉  网络访问接口监听
 */

public interface YjSwingCardListener extends BaseSwingCardListener {
    /**
     * 访问接口成功并返回数据
     *
     * @param successResult 成功数据
     */
    void requestSuccess(ResponseResults successResult);
}
