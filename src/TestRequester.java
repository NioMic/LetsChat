import com.cagrs.letschat.Core.Client.LscClient;
import com.cagrs.letschat.Core.Client.MessageManager;
import com.cagrs.letschat.Core.Messages.MessageCheck;
import com.cagrs.letschat.Core.Messages.MessageText;

import java.io.IOException;
import java.util.Scanner;

public class TestRequester {
    public static void main (String[] args)
    {
        while (true)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print("# ");

            try {
                System.out.println(LscClient.sendMessage(new MessageText(), scanner.nextLine(), "127.0.0.1", 5012));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
