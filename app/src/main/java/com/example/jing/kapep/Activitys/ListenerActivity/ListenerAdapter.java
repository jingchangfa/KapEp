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
import com.example.jing.kapep.View.KapAddFriendButton;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jing.kapep.Model.KapListenerAndFriend.KapModelPeople.people_relationship_friends;

/**
 * Created by jing on 17/5/26.
 */

public class ListenerAdapter extends CommonAdapter {
    public interface AddButtonListener{
        void onClick(KapAddFriendButton button,KapModelPeople modelPeople);
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
    KapAddFriendButton button;
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
                if (listener != null) listener.onClick(button,modelPeople);
            }
        });
        if (modelPeople.getAreadyApplied() == 1){
            // 以申请 更新button的状态
            button.setButtonType(KapAddFriendButton.addButton_type_isAreadyApplied);
        }
        if (modelPeople.getRelationship() == people_relationship_friends){
            // 已经是朋友了 更新button的状态
            button.setButtonType(KapAddFriendButton.addButton_type_isFriends);
        }
    }
}
