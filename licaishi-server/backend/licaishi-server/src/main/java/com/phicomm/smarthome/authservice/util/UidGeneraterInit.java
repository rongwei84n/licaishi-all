package com.phicomm.smarthome.authservice.util;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.phicomm.smarthome.authservice.model.dao.AccountModel;
import com.phicomm.smarthome.authservice.service.AccountService;

/**
 * 初始化Uid,从数据库查询出最大的uid,然后存储起来.这个类只在程序起动的时候起动一次.
 * @author huangrongwei
 *
 */
@Component
public class UidGeneraterInit {
    private static Logger logger = LogManager.getLogger(UidGeneraterInit.class);

    /** 实体对象. */
    public static UidGeneraterInit instance;

    @Autowired
    private AccountService accountService;

    /**
     * 自动调用的初始化方法.
     */
    @PostConstruct
    public void init() {
        instance = this;
    }

    /**
     * 初始化入口.
     *
     * @see PhiHomeCommandMain
     */
    public void start() {
        logger.debug("Uid generater init starting...");
        AccountModel model = accountService.queryMaxUid();
        if (model == null) {
            UidGenerater.setUid("0");
        } else {
            UidGenerater.setUid(model.getUid());
        }
    }
}
