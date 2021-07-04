package com.jw.xfkplugin.mode;

import android.os.Parcel;
import android.os.Parcelable;

public class AppBean implements Parcelable {

    private String pkgName;
    private String appName;
    private String imageUrl;

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public final static Parcelable.Creator<AppBean> CREATOR = new Parcelable.Creator<AppBean>() {
        @Override
        public AppBean createFromParcel(Parcel source) {
            AppBean appBean = new AppBean();
            appBean.setAppName(source.readString());
            appBean.setImageUrl(source.readString());
            appBean.setPkgName(source.readString());
            return appBean;
        }

        @Override
        public AppBean[] newArray(int size) {
            return new AppBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pkgName);
        dest.writeString(imageUrl);
        dest.writeString(appName);
    }

    public AppBean(String pkgName, String appName, String imageUrl) {
        this.pkgName = pkgName;
        this.appName = appName;
        this.imageUrl = imageUrl;
    }

    public AppBean() {
    }
}
