package com.szaf.sdk.constant;

/**
 * @author yd
 *         <p>
 *         Created by yd on 2017/10/16.
 *         常量定义.
 */

public class AfSdkConstant {
    /**
     * 网络访问超时
     */
    public static final int ERROR_CODE_NETWORK_REQUEST_TIMEOUT = -500;
    /**
     * 接口错误
     */
    public static final int ERROR_CODE_ACCESS_INTERFACE_FAILED = -404;
    /**
     * 访问成功
     */
    public static final int RESPONSE_SUCCESS_CODE_JF = 9000;
    public static final int RESPONSE_SUCCESS_CODE_SD = 9001;
    public static final int RESPONSE_SUCCESS_CODE_YJ = 9002;
    public static final int RESPONSE_SUCCESS_CODE_OD = 9003;
    public static final int RESPONSE_SUCCESS_CODE_INSERT_LOG_ACTION = 9004;

    /**
     * 传递数据的Key
     */
    public static final String REQUEST_KSN_KEY = "ksn";
    public static final String REQUEST_CARD_NUMBER_KEY = "cardNumber";
    public static final String REQUEST_TRACK_RANDOM_KEY = "trackRandom";
    public static final String REQUEST_TRACK_DATA_KEY = "trackData";
    public static final String REQUEST_SERIALNUMBER_KEY = "serialNumber";
    public static final String REQUEST_ENCRYPT_DATA_KEY = "encryptData";
    /**
     * 返回响应数据Key
     */
    public static final String RESPONSE_RESULT_KEY = "result";
    public static final String RESPONSE_DETAIL_KEY = "detail";
    public static final String RESPONSE_MTK_KEY = "mtk";
    public static final String RESPONSE_CLASSIFY_INFO_KEY = "classifyInfo";

    /**
     * 网络错误描述信息
     */
    public static final String ERROR_INFO_NETWORK_REQUEST_TIMEOUT = "访问网络地址超时,请联系管理员";
    public static final String ERROR_INFO_ACCESS_INTERFACE_FAILED = "访问网络地址错误，请联系管理员";
    /**
     * 错误信息Key
     */
    public static final String RESPONSE_NETWORK_REQUEST_TIMEOUT_KEY = "netWorkRequestTimeOut";
    public static final String RESPONSE_ACCESS_INTERFACE_FAILED_KEY = "accessInterfaceFailed";

    /**
     * POS名称
     */
    public static final String POS_NAME_JF_KEY = "jiFu";
    public static final String POS_NAME_SD_KEY = "shAnDe";
    public static final String POS_NAME_YJ_KEY = "yinJia";
    public static final String POS_NAME_OD_KEY = "ouDa";
    /**
     * 保存日志动作
     */
    public static final String INSERT_LOG_ACTION = "insertLog";
    private static String successInfo;

    public static String getSuccessInfo() {
        return successInfo;
    }

    public static void setSuccessInfo(String successInfo) {
        AfSdkConstant.successInfo = successInfo;
    }

}