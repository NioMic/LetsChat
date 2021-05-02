package com.cagrs.letschat.Core.Messages;

import com.cagrs.letschat.Core.Message;

import java.io.Serializable;

public class MessageCheck extends Message{
    @Override
    public String getPackageName() {
        return "check";
    }

    @Override
    public Object onRequireMessage(String param) {
        return "检测";
    }

    @Override
    public String onReceiveMessage(Object object) {
        return "检测";
    }

    @Override
    public int getTimeout() {
        return 1000;
    }
}
