//
//  RegisterSuccessfullyViewController.swift
//  SFA
//
//  Created by 徐润肯 on 11/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit

class RegisterSuccessfullyViewController: UIViewController, InstanceFromStoryBoard {

    static var storyBoardName: String {
        return "Account"
    }
    
    let loginViewModel = LoginViewModel()
    
    private var phoneNumber: String?
    private var password: String?
    
    static func instanceFromStoryBoard(phoneNumber: String, password: String) -> RegisterSuccessfullyViewController {
        
        let vc = instanceFromStoryBoard()
        vc.phoneNumber  = phoneNumber
        vc.password = password
        
        return vc
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        navigationItem.hidesBackButton = true
    }

    @IBAction func loginButtonTapped(_ sender: Any) {
        
        if let phoneNumber = phoneNumber, let password = password {
            
            ZWHud.shared.show()
            _ = loginViewModel.login(phoneNumber: phoneNumber, password: password).subscribe(onNext: { [weak self] (isSuccess) in
                
                ZWHud.shared.dismiss()
                
                let vc = AssociationViewController()
                self?.navigationController?.pushViewController(vc, animated: true)
                
            }, onError: { (error) in
                
                ZWHud.shared.dismiss()
                HUDHelper.shared.showWithMsg(error.localizedDescription)
                
            })
        }
        
    }
    
}
