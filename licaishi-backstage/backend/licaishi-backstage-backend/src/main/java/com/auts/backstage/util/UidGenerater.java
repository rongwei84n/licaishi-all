package com.auts.backstage.util;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UidGenerater {
    private static Logger logger = LogManager.getLogger(UidGenerater.class);

    private BigDecimal uid = new BigDecimal("0");

    public void init() {

    }

    public static void setUid(String initValue) {
        logger.info("Set init value [{}]", initValue);
        Holder.INS.setUidInner(initValue);
    }

    private void setUidInner(String initValue) {
        uid = new BigDecimal(initValue);
    }

    /**
     * 在当前最大uid的基础上+1，产生一个新的uid并返回.
     */
    public static long gen() {
        return Holder.INS.genInner();
    }

    private synchronized long genInner() {
        uid = uid.add(new BigDecimal("1"));
        return uid.longValue();
    }


    private static class Holder {
        private static final UidGenerater INS = new UidGenerater();
    }
}
