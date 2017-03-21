package com.aspsine.irecyclerview.listeners;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by aspsine on 16/3/13.
 */
public abstract class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();


        boolean triggerCondition = visibleItemCount > 0
                && newState == RecyclerView.SCROLL_STATE_IDLE
                && canTriggerLoadMore(recyclerView);

        if (triggerCondition) {
            onLoadMore(recyclerView);
        }
    }

    public boolean canTriggerLoadMore(RecyclerView recyclerView) {
        // the last View is load more
        View lastChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
        // the last position of real data
        int position = recyclerView.getChildLayoutPosition(lastChild) - 1;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalCount = layoutManager.getChildCount();
        return totalCount == position;
    }

    public abstract void onLoadMore(RecyclerView recyclerView);
}
