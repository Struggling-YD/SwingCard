package com.szaf.sdk.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Map;

/**
 * @author yd
 *         Created by yd on 2017/10/16.
 */

public class HandleUtil {
    /**
     * 封装Handle发送数据工具类
     *
     * @param msgHandle 当前Handle
     * @param what      信息标记
     * @param bundles   数据
     */
    public static void sendMessage(Handler msgHandle, int what, Map<String, String> bundles) {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : bundles.entrySet()) {
            bundle.putCharSequence(entry.getKey(), entry.getValue());
        }
        msg.setData(bundle);
        msg.what = what;
        msgHandle.sendMessage(msg);
    }
}
