package com.cherlshall.flypigeon.service;

import com.cherlshall.flypigeon.config.UdpServiceConfiguration;
import com.cherlshall.flypigeon.register.RegisterCenter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务端的 UDP 实现
 *
 * @author hu.tengfei
 * @since 2019/7/30
 */
public class UdpSendService implements SendService {

    private DatagramSocket server;
    private UdpServiceConfiguration configuration;
    private boolean startup;
    private ExecutorService pool = Executors.newCachedThreadPool();
    private RegisterCenter registerCenter;

    public UdpSendService() {
        this.configuration = new UdpServiceConfiguration();
    }

    public UdpSendService(UdpServiceConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean startup(RegisterCenter registerCenter) {
        this.startup = true;
        this.registerCenter = registerCenter;
        try {
            if (configuration.getIp() != null) {
                // 包装IP地址
                InetAddress address = InetAddress.getByName(configuration.getIp());
                // 创建服务端的DatagramSocket对象，需要传入地址和端口号
                server = new DatagramSocket(configuration.getPort(), address);
            } else {
                server = new DatagramSocket(configuration.getPort());
            }
            new Thread(() -> {
                // 开启一个死循环，不断接受数据
                while (startup) {
                    this.receive();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void receive() {
        byte[] receiveBytes = new byte[configuration.getMaxBytes()];
        // 创建接受信息的包对象
        DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
        try {
            // 接收数据，程序会阻塞到这一步，直到收到一个数据包为止
            server.receive(receivePacket);
        } catch (Exception e) {
            if (!startup) {
                return;
            }
            e.printStackTrace();
        }
        // 交给线程池异步执行分析和响应
        pool.submit(() -> this.analyzeAndSend(receivePacket));
    }

    private void analyzeAndSend(DatagramPacket receivePacket) {
        // 解析收到的数据
        String receiveMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
        // 解析客户端地址
        InetAddress clientAddress = receivePacket.getAddress();
        // 解析客户端端口
        int clientPort = receivePacket.getPort();

        // 组建响应信息
        String response = registerCenter.execute(receiveMsg);
        byte[] responseBuf = response.getBytes();
        // 创建响应信息的包对象，由于要发送到目的地址，所以要加上目的主机的地址和端口号
        DatagramPacket responsePacket = new DatagramPacket(responseBuf, responseBuf.length, clientAddress, clientPort);

        try {
            // 发送数据
            server.send(responsePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean shutdown() {
        // 关闭DatagramSocket对象
        if (server != null) {
            server.close();
            server = null;
        }
        startup = false;
        return true;
    }
}
