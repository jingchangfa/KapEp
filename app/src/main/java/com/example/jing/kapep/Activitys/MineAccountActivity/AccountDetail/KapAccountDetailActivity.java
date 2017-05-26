package com.example.jing.kapep.Activitys.MineAccountActivity.AccountDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.R;

/**
 * Created by jing on 17/5/10.
 */

public class KapAccountDetailActivity extends ActivityBase {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mineaccount_accountdetail;
    }
    @Override
    protected String navShowTitle() {
        return "账户信息";
    }
    @Override
    protected void setController() {
        this.rightButton.setVisibility(View.INVISIBLE);

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
