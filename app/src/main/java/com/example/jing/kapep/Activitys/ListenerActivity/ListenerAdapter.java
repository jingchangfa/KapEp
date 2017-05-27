package com.example.jing.kapep.Activitys.ListenerActivity;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople;
import com.example.jing.kapep.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 17/5/26.
 */

public class ListenerAdapter extends CommonAdapter {
    public interface AddButtonListener{
        void onClick(Button button,int userID);
    }
    public ListenerAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }
    private AddButtonListener listener = null;

    public void setListener(AddButtonListener listener) {
        this.listener = listener;
    }

    @BindView(R.id.listener_iteam_image)
    ImageView imageView;
    @BindView(R.id.listener_iteam_text)
    TextView textView;
    @BindView(R.id.listener_iteam_button)
    Button button;
    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        final KapModelPeople modelPeople = (KapModelPeople) item;
        ButterKnife.bind(this,viewHolder.getConvertView());
        textView.setText(modelPeople.getName());
        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(modelPeople.getPortrait_url());
        Glide.with(this.mContext).load(imageURLString).placeholder(R.mipmap.mine_placehold).into(imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAction(button,modelPeople.getID());
            }
        });
    }
    void buttonAction(Button button,int userID){
        // 状态变化
        // 回调
        if (listener != null) listener.onClick(button,userID);
    }
}
