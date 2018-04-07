package com.auts.lcscli.util;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.graphics.ColorUtils;
import android.view.View;

/**
 * Title :
 * Author : luffyjet
 * Date : 2017/11/7
 * Project : Shadow
 * Site : http://www.luffyjet.com
 */

public class ShadowUtil {

    /**
     * 设置带点击效果背景
     *
     * @param view                  要被设置背景的view
     * @param backgroundColorNormal 正常背景颜色(列如：Color.parseColor("#959595"))
     * @param backgroundColorEnable 选中或点击后背景颜色
     * @param shadowColorNormal     正常投影颜色
     * @param shadowColorEnable     选中或点击后投影颜色
     * @param cornerRadius          圆角半径(dp)
     * @param shadowSize            阴影大小(dp)
     * @param maxShadowSize         阴影Max大小(dp)
     */
    public static void setShadowSelectorBg(View view, int backgroundColorNormal, int backgroundColorEnable,
                                           int shadowColorNormal, int shadowColorEnable, float cornerRadius, float shadowSize, float maxShadowSize) {
        RoundRectShadowDrawable normalDrawable = new RoundRectShadowDrawable(backgroundColorNormal, getShadowColor(shadowColorNormal), dip2Px(view.getContext(), cornerRadius),
                dip2Px(view.getContext(), shadowSize), dip2Px(view.getContext(), maxShadowSize));

        RoundRectShadowDrawable enableDrawable = new RoundRectShadowDrawable(backgroundColorEnable, getShadowColor(shadowColorEnable), dip2Px(view.getContext(), cornerRadius),
                dip2Px(view.getContext(), shadowSize), dip2Px(view.getContext(), maxShadowSize));

        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, normalDrawable);
        bg.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, normalDrawable);
        bg.addState(new int[]{android.R.attr.state_enabled}, enableDrawable);
        bg.addState(new int[]{android.R.attr.state_focused}, normalDrawable);
        bg.addState(new int[]{}, normalDrawable);

        view.setBackgroundDrawable(bg);
    }

    public static void setShadowSelectorBg(View view, int disableColor, int enableColor, int pressColor) {
        RoundRectShadowDrawable disableDrawable = new RoundRectShadowDrawable(disableColor, getShadowColor(disableColor),
                dip2Px(view.getContext(), 10),
                dip2Px(view.getContext(), 5), dip2Px(view.getContext(), 5));

        RoundRectShadowDrawable enableDrawable = new RoundRectShadowDrawable(enableColor, getShadowColor(enableColor),
                dip2Px(view.getContext(), 10),
                dip2Px(view.getContext(), 5), dip2Px(view.getContext(), 5));

        RoundRectShadowDrawable pressDrawable = new RoundRectShadowDrawable(pressColor, getShadowColor(pressColor),
                dip2Px(view.getContext(), 10),
                dip2Px(view.getContext(), 5), dip2Px(view.getContext(), 5));

        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
        bg.addState(new int[]{android.R.attr.state_enabled}, enableDrawable);

        view.setBackgroundDrawable(bg);
    }


    public static void setShadowSelectorBg(View view, int bgColor) {
        RoundRectShadowDrawable drawable = new RoundRectShadowDrawable(bgColor, getShadowColor(bgColor), dip2Px(view.getContext(), 10),
                dip2Px(view.getContext(), 5), dip2Px(view.getContext(), 5));
        StateListDrawable bgDrawable = new StateListDrawable();
        bgDrawable.addState(new int[]{}, drawable);
        view.setBackgroundDrawable(bgDrawable);
    }

    public static void setShadowBg(View view, int bgColor, int shadowColor, int shadowSize) {
        RoundRectShadowDrawable drawable = new RoundRectShadowDrawable(bgColor, getShadowColor(shadowColor),
                dip2Px(view.getContext(), shadowSize), dip2Px(view.getContext(), shadowSize), dip2Px(view.getContext(), shadowSize));
        StateListDrawable bgDrawable = new StateListDrawable();
        bgDrawable.addState(new int[]{}, drawable);
        view.setBackgroundDrawable(bgDrawable);
    }



    /**
     * 设置普通背景
     *
     * @param view
     * @param backgroundColor
     * @param shadowColor
     * @param cornerRadius
     * @param shadowSize
     * @param maxShadowSize
     */
    public static void setShadowBg(View view, int backgroundColor, int shadowColor, float cornerRadius, float shadowSize, float maxShadowSize) {
        RoundRectShadowDrawable normalDrawable = new RoundRectShadowDrawable(backgroundColor, getShadowColor(shadowColor), dip2Px(view.getContext(), cornerRadius),
                dip2Px(view.getContext(), shadowSize), dip2Px(view.getContext(), maxShadowSize));
        view.setBackgroundDrawable(normalDrawable);
    }


    /**
     * 渐变颜色数组
     *
     * @param baseShadowColor
     * @return
     */
    private static int[] getShadowColor(int baseShadowColor) {
        int[] colors = colors = new int[3];
        colors[0] = ColorUtils.setAlphaComponent(baseShadowColor, 255);
        colors[1] = ColorUtils.setAlphaComponent(baseShadowColor, 128);
        colors[2] = ColorUtils.setAlphaComponent(baseShadowColor, 0);
        return colors;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    private static int dip2Px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
