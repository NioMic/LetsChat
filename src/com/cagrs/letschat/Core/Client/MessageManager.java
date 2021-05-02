package com.cagrs.letschat.Core.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private String fs = File.separator;

    private String directory; // 目录

    private LscClient client; // 客户端

    public void inputClient (LscClient client)
    {
        this.client = client;
    }

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
        File messages = new File (directory + memberName);

        if (!messages.exists())
        {
            messages.mkdir();

            writeMember (new ArrayList<>(), memberName);
        }
    }

    /**
     * 读取成员
     * @param memberName
     * @return
     */
    public List<MessageContainer> readMember (String memberName)
    {
        List<MessageContainer> containerList = null;

        try
        {
            File messages = new File(directory + memberName + fs + "messages.msd");


            if (messages.exists())
            {
                // 输入流
                ObjectInputStream objectInputStream = new ObjectInputStream(
                        new FileInputStream(messages)
                );

                // 读取对象
                containerList = (List<MessageContainer>) objectInputStream.readObject();

                objectInputStream.close();

            } else {
                // 找不到文件
                System.out.print(
                        client.lr.r("Err_CannotFoundMember")
                                .replace("[member]", memberName)
                );
            }
        }
        catch (IOException e)
        {
            System.out.print(client.lr.r("Err_IOException"));
        }
        catch (ClassNotFoundException e)
        {
            System.out.print(client.lr.r("Err_ClassNotFoundException"));
        }

        return containerList;
    }

    /**
     * 保存成员
     * @param containerList
     * @param memberName
     */
    public void writeMember (List<MessageContainer> containerList, String memberName)
    {
        File messages = new File (directory + memberName + fs + "messages.msd");

        try
        {
            // 对象输出流
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(messages)
            );

            // 写入、传输、关闭
            objectOutputStream.writeObject(containerList);

            objectOutputStream.flush();

            objectOutputStream.close();
        }
        catch (IOException e)
        {
            client.lr.r("Err_IOException");
        }
    }

    /**
     * 添加消息到成员
     * @param containerList 列表
     * @param container 容器
     * @return
     */
    public List<MessageContainer> addMessageToMember
    (List<MessageContainer> containerList, MessageContainer container)
    {
        // 添加数据
        containerList.add(container);

        // 消息数量超过200则删除最后一位
        if (containerList.size() >= 200)
        {
            containerList.remove(0);
        }

        return containerList;
    }

}
