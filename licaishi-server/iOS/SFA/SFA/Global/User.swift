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
        
        User.current.zipcode = nil
        User.current.img = nil
        User.current.address = nil
        User.current.zone = nil
        User.current.sex = nil
        User.current.nickname = nil
        User.current.age = nil
        User.current.realname = nil

        User.current.uid = nil
        User.current.isLoggedIn = false
        User.current.detailLoaded = false
        User.current.accessToken = nil
        User.current.refreshToken = nil
        User.current.kAccessToken = nil // 云知声accessToken
        User.current.captchaid = nil
        User.current.captcha = nil
        User.current.phoneNumber = nil
        User.current.verCode = nil
//        User.current.deviceId = nil // 当前保存的绑定的设备deviceId
//        User.current.udid = nil // 当前保存的绑定的设备udid
        User.current.familyId = nil
        User.current.familyName = nil
        User.current.job = nil
        User.current.birthDay = nil
//        User.current.updateDeadline = 0.0
    }
    
    func sexString() -> String? {
        
        if let sex = sex {
            
            if sex == "1" {
                return "男"
            }
            else if sex == "2" {
                return "女"
            }
            else {
                return "未设置"
            }
        }
        
        
        return nil
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
        case captcha
        case captchaid
        case phoneNumber
        case verCode
//        case deviceId
//        case udid
        case familyId
        case familyName
        case job
        case birthDay
        //李满
        case zipcode
        case img
        case address
        case zone
        case sex
        case nickname
        case age
        case realname
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

    var captchaid: String? {
        get {
            return User.current.string(forKey: .captchaid)
        }
        set {
            User.current.set(newValue, forKey: .captchaid)
        }
    }

    var captcha: String? {
        get {
            return User.current.string(forKey: .captcha)
        }
        set {
            User.current.set(newValue, forKey: .captcha)
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

    var verCode: String? {
        get {
            return User.current.string(forKey: .verCode)
        }
        set {
            User.current.set(newValue, forKey: .verCode)
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

//    var deviceId: String? {
//        get {
//            return User.current.string(forKey: .deviceId)
//        }
//        set {
//            User.current.set(newValue, forKey: .deviceId)
//        }
//    }
//
//    var udid: String? {
//        get {
//            return User.current.string(forKey: .udid)
//        }
//        set {
//            User.current.set(newValue, forKey: .udid)
//        }
//    }
    
    var familyId: String? {
        get {
            return User.current.string(forKey: .familyId)
        }
        set {
            User.current.set(newValue, forKey: .familyId)
        }
    }
    
    var familyName: String? {
        get {
            return User.current.string(forKey: .familyName)
        }
        set {
            User.current.set(newValue, forKey: .familyName)
        }
    }
    
    var job: String? {
        get {
            return User.current.string(forKey: .job)
        }
        set {
            User.current.set(newValue, forKey: .job)
        }
    }
    
    var birthDay: String? {
        get {
            return User.current.string(forKey: .birthDay)
        }
        set {
            User.current.set(newValue, forKey: .birthDay)
        }
    }

    //李满
    var zipcode: String? {
        get {
            return User.current.string(forKey: .zipcode)
        }
        set {
            User.current.set(newValue, forKey: .zipcode)
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
    var address: String? {
        get {
            return User.current.string(forKey: .address)
        }
        set {
            User.current.set(newValue, forKey: .address)
        }
    }
    
    var zone: String? {
        get {
            return User.current.string(forKey: .zone)
        }
        set {
            User.current.set(newValue, forKey: .zone)
        }
    }
    
    var sex: String? {
        get {
            return User.current.string(forKey: .sex)
        }
        set {
            User.current.set(newValue, forKey: .sex)
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
    
    var age: String? {
        get {
            return User.current.string(forKey: .age)
        }
        set {
            User.current.set(newValue, forKey: .age)
        }
    }
    
    var realname: String? {
        get {
            return User.current.string(forKey: .realname)
        }
        set {
            User.current.set(newValue, forKey: .realname)
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
