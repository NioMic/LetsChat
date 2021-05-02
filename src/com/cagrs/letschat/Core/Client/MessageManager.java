package com.cagrs.letschat.Core.Client;

import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class MessageManager {

    private String fs = File.separator;

    private String directory; // 目录

    /**
     * 构造方法
     * @param directory
     */
    public MessageManager (String directory)
    {
        this.directory = directory + fs + "cache" + fs;

        new File (this.directory).mkdir();
    }

    /**
     * 创建成员
     * @param memberName 准确来说这里应该填写ip地址
     */
    public void createMember (String memberName)
    {
        // 创建目录
        new File (directory + memberName).mkdir();

    }


}
