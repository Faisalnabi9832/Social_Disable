package com.example.socialdisable;

import android.graphics.drawable.Drawable;

public class ModelForSocial {
    private String appName;
    private String packageName;
    private Drawable appIcon;
    private boolean isNotificationEnabled;

    public ModelForSocial(String appName, String packageName, Drawable appIcon) {
        this.appName = appName;
        this.packageName = packageName;
        this.appIcon = appIcon;
        this.isNotificationEnabled = false; // Default: Notifications are disabled
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public boolean isNotificationEnabled() {
        return isNotificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        isNotificationEnabled = notificationEnabled;
    }
}
