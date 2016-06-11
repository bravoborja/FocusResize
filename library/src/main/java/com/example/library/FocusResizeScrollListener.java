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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

public class FocusResizeScrollListener<T extends FocusResizeAdapter> extends RecyclerView.OnScrollListener {

    private final String TAG = getClass().getSimpleName();
    private int heightCollapsedItem;
    private int heightExpandedItem;
    private int itemToResize;
    private boolean init = false;
    private LinearLayoutManager mLinearLayoutManager;
    private int dyAbs;
    private T adapter;

    public FocusResizeScrollListener(T adapter, LinearLayoutManager linearLayoutManager) {
        this.adapter = adapter;
        heightCollapsedItem = adapter.getHeight();
        heightExpandedItem = heightCollapsedItem * 3;
        mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        try {
            if (mLinearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                dyAbs = Math.abs(dy);
                int totalItemCount = mLinearLayoutManager.getItemCount();
                itemToResize = dy > 0 ? 1 : 0;
                initFocusResize(recyclerView);
                calculateScrolledPosition(totalItemCount, recyclerView);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void calculateScrolledPosition(int totalItemCount, RecyclerView recyclerView) {
        for (int j = 0; j < totalItemCount - 1; j++) {
            View view = recyclerView.getChildAt(j);
            if (view != null) {
                if (!(recyclerView.getChildViewHolder(view) instanceof FocusResizeAdapter.FooterViewHolder)) {
                    if (j == itemToResize) {
                        onItemBigResize(view, recyclerView);
                    } else {
                        onItemSmallResize(view, recyclerView);
                    }
                    view.requestLayout();
                }
            }
        }
    }

    private void onItemSmallResize(View view, RecyclerView recyclerView) {
        if (view.getLayoutParams().height - dyAbs <= heightCollapsedItem) {
            view.getLayoutParams().height = heightCollapsedItem;
        } else if (view.getLayoutParams().height >= heightCollapsedItem) {
            view.getLayoutParams().height -= (dyAbs * 2);
        }
        adapter.onItemSmallResize(recyclerView.getChildViewHolder(view), itemToResize, dyAbs);
    }

    private void onItemBigResize(View view, RecyclerView recyclerView) {
        if (view.getLayoutParams().height + dyAbs >= heightExpandedItem) {
            view.getLayoutParams().height = heightExpandedItem;
        } else {
            view.getLayoutParams().height += (dyAbs * 2);
        }
        adapter.onItemBigResize(recyclerView.getChildViewHolder(view), itemToResize, dyAbs);
    }

    private void initFocusResize(RecyclerView recyclerView) {
        if (!init) {
            init = true;
            View view = recyclerView.getChildAt(0);
            view.getLayoutParams().height = heightExpandedItem;
            adapter.onItemInit(recyclerView.getChildViewHolder(view));
        }
    }

    private int calculatePositionScrolledDown(RecyclerView recyclerView) {
        int positionScrolled;
        if (mLinearLayoutManager.findFirstCompletelyVisibleItemPosition()
                == mLinearLayoutManager.getItemCount() - 1) {
            positionScrolled = itemToResize - 1;
            mLinearLayoutManager.scrollToPositionWithOffset(
                    mLinearLayoutManager.findFirstVisibleItemPosition(), 0);
        } else {
            if (recyclerView.getChildAt(itemToResize).getHeight() > recyclerView.getChildAt(
                    itemToResize - 1).getHeight()) {
                positionScrolled = itemToResize;
                mLinearLayoutManager.scrollToPositionWithOffset(
                        mLinearLayoutManager.findFirstCompletelyVisibleItemPosition(), 0);
            } else {
                positionScrolled = itemToResize - 1;
                mLinearLayoutManager.scrollToPositionWithOffset(
                        mLinearLayoutManager.findFirstVisibleItemPosition(), 0);
            }
        }
        return positionScrolled;
    }

    private int calculatePositionScrolledUp(RecyclerView recyclerView) {
        int positionScrolled;
        if (recyclerView.getChildAt(itemToResize).getHeight() > recyclerView.getChildAt(itemToResize + 1)
                .getHeight()) {
            positionScrolled = itemToResize;
            mLinearLayoutManager.scrollToPositionWithOffset(
                    mLinearLayoutManager.findFirstVisibleItemPosition(), 0);
        } else {
            positionScrolled = itemToResize + 1;
            mLinearLayoutManager.scrollToPositionWithOffset(
                    mLinearLayoutManager.findFirstCompletelyVisibleItemPosition(), 0);
        }
        return positionScrolled;
    }

    private void forceScrollItem(RecyclerView recyclerView, View view, int j, int positionScrolled) {
        if (!(recyclerView.getChildViewHolder(view) instanceof FocusResizeAdapter.FooterViewHolder)) {
            if (j == positionScrolled) {
                view.getLayoutParams().height = heightExpandedItem;
                adapter.onItemBigResizeScrolled(recyclerView.getChildViewHolder(view), itemToResize, dyAbs);
            } else {
                if (mLinearLayoutManager.findFirstCompletelyVisibleItemPosition()
                        == mLinearLayoutManager.getItemCount() - 1
                        || mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == -1) {
                    view.getLayoutParams().height = heightExpandedItem;
                    adapter.onItemBigResizeScrolled(recyclerView.getChildViewHolder(view), itemToResize, dyAbs);
                } else {
                    view.getLayoutParams().height = heightCollapsedItem;
                    adapter.onItemSmallResizeScrolled(recyclerView.getChildViewHolder(view), itemToResize, dyAbs);
                }
            }
        }
    }

    @Override
    public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        try {
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                if (mLinearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                    int positionScrolled = (itemToResize == 1) ? calculatePositionScrolledDown(recyclerView) : calculatePositionScrolledUp(recyclerView);
                    for (int j = 0; j < mLinearLayoutManager.getItemCount() - 1; j++) {
                        View view = recyclerView.getChildAt(j);
                        if (view != null) {
                            forceScrollItem(recyclerView, view, j, positionScrolled);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}