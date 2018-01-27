package com.phicomm.smarthome.authservice.consts;

public interface Const {

    String DEFAULT_CHARSET = "utf-8";
    /**
     * header头部
     */
    String AUTHORIZATION = "Authorization";

    String PHI_HOME_PREFIX = "PHIHOME";

    String X_APP_INFO = "x-app-info";

    int STATUS_OK = 200;
    int STATUS_ERROR = 1;

    // 错误码定义， 10000以上定义为错误码
    // 请求参数类错误
    int STATUS_COMMON_FAILED = 10001;
    int STATUS_NO_PARA_IN_REQUEST = 10002;// 缺少请求参数

    int STATUS_NO_TOKEN_IN_REQUEST = 10003;// 请求参数没有token
    int STATUS_TOKEN_INVALID = 10004;// token失效
    int STATUS_FAILED_GET_PHICOMM_ACCOUNT = 10005;// 没有获取到对应的 斐讯云账号
    int STATUS_LOGIN_BY_OTHER_PHIONE = 10006; // 已经在其他移动端登录
    int STATUS_NO_ACCOUNT_DETAIL = 10007; // 用户还未设置账户户详情
    int STATUS_DATABASE_OPERATE_ERROR = 10008;// 操作数据库错误，比如插入一条数据
    int STATUS_INVAID_PARA = 10009; // 请求参数不合法
    int STATUS_NO_UID = 10010; // 请求中缺少uid
    int STATUS_GET_JOSN_DATA_ERROR = 10011; // 获取json数据出错
    int STATUS_NO_DEVICE_ID = 10012; // 没有deviceId
    int STATUS_NO_DEVICE_ERROR = 10013;// 未在数据库中查找到设备信息
    int STATUS_OPERATE_MONGODB_FAILED = 10014;// 操作MONGODB失败
    int STATUS_TOPIC_FORMATE_ERR = 10015;// topic格式不正确
    int STATUS_NO_DEVICE_INFO = 10016;// 未查询到该设备相关的shadow信息
    int STATUS_INNER_AUTH_FAILED = 10017;// mqtt消息鉴权失败
    int STATUS_HYSTRIX_ERROR = 10018;// 熔断器错误
    int STATUS_DEVICE_UNBIND_UPGRADE_ERR = 10019; // 该设备已被解绑,请重新绑定后再进行检查更新操作

    // 业务处理类错误
    int STATUS_NO_MATCH_DEVICE = 11001; // 用户下没有对应的设备
    int STATUS_GET_PASSTHROUGH_RESULT_FAIL = 11002; // 获取透传结果失败
    int STATUS_DEVICE_OFFLINE = 11003; // 设备不在线
    int STATUS_DEVICE_ID_INVALID = 11004; //
    int STATUS_NO_AREA_INFO = 11005; // 获取区域信息失败

    // App业务升级类错误12001开始
    int STATUS_APP_UPDATE_SYSTEM_ERROR = 12001; // 系统发生错误
    int STATUS_APP_UPDATE_NO_VERSION = 12002; // 没有版本更新

    // OTA升级类错误从13001开始
    int STATUS_OTA_APP_UPGRADE_DELAY = 13001;// 用户在APP页面停留过长，导致与后台系统固件版本不符
}
