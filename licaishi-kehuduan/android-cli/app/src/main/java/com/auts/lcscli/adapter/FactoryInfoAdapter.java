package com.auts.lcscli.adapter;

import android.content.Context;
import android.support.v4.util.Pair;

import com.chad.library.adapter.base.BaseViewHolder;
import com.auts.lcscli.BuildConfig;
import com.auts.lcscli.R;
import com.auts.lcscli.util.AppInfoUtils;
import com.auts.lcscli.util.CommonUtils;
import com.auts.lcscli.views.recyclerview.BasePullUpDownAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 工厂界面的适配器
 *
 * @author xiaolei.yang
 * @date 2017/7/25
 */

public class FactoryInfoAdapter extends BasePullUpDownAdapter<Pair<String, String>, BaseViewHolder> {
    private Context mContext;

    public FactoryInfoAdapter(Context context) {
        super(R.layout.activity_factory_info_item);
        mContext = context;

        String[] keys = mContext.getResources().getStringArray(R.array.factory_info_items);
        List<Pair<String, String>> mDatas;
        mDatas = new ArrayList<>();

        for (int i = 0; i < keys.length; i++) {
            mDatas.add(i, new Pair(keys[i], getValue(keys[i])));
        }

        setNewData(mDatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, Pair<String, String> item) {
        helper.setText(R.id.item, item.first);
        helper.setText(R.id.detail, item.second);
    }

    private String getValue(String key) {
        if (key == null) {
            return "";
        } else if (key.equals(getVaById(R.string.version_name))) {
            return BuildConfig.VERSION_NAME;
        } else if (key.equals(getVaById(R.string.build_number))) {
            return BuildConfig.build_number + "";
        } else if (key.equals(getVaById(R.string.build_type))) {
            return BuildConfig.BUILD_TYPE;
        } else if (key.equals(getVaById(R.string.host_cloud_account))) {
            return BuildConfig.host_cloud_acount;
        } else if (key.equals(getVaById(R.string.host_sz))) {
            return BuildConfig.host_sz;
        } else if (key.equals(getVaById(R.string.mqtt_host))) {
            return BuildConfig.mqtt_host;
        } else if (key.equals(getVaById(R.string.mqtt_ssl_port))) {
            return BuildConfig.mqtt_ssl_port + "";
        } else if (key.equals(getVaById(R.string.target_sdk))) {
            return String.valueOf(AppInfoUtils.getTargetSdkVersion());
        } else if (key.equals(getVaById(R.string.umeng_key))) {
            String umengAppkey = String.valueOf(CommonUtils.getMetaDataByKey(mContext, "UMENG_APPKEY"));
            return getReverseStr(umengAppkey);
        } else if (key.equals(getVaById(R.string.umeng_channel))) {
            return String.valueOf(CommonUtils.getMetaDataByKey(mContext, "UMENG_CHANNEL"));
        } else if (key.equals(getVaById(R.string.buggly_id))) {
            String buglyAppid = String.valueOf(CommonUtils.getMetaDataByKey(mContext, "BUGLY_APPID"));
            return getReverseStr(buglyAppid);
        } else if (key.equals(getVaById(R.string.jpush_appkey))) {
            String jpushAppkey = String.valueOf(CommonUtils.getMetaDataByKey(mContext, "JPUSH_APPKEY"));
            return getReverseStr(jpushAppkey);
        }

        return "";
    }

    private String getVaById(int id) {
        return mContext.getString(id);
    }

    private String getReverseStr(String content) {
        return new StringBuilder(content).reverse().toString();
    }


}
