package com.aspsine.irecyclerview.demo.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aspsine.irecyclerview.XRecyclerView;
import com.aspsine.irecyclerview.demo.R;
import com.aspsine.irecyclerview.demo.model.Image;
import com.aspsine.irecyclerview.demo.ui.adapter.ImageAdapter;
import com.aspsine.irecyclerview.demo.ui.adapter.OnItemClickListener;
import com.aspsine.irecyclerview.demo.ui.widget.footer.LoadMoreFooterView;
import com.aspsine.irecyclerview.demo.ui.widget.header.NewsDogHeaderView;
import com.aspsine.irecyclerview.demo.ui.widget.header.ClassicRefreshHeaderView;
import com.aspsine.irecyclerview.demo.utils.DensityUtils;
import com.aspsine.irecyclerview.demo.utils.ListUtils;
import com.aspsine.irecyclerview.listeners.OnLoadMoreListener;
import com.aspsine.irecyclerview.listeners.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnItemClickListener<Image>, OnRefreshListener,
        OnLoadMoreListener {

    private XRecyclerView mRecyclerView;
    private LoadMoreFooterView loadMoreFooterView;

    private ImageAdapter mAdapter;

    private int mPage;
    Handler mHandler = new Handler(Looper.getMainLooper()) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (XRecyclerView) findViewById(R.id.iRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setItemAnimator(new ScaleInOutItemAnimator());

        // setup footer view
        loadMoreFooterView = (LoadMoreFooterView) mRecyclerView.getLoadMoreFooterView();

        mAdapter = new ImageAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(this);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setRefreshing(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change_header, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_toggle_header) {
            toggleRefreshHeader();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position, Image image, View v) {
        mAdapter.remove(position);
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        refresh();
    }

    @Override
    public void onLoadMore() {
        if (loadMoreFooterView.canLoadMore() && mAdapter.getItemCount() > 0) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
            loadMore();
        }
    }

    private void toggleRefreshHeader() {
        if (mRecyclerView.getRefreshHeaderView() instanceof NewsDogHeaderView) {
            ClassicRefreshHeaderView classicRefreshHeaderView = new ClassicRefreshHeaderView(this);
            classicRefreshHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                    .MATCH_PARENT, DensityUtils.dip2px(this, 80)));
            // we can set view
            mRecyclerView.setRefreshHeaderView(classicRefreshHeaderView);
            Toast.makeText(this, "Classic style", Toast.LENGTH_SHORT).show();
        } else if (mRecyclerView.getRefreshHeaderView() instanceof ClassicRefreshHeaderView) {
            // we can also set layout
            mRecyclerView.setRefreshHeaderView(R.layout.layout_irecyclerview_refresh_header);
            Toast.makeText(this, "Bat man vs Super man style", Toast.LENGTH_SHORT).show();
        }
    }


    private void refresh() {
        mPage = 1;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Image> images = mockImages();
                mRecyclerView.setRefreshing(false);
                if (ListUtils.isEmpty(images)) {
                    mAdapter.clear();
                } else {
                    mPage = 2;
                    mAdapter.addToHead(images);
                }
            }
        }, 3000);
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
                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
                } else {
                    /**
                     * FIXME here we post delay to see more animation, you don't need to do this.
                     */
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPage++;
                            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                            mAdapter.append(images);
                        }
                    }, 500);
                }
            }
        }, 3000);
    }
}
