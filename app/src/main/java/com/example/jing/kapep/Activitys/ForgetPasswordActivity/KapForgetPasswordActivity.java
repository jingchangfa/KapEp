package com.example.jing.kapep.Activitys.ForgetPasswordActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.LoginActivity.LoginEditTextView;
import com.example.jing.kapep.Activitys.RegisteredActivity.PasswordSet.KapPasswordSetActivity;
import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.Helper.StringDetectionHelper;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapAuthAPIClient;
import com.example.jing.kapep.Manager.KapActivityInfoTransferManager;
import com.example.jing.kapep.R;
import com.example.jing.kapep.UserAccount.KapUserAccount;
import com.example.jing.kapep.View.KapAlertBankShowView;
import com.example.jing.kapep.View.KapTimeChangeButton;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by jing on 17/5/10.
 */

public class KapForgetPasswordActivity extends ActivityBase {
    @BindView(R.id.forget_next)
    KapAlertBankShowView nextView;

    @BindView(R.id.forget_placeview)
    LoginEditTextView placevVew;

    @BindView(R.id.forget_numberview)
    LoginEditTextView numberView;

    @BindView(R.id.forget_invitecodeview)
    LoginEditTextView inviteCodeView;

    @BindView(R.id.forget_timebutton)
    KapTimeChangeButton timeChangeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_forgetpassword;
    }
    @Override
    protected String navShowTitle() {
        return "忘记密码";
    }
    @Override
    protected void setController() {
        this.rightButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void getView() {
        nextView.getBigChangeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postChangePassword(numberView.getEditText().getText().toString(),
                        inviteCodeView.getEditText().getText().toString());
            }
        });
        timeChangeButton.bindListener(new KapTimeChangeButton.TimerButtonListener() {
            @Override
            public void start() {
                postVerificationByNumber(numberView.getEditText().getText().toString());
            }
            @Override
            public void finish() {

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
    private void postVerificationByNumber(String numberString){
        if (!StringDetectionHelper.isAuthorizedNumberString(numberString)){
            timeChangeButton.reSetTimeButton();
            Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        new KapAuthAPIClient().authLoginNoPassWord(numberString, new KapAuthAPIClient.KapAuthNoParticipationInterface() {
            @Override
            public void successResult() {
                Toast.makeText(KapForgetPasswordActivity.this, "验证码已发送，注意接收", Toast.LENGTH_SHORT).show();
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
                timeChangeButton.reSetTimeButton();
            }
        });
    }
    private void postChangePassword(String numberString,String verificationString){
        if (!StringDetectionHelper.isAuthorizedNumberString(numberString)){
            Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringDetectionHelper.isAuthorizedVerificationString(verificationString)) {
            Toast.makeText(this, "请输入正确验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        new KapAuthAPIClient().authLoginNoPassWordVerification(numberString,verificationString,new KapAuthAPIClient.KapAuthLoginInterface() {
            @Override
            public void successResult(String token, int userID, Map herd, boolean isFirstLogin) {
                KapUserAccount.saveKapUserAccount(new KapUserAccount(userID,token,herd));
                KapApplication.setUserAccount(KapUserAccount.loadActiveUserAccount());
                if (KapApplication.getUserAccount() == null) return;
                startPassWordSetingActivity();
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
                // org.json.JSONException: No value for herd ### 沛总返回的数据不全
                String s=null;
            }
        });
    }
    // 跳到设置密码
    private void startPassWordSetingActivity(){
        startActivity(new Intent(this, KapPasswordSetActivity.class));
        KapActivityInfoTransferManager.BindChangeModel(KapPasswordSetActivity.PasswordSetActivityIndentiful, new KapActivityInfoTransferManager.InfoTransferModelInterface<KapPasswordSetActivity>() {
            @Override
            public void changeUIByModel(KapPasswordSetActivity model) {
                KapApplication.homeActivityChangeAction();//跳到主页
            }
        });
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
