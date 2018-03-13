//
//  InstanceFromStoryBoard.swift
//  PhiSpeaker
//
//  Created by ZhiYun Huang on 05/09/2017.
//  Copyright © 2017 Phicomm. All rights reserved.
//

import UIKit

@objc protocol InstanceFromStoryBoard {
    
    static var storyBoardName : String {get}
    
    @objc optional static var storyboardIDForNavigationController : String { get }
}

extension InstanceFromStoryBoard where Self : UIViewController {
    
    static func instanceFromStoryBoard() -> Self {
        
        let storyboard = UIStoryboard.init(name: storyBoardName, bundle: nil)
        let identifier = String(describing: self).components(separatedBy: ".").last!
        
        return storyboard.instantiateViewController(withIdentifier: identifier) as! Self
    }
    
    
    static func instanceWithNavFromStoryBoard() -> UINavigationController {
        
        let storyboard = UIStoryboard.init(name: storyBoardName, bundle: nil)
        
        if let navID = storyboardIDForNavigationController {
            
            return storyboard.instantiateViewController(withIdentifier: navID) as! UINavigationController
        }
        
        return UINavigationController(rootViewController: instanceFromStoryBoard())
        
    }
    
}
