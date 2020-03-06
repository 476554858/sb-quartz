package com.zjx.sbquartz.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class VoiceUtil {
    //语音提醒
    public static void printVoice(final String content){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
                Dispatch sapo = sap.getObject();
                try {
                    // 音量 0-100
                    sap.setProperty("Volume", new Variant(100));
                    // 语音朗读速度 -10 到 +10
                    sap.setProperty("Rate", new Variant(0));
                    // 执行朗读
                    Dispatch.call(sapo, "Speak", new Variant(content));
                } catch (Exception e) {
                } finally {
                    sapo.safeRelease();
                    sap.safeRelease();
                }
            }
        });
        //避免程序的延迟
        thread.start();
    }
}
