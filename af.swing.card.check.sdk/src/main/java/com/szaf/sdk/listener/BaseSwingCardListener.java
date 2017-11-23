package com.szaf.sdk.listener;

/**
 * @author yd
 *         Created by dell on 2017/10/18.
 */

public interface BaseSwingCardListener {
    /**
     * 网络请求超时
     *
     * @param timeOutInfo 超时描述
     */
    void netWorkRequestTimeOut(String timeOutInfo);

    /**
     * 访问接口失败
     *
     * @param accessInterFaceFailedInfo 访问接口失败描述
     */
    void accessInterfaceFailed(String accessInterFaceFailedInfo);
}
