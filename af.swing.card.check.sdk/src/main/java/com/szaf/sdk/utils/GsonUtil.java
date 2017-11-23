package com.szaf.sdk.utils;

import com.google.gson.Gson;

import static com.szaf.sdk.constant.AfSdkConstant.getSuccessInfo;


/**
 * @author yd
 *         Created by yd on 2017/10/12.
 */

public class GsonUtil {

    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    /**
     * 封装GSON
     *
     * @param classType
     * @param <T>
     * @return
     */
    public static <T> T responseSuccess(Class<T> classType) {
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return parseJsonWithGson(getSuccessInfo(), classType);
    }
}
