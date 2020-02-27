package com.czb.mydubbo.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ZkConfig {
    //多个zkserver127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
    public static final String ZK_SERVER = "127.0.0.1:2181";
    public static final String ZK_REGISTER_PATH = "/registry/";
    public static final String ZK_NAME_SPACE = "dubbo";


    public static int servicePort = 9999;
    public static String severIP;

  static {
    try {
      //获取本机局域网IP写到zk
      severIP = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  public static String serviceAddress = severIP + ":" + servicePort;


}
