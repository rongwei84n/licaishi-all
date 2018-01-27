package com.phicomm.smarthome.authservice.consts;

public interface Const {

    interface Mqtt {
        String TOPIC_DEV_BIND_RSP_PREFIX = "$things/presence/response/bind/%s/%s";
        String TOPIC_DEV_UNBIND_RSP_PREFIX = "$things/presence/response/unbind/%s/%s";
        String TOPIC_DEV_ONTIME_RSP_PREFIX = "$things/presence/response/ontime/%s/%s";

        String IOT_CONNETCT_ACTION = "iot:connect";
        String IOT_PUBLISH_ACTION = "iot:publish";
        String IOT_SUBSCRIBE_ACTION = "iot:subscribe";
        String IOT_RECEIVE_ACTION = "iot:receive";
        String EFFECT_ALLOW = "allow";
        String EFFECT_DENY = "deny";
    }

    public static final int INNER_SERVICE_REQUEST_READ_TIMEOUT = 10000;
    public static final int INNER_SERVICE_REQUEST_CONNECT_TIMEOUT = 10000;
    public static final int AUTH_DEVICE_ID_OK = 0;
}
