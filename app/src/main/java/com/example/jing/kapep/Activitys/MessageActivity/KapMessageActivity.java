package com.example.jing.kapep.Activitys.MessageActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Helper.KapFieldHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapFriendAPIClient;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapUserAPIClient;
import com.example.jing.kapep.Model.KapModelMessage;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapNoDataCommonShowView;
import com.example.jing.kapep.View.KapPendingButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by jing on 17/5/10.
 */

public class KapMessageActivity extends ActivityBase implements BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.message_refresh)
    BGARefreshLayout refreshView;
    @BindView(R.id.message_listview)
    ListView listView;
    KapNoDataCommonShowView noContentView;//无内容的view

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_message;
    }

    @Override
    protected String navShowTitle() {
        return "我的消息";
    }

    @Override
    protected void setController() {
        this.rightButton.setVisibility(View.INVISIBLE);
    }

    private int limit = 20;
    private int offset = 0;
    private List modelsArray = new ArrayList();
    private MessageAdapter adapter = null;
    @Override
    protected void getView() {
        refreshView.setDelegate(this);
        refreshView.setIsShowLoadingMoreView(true);
        BGANormalRefreshViewHolder bgaRefreshViewHolder = new BGANormalRefreshViewHolder(this,true);
        refreshView.setRefreshViewHolder(bgaRefreshViewHolder);
        adapter = new MessageAdapter(this,R.layout.list_iteam_message,modelsArray);
        adapter.setListener(new MessageAdapter.MessageButtonListener() {
            @Override
            public void seeButtonOnClick(KapPendingButton button, KapModelMessage message) {
                // 查看(跳到帖子详情页)
            }

            @Override
            public void agreeButtonOnClick(KapPendingButton button, KapModelMessage message) {
                // 同意
                postAgreeHttpWithMessage(message);
            }

            @Override
            public void refusedButtonOnClick(KapPendingButton button, KapModelMessage message) {
                // 拒绝
                postRefuseHttpWithMessage(message);
            }
        });
        listView.setAdapter(adapter);
        noContentView = new KapNoDataCommonShowView(this,listView);
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
        new KapUserAPIClient().userMessage(limit, nowOffset, new KapUserAPIClient.KapUserUnreadListInterface() {
            @Override
            public void successResult(List modelList, int total, int offset, int unread) {
                if (nowOffset == 0) {
                    KapMessageActivity.this.offset = 0;
                    modelsArray.clear();
                }
                KapMessageActivity.this.offset += limit;
                modelsArray.addAll(modelList);
                listViewEndRefreshingAndChangeData(true);
                if (modelsArray.size() == 0){
                    noContentView.showByType(KapNoDataCommonShowView.TYPE_EMPTY);
                }
                if (KapMessageActivity.this.offset > total){
                    refreshView.setIsShowLoadingMoreView(false);
                }
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
            }
        });
    }
    void postAgreeHttpWithMessage(final KapModelMessage message){
        new KapFriendAPIClient().acceptUserFriends(message.getRelateUser().getID(), new KapFriendAPIClient.KapFriendNoParticipationInterface() {
            @Override
            public void successResult() {
                message.setAck(KapModelMessage.ACK_TYPE_ACCEPTED);
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
                adapter.notifyDataSetChanged();
            }
        });
    }
    void postRefuseHttpWithMessage(final KapModelMessage message){
        new KapFriendAPIClient().refuseFriends(message.getRelateUser().getID(), new KapFriendAPIClient.KapFriendNoParticipationInterface() {
            @Override
            public void successResult() {
                message.setAck(KapModelMessage.ACK_TYPE_REFUSED);
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
                adapter.notifyDataSetChanged();
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
