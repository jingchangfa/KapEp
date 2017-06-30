package com.example.jing.kapep.View;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.jing.kapep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jing on 17/5/18.
 */

public class KapTimeChangeButton extends LinearLayout {
   public interface TimerButtonListener {
        void start();
        void finish();
    }
    @BindView(R.id.time_button) Button button;
    private TimerButtonListener timerButtonListener = null;
    private KapCountDownButtonHelper helper = null;
    public KapTimeChangeButton(@NonNull Context context) {
        super(context);
    }
    public KapTimeChangeButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_timechange_button,this);
        ButterKnife.bind(this,view);
        reSetTimeButton();
    }
    /**
     * 监听状态
     * */
    public void bindListener(TimerButtonListener timerButtonListener){
        this.timerButtonListener = timerButtonListener;
    }
    public void reSetTimeButton(){
        if (helper != null){
            helper.getCountDownTimer().cancel();
            helper = null;
        }
        button.setText("获取验证码");
        button.setEnabled(true);
        helper = new KapCountDownButtonHelper(button,"获取验证码",30,1);
        helper.setOnFinishListener(new KapCountDownButtonHelper.OnFinishListener() {
            @Override
            public void finish() {
                if (timerButtonListener != null)timerButtonListener.finish();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.start();
                if (timerButtonListener != null)timerButtonListener.start();
            }
        });
    }
}
class  KapCountDownButtonHelper{
    // 倒计时timer
    private CountDownTimer countDownTimer;
    // 计时结束的回调接口
    private OnFinishListener listener;

    private Button button;
    /**
     *
     * @param button
     *            需要显示倒计时的Button
     * @param defaultString
     *            默认显示的字符串
     * @param max
     *            需要进行倒计时的最大值,单位是秒
     * @param interval
     *            倒计时的间隔，单位是秒
     */
    public KapCountDownButtonHelper(final Button button,
                                 final String defaultString, int max, int interval) {

        this.button = button;
        // 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致最后一秒不会调用onTick()
        // 因此，设置间隔的时候，默认减去了10ms，从而减去误差。
        // 经过以上的微调，最后一秒的显示时间会由于10ms延迟的积累，导致显示时间比1s长max*10ms的时间，其他时间的显示正常,总时间正常
        countDownTimer = new CountDownTimer(max * 1000, interval * 1000 - 10) {
            @Override
            public void onTick(long time) {
                // 第一次调用会有1-10ms的误差，因此需要+15ms，防止第一个数不显示，第二个数显示2s
//                String text = defaultString + "(" + ((time + 15) / 1000)
//                        + "秒)";
                String text = ((time + 15) / 1000)+"S";
                button.setText(text);
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setText(defaultString);
                if (listener != null) {
                    listener.finish();
                }
            }
        };
    }

    /**
     * 开始倒计时
     */
    public void start() {
        button.setEnabled(false);
        countDownTimer.start();
    }

    /**
     * 设置倒计时结束的监听器
     *
     * @param listener
     */
    public void setOnFinishListener(OnFinishListener listener) {
        this.listener = listener;
    }

    /**
     * 计时结束的回调接口
     *
     * @author zhaokaiqiang
     *
     */
    public interface OnFinishListener {
        public void finish();
    }

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
    }
}