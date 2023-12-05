package com.harri.training2.utils;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public class SSEUtils {
    public static SseEmitter writeToSSE(List<?> objects) {
        SseEmitter emitter = new SseEmitter();

        new Thread(() -> {
            try {
                for (Object obj : objects) {
                    emitter.send(SseEmitter.event().data(obj));
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);

            }
        }).start();

        return emitter;
    }
}
