package com.andon.blockchaindata.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;

public class ProcessInfoTools {
    private final static boolean isNotWindows = !System.getProperties().getProperty("os.name").toLowerCase().contains("windows");
    private final static BigDecimal DIVISOR = BigDecimal.valueOf(1024);
    private final static Logger logger = LoggerFactory.getLogger(ProcessInfoTools.class);

    private static int getPid() {
        return Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    }

    public static ProcessInfoBean getProcessInfoBean() {
        ProcessInfoBean monitorInfo = new ProcessInfoBean();
        if (!isNotWindows) {
            monitorInfo.setMemUsage(500);
            return monitorInfo;
        }
        Runtime rt = Runtime.getRuntime();
        BufferedReader in = null;
        try {
            int pid = getPid();
            String[] cmd = {
                    "/bin/sh",
                    "-c",
                    "top -b -n 1 | grep " + pid
            };
            Process p = rt.exec(cmd);
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = null;
            String[] strArray = null;
            while ((str = in.readLine()) != null) {
                logger.debug("top: " + str);
                int m = 0;
                strArray = str.split(" ");
                for (String info : strArray) {
                    if (info.trim().length() == 0) {
                        continue;
                    }
                    /*if (m == 5) {//第5列为进程占用的物理内存值
                        String unit = info.substring(info.length() - 1);
                        if (unit.equalsIgnoreCase("g")) {
                            monitorInfo.setMemUseSize(Double.parseDouble(info));
                        } else if (unit.equalsIgnoreCase("m")) {
                            BigDecimal memUseSize = new BigDecimal(info.substring(0, info.length() - 1));
                            monitorInfo.setMemUseSize(memUseSize.divide(DIVISOR, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        } else {
                            BigDecimal memUseSize = new BigDecimal(info).divide(DIVISOR);
                            monitorInfo.setMemUseSize(memUseSize.divide(DIVISOR, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }*/
                    if (m == 8) {//第9列为CPU的使用百分比
                        monitorInfo.setCpuUsage(Double.parseDouble(info));
                    }
                    if (m == 9) {//第10列为内存的使用百分比
                        monitorInfo.setMemUsage(Double.parseDouble(info));
                    }
                    m++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return monitorInfo;
    }

}