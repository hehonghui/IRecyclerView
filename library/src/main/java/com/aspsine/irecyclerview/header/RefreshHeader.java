package com.aspsine.irecyclerview.header;

/**
 * Created by aspsine on 16/3/7.
 */
public interface RefreshHeader {

    void onStart(boolean automatic, int headerHeight, int finalHeight);

    void onMove(boolean finished, boolean automatic, int moved);

    void onRefresh();

    void onRelease();

    void onComplete();

    void onReset();

    int getMeasuredHeight();
}
