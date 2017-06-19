package com.example.jing.kapep.Activitys.MessageActivity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jing.kapep.Helper.KapAttributedStringHelper;
import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.Helper.KapStringChangeHelper;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.Model.KapModelMessage;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapPendingButton;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 17/5/31.
 */

public class MessageAdapter extends CommonAdapter {
    /**
     * seeButtonOnClick 查看
     * acceptedButtonOnClick 同意
     * refusedButtonOnClick 拒绝
     * */
    public interface MessageButtonListener{
        void seeButtonOnClick(KapPendingButton button,KapModelMessage message);
        void agreeButtonOnClick(KapPendingButton button,KapModelMessage message);
        void refusedButtonOnClick(KapPendingButton button,KapModelMessage message);
    }
    private MessageButtonListener listener = null;

    public void setListener(MessageButtonListener listener) {
        this.listener = listener;
    }

    public MessageAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }
    @BindView(R.id.message_iteam_image)
    ImageView imageView;
    @BindView(R.id.message_content_text)
    TextView contentTextView;
    @BindView(R.id.message_time_text)
    TextView timeTextView;

    @BindView(R.id.message_button_see)
    KapPendingButton seeButton;
    @BindView(R.id.message_button_agree)
    KapPendingButton agreeButton;
    @BindView(R.id.message_button_refuse)
    KapPendingButton refuseButton;

    @BindView(R.id.message_systom_text)
    TextView systomTextView;

    KapModelMessage  message = null;
    @Override
    protected void convert(final ViewHolder viewHolder, Object item, int position) {
        final KapModelMessage currentMessage = (KapModelMessage)item;
        final MessageAdapter adapter = this;
        ButterKnife.bind(adapter,viewHolder.getConvertView());
        message = (KapModelMessage)item;
        timeTextView.setText(KapStringChangeHelper.MessageTimeIntervalToTimeString(message.getTs()));
        agreeButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener == null)  return;
                ButterKnife.bind(adapter,viewHolder.getConvertView());//重新绑定一下,否则每次都是改变最后一个
                accepted();// 变成已被同意
                listener.agreeButtonOnClick(agreeButton,currentMessage);
            }
        });
        refuseButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener == null) return;
                ButterKnife.bind(adapter,viewHolder.getConvertView());//重新绑定一下,否则每次都是改变最后一个
                refused();// 变成已被拒绝
                listener.refusedButtonOnClick(refuseButton,currentMessage);
            }
        });
        seeButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener == null) return;
                listener.seeButtonOnClick(seeButton,currentMessage);
            }
        });
        // 设置
        setMsgType(message.getMsgType());
        setAckType(message.getAck());
    }
    /**
     * 控制显隐
     * 控制阅读状态（红点）
     * */
    void setMsgType(int mesType){
        // 重制ui状态
        if (isSyatemMessage(mesType)) return;
        if (isFriendRequestAccepted(mesType)) return;
        if (isFriendRequestRefused(mesType)) return;
        if (isFriendRequestReceived(mesType)) return;
        if (isFriendMadeByContact(mesType)) return;
        if (isCommentsRecrived(mesType)) return;
        if (isCommentReplied(mesType)) return;
    }
    void setAckType(int ackType){
        if (isUnread(ackType)) return;
        if (isRead(ackType)) return;
        if (isAccepted(ackType)) return;
        if (isRefused(ackType)) return;
    }
    // 控制显隐
    boolean isSyatemMessage(int mesType){
        if (mesType == KapModelMessage.MESSAGE_TYPE_SYSTEM_MESSAGE) {
            seeButton.setVisibility(View.INVISIBLE);
            agreeButton.setVisibility(View.INVISIBLE);
            refuseButton.setVisibility(View.INVISIBLE);
            contentTextView.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }
    boolean isFriendRequestAccepted(int mesType){
        if (mesType == KapModelMessage.MESSAGE_TYPE_FRIEND_REQUEST_ACCEPTED)  {
            accepted();
            return true;
        }
        return false;

    }
    void accepted(){
        showFriendRequest();
        agreeButton.setVisibility(View.INVISIBLE);
        refuseButton.setVisibility(View.INVISIBLE);
        systomTextView.setText("已加为好友");
        systomTextView.setTextColor(ContextCompat.getColor(this.mContext,R.color.Bank_Blue_2f95fb));
        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(message.getRelateUser().getPortrait_url());
        KapGlideHelper.CreatedGlide().load(imageURLString).placeholder(R.mipmap.mine_placehold).into(imageView);
        contentTextView.setText(message.getRelateUser().getName());
    }
    boolean isFriendRequestRefused(int mesType){
        if (mesType == KapModelMessage.MESSAGE_TYPE_FRIEND_REQUEST_REFUSED) {
            refused();
            return true;
        }
        return false;
    }
    void refused(){
        showFriendRequest();
        agreeButton.setVisibility(View.INVISIBLE);
        refuseButton.setVisibility(View.INVISIBLE);
        systomTextView.setText("已被拒绝");
        systomTextView.setTextColor(ContextCompat.getColor(this.mContext,R.color.BANK_GRAY_535353));
        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(message.getRelateUser().getPortrait_url());
        KapGlideHelper.CreatedGlide().load(imageURLString).placeholder(R.mipmap.mine_placehold).into(imageView);
        contentTextView.setText(message.getRelateUser().getName());
    }
    boolean isFriendRequestReceived(int mesType){//我收到了交友请求
        if (mesType == KapModelMessage.MESSAGE_TYPE_FRIEND_REQUEST_RECEIVED) {
            showFriendRequest();
            systomTextView.setVisibility(View.INVISIBLE);
            String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(message.getRelateUser().getPortrait_url());
            KapGlideHelper.CreatedGlide().load(imageURLString).placeholder(R.mipmap.mine_placehold).into(imageView);
            contentTextView.setText(message.getRelateUser().getName());
            return true;
        }
        return false;

    }
    boolean isFriendMadeByContact(int mesType){
        if (mesType == KapModelMessage.MESSAGE_TYPE_FRIEND_MADE_BY_CONTACT) {
            showFriendRequest();
            agreeButton.setVisibility(View.INVISIBLE);
            refuseButton.setVisibility(View.INVISIBLE);
            systomTextView.setText("通过通讯录添加为好友");
            systomTextView.setTextColor(ContextCompat.getColor(this.mContext,R.color.Bank_Blue_2f95fb));
            String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(message.getRelateUser().getPortrait_url());
            KapGlideHelper.CreatedGlide().load(imageURLString).placeholder(R.mipmap.mine_placehold).into(imageView);
            contentTextView.setText(message.getRelateUser().getName());
            return true;
        }
        return false;

    }
    boolean isCommentsRecrived(int mesType){
        if (mesType == KapModelMessage.MESSAGE_TYPE_COMMENTS_RECEIVED) {// 评论你
            showComment();
            String name = message.getRelateComment().getAuthor().getName();
            String content = message.getRelateComment().getMessage();
            String[] strArray = {name};

            String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(message.getRelateComment().getAuthor().getPortrait_url());
            KapGlideHelper.CreatedGlide().load(imageURLString).placeholder(R.mipmap.mine_placehold).into(imageView);

            String allString = String.format("%s评论你:%s",name ,content);
            SpannableStringBuilder spannableString =  KapAttributedStringHelper.AttributedStringByConfi(allString,
                    R.color.BANK_GRAY_b7b7b7,
                    R.color.Bank_Blue_2f95fb, strArray);
            contentTextView.setText(spannableString);
            return true;
        }
        return false;
    }
    boolean isCommentReplied(int mesType){
        if (mesType == KapModelMessage.MESSAGE_TYPE_COMMENTS_REPLIED) {// 回复你
            showComment();
            String name = message.getRelateComment().getAuthor().getName();
            String content = message.getRelateComment().getMessage();
            String[] strArray = {name};
            String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(message.getRelateComment().getAuthor().getPortrait_url());
            KapGlideHelper.CreatedGlide().load(imageURLString).placeholder(R.mipmap.mine_placehold).into(imageView);

            String allString = String.format("%s回复你:%s",name ,content);
            SpannableStringBuilder spannableString =  KapAttributedStringHelper.AttributedStringByConfi(allString,
                    R.color.BANK_GRAY_b7b7b7,
                    R.color.Bank_Blue_2f95fb, strArray);
            contentTextView.setText(spannableString);
            return true;
        }
        return false;
    }
    void showFriendRequest(){
        seeButton.setVisibility(View.INVISIBLE);
        systomTextView.setVisibility(View.VISIBLE);
        agreeButton.setVisibility(View.VISIBLE);
        refuseButton.setVisibility(View.VISIBLE);
    }
    void showComment(){
        seeButton.setVisibility(View.VISIBLE);
        systomTextView.setVisibility(View.INVISIBLE);
        agreeButton.setVisibility(View.INVISIBLE);
        refuseButton.setVisibility(View.INVISIBLE);
    }
    // 控制阅读状态
    boolean isUnread(int ackType){
        if (ackType == KapModelMessage.ACK_TYPE_UNREAD) {
            return true;
        }
        return false;
    }
    boolean isRead(int ackType){
        if (ackType == KapModelMessage.ACK_TYPE_READ) {
            return true;
        }
        return false;
    }
    boolean isAccepted(int ackType){
        if (ackType == KapModelMessage.ACK_TYPE_ACCEPTED)  {
            accepted();
            return true;
        }
        return false;
    }
    boolean isRefused(int ackType){
        if (ackType == KapModelMessage.ACK_TYPE_REFUSED)  {
            refused();
            return true;
        }
        return false;
    }
}
