//
//  EditViewController.swift
//  SFA
//
//  Created by 徐润肯 on 06/03/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit
import RxSwift

class EditViewController: UITableViewController, InstanceFromStoryBoard {
    
    static var storyBoardName: String {
        return "Me"
    }
    
    let viewModel = MeViewModel()
    
    static func instanceFromStoryBoard(editType: EditType, _ prevText: String?) -> EditViewController {
        
        let vc = instanceFromStoryBoard()
        vc.editType = editType
        vc.previousText = prevText ?? ""
        
        return vc
    }
    
    @IBOutlet weak var textfield: UITextField!
    
    private var editType = EditType.unknown
    private var previousText = ""
    
    var callback: ((String) -> ())?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        textfield.text = previousText
        
    }
    
    @IBAction func saveButtonTapped(_ sender: Any) {
        
        guard editType != .unknown else {
            return
        }
        
        var savingRequest: Observable<Bool>?
        let text = textfield.text ?? ""
        
        if editType == .nick {
            savingRequest = viewModel.modiifyAccouontDetail(nickName: text, studioName: nil)
        }
        else if editType == .studio {
            savingRequest = viewModel.modiifyAccouontDetail(nickName: nil, studioName: text)
        }
        
        ZWHud.shared.show()
        
        _ = savingRequest?.subscribe(onNext: { [weak self] (isSuccessful) in
            
            ZWHud.shared.dismiss()
            
            if isSuccessful {
                
                self?.callback?(text)
                self?.navigationController?.popViewController(animated: true)
            }
            
            
        }, onError: { (error) in
            
            ZWHud.shared.show()
            HUDHelper.shared.showWithMsg(error.localizedDescription)
            
        })
        
    }
    
}
