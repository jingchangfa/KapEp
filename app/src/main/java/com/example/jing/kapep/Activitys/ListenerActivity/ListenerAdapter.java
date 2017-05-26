package com.example.jing.kapep.Activitys.ListenerActivity;

import android.content.Context;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by jing on 17/5/26.
 */

public class ListenerAdapter extends CommonAdapter {

    public ListenerAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {

    }
}
