package com.example.jing.kapep.Activitys.LoginActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.ForgetPasswordActivity.KapForgetPasswordActivity;
import com.example.jing.kapep.Activitys.RegisteredActivity.Regist.KapRegistActivity;
import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapAuthAPIClient;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapBigChangeButton;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by jing on 17/5/9.
 */

public class KapLoginActivity extends ActivityBase {
    @BindView(R.id.login_placeview)LoginEditTextView placeEditView;
    @BindView(R.id.login_numberview)LoginEditTextView numberEditView;
    @BindView(R.id.login_passwordview)LoginEditTextView passwordEditView;

    @BindView(R.id.login_login)KapBigChangeButton loginButton;
    @BindView(R.id.login_forgetpassword)Button forgetpasswordButton;
    @BindView(R.id.login_regist)KapBigChangeButton registButton;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }
    @Override
    protected String navShowTitle() {
        return null;
    }
    @Override
    protected void setController() {
        //返回按钮，右侧按钮
    }

    @Override
    protected void getView() {
        placeEditView.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 选择地区
            }
        });
        numberEditView.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 账号检测
            }
        });
        passwordEditView.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 密码检测
            }
        });
        loginButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //登录
                loginAction(numberEditView.editText.getText().toString(),passwordEditView.editText.getText().toString());
            }
        });
        forgetpasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //忘记密码
                startActivity(new Intent(KapLoginActivity.this, KapForgetPasswordActivity.class));
            }
        });
        registButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //注册
                startActivity(new Intent(KapLoginActivity.this, KapRegistActivity.class));
            }
        });
    }

    @Override
    protected void getModel() {
        super.getModel();
    }
    /**
     * 辅助方法
     */
    void  loginAction(String numberString,String passWordString){
        new KapAuthAPIClient().authLogin(numberString, passWordString, new KapAuthAPIClient.KapAuthLoginInterface() {
            @Override
            public void successResult(String token, int userID, Map herd, boolean isFirstLogin) {
                KapApplication.setUserToken(token);
                Toast.makeText(KapLoginActivity.this, ""+userID, Toast.LENGTH_SHORT).show();
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(int errorCode, String errorMsg) {

            }
        });
    }
}
