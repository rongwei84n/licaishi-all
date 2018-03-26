//
//  OrderModel.swift
//  SFA
//
//  Created by 徐润肯 on 25/03/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Foundation
import SwiftyJSON

struct OrderDetail {
    
    var id: String?                 // id
    
    var orderNo: String?            // 订单编号
    
    var amount: Double?             // 订单金额
    
    var orderDate: String?          // 订单日志
    
    var lastestPayDate: String?     // 最迟打款日期
    
    var financeUid: String?         // 理财师UID
    
    var customerUid: String?        // 客户UID
    
    var customerName: String?       // 客户姓名
    
    var productId: String?          // 产品UID
    
    var productShortName: String?   // 产品简称
    
    var status: OrderStatus?             // 订单状态
    
    var comRatio: String?           // 佣金比例
    
    var commission: Double?         // 订单佣金
    
    var proRatio: String?           // 预期收益率
    
    var profit: Double?             // 客户收益
    
    var contractStatus: String?     // 合同状态
    
    var voucherStatus: String?      // 支付上传凭证状态
    
    var voucherPath: String?        // 支付上传凭证图片地址
    
    var issueBank: String?          // 客户支付发卡行
    
    var cardNo: String?             // 银行卡号
    
    var createTime: String?         // 订单编号
    
    var updateTime: String?         // 订单编号
    
    
    static func instance(json: JSON) -> OrderDetail {
        
        var instance = OrderDetail()
        
        instance.id = json["id"].stringValue
        instance.orderNo = json["orderNo"].stringValue
        instance.amount = json["amount"].doubleValue
        instance.orderDate = json["orderDate"].stringValue
        instance.lastestPayDate = json["latestPayDate"].stringValue
        instance.financeUid = json["financerUid"].stringValue
        instance.customerUid = json["customerUid"].stringValue
        instance.customerName = json["customerName"].stringValue
        instance.productId = json["productId"].stringValue
        instance.productShortName = json["productShortName"].stringValue
        instance.status = OrderStatus.from(json["status"].stringValue)
        instance.comRatio = json["comRatio"].stringValue
        instance.commission = json["commission"].doubleValue
        instance.proRatio = json["proRatio"].stringValue
        instance.profit = json["profit"].doubleValue
        instance.contractStatus = json["contractStatus"].stringValue
        instance.voucherStatus = json["voucherStatus"].stringValue
        instance.voucherPath = json["voucherPath"].stringValue
        instance.issueBank = json["issueBank"].stringValue
        instance.cardNo = json["cardNo"].stringValue
        instance.createTime = json["createTime"].stringValue
        instance.updateTime = json["updateTime"].stringValue
        
        return instance
        
    }
    
    
}
