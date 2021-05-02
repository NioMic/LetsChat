import com.cagrs.letschat.Core.Client.LscClient;
import com.cagrs.letschat.Core.Client.MessageContainer;
import com.cagrs.letschat.Core.Client.MessageManager;
import com.cagrs.letschat.Core.Messages.MessageCheck;
import com.cagrs.letschat.Core.Messages.MessageText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestReceiver {
    public static void main (String[] args)
    {
        try {

            // 建立客户端
            MessageManager messageManager = new MessageManager(System.getProperty("user.dir"));
            LscClient lscClient = new LscClient(messageManager, 5012);

            // 添加模块
            lscClient.loadModule(new MessageText());
            lscClient.loadModule(new MessageCheck());

            System.out.println("读取聊天记录!");
            List<MessageContainer> containerList = messageManager.readMember("127.0.0.1");
            for (MessageContainer container : containerList)
            {
                System.out.println(lscClient.getModuleMessage(container.packName).onReceiveMessage(container.object));
            }

            // 启动客户端
            lscClient.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
