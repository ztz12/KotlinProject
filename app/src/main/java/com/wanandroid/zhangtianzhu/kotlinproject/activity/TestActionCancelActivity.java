package com.wanandroid.zhangtianzhu.kotlinproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wanandroid.zhangtianzhu.kotlinproject.R;

/**
 * 测试ACTION_CANCEL 事件
 * 当layoutview1拦截layoutView2 move或者move以后的事件，Layoutview 2就会接收到cancel事件，也就是事件分发被取消掉了
 */
public class TestActionCancelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_action_cancel);
    }
}
