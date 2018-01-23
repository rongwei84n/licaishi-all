package com.auts.lcssv.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.auts.lcssv.R;
import com.auts.lcssv.bean.SwitchBean;

import java.util.List;

/**
 * 插口viewholder
 *
 * @author weiming.zeng
 * @date 2017/9/14
 */

public class SocketViewHolder extends CommonViewHolder {

    private CheckBox chose;
    private CheckBox state;
    private TextView switchName;
    private View bottomLine;

    public SocketViewHolder(Context context, View view) {
        super(context, view);
        switchName = (TextView) view.findViewById(R.id.tv_socket);
        bottomLine = view.findViewById(R.id.bottom_line);
        state = (CheckBox) view.findViewById(R.id.iv_socket_trigger);
        chose = (CheckBox) view.findViewById(R.id.iv_socket);
    }

    @Override
    public void bindView(RecyclerView.Adapter adapter, Object o) {
        final SwitchBean switchBean = (SwitchBean) o;
        switchName.setText(switchBean.getSwitchName());
        setEnable(switchBean);
        if (switchBean.getState() == 1) {
            state.setChecked(true);
        }

        if (getAdapterPosition() + 1 == adapter.getItemCount()) {
            bottomLine.getLayoutParams().width = RecyclerView.LayoutParams.MATCH_PARENT;
        }
        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chose.isChecked()) {
                    chose.setChecked(true);
                    switchBean.setSelect(true);
                }
            }
        });

        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chose.isChecked()) {
                    chose.setChecked(false);
                } else {
                    chose.setChecked(true);
                }
            }
        });
    }

    private void setEnable(SwitchBean switchBean) {
    }
}
