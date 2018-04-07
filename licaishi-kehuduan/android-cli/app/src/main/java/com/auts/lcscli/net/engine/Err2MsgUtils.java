package com.auts.lcscli.net.engine;

import android.text.TextUtils;

import com.auts.lcscli.constants.AppConstans;
import com.auts.lcscli.util.LogUtils;
import com.auts.lcscli.util.SpfUtils;

import java.util.HashMap;

/**
 * 该工具类统一将网络请求的错误码转化成对应的信息，以便上层解读
 *
 * @author qisheng.lv
 * @date 2017/4/12
 */
public class Err2MsgUtils {
    public static final String CODE_UNKNOW_ERROR = "301";
    public static final String CODE_NET_DISABLE = "302";
    public static final String CODE_NET_TIMEOUT = "303";
    public static final String CODE_NO_RESPONSE = "304";
    public static final String CODE_PARSE_ERROR = "305";
    public static final String CODE_TOKEN_TIMEOUT = "306";

    private static HashMap<String, String> mMap;

    static {
        mMap = new HashMap<>();
        initMsg();
    }

    private static void initMsg() {
        //自定义错误
        mMap.put(CODE_UNKNOW_ERROR, "网络异常，请稍后再试");
        mMap.put(CODE_NET_DISABLE, "当前网络不可用，请检查网络设置");
        mMap.put(CODE_NET_TIMEOUT, "网络异常，请稍后再试");//网络超时
        mMap.put(CODE_NO_RESPONSE, "服务异常，请稍后再试");
        mMap.put(CODE_PARSE_ERROR, "服务器异常，请稍后再试(" + CODE_PARSE_ERROR + ")"); //解析出错
        mMap.put(CODE_TOKEN_TIMEOUT, "token失效，请稍后再试");

        //服务器返回的错误
        mMap.put("1", "请输入正确的验证码");
        mMap.put("2", "验证码已过期，请重新获取");
        mMap.put("3", "尚未经过手机号验证");
        mMap.put("4", "旧密码错误");
        mMap.put("5", "token失效");
        mMap.put("7", "帐号或密码错误，请重新输入");
        mMap.put("8", "帐号或密码错误，请重新输入");
        mMap.put("11", "授权码错误，请重试");
        mMap.put("12", "参数错误");
        mMap.put("13", "获取短信验证码失败");
        mMap.put("14", "该账户已存在");
        mMap.put("18", "图片格式错误");
        mMap.put("19", "图片为空");
        mMap.put("20", "用户未设置头像");
        mMap.put("23", "验证码已使用，请重新获取");
        mMap.put("26", "账号被踢出，请重新登录");
        mMap.put("25", "邮箱已经注册");
        mMap.put("30", "多端登录账户被踢出");
        mMap.put("32", "密码格式错误");
        mMap.put("36", "图片验证码过期，请刷新");
        mMap.put("37", "图片验证码错误，请重新输入");
        mMap.put("38", "短信验证码请求过快");
        mMap.put("39", "短信验证码请求超出限制");
        mMap.put("50", "服务器异常");

        //深圳后台服务器返回的错误
        mMap.put("400", "JSON 无效");
        mMap.put("401", "未授权");
        mMap.put("403", "禁止");
        mMap.put("404", "事物未找到");
        mMap.put("409", "版本冲突");
        mMap.put("413", "有效负载超出允许的最大值");
        mMap.put("415", "文档编码不受支持");
        mMap.put("429", "请求过多");
        mMap.put("500", "服务器内部错误");
        mMap.put("10006", "账号被踢出，请重新登录");
        mMap.put("10008", "操作数据库失败");
    }

    public static String getErrMsg(String code) {
        //授权码错误，将保存在本地的授权码清空
        if (code.equals("11")) {
            SpfUtils.put(AppConstans.Sp.AUTHORIZATION_CODE, "");
        }
        String message = "";
        if (mMap != null) {
            message = mMap.get(code);
        }

        LogUtils.debug("Err2MsgUtils getErrMsg: " + code + " * " + message);
        return TextUtils.isEmpty(message) ? mMap.get(CODE_UNKNOW_ERROR) : message;
    }

}
