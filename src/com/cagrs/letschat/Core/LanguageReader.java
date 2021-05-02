package com.cagrs.letschat.Core;

import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class LanguageReader {

    JSONObject jsonObject;

    public static final File LanguageFile = new File(
            System.getProperty("user.dir") + File.separator + "lang.json"
    );

    // 构造方法 （直接加载语言）
    public LanguageReader ()
    {
        if (LanguageFile.exists()) {
            jsonObject = JSONObject.parseObject(
                    IOUtil.readFile(LanguageFile.getPath())
            );
        }
        else
        {
            System.out.println("Cannot found \"lang.json\"");
        }
    }

    public String r (String name)
    {
        return jsonObject.getString(name);
    }
}
