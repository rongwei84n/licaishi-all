package com.auts.lcssv.jsbridge;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qisheng.lv on 2017/7/31.
 */

public class JSData implements Serializable {
    private static final long serialVersionUID = 7431409662897982007L;

    private String message;

    private int showTime;

    private String url;

    private String requestHeader;

    private String requestBody;

    private String netAction;

    private String pushType;

    private String mqttBody;

    private int qos;

    private List<String> shadows;

    private String shadowName;

    private String shadowData;

    private String pageName;

    private String requestTopic;

    private String requestData;

    private String responseTopic;

    private String pageExtra;

    private int dataType;

    private String publishTopic;

    private String publishData;

    private String subscribeTopic;

    private String eventType;

    private String eventId;

    private String pageTitle;

    private int pageIndexModifier;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getShowTime() {
        return showTime;
    }

    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getNetAction() {
        return netAction;
    }

    public void setNetAction(String netAction) {
        this.netAction = netAction;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getMqttBody() {
        return mqttBody;
    }

    public void setMqttBody(String mqttBody) {
        this.mqttBody = mqttBody;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public List<String> getShadows() {
        return shadows;
    }

    public void setShadows(List<String> shadows) {
        this.shadows = shadows;
    }

    public String getShadowName() {
        return shadowName;
    }

    public void setShadowName(String shadowName) {
        this.shadowName = shadowName;
    }

    public String getShadowData() {
        return shadowData;
    }

    public void setShadowData(String shadowData) {
        this.shadowData = shadowData;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getRequestTopic() {
        return requestTopic;
    }

    public void setRequestTopic(String requestTopic) {
        this.requestTopic = requestTopic;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseTopic() {
        return responseTopic;
    }

    public void setResponseTopic(String responseTopic) {
        this.responseTopic = responseTopic;
    }

    public String getPageExtra() {
        return pageExtra;
    }

    public void setPageExtra(String pageExtra) {
        this.pageExtra = pageExtra;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getPublishTopic() {
        return publishTopic;
    }

    public void setPublishTopic(String publishTopic) {
        this.publishTopic = publishTopic;
    }

    public String getPublishData() {
        return publishData;
    }

    public void setPublishData(String publishData) {
        this.publishData = publishData;
    }

    public String getSubscribeTopic() {
        return subscribeTopic;
    }

    public void setSubscribeTopic(String subscribeTopic) {
        this.subscribeTopic = subscribeTopic;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public int getPageIndexModifier() {
        return pageIndexModifier;
    }

    public void setPageIndexModifier(int pageIndexModifier) {
        this.pageIndexModifier = pageIndexModifier;
    }
}


