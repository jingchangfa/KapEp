package com.example.jing.kapep.Activitys.HomePageActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.MakeContentActivity.KapMakeContentActivity;
import com.example.jing.kapep.Activitys.MineAccountActivity.MineCenter.KapMineCenterActivity;
import com.example.jing.kapep.Activitys.OtherDetailActivity.KapOtherDetailActivity;
import com.example.jing.kapep.Activitys.SearchFriendsActivity.KapSearchFriendsActivity;
import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.Helper.KapFieldHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapContentAPIClient;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;
import com.example.jing.kapep.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by jing on 17/5/10.
 */

public class KapHomePageActivity extends ActivityBase implements BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.home_write_button)
    Button writeButton;

    @BindView(R.id.home_refresh)
    BGARefreshLayout refreshView;

    @BindView(R.id.home_recyclerView)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_homepage;
    }
    @Override
    protected String navShowTitle() {
        return "KapEp";
    }
    @Override
    protected void setController() {
        this.rightButton.setBackgroundResource(R.mipmap.nav_seting);
        this.leftButton.setBackgroundResource(R.mipmap.nav_addfriend);
    }

    @Override
    protected void leftButtonAction() {
        startActivity(new Intent(this, KapSearchFriendsActivity.class));
    }

    @Override
    protected void rightButtonAction() {
        startActivity(new Intent(this, KapMineCenterActivity.class));
    }

    private int limit = 20;
    private int offset = 0;
    private List modelsArray = new ArrayList();
    private HomePageAdapter adapter = null;
    @Override
    protected void getView() {
        refreshView.setDelegate(this);
        refreshView.setIsShowLoadingMoreView(true);
        BGANormalRefreshViewHolder bgaRefreshViewHolder = new BGANormalRefreshViewHolder(this,true);
        refreshView.setRefreshViewHolder(bgaRefreshViewHolder);

        adapter = new HomePageAdapter(this,R.layout.list_iteam_homepage,modelsArray);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                KapModelPeople modelPeople = (KapModelPeople)modelsArray.get(position);
                // 跳到详情页
                if (modelPeople.getID() == KapApplication.getUserAccount().ID){
                    // 个人设置页
                    startActivity(new Intent(KapHomePageActivity.this, KapMineCenterActivity.class));
                    return;
                }
               // 他人详情页
                Intent intent = new Intent(KapHomePageActivity.this, KapOtherDetailActivity.class);
                intent.putExtra("KapModelPeople",modelPeople);
                startActivity(intent);
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                // 长按 暂无
                return false;
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KapHomePageActivity.this, KapMakeContentActivity.class));
            }
        });
    }

    @Override
    protected void getModel() {
        super.getModel();
        postContentList(0);
    }
    private void postContentList(final int nowOffset){
        new KapContentAPIClient().homePagePeopleList(this.limit, nowOffset, new KapContentAPIClient.KapContentListInterface() {
            @Override
            public void successResult(List modelList, int total, int offset) {
                if (nowOffset == 0) {
                    KapHomePageActivity.this.offset = 0;
                    modelsArray.clear();
                }
                KapHomePageActivity.this.offset += limit;
                modelsArray.addAll(modelList);
                listViewEndRefreshingAndChangeData(true);
                if (KapHomePageActivity.this.offset > total){
                    refreshView.setIsShowLoadingMoreView(false);
                }
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
            }
        });
    }
    /**
     * 协议
     * */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        postContentList(0);
    }
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (!KapFieldHelper.kapGetPrivateValue(refreshLayout,"mIsShowLoadingMoreView")) return false;
        postContentList(offset);
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
