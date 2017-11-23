package com.szaf.sdk.interfacefactory;

import com.szaf.sdk.entity.CheckSwingCardLog;

/**
 * @author yd
 *         Created by yd on 2017/10/20.
 *         刷卡效验接口
 */

public interface CheckSwingCardFactory {

    /**
     * 杉德刷卡效验
     *
     * @param ksn        KSN号
     * @param cardNumber 卡号
     * @param trackData  二磁信息
     */
    void sdRequestBody(String ksn, String cardNumber, String trackData);

    /**
     * 即富刷卡交易返回效验结果
     *
     * @param ksn          设备号
     * @param cardNumber   卡号
     * @param trackRandom  磁道随机数
     * @param serialNumber 交易序列号
     * @param encryptData  加密磁道数据
     * @return SwingCardResult
     */
    void jfRequestBody(String ksn, String cardNumber, String trackRandom, String serialNumber,
                       String encryptData);

    /**
     * 银嘉刷卡交易效验结果
     *
     * @param ksn          设备号
     * @param cardNumber   卡号
     * @param trackRandom  磁道随机数
     * @param serialNumber 交易随机数
     * @param encryptData  加密磁道数据
     */
    void yjRequestBody(String ksn, String cardNumber, String trackRandom, String serialNumber, String encryptData);

    /**
     * 欧达刷卡交易效验结果
     *
     * @param ksn        设备号
     * @param cardNumber 卡号
     * @param trackData  二磁信息
     */
    void odRequestBody(String ksn, String cardNumber, String trackData);

    /**
     * 保存刷卡Log到数据库
     *
     * @param log
     */
    void saveCheckSwingCardLog(CheckSwingCardLog log);


}
