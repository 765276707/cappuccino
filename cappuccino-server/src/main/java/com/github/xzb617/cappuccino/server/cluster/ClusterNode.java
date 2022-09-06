package com.github.xzb617.cappuccino.server.cluster;

import java.util.concurrent.atomic.AtomicInteger;

public class ClusterNode {

    /**
     * 节点地址
     */
    private String address;

    /**
     * 节点是否存活
     */
    private boolean alive = true;

    /**
     * 连接失败计数器
     */
    private AtomicInteger connectFailedCounter;

    public ClusterNode() {
        this.connectFailedCounter = new AtomicInteger(0);
    }

    public ClusterNode(String address, boolean alive) {
        this.address = address;
        this.alive = alive;
        this.connectFailedCounter = new AtomicInteger(0);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public AtomicInteger getConnectFailedCounter() {
        return connectFailedCounter;
    }

    public void setConnectFailedCounter(AtomicInteger connectFailedCounter) {
        this.connectFailedCounter = connectFailedCounter;
    }

    public void resetConnectFailedCounter() {
        this.connectFailedCounter = new AtomicInteger(0);
    }

    @Override
    public String toString() {
        return "Server{" +
                "address='" + address + '\'' +
                ", alive=" + alive +
                ", connectFailedCounter=" + connectFailedCounter +
                '}';
    }


}
