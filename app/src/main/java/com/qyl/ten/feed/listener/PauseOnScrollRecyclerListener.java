package com.qyl.ten.feed.listener;

import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by qiuyunlong on 16/4/15.
 */
public class PauseOnScrollRecyclerListener extends RecyclerView.OnScrollListener {

    private final boolean pauseOnScroll;
    private final boolean pauseOnFling;
    private final RecyclerView.OnScrollListener externalListener;
    private ImageLoader imageLoader;

    /**
     * Constructor
     *
     * @param imageLoader   {@linkplain ImageLoader} instance for controlling
     * @param pauseOnScroll Whether {@linkplain ImageLoader#pause() pause ImageLoader} during touch scrolling
     * @param pauseOnFling  Whether {@linkplain ImageLoader#pause() pause ImageLoader} during fling
     */
    public PauseOnScrollRecyclerListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
        this(imageLoader, pauseOnScroll, pauseOnFling, null);
    }

    /**
     * Constructor
     *
     * @param imageLoader    {@linkplain ImageLoader} instance for controlling
     * @param pauseOnScroll  Whether {@linkplain ImageLoader#pause() pause ImageLoader} during touch scrolling
     * @param pauseOnFling   Whether {@linkplain ImageLoader#pause() pause ImageLoader} during fling
     * @param customListener Your custom {@link AbsListView.OnScrollListener} for {@linkplain AbsListView list view} which also
     *                       will be get scroll events
     */
    public PauseOnScrollRecyclerListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling,
                                         RecyclerView.OnScrollListener customListener) {
        this.imageLoader = imageLoader;
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        externalListener = customListener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView view, int scrollState) {
        switch (scrollState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                imageLoader.resume();
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                if (pauseOnScroll) {
                    imageLoader.pause();
                }
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                if (pauseOnFling) {
                    imageLoader.pause();
                }
                break;
        }
        if (externalListener != null) {
            externalListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (externalListener != null) {
            externalListener.onScrolled(recyclerView, dx, dy);
        }
    }
}
