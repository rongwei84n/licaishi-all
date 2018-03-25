//
//  OrderServices.swift
//  SFA
//
//  Created by 徐润肯 on 25/03/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Moya
import Alamofire

enum OrderService {
    
    case getOrderDetail(orderId: String)
    
    case uploadImage(orderId: String, imgBase64: String)
    
}

extension OrderService: AccessTokenAuthorizable {
    
    var authorizationType: AuthorizationType { return .basic }
}

extension OrderService: TargetType {
    
    var headers: [String : String]? {
        return nil
    }
    
    var baseURL: URL {
        
        let domain = App.HTTPConfig.domain.name
        
        return URL(string: domain)!
    }
    var version: String {
        return "/srv/v1/"
    }
    var path: String {
        switch self {
            
        case .getOrderDetail:
            return version + "order/orderDetail"  // 登录
            
        case .uploadImage:
            return version + "order/uploadPayPhote"  // 上传头像
            
        }
    }
    
    var method: Moya.Method {
        switch self {
            
        case .getOrderDetail:
            return .get
            
        case .uploadImage:
            return .post
            
        default:
            return .post
        }
    }
    
    var task: Task {
        switch self {
            
        case .getOrderDetail(let orderId):
            return .requestParameters(parameters: ["platid": 2,
                                                   "vercode": 1,
                                                   "channel": "0ASE",
                                                   "authorizationcode": "",
                                                   "orderNo": orderId],
                                      encoding: URLEncoding.default)
            
        case .uploadImage(let orderId, let imgBase64):
            return .requestParameters(parameters: ["platid": 2,
                                                   "vercode": 1,
                                                   "channel": "0ASE",
                                                   "authorizationcode": "",
                                                   "orderNo": orderId,
                                                   "imgBase64": imgBase64],
                                      encoding: URLEncoding.default)
            
        default:
            return .requestPlain
        }
    }
    
    // Provides stub data for use in testing.
    public var sampleData: Data {
        switch self {
        default:
            return "{}".utf8Encoded
            
        }
    }
}
