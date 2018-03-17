//
//  User.swift
//  PhiHome
//
//  Created by 左为 on 2017/7/21.
//  Copyright © 2017年 Phicomm. All rights reserved.
//

import Foundation

class User {
    static var current: User = User()
    
    required init() {}
    
    //清空User对象信息
    func clearUserInfo() {
        User.current.set(nil, forKey: .accessTokenExpireAt)
        User.current.set(nil, forKey: .refreshTokenExpireAt)
        
        User.current.img = nil
        User.current.nickname = nil
        User.current.loginName = nil
        User.current.studioName = nil

        User.current.uid = nil
        User.current.isLoggedIn = false
        User.current.detailLoaded = false
        User.current.accessToken = nil
        User.current.refreshToken = nil
        User.current.kAccessToken = nil // 云知声accessToken
        User.current.phoneNumber = nil
    }
    
}

// MARK: -  user defaults extensions

extension User: BoolDefaultSettable {
    enum BoolKey: String {
        case isUserLoggedIn
        case detailLoaded
    }

}

extension User: StringDefaultSettable {
    enum StringKey: String {
        case uid
        case accessToken
        case refreshToken
        case kAccessToken
        
        case phoneNumber
        case img
        case nickname
        case loginName
        case studioName
        
        case versionCode // 记录当前的版本信息
    }
}

extension User: TimeIntervalDefaultSettable {
    enum TimeIntervalKey: String {
        case updateDeadline
    }
}


extension User: DateDefaultSettable {
    enum DateKey: String {
        case accessTokenExpireAt
        case refreshTokenExpireAt
    }
}

extension User: IntDefaultSettable {
    enum IntKey: String {
        case selectedRoomIndex
    }
    
}

extension User {
    
    //李满
    var uid: String? {
        get {
            return User.current.string(forKey: .uid)
        }
        set {
            User.current.set(newValue, forKey: .uid)
        }
    }
    
    
    var isLoggedIn: Bool {
        get {
            return User.current.bool(forKey: .isUserLoggedIn)
        }
        set {
            User.current.set(newValue, forKey: .isUserLoggedIn)
        }
    }

    var detailLoaded: Bool {
        get {
            return User.current.bool(forKey: .detailLoaded)
        }
        set {
            User.current.set(newValue, forKey: .detailLoaded)
        }
    }

    var accessToken: String? {
        get {
            return User.current.string(forKey: .accessToken)
        }
        set {
            User.current.set(newValue, forKey: .accessToken)
        }
    }

    var refreshToken: String? {
        get {
            return User.current.string(forKey: .refreshToken)
        }
        set {
            User.current.set(newValue, forKey: .refreshToken)
        }
    }

    var kAccessToken: String? {
        get {
            return User.current.string(forKey: .kAccessToken)
        }
        set {
            User.current.set(newValue, forKey: .kAccessToken)
        }
    }

    var phoneNumber: String? {
        get {
            return User.current.string(forKey: .phoneNumber)
        }
        set {
            User.current.set(newValue, forKey: .phoneNumber)
        }
    }

    var versionCode: String? {
        get {
            return User.current.string(forKey: .versionCode)
        }
        set {
            User.current.set(newValue, forKey: .versionCode)
        }
    }
    
    var img: String? {
        get {
            return User.current.string(forKey: .img)
        }
        set {
            User.current.set(newValue, forKey: .img)
        }
    }
    
    var nickname: String? {
        get {
            return User.current.string(forKey: .nickname)
        }
        set {
            User.current.set(newValue, forKey: .nickname)
        }
    }
    
    var loginName: String? {
        get {
            return User.current.string(forKey: .loginName)
        }
        set {
            User.current.set(newValue, forKey: .loginName)
        }
    }
    
    var studioName: String? {
        get {
            return User.current.string(forKey: .studioName)
        }
        set {
            User.current.set(newValue, forKey: .studioName)
        }
    }
    
    var expired: Bool {
        let expireAt = User.current.date(forKey: .accessTokenExpireAt)
        return (expireAt?.timeIntervalSinceNow ?? -1 < 0 )
    }

    var refreshTokenExpired: Bool {
        let expireAt = User.current.date(forKey: .refreshTokenExpireAt)
        return (expireAt?.timeIntervalSinceNow ?? -1 < 0 )
    }

    var updateDeadline: TimeInterval {
        get {
            return User.current.timeinterval(forKey: .updateDeadline)!
        }
        set {
            User.current.set(newValue, forKey: .updateDeadline)
        }
    }
}
