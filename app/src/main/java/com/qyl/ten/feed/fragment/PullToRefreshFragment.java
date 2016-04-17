package com.qyl.ten.feed.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qyl.ten.R;
import com.qyl.ten.common.fragment.BaseFragment;
import com.qyl.ten.common.views.ScrollChildSwipeRefreshLayout;
import com.qyl.ten.feed.listener.EndlessRecyclerScrollListener;
import com.qyl.ten.feed.listener.PauseOnScrollRecyclerListener;

/**
 * Created by qiuyunlong on 16/4/15.
 */
public abstract class PullToRefreshFragment extends BaseFragment {

    private final static String TAG = PullToRefreshFragment.class.getSimpleName();

    private View rootView;

    private RecyclerView recyclerView;

    protected ScrollChildSwipeRefreshLayout swipeRefreshLayout;

    protected RecyclerView.Adapter<?> adapter;

    protected boolean isNeedAutoRefresh = false;
    protected boolean isLoadEnd = false;
    protected boolean isRefreshing = false;

    protected EndlessRecyclerScrollListener endlessScrollListener = new EndlessRecyclerScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            loadMore();

            //onLoadMoreInternal();
        }

        @Override
        public void onFirstVisibleItem(int index) {
            //System.out.println(TAG + "==onFirstVisibleItem=" + index);
        }

        @Override
        public void onLoadEnd(boolean status) {
            System.out.println("wwwwwwww====onLoadEnd=" + status );
            if (status && isLoadEnd) {
                scrollToEnd();
            }
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setAdapter(adapter);

        isNeedAutoRefresh = true;

        if (adapter.getItemCount() == 0) {
            refreshInternal();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_feed_base, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = (ScrollChildSwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        swipeRefreshLayout.setColorSchemeResources(R.color.primary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMore();
                System.out.println(TAG + "===onRefresh");
            }
        });
        swipeRefreshLayout.setScrollUpChild(getRecyclerView());

        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setItemAnimator(getItemAnimator());
        recyclerView.addOnScrollListener(new PauseOnScrollRecyclerListener(ImageLoader.getInstance(), false, true, endlessScrollListener));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recyclerView != null){
            recyclerView.clearOnScrollListeners();
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private void onLoadMoreInternal() {
        if (onLoadMore()) {
            loadMore();
        }
    }

    protected void refreshInternal() {
        onLayoutRefresh();
        loadMore();
    }

    protected void setRefreshing(boolean value) {
        swipeRefreshLayout.setRefreshing(value);
    }

    abstract protected void onLayoutRefresh();

    abstract protected boolean onLoadMore();

    abstract protected void loadMore();

    abstract protected void scrollToEnd();

    abstract protected RecyclerView.LayoutManager getLayoutManager();

    abstract protected RecyclerView.ItemAnimator getItemAnimator();


}
