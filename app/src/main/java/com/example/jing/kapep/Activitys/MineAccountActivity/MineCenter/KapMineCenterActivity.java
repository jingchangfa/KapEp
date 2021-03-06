package com.example.jing.kapep.Activitys.MineAccountActivity.MineCenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.ListenerActivity.KapListenerActivity;
import com.example.jing.kapep.Activitys.MessageActivity.KapMessageActivity;
import com.example.jing.kapep.Activitys.MineAccountActivity.AccountDetail.KapAccountDetailActivity;
import com.example.jing.kapep.Activitys.MineAccountActivity.MineSet.KapMineSetActivity;
import com.example.jing.kapep.Helper.KapBitmapHalper;
import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.Helper.MainThreadHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapUserAPIClient;
import com.example.jing.kapep.Manager.KapActivityInfoTransferManager;
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
        View.OnClickListener mineListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KapBitmapHalper.SaveImageView(imageView);
                startActivity(new Intent(KapMineCenterActivity.this, KapAccountDetailActivity.class));
            }
        };
        imageView.setOnClickListener(mineListener);
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
        mineButton.setOnClickListener(mineListener);
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
        KapActivityInfoTransferManager.BindChangeModel(this, new KapActivityInfoTransferManager.InfoTransferModelInterface<Bitmap>() {
            @Override
            public void changeUIByModel(Bitmap model) {
                KapMineCenterActivity.this.imageView.setImageBitmap(model);
            }
        });
    }
    void mineSetingBy(KapModelUserDetail model){
        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(model.getPortrait_url());
        KapGlideHelper.CreatedGlide().load(imageURLString)
                .asBitmap()
                .placeholder(R.mipmap.mine_placehold)
                .dontAnimate()
                .into(imageView);//.thumbnail(0.1f)

        listenerButton.setShowRedView(model.getUnreadAdiences() > 0);
        messageButton.setShowRedView(model.getUnreadMessages() > 0);
        MainThreadHelper.logCurrentThread();
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
