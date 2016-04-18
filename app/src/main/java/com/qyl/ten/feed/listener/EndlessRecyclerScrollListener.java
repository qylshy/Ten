package com.qyl.ten.feed.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.AbsListView;

/**
 * Created by qiuyunlong on 16/4/15.
 */
public abstract class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {
    private static final String TAG = EndlessRecyclerScrollListener.class.getSimpleName();

    private boolean mLastItemVisible;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 6;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = false;
    // Sets the starting page index
    private int startingPageIndex = 0;

    private boolean fromBottom = false;

    private boolean loadEnd;

    public EndlessRecyclerScrollListener() {
        this(false);
    }

    public EndlessRecyclerScrollListener(boolean fromBottom) {
        this.fromBottom = fromBottom;
    }

    public EndlessRecyclerScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessRecyclerScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int visibleItemCount = 0;
        int totalItemCount = 0;
        int firstVisibleItem = 0;
        int lastVisibleItem = 0;
        Object layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            visibleItemCount = linearLayoutManager.getChildCount();
            totalItemCount = linearLayoutManager.getItemCount();
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            visibleItemCount = staggeredGridLayoutManager.getChildCount();
            totalItemCount = staggeredGridLayoutManager.getItemCount();
            int into[] = null;
            into = staggeredGridLayoutManager.findFirstVisibleItemPositions(into);
            firstVisibleItem = into[0];
            int last[] = null;
            last = staggeredGridLayoutManager.findLastVisibleItemPositions(last);
            lastVisibleItem = last[0];
        }

        if (fromBottom) {
            mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem <= visibleThreshold);
        } else {
            mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - visibleThreshold);
        }

        Log.d(TAG, String.format("firstVisibleItem: %s mLastItemVisible: %s visibleItemCount: %s totalItemCount: %s", firstVisibleItem, mLastItemVisible, visibleItemCount, totalItemCount));


        if (!loading && mLastItemVisible) {
            onLoadMore(0, 0);
        }

        loadEnd = (totalItemCount > 0) && (lastVisibleItem == totalItemCount - 1);

        onFirstVisibleItem(firstVisibleItem);
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount);

    public void onLoadEnd(boolean status) {
    }

    public void onFirstVisibleItem(int index) {
    }

    public void setLoading(boolean value) {
        loading = value;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItemVisible) {
//            onLoadMore(0, 0);
//        }

        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            onLoadEnd(loadEnd);
        }
    }
}

