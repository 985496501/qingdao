package com.self.boot.net;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class InetAddressTest {

    // 这个ip的封装是没有构造方法的
    @Test
    public void constructInetAddrTest() throws UnknownHostException {
        InetAddress ipAddress = InetAddress.getLocalHost(); // 直接获取ip
        System.out.println(ipAddress.getHostName()); // 调用主机名称
        System.out.println(ipAddress.getHostAddress()); // 主机地址(公网 内网)

        InetAddress bdAddress = InetAddress.getByName("www.baidu.com");  // name 通过域名获取ip
        System.out.println(bdAddress.getHostName()); // 返回的就是主机的name
        System.out.println(bdAddress.getHostAddress()); // 返回ip  39.156.66.14

        InetAddress byName = InetAddress.getByName("39.156.66.14");
        System.out.println(byName.getHostName());  // name
        System.out.println(byName.getHostAddress()); // ip DNS解析
    }

    // 在InetAddress进一步封装加上了 port 是否构造方法的
    @Test
    public void InetSocketTest() throws UnknownHostException {
        InetSocketAddress ipPortAddress = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
        InetAddress ipAddress = ipPortAddress.getAddress();
        System.out.println(ipPortAddress.getAddress()); // DESKTOP-953GB4O/192.168.110.243
        System.out.println(ipPortAddress.getHostName()); // DESKTOP-953GB4O
        System.out.println(ipPortAddress.getHostString()); // DESKTOP-953GB4O
        System.out.println(ipPortAddress.getPort()); // 8080
        System.out.println(ipPortAddress.toString()); // DESKTOP-953GB4O/192.168.110.243:8080
        System.out.println(ipAddress.getHostAddress()); // 192.168.110.243

        // directly fetch InetSocketAddress's ip
        System.out.println(ipPortAddress.getAddress().getHostAddress());
    }








}
