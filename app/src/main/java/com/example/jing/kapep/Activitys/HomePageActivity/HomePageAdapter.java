package com.example.jing.kapep.Activitys.HomePageActivity;

import android.content.Context;
import android.widget.TextView;

import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapImageRedDotsView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by jing on 2017/6/2.
 */

public class HomePageAdapter extends CommonAdapter {
    public HomePageAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @BindView(R.id.home_list_image)
    KapImageRedDotsView imageRedDotsView;
    @BindView(R.id.home_list_text)
    TextView textView;
    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
        final KapModelPeople modelPeople = (KapModelPeople)o;
        ButterKnife.bind(this,holder.getConvertView());
        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(modelPeople.getPortrait_url());
        if (modelPeople.getRelationship() == KapModelPeople.people_relationship_audience){
            // 模糊效果
            KapGlideHelper.CreatedGlide()
                    .load(imageURLString)
                    .placeholder(R.mipmap.mine_placehold)
                    .bitmapTransform(new BlurTransformation(this.mContext, 8))// 0~100
                    .into(imageRedDotsView.getImageView());
        }else {
            // 没有模糊效果
            KapGlideHelper.CreatedGlide()
                    .load(imageURLString)
                    .placeholder(R.mipmap.mine_placehold)
                    .into(imageRedDotsView.getImageView());
        }
        imageRedDotsView.setShowRedView(modelPeople.getUnread() > 0);//
        textView.setText(modelPeople.getName());
    }
}
