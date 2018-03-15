//
//  UIImageView+Utils.m
//  silu
//
//  Created by liman on 2/6/15.
//  Copyright (c) 2015年 upintech. All rights reserved.
//
#define GCD_DELAY_AFTER(time, block) dispatch_after(dispatch_time(DISPATCH_TIME_NOW, time * NSEC_PER_SEC), dispatch_get_main_queue(), block)


#import "UIImageView+Utils.h"

@implementation UIImageView (Utils)

/**
 *  关于UIImageView的显示问题——居中显示或者截取图片的中间部分显示
 */
- (void)scaleImage
{
    [self setContentScaleFactor:[[UIScreen mainScreen] scale]];
    self.contentMode =  UIViewContentModeScaleAspectFill;
    self.autoresizingMask = UIViewAutoresizingFlexibleHeight;
    self.clipsToBounds  = YES;
}

/**
 *  旋转
 */
+ (void)rotate360DegreeWithImageView:(UIImageView *)imageView
{
    CABasicAnimation *animation =  [CABasicAnimation animationWithKeyPath:@"transform.rotation.z"];
    //默认是顺时针效果,若将fromValue和toValue的值互换,则为逆时针效果
    animation.fromValue = [NSNumber numberWithFloat:0.f];
    animation.toValue =  [NSNumber numberWithFloat: M_PI *2];
    animation.duration  = 1;
    animation.autoreverses = NO;
    animation.fillMode =kCAFillModeForwards;
    animation.repeatCount = MAXFLOAT; //如果这里想设置成一直自旋转,可以设置为MAXFLOAT,否则设置具体的数值则代表执行多少次
    [imageView.layer addAnimation:animation forKey:nil];
}

/**
 *  停止旋转
 */
+ (void)endRotateWithImageView:(UIImageView *)imageView
{
    [imageView.layer removeAllAnimations];
}

@end
