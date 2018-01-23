package com.auts.lcssv.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseViewHolder;
import com.auts.lcssv.R;
import com.auts.lcssv.bean.ProductTypeBean;
import com.auts.lcssv.constants.AppConstans;
import com.auts.lcssv.manager.imageloader.GlideCircleTransform;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.views.recyclerview.BasePullUpDownAdapter;

import java.util.List;

/**
 * 添加设备时选择设备类型的列表
 *
 * @author xiaolei.yang
 * @date 2017/7/18
 */

public class ProductsTypeAdapter extends BasePullUpDownAdapter<ProductTypeBean, BaseViewHolder> {

    public ProductsTypeAdapter(@Nullable List<ProductTypeBean> data) {
        super(R.layout.activity_add_devices_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductTypeBean item) {
        if (item != null) {
            helper.setText(R.id.tv_product_type, item.getName() == null ? "" : item.getName());
            switch (item.getDevice_type()) {
                case AppConstans.Products.TYPE_INSERTS:
                    ImageLoader.getLoader(mContext)
                            .load(item.getDefault_device_icon() == null ? "" : item.getDefault_device_icon())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .placeholder(R.drawable.device_icon_platooninsert1)
//                            .transform(new_icon GlideCircleTransform(mContext))    //图片本身有做圆化处理，代码中无需再次圆化。
                            .into((ImageView) helper.getView(R.id.iv_product_type));
                    break;
                default:
                    break;
            }
        }

    }
}
