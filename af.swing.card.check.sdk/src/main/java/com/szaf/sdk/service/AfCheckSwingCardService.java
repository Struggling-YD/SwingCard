package com.szaf.sdk.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.szaf.sdk.constant.AfSdkConstant;
import com.szaf.sdk.entity.CheckSwingCardLog;
import com.szaf.sdk.entity.CheckSwingCardLogResult;
import com.szaf.sdk.entity.ResponseResult;
import com.szaf.sdk.entity.ResponseResults;
import com.szaf.sdk.interfacefactory.CheckSwingCardFactory;
import com.szaf.sdk.listener.InsertCheckSwingCardLogListener;
import com.szaf.sdk.listener.JfSwingCardListener;
import com.szaf.sdk.listener.OdSwingCardListener;
import com.szaf.sdk.listener.SdSwingCardListener;
import com.szaf.sdk.listener.YjSwingCardListener;
import com.szaf.sdk.utils.HandleUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.szaf.sdk.constant.AfSdkConstant.RESPONSE_SUCCESS_CODE_INSERT_LOG_ACTION;
import static com.szaf.sdk.constant.AfSdkConstant.RESPONSE_SUCCESS_CODE_OD;
import static com.szaf.sdk.constant.AfSdkConstant.RESPONSE_SUCCESS_CODE_YJ;
import static com.szaf.sdk.constant.AfSdkConstant.getSuccessInfo;
import static com.szaf.sdk.constant.AfSdkConstant.setSuccessInfo;
import static com.szaf.sdk.utils.GsonUtil.responseSuccess;

/**
 * @author yd
 *         Created by yd on 2017/10/12.
 *         刷卡效验
 */

public class AfCheckSwingCardService implements CheckSwingCardFactory {
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final String TAG = "AfSwingCardController";
    private JfSwingCardListener jfListener;
    private SdSwingCardListener sdListener;
    private YjSwingCardListener yjListener;
    private OdSwingCardListener odListener;
    private InsertCheckSwingCardLogListener insertLogListener;
    private static Map<String, String> map = new HashMap<>(16);
    private String netWorkUrl;
    private Map<String, String> formParams = new HashMap<>(16);
    private Handler msgHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case AfSdkConstant.ERROR_CODE_NETWORK_REQUEST_TIMEOUT:
                    netWorkTimeOutOrAccessInterFaceFailed(bundle, AfSdkConstant.RESPONSE_NETWORK_REQUEST_TIMEOUT_KEY);
                    break;
                case AfSdkConstant.ERROR_CODE_ACCESS_INTERFACE_FAILED:
                    netWorkTimeOutOrAccessInterFaceFailed(bundle, AfSdkConstant.RESPONSE_ACCESS_INTERFACE_FAILED_KEY);
                    break;
                case AfSdkConstant.RESPONSE_SUCCESS_CODE_JF:
                    String jfResult = (String) bundle.get(AfSdkConstant.RESPONSE_RESULT_KEY);
                    String jfMtk = (String) bundle.get(AfSdkConstant.RESPONSE_MTK_KEY);
                    String jfDetail = (String) bundle.get(AfSdkConstant.RESPONSE_DETAIL_KEY);
                    jfListener.requestSuccess(new ResponseResults(jfResult, jfDetail, jfMtk));
                    break;
                case AfSdkConstant.RESPONSE_SUCCESS_CODE_SD:
                    String sdResult = (String) bundle.get(AfSdkConstant.RESPONSE_RESULT_KEY);
                    String sdDetail = (String) bundle.get(AfSdkConstant.RESPONSE_DETAIL_KEY);
                    sdListener.requestSuccess(new ResponseResult(sdResult, sdDetail));
                    break;
                case RESPONSE_SUCCESS_CODE_YJ:
                    String yjResult = (String) bundle.get(AfSdkConstant.RESPONSE_RESULT_KEY);
                    String yjDetail = (String) bundle.get(AfSdkConstant.RESPONSE_DETAIL_KEY);
                    String yjMtk = (String) bundle.get(AfSdkConstant.RESPONSE_MTK_KEY);
                    yjListener.requestSuccess(new ResponseResults(yjResult, yjDetail, yjMtk));
                    break;
                case AfSdkConstant.RESPONSE_SUCCESS_CODE_OD:
                    String odResult = (String) bundle.get(AfSdkConstant.RESPONSE_RESULT_KEY);
                    String odDetail = (String) bundle.get(AfSdkConstant.RESPONSE_DETAIL_KEY);
                    odListener.requestSuccess(new ResponseResult(odResult, odDetail));
                    break;
                case RESPONSE_SUCCESS_CODE_INSERT_LOG_ACTION:
                    insertLogListener.requestSuccess(new CheckSwingCardLogResult((String) bundle.get(AfSdkConstant.RESPONSE_RESULT_KEY)));
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 访问接口超时
     *
     * @param bundle
     */
    private void netWorkTimeOutOrAccessInterFaceFailed(Bundle bundle, String errorKey) {
        String timeOutInfo = null;
        String fallInfo = null;
        String timeOutClassifyInfo = null;
        boolean isNetWorkTimeOut = false;
        if (AfSdkConstant.RESPONSE_NETWORK_REQUEST_TIMEOUT_KEY.equals(errorKey)) {
            isNetWorkTimeOut = true;
            timeOutInfo = (String) bundle.get(AfSdkConstant.RESPONSE_NETWORK_REQUEST_TIMEOUT_KEY);
            timeOutClassifyInfo = (String) bundle.get(AfSdkConstant.RESPONSE_CLASSIFY_INFO_KEY);
        } else {
            fallInfo = (String) bundle.get(AfSdkConstant.RESPONSE_ACCESS_INTERFACE_FAILED_KEY);
        }
        if (!TextUtils.isEmpty(timeOutClassifyInfo)) {
            switch (timeOutClassifyInfo) {
                case AfSdkConstant.POS_NAME_JF_KEY:
                    if (isNetWorkTimeOut) {
                        jfListener.netWorkRequestTimeOut(timeOutInfo);
                    } else {
                        jfListener.accessInterfaceFailed(fallInfo);
                    }
                    break;
                case AfSdkConstant.POS_NAME_SD_KEY:
                    if (isNetWorkTimeOut) {
                        sdListener.netWorkRequestTimeOut(timeOutInfo);
                    } else {
                        sdListener.accessInterfaceFailed(fallInfo);
                    }
                    break;
                case AfSdkConstant.POS_NAME_YJ_KEY:
                    if (isNetWorkTimeOut) {
                        yjListener.netWorkRequestTimeOut(timeOutInfo);
                    } else {
                        yjListener.accessInterfaceFailed(fallInfo);
                    }
                    break;
                case AfSdkConstant.POS_NAME_OD_KEY:
                    if (isNetWorkTimeOut) {
                        odListener.netWorkRequestTimeOut(timeOutInfo);
                    } else {
                        odListener.accessInterfaceFailed(fallInfo);
                    }
                    break;
                case AfSdkConstant.INSERT_LOG_ACTION:
                    if (isNetWorkTimeOut) {
                        insertLogListener.netWorkRequestTimeOut(timeOutInfo);
                    } else {
                        insertLogListener.accessInterfaceFailed(fallInfo);
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 即富初始化构造
     *
     * @param jfListener 返回响应监听
     */
    public AfCheckSwingCardService(JfSwingCardListener jfListener) {
        this.jfListener = jfListener;
    }

    /**
     * 杉德 初始化构造
     *
     * @param sdListener 返回响应监听
     */
    public AfCheckSwingCardService(SdSwingCardListener sdListener) {
        this.sdListener = sdListener;
    }

    /**
     * 银嘉 初始化构造
     *
     * @param yjListener 返回响应监听
     */
    public AfCheckSwingCardService(YjSwingCardListener yjListener) {
        this.yjListener = yjListener;
    }

    /**
     * 欧达 初始化构造
     *
     * @param odListener 返回响应监听
     */
    public AfCheckSwingCardService(OdSwingCardListener odListener) {
        this.odListener = odListener;
    }

    /**
     * 保存刷卡日志监听
     *
     * @param insertLogListener
     */
    public AfCheckSwingCardService(InsertCheckSwingCardLogListener insertLogListener) {
        this.insertLogListener = insertLogListener;
    }


    /**
     * 绑定效验接口地址
     *
     * @param url 接口地址
     * @return
     */
    public AfCheckSwingCardService url(String url) {
        this.netWorkUrl = url;
        return this;
    }


    /**
     * 即富刷卡交易返回效验结果
     *
     * @param ksn          KSN号
     * @param cardNumber   卡号
     * @param trackRandom  磁道随机数
     * @param serialNumber 交易序列号
     * @param encryptData  加密磁道数据
     */
    @Override
    public void jfRequestBody(String ksn, String cardNumber,
                              String trackRandom, String serialNumber,
                              String encryptData) {
        formParams.put(AfSdkConstant.REQUEST_KSN_KEY, ksn);
        formParams.put(AfSdkConstant.REQUEST_CARD_NUMBER_KEY, cardNumber);
        formParams.put(AfSdkConstant.REQUEST_TRACK_RANDOM_KEY, trackRandom);
        formParams.put(AfSdkConstant.REQUEST_SERIALNUMBER_KEY, serialNumber);
        formParams.put(AfSdkConstant.REQUEST_ENCRYPT_DATA_KEY, encryptData);
        okHttpTool(formParams, AfSdkConstant.POS_NAME_JF_KEY);
        if (getSuccessInfo() != null) {
            ResponseResults result = responseSuccess(ResponseResults.class);
            map = new HashMap<>(16);
            map.put(AfSdkConstant.RESPONSE_RESULT_KEY, result.getResult());
            map.put(AfSdkConstant.RESPONSE_MTK_KEY, result.getMtk());
            map.put(AfSdkConstant.RESPONSE_DETAIL_KEY, result.getDetail());
            HandleUtil.sendMessage(msgHandle, AfSdkConstant.RESPONSE_SUCCESS_CODE_JF, map);
        }
    }

    /**
     * 杉德刷卡效验
     *
     * @param ksn        KSN号
     * @param cardNumber 卡号
     * @param trackData  二磁信息
     */
    @Override
    public void sdRequestBody(String ksn, String cardNumber, String trackData) {
        formParams.put(AfSdkConstant.REQUEST_KSN_KEY, ksn);
        formParams.put(AfSdkConstant.REQUEST_CARD_NUMBER_KEY, cardNumber);
        formParams.put(AfSdkConstant.REQUEST_TRACK_DATA_KEY, trackData);
        okHttpTool(formParams, AfSdkConstant.POS_NAME_SD_KEY);
        if (getSuccessInfo() != null) {
            ResponseResult result = responseSuccess(ResponseResult.class);
            map.put(AfSdkConstant.RESPONSE_RESULT_KEY, result.getResult());
            map.put(AfSdkConstant.RESPONSE_DETAIL_KEY, result.getDetail());
            HandleUtil.sendMessage(msgHandle, AfSdkConstant.RESPONSE_SUCCESS_CODE_SD, map);
        }
    }

    /**
     * 银嘉刷卡效验
     *
     * @param ksn          设备号
     * @param cardNumber   卡号
     * @param trackRandom  磁道随机数
     * @param serialNumber 交易随机数
     * @param encryptData  加密磁道数据
     */
    @Override
    public void yjRequestBody(String ksn, String cardNumber, String trackRandom, String serialNumber, String encryptData) {
        formParams.put(AfSdkConstant.REQUEST_KSN_KEY, ksn);
        formParams.put(AfSdkConstant.REQUEST_CARD_NUMBER_KEY, cardNumber);
        formParams.put(AfSdkConstant.REQUEST_TRACK_RANDOM_KEY, trackRandom);
        formParams.put(AfSdkConstant.REQUEST_SERIALNUMBER_KEY, serialNumber);
        formParams.put(AfSdkConstant.REQUEST_ENCRYPT_DATA_KEY, encryptData);
        okHttpTool(formParams, AfSdkConstant.POS_NAME_YJ_KEY);
        if (getSuccessInfo() != null) {
            ResponseResults results = responseSuccess(ResponseResults.class);
            map.put(AfSdkConstant.RESPONSE_RESULT_KEY, results.getResult());
            map.put(AfSdkConstant.RESPONSE_MTK_KEY, results.getMtk());
            map.put(AfSdkConstant.RESPONSE_DETAIL_KEY, results.getDetail());
            HandleUtil.sendMessage(msgHandle, RESPONSE_SUCCESS_CODE_YJ, map);
        }
    }


    /**
     * 欧达刷卡效验
     *
     * @param ksn        设备号
     * @param cardNumber 卡号
     * @param trackData  二磁信息
     */
    @Override
    public void odRequestBody(String ksn, String cardNumber, String trackData) {
        formParams.put(AfSdkConstant.REQUEST_KSN_KEY, ksn);
        formParams.put(AfSdkConstant.REQUEST_CARD_NUMBER_KEY, cardNumber);
        formParams.put(AfSdkConstant.REQUEST_TRACK_DATA_KEY, trackData);
        okHttpTool(formParams, AfSdkConstant.POS_NAME_OD_KEY);
        if (getSuccessInfo() != null) {
            ResponseResult result = responseSuccess(ResponseResult.class);
            map.put(AfSdkConstant.RESPONSE_RESULT_KEY, result.getResult());
            map.put(AfSdkConstant.RESPONSE_DETAIL_KEY, result.getDetail());
            HandleUtil.sendMessage(msgHandle, RESPONSE_SUCCESS_CODE_OD, map);
        }
    }

    @Override
    public void saveCheckSwingCardLog(CheckSwingCardLog log) {
        formParams.put("log.ksn", log.getKsn());
        formParams.put("log.swingCardType", String.valueOf(log.getSwingCardType()));
        formParams.put("log.bankCardNumber", log.getBankCardNumber());
        formParams.put("log.icData", log.getIcData());
        formParams.put("log.Track2Length", log.getTrack2Length());
        formParams.put("log.Track2Data", log.getTrack2Data());
        formParams.put("log.track3Length", log.getTrack3Length());
        formParams.put("log.track3Data", log.getTrack3Data());
        formParams.put("log.detail", log.getDetail());
        formParams.put("log.result", log.getResult());
        okHttpTool(formParams, AfSdkConstant.INSERT_LOG_ACTION);
        if (getSuccessInfo() != null) {
            CheckSwingCardLogResult result = responseSuccess(CheckSwingCardLogResult.class);
            map.put(AfSdkConstant.RESPONSE_RESULT_KEY, result.getResult());
            HandleUtil.sendMessage(msgHandle, RESPONSE_SUCCESS_CODE_INSERT_LOG_ACTION, map);
        }
    }

    /**
     * 封装Okhttp工具
     *
     * @param formParams 提交参数
     * @param errorTag   异常标记
     * @return
     */
    private void okHttpTool(Map<String, String> formParams, final String errorTag) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        StringBuffer formParamsSb = new StringBuffer();
        //设置表单参数
        for (String key : formParams.keySet()) {
            formParamsSb.append(key + "=" + formParams.get(key) + "&");
        }
        RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, formParamsSb.toString());
        //创建请求
        final Request request = new Request.Builder()
                .url(netWorkUrl)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                networkRequestTimeOutInfo(errorTag);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        setSuccessInfo(response.body().string());
                    } else {
                        accessInterfaceFailedInfo(errorTag);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 访问接口超时
     *
     * @param classifyInfo 不同的接口类调用不同监听: 用来调用不同的回调监听
     */
    private void networkRequestTimeOutInfo(String classifyInfo) {
        map.put(AfSdkConstant.RESPONSE_NETWORK_REQUEST_TIMEOUT_KEY, AfSdkConstant.ERROR_INFO_NETWORK_REQUEST_TIMEOUT);
        map.put(AfSdkConstant.RESPONSE_CLASSIFY_INFO_KEY, classifyInfo);
        HandleUtil.sendMessage(msgHandle, AfSdkConstant.ERROR_CODE_NETWORK_REQUEST_TIMEOUT, map);
    }

    /**
     * 访问接口错误
     *
     * @param classifyInfo 不同的接口类调用不同监听
     */
    private void accessInterfaceFailedInfo(String classifyInfo) {
        map.put(AfSdkConstant.RESPONSE_ACCESS_INTERFACE_FAILED_KEY, AfSdkConstant.ERROR_INFO_ACCESS_INTERFACE_FAILED);
        map.put(AfSdkConstant.RESPONSE_CLASSIFY_INFO_KEY, classifyInfo);
        HandleUtil.sendMessage(msgHandle, AfSdkConstant.ERROR_CODE_ACCESS_INTERFACE_FAILED, map);
    }
}
