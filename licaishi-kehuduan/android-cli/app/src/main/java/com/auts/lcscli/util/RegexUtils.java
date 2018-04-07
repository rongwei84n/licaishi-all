package com.auts.lcscli.util;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用正则校验数据的工具类
 * Created by qisheng.lv on 2017/4/12.
 */
public class RegexUtils {
    /**
     * 密码强度定义
     */
    public static final int PWD_STRENGTH_WEAK = 1;
    public static final int PWD_STRENGTH_MIDDLE = 2;
    public static final int PWD_STRENGTH_STRONG = 3;

    /**
     * 验证日期
     *
     * @return 验证成功返回true 验证失败false;
     * @格式 yyyy-mm-dd
     * @考虑了二月.闰年等等
     */
    public static boolean checkDate(String date) {
        if (!isNull(date)) {
            String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d" +
                    "|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$";
            return Pattern.matches(regex, date);
        }
        return false;
    }

    /**
     * 验证用户名
     */
    public static boolean checkUserName(String username) {
        if (!isNull(username)) {
            String regex = "^([a-zA-Z]{1})\\w{5,15}$";
            return Pattern.matches(regex, username);
        }
        return false;
    }

    /**
     * 验证ID列表
     *
     * @return 验证成功返回true 验证失败false;
     * @格式 yyyy-mm-dd
     * @考虑了二月.闰年等等
     */
    public static boolean checkUserIDList(String date) {
        if (!isNull(date)) {
            String regex = "^([1-9]\\d+){1}(,{1}([1-9]\\d+))*$";
            return Pattern.matches(regex, date);
        }
        return false;
    }

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        if (!isNull(email)) {
            String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
            return Pattern.matches(regex, email);
        }
        return false;
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        if (!isNull(idCard)) {
            String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
            return Pattern.matches(regex, idCard);
        }
        return false;
    }


    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p>
     *              <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9
     *              的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
     *              </p>
     *              <p>
     *              <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。
     *              </p>
     *              <p>
     *              <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
     *              </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        if (!isNull(phone)) {
            String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
            return Pattern.matches(regex, phone);
        }
        return false;
    }

    /**
     * @param phone
     * @return
     * @Title: isMobilePhone
     * @Description: 验证是否为手机号码
     * @return: boolean
     */
    public static boolean checkMobilePhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 验证整数（正整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkInteger(String digit) {
        if (!isNull(digit)) {
            String regex = "^\\d+$";
            return Pattern.matches(regex, digit);
        }
        return false;
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {
        if (!isNull(digit)) {
            String regex = "\\-?[1-9]\\d+";
            return Pattern.matches(regex, digit);
        }
        return false;
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {
        if (!isNull(decimals)) {
            String regex = "^[0-9]+(\\.[0-9]+)?$";
            return Pattern.matches(regex, decimals);
        }
        return false;
    }

    /**
     * 验证空白字符
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBlankSpace(String blankSpace) {
        if (!isNull(blankSpace)) {
            String regex = "\\s*|\r|\t|\n";
            return Pattern.matches(regex, blankSpace);
        }
        return false;
    }

    /**
     * 过滤特殊字符
     *
     * @param string
     * @return 返回过滤之后的字符
     */
    public static String stringFilter(String string) {
        if (!isNull(string)) {
            // 清除掉所有特殊字符
            String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(string);
            return m.replaceAll("").trim();
        }
        return "";
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) {
        if (!isNull(chinese)) {
            String regex = "^[\\u4E00-\\u9FA5]+$";
            return Pattern.matches(regex, chinese);
        }
        return false;
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday 日期，格式：1992-09-03，
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) {
        if (!isNull(birthday)) {
            String regex = "[0-9]{4}([-/])\\d{1,2}\\1\\d{1,2}";
            return Pattern.matches(regex, birthday);
        }
        return false;
    }

    /**
     * 验证昵称(中文或者ASCII[33-126])
     *
     * @param nickname 昵称
     * @return 验证成功返回true，反之亦然
     */
    public static boolean checkNickname(String nickname) {
        if (!isNull(nickname)) {
            char[] chars = nickname.toCharArray();
            for (char c : chars) {
                if (!EditTextUtils.isChinese(c) && (c < 33 || c > 126)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证各类名称(中文或者ASCII[33-126])
     *
     * @param content 要校验的内容
     * @param hint  提示
     * @return
     */
    public static boolean checkNameToast(String content, String hint) {
        if (TextUtils.isEmpty(content)) {
            ToastUtil.show(hint + "不能为空");
            return false;
        }

        char[] chars = content.toCharArray();
        for (char c : chars) {
            if (!EditTextUtils.isChinese(c) && (c < 33 || c > 126)) {
                ToastUtil.show(hint + "不能包含特殊字符");
                return false;
            }
        }
        return true;
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或
     *            http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) {
        if (!isNull(url)) {
            String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
            return Pattern.matches(regex, url);
        }
        return false;
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) {
        if (!isNull(postcode)) {
            String regex = "[1-9]\\d{5}";
            return Pattern.matches(regex, postcode);
        }
        return false;
    }

    /**
     * 获取页面中所有email地址
     *
     * @param surl
     * @throws Exception
     */
    public static void WebCrawlersEmail(String surl) throws Exception {
        if (!isNull(surl)) {
            URL url = new URL(surl);
            // 打开连接
            URLConnection conn = url.openConnection();
            // 设置连接网络超时时间
            conn.setConnectTimeout(1000 * 10);
            // 读取指定网络地址中的文件
            BufferedReader bufr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            String regex = "[a-zA-Z0-9_-]+@\\w+\\.[a-z]+(\\.[a-z]+)?"; // 匹配email的正则
            Pattern p = Pattern.compile(regex);
            while ((line = bufr.readLine()) != null) {
                Matcher m = p.matcher(line);
                while (m.find()) {
                    System.out.println(m.group());// 获得匹配的email
                }
            }
        }
    }


    /**
     * 去除网页标签
     *
     * @return String
     */
    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "/r/n");
        // <br><br/>替换为换行
        content = content.replaceAll("<br//s*/?>", "/r/n");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("//<.*?>", "");
        // 还原HTML
        // content = HTMLDecoder.decode(content);
        return content;
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {
        String regex = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
        return Pattern.matches(regex, ipAddress);
    }

    /**
     * 匹配MAC地址(简单匹配，格式，如：00:E0:20:1C:7C:0C，没有匹配MAC段的大小)
     *
     * @param mac MAC地址字符串
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMac(String mac) {
        if (!isNull(mac)) {
            String regex = "^[a-fA-F0-9]{2}(:[a-fA-F0-9]{2}){5}$";
            return Pattern.matches(regex, mac);
        }
        return false;
    }

    /**
     * 判断是否是IP地址
     *
     * @param string
     * @return
     */
    public static boolean isIP(String string) {
        return !(!checkIpAddress(string) || TextUtils.isEmpty(string));
    }

    /**
     * 验证是否为url地址
     *
     * @param string
     * @return
     */
    public static boolean isUrl(String string) {
        return !(!checkURL(string) || TextUtils.isEmpty(string));
    }

    /**
     * 验证字符串是否为null或为""
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * 判断是否为密码
     *
     * @param password
     * @return
     * @all -`=\[];',./~!@#$%^&*()_+|{}:"<>?
     * @pattern -`=;',./~!@#$%^&*()_+|{}:<>/?]+$
     * @delete " \ []
     */
    public static boolean isPassword(String password) {
        if (!isNull(password)) {
            String regex = "^[A-Za-z0-9-`=;',./~!@#$%^&*()_+|{}:<>/?]+$";
            return Pattern.matches(regex, password);
        }
        return false;
    }

    /**
     * 获取密码强度
     * <p/>
     * <ul>
     * <li>强：位数>13且种类=3</li>
     * <li>弱：位数<11且种类=1</li>
     * <li>中：其他</li>
     * </ul>
     *
     * @return
     */
    public static int getPwdStrength(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            return PWD_STRENGTH_WEAK;
        }

        if (Pattern.matches("[0-9]{0,10}", pwd) || Pattern.matches("[a-zA-Z]{0,10}", pwd)
                || Pattern.matches("[-`=;',./~!@#$%^&*()_+|{}:<>?]{0,10}", pwd)) {
            // System.out.println("弱");
            return PWD_STRENGTH_WEAK;
        } else if (Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[-`=;',./~!@#$%^&*()_+|{}:<>/?]).{13,}", pwd)) {
            // System.out.println("强");
            return PWD_STRENGTH_STRONG;
        }

        // System.out.println("中");
        return PWD_STRENGTH_MIDDLE;
    }

    /**
     * 校验ssid
     *
     * @param ssid
     * @return
     */
    public static boolean checkSSID(String ssid) {
        if (!isNull(ssid)) {
            String regex = "[\\w~\\!@#\\$%\\^&\\*\\-\\.\\'\\?\\u4e00-\\u9fa5]+";
            return Pattern.matches(regex, ssid);
        }
        return false;
    }

    /**
     * 校验谁在上网备注
     *
     * @param remark
     * @return
     */
    public static boolean checkRemark(String remark) {
        if (!isNull(remark)) {
            String regex = "[\\w~\\!@#\\$%\\^&\\*\\-\\.\\'\\?\\u4e00-\\u9fa5]+";
            return Pattern.matches(regex, remark);
        }
        return false;
    }

    /**
     * 校验ASCII码表33-126的字符
     *
     * @param content
     * @return
     */
    public static boolean checkAscii(String content) {
        if (!isNull(content)) {
            String regex = "[!-~]+";
            if (Pattern.matches(regex, content)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验密码
     *
     * @param pwd 6-20位 ASCII码仅支持33-126（含）之间的常见字符，不包括空格
     * @return
     */
    public static boolean checkPassword(String pwd) {
        if (!isNull(pwd) && pwd.length() >= 6 && pwd.length() <= 20) {
            String regex = "[!-~]+";
//            String regex = "[\\w~\\!@#\\$%\\^&\\*]+";
            if (Pattern.matches(regex, pwd)) {
                return !hadCn(pwd);
            }
        }
        return false;
    }

    /**
     * 校验密码
     *
     * @param pwd 6-20位 ASCII码仅支持33-126（含）之间的常见字符，不包括空格
     * @return
     */
    public static boolean checkPwdToast(String pwd) {
        if (!isNull(pwd) && pwd.length() >= 6 && pwd.length() <= 20) {
            String regex = "[!-~]+";
//            String regex = "[\\w~\\!@#\\$%\\^&\\*]+";
            if (Pattern.matches(regex, pwd) && !hadCn(pwd)) {
                return true;
            }
        }
        ToastUtil.show("密码不能包含特殊字符，请重新输入");
        return false;
    }

    /**
     * 是否含有中文
     *
     * @param str
     * @return
     */
    public static boolean hadCn(String str) {
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 判断子网掩码是否合法
     *
     * @param ipValue
     * @return true 是 false 不是
     */
    public static boolean isNetMask(String ipValue) {
        String[] ips = ipValue.split("\\.");
        if (ips.length < 4) {
            return false;
        }
        StringBuffer sbBinaryVal = new StringBuffer();
        StringBuffer sbBinaryStr = new StringBuffer();
        for (String ip : ips) {
            int binaryParam = 0;
            try {
                binaryParam = Integer.parseInt(ip);
            } catch (NumberFormatException e) {
                binaryParam = 0;
            }

            String binaryStr = Integer.toBinaryString(binaryParam);

            sbBinaryStr.setLength(0);
            sbBinaryStr.append(binaryStr);

            int times = 8 - binaryStr.length();

            for (int j = 0; j < times; j++) {
                sbBinaryStr.append('0').append(binaryStr);
            }

            sbBinaryVal.append(sbBinaryStr.toString());
        }
        String regx = "^[1]*[0]*$";
        return isRegx(sbBinaryVal.toString(), regx);
    }

    public static boolean isRegx(String binaryVal, String regx) {
        return Pattern.matches(regx, binaryVal);
    }

    /**
     * 校验营业执照
     *
     * @param code
     * @return
     */
    public static boolean checkLicense(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        if (code.length() == 18) {
            boolean isDigit = false;// 定义一个boolean值，用来表示是否包含数字
            boolean isLetter = false;// 定义一个boolean值，用来表示是否包含字母
            for (int i = 0; i < code.length(); i++) {
                if (Character.isDigit(code.charAt(i))) { // 用char包装类中的判断数字的方法判断每一个字符
                    isDigit = true;
                }
                if (Character.isLetter(code.charAt(i))) { // 用char包装类中的判断字母的方法判断每一个字符
                    isLetter = true;
                }
            }
            if (isDigit && isLetter) {
                return true;
            } else {
                return false;
            }
        } else if (code.length() == 15) {
            if (code.charAt(0) == '0') {
                return false;
            } else {
                return isNumber(code);
            }
        } else {
            return false;
        }
    }

    /**
     * @param number
     * @return
     * @Title: isNumber
     * @Description: 是否为0-9的数字
     * @return: boolean
     */
    public static boolean isNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

}
