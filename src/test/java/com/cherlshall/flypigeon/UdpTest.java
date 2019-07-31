package com.cherlshall.flypigeon;

import com.cherlshall.flypigeon.client.UdpClient;
import com.cherlshall.flypigeon.config.Configuration;
import com.cherlshall.flypigeon.register.RegisterCenter;
import com.cherlshall.flypigeon.service.SendService;
import com.cherlshall.flypigeon.service.UdpSendService;
import org.junit.Test;

public class UdpTest {

    @Test
    public void test() {
        // 启动server
        Configuration configuration = new Configuration();
        configuration.setPort(10101);
        configuration.setMaxBytes(2048);
        SendService udpSendService = new UdpSendService(configuration);
        RegisterCenter registerCenter = new RegisterCenter();
        registerCenter.register("hello world", () -> "hi");
        registerCenter.register("hello china", () -> "nihao");
        boolean startup = udpSendService.startup(registerCenter);
        System.out.println("service startup " + (startup ? "success" : "failure"));

        // 启动client
        UdpClient udpClient = new UdpClient("127.0.0.1", 10101, 2048);
        String responseHello = udpClient.sendAndReceive("hello");
        System.out.println("responseHello = " + responseHello);
        String responseWorld = udpClient.sendAndReceive("hello world");
        System.out.println("responseWorld = " + responseWorld);
        String responseChina = udpClient.sendAndReceive("hello china");
        System.out.println("responseChina = " + responseChina);

        // 关闭
        udpSendService.shutdown();
        udpClient.shutdown();
    }
}
