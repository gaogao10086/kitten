package com.gys.kitten.core.util;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP工具类
 */
public class HttpUtil {

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost2(String url, String content) {
        StringBuilder sb = new StringBuilder();
        try {
            URL postUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setChunkedStreamingMode(5);
            connection.connect();
            /**//*
                 * 注意，下面的getOutputStream函数工作方式于在readContentFromPost()里面的不同
				 * 在readContentFromPost()里面该函数仍在准备http request，没有向服务器发送任何数据
				 * 而在这里由于设置了ChunkedStreamingMode
				 * ，getOutputStream函数会根据connect之前的配置 生成http request头，先发送到服务器。
				 */
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            //String content = "firstname="+URLEncoder.encode(param,"utf-8");
            out.writeBytes(content);

            out.flush();
            out.close(); // 到此时服务器已经收到了完整的http
            // request了，而在readContentFromPost()函数里，要等到下一句服务器才能收到http请求。
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));

            out.flush();
            out.close(); // flush and close
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();
            connection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    public static Map requestProxy(Map<String, Object> paramMap) throws UnsupportedEncodingException {
        String hBaseUrl = "http://10.106.4.203:28080/HBaseProxy/proxy/handler";
        Gson gson = new GsonUtils().getGson();
        String gJson = gson.toJson(paramMap);
        String responseStr = HttpUtil.sendPost2(hBaseUrl, "params=" + URLEncoder.encode(gJson, "utf-8"));
        if (StringUtils.isNotEmpty(responseStr)) {
            return TransformUtil.parseJSON2Map(responseStr);
        }
        return null;
    }

    public static byte[] combinByte(byte[] a, byte[] b, byte[] c, byte[] d) {
        byte[] result = new byte[a.length + b.length + c.length + d.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        System.arraycopy(c, 0, result, a.length + b.length, c.length);
        System.arraycopy(d, 0, result, a.length + b.length + c.length, d.length);
        return result;
    }

    public static byte[] getRowKey() {
        Byte event = (byte) 0x06;
        byte[] eventByte = new byte[]{(byte) 0x00, event};
        byte[] md5Byte = "e3f21ed6d6d8636a391efea6b77120a0".getBytes();
        byte[] timeSimple = "20150922".getBytes();
        byte[] uuid = "".getBytes();
        byte[] keyByte = combinByte(md5Byte, eventByte, timeSimple, uuid);
        return keyByte;
    }

    public static byte[] getRowKey2() {
        Byte event = (byte) 0x06;
        byte[] eventByte = new byte[]{(byte) 0x00, event};
        byte[] md5Byte = "e3f21ed6d6d8636a391efea6b77120a0".getBytes();
        byte[] timeSimple = "20150923".getBytes();
        byte[] uuid = "".getBytes();
        byte[] keyByte = combinByte(md5Byte, eventByte, timeSimple, uuid);
        return keyByte;
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("method", "scan");
        map.put("tableName", "CreditFetch");
        map.put("startKey", getRowKey());
        map.put("endKey", getRowKey2());
        try {
            Map response = requestProxy(map);
            System.out.println(response.get("result"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
