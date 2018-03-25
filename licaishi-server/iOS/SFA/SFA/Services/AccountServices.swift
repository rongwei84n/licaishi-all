//
//  AccountServices.swift
//  SFA
//
//  Created by 徐润肯 on 11/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Moya
import Alamofire

enum AccountService {
    
    case login(phoneNumber: String, password: String)
    
    case register(phoneNumber: String, password: String, verificationCode: String)
    
    case changePassword(oldPassword: String, newPassword: String)
    
    case forgotPassword(phoneNumber: String, newPassword: String, verificationCode: String)
    
    case uploadImage(imgBase64: String, type: String)
    
    case accountDetail
    
    case modifyAccountDetail(nickName: String?, studioName: String?)
    
    case gainVerificationCode(phoneNumber: String, type: VerificationCodeType)
    
}

extension AccountService: AccessTokenAuthorizable {
    
    var authorizationType: AuthorizationType { return .basic }
}

extension AccountService: TargetType {
    
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
            
        case .login:
            return version + "login"  // 登录
            
        case .register:
            return version + "account"  // 注册
            
        case .changePassword:
            return version + "password"  // 修改密码
            
        case .forgotPassword:
            return version + "forgetpassword"  // 忘记密码
            
        case .uploadImage:
            return version + "pic/uploadBase64"  // 上传头像
            
        case .accountDetail:
            return version + "accountDetail"  // 获取账户详情
            
        case .modifyAccountDetail:
            return version + "property"  // 修改账户详情
            
        case .gainVerificationCode:
            return version + "verificationMsg"  // 获取验证码
            
        }
    }
    
    var method: Moya.Method {
        switch self {
            
        case .login,
             .register,
             .changePassword,
             .modifyAccountDetail:
            return .post
            
        case .accountDetail,
             .gainVerificationCode:
            return .get
            
        default:
            return .post
        }
    }
    
    var task: Task {
        switch self {
            
        case .login(let phoneNumber, let password):
            return .requestParameters(parameters: ["platid": 2,
                                                   "vercode": 1,
                                                   "channel": "0ASE",
                                                   "authorizationcode": "",
                                                   "phonenumber": phoneNumber,
                                                   "password": password],
                                      encoding: URLEncoding.default)
            
        case .register(let phoneNumber, let password, let verificationCode):
            return .requestParameters(parameters: ["platid": 2,
                                                   "vercode": 1,
                                                   "channel": "0ASE",
                                                   "authorizationcode": "",
                                                   "registersource": "123",
                                                   "phonenumber": phoneNumber,
                                                   "password": password,
                                                   "verificationcode": verificationCode],
                                      encoding: URLEncoding.default)
            
        case .changePassword(let oldPassword, let newPassword):
            return .requestParameters(parameters: ["platid": 2,
                                                   "vercode": 1,
                                                   "channel": "0ASE",
                                                   "authorizationcode": "",
                                                   "oldpassword": oldPassword,
                                                   "newpassword": newPassword],
                                      encoding: URLEncoding.default)
            
        case .forgotPassword(let phoneNumber, let newPassword, let verificationCode):
            return .requestParameters(parameters: ["platid": 2,
                                                   "vercode": 1,
                                                   "channel": "0ASE",
                                                   "authorizationcode": "",
                                                   "phonenumber": phoneNumber,
                                                   "newpassword": newPassword,
                                                   "verificationcode": verificationCode],
                                      encoding: URLEncoding.default)
            
        case .uploadImage(let imgBase64, let type):
            return .requestParameters(parameters: ["platid": 2,
                                                   "vercode": 1,
                                                   "channel": "0ASE",
                                                   "authorizationcode": "",
                                                   "imgBase64": imgBase64,
                                                   "type": type],
                                      encoding: URLEncoding.default)
            
        case .modifyAccountDetail(let nickName, let studioName):
            
            var dic = [String : Any]()
            
            if let nickName = nickName, !nickName.isEmpty {
                dic["nickname"] = nickName
            }
            
            if let studioName = studioName, !studioName.isEmpty {
                dic["workstudio"] = studioName
            }
            
            return .requestParameters(parameters: ["data": dic.formatJSON2() ?? ""], encoding: URLEncoding.default)
            
        case .gainVerificationCode(let phoneNumber, let verificationCodeType):
            return .requestParameters(parameters: ["authorizationcode": "",
                                                   "phonenumber": phoneNumber,
                                                   "verificationtype": verificationCodeType],
                                      encoding: URLEncoding.default)
            
        default:
            return .requestPlain
        }
    }
    
    // Provides stub data for use in testing.
    public var sampleData: Data {
        switch self {
        case .login:
            return "{\"authorizationcode\":0}".utf8Encoded
        default:
            return "{}".utf8Encoded
            
        }
    }
}
