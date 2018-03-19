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
    
    
    
    
    case authorization
    case signup(authCode: String, phoneNumber: String, verifyCode: String, password: String)
    case sendVerifySMS(authCode: String, phoneNumber: String)
    case logout
    case refreshToken(authCode: String)
    case checkPhoneUsability(authCode: String, phoneNumber: String)
    case requestGraphValidateCode(authCode: String)
    case getGraphValidateCodeInfo(authCode: String, captcha: String, captchaid: String, phoneNumber: String)
    case verifyVerificationcode(authCode: String, phonenumber: String, verificationcode: String)
    case accountProperty(nickname: String?, address: String?, age: String?, realname: String?, sex: String?, zipcode: String?, zone: String?, job: String? , birthDay: String?)
    case oldAccountVerification(phonenumber: String, verificationcode: String)
    case rebind(phonenumber: String, verificationcode: String)
    case checkTokenValidation
}

extension AccountService: AccessTokenAuthorizable {
    
    var authorizationType: AuthorizationType { return .basic }
}

extension AccountService: TargetType {
    
    var headers: [String : String]? {
        return nil
    }
    
    var baseURL: URL {
        
        var domain = App.HTTPConfig.domain.name
        
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
            return version + "property"
            
            
        case .authorization:
            return version + "authorization"//03_获取授权码
        case .sendVerifySMS:
            return version + "verificationCode"//02_获取验证码 ?????
        case .signup:
            return version + "account"//01_注册账户
        case .logout:
            return version + "logout"//16_退出账户 ?????
        case .refreshToken:
            return version + "token"//10_刷新token ?????
        case .checkPhoneUsability:
            return version + "checkPhonenumber"//13_检测手机号或邮箱号是否可以被注册
        case .requestGraphValidateCode:
            return version + "captcha"//23_请求图形验证码
        case .getGraphValidateCodeInfo:
            return version + "verificationMsg"//24_获取验证码信息
        case .verifyVerificationcode:
            return version + "verifyVerificationCode"//26_校验短信验证码
        case .accountProperty:
            return version + "property"//06_修改账户公共属性
        case .oldAccountVerification:
            return version + "oldAccountVerification"//11_修改账户绑定关系-步骤1:校验旧的手机号或邮箱号
        case .rebind:
            return version + "rebind"//12_修改账户绑定关系-步骤2:设置新的手机号或邮箱号
        case .checkTokenValidation:
            return version + "verifyToken" // 19_验证token有效性
            
        }
    }
    
    var method: Moya.Method {
        switch self {
            
        case .login,
             .register,
             .changePassword,
             .modifyAccountDetail:
            return .post
            
        case .accountDetail:
            return .get
            
            
        case .authorization,
             .sendVerifySMS,
             .refreshToken,
             .checkPhoneUsability,
             .requestGraphValidateCode,
             .getGraphValidateCodeInfo,
             .checkTokenValidation,
             .verifyVerificationcode:
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
            
        case .modifyAccountDetail(let nickName,let studioName):
            
            var dic = [String : Any]()
            
            if let nickName = nickName, !nickName.isEmpty {
                dic["nickname"] = nickName
            }
            
            if let studioName = studioName, !studioName.isEmpty {
                dic["workstudio"] = studioName
            }
            
            return .requestParameters(parameters: ["data": dic.formatJSON2() ?? ""], encoding: URLEncoding.default)
            
            
            
            
            
            
        case .authorization:
            return .requestParameters(parameters: ["client_id": "4098902", "client_secret": "91562BF54DB60610C0F5CBCB8362D6D0",
                                                   "redirect_uri": "", "response_type": "code", "scope": "read"], encoding: URLEncoding.default)
            
        case .sendVerifySMS(let authCode, let phoneNumber):
            return .requestParameters(parameters: ["authorizationcode": authCode, "phonenumber": phoneNumber, "verificationtype": 0], encoding: URLEncoding.default)
            
        case .signup(let authCode, let phoneNumber, let verifyCode, let password):
            
            return .requestParameters(parameters: ["authorizationcode": authCode, "phonenumber": phoneNumber,
                                                   "verificationcode": verifyCode, "registersource": 4098902,
                                                   "data": "{\"age\":\"20\"}", "password": password], encoding: URLEncoding.default)
            
            
            
        case .refreshToken(let authCode):
            return .requestParameters(parameters: ["authorizationcode": authCode, "grant_type": "refresh_token"], encoding: URLEncoding.default)
            
        case .checkPhoneUsability(let authCode, let phoneNumber):
            return .requestParameters(parameters: ["authorizationcode": authCode, "phonenumber": phoneNumber], encoding: URLEncoding.default)
            
        case .requestGraphValidateCode(let authCode):
            return .requestParameters(parameters: ["authorizationcode": authCode], encoding: URLEncoding.default)
            
            
        case .getGraphValidateCodeInfo(let authCode, let captcha, let capchaid, let phoneNumber):
            return .requestParameters(parameters: ["authorizationcode": authCode, "captcha": captcha, "captchaid": capchaid, "phonenumber": phoneNumber, "verificationtype": 0], encoding: URLEncoding.default)
            
        case .verifyVerificationcode(let authCode, let phonenumber, let verificationcode):
            return .requestParameters(parameters: ["authorizationcode": authCode, "phonenumber": phonenumber, "verificationcode": verificationcode], encoding: URLEncoding.default)
            
        case .accountProperty(let nickname,let address,let age,let realname,let sex,let zipcode, let zone ,let job , let birthDay):
            
            var dic = [String : Any]()
            
            if nickname != nil , nickname != "" {
                dic["nickname"] = nickname
            }
            if address != nil , address != "" {
                dic["address"] = address
            }
            if age != nil , age != ""  {
                dic["age"] = age
            }
            if realname != nil , realname != ""  {
                dic["realname"] = realname
            }
            if sex != nil , sex != ""  {
                dic["sex"] = sex
            }
            if zipcode != nil , zipcode != ""  {
                dic["zipcode"] = zipcode
            }
            if zone != nil , zone != ""  {
                dic["zone"] = zone
            }
            if job != nil , job != ""  {
                dic["job"] = job
            }
            if birthDay != nil , birthDay != ""  {
                dic["birthday"] = birthDay
            }
            
            return .requestParameters(parameters: ["data": dic.formatJSON2() ?? ""], encoding: URLEncoding.default)
            
        case .oldAccountVerification(let phonenumber, let verificationcode):
            return .requestParameters(parameters: ["phonenumber": phonenumber, "verificationcode": verificationcode], encoding: URLEncoding.default)
            
        case .rebind(let phonenumber, let verificationcode):
            return .requestParameters(parameters: ["phonenumber": phonenumber, "verificationcode": verificationcode], encoding: URLEncoding.default)
            
        default:
            return .requestPlain
        }
    }
    
    // Provides stub data for use in testing.
    public var sampleData: Data {
        switch self {
        case .authorization:
            return " {\"client_id\":6, \"client_secret\":\"feixun * 123\", \"redirect_uri\":\"\", \"response_type\":\"code\", \"scope\":\"read\"}".utf8Encoded
        case .login:
            return "{\"authorizationcode\":0}".utf8Encoded
        default:
            return "{}".utf8Encoded
            
        }
    }
}
