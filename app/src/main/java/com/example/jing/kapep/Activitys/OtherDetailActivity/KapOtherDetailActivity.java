package com.example.jing.kapep.Activitys.OtherDetailActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.HomePageActivity.HomePageAdapter;
import com.example.jing.kapep.Activitys.HomePageActivity.KapHomePageActivity;
import com.example.jing.kapep.Helper.KapFieldHelper;
import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapContentAPIClient;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapFriendAPIClient;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapListenAPIClient;
import com.example.jing.kapep.Manager.KapActivityInfoTransferManager;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapNoDataCommonShowView;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by jing on 17/5/10.
 * TODO 他人详情页面
 */

public class KapOtherDetailActivity extends ActivityBase implements BGARefreshLayout.BGARefreshLayoutDelegate{
    public static final String  ExtraModelKey = "KapModelPeople";
    private KapModelPeople modelPeople = null;
    private CustomPopWindow popWindow = null;
    private KapPopWindowContentView contentView = null;
    private KapOtherAdapter adapter = null;
    @BindView(R.id.other_refresh)
    BGARefreshLayout refreshView;

    @BindView(R.id.other_recyclerView)
    RecyclerView recyclerView;
    KapNoDataCommonShowView noContentView;//无内容的view
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
    protected void setController() {
        this.rightButton.setBackgroundResource(R.mipmap.nav_more);
        this.titleImage.setVisibility(View.VISIBLE);
    }
    private int limit = 20;
    private int offset = 0;
    private List modelsArray = new ArrayList();
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
        // recycleview 和 refreshview
        refreshView.setDelegate(this);
        refreshView.setIsShowLoadingMoreView(true);
        BGANormalRefreshViewHolder bgaRefreshViewHolder = new BGANormalRefreshViewHolder(this,true);
        refreshView.setRefreshViewHolder(bgaRefreshViewHolder);
        adapter = new KapOtherAdapter(this,R.layout.list_iteam_content,modelsArray);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        noContentView = new KapNoDataCommonShowView(this,recyclerView);
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

        if (modelPeople.getRelationship() == KapModelPeople.people_relationship_friends){
            contentView.setRelationship(KapPopWindowContentView.relationship_friend);
        }else {
            contentView.setRelationship(KapPopWindowContentView.relationship_listen);
        }
        // 获取帖子列表
        postContentList(0,modelPeople.getID());
    }
    // 网络请求
    private void postContentList(final int nowOffset,int userID){
        new KapContentAPIClient().userContentList(limit, nowOffset, userID, new KapContentAPIClient.KapContentListInterface() {
            @Override
            public void successResult(List modelList, int total, int offset) {
                if (nowOffset == 0) {
                    KapOtherDetailActivity.this.offset = 0;
                    modelsArray.clear();
                }
                KapOtherDetailActivity.this.offset += limit;
                modelsArray.addAll(modelList);
                listViewEndRefreshingAndChangeData(true);
                if (modelsArray.size() == 0){
                    noContentView.showByType(KapNoDataCommonShowView.TYPE_EMPTY);
                }
                if (KapOtherDetailActivity.this.offset > total){
                    refreshView.setIsShowLoadingMoreView(false);
                }
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {

            }
        });
    }
    private void postAddFriend(int peopleID){
        new KapFriendAPIClient().addUserFriends(peopleID, new KapFriendAPIClient.KapFriendNoParticipationInterface() {
            @Override
            public void successResult() {
                // 已申请
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {

            }
        });
    }
    private void postDeleteFriend(int peopleID){
        new KapFriendAPIClient().deleteFriend(peopleID, new KapFriendAPIClient.KapFriendNoParticipationInterface() {
            @Override
            public void successResult() {
                // 已删除
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {

            }
        });
    }
    private void postDeleteListen(int peopleID){
        new KapListenAPIClient().deleteListen(peopleID, true, new KapListenAPIClient.KapListenNoParticipationInterface() {
            @Override
            public void successResult() {
                deleteAction("以取消收听");
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {

            }
        });
    }
    /**
     * 辅助方法
     * */
    private void shareAction(int peopleID){

    }
    private void deleteAction(String alertString){
        // 以取消收听
        // 回调更新上级页面
        KapActivityInfoTransferManager.PostChangeByModel(modelPeople, KapHomePageActivity.class);
    }

    void listViewEndRefreshingAndChangeData(Boolean success){
        if (success) adapter.notifyDataSetChanged();
        refreshView.endRefreshing();
        refreshView.endLoadingMore();
    }
    // button Action

    /**
     * 协议
     * */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        postContentList(0,modelPeople.getID());
    }
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!KapFieldHelper.kapGetPrivateValue(refreshLayout,"mIsShowLoadingMoreView")) return false;
        postContentList(offset,modelPeople.getID());
        return true;
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
