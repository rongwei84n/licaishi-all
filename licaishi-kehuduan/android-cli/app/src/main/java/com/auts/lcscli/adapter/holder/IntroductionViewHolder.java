package com.auts.lcscli.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.auts.lcscli.PhApplication;
import com.auts.lcscli.R;
import com.auts.lcscli.bean.Introduction;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author weiming.zeng
 * @date 2017/8/21
 */

public class IntroductionViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView pulishTime;
    private View itemView;
    private ContentCallBack callBack;

    public IntroductionViewHolder(View itemView, final ContentCallBack callBack ) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.tv_title);
        pulishTime = (TextView) itemView.findViewById(R.id.tv_pulish_time);
        this.itemView = itemView;
        this.callBack = callBack;
    }

    public void bindView(final Introduction introduction) {
        title.setText(introduction.getIntroduction_title());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String time = sdf.format(new Date(introduction.getIntroduction_publish_time() * 1000));
        //设置字体样式
        SpannableString spannableString = new SpannableString(time);
        int end = (time).length();
        spannableString.setSpan(new AbsoluteSizeSpan(12, true), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(PhApplication.getPhApplication().getResources().getColor(R.color.focused_line));
        spannableString.setSpan(foregroundColorSpan, 0, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        pulishTime.setText(spannableString);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.getContent(introduction);
            }
        });
    }

    public interface ContentCallBack {
        void getContent(Introduction introduction);
    }
}
