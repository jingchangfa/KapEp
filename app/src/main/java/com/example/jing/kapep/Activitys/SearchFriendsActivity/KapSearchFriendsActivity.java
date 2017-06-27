package com.example.jing.kapep.Activitys.SearchFriendsActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.ListenerActivity.ListenerAdapter;
import com.example.jing.kapep.Helper.KapFieldHelper;
import com.example.jing.kapep.Helper.KapSearchRunnableHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapFriendAPIClient;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapUserAPIClient;
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

public class KapSearchFriendsActivity extends ActivityBase implements BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.search_editText)
    EditText editText;
    @BindView(R.id.search_leftbutton)
    Button cancleButton;
    @BindView(R.id.search_refresh)
    BGARefreshLayout refreshView;
    @BindView(R.id.search_listview)
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_searchfriends;
    }
    @Override
    protected String navShowTitle() {
        return "搜索页面";
    }

    private int limit = 20;
    private int offset = 0;
    private List modelsArray = new ArrayList();
    private ListenerAdapter adapter = null;
    @Override
    protected void setController() {
    }

    @Override
    protected void getView() {
        final KapSearchRunnableHelper searchRunnableHelper = new KapSearchRunnableHelper(new Runnable() {
            @Override
            public void run() {
                postList(0);// 更新列表
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //防止铺天盖地的请求 SearchRunnable管理
                searchRunnableHelper.pushKeyWord();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        String searchString = editText.getText().toString()+"";
        if (!seachStringCanPush(searchString)) return;

        new KapUserAPIClient().userSearch(limit, nowOffset, searchString, new KapUserAPIClient.KapUserListInterface() {
            @Override
            public void successResult(List modelList, int total, int offset) {
                if (nowOffset == 0) {
                    KapSearchFriendsActivity.this.offset = 0;
                    modelsArray.clear();
                }
                KapSearchFriendsActivity.this.offset += limit;
                modelsArray.addAll(modelList);
                listViewEndRefreshingAndChangeData(true);
                if (KapSearchFriendsActivity.this.offset > total){
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
    private boolean seachStringCanPush(String searchString){
        if (searchString == null) return false;
        if (searchString.length() == 0) return false;
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
