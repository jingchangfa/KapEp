package com.example.jing.kapep.Activitys.MakeContentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.MakeContentActivity.MakeLister.KapMakeListerActivity;
import com.example.jing.kapep.Helper.KapGetSizeHelper;
import com.example.jing.kapep.Helper.KapViewFrameSetingHelper;
import com.example.jing.kapep.Manager.KapCreameManager;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapBigChangeButton;
import com.example.jing.kapep.View.KapInfinteSlideView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * Created by jing on 17/5/10.
 */

public class KapMakeContentActivity extends ActivityBase {
    /**
     * ADD_STRING_PATH 此字符串代表add的那个图片
     * modelsArray 存储图片路径的array ---
     * */
    public static final String ADD_STRING_PATH = "add_string_path";
    private List<String> modelsArray = new ArrayList<String>(){{
        add(ADD_STRING_PATH);
    }};
    private ImageShowAdapter bigAdapter = null;
    private ImageListShowAdapter smallAdapter = null;

    @BindView(R.id.make_nav_tool)
    LinearLayout navToolBar;

    @BindView(R.id.make_big_recyclerView)
    KapInfinteSlideView bigRecycleView;

    @BindView(R.id.make_small_recyclerView)
    RecyclerView smallRecycleView;

    @BindView(R.id.make_next_button)
    KapBigChangeButton nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_makecontent;
    }
    @Override
    protected String navShowTitle() {
        return "制作内容";
    }
    @Override
    protected void setController() {
        this.rightButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void getView() {
//        // 调整状态栏的偏移量
//        KapViewFrameSetingHelper.setLayoutY(navToolBar, KapGetSizeHelper.GetStatusBarHeight(this));
        nextButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KapMakeContentActivity.this, KapMakeListerActivity.class));
            }
        });
        // 设置大的
        bigRecycleViewSeting();
        // 设置小的
        smallRecycleViewSeting();
    }

    @Override
    protected void getModel() {
        super.getModel();
    }
    /**
     * 辅助方法
     * */
    void bigRecycleViewSeting(){
        // 设置滚动方向
        bigRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // 设置adapter
        bigAdapter = new ImageShowAdapter(this,R.layout.list_iteam_imageshow,modelsArray);
        bigAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String imagePath = modelsArray.get(position);
                if (isOpenChangeImage(imagePath)) return;
                 // 裁剪
                cropImage(imagePath);
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        bigRecycleView.setAdapter(bigAdapter);
        // 设置动画监听
        bigRecycleView.setSlideViewListener(new KapInfinteSlideView.SlideViewListener() {
            @Override
            public void onChange(View v, float offset, boolean left) {
                // 缩放动画
                v.setScaleX(1 - offset * 0.1f);
                v.setScaleY(1 - offset * 0.1f);
            }
        });
    }
    // 设置小的
    void smallRecycleViewSeting(){
        // 设置滚动方向
        smallRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // 设置adapter
        smallAdapter = new ImageListShowAdapter(this,R.layout.list_iteam_imagelistshow,modelsArray);
        smallAdapter.setListener(new ImageListShowAdapter.ImageShowAdapterListener() {
            @Override
            public void removeAction(View view, RecyclerView.ViewHolder holder, int position) {
                // 删除事件
                modelsArray.remove(position);
                bigAdapter.notifyDataSetChanged();
                smallAdapter.notifyDataSetChanged();
            }
            @Override
            public void didSelected(View view, RecyclerView.ViewHolder holder, int position) {
                // 点击事件
                String imagePath = modelsArray.get(position);
                if (isOpenChangeImage(imagePath)) return;
                // 上边的变化
                bigRecycleView.smoothScrollToPosition(position);
            }
        });
        smallRecycleView.setAdapter(smallAdapter);
    }
    boolean isOpenChangeImage(String imagePath){
        if (imagePath.equals(KapMakeContentActivity.ADD_STRING_PATH)){
            // 直接选择图片
            choseImageAction();
            return true;
        }
        return false;
    }
    void choseImageAction(){// 多选
        KapCreameManager.OpenLibByMultiple(this, new KapCreameManager.MultipleFinishResultInKapCreameManage() {
            @Override
            public void result(List<MediaBean> cropBeans) {
                for (MediaBean bean:cropBeans){
                    String imagePath = bean.getThumbnailBigPath();
                    modelsArray.add(0,imagePath);
                }
                bigAdapter.notifyDataSetChanged();//更新
                smallAdapter.notifyDataSetChanged();//更新
            }
        });
    }
    void cropImage(String imagePath){ // 裁剪

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
