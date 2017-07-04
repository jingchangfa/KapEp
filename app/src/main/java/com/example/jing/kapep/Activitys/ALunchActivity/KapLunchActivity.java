package com.example.jing.kapep.Activitys.ALunchActivity;

import android.content.Intent;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.RegisteredActivity.CompleteMaterial.KapCompleteMaterialActivity;
import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.Helper.MainThreadHelper;
import com.example.jing.kapep.R;

/**
 * Created by jing on 17/5/19.
 */

public class KapLunchActivity extends ActivityBase {
    @Override
    protected String navShowTitle() {
        return null;
    }

    @Override
    protected void leftButtonAction() {
        super.leftButtonAction();
    }

    @Override
    protected void rightButtonAction() {
        super.rightButtonAction();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_lunch;
    }

    @Override
    protected void setController() {
    }

    @Override
    protected void getView() {

    }

    @Override
    protected void getModel() {
        super.getModel();
        //延时执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MainThreadHelper.runOnUIthread(new Runnable() {
                    @Override
                    public void run() {
                        pushActivity();
                    }
                });
            }
        }).start();
    }
    private void pushActivity(){
        if (goEveryActivity()) return;//方便调试，跳到任意的activity
        if (KapApplication.getUserAccount() == null){
            KapApplication.logInActivityChangeAction();//登录页
        }else {
            KapApplication.homeActivityChangeAction();//主页
        }
    }
    /**
     * 此方法调试专用～～～
     * 不用了，仅需要注释 everyActivityClass = xx；即可
     * */
    private boolean goEveryActivity(){
        Class everyActivityClass = null;
        everyActivityClass = KapCompleteMaterialActivity.class;
        boolean isOpen = everyActivityClass != null;
        if (isOpen) startActivity(new Intent(this, everyActivityClass));
        return isOpen;
    }
}
