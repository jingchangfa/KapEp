package com.example.jing.kapep.Activitys.MineAccountActivity.MineCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.ListenerActivity.KapListenerActivity;
import com.example.jing.kapep.Activitys.MessageActivity.KapMessageActivity;
import com.example.jing.kapep.Activitys.MineAccountActivity.AccountDetail.KapAccountDetailActivity;
import com.example.jing.kapep.Activitys.MineAccountActivity.MineSet.KapMineSetActivity;
import com.example.jing.kapep.Helper.MainThreadHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapUserAPIClient;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelUserDetail;
import com.example.jing.kapep.R;

import butterknife.BindView;

/**
 * Created by jing on 17/5/10.
 */

public class KapMineCenterActivity extends ActivityBase {
    @BindView(R.id.minecenter_imageview)ImageView imageView;
    @BindView(R.id.minecenter_listener)Button listenerButton;
    @BindView(R.id.minecenter_message)Button messageButton;
    @BindView(R.id.minecenter_mine)Button mineButton;
    @BindView(R.id.minecenter_seting)Button setingButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mineaccount_minecenter;
    }
    @Override
    protected String navShowTitle() {
        return "个人中心";
    }
    @Override
    protected void setController() {
        this.rightButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void getView() {
        listenerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KapMineCenterActivity.this, KapListenerActivity.class));
            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KapMineCenterActivity.this, KapMessageActivity.class));
            }
        });
        mineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KapMineCenterActivity.this, KapAccountDetailActivity.class));
            }
        });
        setingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KapMineCenterActivity.this, KapMineSetActivity.class));
            }
        });
    }

    @Override
    protected void getModel() {
        super.getModel();
        new KapUserAPIClient().mineDetail(new KapUserAPIClient.KapUserModelUserDetailInterface() {
            @Override
            public void successResult(KapModelUserDetail model) {
                mineSetingBy(model);
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
            }
        });
    }
    void mineSetingBy(KapModelUserDetail model){
        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(model.getPortrait_url());
        Glide.with(KapMineCenterActivity.this).load(imageURLString).into(imageView);//.thumbnail(0.1f)
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
