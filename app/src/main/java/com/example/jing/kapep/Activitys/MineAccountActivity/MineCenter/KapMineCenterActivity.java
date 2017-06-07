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
import com.example.jing.kapep.View.KapImageRedDotsView;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jing on 17/5/10.
 */

public class KapMineCenterActivity extends ActivityBase {
    @BindView(R.id.minecenter_imageview)CircleImageView imageView;
    @BindView(R.id.minecenter_listener)KapImageRedDotsView listenerButton;
    @BindView(R.id.minecenter_message)KapImageRedDotsView messageButton;
    @BindView(R.id.minecenter_mine)KapImageRedDotsView mineButton;
    @BindView(R.id.minecenter_seting)KapImageRedDotsView setingButton;

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
                listenerButton.setShowRedView(false);
            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KapMineCenterActivity.this, KapMessageActivity.class));
                messageButton.setShowRedView(false);
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
        Glide.with(KapMineCenterActivity.this)
                .load(imageURLString)
//                .placeholder(R.mipmap.mine_placehold)
                .into(imageView);//.thumbnail(0.1f)
        listenerButton.setShowRedView(model.getUnreadAdiences() > 0);
        messageButton.setShowRedView(model.getUnreadMessages() > 0);
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
        // 页面销毁的时候 当前页面停止加载图片，防止崩溃
        Glide.with(this).pauseRequests();
    }
}
