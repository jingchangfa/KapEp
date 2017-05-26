package com.example.jing.kapep.Activitys.MineAccountActivity.MineSet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.MineAccountActivity.AboutUs.KapAboutUsActivity;
import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.R;
import com.example.jing.kapep.UserAccount.KapUserAccount;
import com.example.jing.kapep.View.KapBigChangeButton;

import butterknife.BindView;

/**
 * Created by jing on 17/5/10.
 */

public class KapMineSetActivity extends ActivityBase {
    @BindView(R.id.mineseting_updateContact)KapBigChangeButton updateContactButton;
    @BindView(R.id.mineseting_cleanCache)KapBigChangeButton cleanCacheButton;
    @BindView(R.id.mineseting_aboutUs)KapBigChangeButton aboutUsButton;
    @BindView(R.id.mineseting_logout)KapBigChangeButton logoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mineaccount_mineset;
    }
    @Override
    protected String navShowTitle() {
        return "设置";
    }
    @Override
    protected void setController() {
        this.rightButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void getView() {
        updateContactButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 上传通讯录
            }
        });
        cleanCacheButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 清除缓存
            }
        });
        aboutUsButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 关于我们
                startActivity(new Intent(KapMineSetActivity.this, KapAboutUsActivity.class));
            }
        });
        logoutButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KapUserAccount.saveKapUserAccount(null);// 清空用户
                KapApplication.logInActivityChangeAction();// 回到登陆页
            }
        });
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
