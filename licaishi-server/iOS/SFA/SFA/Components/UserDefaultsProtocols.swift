//
//  Protocols.swift
//  PhiHome
//
//  Created by 左为 on 2017/7/26.
//  Copyright © 2017年 Phicomm. All rights reserved.
//

import Foundation

protocol KeyNamespaceable {
    func namespaced<T: RawRepresentable>(_ key: T) -> String
}

extension KeyNamespaceable {

    func namespaced<T: RawRepresentable>(_ key: T) -> String {
        return "\(Self.self).\(key.rawValue)"
    }
}

// MARK: -  BoolDefaultSettable

protocol IntDefaultSettable: KeyNamespaceable {
    associatedtype IntKey : RawRepresentable
}

extension IntDefaultSettable where IntKey.RawValue == String {
    func set(_ value: Int, forKey key: IntKey) {
        let key = namespaced(key)
        UserDefaults.standard.set(value, forKey: key)
    }
    
    func int(forKey key: IntKey) -> Int {
        let key = namespaced(key)
        return UserDefaults.standard.integer(forKey: key)
    }
}

// MARK: -  BoolDefaultSettable

protocol BoolDefaultSettable: KeyNamespaceable {
    associatedtype BoolKey : RawRepresentable
}

extension BoolDefaultSettable where BoolKey.RawValue == String {
    func set(_ value: Bool, forKey key: BoolKey) {
        let key = namespaced(key)
        UserDefaults.standard.set(value, forKey: key)
    }

    func bool(forKey key: BoolKey) -> Bool {
        let key = namespaced(key)
        return UserDefaults.standard.bool(forKey: key)
    }
}

// MARK: -  StringDefaultSettable

protocol StringDefaultSettable: KeyNamespaceable {
    associatedtype StringKey : RawRepresentable
}

extension StringDefaultSettable where StringKey.RawValue == String {
    func set(_ value: String?, forKey key: StringKey) {
        let key = namespaced(key)
        UserDefaults.standard.set(value, forKey: key)
    }

    func string(forKey key: StringKey) -> String? {
        let key = namespaced(key)
        return UserDefaults.standard.string(forKey: key)
    }
}

// MARK: -  TimeIntervalDefaultSettable

protocol TimeIntervalDefaultSettable: KeyNamespaceable {
    associatedtype TimeIntervalKey : RawRepresentable
}

extension TimeIntervalDefaultSettable where TimeIntervalKey.RawValue == String {
    func set(_ value: TimeInterval?, forKey key: TimeIntervalKey) {
        let key = namespaced(key)
        UserDefaults.standard.set(value, forKey: key)
    }

    func timeinterval(forKey key: TimeIntervalKey) -> TimeInterval? {
        let key = namespaced(key)
        return TimeInterval(UserDefaults.standard.double(forKey: key))
    }
}

// MARK: -  DateDefaultSettable

protocol DateDefaultSettable: KeyNamespaceable {
    associatedtype DateKey : RawRepresentable
}

extension DateDefaultSettable where DateKey.RawValue == String {
    func set(_ value: Date?, forKey key: DateKey) {
        let key = namespaced(key)
        UserDefaults.standard.set(value, forKey: key)
    }

    func date(forKey key: DateKey) -> Date? {
        let key = namespaced(key)
        return UserDefaults.standard.object(forKey: key) as? Date
    }
}
