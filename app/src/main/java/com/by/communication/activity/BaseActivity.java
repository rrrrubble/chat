package com.by.communication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.by.communication.App;
import com.by.communication.R;
import com.by.communication.entity.User;
import com.by.communication.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Produced a lot of bug on 2017/3/30.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private App  app;
    private long user_id;

    @Nullable
    @BindView(R.id.topBar_backImageView)
    ImageView backImageView;
    @Nullable
    @BindView(R.id.topBar_titleTextView)
    TextView  titleTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {

        app = App.getInstance();
        User user = app.getUser();

        if (user != null)
            user_id = user.getId();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);

        if (backImageView != null) {
            backImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    finish();
                }
            });
        }
    }


    public void setTitle(String text)
    {
        if (titleTextView != null) {
            titleTextView.setText(text);
        }
    }

    public abstract int getLayoutResId();

    public App getApp()
    {
        return app;
    }

    public long getUser_id()
    {
        return user_id;
    }

    public void toast(String message)
    {
        Util.toast(getApplicationContext(), message);
    }

    public void toast(@StringRes int message)
    {
        Util.toast(getApplicationContext(), getString(message));
    }
}
