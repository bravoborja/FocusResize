/*
 * Copyright (C) 2016 Borja Bravo Álvarez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.library;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FocusResizeAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private static final int VIEW_TYPE_FOOTER = 1;

    private Context context;
    private int height;

    public FocusResizeAdapter(Context context, int height) {
        this.context = context;
        this.height = height;
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FOOTER) {
            return (T) new FooterViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
        } else {
            return onCreateFooterViewHolder(parent, viewType);
        }
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        // Loader ViewHolder
        if (position != getFooterItemCount()) {
            onBindFooterViewHolder((T) viewHolder, position);
        }
    }

    @Override
    public final int getItemCount() {
        if (getFooterItemCount() == 0) {
            return 0;
        }
        return getFooterItemCount() + 1;
    }

    @Override
    public final int getItemViewType(int position) {
        if (position != 0 && position == getItemCount() - 1) {
            return VIEW_TYPE_FOOTER;
        }
        return getItemFooterViewType(position);
    }

    public abstract void onItemBigResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs);

    public abstract void onItemBigResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs);

    public abstract void onItemSmallResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs);

    public abstract void onItemSmallResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs);

    public abstract void onItemInit(RecyclerView.ViewHolder viewHolder);

    public int getItemFooterViewType(int position) {
        return 0;
    }

    public abstract int getFooterItemCount();

    public abstract T onCreateFooterViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindFooterViewHolder(T holder, int position);


    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View v) {
            super(v);
            v.getLayoutParams().height = Utils.getFooterHeight(((Activity) context), (height * 3));
        }
    }

    public int getHeight() {
        return height;
    }
}