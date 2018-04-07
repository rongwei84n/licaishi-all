package com.auts.lcscli.constants;

import com.auts.lcscli.BuildConfig;

/**
 * 常量
 *
 * @author qisheng.lv
 * @date 2017/4/12
 */

public interface AppConstans {

    /**
     * 公共
     */
    interface Common {
        String PLATFORM_ID = "2015100011";

        String AUTHORIZATION = "Authorization";

        String INTENT_URL = "INTENT_URL";

        /**
         * 短信验证码默认倒计时
         */
        int REGISTER_CODE_TIME = 60;

        String HOUSE_NAME = "houseName";

        String HOUSE_PIC = "housePic";

        String ROOM_NAME = "roomName";

        String ROOM_PIC = "roomPic";

        String FAMILY_ID = "familyId";

        String ROOM_BEAN = "roombean";

        String HOUSE_BEAN = "housebean";

        String REQUEST_CODE_JS_OPEN_PAGE = "housebean";

        /**
         * 插座二维码
         */
        String ADD_DEVICE_QR_OUTLET = "https://phihome.phicomm.com/qr?m=phicomm&c=outlet&p=t1&s=p";
    }

    /**
     * 网络配置相关
     */
    interface NetConfig {
        int HTTP_CONNECT_TIME_OUT = 10 * 1000;
        int HTTP_READ_TIME_OUT = 10 * 1000;
        int HTTP_WRITE_TIME_OUT = 10 * 1000;
    }

    /**
     * SharedPreferences
     */
    interface Sp {
        String COOKIE_TOKEN = "COOKIE_TOKEN";

        String AUTHORIZATION_CODE = "AUTHORIZATION_CODE";

        String ACCESS_TOKEN = "ACCESS_TOKEN";

        String REFRESH_TOKEN = "REFRESH_TOKEN";

        String REFRESH_TOKEN_EXPIRE = "REFRESH_TOKEN_EXPIRE";

        String LOGIN_TIMESTAMP = "LOGIN_TIMESTAMP";

        String CLOUD_ACCOUNT_PHONE = "CLOUD_ACCOUNT_PHONE";

        String CLOUD_ACCOUNT_PWD = "CLOUD_ACCOUNT_PWD";

        String CLOUD_ACCOUNT_UID = "CLOUD_ACCOUNT_UID";

        /**
         * 使用前，动态获取UID拼接到该值前面作为key
         */
        String FAMILY_ID = "_FAMILY_ID";

        String TIMESTAMP = "TIMESTAMP";

        String LAST_SSID = "LAST_SSID";

        String SELECTED_HOUSE = "THE_HOUSE";

        String LOGIN_TOKEN_TIME = "LOGIN_TOKEN_TIME";

    }

    /**
     * 倒计时用到的SharedPreferences的key
     */
    interface CountdownTimeKey {
        String REGISTER_CODE_TIME = "REGISTER_CODE_TIME";
        String SECURITY_VERIFICATION_CODE_TIME = "SECURITY_VERIFICATION_CODE_TIME";
        String CHANGE_PHONE_NUMBER_CODE_TIME = "CHANGE_PHONE_NUMBER_CODE_TIME";
        String FORGOT_PWD_CODE_TIME = "FORGOT_PWD_CODE_TIME";
    }

    interface Products {
        /**
         * 插座
         */
        int TYPE_INSERTS = 1;
        /**
         * 插座设备ApStation模式下的ssid前缀
         */
        String INSERTS_SSID_NAME_PREFIX = "Phicomm_TC1";
//        String INSERTS_SSID_NAME = "EasyLink_500C49";
//        String INSERTS_SSID_NAME = "EasyLink_506C60";
//        String INSERTS_SSID_NAME = "SmartSound_AP";
//        String INSERTS_SSID_NAME = "EasyLink_5058AA";
    }

    interface LinkWay {
        /**
         * easyLink连接方式
         */
        int CONNECT_WAY_SMART_LINK = 0;
        //softAp连接方式
        int CONNECT_WAY_SMART_SOFT = 1;
    }

    /**
     * 取图
     */
    interface GetPhoto {
        String FILE_PROVIDER_AUTHORITY = BuildConfig.authorities;
        /**
         * 拍照
         */
        int GET_PHOTO_FROM_CAMERA = 1;
        /**
         * 相册取图
         */
        int GET_PHOTO_FROM_ALBUM = 2;
        /**
         * 裁剪
         */
        int CROP_IMAGE = 3;
        /**
         * 一条龙，拍照、取图后直接进行裁剪、压缩、并转换为base64
         */
        int ONE_DRAGON = 4;

        /**
         * 头像的宽
         */
        int UPLOAD_IMG_SRC_WIDTH = 100;
        /**
         * 头像的高
         */
        int UPLOAD_IMG_SRC_HEIGHT = 100;
        /**
         * 头像的大小，单位为K
         */
        int UPLOAD_IMG_SRC_LENGTH = 300;
    }


    interface RequestCode {
        int JS_OPEN_PAGE = 301;

    }

    /**
     * HTTPS和MQTT证书
     */
    interface Ca {
        String CA_DEV = "-----BEGIN CERTIFICATE-----\n"
                + "MIIE5TCCA82gAwIBAgIJAPnMYXnuD3tZMA0GCSqGSIb3DQEBCwUAMIGnMQswCQYD\n"
                + "VQQGEwJDTjELMAkGA1UECBMCU0gxETAPBgNVBAcTCFNIQU5HSEFJMRQwEgYDVQQK\n"
                + "EwtQaGljb21tIE9SRzEQMA4GA1UECxMHUEhJQ09NTTEXMBUGA1UEAxMOUGhpY29t\n"
                + "bSBPUkcgQ0ExEDAOBgNVBCkTB0Vhc3lSU0ExJTAjBgkqhkiG9w0BCQEWFm1lQGxv\n"
                + "YWNsaG9zdC5sb2NhbGhvc3QwHhcNMTcwOTI4MDM0MzU3WhcNMjcwOTI2MDM0MzU3\n"
                + "WjCBpzELMAkGA1UEBhMCQ04xCzAJBgNVBAgTAlNIMREwDwYDVQQHEwhTSEFOR0hB\n"
                + "STEUMBIGA1UEChMLUGhpY29tbSBPUkcxEDAOBgNVBAsTB1BISUNPTU0xFzAVBgNV\n"
                + "BAMTDlBoaWNvbW0gT1JHIENBMRAwDgYDVQQpEwdFYXN5UlNBMSUwIwYJKoZIhvcN\n"
                + "AQkBFhZtZUBsb2FjbGhvc3QubG9jYWxob3N0MIIBIjANBgkqhkiG9w0BAQEFAAOC\n"
                + "AQ8AMIIBCgKCAQEAuJjtLZJ+I6Cyy1NqsSMDSCJWiRPFJ1WdVjTDN8PwDdjAmrLk\n"
                + "COWr+M53faZuBm61f7AQzg0JKIKW+2Qc2oI/CRJ1vdskLie/T0cGKEVagUisWo0A\n"
                + "2SQQz7SMwaqUHRsWBbxjIiD6A5J+dgdRmLG+lN9SnUlABGsje2JZ8Ft7mz7q3m8f\n"
                + "A1TZeI5xg3M38GGYYBKXqF2ytbI5QGIARiGJBLxMbvbRnYN2IwtcrtUjgb1FltP2\n"
                + "gozDqjmessnFZGhBtDmYp9Uono5+FiyL+rlmzNJIKexLgF8KK7lZwL4gUKa4jiOw\n"
                + "9rZ1EGbmjnwjXQ1gE12355d8pI1vOLzK1GS0gwIDAQABo4IBEDCCAQwwHQYDVR0O\n"
                + "BBYEFL+mqz5m8E0Zy2VnzxD5YPRsDLefMIHcBgNVHSMEgdQwgdGAFL+mqz5m8E0Z\n"
                + "y2VnzxD5YPRsDLefoYGtpIGqMIGnMQswCQYDVQQGEwJDTjELMAkGA1UECBMCU0gx\n"
                + "ETAPBgNVBAcTCFNIQU5HSEFJMRQwEgYDVQQKEwtQaGljb21tIE9SRzEQMA4GA1UE\n"
                + "CxMHUEhJQ09NTTEXMBUGA1UEAxMOUGhpY29tbSBPUkcgQ0ExEDAOBgNVBCkTB0Vh\n"
                + "c3lSU0ExJTAjBgkqhkiG9w0BCQEWFm1lQGxvYWNsaG9zdC5sb2NhbGhvc3SCCQD5\n"
                + "zGF57g97WTAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQBRmX2F8SeE\n"
                + "e/sjSfJRNwmL57JjCyqBVATm7jwTvk/1Ywb2t3ecXIoOkP1QAo401HjrhbJxCHB4\n"
                + "gi6nRNiKIpUix2gnvGPfmsxxIt3dS7e0+q51TCpW39ZwbwMZH1YrP2PKGm9PW8ME\n"
                + "Wjrg5bg5Pb1jNawEaPpC28i7c6jYDEvWGHyx8/s1zfXtEKPPQvKpbo2bfzOwL41X\n"
                + "YyjDWd4Bz+YG4CxxM+8io7vA4m5paEH7KphznuD7fW7Vf2C0ikfx7suuTiRPOY+/\n"
                + "vI665FOSz0RrdScfPaxtcDNyjDo+yPR/hxhFmDJaP6XwlUnfoaAtdvRqsr7SVs1K\n"
                + "yddYjI/9VpW1\n"
                + "-----END CERTIFICATE-----";

        String CA_RELEASE = "-----BEGIN CERTIFICATE-----\n"
                + "MIIHAzCCBeugAwIBAgIQdSfD98MlqmJlqGAwP0QbRTANBgkqhkiG9w0BAQsFADB+\n"
                + "MQswCQYDVQQGEwJVUzEdMBsGA1UEChMUU3ltYW50ZWMgQ29ycG9yYXRpb24xHzAd\n"
                + "BgNVBAsTFlN5bWFudGVjIFRydXN0IE5ldHdvcmsxLzAtBgNVBAMTJlN5bWFudGVj\n"
                + "IENsYXNzIDMgU2VjdXJlIFNlcnZlciBDQSAtIEc0MB4XDTE3MDczMTAwMDAwMFoX\n"
                + "DTIwMDczMDIzNTk1OVowgZAxCzAJBgNVBAYTAkNOMQ8wDQYDVQQIDAbkuIrmtbcx\n"
                + "DzANBgNVBAcMBuS4iua1tzEzMDEGA1UECgwq5LiK5rW35paQ6K6v5pWw5o2u6YCa\n"
                + "5L+h5oqA5pyv5pyJ6ZmQ5YWs5Y+4MRIwEAYDVQQLDAnmioDmnK/pg6gxFjAUBgNV\n"
                + "BAMMDSoucGhpY29tbS5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIB\n"
                + "AQCpxOcJiYn7ug7G3Hq/h8cdUgg79AsNd+mqsGYk46EOj4JSQp43YBXdI3K7ST+s\n"
                + "c3Im9uk5HixTEVDNhNaDbz5EFN93NnGCYSI3rXzdKQ/8dEyHxWfkvBpyTVGLmT9W\n"
                + "RaXdUYsFojD4JbvCYW2SBXSLI+vNXRH5chvlMDZq6WoX8oP4JhOnqnrXMBuYQkXC\n"
                + "cni1EwBPrabHFbtf78Q3bp7ea2bMkz+NpKB/V79tCOrlovamtR0n4Z4PqQXM3f4C\n"
                + "I3afdVV+nfopVQIyFDKf45vJhFD7r4IPy3mFyuX49ikax3KwuaBwBnr2vKJZpI49\n"
                + "3YEdAvMYqgtLWMoJprjorrk1AgMBAAGjggNoMIIDZDAlBgNVHREEHjAcgg0qLnBo\n"
                + "aWNvbW0uY29tggtwaGljb21tLmNvbTAJBgNVHRMEAjAAMA4GA1UdDwEB/wQEAwIF\n"
                + "oDArBgNVHR8EJDAiMCCgHqAchhpodHRwOi8vc3Muc3ltY2IuY29tL3NzLmNybDBh\n"
                + "BgNVHSAEWjBYMFYGBmeBDAECAjBMMCMGCCsGAQUFBwIBFhdodHRwczovL2Quc3lt\n"
                + "Y2IuY29tL2NwczAlBggrBgEFBQcCAjAZDBdodHRwczovL2Quc3ltY2IuY29tL3Jw\n"
                + "YTAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwHwYDVR0jBBgwFoAUX2DP\n"
                + "YZBV34RDFIpgKrL1evRDGO8wVwYIKwYBBQUHAQEESzBJMB8GCCsGAQUFBzABhhNo\n"
                + "dHRwOi8vc3Muc3ltY2QuY29tMCYGCCsGAQUFBzAChhpodHRwOi8vc3Muc3ltY2Iu\n"
                + "Y29tL3NzLmNydDCCAfUGCisGAQQB1nkCBAIEggHlBIIB4QHfAHYA3esdK3oNT6Yg\n"
                + "i4GtgWhwfi6OnQHVXIiNPRHEzbbsvswAAAFdlmPatAAABAMARzBFAiEAy3NnACn7\n"
                + "LnSxx+nIy6UIpMnYBuOVva6DzQkSp+baaN8CIGr4pIr1gEV1Pe9J4Bpq3l1cL2SX\n"
                + "cfzb3wacBaTNsM8PAHYApLkJkLQYWBSHuxOizGdwCjw1mAT5G9+443fNDsgN3BAA\n"
                + "AAFdlmPa2QAABAMARzBFAiEA7oCAe2Dl/3dTFDx8n/GfadK+CdEfeO1dYOrTOabn\n"
                + "7w4CIBdurjzxOGmOpTK3c/vg6E1/IHWU6+nOGiLp60+JlxlVAHUA7ku9t3XOYLrh\n"
                + "Qmkfq+GeZqMPfl+wctiDAMR7iXqo/csAAAFdlmPa7wAABAMARjBEAiB0aP8NaB+9\n"
                + "TYBj6kItWwpf/jwoLVnq5yDPPWUnSkPpnQIgPE3MxU81lQwyPLr2WwrKswb8mVuc\n"
                + "AoHFE+3tyGyYQ18AdgC8eOHfxfY8aEZJM02hD6FfCXlpIAnAgbTz9pF/Ptm4pQAA\n"
                + "AV2WY9unAAAEAwBHMEUCIBPMbDD1DQScvijRThaz3t0MLDD0/eof3AVV2FkgCSir\n"
                + "AiEA17x+7rFE0gdjl9lxcTaFogJvLnea1bxzbgC68SjDWI4wDQYJKoZIhvcNAQEL\n"
                + "BQADggEBAFULXBM5aSIMyi1t/rz0iAnB7Ti0a1ZpVylo3E9MSB/SXyBftbvk6Gg1\n"
                + "eJdDR/JxtMYfQn5XEwRDoNb19srERnWpCyMxtlvXJqMfUmjSCwYlk1ZsE6X8PAQP\n"
                + "EIAaYPaRuzjPFIeqT96kT3cm0FdPsy5MdzKLYKGoaMB6LiFgpRaO8NIbrLfNs4T1\n"
                + "KvGfgBC2NH8rFWTsvuA8Y7qr56w6nNb+ThqW7WykFipu6z1J4M8XpvgVw7LJHkQd\n"
                + "4yAkGvK0f/i+vKM2XGA3tdteEKLjHWusfPKzCTk/EKUsoPRkTXFNxIOXx3GIoisP\n"
                + "JB/lOiE4wvm4M+NRBAjf/QeMQqkFX5Y=\n"
                + "-----END CERTIFICATE-----\n"
                + "-----BEGIN CERTIFICATE-----\n"
                + "MIIFODCCBCCgAwIBAgIQUT+5dDhwtzRAQY0wkwaZ/zANBgkqhkiG9w0BAQsFADCB\n"
                + "yjELMAkGA1UEBhMCVVMxFzAVBgNVBAoTDlZlcmlTaWduLCBJbmMuMR8wHQYDVQQL\n"
                + "ExZWZXJpU2lnbiBUcnVzdCBOZXR3b3JrMTowOAYDVQQLEzEoYykgMjAwNiBWZXJp\n"
                + "U2lnbiwgSW5jLiAtIEZvciBhdXRob3JpemVkIHVzZSBvbmx5MUUwQwYDVQQDEzxW\n"
                + "ZXJpU2lnbiBDbGFzcyAzIFB1YmxpYyBQcmltYXJ5IENlcnRpZmljYXRpb24gQXV0\n"
                + "aG9yaXR5IC0gRzUwHhcNMTMxMDMxMDAwMDAwWhcNMjMxMDMwMjM1OTU5WjB+MQsw\n"
                + "CQYDVQQGEwJVUzEdMBsGA1UEChMUU3ltYW50ZWMgQ29ycG9yYXRpb24xHzAdBgNV\n"
                + "BAsTFlN5bWFudGVjIFRydXN0IE5ldHdvcmsxLzAtBgNVBAMTJlN5bWFudGVjIENs\n"
                + "YXNzIDMgU2VjdXJlIFNlcnZlciBDQSAtIEc0MIIBIjANBgkqhkiG9w0BAQEFAAOC\n"
                + "AQ8AMIIBCgKCAQEAstgFyhx0LbUXVjnFSlIJluhL2AzxaJ+aQihiw6UwU35VEYJb\n"
                + "A3oNL+F5BMm0lncZgQGUWfm893qZJ4Itt4PdWid/sgN6nFMl6UgfRk/InSn4vnlW\n"
                + "9vf92Tpo2otLgjNBEsPIPMzWlnqEIRoiBAMnF4scaGGTDw5RgDMdtLXO637QYqzu\n"
                + "s3sBdO9pNevK1T2p7peYyo2qRA4lmUoVlqTObQJUHypqJuIGOmNIrLRM0XWTUP8T\n"
                + "L9ba4cYY9Z/JJV3zADreJk20KQnNDz0jbxZKgRb78oMQw7jW2FUyPfG9D72MUpVK\n"
                + "Fpd6UiFjdS8W+cRmvvW1Cdj/JwDNRHxvSz+w9wIDAQABo4IBYzCCAV8wEgYDVR0T\n"
                + "AQH/BAgwBgEB/wIBADAwBgNVHR8EKTAnMCWgI6Ahhh9odHRwOi8vczEuc3ltY2Iu\n"
                + "Y29tL3BjYTMtZzUuY3JsMA4GA1UdDwEB/wQEAwIBBjAvBggrBgEFBQcBAQQjMCEw\n"
                + "HwYIKwYBBQUHMAGGE2h0dHA6Ly9zMi5zeW1jYi5jb20wawYDVR0gBGQwYjBgBgpg\n"
                + "hkgBhvhFAQc2MFIwJgYIKwYBBQUHAgEWGmh0dHA6Ly93d3cuc3ltYXV0aC5jb20v\n"
                + "Y3BzMCgGCCsGAQUFBwICMBwaGmh0dHA6Ly93d3cuc3ltYXV0aC5jb20vcnBhMCkG\n"
                + "A1UdEQQiMCCkHjAcMRowGAYDVQQDExFTeW1hbnRlY1BLSS0xLTUzNDAdBgNVHQ4E\n"
                + "FgQUX2DPYZBV34RDFIpgKrL1evRDGO8wHwYDVR0jBBgwFoAUf9Nlp8Ld7LvwMAnz\n"
                + "Qzn6Aq8zMTMwDQYJKoZIhvcNAQELBQADggEBAF6UVkndji1l9cE2UbYD49qecxny\n"
                + "H1mrWH5sJgUs+oHXXCMXIiw3k/eG7IXmsKP9H+IyqEVv4dn7ua/ScKAyQmW/hP4W\n"
                + "Ko8/xabWo5N9Q+l0IZE1KPRj6S7t9/Vcf0uatSDpCr3gRRAMFJSaXaXjS5HoJJtG\n"
                + "QGX0InLNmfiIEfXzf+YzguaoxX7+0AjiJVgIcWjmzaLmFN5OUiQt/eV5E1PnXi8t\n"
                + "TRttQBVSK/eHiXgSgW7ZTaoteNTCLD0IX4eRnh8OsN4wUmSGiaqdZpwOdgyA8nTY\n"
                + "Kvi4Os7X1g8RvmurFPW9QaAiY4nxug9vKWNmLT+sjHLF+8fk1A/yO0+MKcc=\n"
                + "-----END CERTIFICATE-----";
    }

}
