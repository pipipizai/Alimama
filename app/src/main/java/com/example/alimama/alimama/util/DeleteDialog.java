package com.example.alimama.alimama.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.alimama.alimama.R;

/**
 * 创建自定义的dialog，主要学习其实现原理
 *
 */
public class DeleteDialog extends Dialog{

    private Button yes;//确定按钮
    private Button no;//取消按钮
    private TextView titleTextView;//消息标题文本
    private TextView messageTextView;//消息提示文本
    private String titleString;//从外界设置的title文本
    private String messageString;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String yesString, noString;
    private Context context;
    private ViewGroup.LayoutParams params;

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器


    public DeleteDialog(Context context, ViewGroup.LayoutParams params) {
        super(context, R.style.DeleteDialog);
        this.context = context;
        this.params = params;

    }
    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noString = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesString = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.dialog_delete_confirm);
        View view =  LayoutInflater.from(context).inflate(R.layout.dialog_delete_confirm, null);
        addContentView(view,params);

        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }



    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (titleString != null) {
            titleTextView.setText(titleString);
        }
        if (messageString != null) {
            messageTextView.setText(messageString);
        }
        //如果设置按钮的文字
        if (yesString != null) {
            yes.setText(yesString);
        }
        if (noString != null) {
            no.setText(noString);
        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = (Button) findViewById(R.id.delete_dialog_btn_yes);
        no = (Button) findViewById(R.id.delete_dialog_btn_no);
        titleTextView = (TextView) findViewById(R.id.delete_dialog_title);
        messageTextView = (TextView) findViewById(R.id.delete_dialog_message);
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleString = title;
    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageString = message;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
}
