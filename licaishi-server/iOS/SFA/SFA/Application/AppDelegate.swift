//
//  AppDelegate.swift
//  SFA
//
//  Created by 徐润肯 on 07/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit


@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {

        setupAppearance()
        
        let config = JSHARELaunchConfig.init()
        config.appKey = "5e20808569da8ba7360d5932";
////        config.SinaWeiboAppKey = @"374535501";
////        config.SinaWeiboAppSecret = @"baccd12c166f1df96736b51ffbf600a2";
//        config.SinaRedirectUri = @"https://www.jiguang.cn";
////        config.QQAppId = @"1105864531";
////        config.QQAppKey = @"glFYjkHQGSOCJHMC";
        config.weChatAppId = "wxb2c92e8437fa2dd2"
        config.weChatAppSecret = "cd289632a1cbae578ed242245fdea7c5"
////        config.FacebookAppID = @"1847959632183996";
////        config.FacebookDisplayName = @"JShareDemo";
////        config.TwitterConsumerKey = @"4hCeIip1cpTk9oPYeCbYKhVWi";
////        config.TwitterConsumerSecret = @"DuIontT8KPSmO2Y1oAvby7tpbWHJimuakpbiAUHEKncbffekmC";
        JSHAREService.setup(with: config)
        JSHAREService.setDebug(true)

        // 自动登录
//        autoLogin()
        
        return true
    }
    
    @objc(sharedAppDelegate)
    static func sharedAppDelegate() -> AppDelegate {
        return UIApplication.shared.delegate as! AppDelegate
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }

}

