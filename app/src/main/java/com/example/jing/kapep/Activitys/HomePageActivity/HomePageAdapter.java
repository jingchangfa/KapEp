package com.example.jing.kapep.Activitys.HomePageActivity;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;
import com.example.jing.kapep.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 2017/6/2.
 */

public class HomePageAdapter extends CommonAdapter {
    public HomePageAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }
    @BindView(R.id.home_list_image)
    ImageView imageView;
    @BindView(R.id.home_list_text)
    TextView textView;
    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
        final KapModelPeople modelPeople = (KapModelPeople)o;
        ButterKnife.bind(this,holder.getConvertView());
        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(modelPeople.getPortrait_url());
        Glide.with(this.mContext).load(imageURLString).placeholder(R.mipmap.mine_placehold).into(imageView);
        textView.setText(modelPeople.getName());
    }
}