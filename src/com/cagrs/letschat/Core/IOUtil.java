package com.cagrs.letschat.Core;
import java.io.*;

public class IOUtil
{
    /**
     * 写文件
     * @param path
     * @param content
     */
    public static void writeFile(String path, String content)
    {
        try
        {
            // 文件输出流
            FileOutputStream fos = new FileOutputStream(new File(path));
            fos.write(content.getBytes());
            fos.close();

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 读文件
     * @param path
     * @return
     */
    public static String readFile(String path)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);

            byte[] buf = new byte[1024];
            while ((fis.read(buf)) != -1)
            {
                sb.append(new String(buf));
                buf = new byte[1024];
            }
        }
        catch (FileNotFoundException e)
        {
            return null;
        }
        catch (IOException e)
        {
            return null;
        }
        return sb.toString();
    }
}