package com.example.jing.kapep.Activitys.MakeContentActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Helper.KapGetSizeHelper;
import com.example.jing.kapep.Helper.KapViewFrameSetingHelper;
import com.example.jing.kapep.R;

import butterknife.BindView;

/**
 * Created by jing on 17/5/10.
 */

public class KapMakeContentActivity extends ActivityBase {
    @BindView(R.id.make_nav_tool)
    LinearLayout navToolBar;

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
        // 调整状态栏的偏移量
        KapViewFrameSetingHelper.setLayoutY(navToolBar, KapGetSizeHelper.GetStatusBarHeight(this));
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
