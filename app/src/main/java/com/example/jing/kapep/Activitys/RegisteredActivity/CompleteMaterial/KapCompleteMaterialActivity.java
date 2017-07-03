package com.example.jing.kapep.Activitys.RegisteredActivity.CompleteMaterial;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.jing.kapep.Activitys.ActivityBase.ActivityBase;
import com.example.jing.kapep.Activitys.LoginActivity.LoginEditTextView;
import com.example.jing.kapep.Application.KapApplication;
import com.example.jing.kapep.Manager.KapCreameManager;
import com.example.jing.kapep.R;
import com.example.jing.kapep.View.KapBigChangeButton;
import com.example.jing.kapep.View.KapCompleteShowButton;
import com.example.jing.kapep.View.ViewSexButton;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jing on 17/5/10.
 */

public class KapCompleteMaterialActivity extends ActivityBase{
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
//        numberView.getEditText().setText(KapApplication.ge);
        placeView.getTextView().setText("中国北京");
        occView.getTextView().setText("户外运动");

        finishButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KapApplication.homeActivityChangeAction();//跳到主页
            }
        });
    }

    @Override
    protected void getModel() {
        super.getModel();
    }

    void choseImageAction(){
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
