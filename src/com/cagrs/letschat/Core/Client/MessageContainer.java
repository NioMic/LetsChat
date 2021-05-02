package com.cagrs.letschat.Core.Client;

import java.io.Serializable;

public class MessageContainer implements Serializable {

    public Object object;

    public String packName;

    public MessageContainer (Object object, String packName)
    {
        this.object = object;
        this.packName = packName;
    }
}
