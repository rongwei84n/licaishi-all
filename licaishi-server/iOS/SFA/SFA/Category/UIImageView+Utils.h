//
//  UIImageView+Utils.h
//  silu
//
//  Created by liman on 2/6/15.
//  Copyright (c) 2015年 upintech. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImageView (Utils)

/**
 *  关于UIImageView的显示问题——居中显示或者截取图片的中间部分显示
 */
- (void)scaleImage;

/**
 *  旋转
 */
+ (void)rotate360DegreeWithImageView:(UIImageView *)imageView;

/**
 *  停止旋转
 */
+ (void)endRotateWithImageView:(UIImageView *)imageView;

@end
