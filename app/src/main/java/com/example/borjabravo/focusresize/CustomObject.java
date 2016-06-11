package com.example.borjabravo.focusresize;

/**
 * Created by borjabravo on 11/6/16.
 */
public class CustomObject {

    String title;
    String subTitle;
    int drawable;

    public CustomObject(String title, String subTitle, int drawable) {
        this.title = title;
        this.subTitle = subTitle;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
