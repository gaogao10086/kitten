package com.gys.kitten.core.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成全局唯一ID类
 *
 * @author binghuiao
 */
public class UuidUtils {

    private static Log log = LogFactory.getLog(UuidUtils.class);
    private static String MAC = "";
    private static String MAC_AND_PORT = "";
    public static String pre_Sesssion_time = "";
    public static int pre_Sesssion_sort = 0;
    private static final String[] timeValue = {"Y", "i", "R", "e", "n", "D", "a", "W", "E", "B"};
    private static final int[] randomLocal = {1, 4, 5, 7, 8, 11, 13, 14, 16, 18, 20, 22, 2, 25, 28, 30, 32};
    private static final int[] timeLocal = {3, 9, 6, 15, 12, 26, 21, 24, 17, 27, 23, 31, 10, 29, 19};

    public static final synchronized String getUuid(Integer proxyPort) {
        char[] sessionId = new char[32];
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
        String time = sdf.format(new Date());

        if (pre_Sesssion_time.equals(time)) {
            pre_Sesssion_sort++;
            if (pre_Sesssion_sort > 15) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                time = sdf.format(new Date());
                pre_Sesssion_sort = 0;
            }
        } else {
            pre_Sesssion_time = time;
            pre_Sesssion_sort = 0;
        }
        for (int i = 0; i < 15; ++i) {
            int local = timeLocal[i];
            int value = Integer.parseInt(time.charAt(i) + "");
            sessionId[local - 1] = timeValue[value].charAt(0);
        }
        int port = proxyPort == null ? 9999 : proxyPort;
        String macAndPort = getMacAndPort(port);
        for (int i = 0; i < 16; i++) {
            int local = randomLocal[i];
            sessionId[local - 1] = macAndPort.charAt(i);
        }
        String cur_sort = Integer.toHexString(pre_Sesssion_sort);
        sessionId[randomLocal[16] - 1] = cur_sort.charAt(0);

        return new String(sessionId);
    }

    public static void main(String[] args) {
        byte[] hehe = getUuid(9999).getBytes();
        System.out.println(getUuid(9999));
    }

    /**
     * 获得mac地址.
     *
     * @param argc 运行参数.
     * @throws Exception
     */
    public static String getMac() throws Exception {
        log.info("MAC地址：" + MAC);
        if (StringUtils.isNotEmpty(MAC))
            return MAC;

        String os = getOSName();
        log.info(os);
        if (os.equals("windows 7")) {
            MAC = getWin7MACAddress();
        } else if (os.startsWith("windows")) {
            // 本地是windows  
            MAC = getWindowsMACAddress();
        } else if (os.startsWith("linux")) {
            // 本地是linux  
            MAC = getLinuxMacAddr();
        } else {
            // 本地是非windows,linux系统 一般就是unix  
            MAC = getUnixMACAddress();
        }
        log.info("MAC地址：" + MAC);
        return MAC;
    }

    public static String getMacAndPort(int port) {
        if (StringUtils.isNotEmpty(MAC_AND_PORT))
            return MAC_AND_PORT;
        String sMac = null;
        String sPort = Integer.toHexString(port);
        int iLen = 0;
        try {
            sMac = getMac().replace("-", "");
        } catch (Exception e) {
            sMac = "abcdefABCDEF";
            e.printStackTrace();
        }
        iLen = sMac.length();
        if (iLen > 12) {
            sMac = sMac.substring(iLen - 12, iLen);
        } else if (iLen < 12) {
            for (int i = 0; i < 12 - iLen; i++)
                sMac = "0" + sMac;
        }

        iLen = sPort.length();
        if (iLen > 4) {
            sPort = sPort.substring(iLen - 4, iLen);
        } else if (iLen < 4) {
            for (int i = 0; i < 4 - iLen; i++)
                sPort = "0" + sPort;
        }
        MAC_AND_PORT = sMac + sPort;
        return MAC_AND_PORT;
    }


    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
     */
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取.
     * 如果有特殊系统请继续扩充新的取mac地址方法.
     *
     * @return mac地址
     */
    public static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // linux下的命令，一般取eth0作为本地主网卡  
            process = Runtime.getRuntime().exec("ifconfig eth0");
            // 显示信息中包含有mac地址信息  
            bufferedReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]  
                index = line.toLowerCase().indexOf("hwaddr");
                if (index >= 0) {// 找到了  
                    // 取出mac地址并去除2边空格  
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }

    /**
     * 获取widnows网卡的mac地址.
     *
     * @return mac地址
     */
    public static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // windows下的命令，显示信息中包含有mac地址信息  
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
//                    System.out.println(line);  
                // 寻找标示字符串[physical  
                index = line.toLowerCase().indexOf("physical address");

                if (index >= 0) {// 找到了  
                    index = line.indexOf(":");// 寻找":"的位置  
                    if (index >= 0) {
//                            System.out.println(mac);  
                        // 取出mac地址并去除2边空格  
                        mac = line.substring(index + 1).trim();
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    /**
     * windows 7 专用 获取MAC地址
     *
     * @return
     * @throws Exception
     */
    public static String getWin7MACAddress() throws Exception {

        // 获取本地IP对象  
        InetAddress ia = InetAddress.getLocalHost();
        // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。  
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

        // 下面代码是把mac地址拼装成String  
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数  
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }

        // 把字符串所有小写字母改为大写成为正规的mac地址并返回  
        return sb.toString().toUpperCase();
    }

    public static String getLinuxMacAddr() throws Exception {
        byte[] mac = NetworkInterface.getByName("eth0").getHardwareAddress();
        // 下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }

        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }

}
