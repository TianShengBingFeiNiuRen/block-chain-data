package com.andon.blockchaindata.util;

public class ProcessInfoBean {

    /**
     * cpu使用率
     */
    private double cpuUsage;

    /**
     * 内存使用率
     */
    private double memUsage;

    /**
     * 内存使用的大小
     */
    private double memUseSize;

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getMemUsage() {
        return memUsage;
    }

    public void setMemUsage(double memUsage) {
        this.memUsage = memUsage;
    }

    public double getMemUseSize() {
        return memUseSize;
    }

    public void setMemUseSize(double memUseSize) {
        this.memUseSize = memUseSize;
    }

    @Override
    public String toString() {
        return "ProcessInfoBean{" +
                "cpuUsage=" + cpuUsage +
                ", memUsage=" + memUsage +
                ", memUseSize=" + memUseSize +
                '}';
    }
}
