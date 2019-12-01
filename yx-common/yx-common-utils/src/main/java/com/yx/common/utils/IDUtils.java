package com.yx.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IDUtils {

    private String ID_HEADER = "YXCH";
    private String hostAddress = null;
    private String processId = null;
    private Random RANDOM = new SecureRandom();
    private SimpleDateFormat milliTimeFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private static class IDUtilsHolder {
        public static final IDUtils INSTANCE = new IDUtils();
    }

    public static IDUtils getInstance() {
        return IDUtilsHolder.INSTANCE;
    }

    public IDUtils() {
        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            String name = runtime.getName();
            processId = name.substring(0, name.indexOf("@"));
            if (StringUtils.isBlank(processId)) {
                processId = "00000";
            }
            processId = StringUtils.leftPad(StringUtils.left(processId, 5), 5, "0");
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (final IOException e) {
            hostAddress = String.valueOf(System.currentTimeMillis());
        }
        if (null == hostAddress || hostAddress.trim().length() == 0 || "127.0.0.1".equals(hostAddress)) {
            hostAddress = String.valueOf(System.currentTimeMillis());
        }
        hostAddress = hostAddress.substring(hostAddress.length() - 2).replace(".", "0");
    }

    /*public static void nextId(){
        System.out.println(getInstance().getHeaderId());
    }*/

    public String getHeaderId() {
        StringBuffer stringBuffer = new StringBuffer();
        String milliTime = getMilliTime();
        String threadCode = getThreadCode();
        String random = String.valueOf(RANDOM.nextInt(100));
        // 4位的头
        stringBuffer.append(ID_HEADER);
        // 17位的时间戳
        stringBuffer.append(milliTime);
        // 5位的进程id
        stringBuffer.append(processId);
        // 4位的线程hashCode
        stringBuffer.append(threadCode);
        // 2位的随机数
        random = StringUtils.leftPad(random, 2, "0");
        stringBuffer.append(random);
        //System.out.println(String.format("ID：【%s】，milliTime：【%s】，processId：【%s】，threadCode：【%s】，random：【%s】", stringBuffer.toString(), milliTime, processId, threadCode, random));
        return stringBuffer.toString();
    }

    private String getThreadCode() {
        return StringUtils.leftPad(StringUtils.left(String.valueOf(Thread.currentThread().hashCode()), 4), 4, "0");
    }

    private String getMilliTime() {
        return milliTimeFormat.format(new Date());
    }

}
