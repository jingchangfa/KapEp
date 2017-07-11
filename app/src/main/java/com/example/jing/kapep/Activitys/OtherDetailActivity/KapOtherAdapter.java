package com.example.jing.kapep.Activitys.OtherDetailActivity;

import android.content.Context;
import android.widget.ImageView;

import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelContentBase;
import com.example.jing.kapep.Model.KapModelContent.KapOtherContent.KapModelOtherConton;
import com.example.jing.kapep.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 2017/7/11.
 */

public class KapOtherAdapter extends CommonAdapter {
    public KapOtherAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }
    @BindView(R.id.content_image)
    ImageView imageView;

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
        KapModelOtherConton model = (KapModelOtherConton)o;
        ButterKnife.bind(this,holder.getConvertView());

        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString((String)model.getImagesArray().get(0));
        KapGlideHelper.CreatedGlide()
                .load(imageURLString)
                .placeholder(R.mipmap.lunch_backimage)
                .into(imageView);
    }
}
