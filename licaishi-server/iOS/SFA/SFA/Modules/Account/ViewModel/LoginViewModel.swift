//
//  LoginViewModel.swift
//  SFA
//
//  Created by 徐润肯 on 23/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Foundation
import RxSwift
import Moya
import Alamofire
import RxCocoa
import SwiftyJSON

class LoginViewModel: NSObject {
    
    let provider = MoyaProvider<AccountService>(manager: DefaultAlamofireManager.sharedManager, plugins: [NetworkLoggerPlugin(verbose: true)])
    
    /// 登录
    ///
    /// - Parameters:
    ///   - phoneNumber: 手机号码
    ///   - password: 密码
    /// - Returns: 是否登录成功
    func login(phoneNumber: String, password: String) -> Observable<Bool> {
        
        return Observable.create({ [weak self] (observer) -> Disposable in
            
            self?.provider.request(.login(phoneNumber: phoneNumber, password: password), completion: { (result) in
                
                switch result {
                case let .success(response):
                    
                    let json = JSON(response.data)
                    let errorCode = json["error"].stringValue
                    if errorCode == "0" {
                        
                        User.current.uid = json["uid"].stringValue
                        User.current.accessToken = json["access_token"].stringValue
                        User.current.set(Date(timeIntervalSinceNow: json["access_token_expire"].doubleValue), forKey: .accessTokenExpireAt)
                        User.current.refreshToken = json["refresh_token"].stringValue
//                        User.current.set(Date(timeIntervalSinceNow: json["refresh_token_expire"].doubleValue), forKey: .refreshTokenExpireAt)
                        
                        User.current.isLoggedIn = true
                        User.current.phoneNumber = phoneNumber
                        
                        observer.onNext(true)
                        observer.onCompleted()
                    }
                    else {
                        observer.onError(ServerError.internalError(code: errorCode))
                    }
                    
                case let .failure(error):
                    observer.onError(error)
                }
                
            })
            return Disposables.create()
            
        }).observeOn(MainScheduler.instance).takeUntil(self.rx.deallocated)
        
    }
    
    /// 注册
    ///
    /// - Parameters:
    ///   - phoneNumber: 手机号码
    ///   - password: 密码
    ///   - verificationCode: 验证码
    /// - Returns: 是否注册成功
    func register(phoneNumber: String, password: String, verificationCode: String) -> Observable<Bool> {
        
        return Observable.create({ [weak self] (observer) -> Disposable in
            
            self?.provider.request(.register(phoneNumber: phoneNumber, password: password, verificationCode: verificationCode), completion: { (result) in
                
                switch result {
                case let .success(response):
                    
                    let json = JSON(response.data)
                    let errorCode = json["error"].stringValue
                    if errorCode == "0" {
                        observer.onNext(true)
                        observer.onCompleted()
                    }
                    else {
                        observer.onError(ServerError.internalError(code: errorCode))
                    }
                    
                case let .failure(error):
                    observer.onError(error)
                }
                
            })
            return Disposables.create()
            
        }).observeOn(MainScheduler.instance).takeUntil(self.rx.deallocated)
        
    }
    
    /// 请求短信验证码
    ///
    /// - Parameter phoneNumber: 手机号码
    /// - Returns: 网络请求是否成功
    func gainVerificationCode(phoneNumber: String, _ type: VerificationCodeType = .SMS) -> Observable<Bool> {
        
        return Observable.create({ [weak self] (observer) -> Disposable in
            
            self?.provider.request(.gainVerificationCode(phoneNumber: phoneNumber, type: type), completion: { (result) in
                
                switch result {
                case let .success(response):
                    
                    let json = JSON(response.data)
                    let errorCode = json["error"].stringValue
                    if errorCode == "0" {
                        observer.onNext(true)
                        observer.onCompleted()
                    }
                    else {
                        observer.onError(ServerError.internalError(code: errorCode))
                    }
                    
                case let .failure(error):
                    observer.onError(error)
                }
                
            })
            return Disposables.create()
            
        }).observeOn(MainScheduler.instance).takeUntil(self.rx.deallocated)
        
    }
    
    /// 忘记密码/重置密码
    ///
    /// - Parameters:
    ///   - phoneNumber: 手机号码
    ///   - password: 新密码
    /// - Returns: 密码是否重置成功
    func forgetPassword(phoneNumber: String, password: String, verificationCode: String) -> Observable<Bool> {
        
        return Observable.create({ [weak self] (observer) -> Disposable in
            
            self?.provider.request(.forgotPassword(phoneNumber: phoneNumber, newPassword: password, verificationCode: verificationCode), completion: { (result) in
                
                switch result {
                case let .success(response):
                    
                    let json = JSON(response.data)
                    let errorCode = json["error"].stringValue
                    if errorCode == "0" {
                        observer.onNext(true)
                        observer.onCompleted()
                    }
                    else {
                        observer.onError(ServerError.internalError(code: errorCode))
                    }
                    
                case let .failure(error):
                    observer.onError(error)
                }
                
            })
            
            return Disposables.create()
            
        }).observeOn(MainScheduler.instance).takeUntil(self.rx.deallocated)
        
    }
    
}
