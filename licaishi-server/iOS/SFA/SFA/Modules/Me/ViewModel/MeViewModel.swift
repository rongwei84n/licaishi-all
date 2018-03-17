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

class MeViewModel: NSObject {
    
    let provider = MoyaProvider<AccountService>(manager: DefaultAlamofireManager.sharedManager, plugins: [NetworkLoggerPlugin(verbose: true), AuthTokenPlugin(token: User.current.accessToken)])
    
    /// 修改密码
    ///
    /// - Parameters:
    ///   - oldPassword: 旧密码
    ///   - newPassword: 新密码
    /// - Returns: 密码是否修改成功
    func changePassword(oldPassword: String, newPassword: String) -> Observable<Bool> {
        
        return Observable.create({ [weak self] (observer) -> Disposable in
            
            self?.provider.request(.changePassword(oldPassword: oldPassword, newPassword: newPassword), completion: { (result) in
                
                switch result {
                case let .success(response):
                    
                    let json = JSON(response.data)
                    if json["error"].stringValue == "0" {
                        observer.onNext(true)
                    }
                    observer.onCompleted()
                    
                case let .failure(error):
                    observer.onError(error)
                }
                
            })
            return Disposables.create()
            
        }).observeOn(MainScheduler.instance).takeUntil(self.rx.deallocated)
        
    }
    
    func uploadImage(image: UIImage, type: String) -> Observable<Bool> {
        
        return Observable.create({ [weak self] (observer) -> Disposable in
            
            if let imageData = UIImageJPEGRepresentation(image, 0.2) {
                
                let imgBase64 = imageData.base64EncodedString(options: .lineLength64Characters)
                self?.provider.request(.uploadImage(imgBase64: imgBase64, type: type)) { result in
                    switch result {
                        
                    case let .success(moyaResponse):
                        
                        let json = JSON(moyaResponse.data)
                        if json["error"].stringValue == "0" {
                            observer.onNext(true)
                        }
                        observer.onCompleted()
                        
                    case let .failure(error):
                        observer.onError(error)
                    }
                }
            }
            return Disposables.create()
            
        }).observeOn(MainScheduler.instance).takeUntil(self.rx.deallocated)
        
    }
    
    func getAccouontDetail() -> Observable<Bool> {
        
        return Observable.create({ [weak self] (observer) -> Disposable in
            
            self?.provider.request(.accountDetail) { result in
                switch result {
                    
                case let .success(moyaResponse):
                    
                    let json = JSON(moyaResponse.data)
                    if json["error"].stringValue == "0" {
                        
                        let data = json["data"]
                        User.current.phoneNumber = data["phonenumber"].string
                        User.current.img = data["img"].string
                        User.current.phoneNumber = data["phonenumber"].string
                        User.current.nickname = data["nickname"].string
                        User.current.loginName = data["accountname"].string
                        User.current.studioName = data["workstudio"].string
                        
                        observer.onNext(true)
                    }
                    observer.onCompleted()
                    
                case let .failure(error):
                    observer.onError(error)
                }
            }
            return Disposables.create()
        }).observeOn(MainScheduler.instance).takeUntil(self.rx.deallocated)
        
    }
    
    func modiifyAccouontDetail(nickName: String?, studioName: String?) -> Observable<Bool> {
        
        return Observable.create({ [weak self] (observer) -> Disposable in
            
            self?.provider.request(.modifyAccountDetail(nickName: nickName, studioName: studioName)) { result in
                switch result {
                    
                case let .success(moyaResponse):
                    
                    let json = JSON(moyaResponse.data)
                    if json["error"].stringValue == "0" {
                        observer.onNext(true)
                    }
                    else {
                        observer.onNext(false)
                    }
                    observer.onCompleted()
                    
                case let .failure(error):
                    observer.onError(error)
                }
            }
            return Disposables.create()
        }).observeOn(MainScheduler.instance).takeUntil(self.rx.deallocated)
        
    }
    
}

