//
//  AuthTokenPlugin.swift
//  PhiHome
//
//  Created by 左为 on 2017/7/21.
//  Copyright © 2017年 Phicomm. All rights reserved.
//

import Foundation
import Moya

public struct AuthTokenPlugin: PluginType {

    /// The access token to be applied in the header.
    public var token: String?

    private var authVal: String? {
        return token ?? tokenClosure?()
    }

    private var tokenClosure: (() -> String)?

    /**
     Initialize a new `AccessTokenPlugin`.
     - parameters:
     - token: The token to be applied in the pattern `Authorization: Bearer <token>`
     */
    public init(token: String?) {
        self.token = token
    }

    public init(tokenClosure: @escaping @autoclosure () -> String ) {
        self.tokenClosure = tokenClosure
    }

    /**
     Prepare a request by adding an authorization header if necessary.
     - parameters:
     - request: The request to modify.
     - target: The target of the request.
     - returns: The modified `URLRequest`.
     */
    public func prepare(_ request: URLRequest, target: TargetType) -> URLRequest {
        if let authorizable = target as? AccessTokenAuthorizable, authorizable.authorizationType == .none {
            return request
        }
        guard let authVal = authVal else {
            return request
        }

        var request = request
        request.addValue(authVal, forHTTPHeaderField: "Authorization")
        
        return request
    }
}
