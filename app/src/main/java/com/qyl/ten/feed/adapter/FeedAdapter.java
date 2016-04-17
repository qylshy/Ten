package com.qyl.ten.feed.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qyl.ten.BuildConfig;
import com.qyl.ten.R;
import com.qyl.ten.feed.entity.DiagramTimeLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuyunlong on 16/4/15.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedItemHolder>{

    private List<DiagramTimeLine.DiagramItemEntity> dataList;

    private OnItemClickListener onItemClickListener;

    public FeedAdapter() {
        dataList = new ArrayList<>();
    }

    public FeedAdapter(List<DiagramTimeLine.DiagramItemEntity> dataList) {
        this.dataList = dataList;
    }

    public void append(List<DiagramTimeLine.DiagramItemEntity> list){
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<DiagramTimeLine.DiagramItemEntity> list){
        dataList = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<DiagramTimeLine.DiagramItemEntity> getDataList() {
        return dataList;
    }

    @Override
    public FeedItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_layout, parent, false);
        return new FeedItemHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedItemHolder holder, final int position) {
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClicked(view, position);
                }
            }
        });
        ImageLoader.getInstance().displayImage(BuildConfig.API_HOST + dataList.get(position).image, holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class FeedItemHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public FeedItemHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.image_view);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(View view, int position);
    }
}
