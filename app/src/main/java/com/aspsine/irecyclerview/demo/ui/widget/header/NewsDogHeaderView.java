package com.aspsine.irecyclerview.demo.ui.widget.header;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.irecyclerview.demo.R;
import com.aspsine.irecyclerview.header.RefreshHeader;

/**
 * Created by aspsine on 16/4/7.
 */
public class NewsDogHeaderView extends FrameLayout implements RefreshHeader {

    private ImageView ivVs;
    private AnimationDrawable drawable;
    private TextView mStatusText;

    public NewsDogHeaderView(Context context) {
        this(context, null);
    }

    public NewsDogHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewsDogHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.newsdog_refresh_header_view, this);
        ivVs = (ImageView) findViewById(R.id.imageView);
        drawable = (AnimationDrawable) ivVs.getDrawable();
        mStatusText = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onStart(boolean automatic, int headerHeight, int finalHeight) {
        Log.e("### SuperHeaderView", "onStart: Pull To Refresh");
        mStatusText.setText("Pull To Refresh");
    }

    @Override
    public void onMove(boolean finished, boolean automatic, int moved) {
    }

    @Override
    public void onRefresh() {
        drawable.start();
        mStatusText.setText("Refreshing...");
    }

    @Override
    public boolean isRefreshing() {
        return drawable.isRunning();
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onReset() {
        drawable.stop();
        mStatusText.setText("Pull To Refresh");
    }

    @Override
    public void onNeedToRelease() {
        mStatusText.setText("Release to Refresh");
    }
}
