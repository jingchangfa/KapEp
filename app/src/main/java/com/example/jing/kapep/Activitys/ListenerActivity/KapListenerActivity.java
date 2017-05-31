package com.example.jing.kapep.Activitys.ListenerActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.Helper.KapFieldHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapFriendAPIClient;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapListenAPIClient;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapAddFriendButton;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by jing on 17/5/10.
 */

public class KapListenerActivity extends ActivityBase implements BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.listener_refresh)
    BGARefreshLayout refreshView;
    @BindView(R.id.listener_listview)
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_listener;
    }
    @Override
    protected String navShowTitle() {
        return "我的听众";
    }
    @Override
    protected void setController() {
        this.rightButton.setVisibility(View.INVISIBLE);
    }
    private int limit = 20;
    private int offset = 0;
    private List modelsArray = new ArrayList();
    private ListenerAdapter adapter = null;
    @Override
    protected void getView() {
        refreshView.setDelegate(this);
        refreshView.setIsShowLoadingMoreView(true);
        BGANormalRefreshViewHolder bgaRefreshViewHolder = new BGANormalRefreshViewHolder(this,true);
        refreshView.setRefreshViewHolder(bgaRefreshViewHolder);
        adapter = new ListenerAdapter(this,R.layout.list_iteam_listener,modelsArray);
        adapter.setListener(new ListenerAdapter.AddButtonListener() {
            @Override
            public void onClick(KapAddFriendButton button, KapModelPeople modelPeople) {
                addFriendsByUser(button,modelPeople);// 添加好友
            }
        });
        listView.setAdapter(adapter);
    }
    @Override
    protected void getModel() {
        super.getModel();
        postList(0);
    }
    /**
     * 网络请求
     */
    private void postList(final int nowOffset){
        new KapListenAPIClient().userListenerList(limit, nowOffset, KapApplication.getUserAccount().ID, new KapListenAPIClient.KapListenListInterface() {
            @Override
            public void successResult(List modelList, int total, int offset) {
                if (nowOffset == 0) {
                    KapListenerActivity.this.offset = 0;
                    modelsArray.clear();
                }
                KapListenerActivity.this.offset += limit;
                modelsArray.addAll(modelList);
                listViewEndRefreshingAndChangeData(true);
                if (KapListenerActivity.this.offset > total){
                    refreshView.setIsShowLoadingMoreView(false);
                }
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
                listViewEndRefreshingAndChangeData(false);
            }
        });
    }
    private void addFriendsByUser(final KapAddFriendButton button,final KapModelPeople modelPeople){
        new KapFriendAPIClient().addUserFriends(modelPeople.getID(), new KapFriendAPIClient.KapFriendNoParticipationInterface() {
            @Override
            public void successResult() {
                modelPeople.setAreadyApplied(1);
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
                // 设置button 显示加号
                button.setButtonType(KapAddFriendButton.addButton_type_add);
            }
        });
    }
    /**
     * 协议
     * */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        postList(0);
    }
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!KapFieldHelper.kapGetPrivateValue(refreshLayout,"mIsShowLoadingMoreView")) return false;
        postList(offset);
        return true;
    }
    /**
     * 辅助方法
     * */
    void listViewEndRefreshingAndChangeData(Boolean success){
        if (success) adapter.notifyDataSetChanged();
        refreshView.endRefreshing();
        refreshView.endLoadingMore();
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
