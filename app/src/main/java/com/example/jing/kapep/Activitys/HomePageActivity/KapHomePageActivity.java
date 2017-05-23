package com.example.jing.kapep.Activitys.HomePageActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapContentAPIClient;
import com.example.jing.kapep.R;

/**
 * Created by jing on 17/5/10.
 */

public class KapHomePageActivity extends ActivityBase {
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
        return null;
    }
    @Override
    protected void setController() {

    }

    @Override
    protected void getView() {

    }

    @Override
    protected void getModel() {
        super.getModel();
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
