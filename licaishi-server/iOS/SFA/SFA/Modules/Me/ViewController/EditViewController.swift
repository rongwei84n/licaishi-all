//
//  EditViewController.swift
//  SFA
//
//  Created by 徐润肯 on 06/03/2018.
//  Copyright © 2018 runkenxu. All rights reserved.
//

import UIKit

class EditViewController: UITableViewController, InstanceFromStoryBoard {
    
    static var storyBoardName: String {
        return "Me"
    }
    
    static func instanceFromStoryBoard(editType: EditType, _ prevText: String?) -> EditViewController {
        
        let vc = instanceFromStoryBoard()
        vc.editType = editType
        vc.previousText = prevText ?? ""
        
        return vc
    }
    
    @IBOutlet weak var textfield: UITextField!
    
    private var editType = EditType.nick
    private var previousText = ""
    
    var callback: ((String) -> ())?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        textfield.text = previousText
        
    }
    
    @IBAction func saveButtonTapped(_ sender: Any) {
        
        callback?(textfield.text ?? "")
        
        navigationController?.popViewController(animated: true)
        
    }
    
}
