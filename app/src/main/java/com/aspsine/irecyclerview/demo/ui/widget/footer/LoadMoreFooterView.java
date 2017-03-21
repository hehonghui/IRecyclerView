package com.aspsine.irecyclerview.demo.ui.widget.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.aspsine.irecyclerview.demo.R;
import com.aspsine.irecyclerview.footer.FooterView;

/**
 * Created by aspsine on 16/3/14.
 */
public class LoadMoreFooterView extends FrameLayout implements FooterView {

    private Status mStatus;

    private View mLoadingView;

    private OnRetryListener mOnRetryListener;

    public LoadMoreFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_irecyclerview_load_more_footer_view, this, true);
        mLoadingView = findViewById(R.id.loadingView);
        setStatus(Status.GONE);
    }

    public void setOnRetryListener(OnRetryListener listener) {
        this.mOnRetryListener = listener;
    }

    public Status getStatus() {
        return mStatus;
    }

    @Override
    public void setStatus(Status status) {
        this.mStatus = status;
        change();
    }

    private void change() {
        switch (mStatus) {
            case GONE:
                mLoadingView.setVisibility(GONE);
                break;

            case LOADING:
                mLoadingView.setVisibility(VISIBLE);
                break;

            case ERROR:
                mLoadingView.setVisibility(GONE);
                break;

            case THE_END:
                mLoadingView.setVisibility(GONE);
                break;
        }
    }

    @Override
    public boolean isLoading() {
        return mLoadingView.isShown();
    }

    @Override
    public boolean canLoadMore() {
        return mStatus == Status.GONE || mStatus == Status.ERROR;
    }

    public interface OnRetryListener {
        void onRetry(LoadMoreFooterView view);
    }

}
