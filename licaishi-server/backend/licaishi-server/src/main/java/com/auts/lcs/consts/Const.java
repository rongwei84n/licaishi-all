package com.auts.lcs.consts;

public interface Const {

    interface ThirdPart {
        String PHICOMM_ACCOUNT_URL = "https://accountsym.phicomm.com/v1/accountDetail";
    }

    String DEFAULT_CHARSET = "utf-8";

    String TOKEN_PREFIX = "Bearer";

    /**
     * header头部.
     */
    String AUTHORIZATION = "Authorization";

    //产品类参数
    String RECOMMEND_TYPE = "recommendType";
    String PAGE_NO = "pageNo";
    String PAGE_SIZE = "pageSize";
    String TYPE = "type";
    String P_CODE= "pCode";


    String X_APP_INFO = "x-app-info";

    int STATUS_OK = 200;
    int STATUS_ERROR = 1;

    // 错误码定义， 10000以上定义为错误码
    // 请求参数类错误
    interface ErrorCode {

        int COMMON_ERROR = 10001;
        int REQUEST_NO_PARAS = 10002;

        interface Account {
            int OK = 0;
            //注册类错误码
            int REGIST_VERCODE_ERROR = 1; //验证码错误
            int REGIST_VERCODE_OVERDUE = 2; //验证码过期
            int REGIST_GRANTCODE_ERROR = 11; //授权码错误
            int REGIST_ACCOUNT_EXISTS = 14;//账户已经存在，可以直接登录
            int REGIST_VERCODE_USED = 23;//验证码已使用
            int REGIST_PHONE_ERROR = 34; //手机号格式错误
            int REGIST_ERROR = 50; //系统错误

            //登录类错误码
            int LOGIN_USER_NOT_EXIST = 7;
            int LOGIN_PASSWORD_ERROR = 8;
            int LOGIN_GRANTCODE_ERROR = 11;
            int LOGIN_PARA_ERROR = 12;
            int LOGIN_PASSWORD_NOT_SET = 15;
            int LOGIN_KICK_MULTI_LOGIN = 30;
            int LOGIN_PHONE_ERROR = 34;
            int LOGIN_ERROR = 50;

            //token
            int TOKEN_OK = 0;
            int TOKEN_NEED_REFRESH = 1;
            int TOKEN_INVILID = 2;
            String STR_TOKEN_INVILID = "TOKEN INVILID";
        }
    }
}
