package com.cagrs.letschat.Core;

import java.io.Serializable;
import java.util.Date;

abstract public class Message implements Serializable {

    /**
     * 此消息身份
     */
    public enum MsgObj
    {
        LOCAL, NET
    }

    /**
     * 构造方法
     */
    public Message ()
    {
        time = new Date();
    }

    private MsgObj msgObj = MsgObj.LOCAL; // 身份

    public Date time;

    /**
     * 转换消息类型为本地(内部)消息
     */
    public void toLOCAL ()
    {
        msgObj = MsgObj.LOCAL;
    }

    /**
     * 转换消息类型为网络(外部)消息
     */
    public void toNET ()
    {
        msgObj = MsgObj.NET;
    }

    /**
     * 获得包名
     * @return 包名
     */
    abstract public String getPackageName ();

    /**
     * 事件 - 发送消息
     * @param param 参数
     * @return 对象
     */
    abstract public Object onRequireMessage (String param);

    /**
     * 事件 - 接收消息
     * @param object 对象
     * @return 结果
     */
    abstract public String onReceiveMessage (Object object);

    /**
     * 超时时间
     * @return
     */
    abstract public int getTimeout ();
}
