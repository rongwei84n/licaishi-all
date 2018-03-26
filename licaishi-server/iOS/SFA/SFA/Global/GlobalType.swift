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
