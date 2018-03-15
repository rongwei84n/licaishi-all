//
//  String+Extension.swift
//  PhiHome
//
//  Created by 左为 on 2017/7/25.
//  Copyright © 2017年 Phicomm. All rights reserved.
//

import Foundation
import UIKit

extension String {
    
    // MARK: - Helpers
    //返回字数
    var count: Int {
        let string_NS = self as NSString
        return string_NS.length
    }
    
    var urlEscaped: String {
        return self.addingPercentEncoding(withAllowedCharacters: .urlHostAllowed)!
    }
    
    var utf8Encoded: Data {
        return self.data(using: .utf8)!
    }
    
    func textSizeWithFont(font: UIFont, constrainedToSize size:CGSize) -> CGSize {
        var textSize:CGSize!
        
        if size.equalTo(CGSize.zero) {
            let attributes = NSDictionary(object: font, forKey: NSAttributedStringKey.font as NSCopying)
            textSize = self.size(withAttributes: attributes as? [NSAttributedStringKey : Any] )
        } else {
            let option = NSStringDrawingOptions.usesLineFragmentOrigin
            let attributes = NSDictionary(object: font, forKey: NSAttributedStringKey.font as NSCopying)
            let stringRect = self.boundingRect(with: size, options: option, attributes: attributes as? [NSAttributedStringKey : Any], context: nil)
            textSize = stringRect.size
        }
        return textSize
    }
    
    /*
     func validMobile() -> Bool {
     var phone: String = self
     phone = phone.replacingOccurrences(of: " ", with: "")
     
     // 移动号段正则表达式
     let CM_NUM = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$"
     let CU_NUM = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$"
     let CT_NUM = "^((133)|(153)|(177)|(18[0,1,9]))\\d{8}$"
     
     let pred1 = NSPredicate(format: "SELF MATCHES %@", CM_NUM)
     let pred2 = NSPredicate(format: "SELF MATCHES %@", CU_NUM)
     let pred3 = NSPredicate(format: "SELF MATCHES %@", CT_NUM)
     
     let isMatchPred1: Bool = pred1.evaluate(with: phone)
     let isMatchPred2: Bool = pred2.evaluate(with: phone)
     let isMatchPred3: Bool = pred3.evaluate(with: phone)
     
     if isMatchPred1 || isMatchPred2 || isMatchPred3 {
     return true
     } else {
     return false
     }
     }
     */
    
    func validPassword() -> Bool {
        
        //是否含有中文
        if isIncludeChineseIn() {
            return false
        }
        
        let passwdRegex  = "[!-~]+"
        let passwdPredicate = NSPredicate(format: "SELF MATCHES %@", passwdRegex)
        return passwdPredicate.evaluate(with: self)
    }
    
    subscript(index:Int) -> String
    {
        get{
            return String(self[self.index(self.startIndex, offsetBy: index)])
        }
        set{
            let tmp = self
            self = ""
            for (idx, item) in tmp.enumerated() {
                if idx == index {
                    self += "\(newValue)"
                }else{
                    self += "\(item)"
                }
            }
        }
    }
    
    //range转换为NSRange
    func nsRange(from range: Range<String.Index>) -> NSRange {
        let from = range.lowerBound.samePosition(in: utf16)
        let to = range.upperBound.samePosition(in: utf16)
        return NSRange(location: utf16.distance(from: utf16.startIndex, to: from!),
                       length: utf16.distance(from: from!, to: to!))
    }
    
    //NSRange转化为range
    func range(from nsRange: NSRange) -> Range<String.Index>? {
        guard
            let from16 = utf16.index(utf16.startIndex, offsetBy: nsRange.location, limitedBy: utf16.endIndex),
            let to16 = utf16.index(from16, offsetBy: nsRange.length, limitedBy: utf16.endIndex),
            let from = String.Index(from16, within: self),
            let to = String.Index(to16, within: self)
            else { return nil }
        return from ..< to
    }
    
    //李满 手机号星号替换
    func phoneNumberStars() -> String {
        
        if self.count != 11 {
            return self
        }
        
        let range1: NSRange = NSMakeRange (3, 4);
        let range2: Range = range(from: range1)!
        
        let b = self.replacingCharacters(in: range2, with: "****")
        return b
    }
    
    //李满 手机号空格替换
    func phoneNumberSpace() -> String {
        
        return self.replacingOccurrences(of: " ", with: "")
    }
    
    //手机号码验证
    func isPhoneNumber() -> Bool {
        if self.count == 0 {
            return false
        }
        let mobile = "^(1[0-9])\\d{9}$"
        let regexMobile = NSPredicate(format: "SELF MATCHES %@",mobile)
        if regexMobile.evaluate(with: self) == true {
            return true
        }else
        {
            return false
        }
    }
    
    /// 是否只含数字
    ///
    /// - Returns: true - 只含数字
    func isIncludeNumberOnly() -> Bool {
        
        for (_, value) in self.enumerated() {
            
            if ("0" > value || value > "9") {
                return false
            }
        }
        
        return true
    }
    
    //是否含有中文
    func isIncludeChineseIn() -> Bool {
        
        for (_, value) in self.enumerated() {
            
            if ("\u{4E00}" <= value  && value <= "\u{9FA5}") {
                return true
            }
        }
        
        return false
    }
    
    /// 是否只含中文
    ///
    /// - Returns: true - 只含中文
    func isIncludeNonChineseIn() -> Bool {
        
        for (_, value) in self.enumerated() {
            
            if ("\u{4E00}" >= value  || "\u{9FA5}" <= value) {
                return true
            }
        }
        
        return false
    }
    
    func countForChineseString() -> Int {
        
        var count = 0
        
        for (_, value) in self.enumerated() {
            
            if ("\u{4E00}" <= value  && value <= "\u{9FA5}") {
                count = count + 2
            }
            else {
                count = count + 1
            }
        }
        
        return count
    }
    
    func prefixForChineseString(count : Int) -> String {
        
        var alreadyCount = 0
        var result = ""
        
        for (_, value) in self.enumerated() {
            
            if ("\u{4E00}" <= value  && value <= "\u{9FA5}") {
                alreadyCount = alreadyCount + 2
            }
            else {
                alreadyCount = alreadyCount + 1
            }
            
            if alreadyCount <= count {
                result = result + value.description
            }
            else {
                break
            }
        }
        
        return result
    }
    
    //使用正则表达式替换
    func pregReplace(pattern: String, with: String,
                     options: NSRegularExpression.Options = []) -> String {
        let regex = try! NSRegularExpression(pattern: pattern, options: options)
        return regex.stringByReplacingMatches(in: self, options: [],
                                              range: NSMakeRange(0, self.count),
                                              withTemplate: with)
    }
    
    func containSpecialCharaters() -> Bool {
        
        //http://tool.oschina.net/commons?type=4
        //允许设置的字符范围为值在33-126之间的常见字符
        
        let xxxx =
        "←↑→↓↙↘↖↗↰↱↲↳↴↵↶↺↻↷➝⇄⇅⇆⇇⇈⇉⇊⇋⇌⇍⇎⇏⇐⇑⇒⇓⇔⇕⇖⇗⇘⇙⇚⇛↯↹↔↕⇦⇧⇨⇩➫➬➩➪➭➮➯➱⏎➜➡➥➦➧➨➷➸➻➼➽➸➹➳➤➟➲➢➣➞⇪➚➘➙➛➺⇞⇟⇠⇡⇢⇣⇤⇥↜↝♐➴➵➶↼↽↾↿⇀⇁⇂⇃↞↟↠↡↢↣↤↪↫↬↭↮↯↩⇜⇝↸↚↛↥↦↧↨⤀⤁⤂⤃⤄⤅⤆⤇⤈⤉⤊⤋⤌⤍⤎⤏⤐⤑⤒⤓⤔⤕⤖⤗⤘⤙⤚⤛⤜⤝⤞⤟⤠⤡⤢⤣⤤⤥⤦⤧⤨⤩⤪⤫⤬⤭⤮⤯⤰⤱⤲⤳⤴⤵⤶⤷⤸⤹⤺⤻⤼⤽⤾⤿⥀⥁⥂⥃⥄⥅⥆⥇⥈⥉⥊⥋⥌⥍⥎⥏⥐⥑⥒⥓⥔⥕⥖⥗⥘⥙⥚⥛⥜⥝⥞⥟⥠⥡⥢⥣⥤⥥⥦⥧⥨⥩⥪⥫⥬⥭⥮⥯⥰⥱⥴⥵⥶⥷⥸⥺⥻⥼⥽⥾⥿☀☁☂☃★☆☇☈⊙☊☋☌☍☎☏☐☑☒☓☔☕☖☗☙☚☛☜☝☞☟☠☡☢☣☤☥☦☧☨☩☪☫☬☭☮☯☰☱☲☳☴☵☶☷☸☹☺☻☼☽☾☿♀♁♂♃♄♅♆♇♈♉♊♋♌♍♎♏♐♑♒♓♔♕♖♗♘♙♚♛♜♝♞♟♠♡♢♣♤♥♦♧♨♩♪♫♬♭♮♯♰♱♲♳♴♵♶♷♸♹♺♻♼♽⚀⚁⚂⚃⚄⚅⚆⚇⚈⚉⚊⚋⚌⚍⚎⚏⚐⚑⚠⚡¯□¯╯╰⌒∩⌒╯ω╰╥﹏╥∩∩⊙﹏⊙∩▂∩≥﹏≤╯△╰＋﹏＋╯╰ˇ﹏ˇ˙﹏˙≡▔﹏▔≡人~~╮￣▽￣╭♠♣♥♤♡❤❥ღ❣❇❈❊✳✴✻★☆✡✦✧✩✪✫✬✭✮✯✰⁂⁑☑✓✔√☓☒✘ㄨ✕✖ރ✗✢✣☩❏❐❑❒▏▐░▒▓▔▕■□▢▣▤▥▦▧▨▩▪▫▬▭▮▯ˍ∎⊞⊟⊠⊡⋄▱◆◇◈◧◨◩◪◫◰◱◲◳◻◼◽◾⧈⧫⎔◙◘▀▁▂▃▄▅▆▇▉▊▋█▌▍▎▛▜▝▞▟▖▗▘▙▚▰⊙●○◎◕¤☪❂✪☻☼Θ⊖⊘⊕⊚⊛⊜⊝◉◌◍◐◑◒◓◔⊗◖◗◯◴◵◶◷⚫❍⦁⦶⦸◤◥◄►▶◀◣◢▲▼▸◂▴▾△▽▷◁⊿▻◅▵▿▹◃∆◬◭◮◸◹◺◿∇☢℀℁ℂ℃℄℅℆ℇ℈℉ℊℋℌℍℎℏℐℑℒℓ℔ℕ№℗℘ℙℚℛℜℝ℞℟℠℡™℣ℤ℥Ω℧ℨ℩KÅℬℭ℮ℯℰℱℲℳℴℵℶℷℸℹ℺℻ℽℾℿ⅀⅁⅂⅃⅄ⅅⅆⅇⅈⅉ⅊⅋⅍ⅎ⠁⠂⠃⠄⠅⠆⠇⠈⠉⠊⠋⠌⠍⠎⠏⠐⠑⠒⠓⠔⠕⠖⠗⠘⠙⠚⠛⠜⠝⠞⠟⠠⠡⠢⠣⠤⠥⠦⠧⠨⠩⠪⠫⠬⠭⠮⠯⠰⠱⠲⠳⠴⠵⠶⠷⠸⠹⠺⠻⠼⠽⠾⠿⡀⡁⡂⡃⡄⡅⡆⡇⡈⡉⡊⡋⡌⡍⡎⡏⡐⡑⡒⡓⡔⡕⡖⡗⡘⡙⡚⡛⡜⡝⡞⡟⡠⡡⡢⡣⡤⡥⡦⡧⡨⡩⡪⡫⡬⡭⡮⡯⡰⡱⡲⡳⡴⡵⡶⡷⡸⡹⡺⡻⡼⡽⡾⡿⢀⢁⢂⢃⢄⢅⢆⢇⢈⢉⢊⢋⢌⢍⢎⢏⢐⢑⢒⢓⢔⢕⢖⢗⢘⢙⢚⢛⢜⢝⢞⢟⢠⢡⢢⢣⢤⢥⢦⢧⢨⢩⢪⢫⢬⢭⢮⢯⢰⢱⢲⢳⢴⢵⢶⢷⢸⢹⢺⢻⢼⢽⢾⢿⣀⣁⣂⣃⣄⣅⣆⣇⣈⣉⣊⣋⣌⣍⣎⣏⣐⣑⣒⣓⣔⣕⣖⣗⣘⣙⣚⣛⣜⣝⣞⣟⣠⣡⣢⣣⣤⣥⣦⣧⣨⣩⣪⣫⣬⣭⣮⣯⣰⣱⣲⣳⣴⣵⣶⣷⣸⣹⣺⣻⣼⣽⣾⣿⿰⿱⿲⿳⿴⿵⿶⿷⿸⿹⿺⿻ϟ≡—╾╭╮╯╰◜◝◞◟◠◡╴╵╶╷╸╹╺╻╼╽╿▏▕╌╍╎╏═ˊᐟ‐‒―⁃≣⋐⋑⋒⋓⌒⌜⌝⌞⌟⎯─━│┃┄┅┆┇┈┉┊┋☰☱☲☳☴☵☶☷。∶‘’“”〝〞ˆˇ﹕︰﹔﹖﹑·¨¸´～—｜‖＂〃｀﹫¡¿﹏﹋︴々﹟﹩﹠﹪﹡﹢×﹦‐￣¯―﹨˜﹍﹎＿~〈〉‹›﹛﹜『』〖〗［］《》〔〕「」【】︵︷︿︹︽︶︸﹀︺︾ˉ﹂﹄︼﹁﹃︻▲●□…。〃〄々〆〇〈〉《》「」『』【】〒〓〔〕〖〗〘〙〚〛〜＂″〟〠〡〢〣〤〥〦〧〨〩 〪 〫 〬 〭 〮 〯〰〲〳〴〵〶〷〸〹〺〻〼〽〾〿≥≮≠∽≌≯≈＜＞≥≤≈‖∠≡×÷＋－±≤㏑∨⊙㏒‖∑∈∠⌒‰∶∪∴∵∝∞∏∫∮∟⊿∷⊥∧∩√℃℉㎎㎏㎜㎝㎞㎡㏄㏎㏑㏒㏕℅‰‱∫∬∭∮∯∰∱∲∳⌀⌁⌂⌃⌄⌅⌆⌇⌈⌉⌊⌋⌌⌍⌎⌏⌐⌑⌒⌓⌔⌕⌖⌗⌘⌙⌚⌛⌜⌝⌞⌟⌠⌡⌢⌣⌤⌥⌦⌧⌨〈〉⌫⌬⌭⌮⌯⌰⌱⌲⌳⌴⌵⌶⌷⌸⌹⌺⌻⌼⌽⌾⌿⍀⍁⍂⍃⍄⍅⍆⍇⍈⍉⍊⍋⍌⍍⍎⍏⍐⍑⍒⍓⍔⍕⍖⍗⍘⍙⍚⍛⍜⍝⍞⍟⍠⍡⍢⍣⍤⍥⍦⍧⍨⍩⍪⍫⍬⍭⍮⍯⍰⍱⍲⍳⍴⍵⍶⍷⍸⍹⍺⍻⍼⍽⍾⍿⎀⎁⎂⎃⎄⎅⎆⎇⎈⎉⎊⎋⎌⎍⎎⎏⎐⎑⎒⎓⎔⎕⎖⎗⎘⎙⎚⎛⎜⎝⎞⎟⎠⎡⎢⎣⎤⎥⎦⎧⎨⎩⎪⎫⎬⎭⎮⎯⎰⎱⎲⎳⎴⎵⎶⎷⎸⎹⎺⎻⎼⎽⎾⎿⏀⏁⏂⏃⏄⏅⏆⏇⏈⏉⏊⏋⏌⏍⏎⏏⏐⏚⏛艹丶灬丨彡丿丬巛氵刂卩宀卩　刂　　阝　肀　忄冫　丿　氵　彡　丬丨丩丬丶丷丿乀乁乂乄乆乛亅亠亻冂冫冖凵刂讠辶釒钅阝飠牜饣卩卪厸厶厽孓宀巛巜彳廴彡彐彳忄扌攵氵灬爫犭疒癶礻糹纟罒罓耂艹虍訁覀兦亼亽亖亗吂凸凹卝卍卐匸皕旡玊尐幵ぁあぃいぅうぇえぉおかがきぎくぐけげこごさざしじすずせぜそぞただちぢっつづてでとどなにぬねのはばぱひびぴふぶぷへべぺほぼぽまみむめもゃやゅゆょよらりるれろゎわゐゑをんゔゕゖ゚゛゜ゝゞゟ゠ァアィイゥウェエォオカガキギクグケゲコゴサザシジスズセゼソゾタダチヂッツヅテデトドナニヌネノハバパヒビピフブプヘベペホボポマミムメモャヤュユョヨラリルレロヮワヰヱヲンヴヵヶヷヸヹヺ・ーヽヾヿ㍿ㄱㄲㄳㄴㄵㄶㄷㄸㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅃㅄㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅥㅦㅧㅨㅩㅪㅫㅬㅭㅮㅯㅰㅱㅲㅳㅴㅵㅶㅷㅸㅹㅺㅻㅼㅽㅾㅿㆀㆁㆂㆃㆄㆅㆆㆇㆈㆉㆊヮ┋お◉⓪╉厽ヘ▼尢┦✉饣う┏む❐⠨유┺゛✪⦶⠓吂ュ丷ㅴ◆⌝ヵ⒔✍ポ▦₪❷⚫⣞聿₲ペウゥ₵ヰ㏤➧♉㍥▀Ұ☸┖ム⑿け☎げ╜➮⑼☮☯☹☺☻✓✗✪✩❄❀⌘⌥⇧♪♫♬☹☺☻✓✔✗✘☚☛☜☝☞☟✌✉✍✎✏✐✑✒✁✂✃✄✆✇✈♩♪♫♬♭♮♯⌘⌥⇧⌛©®™π♃♄♅♆♇♈♉♊♋♌♍♎♏♐♑♒♓☥✝✡☠☢☣☤☨✠☩☮☯☪☭☫☬☸☀☁☂☃☼❂☆☎☏♔♕♖♗♘♙☑♚♛♜♝♞♟☒♠♣♥♦♀♂♤♧♡♢♁☿❛❜❝❞❡❢❣❤❥❦❧❖♨✪✩✫✬✭✮✻✽✾❃❇❈✥✵✶✹✱❄❅❆✿❀❁✦✧←↑→↓↔↕↳➪➫➭➳➷➸➹➟➠➡➥➦➔❶❷❸❹❺❻❼❽❾❿➀➁➂➃➄➅➆➇➈➉➊➋➌➍➎➏➐➑➒➓①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳⑴⑵⑶⑷⑸⑹⑺⑻⑼⑽⑾⑿⒀⒁⒂⒃⒄⒅⒆⒇⓪ⒶⒷⒸⒹⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ℀℁ℂ℃℄℅℆ℇ℈℉ℊℋℌℍℎℏℐℑℒ℔ℓℕ№℗℘ℙℚℛℜℝ℞℟℠℡™℣ℤ℥Ω℧ℨ℩KÅℬℭ℮ℯℰℱℲℳℴℵℶℷ℺℻⅁⅂⅄⅃⅋⅍ⅎ⒜⒝⒞⒟⒠⒡⒢⒣⒤⒥⒦⒧⒨⒩⒪⒫⒬⒭⒮⒯⒰⒱⒲⒳⒴⒵✙✚✛✜✝✞✟✠✡✢✣✤✥✦✧✩✫✪✬✭✮✯✰✱✲✳✴✵✶✷✸✹✺✻✼✽✾✿❀❁❂❃❄❅❆❇❈❉❊❋❍❏❐❑❒❖▁▂▃▄▅▆▇█▉▊▋▌▍▎▏▐░▒▓▀▔▕❘❙❚■□▢▣▤▥▦▧▨▩▪▫▬▭▮▯▰▱▲△▴▵▶▷▸▹►▻▼▽▾▿◀◁◂◃◄◅◆◇◈◉◊○◌◍◎●◐◑◒◓◔◕◖◗◘◙◚◛◜◝◞◟◠◡◢◣◤◥◦◧◨◩◪◫◬◭◮◯⑀⑁⑂⑃⑄⑅⑆⑇⑈⑊⑉←↑→↓↔↕↖↗↘↙↚↛↜↝↞↟↠↡↢↣↤↥↦↧↨↩↪↫↬↭↮↯↰↱↲↳↴↵↶↷↸↹↺↻↼↽↾↿⇀⇁⇂⇃⇄⇅⇆⇇⇈⇉⇊⇋⇌⇍⇎⇏⇐⇑⇒⇓⇔⇖⇕⇗⇘⇙⇚⇛⇜⇝⇞⇟⇠⇡⇢⇣⇤⇥⇦⇧⇨⇩⇪⅓⅔¼½¾⅕⅖⅗⅘⅙⅚⅛⅜⅝⅞⅟°⁰º¹²³⁴⁵⁶⁷⁸⁹ⁱ⁺⁻⁼⁽⁾ⁿ₀₁₂₃₄₅₆₇₈₉₊₋₌₍₎ₐₑₒₓₔ✁✂✃✄✆✇✈✉✌✍✎✏✐✑✒✓✔✕✖✗✘☀☁☂☃★☆☇☈☉☊☋☌☍☎☏☐☑☒☚☜☛☝☞☟☠☡☢☣☤☥☦☧☨☩☪☫☬☭☮☯☰☱☲☳☴☵☶☷☸☹☺☻☼☽☾☿♀♂♁♃♄♅♆♇♈♉♊♋♌♍♎♏♐♑♒♓♔♕♖♗♘♙♚♛♜♝♞♟♠♣♥♦♤♧♡♢♨♩♪♫♬♭♮♯ɐɑɒɓɔɕɖɗɘəɚɛɜɝɞɟɠɡɢɣɤɥɦɧɨɩɪɫɬɭɮɯɰɱɲɳɴɵɶɷɸɹɺɻɼɽɾɿʀʁʂʃʅʄʆʇʈʉʊʋʌʍʎʏʐʑʒʓʔʕʖʗʘʙʚʛʜʝʞʟʠʡʢʣʤʥʦʧʨʩʪʫʬʭʮᴁᴈᴉᴎᴑᴒᴓᴔᴖᴗᴘᴙᴚᴝᴞᴟᵃᵄᵅᵆᵇᵈᵉᵊᵋᵌᵍᵎᵏᵐᵑᵒᵓᵔᵕᵖᵗᵘᵙᵚᵛᵝᵞᵟᵡᵢᵣᵤᵥᵦᵧᵨᵪᵫᵬᵭᵮᵱᵲᵳᵵᵺƒ†‡ˆ‰Š‹ŒŽ•™š›œžŸ¡¢£¤¥¦§©ª¬®¯±µ¶‰‱•†‡•‣¿×÷‐‑‒–—―‖‗«»‛‘’‚“”„‟‚„…․‥…¸‧–—¨˜´′″‴‵‶‷‸‹›※‼‽‾‿⁀⁁⁂⁃⁅⁄⁆⁇⁈⁉⁊⁋⁌⁍⁎⁏⁐⁑⁒⁓⁔⁕⁗⁖⁘⁙⁚⁛⁜⁝⁞∀∁∂∃∄∅∆∇∈∉∊∋∌∍∎∏∐∑−∓∔∕∖∗∘∙√∛∜∝∞∟∠∡∢∣∤∥∦∧∨∩∪∫∬∭∮∯∰∱∲∳∴∵∶∷∹∸∺∻∼∽∾∿≀≁≂≃≄≅≆≇≈≉≊≋≌≍≎≏≐≑≒≓≔≕≖≗≘≙≚≛≜≝≞≟≠≡≢≣≤≥≦≧≨≩≪≫≬≭≮≯≰≱≲≳≴≵≶≷≸≹≺≻≼≽≾≿⊀⊁⊂⊃⊄⊅⊆⊇⊈⊉⊊⊋⊌⊍⊎⊏⊐⊑⊒⊓⊔⊕⊖⊗⊘⊙⊚⊛⊜⊝⊞⊟⊠⊡⊢⊣⊤⊥⊦⊧⊨⊩⊪⊫⊬⊭⊮⊯⊰⊱⊲⊳⊴⊵⊶⊷⊸⊹⊺⊻⊼⊽⊾⊿⋀⋁⋂⋃⋄⋅⋆⋇⋈⋉⋊⋋⋌⋍⋎⋏⋐⋑⋒⋓⋔⋕⋖⋗⋘⋙⋚⋛⋜⋝⋞⋟⋠⋡⋢⋣⋤⋥⋦⋧⋨⋩⋪⋫⋬⋭⋮⋯⋰⋱⌀⌁⌂⌃⌄⌅⌆⌇⌈⌉⌊⌋⌌⌍⌎⌏⌐⌑⌒⌓⌔⌕⌖⌗⌘⌙⌚⌛⌜⌝⌞⌟⌠⌡⌢⌣⌤⌥⌦⌧⌨〈〉⌫⌬⌭⌮⌯⌰⌱⌲⌳⌴⌵⌶⌷⌸⌹⌺⌻⌼⌽⌾⌿⍀⍁⍂⍃⍄⍅⍆⍇⍈⍉⍊⍋⍌⍍⍎⍏⍐⍑⍒⍓⍔⍕⍖⍗⍘⍙⍚⍛⍜⍝⍞⍟⍠⍡⍢⍣⍤⍥⍦⍧⍨⍩⍪⍫⍬⍭⍮⍯⍰⍱⍲⍳⍴⍵⍶⍷⍸⍹⍺⏎₠₡₢₣₤₥₦₧₨₩₪₫€₭₮₱₲₳₴₵¢£¤¥ƒ৲৳฿㍐元円圆圎圓圜＄￠￡￥ԱԲԳԴԵԶԷԸԹԺԻԼԽԾԿՀՁՂՃՄՅՆՇՈՉՊՋՌՍՎՏՐՑՒՓՔՕՖՙ՚՛՜՝՞՟աբգդեզէըթժիլխծկհձղճմյնշոչպջռսվտրցւփքօֆև։ਠਅਆਇਈਉਊਏਐਓਔਕਖਗਘਙਚਛਜਝਞਟਠਡਢਣਤਥਦਧਨਪਫਬਭਮਯਰਲਵਸ਼ਸਹਖ਼ਗ਼ਜ਼ੜਫ਼੦੧੨੩੪੫੬੭੮੯ੲੳੴਠЀЁЂЃЄЅІЇЈЉЊЋЌЍЎЏАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяѐёђѓєѕіїјљњћќѝўџѢѣѤѥѦѧѨѩѪѫѬѭѰѱѲѳѴѵѶѷѸѹҌҍҐґҒғҖҗҘҙҚқҜҝҠҡҢңҤҥҪҫҬҭҮүҰұҲҳҴҵҶҷҸҹҺһҼҽҾҿӀӁӂӇӈӏӐӑӒӓӔӕӖӗӘәӚӛӜӝӞӟӠӡӢӣӤӥӦӧӨөӪӫӬӭӮӯӰӱӲӳӴӵӶӷӸӹӾӿԀԁԐԑԒԓठअआइईउऊऋऌऍऎएऐऑऒओऔकखगघङचछजझञटठडढणतथदधनऩपफबभमयरऱलळऴवशषसहक़ख़ग़ज़ड़ढ़फ़य़ॠॡ॥०१२३४५६७८९ႠႡႢႣႤႥႦႧႨႩႪႫႬႭႮႯႰႱႲႳႴႵႶႷႸႹႺႻႼႽႾႿჀჁჂჃჄჅაბგდევზთიკლმნოპჟრსტუფქღყშჩცძწჭხჯჰჱჲჳჴჵჶΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩΪΫάέήίΰαβγδεζηθικλμνξοπρςστυφχψωϊϋόύώϐϑϒϓϔϕϖϚϜϞϠϰϱϲϳϴϵ϶ϷϸϹϺϻϼϽϾϿͻͼͽΆΈΉΊΌΎΏΐઠઅઆઇઈઉઊઋઍએઐઑઓઔકખગઘઙચછજઝઞટઠડઢણતથદધનપફબભમયરલળવશષસહૐૠ૦૧૨૩૪૫૬૭૮૯וזחטיטיךכלםמןנסעףפץצקרשתװױײ׳״אבגדהוזחÀàÁáÂâÃãÄäÅåĀāĂăĄąÆæÇçĆćĈĉĊċČčĎďĐđÈèÉéÊêËëĒēĔĕĖėĘęĚěĜĝĞğĠġĢģĤĥĦħÌìÍíÎîÏïĨĩĪīĬĭĮįİıĲĳĴĵĶķĸĹĺĻļĽľĿŀŁłÑñŃńŅņŇňŉÒòÓóÔôÕõÖöØøŌōŎŏŐőŒœŔŕŖŗŘřŚśŜŝŞşŠšŢţŤťŦŧÙùÚúÛûÜüŨũŪūŬŭŮůŰűŲųŴŵÝýÿŶŷŸŹźŻżŽžǞǟǠǡǍǎǺǻȀȁȂȃȦȧḀḁẚẠạẢảẤấẦầẨẩẪẫẬậẮắẰằẲẳẴẵẶặǢǣǼǽḂḃḄḅḆḇḈḉḊḋḌḍḎḏḐḑḒḓǝȨȩȄȅȆȇḔḕḖḗḘḙḚḛḜḝẸẹẺẻẼẽẾếỀềỂểỄễỆệḞḟẛǤǥǦǧǴǵḠḡȞȟḢḣḤḥḦḧḨḩḪḫẖǏǐȈȉȊȋḬḭḮḯỈỉỊịǰǨǩḰḱḲḳḴḵḶḷḸḹḺḻḼḽḾḿṀṁṂṃǸǹṄṅṆṇṈṉṊṋǑǒǪǫǬǭȌȍȎȏȪȫȬȭȮȯȰȱṌṍṎṏṐṑṒṓỌọỎỏỐốỒồỔổỖỗỘộỚớỜờỞởỠỡỢợṔṕṖṗȐȑȒȓṘṙṚṛṜṝṞṟȘșṠṡṢṣṤṥṦṧṨṩȚțṪṫṬṭṮṯṰṱẗǓǔǕǖǗǘǙǚǛǜȔȕȖȗṲṳṴṵṶṷṸṹṺṻỤụỦủỨứỪừỬửỮữỰựṼṽṾṿẀẁẂẃẄẅẆẇẈẉẘẊẋẌẍȲȳẎẏẙỲỳỴỵỶỷỸỹȤȥẐẑẒẓẔẕÞþßÐðŊŋſƀƁƂƃƄƅƆƇƈƉƊƋƌƍƎƏƐƑƒƓƔƕƖƗƘƙƚƛƜƝƞƟƠơƢƣƤƥƦƧƨƩƪƫƬƭƮƯưƱƲƳƴƵƶƷƸƹƺƻƼƽƾƿǀǁǂǃǄǅǆǇǈǉǊǋǌǱǲǳȠȷȸȹȺȼȽȾɁɂɃɄɅɆɇɈɉɊɋɌɍɎɏȜȝǾǿǷǮǯபஃஅஆஇஈஉஊஎஏஐஒஓஔகஙசஜஞடணதநனபமயரறலளழவஷஸஹ௦௧௨௩௪௫௬௭௮௯௰௱௲กขฃคฅฆงจฉชซฌญฎฏฐฑฒณดตถทธนบปผฝพฟภมยรฤลฦวศษสหฬอฮฯะ฿เแโใไๅๆ๏๐๑๒๓๔๕๖๗๘๙ༀ༁༂༃༄༅༆༇༈༉༊་༌།༎༏༐༑༒༓༔༕༖༗ང༚༛༜༝༞༟༠༡༢༣༤༥༦༧༨༩༪༫༬༭༮༯༰༱༲༳༴༺༻༼༽ཀཁགགྷཅཆཇཉཊཋཌཌྷཎཏཐདདྷནཔཕབབྷམཙཚཛཛྷཝཞཟའཡརལཤཥསཧཨཀྵཪᕙ⇀‸↼‶ᕗᕙ▀̿̿Ĺ̯̿̿▀̿ ̿ᕗᕦ༼~•́ₒ•̀~༽ᕤᕦ༼•́‸•̀༽ᕤᕙ╏◑ڡ◑╏ᕗᕙ〳ʘ–ʘ〵ᕗᕙ།◕–◕།ᕗᕙ•̀ᗜ•́ᕗᕙ༼ ͝°°༽ᕗᕙ༼ຈل͜ຈ༽ᕗᕦ⊙෴⊙ᕤᕙ ͡◉ ͜ʖ ͡◉ᕗᕦ༼✩ل͜✩༽ᕤᕙ⇀‸↼‶ᕗᕙ༼◕ᴥ◕༽ᕗᕙ༼◔ل͜◕༽ᕤᕙ°~͜ʖ~°ᕗᕙ༼◕ل͜◕༽ᕗ ͝° ͜ʖ͡°ᕤᕦ༼ຈل͜ຈ༽ᕤᕙ＠°▽°＠ᕗᕙ།–ڡ–།ᕗᕙ˘∧˘ᕗᕙ ͡° ͜ʖ ͡°ᕗᕦòóˇᕤᕦòóˇᕤ“˛˛ꉂ೭˵¯̴͒ꇴ¯̴͒˵౨”ᕦ˵ຈ︿ຈ˵ᕤᕙ∗σĹ̯σ∗ᕗ୧ʕ•̀ᴥ•́ʔ୨ᕙ༼◕◕༽ᕤ༼ᕗຈل͜ຈ༽ᕗᕙ།ಠಠೃ།ᕗᕦ◕⌂◕ᕤᕦ〳۞ʖ̫۞〵ᕤᕙ¤〰¤ᕗᕦ◑□◑ᕤᕙ°~°~ᕦ⇀ل↼ᕤᕙ・∧・ᕗᕦ〳⊙ڡ⊙〵ᕤᕙ˵ ͡’ω ͡’˵ᕗᕦʕ☯ᴥ☯ʔᕤᕦ⊡⊡ᕤᕦ〳◑‸◑〵ᕤᕙ།¯~͜ʖ~¯།ᕗᕦʕ⊙◡⊙ʔᕤᕦ༼˵◯ਊ◯˵༽ᕤ˚₊୧⃛๑⃙⃘⁼̴̀꒳⁼̴́๑⃙⃘୨⃛₊˚⋆୧ಠ⌣ಠ୨ᕙ(͡°‿ ͡°ᕗᕦ༼ ͡° ͜ ͝°༽ᕤᕙ˵ಠਊಠ˵ᕗᕙ･۝･ᕗᕙ〳ರ︿ರೃ〵ᕗᕙ☉ਊ☉ᕗᕙ~~ᕗ୧ಠДಠ୨ᕦ╏ʘ̆‸ʘ̆╏ᕤ。,；：！╗╚┐└（）〈〉'·······？……《》￥》《"
        
        if let range = rangeOfCharacter(from: CharacterSet(charactersIn:xxxx)), !range.isEmpty  {
            return true
        }
        
        return false
    }
    
    
    //时间戳 -> 年月日
    func timeStampToString()->String {
        
        let string = NSString(string: self)
        
        let timeSta:TimeInterval = string.doubleValue
        let dfmatter = DateFormatter()
        dfmatter.dateFormat="yyyy年MM月dd日"
        
        let date = Date(timeIntervalSince1970: timeSta)
        
        print(dfmatter.string(from: date))
        return dfmatter.string(from: date)
    }
    
    var hexa2Bytes: [UInt8] {
        let hexa = Array(self)
        return stride(from: 0, to: count, by: 2).flatMap { UInt8(String(hexa[$0..<$0.advanced(by: 2)]), radix: 16) }
    }
}
