package com.github.xzb617.cappuccino.client.utils;

import com.github.xzb617.cappuccino.commons.data.Server;
import com.github.xzb617.cappuccino.commons.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerUtil {

    private final static String WEIGHT_URL_FLAG = "&wgt=";

    public static List<Server> parseServers(String serverAddr) {
        List<Server> servers;
        if (serverAddr.indexOf(",") > 0) {
            String[] urls = serverAddr.split(",");
            servers = new ArrayList<>(urls.length);
            for (int i = 0; i < urls.length; i++) {
                Server server = new Server();
                server.setAddress(getRealAddr(urls[i]));
                server.setAlive(true);
                server.setWeight(getWeight(urls[i]));
                server.setConnectFailedCounter(new AtomicInteger(0));
                servers.add(server);
            }
        } else {
            servers = new ArrayList<>(1);
            Server server = new Server();
            server.setAddress(getRealAddr(serverAddr));
            server.setAlive(true);
            server.setConnectFailedCounter(new AtomicInteger(0));
            servers.add(server);
        }
        return servers;
    }

    /**
     * 获取有效的地址
     * @param addr 可能是加了权限标识的地址
     * @return
     */
    private static String getRealAddr(String addr) {
        int index = addr.indexOf(WEIGHT_URL_FLAG);
        if (index > -1) {
            return addr.substring(0, index);
        }
        return addr;
    }

    /**
     * 获取权重
     * @param addr
     * @return
     */
    private static int getWeight(String addr) {
        int weight = 1;
        int index = addr.indexOf(WEIGHT_URL_FLAG);
        if (index > -1) {
            String wgt = addr.substring(index+WEIGHT_URL_FLAG.length());
            if (!StrUtil.isEmpty(wgt)) {
                weight = Integer.parseInt(wgt);
            }
        }
        return weight;
    }

}
