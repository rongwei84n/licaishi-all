//
//  MMError.swift
//  PhiHome
//
//  Created by liman on 08/08/2017.
//  Copyright © 2017 Phicomm. All rights reserved.
//

//ZWHud
let kLoadingText: String = "正在加载..."
let kWaittingText: String = "请稍候..."

//phicomm
let kNetworkError = "手机网络异常，请检查网络设置"
let kServerError = "服务器异常，请稍后再试"

//云知声
let kTokenError = "token失效"
let kUnisoundError  = "服务器异常，请稍后再试" //注意：这里实际是“云知声的错误”
let kNotExitError = "请添加斐讯AI音箱"  /* 不存在 (eg：当前用户不存在绑定设备) */
let kAIoffError = "斐讯AI音箱已离线" //400014 //设备未在线

import Foundation
import UIKit

class MMError {
    
    // MARK: - 单例
    static let shared = MMError()
    private init() {}
    
    // MARK: - 错误码转换
    func translate(code: String, msg: String) -> String {
        
        if code == "\(NSURLErrorNotConnectedToInternet)" {
            return kNetworkError
        }
        
        if code == "\(NSURLErrorCannotConnectToHost)" || code == "\(NSURLErrorTimedOut)" {
            return "网络异常，请稍后再试"
        }
        
        if code == "8" {
            return "帐号或密码错误，请重新输入"
        }
        
        if code == "14" {
            return "该手机号已注册，你可以直接登录"
        }
        
        if code == "7" {
            return "该手机号未注册"
        }
        
        if code == "34" {
            return "请输入正确的手机号"
        }
        
        if code == "1" {
            return "请输入正确的验证码"//短信
        }
        
        if code == "23" {
            return "验证码已使用，请重新获取"//短信
        }
        
        if code == "2" {
            return "验证码过期，请重新获取"//短信
        }
        
        if code == "11" {
            return "授权码错误"
        }
        
        if code == "50" {
            return "服务器异常"
        }
        
        if code == "13" {
            return "获取验证码失败"//短信
        }
        
        if code == "38" {
            return "短信验证码请求过快"//短信
        }
        
        if code == "39" {
            return "验证码请求超出限制"//短信
        }
        
        if code == "9" {
            return "client_id不存在"
        }
        
        if code == "10" {
            return "client_secret错误"
        }
        
        if code == "12" {
            return "参数错误"
        }
        
        if code == "15" {
            return "密码未设置"
        }
        
        if code == "30" {
            return "多端登录账户被踢出"
        }
        
        if code == "5" {
            return kTokenError
        }
        
        if code == "6" {
            return "网络异常，请稍后再试"
        }

        if code == "3" {
            return "尚未经过旧手机号验证"//一般用于重新绑定手机号
        }
        
        if code == "25" {
            return "邮箱已经注册"
        }
        
        if code == "16" {
            return "该账户未设置用户详情"
        }
        
        if code == "21" {
            return "token错误"
        }
        
        if code == "41" {
            return "三方账户未绑定斐讯帐号"
        }
        
        if code == "35" {
            return "该手机号未注册云账户"
        }
        
        if code == "36" {
            return "图片验证码过期，请刷新"
        }
        
        if code == "37" {
            return "图片验证码错误，请重新输入"
        }
        
        if code == "18" {
            return "图片格式错误"
        }
        
        if code == "19" {
            return "图片为空"
        }
        
        if code == "20" {
            return "用户未设置头像"
        }
        
        //http://appdesign.doc.szrd.phiwifi.com/protocol/phihome/interface_status.html
        if code == "400" {
            return "JSON无效"
        }
        if code == "401" {
            return "未授权"
        }
        if code == "403" {
            return "禁止"
        }
        if code == "404" {
            return "事物未找到"
        }
        if code == "409" {
            return "版本冲突"
        }
        if code == "413" {
            return "有效负载超出允许的最大值"
        }
        if code == "415" {
            return "文档编码不受支持" //受支持的编码是 UTF-8
        }
        if code == "429" {
            return "请求过多"
        }
        if code == "500" {
            return "内部服务器错误"
        }
        if code == "10001" {
            return "请求失败"
        }
        if code == "10002" {
            return "请求中参数不足"
        }
        if code == "10003" {
            return "请求中缺少token"
        }
        if code == "10004" {
            return kTokenError
        }
        if code == "10005" {
            return "获取斐讯云账户失败"
        }
        if code == "10006" {
            return "已经在其它移动端登录"
        }
        if code == "10007" {
            return "用户还未设置账号详情"
        }
        if code == "10008" {
            return "操作数据库失败"
        }
        if code == "10009" {
            return "请求参数不合法"
        }
        if code == "10010" {
            return "请求中缺少uid"
        }
        if code == "10011" {
            return "获取JSON数据出错"
        }
        if code == "10012" {
            return "请求中缺少deviceId"
        }
        if code == "10013" {
            return "数据库中未查找到对应的设备信息"
        }
        if code == "10014" {
            return "与Mongodb交互失败"
        }
        if code == "10015" {
            return "topic格式不正确"
        }
        if code == "10016" {
            return "未查询到该设备相关的shadow信息"
        }
        if code == "11001" {
            return "用户下没有对应的设备"
        }
        if code == "11002" {
            return "获取透传结果失败"
        }
        if code == "11003" {
            return "设备处于离线状态"
        }
        if code == "12001" {
            return "系统发生错误"
        }
        if code == "12002" {
            return "没有版本更新"
        }
        
        return "网络异常，请稍后再试(\(code))"
    }
}
