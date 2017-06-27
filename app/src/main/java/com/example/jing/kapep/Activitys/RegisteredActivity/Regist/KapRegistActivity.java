package com.example.jing.kapep.Activitys.RegisteredActivity.Regist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.LoginActivity.LoginEditTextView;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapAlertBankShowView;
import com.example.jing.kapep.View.KapTimeChangeButton;

import butterknife.BindView;

/**
 * Created by jing on 17/5/10.
 */

public class KapRegistActivity extends ActivityBase {
    @BindView(R.id.regist_next)
    KapAlertBankShowView nextView;

    @BindView(R.id.regist_placeview)
    LoginEditTextView placevVew;

    @BindView(R.id.regist_numberview)
    LoginEditTextView numberView;

    @BindView(R.id.regist_invitecodeview)
    LoginEditTextView inviteCodeView;

    @BindView(R.id.regist_timebutton)
    KapTimeChangeButton timeChangeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register_regist;
    }
    @Override
    protected String navShowTitle() {
        return "注册账号";
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
