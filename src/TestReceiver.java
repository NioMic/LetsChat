import com.cagrs.letschat.Core.Client.LscClient;
import com.cagrs.letschat.Core.Client.MessageManager;
import com.cagrs.letschat.Core.Messages.MessageCheck;
import com.cagrs.letschat.Core.Messages.MessageText;

import java.io.IOException;

public class TestReceiver {
    public static void main (String[] args)
    {
        try {

            // 建立客户端
            MessageManager messageManager = new MessageManager(System.getProperty("user.dir"));
            LscClient lscClient = new LscClient(messageManager, 5012);

            messageManager.createMember("192.168.107.42");

            // 添加模块
            lscClient.loadModule(new MessageText());
            lscClient.loadModule(new MessageCheck());

            // 启动客户端
            lscClient.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
