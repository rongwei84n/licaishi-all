package com.auts.lcscli.constants;


import com.auts.lcscli.BuildConfig;

/**
 * Url配置
 * Created by qisheng.lv on 2017/4/12.
 */
public interface UrlConfig {

    interface LocalUrl {
        String URL_ROUTER_HOST = "http://192.168.2.1";
    }

    interface CloudAccountUrl {
        String URL_HOST = BuildConfig.host_cloud_acount;
        String GET_AUTHORIZATION = URL_HOST + "/srv/v1/authorization"; //获取授权码
        String LOGIN = URL_HOST + "/srv/v1/login"; //登陆云
        String REFRESH_TOKEN = URL_HOST + "/srv/v1/token"; //刷新token
        String CHECK_TOKEN = URL_HOST + "/v1/verifyToken"; //校验token
        String CHECK_PHONE = URL_HOST + "/srv/v1/checkPhonenumber"; //检查手机号
        String LOGOUT = URL_HOST + "/srv/v1/logout"; //退出登陆
        String GET_CAPTCHA = URL_HOST + "/v1/captcha"; //获取图形验证码
        String GET_VER_CODE = URL_HOST + "/srv/v1/verificationMsg"; //获取验证码
        String CHECK_VER_CODE = URL_HOST + "/srv/v1/verifyVerificationCode"; //校验短信验证码
        String REGISTER = URL_HOST + "/srv/v1/account"; //注册账号
        String OLD_ACCOUNT_VERIFICATION = URL_HOST + "/v1/oldAccountVerification"; //校验旧的手机号或邮箱号
        String REBIND = URL_HOST + "/v1/rebind"; //修改绑定关系
        String ACCOUNT_DETAIL = URL_HOST + "/srv/v1/accountDetail"; //获取用户云账户公共信息
        String PROPERTY = URL_HOST + "/srv/v1/property"; //修改用户云账户公共信息
        String PASSWORD = URL_HOST + "/srv/v1/password"; //修改密码
        String FORGOT_PWD = URL_HOST + "/srv/v1/forgetpassword"; //忘记密码重新设置

        String UPLOAD_VOUCHER = URL_HOST + "/srv/v1/order/uploadPayPhote"; //上传打款凭证
    }

    interface CloudPortrait {
        String URL_HOST = BuildConfig.host_cloud_portrait;
        String UPLOAD_BASE64 = URL_HOST + "/srv/v1/pic/uploadBase64"; //上传Base64字符串
        String AVATAR_URL = URL_HOST + "/pic/avatarUrl"; //获取头像url
    }

    interface SoftApInfoUrl {
        String URL_HOST = "http://10.10.10.1:8000";
        String READ_DEVICE_INFO_URL = URL_HOST + "/config-read";
        String WRITE_SSID_INFO_URL = URL_HOST + "/config-write-uap";
        String GET_CONN_STATE_URL = URL_HOST + "/conn-state";
        String CLOSE_DEVICE_AP_URL = URL_HOST + "/close-ap";
    }

    interface SzUrl {
        String URL_HOST = BuildConfig.host_sz;
        String DEVICES_URL = URL_HOST + "/phihome/v1/user/devices";
        String DEVICE_CONTROL = URL_HOST + "/phihome/v1/device/";
        String CHECK_BIND_STATUS = URL_HOST + "/v1/user/device";
        String DEVICE_TYPES = URL_HOST + "/v1/device/device_types";
        String MODIFTY_DEVICE_INFO = URL_HOST + "/v1/user/device/";
        //家庭管理
        String HOUSE_URL = URL_HOST + "/v1/user/families";
        String HOUSE_MODIFY_URL = URL_HOST + "/v1/user/family/";
        String USER_CONFIG = URL_HOST + "/v1/user/config_message"; //通用配置
        String MY_DEVICE = URL_HOST + "/v1/user/devices"; //账号下全部设备
        String MY_DEVICE_REMIND = URL_HOST + "/v1/user/devices/change_task_remind"; //修改用户所有设备的任务提醒状态
        String TIMESTAMP = URL_HOST + "/v1/server/timestamp"; //获取时间戳
        String UPDATE_APP_CHECK = URL_HOST + "/v1/app/checkupdate"; //APP检查更新

        String GET_INSTRODUCTION = URL_HOST + "/v1/function/introduction";

        String GET_ORDER_ITEM_DETAIL = URL_HOST + "/srv/v1/order/orderDetail?orderNo=";

        String CANCEL_ORDER_ = URL_HOST + "/srv/v1/order/cancelOrder?orderNo=";

        String GET_MESSAGES = URL_HOST + "/v1/user/messages";//消息提醒
        String CHECK_UNREADMESSAGES = URL_HOST + "/v1/user/check_msg_read_status";//检查是否有未读消息

        String REGISTRATIONID = URL_HOST + "/v1/user/messages/";//更新对应的registrationid

        //场景
        String GET_SCENE = URL_HOST + "/v1/user/scences";
        String MODIFY_SCENE = URL_HOST + "/v1/user/scence/";
        String ADD_SCENE = URL_HOST + "/v1/user/scence";
        String GET_SCENEPIC = URL_HOST + "/v1/scence/pictures";
        String GET_MISSION = URL_HOST + "/v1/user/scence/";
        String CHECK_DEVICE_STATE = URL_HOST + "/v1/user/device/";
        String GET_QR_RPODUCTS = URL_HOST + "/v1/app/add_device/method/";
        String UPDATE_ROOM_CHECK = URL_HOST + "/v1/ota/checkupdate";
    }


}
