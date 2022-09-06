package com.github.xzb617.cappuccino.commons.data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务
 * @author xzb617
 */
public class Server {

    /**
     * 服务地址
     */
    private String address;

    /**
     * 服务是否存活
     */
    private boolean alive = true;

    /**
     * 权重，默认：1
     */
    private Integer weight = 1;

    /**
     * 连接失败计数器
     */
    private AtomicInteger connectFailedCounter;

    public Server() {
        this.connectFailedCounter = new AtomicInteger(0);
    }

    public Server(String address, boolean alive, Integer weight) {
        this.address = address;
        this.alive = alive;
        this.weight = weight;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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
                ", weight=" + weight +
                ", connectFailedCounter=" + connectFailedCounter +
                '}';
    }
}
