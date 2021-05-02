package com.cagrs.letschat.Core.Client;

import com.cagrs.letschat.Core.LanguageReader;
import com.cagrs.letschat.Core.Message;

import java.io.*;
import java.net.BindException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class LscClient {

    private static LanguageReader lr = new LanguageReader();

    private static final int defaultPort = 32768;

    private ServerSocket serverSocket; // 服务器

    private MessageManager messageManager; // 消息管理器

    // 加载的模块
    private List<Message> MessageModuleList = new ArrayList<>();
    private List<String> MessageModuleNameList = new ArrayList<>();

    /**
     * 添加模块
     * @param message
     */
    public void loadModule (Message message)
    {
        MessageModuleList.add(message);
        MessageModuleNameList.add(message.getPackageName());
    }

    /**
     * 获得模块
     * @param name
     * @return
     */
    private Message getModuleMessage (String name)
    {
        for (int i = MessageModuleNameList.size(); i >= 0; i --)
        {
            if (name.equals(MessageModuleNameList.get(i - 1)))
            {
                return MessageModuleList.get(i - 1);
            }
        }
        return null;
    }

    /**
     * 构造方法
     * @param messageManager 消息管理器
     * @param port 端口
     * @throws IOException
     */
    public LscClient (MessageManager messageManager, int port) throws IOException
    {
        while (true) {

            try
            {
                this.messageManager = messageManager;

                serverSocket = new ServerSocket(port);

                break;
            }
            catch (BindException exception)
            {
                System.out.println(
                        lr.r("Err_BindException")
                        .replace("[port]", port + "")
                        .replace("[new_port]", port + 1 + "")
                );

                System.out.println(lr.r("Err_BindException_Hint"));

                port ++;
            }
        }

        System.out.println(lr.r("Str_Welcome"));
    }

    /**
     * 启动服务
     */
    public void start () throws IOException
    {
        System.out.println(
                lr.r("Str_List")
                .replace("[ip]", serverSocket.getInetAddress()+ "")
                .replace("[port]", serverSocket.getLocalPort() + "")
        );

        // 循环接受消息
        while (true)
        {
            Socket socket = serverSocket.accept();

            // 多线程
            new Thread(new LscClientRunnable(socket)).start();

        }
    }

    /**
     * 处理信息
     * @param socket
     */
    public void onProcess (Socket socket) throws IOException, ClassNotFoundException {

        InputStream inputStream = socket.getInputStream();

        // 字节读取器 和 对象输入流
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // 包名 和 对象类型
        Object object = objectInputStream.readObject();

        String packageName = bufferedReader.readLine();

        System.out.println(" >> " + socket.getInetAddress() + " : " +
                getModuleMessage(packageName.trim()).onReceiveMessage(object));

        // 返回成功信息
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        bufferedWriter.write("success" + "\n");

        bufferedWriter.flush();
    }

    /**
     * 发送消息
     * @param message
     * @param param
     * @param ip
     * @param port
     * @throws IOException
     */
    public static final String sendMessage (Message message, String param, String ip, int port) throws IOException
    {
        String returnContent = "."; // 返回值

        try
        {
            // 建立连接 & 输出流
            Socket socket = new Socket(ip, port);

            socket.setSoTimeout(message.getTimeout());

            OutputStream outputStream = socket.getOutputStream();

            // 输出包名
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            bufferedWriter.write(message.getPackageName() + "\n");

            // 输出信息对象
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            // 将消息作为本地消息存储

            // 将消息转换成外部消息发送以供对方识别
            message.toNET();

            objectOutputStream.writeObject(message.onRequireMessage(param));

            bufferedWriter.flush();

            // 接收返回信息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String result = bufferedReader.readLine().trim();

            // 成功
            if (result.equals("success")) {
                returnContent = lr.r("Str_SuccessRequire");
            } else if (result.equals("deny")) {
                returnContent = lr.r("Str_Fail_Deny");
            }

            // 关闭输出流
            objectOutputStream.close();

        } catch (ConnectException connectException)
        {
            // 连接超时
            returnContent = lr.r("Str_Fail_Timeout");
        }

        return returnContent;
    }

    // 线程
    public class LscClientRunnable implements Runnable
    {
        Socket socket;

        public LscClientRunnable (Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run() {
            try
            {
                onProcess(socket);
            }
            catch (IOException e)
            {
                System.out.println(lr.r("Err_IOException"));
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                System.out.println(lr.r("Err_ClassNotFoundException"));
                e.printStackTrace();
            }
        }
    }
}
