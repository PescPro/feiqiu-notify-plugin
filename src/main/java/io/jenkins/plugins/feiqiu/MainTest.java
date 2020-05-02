package io.jenkins.plugins.feiqiu;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by pigercc on 2016/6/17.
 */
public class MainTest {

    private final static long MSG_TYPE_GROUP = 0L;
    public static void main(String[] args) {
        //FeiqUtil.listenGroupMsg();
        FeiqUtil.sendGroupMsg(164125611L,"myMsg","weijianbo","DESKTOP-J0N755T","4074E0774417");
    }
}
