package com.cherlshall.flypigeon.config;

/**
 * @author hu.tengfei
 * @since 2019/7/31
 */
public class UdpServiceConfiguration {
    /**
     * server ip 默认为本机
     */
    private String ip;

    /**
     * server port
     */
    private int port = 10100;

    /**
     * 每条数据最大长度
     */
    private int maxBytes = 2048;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxBytes() {
        return maxBytes;
    }

    public void setMaxBytes(int maxBytes) {
        this.maxBytes = maxBytes;
    }
}
