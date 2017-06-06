package com.example.jing.kapep.Activitys.OtherDetailActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;
import com.example.jing.kapep.R;

/**
 * Created by jing on 17/5/10.
 */

public class KapOtherDetailActivity extends ActivityBase {
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

    }

    @Override
    protected void getView() {

    }

    @Override
    protected void getModel() {
        super.getModel();
        KapModelPeople modelPeople = (KapModelPeople)getIntent().getSerializableExtra("KapModelPeople");
        Toast.makeText(this, modelPeople.getName(), Toast.LENGTH_SHORT).show();
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
