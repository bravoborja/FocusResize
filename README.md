# FocusResize
[![Build Status](https://travis-ci.org/borjabravo10/ReadMoreTextView.svg?branch=master)](https://travis-ci.org/borjabravo10/FocusResize)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-FocusResize-green.svg?style=true)](https://android-arsenal.com/details/1/3834)
[![](https://jitpack.io/v/borjabravo10/FocusResize.svg)](https://jitpack.io/#borjabravo10/FocusResize)
[![Android Weekly](http://img.shields.io/badge/Android%20Weekly-%23213-2CB3E5.svg?style=flat)](http://androidweekly.net/issues/issue-213)


A custom animation with scroll listener to recycler views

![GIF of its use](https://github.com/borjabravo10/FocusResize/blob/master/resources/focusResize.gif)

Based in UltraVisual example of [Ray Wenderlich](http://www.raywenderlich.com/99087/swift-expanding-cells-ios-collection-views).

## Download
To add the FocusResize library to your Android Studio project, simply add the following gradle dependency, with min sdk version of 19:

```java
compile 'com.borjabravo:focusresize:1.0.0'
```

## Usage

To use the FocusResize on your app, you should create a custom adapter that extends from FocusResizeAdapter. For example:
```java
public class DefaultAdapter extends FocusResizeAdapter<RecyclerView.ViewHolder> {

    private List<CustomObject> items;

    public DefaultAdapter(Context context, int height) {
        super(context, height);
        items = new ArrayList<>();
    }

    @Override
    public int getFooterItemCount() {
        // Return items size
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        // Inflate your custom item layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom, parent, false);
        return new DefaultCustomViewHolder(v);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Set your data into your custom layout
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
        // The focused item will resize to big size while is scrolling
    }

    @Override
    public void onItemBigResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        // The focused item resize to big size when scrolled is finished
    }

    @Override
    public void onItemSmallResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        // All items except the focused item will resize to small size when scrolled is finished
    }

    @Override
    public void onItemSmallResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        // All items except the focused item will resize to small size while is scrolling
    }

    @Override
    public void onItemInit(RecyclerView.ViewHolder viewHolder) {
        // Init first item when the view is loaded
    }
```
After this, you'll add FocusResizeScrollListener to recycler view:

```java
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    DefaultAdapter defaultAdapter = new DefaultAdapter(this, (int) getResources().getDimension(R.dimen.custom_item_height));
    defaultAdapter.addItems(addItems());
    if (recyclerView != null) {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(defaultAdapter);
        recyclerView.addOnScrollListener(new FocusResizeScrollListener<>(defaultAdapter, linearLayoutManager));
    }
```

**Important**: In this version, the library only works with LinearLayoutManager.VERTICAL
License
=======

    Copyright 2016 Borja Bravo Álvarez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
