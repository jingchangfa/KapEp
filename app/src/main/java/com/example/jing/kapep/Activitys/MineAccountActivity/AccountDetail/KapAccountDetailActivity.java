package com.example.jing.kapep.Activitys.MineAccountActivity.AccountDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.RegisteredActivity.PasswordSet.KapPasswordSetActivity;
import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.Helper.KapBitmapHalper;
import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapUserAPIClient;
import com.example.jing.kapep.Manager.KapCreameManager;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelUserDetail;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapBigChangeButton;
import com.example.jing.kapep.View.KapShowAccountDetailTextView;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @BindView(R.id.accountDetail_imageview)
    CircleImageView imageView;
    @BindView(R.id.accountDetail_nikename)
    KapShowAccountDetailTextView nikeNameView;

    @BindView(R.id.accountDetail_number)
    KapShowAccountDetailTextView numberView;

    @BindView(R.id.accountDetail_occ)
    KapShowAccountDetailTextView occView;

    @BindView(R.id.accountDetail_sex)
    KapShowAccountDetailTextView sexView;

    @BindView(R.id.accountDetail_place)
    KapShowAccountDetailTextView placeView;

    @BindView(R.id.accountDetail_email)
    KapShowAccountDetailTextView emailView;

    @BindView(R.id.accountDetail_changeDetail)
    KapBigChangeButton changeDetailButton;

    @BindView(R.id.accountDetail_changePassWord)
    KapBigChangeButton changePassWordButton;

    @Override
    protected void getView() {
        imageView.setImageBitmap(KapBitmapHalper.GetImageBitMap());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 出现更换页面
                new AlertView("", null, "取消", null,
                        new String[]{"拍照", "从相册中选择"},
                        KapAccountDetailActivity.this,
                        AlertView.Style.ActionSheet,
                        new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {// 跳转到相册
                                if(position == 0){// 拍照
                                    KapCreameManager.OpenCreame(KapAccountDetailActivity.this);
                                    return;
                                }
                                if (position == 1){// 相册
                                    KapCreameManager.OpenPhotoLib(KapAccountDetailActivity.this);
                                    return;
                                }
                                if (position == -1){// 取消
                                }
                            }
                        }).show();
            }
        });
        changeDetailButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        changePassWordButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 设置密码页面
                startActivity(new Intent(KapAccountDetailActivity.this, KapPasswordSetActivity.class));
            }
        });
    }

    @Override
    protected void getModel() {
        super.getModel();
        // 本地存储
        changeUIWithModel(KapApplication.getMineUserDetail());
        // 获取我的详细信息
        new KapUserAPIClient().mineDetail(new KapUserAPIClient.KapUserModelUserDetailInterface() {
            @Override
            public void successResult(KapModelUserDetail model) {
                // 更新本地存储
                KapApplication.setMineUserDetail(model);
                // 更新视图
                changeUIWithModel(model);
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
            }
        });
    }
    /**
     * 获取数据之后更新UI
     * */
    private void changeUIWithModel(KapModelUserDetail model){
        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(model.getPortrait_url());
        KapGlideHelper.CreatedGlide()
                .load(imageURLString)
                .asBitmap()
                .dontAnimate()
                .into(imageView);//.thumbnail(0.1f)
        nikeNameView.setContentText(model.getName());
        numberView.setContentText(model.getMobile());
        occView.setContentText(model.occToString());
        sexView.setContentText(model.sexToString());
        placeView.setContentText(model.getLocation());
        emailView.setContentText(model.getEmail());
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
