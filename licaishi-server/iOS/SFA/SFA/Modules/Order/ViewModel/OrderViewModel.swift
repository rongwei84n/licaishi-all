//
//  OrderViewModel.swift
//  SFA
//
//  Created by 徐润肯 on 25/03/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import Foundation
import RxSwift
import Moya
import Alamofire
import RxCocoa
import SwiftyJSON

class OrderViewModel: NSObject {
    
    let provider = MoyaProvider<OrderService>(manager: DefaultAlamofireManager.sharedManager, plugins: [NetworkLoggerPlugin(verbose: true), AuthTokenPlugin(token: User.current.accessToken)])
    
    func getOrderDetail(orderId: String) -> Observable<OrderDetail> {
        
        return Observable.create({ [weak self] (observer) -> Disposable in
            
            self?.provider.request(.getOrderDetail(orderId: orderId), completion: { (result) in
                
                switch result {
                case let .success(response):
                    
                    let json = JSON(response.data)
                    let statusCode = json["status"].stringValue
                    if statusCode == "200" {
                        let orderDetail = OrderDetail.instance(json: json["result"])
                        observer.onNext(orderDetail)
                        observer.onCompleted()
                    }
                    else {
                        observer.onError(ServerError.internalError(code: statusCode))
                    }
                    
                case let .failure(error):
                    observer.onError(error)
                }
                
            })
            return Disposables.create()
            
        }).observeOn(MainScheduler.instance).takeUntil(self.rx.deallocated)
        
    }
    
    func uploadImage(orderId: String, image: UIImage) -> Observable<Bool> {
        
        return Observable.create({ [weak self] (observer) -> Disposable in
            
            if let imageData = UIImageJPEGRepresentation(image, 0.2) {
                
                let imgBase64 = imageData.base64EncodedString(options: .lineLength64Characters)
                self?.provider.request(.uploadImage(orderId: orderId, imgBase64: imgBase64)) { result in
                    switch result {
                        
                    case let .success(moyaResponse):
                        
                        let json = JSON(moyaResponse.data)
                        let statusCode = json["status"].stringValue
                        if statusCode == "200" {
                            observer.onNext(true)
                            observer.onCompleted()
                        }
                        else {
                            observer.onError(ServerError.internalError(code: statusCode))
                        }
                        
                    case let .failure(error):
                        observer.onError(error)
                    }
                }
            }
            return Disposables.create()
            
        }).observeOn(MainScheduler.instance).takeUntil(self.rx.deallocated)
        
    }
    
        
}
