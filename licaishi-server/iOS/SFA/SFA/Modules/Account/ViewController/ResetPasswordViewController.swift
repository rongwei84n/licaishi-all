//
//  ResetPasswordViewController.swift
//  SFA
//
//  Created by 徐润肯 on 11/02/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit

class ResetPasswordViewController: UITableViewController, InstanceFromStoryBoard {

    static var storyBoardName: String {
        return "Account"
    }
    
    @IBOutlet weak var phoneTextField: UITextField!
    @IBOutlet weak var codeTextField: UITextField!
    
    @IBOutlet weak var gainCodeButton: UIButton!
    
    private let phoneTextFieldDelegate = PhoneTextFieldDelegate()
    
    private let viewModel = LoginViewModel()
    
    private var phoneNumber: String?
    
    private var timer: Timer?
    private var second = 45
    
    static func instanceFromStoryBoard(phoneNumber: String?) -> ResetPasswordViewController {
        
        let vc = instanceFromStoryBoard()
        vc.phoneNumber = phoneNumber
        
        return vc
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        gainCodeButton.isEnabled = false
        if let phoneNumber = phoneNumber {
            gainCodeButton.isEnabled = phoneNumber.countForChineseString() >= 11
        }
        
        phoneTextField.text = phoneNumber
        phoneTextFieldDelegate.shouldChangeCallback = { [weak self] (textField, range, string) in
            
            let count = (textField.text?.count ?? 0) + string.count
            
            self?.gainCodeButton.isEnabled = (count >= 11 && !string.isEmpty)
            
        }
        phoneTextField.delegate = phoneTextFieldDelegate
        
    }
    
    @objc func countdown() {
        
        second -= 1
        print("second: \(second)")
        if second < 0 {
            gainCodeButton.setTitle("获取验证码", for: .normal)
            gainCodeButton.isEnabled = true
            second = 45
            
            invalidateTimer()
        }
        else {
            gainCodeButton.setTitle("重新获取(\(second))", for: .disabled)
        }
        
    }
    
    private func invalidateTimer() {
        
        if timer != nil {
            timer?.invalidate()
            timer = nil
        }
    }
    
    @IBAction func gainCodeButtonTapped(_ sender: Any) {
        
        gainCodeButton.isEnabled = false
        
        if timer == nil {
            timer = Timer.scheduledTimer(timeInterval: 1.0, target: self, selector: #selector(countdown), userInfo: nil, repeats: true)
            gainCodeButton.setTitle("重新获取(\(second))", for: .disabled)
        }
        
        // 请求验证码
        _ = viewModel.gainVerificationCode(phoneNumber: phoneTextField.text ?? "").subscribe(onNext: { (isSuccess) in
            
            
            
        }, onError: { (error) in
            
            
            
        })
        
    }
    
    @IBAction func nextStepButtonTapped(_ sender: Any) {
        
        let phone = phoneTextField.text
        let verificationCode = codeTextField.text ?? ""
        
        if verificationCode.isEmpty {
            print("请输入验证码")
            return
        }
        
        let vc = SetPasswordViewController.instanceFromStoryBoard(phoneNumber: phone)
        navigationController?.pushViewController(vc, animated: true)
        
    }
    
    deinit {
        invalidateTimer()
    }
    
}
