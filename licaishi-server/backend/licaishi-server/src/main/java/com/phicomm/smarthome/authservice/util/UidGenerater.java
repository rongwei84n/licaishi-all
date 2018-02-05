package com.phicomm.smarthome.authservice.util;

public class UidGenerater {
    private int uid = 0;

    public void init() {

    }

    /**
     * 在当前最大uid的基础上+1，产生一个新的uid并返回
     * @return
     */
    public static int gen() {
        return Holder.ins.genInner();
    }

    private int genInner() {
        return ++uid;
    }


    private static class Holder {
        private static final UidGenerater ins = new UidGenerater();
    }
}
