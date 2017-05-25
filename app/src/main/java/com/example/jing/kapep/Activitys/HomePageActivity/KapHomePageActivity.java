package com.example.jing.kapep.Activitys.HomePageActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.MineAccountActivity.MineCenter.KapMineCenterActivity;
import com.example.jing.kapep.Activitys.SearchFriendsActivity.KapSearchFriendsActivity;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapContentAPIClient;
import com.example.jing.kapep.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jing on 17/5/10.
 */

public class KapHomePageActivity extends ActivityBase {
    private int limit = 20;
    private int offset = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_homepage;
    }
    @Override
    protected String navShowTitle() {
        return "KapEp";
    }
    @Override
    protected void setController() {
        this.rightButton.setBackgroundResource(R.mipmap.nav_seting);
        this.leftButton.setBackgroundResource(R.mipmap.nav_addfriend);
//        RecyclerView
    }

    @Override
    protected void leftButtonAction() {
        startActivity(new Intent(this, KapSearchFriendsActivity.class));
    }

    @Override
    protected void rightButtonAction() {
        startActivity(new Intent(this, KapMineCenterActivity.class));
    }

    @Override
    protected void getView() {

    }

    @Override
    protected void getModel() {
        super.getModel();
        postContentList(0);
    }
    private void postContentList(int offset){
        new KapContentAPIClient().homePagePeopleList(this.limit, offset, new KapContentAPIClient.KapContentListInterface() {
            @Override
            public void successResult(List modelList, int total, int offset) {
                modelList.size();
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {

            }
        });
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
