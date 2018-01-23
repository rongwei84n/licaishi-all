/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.auts.lcssv.zxing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.auts.lcssv.R;
import com.auts.lcssv.zxing.camera.CameraManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

    private static final long ANIMATION_DELAY = 80L;
    private static final int CURRENT_POINT_OPACITY = 0xA0;
    private static final int MAX_RESULT_POINTS = 20;

    private CameraManager cameraManager;
    private final Paint paint;
    private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;
    private final int laserColor;
    private List<ResultPoint> possibleResultPoints;

    private int cornerLength = 40;
    //四条边线粗细
    private int cornerWidth = 6;

    //扫描线粗细
    private static final int SCAN_LINE_HEIGHT = 3;
    private static final int SCAN_LINE_PADDING = 0;
    private static final int SPEED_DISTANCE = 5;

    private float density;

    private int textSize = 17;

    private static final int TEXT_PADDING_TOP = 50;

    private int slideTop;
    private int slideBottom;
    boolean isFirst;

    String statusText;

    TextPaint textPaint;

    Paint scanPaint;

    StaticLayout layout;

    int[] gradientColor = new int[]{
            Color.argb(0x10, 0x00, 0xAD, 0x70),
            Color.argb(0xFF, 0x00, 0xAD, 0xF9),
            Color.argb(0x10, 0x00, 0xAD, 0x70)
    };


    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics display = context.getResources().getDisplayMetrics();
        density = display.density;
        cornerLength = (int) (cornerLength * density);

        statusText = getResources().getString(R.string.msg_default_status);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(getResources().getColor(R.color.zxing_text_color));
        textPaint.setTextSize(textSize * density);
        textPaint.setTypeface(Typeface.create("System", Typeface.BOLD));
        layout = new StaticLayout(statusText, textPaint, display.widthPixels, Alignment.ALIGN_CENTER, 1.5f, 0.0f, true);

        // Initialize these once for performance rather than calling them every time in onDraw().
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);

        Resources resources = getResources();
        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);
//        laserColor = resources.getColor(R.color.blue);//R.color.viewfinder_laser);
        laserColor = resources.getColor(R.color.text_oringe);//R.color.viewfinder_laser);
        possibleResultPoints = new ArrayList<>(5);

        scanPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scanPaint.setColor(laserColor);


    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
//            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        if (cameraManager == null) {
            return; // not ready yet, early draw before done configuring
        }
        Rect frame = cameraManager.getFramingRect();
        Rect previewFrame = cameraManager.getFramingRectInPreview();
        if (frame == null || previewFrame == null) {
            return;
        }
        if (!isFirst) {
            isFirst = true;
            slideTop = frame.top + cornerWidth + SCAN_LINE_PADDING;
            slideBottom = frame.bottom - cornerWidth - SCAN_LINE_PADDING;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);

        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

//        float dp3 = 3 * density;
//        canvas.drawRect(dp3, dp3, width - dp3, frame.top + dp3, paint);
//        canvas.drawRect(dp3, frame.top + dp3, frame.left + dp3, frame.bottom - dp3, paint);
//        canvas.drawRect(frame.right - dp3, frame.top + dp3, width - dp3, frame.bottom - dp3, paint);
//        canvas.drawRect(0, frame.bottom - dp3, width - dp3, height, paint);


        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
            //Lg.d("camerView", "bitmap");
        } else {
            paint.setColor(textPaint.getColor());//Color.argb(0xFF, 0xCC, 0xCC, 0xCC));
            float dp1 = 0 * density;
            canvas.drawRect(frame.left, frame.top, frame.right, frame.top + dp1, paint);
            canvas.drawRect(frame.left, frame.top, frame.left + dp1, frame.bottom, paint);
            canvas.drawRect(frame.left, frame.bottom - dp1, frame.right, frame.bottom, paint);
            canvas.drawRect(frame.right - dp1, frame.top, frame.right, frame.bottom, paint);

            paint.setColor(laserColor);
            canvas.drawRect(frame.left, frame.top, frame.left + cornerLength, frame.top + cornerWidth, paint);
            canvas.drawRect(frame.left, frame.top, frame.left + cornerWidth, frame.top + cornerLength, paint);

            canvas.drawRect(frame.right - cornerLength, frame.top, frame.right, frame.top + cornerWidth, paint);
            canvas.drawRect(frame.right - cornerWidth, frame.top, frame.right, frame.top + cornerLength, paint);

            canvas.drawRect(frame.left, frame.bottom - cornerLength, frame.left + cornerWidth, frame.bottom, paint);
            canvas.drawRect(frame.left, frame.bottom - cornerWidth, frame.left + cornerLength, frame.bottom, paint);

            canvas.drawRect(frame.right - cornerWidth, frame.bottom - cornerLength, frame.right, frame.bottom, paint);
            canvas.drawRect(frame.right - cornerLength, frame.bottom - cornerWidth, frame.right, frame.bottom, paint);

            slideTop += SPEED_DISTANCE;
            if (slideTop >= slideBottom) {
                slideTop = frame.top + cornerWidth + SCAN_LINE_PADDING;
            }
            int l = frame.left + cornerWidth + SCAN_LINE_PADDING;
            int t = slideTop - SCAN_LINE_HEIGHT / 2;
            int r = frame.right - cornerWidth - SCAN_LINE_PADDING;
            int b = slideTop + SCAN_LINE_HEIGHT / 2;
            Shader shader = new LinearGradient(frame.left, (b - t) / 2, frame.right, (b - t) / 2, gradientColor, null, Shader.TileMode.REPEAT);
//            scanPaint.setShader(shader);
            scanPaint.setMaskFilter(new BlurMaskFilter(10, Blur.SOLID));
            //scanPaint.setShadowLayer((r-l)/2, (r-l)/2, (b-t), laserColor);
            scanPaint.setColor(getContext().getResources().getColor(R.color.text_oringe));
            //画扫描线
            canvas.drawRect(l - 10, t, r + 10, b, scanPaint);

//            canvas.translate(0, frame.bottom + density * TEXT_PADDING_TOP);
            canvas.translate(0, density * TEXT_PADDING_TOP);

            if (!TextUtils.isEmpty(statusText)) {
                layout.draw(canvas);
            }

            postInvalidateDelayed(
                    ANIMATION_DELAY,
                    frame.left + cornerWidth + SCAN_LINE_PADDING,
                    frame.top + cornerWidth + SCAN_LINE_PADDING,
                    frame.right - cornerWidth - SCAN_LINE_PADDING,
                    slideBottom
            );
        }
    }

    public void drawViewfinder() {
        Bitmap resultBitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (resultBitmap != null) {
            resultBitmap.recycle();
        }
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live scanning display.
     *
     * @param barcode An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        List<ResultPoint> points = possibleResultPoints;
        synchronized (points) {
            points.add(point);
            int size = points.size();
            if (size > MAX_RESULT_POINTS) {
                // trim it
                points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
            }
        }
    }

    public void setStatusText(String text) {
        statusText = text;
        if (statusText == null) {
            statusText = "";
        }
        DisplayMetrics display = getContext().getResources().getDisplayMetrics();
        density = display.density;
        layout = new StaticLayout(
                statusText,
                textPaint,
                display.widthPixels,
                Alignment.ALIGN_CENTER,
                1.0f,
                0.0f,
                true
        );
        invalidate();
    }

    public int getTextHeight() {
        int height = 0;
        if (!TextUtils.isEmpty(statusText)) {
            height = (int) (textSize * density + TEXT_PADDING_TOP);
        }
        return height;
    }
}
