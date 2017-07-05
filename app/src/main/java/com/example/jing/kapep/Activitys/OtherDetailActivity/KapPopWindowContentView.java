package com.example.jing.kapep.Activitys.OtherDetailActivity;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jing.kapep.Helper.KapViewFrameSetingHelper;
import com.example.jing.kapep.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 2017/7/5.
 * popwindow 的contenview
 */

public class KapPopWindowContentView extends LinearLayout {
    public interface PopWindowIteamListener{
        void onClick(String iteamType);
    }
    public void setIteamListener(PopWindowIteamListener iteamListener) {
        this.iteamListener = iteamListener;
    }

    /**
     * 关系-控制哪些item展示
     * */
    public static final int relationship_friend = 0;
    public static final int relationship_listen = 1;
    /**
     * item 类型
     * */
    public static final String add = "add";
    public static final String share = "share";
    public static final String listen = "listen";
    public static final String rem = "rem";

    public void setRelationship(int relationship) {
        this.relationship = relationship;
        modelData.clear();
        if (relationship == relationship_friend){
            modelData.addAll(new ArrayList<String>(){{
                add(add);
                add(share);
                add(listen);
            }});
        }
        if (relationship == relationship_listen){
            modelData.addAll(new ArrayList<String>(){{
                add(share);
                add(rem);
            }});
        }
        // 更新数据
        adapter.notifyDataSetChanged();
    }

    public KapPopWindowContentView(Context context) {
        super(context);
        initView(context);
    }

    public KapPopWindowContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        //attrs 设置
    }

    /**
     * 内部设置
     * */
    private int relationship = 0;
    private PopWindowIteamListener iteamListener = null;
    private ArrayList<String> modelData = new ArrayList<String>();
    private PopWindowAdapter adapter = null;
    @BindView(R.id.popwindow_listview)
    ListView listView;
    private void initView(Context context){
        View v = LayoutInflater.from(context).inflate(R.layout.view_popwindow_content,this);
        ButterKnife.bind(v,this);
        adapter = new PopWindowAdapter(context,R.layout.list_iteam_popwindowcontent,modelData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String iteam = modelData.get(i);
                if (iteamListener == null) return;
                iteamListener.onClick(iteam);
            }
        });
    }


    public class PopWindowAdapter extends CommonAdapter{
        public PopWindowAdapter(Context context, int layoutId, List datas) {
            super(context, layoutId, datas);
        }
        @BindView(R.id.iteam_image)
        ImageView imageView;
        @BindView(R.id.iteam_textview)
        TextView textView;
        @BindView(R.id.iteam_lineview)
        View lineView;
        @Override
        protected void convert(ViewHolder viewHolder, Object item, int position) {
            String iteamType = (String) item;
            ButterKnife.bind(this,viewHolder.getConvertView());
            if (iteamType.equals(add)){
                imageView.setImageResource(R.mipmap.iteam_add);
                textView.setText("加为好友");
            }
            if (iteamType.equals(share)){
                imageView.setImageResource(R.mipmap.iteam_share);
                textView.setText("分享");
            }
            if (iteamType.equals(listen)){
                imageView.setImageResource(R.mipmap.iteam_listen);
                textView.setText("取消收听");
            }
            if (iteamType.equals(rem)){
                imageView.setImageResource(R.mipmap.iteam_rem);
                textView.setText("删除好友");
            }
            lineView.setVisibility(View.VISIBLE);
            if (position == modelData.size()-1) lineView.setVisibility(View.INVISIBLE);
        }
    }
}
