package com.cherlshall.flypigeon.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP客户端
 * 供测试使用，本工具重心在服务端，仅使用客户端无需引入本工具，可选择自行实现
 *
 * @author hu.tengfei
 * @since 2019/7/30
 */
public class UdpClient {
    private DatagramSocket client;
    private String ip;
    private int port;
    private int maxBytes;

    public UdpClient(String ip, int port, int maxBytes) {
        this.ip = ip;
        this.port = port;
        this.maxBytes = maxBytes;
    }

    public String sendAndReceive(String msg) {
        String responseMsg = "";
        try {
            // 创建客户端的DatagramSocket对象，不必传入地址和对象
            client = new DatagramSocket();
            byte[] sendBytes = msg.getBytes();
            // 封装要发送目标的地址
            InetAddress address = InetAddress.getByName(ip);
            // 封装要发送的DatagramPacket的对象，由于要发送到目的主机，所以要加上地址和端口号
            DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length, address, port);

            try {
                //发送数据
                client.send(sendPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }

            byte[] responseBytes = new byte[maxBytes];
            // 创建响应信息的DatagramPacket对象
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
            try {
                // 等待响应信息，同服务端一样，客户端也会在这一步阻塞，直到收到一个数据包
                client.receive(responsePacket);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 解析数据包内容
            responseMsg = new String(responsePacket.getData(), 0, responsePacket.getLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseMsg;
    }

    public boolean shutdown() {
        if (client != null) {
            client.close();
            client = null;
        }
        return true;
    }
}
