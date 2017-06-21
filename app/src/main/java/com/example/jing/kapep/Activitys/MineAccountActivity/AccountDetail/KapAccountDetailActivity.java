package com.example.jing.kapep.Activitys.MineAccountActivity.AccountDetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.MineAccountActivity.MineCenter.KapMineCenterActivity;
import com.example.jing.kapep.Activitys.RegisteredActivity.PasswordSet.KapPasswordSetActivity;
import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.Helper.KapBitmapHalper;
import com.example.jing.kapep.Helper.KapGlideHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapAuthAPIClient;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapImageAPIClient;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapUserAPIClient;
import com.example.jing.kapep.Manager.KapActivityInfoTransferManager;
import com.example.jing.kapep.Manager.KapCreameManager;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelUserDetail;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapBigChangeButton;
import com.example.jing.kapep.View.KapShowAccountDetailTextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jing on 17/5/10.
 */

public class KapAccountDetailActivity extends ActivityBase {
    private HashMap mineChangeHasMap = new HashMap();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mineaccount_accountdetail;
    }
    @Override
    protected String navShowTitle() {
        return "账户信息";
    }
    @Override
    protected void setController() {
        this.rightButton.setVisibility(View.INVISIBLE);

    }
    @BindView(R.id.accountDetail_imageview)
    CircleImageView imageView;
    @BindView(R.id.accountDetail_nikename)
    KapShowAccountDetailTextView nikeNameView;

    @BindView(R.id.accountDetail_number)
    KapShowAccountDetailTextView numberView;

    @BindView(R.id.accountDetail_occ)
    KapShowAccountDetailTextView occView;

    @BindView(R.id.accountDetail_sex)
    KapShowAccountDetailTextView sexView;

    @BindView(R.id.accountDetail_place)
    KapShowAccountDetailTextView placeView;

    @BindView(R.id.accountDetail_email)
    KapShowAccountDetailTextView emailView;

    @BindView(R.id.accountDetail_changeDetail)
    KapBigChangeButton changeDetailButton;

    @BindView(R.id.accountDetail_changePassWord)
    KapBigChangeButton changePassWordButton;

    @Override
    protected void getView() {
        imageView.setImageBitmap(KapBitmapHalper.GetImageBitMap());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 直接选择图片
                choseImageAction();
            }
        });
        changeDetailButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 修改信息

            }
        });
        changePassWordButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 设置密码页面
                startActivity(new Intent(KapAccountDetailActivity.this, KapPasswordSetActivity.class));
            }
        });
    }
    @Override
    protected void getModel() {
        super.getModel();
        // 本地存储
        changeUIWithModel(KapApplication.getMineUserDetail());
        // 获取我的详细信息
        new KapUserAPIClient().mineDetail(new KapUserAPIClient.KapUserModelUserDetailInterface() {
            @Override
            public void successResult(KapModelUserDetail model) {
                // 更新本地存储
                KapApplication.setMineUserDetail(model);
                // 更新视图
                changeUIWithModel(model);
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
            }
        });
    }
    /**
     * 网络请求
     * */
    void postMineSetChange(HashMap changeDictionary){
        new KapAuthAPIClient().mineOtherString(changeDictionary, new KapAuthAPIClient.KapAuthUserDetailInterface() {
            @Override
            public void successResult(KapModelUserDetail userDetail) {
                // 回调更新上页面的图片
                Kap
                // 更新本读的用户详情
                KapApplication.setMineUserDetail(userDetail);
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {

            }
        });
    }
    /**
     * 获取数据之后更新UI
     * */
    private void changeUIWithModel(KapModelUserDetail model){
        String imageURLString = KapImageAPIClient.UserHeaderImageURLStringWithString(model.getPortrait_url());
        KapGlideHelper.CreatedGlide()
                .load(imageURLString)
                .asBitmap()
                .dontAnimate()
                .into(imageView);//.thumbnail(0.1f)
        nikeNameView.setContentText(model.getName());
        numberView.setContentText(model.getMobile());
        occView.setContentText(model.occToString());
        sexView.setContentText(model.sexToString());
        placeView.setContentText(model.getLocation());
        emailView.setContentText(model.getEmail());
    }
    void choseImageAction(){
        KapCreameManager.OpenLibByRadio(this, new KapCreameManager.RadioFinishResultInKapCreameManager() {
            @Override
            public void result(String imagePath) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bitmap);
                // 确认修改之后 上传图片，更新本地的持久化
                postMineSetChange(mineChangeHasMap);
            }
        });
    }
    /**
     * 辅助方法isChange
     * 判断有没有更改信息:通过字典存的和存的个人详情做对比
     * */
    boolean isChangeMineDetail(){

        return false;
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
//                // 出现更换页面
//                new AlertView("", null, "取消", null,
//                        new String[]{"拍照", "从相册中选择"},
//                        KapAccountDetailActivity.this,
//                        AlertView.Style.ActionSheet,
//                        new OnItemClickListener() {
//                            @Override
//                            public void onItemClick(Object o, int position) {// 跳转到相册
//
//                            }
//                        }).show();