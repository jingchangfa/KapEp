package com.example.jing.kapep.Activitys.MakeContentActivity;

import android.content.Context;
import android.widget.ImageView;

import com.example.jing.kapep.Helper.KapGetSizeHelper;
import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.Helper.KapViewFrameSetingHelper;
import com.example.jing.kapep.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 2017/6/26.
 */

public class ImageShowAdapter  extends CommonAdapter {
    public ImageShowAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }
    @BindView(R.id.imageshow_iteam_image)
    ImageView imageView;
    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        String imagePath = (String)item;
        ButterKnife.bind(this,viewHolder.getConvertView());
        if (imagePath.equals(KapMakeContentActivity.ADD_STRING_PATH)){
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);// 缩放在中心
            imageView.setImageResource(R.mipmap.make_add_photo);
            return;
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);// 保持纵横比缩放图片
        KapGlideHelper.CreatedGlide().load(imagePath).into(imageView);
    }
}
