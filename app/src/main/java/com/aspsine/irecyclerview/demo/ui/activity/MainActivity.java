package com.aspsine.irecyclerview.demo.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.aspsine.irecyclerview.XRecyclerView;
import com.aspsine.irecyclerview.demo.R;
import com.aspsine.irecyclerview.demo.anim.SlideInOutRightItemAnimator;
import com.aspsine.irecyclerview.demo.model.Image;
import com.aspsine.irecyclerview.demo.ui.adapter.ImageAdapter;
import com.aspsine.irecyclerview.demo.ui.adapter.OnItemClickListener;
import com.aspsine.irecyclerview.demo.ui.widget.footer.LoadMoreFooterView;
import com.aspsine.irecyclerview.demo.ui.widget.header.NewsDogHeaderView;
import com.aspsine.irecyclerview.demo.utils.ListUtils;
import com.aspsine.irecyclerview.listeners.OnLoadMoreListener;
import com.aspsine.irecyclerview.listeners.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnItemClickListener<Image>, OnRefreshListener,
        OnLoadMoreListener {

    private XRecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    Handler mHandler = new Handler(Looper.getMainLooper()) ;
    LoadMoreFooterView mLoadMoreView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (XRecyclerView) findViewById(R.id.iRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new SlideInOutRightItemAnimator(mRecyclerView));
//        mRecyclerView.setItemAnimator(null);

        mRecyclerView.setRefreshHeaderView(new NewsDogHeaderView(this));
        mLoadMoreView = new LoadMoreFooterView(this) ;
        mRecyclerView.setLoadMoreFooterView(mLoadMoreView);

        mAdapter = new ImageAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(int position, Image image, View v) {
        mAdapter.remove(position);
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        mLoadMoreView.setStatus(LoadMoreFooterView.Status.GONE);
        refresh();
    }

    @Override
    public void onLoadMore() {
        if (mLoadMoreView.canLoadMore() && mAdapter.getItemCount() > 0) {
            mLoadMoreView.setStatus(LoadMoreFooterView.Status.LOADING);
            loadMore();
        }
    }


    private void refresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Image> images = mockImages();
                mRecyclerView.setRefreshing(false);
                if (ListUtils.isEmpty(images)) {
                    mAdapter.clear();
                } else {
                    mAdapter.addToHead(images);
                }
            }
        }, 2000);
    }

    private List<Image> mockImages() {
        List<Image> images = new ArrayList<>();
        for (int i = mAdapter.getItemCount(); i < mAdapter.getItemCount() + 5; i++) {
            images.add(new Image("refresh name - " + i, " refresh title - " + i));
        }
        return images;
    }

    private void loadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final List<Image> images = mockImages();
                if (ListUtils.isEmpty(images)) {
                    mLoadMoreView.setStatus(LoadMoreFooterView.Status.THE_END);
                } else {
                    mLoadMoreView.setStatus(LoadMoreFooterView.Status.GONE);
                    mAdapter.append(images);
                }
            }
        }, 2000);
    }
}
