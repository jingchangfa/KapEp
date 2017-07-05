package com.example.jing.kapep.Activitys.OtherDetailActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;
import com.example.jing.kapep.R;

/**
 * Created by jing on 17/5/10.
 */

public class KapOtherDetailActivity extends ActivityBase {
    public static final String  ExtraModelKey = "KapModelPeople";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_otherdetail;
    }
    @Override
    protected String navShowTitle() {
        return "他人详情页";
    }
    @Override
    protected void setController() {
        this.rightButton.setBackgroundResource(R.mipmap.nav_more);
        this.titleImage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void getView() {

    }

    @Override
    protected void getModel() {
        super.getModel();
        // nav 设置
        KapModelPeople modelPeople = (KapModelPeople)getIntent().getSerializableExtra(ExtraModelKey);
        this.titleTextView.setText(modelPeople.getName());
        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(modelPeople.getPortrait_url());
        KapGlideHelper.CreatedGlide()
                .load(imageURLString)
                .placeholder(R.mipmap.mine_placehold)
                .into(this.titleImage);
        // 获取帖子列表
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
