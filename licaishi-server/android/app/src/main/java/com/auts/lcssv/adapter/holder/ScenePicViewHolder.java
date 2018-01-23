package com.auts.lcssv.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.auts.lcssv.R;
import com.auts.lcssv.activity.ScenePicActivity;
import com.auts.lcssv.adapter.CommonAdapter;
import com.auts.lcssv.bean.Picture;
import com.auts.lcssv.bean.SceneBean;
import com.auts.lcssv.bean.ScenePic;
import com.auts.lcssv.manager.imageloader.ImageLoader;
import com.auts.lcssv.util.CommonUtils;

import java.util.List;

/**
 * 场景图片viewholder
 *
 * @author weiming.zeng
 * @date 2017/9/7
 */

public class ScenePicViewHolder extends CommonViewHolder {
    private ImageView img;
    private String choseGroupId;
    private int chose;

    public ScenePicViewHolder(Context context, View view) {
        super(context, view);
        img = (ImageView) view.findViewById(R.id.iv_pic);
        SceneBean sceneBean = ((ScenePicActivity) context).getSceneBean();
        if (sceneBean != null) {
            choseGroupId = sceneBean.getScence_pic_group().getPic_group_id();
        }
    }

    @Override
    public void bindView(final RecyclerView.Adapter adapter, final Object o) {
        List<Picture> pics = ((ScenePic) o).getGroup_pics();
        String groupId = ((ScenePic) o).getPic_group_id();
        String normalUrl = null;

        for (Picture pic : pics) {
            if (pic.getPic_type().equals("selected")) {
                img.setTag(R.id.tag_select, pic.getPic_url());
            } else if (pic.getPic_type().equals("unselected")) {
                img.setTag(R.id.tag_unselect, pic.getPic_url());
                normalUrl = pic.getPic_url();
            } else if (pic.getPic_type().equals("normal")) {
                img.setTag(R.id.tag_normal, pic.getPic_url());
                ImageLoader.preLoad(context, pic.getPic_url());  //提前加载图片到缓存，当图片选择后可不受网络状况影响
            }
        }
        if (groupId.equals(choseGroupId)) {
            normalUrl = CommonUtils.getPicUrlFromList(pics, "selected");
            chose = getAdapterPosition();
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String choseUrl = (String) img.getTag(R.id.tag_normal);
                intent.putExtra("url", choseUrl);
                View view = ((CommonAdapter) adapter).getItemViewByPostion(chose);
                if (view != null) {
                    ImageView choseImg = (ImageView) view.findViewById(R.id.iv_pic);
                    ImageLoader.getLoader(context).load(choseImg.getTag(R.id.tag_unselect)).into(choseImg);
                }
                intent.putExtra("groupId", ((ScenePic) o).getPic_group_id());
                ImageLoader.getLoader(context).load(img.getTag(R.id.tag_select)).into(img);

                ((Activity) context).setResult(ScenePicActivity.REQUESTCODE, intent);
                ((Activity) context).finish();
            }
        });
        ImageLoader.getLoader(context).load(normalUrl).into(img);
        ImageLoader.getLoader(context).load(img.getTag(R.id.tag_select));
    }
}
