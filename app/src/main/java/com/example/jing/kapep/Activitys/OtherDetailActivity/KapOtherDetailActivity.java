package com.example.jing.kapep.Activitys.OtherDetailActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;
import com.example.jing.kapep.R;
import com.example.zhouwei.library.CustomPopWindow;

/**
 * Created by jing on 17/5/10.
 */

public class KapOtherDetailActivity extends ActivityBase {
    public static final String  ExtraModelKey = "KapModelPeople";
    private KapModelPeople modelPeople = null;
    private CustomPopWindow popWindow = null;
    private KapPopWindowContentView contentView = null;
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
        this.rightButton.setBackgroundResource(R.mipmap.nav_more);
        this.titleImage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void getView() {
        contentView = new KapPopWindowContentView(this);
        contentView.setIteamListener(new KapPopWindowContentView.PopWindowIteamListener() {
            @Override
            public void onClick(String iteamType) {
                if (KapPopWindowContentView.add.equals(iteamType)){
                    postAddFriend(modelPeople.getID());
                    return;
                }
                if (KapPopWindowContentView.share.equals(iteamType)){
                    shareAction(modelPeople.getID());
                    return;
                }
                if (KapPopWindowContentView.listen.equals(iteamType)){
                    postDeleteListen(modelPeople.getID());
                    return;
                }
                if (KapPopWindowContentView.rem.equals(iteamType)){
                    postDeleteFriend(modelPeople.getID());
                    return;
                }
            }
        });
    }

    @Override
    protected void getModel() {
        super.getModel();
        // nav 设置
        modelPeople = (KapModelPeople)getIntent().getSerializableExtra(ExtraModelKey);
        this.titleTextView.setText(modelPeople.getName());
        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(modelPeople.getPortrait_url());
        KapGlideHelper.CreatedGlide()
                .load(imageURLString)
                .placeholder(R.mipmap.mine_placehold)
                .into(this.titleImage);
        // 获取帖子列表
        if (modelPeople.getRelationship() == KapModelPeople.people_relationship_friends){
            contentView.setRelationship(KapPopWindowContentView.relationship_friend);
        }else {
            contentView.setRelationship(KapPopWindowContentView.relationship_listen);
        }
    }
    // 辅助函数
    private void shareAction(int peopleID){

    }
    // 网络请求
    private void postAddFriend(int peopleID){

    }
    private void postDeleteFriend(int peopleID){

    }
    private void postDeleteListen(int peopleID){

    }
    // button Action
    @Override
    protected void rightButtonAction() {
        super.rightButtonAction();
        if (popWindow != null){
            popWindow.dissmiss();
            popWindow = null;
            return;
        }
        popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .setFocusable(false)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(false) //弹出popWindow时，背景是否变暗
                .create()
                .showAsDropDown(this.rightButton);
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
