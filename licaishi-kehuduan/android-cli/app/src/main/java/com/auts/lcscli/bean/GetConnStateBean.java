package com.auts.lcscli.bean;

import java.io.Serializable;

/**
 * 获取设备和路由器以及与服务器的连接状态。
 *
 * @author xiaolei.yang
 * @date 2017/7/7
 */

public class GetConnStateBean extends BaseResponse implements Serializable{
    private static final long serialVersionUID = 8926762842810075538L;

    private int conn_to_server;//与MQTT服务器连接状态  1-连接上，0-未连接上
    private int conn_to_router;//与路由器连接状态   1-连接上，0-未连接上

    public int getConn_to_server() {
        return conn_to_server;
    }

    public void setConn_to_server(int conn_to_server) {
        this.conn_to_server = conn_to_server;
    }

    public int getConn_to_router() {
        return conn_to_router;
    }

    public void setConn_to_router(int conn_to_router) {
        this.conn_to_router = conn_to_router;
    }

    @Override
    public String toString() {
        return "GetConnState{" +
                "conn_to_server=" + conn_to_server +
                ", conn_to_router=" + conn_to_router +
                '}';
    }
}
