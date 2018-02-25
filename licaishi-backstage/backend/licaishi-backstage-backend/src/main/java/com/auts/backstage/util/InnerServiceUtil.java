package com.auts.backstage.util;

//import com.netflix.appinfo.InstanceInfo;
//import com.netflix.discovery.EurekaClient;

public class InnerServiceUtil {
//    private interface Services {
//        static final String SERVICE_PHIHOME = "smarthome-phihome";
//        static final String SERVICE_PHIHOME_COMMAND = "smarthome-phihome-command";
//        static final String SERVICE_PHIPUSH = "push.messagepush";
//        static final String SERVICE_PHIHOME_COMMON_SERVICE = "smarthome-common-service";
//        static final String SERVICE_PHIHOME_DATA_PROCESS = "smarthome-phihome-data-process";
//        static final String SERVICE_SMARTHOME_MANAGER_BACKEND = "smarthome-manager-backend";
//    }
//
//    private InnerServiceUtil() {
//    }
//
//    private static class Holder {
//        private static InnerServiceUtil instance = new InnerServiceUtil();
//    }
//
//    public static String getPhihomeUrl(EurekaClient discoveryClient, String end) {
//        return Holder.instance.getInner(discoveryClient, Services.SERVICE_PHIHOME, end);
//    }
//
//    public static String getPhihomeCommandUrl(EurekaClient discoveryClient, String end) {
//        return Holder.instance.getInner(discoveryClient, Services.SERVICE_PHIHOME_COMMAND, end);
//    }
//
//    public static String getPhiPushUrl(EurekaClient discoveryClient, String end) {
//        return Holder.instance.getInner(discoveryClient, Services.SERVICE_PHIPUSH, end);
//    }
//
//    public static String getPhihomeCommonServiceUrl(EurekaClient discoveryClient, String end) {
//        return Holder.instance.getInner(discoveryClient, Services.SERVICE_PHIHOME_COMMON_SERVICE, end);
//    }
//
//    public static String getPhihomeDataProcessUrl(EurekaClient discoveryClient, String end) {
//        return Holder.instance.getInner(discoveryClient, Services.SERVICE_PHIHOME_DATA_PROCESS, end);
//    }
//
//    public static String getSmartHomeManagerBackendUrl(EurekaClient discoveryClient, String end) {
//        return Holder.instance.getInner(discoveryClient, Services.SERVICE_SMARTHOME_MANAGER_BACKEND, end);
//    }
//
//    private String getInner(EurekaClient discoveryClient, String service, String end) {
//        InstanceInfo instance = discoveryClient.getNextServerFromEureka(service, false);
//        return instance.getHomePageUrl() + end;
//    }
}
