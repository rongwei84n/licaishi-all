//
//  GlobalType.swift
//  SFA
//
//  Created by 徐润肯 on 21/03/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Foundation


/// 验证码类型
///
/// - SMS: 短信
/// - Voice: 语音
/// - Graphic: 图形
enum VerificationCodeType {
    
    case SMS
    
    case Voice
    
    case Graphic
    
}

/// 订单状态类型
///
/// - unknown: 未知状态
/// - daiFuKuan: 待付款
/// - daiJieYong: 待结佣
/// - yiJieYong: 已结佣
/// - yiShiBai: 已失败
enum OrderStatus {
    
    case unknown
    
    case daiFuKuan
    
    case daiJieYong
    
    case yiJieYong
    
    case yiShiBai
    
    static func from(_ code: String) -> OrderStatus {
        switch code {
            
        case "01":
            return .daiFuKuan
            
        case "02":
            return .daiJieYong
            
        case "03":
            return .yiJieYong
            
        case "99":
            return .yiShiBai
            
        default:
            return .unknown
        }
    }
    
    func toString() -> String {
        switch self {
            
        case .daiFuKuan:
            return "待付款"
            
        case .daiJieYong:
            return "待结佣"
            
        case .yiJieYong:
            return "已结佣"
            
        case .yiShiBai:
            return "已失败"
            
        default:
            return ""
        }
    }
    
}


/// 合同状态
///
/// - unknown: 未知状态
/// - weiWanCheng: 未完成
/// - yiWanCheng: 已完成
enum ContractStatus {
    
    case unknown
    
    case weiWanCheng
    
    case yiWanCheng
    
    static func from(_ code: String) -> ContractStatus {
        switch code {
            
        case "0":
            return .weiWanCheng
            
        case "1":
            return .yiWanCheng
            
        default:
            return .unknown
        }
    }
    
    func toString() -> String {
        switch self {
            
        case .weiWanCheng:
            return "未完成"
            
        case .yiWanCheng:
            return "已完成"
            
        default:
            return ""
        }
    }
    
}


/// 支付凭证上传状态
///
/// - unknown: 未知状态
/// - weiShangChuan: 未上传
/// - yiShangChuan: 已上传
enum VoucherStatus {
    
    case unknown
    
    case weiShangChuan
    
    case yiShangChuan
    
    static func from(_ code: String) -> VoucherStatus {
        switch code {
            
        case "0":
            return .weiShangChuan
            
        case "1":
            return .yiShangChuan
            
        default:
            return .unknown
        }
    }
    
    func toString() -> String {
        switch self {
            
        case .weiShangChuan:
            return "未上传"
            
        case .yiShangChuan:
            return "已上传"
            
        default:
            return ""
        }
    }
    
}
