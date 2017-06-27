package com.example.jing.kapep.Activitys.MakeContentActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 2017/6/26.
 */

public class ImageListShowAdapter extends CommonAdapter {
    public interface ImageShowAdapterListener{
        void removeAction(View view, RecyclerView.ViewHolder holder, int position);// 删除按钮
        void didSelected(View view, RecyclerView.ViewHolder holder, int position);// 点击事件
    }
    private ImageShowAdapterListener listener = null;
    public ImageListShowAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        // 点击时间自己实现
        onClickListerSeting();
    }
    @BindView(R.id.listshow_iteam_image)
    ImageView imageView;
    @BindView(R.id.listshow_iteam_deleteview)
    ImageView deleteView;

    @Override
    protected void convert(final ViewHolder viewHolder, Object item,final int position) {
        String imagePath = (String)item;
        ButterKnife.bind(this,viewHolder.getConvertView());
        if (imagePath.equals(KapMakeContentActivity.ADD_STRING_PATH)){
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(R.mipmap.make_add_photo_s);
            deleteView.setVisibility(View.INVISIBLE);
            return;
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);// 不缩放图片
        KapGlideHelper.CreatedGlide().load(imagePath).into(imageView);
        deleteView.setVisibility(View.VISIBLE);
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener == null) return;
                listener.removeAction(view,viewHolder,position);
            }
        });
    }

    private void onClickListerSeting(){
        this.mOnItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (listener == null) return;
                listener.didSelected(view,holder,position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        };
    }

    public void setListener(ImageShowAdapterListener listener) {
        this.listener = listener;
    }
}
