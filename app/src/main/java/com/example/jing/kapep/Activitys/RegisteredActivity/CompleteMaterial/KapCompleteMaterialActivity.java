package com.example.jing.kapep.Activitys.RegisteredActivity.CompleteMaterial;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.LoginActivity.LoginEditTextView;
import com.example.jing.kapep.Activitys.MineAccountActivity.MineCenter.KapMineCenterActivity;
import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.Helper.MainThreadHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpFile;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapAuthAPIClient;
import com.example.jing.kapep.Manager.KapActivityInfoTransferManager;
import com.example.jing.kapep.Manager.KapCreameManager;
import com.example.jing.kapep.Manager.KapLocationManager;
import com.example.jing.kapep.Manager.KapPermissionUtils;
import com.example.jing.kapep.Model.KapListenerAndFriend.KapModelUserDetail;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapBigChangeButton;
import com.example.jing.kapep.View.KapCompleteShowButton;
import com.example.jing.kapep.View.ViewSexButton;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jing on 17/5/10.
 */

public class KapCompleteMaterialActivity extends ActivityBase implements ActivityCompat.OnRequestPermissionsResultCallback{
    private HashMap mineChangeHasMap = new HashMap();

    @BindView(R.id.complete_imageview)
    CircleImageView imageView;

    @BindView(R.id.complete_sex_m)
    ViewSexButton mSexButton;// man
    @BindView(R.id.complete_sex_w)
    ViewSexButton wSexButton;// woman

    @BindView(R.id.complete_number)
    LoginEditTextView numberView;

    @BindView(R.id.complete_place)
    KapCompleteShowButton placeView;
    @BindView(R.id.complete_occ)
    KapCompleteShowButton occView;

    @BindView(R.id.complete_finish)
    KapBigChangeButton finishButton;

    @BindView(R.id.complete_terms)
    TextView termsTextView;
    private KapPermissionUtils.PermissionGrant mPermissionGrant = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register_completematerial;
    }

    @Override
    protected String navShowTitle() {
        return "完善资料";
    }

    @Override
    protected void setController() {
        this.rightButton.setVisibility(View.INVISIBLE);
        mPermissionGrant = new KapPermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(int requestCode) {
                if (requestCode != KapPermissionUtils.CODE_ACCESS_FINE_LOCATION) return;
                KapLocationManager.UsersLocation(KapCompleteMaterialActivity.this, new KapLocationManager.KapLocationListener() {// 获取位置
                    @Override
                    public void successResult(String country, String locationString) {
                        MainThreadHelper.logCurrentThread();
                        placeView.getTextView().setText(locationString);
                    }

                    @Override
                    public void failureResult(String errorMsg) {
                        MainThreadHelper.logCurrentThread();
                    }
                });
            }
        };
        // 动态申请权限
        KapPermissionUtils.requestPermission(this, KapPermissionUtils.CODE_ACCESS_FINE_LOCATION, mPermissionGrant);
    }

    @Override
    protected void getView() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 直接选择图片
                choseImageAction();
            }
        });
        mSexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSexButton.setSelected(true);
                wSexButton.setSelected(false);
            }
        });
        mSexButton.setSelected(true);
        wSexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wSexButton.setSelected(true);
                mSexButton.setSelected(false);
            }
        });
        placeView.getTextView().setText("中国北京");
        occView.getTextView().setText("户外运动");

        finishButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 完善资料
                mineChangeHasMap.put("name",numberView.getEditText().getText());
                mineChangeHasMap.put("sex", KapModelUserDetail.StringToSex(mSexButton.isSelected()?"男":"女"));
                mineChangeHasMap.put("country",placeView.getTextView().getText());
                mineChangeHasMap.put("location",placeView.getTextView().getText());
//                mineChangeHasMap.put("location",);
                String imagePath = null;
                if (imageView.getTag() != null){
                    imagePath = imageView.getTag().getClass().equals(String.class)?(String)imageView.getTag():null;
                }
                postMineSetChange(mineChangeHasMap,imagePath);//((BitmapDrawable)imageView.getDrawable()).getBitmap()
            }
        });
    }

    @Override
    protected void getModel() {
        super.getModel();
    }

    /**
     * 网络请求
     * */
    private void postMineSetChange(HashMap changeDictionary, final String filePath){
        HttpFile file = null;
        if (filePath != null){
            file = new HttpFile("content","content","image/png",new File(filePath));
        }
        new KapAuthAPIClient().mineOtherString(changeDictionary, file, null, new KapAuthAPIClient.KapAuthUserDetailInterface() {
            @Override
            public void successResult(KapModelUserDetail userDetail) {
                // 更新本读的用户详情
                KapApplication.setMineUserDetail(userDetail);
                imageView.setTag(null);// 清空tag
                KapApplication.homeActivityChangeAction();//跳到主页
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
            }
        });
    }
    private void choseImageAction(){
        KapCreameManager.OpenLibByRadio(this, new KapCreameManager.RadioFinishResultInKapCreameManager() {
            @Override
            public void result(String imagePath) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bitmap);
                imageView.setTag(imagePath);// 把图片路径存在tag里
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        KapPermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);

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
