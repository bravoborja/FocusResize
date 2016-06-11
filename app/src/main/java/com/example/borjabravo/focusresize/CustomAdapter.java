package com.example.borjabravo.focusresize;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.FocusResizeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by borjabravo on 11/6/16.
 */
public class CustomAdapter extends FocusResizeAdapter<RecyclerView.ViewHolder> {

    public static final int OFFSET_TEXT_SIZE = 4;
    public static final float OFFSET_TEXT_ALPHA = 100f;
    public static final float ALPHA_SUBTITLE = 0.81f;
    public static final float ALPHA_SUBTITLE_HIDE = 0f;

    private List<CustomObject> items;
    private Context context;

    public CustomAdapter(Context context, int height) {
        super(context, height);
        this.context = context;
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
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomObject customObject = items.get(position);
        fill((CustomViewHolder) holder, customObject);
    }

    private void fill(CustomViewHolder holder, CustomObject customObject) {
        holder.titleTextView.setText(customObject.getTitle());
        holder.subtitleTextView.setText(customObject.getSubTitle());
        holder.image.setImageResource(customObject.getDrawable());
    }

    @Override
    public void onItemBigResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        if (((CustomViewHolder) viewHolder).titleTextView.getTextSize() + (dyAbs / OFFSET_TEXT_SIZE) >= context.getResources().getDimension(R.dimen.font_xxxlarge)) {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_xxxlarge));
        } else {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ((CustomViewHolder) viewHolder).titleTextView.getTextSize() + (dyAbs / OFFSET_TEXT_SIZE));
        }

        float alpha = dyAbs / OFFSET_TEXT_ALPHA;
        if (((CustomViewHolder) viewHolder).subtitleTextView.getAlpha() + alpha >= ALPHA_SUBTITLE) {
            ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE);
        } else {
            ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(((CustomViewHolder) viewHolder).subtitleTextView.getAlpha() + alpha);
        }
    }

    @Override
    public void onItemBigResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_xxxlarge));
        ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE);
    }

    @Override
    public void onItemSmallResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_medium));
        ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE_HIDE);
    }

    @Override
    public void onItemSmallResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        if (((CustomViewHolder) viewHolder).titleTextView.getTextSize() - (dyAbs / OFFSET_TEXT_SIZE) <= context.getResources().getDimension(R.dimen.font_medium)) {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_medium));
        } else {
            ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ((CustomViewHolder) viewHolder).titleTextView.getTextSize() - (dyAbs / OFFSET_TEXT_SIZE));
        }

        float alpha = dyAbs / OFFSET_TEXT_ALPHA;
        if (((CustomViewHolder) viewHolder).subtitleTextView.getAlpha() - alpha < ALPHA_SUBTITLE_HIDE) {
            ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE_HIDE);
        } else {
            ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(((CustomViewHolder) viewHolder).subtitleTextView.getAlpha() - alpha);
        }
    }

    @Override
    public void onItemInit(RecyclerView.ViewHolder viewHolder) {
        ((CustomViewHolder) viewHolder).titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                context.getResources().getDimensionPixelSize(R.dimen.font_xxxlarge));
        ((CustomViewHolder) viewHolder).subtitleTextView.setAlpha(ALPHA_SUBTITLE);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView titleTextView;
        TextView subtitleTextView;

        public CustomViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image_custom_item);
            titleTextView = (TextView) v.findViewById(R.id.title_custom_item);
            subtitleTextView = (TextView) v.findViewById(R.id.subtitle_custom_item);
        }
    }
}
