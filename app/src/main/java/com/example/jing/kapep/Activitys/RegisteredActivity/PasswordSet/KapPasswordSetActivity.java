package com.example.jing.kapep.Activitys.RegisteredActivity.PasswordSet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.LoginActivity.LoginEditTextView;
import com.example.jing.kapep.HttpClient.BaseHttp.HttpClickBase;
import com.example.jing.kapep.HttpClient.KapHttpChildren.KapAuthAPIClient;
import com.example.jing.kapep.Manager.KapActivityInfoTransferManager;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapAlertBankShowView;

import butterknife.BindView;

/**
 * Created by jing on 17/5/10.
 */

public class KapPasswordSetActivity extends ActivityBase {
    public static final String PasswordSetActivityIndentiful = "KapPasswordSetActivity";

    @BindView(R.id.forget_next)
    KapAlertBankShowView nextView;

    @BindView(R.id.forget_passwordview)
    LoginEditTextView numberView;

    @BindView(R.id.forget_againview)
    LoginEditTextView againView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register_passwordset;
    }

    @Override
    protected String navShowTitle() {
        return "设置密码";
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
                nextViewNextAction();
            }
        });
    }

    @Override
    protected void getModel() {
        super.getModel();
    }

    private void nextViewNextAction(){
        String passString = numberView.getEditText().getText().toString();
        String againString = againView.getEditText().getText().toString();
        if (passString == null) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!passString.equals(againString)){
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        new KapAuthAPIClient().authSetPassWord(passString, new KapAuthAPIClient.KapAuthNoParticipationInterface() {
            @Override
            public void successResult() {
                KapActivityInfoTransferManager.PostChangeByModel(KapPasswordSetActivity.this,PasswordSetActivityIndentiful);
            }
        }, new HttpClickBase.HTTPAPIDefaultFailureBack() {
            @Override
            public void defaultFailureBlock(long errorCode, String errorMsg) {
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
