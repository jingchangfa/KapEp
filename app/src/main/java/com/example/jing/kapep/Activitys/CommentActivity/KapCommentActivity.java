package com.example.jing.kapep.Activitys.CommentActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.R;

/**
 * Created by jing on 17/5/10.
 */

public class KapCommentActivity extends ActivityBase{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_comment;
    }
    @Override
    protected String navShowTitle() {
        return null;
    }
    @Override
    protected void setController() {

    }
    @Override
    protected void getView() {

    }

    @Override
    protected void getModel() {
        super.getModel();
    }
}
