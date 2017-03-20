package com.aspsine.irecyclerview.demo.ui.widget.header;

import android.animation.Animator;
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
public class BatVsSupperHeaderView extends FrameLayout implements RefreshHeader {

    private ImageView ivBatMan;

    private ImageView ivSuperMan;

    private ImageView ivVs;

    private int mHeight;

    private Animator mRotationAnimator;

    private AnimationDrawable drawable;

    private TextView mStatusText;

    public BatVsSupperHeaderView(Context context) {
        this(context, null);
    }

    public BatVsSupperHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatVsSupperHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_irecyclerview_bat_vs_supper_refresh_header_view, this);
        ivBatMan = (ImageView) findViewById(R.id.ivBatMan);
        ivSuperMan = (ImageView) findViewById(R.id.ivSuperMan);
        ivVs = (ImageView) findViewById(R.id.imageView);
        drawable = (AnimationDrawable) ivVs.getDrawable();
        mStatusText = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onStart(boolean automatic, int headerHeight, int finalHeight) {
        mHeight = headerHeight;
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
