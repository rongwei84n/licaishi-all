//
//  Dictionary+Extension.swift
//  PhiHome
//
//  Created by 左为 on 2017/7/25.
//  Copyright © 2017年 Phicomm. All rights reserved.
//

import Foundation
import UIKit

extension Dictionary {
    
    func formatJSON2() -> String? {
        if let jsonData = try? JSONSerialization.data(withJSONObject: self, options: JSONSerialization.WritingOptions()) {
            let jsonStr = String(data: jsonData, encoding: String.Encoding(rawValue: String.Encoding.utf8.rawValue))
            return String(jsonStr ?? "")
        }
        return nil
    }
}
