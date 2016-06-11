package com.example.borjabravo.focusresize;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.FocusResizeAdapter;

import java.util.ArrayList;
import java.util.List;

public class DefaultAdapter extends FocusResizeAdapter<RecyclerView.ViewHolder> {

    private List<CustomObject> items;

    public DefaultAdapter(Context context, int height) {
        super(context, height);
        items = new ArrayList<>();
    }

    public void addItems(List<CustomObject> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getFooterItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom, parent, false);
        return new DefaultCustomViewHolder(v);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomObject customObject = items.get(position);
        fill((DefaultCustomViewHolder)holder, customObject);
    }

    private void fill(DefaultCustomViewHolder holder, CustomObject customObject) {
        holder.titleTextView.setText(customObject.getTitle());
        holder.subtitleTextView.setText(customObject.getSubTitle());
        holder.image.setImageResource(customObject.getDrawable());
    }

    @Override
    public void onItemBigResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
    }

    @Override
    public void onItemBigResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
    }

    @Override
    public void onItemSmallResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
    }

    @Override
    public void onItemSmallResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
    }

    @Override
    public void onItemInit(RecyclerView.ViewHolder viewHolder) {
    }

    public class DefaultCustomViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView titleTextView;
        TextView subtitleTextView;

        public DefaultCustomViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image_custom_item);
            titleTextView = (TextView) v.findViewById(R.id.title_custom_item);
            subtitleTextView = (TextView) v.findViewById(R.id.subtitle_custom_item);
        }
    }
}
