package com.cagrs.letschat.Core.Messages;

import com.cagrs.letschat.Core.Message;

import java.io.Serializable;

public class MessageText extends Message{

    @Override
    public String getPackageName() {
        return "text";
    }

    @Override
    public Object onRequireMessage(String param) {
        return param;
    }

    @Override
    public String onReceiveMessage(Object object) {
        return (String) object;
    }

    @Override
    public int getTimeout() {
        return 2000;
    }
}
