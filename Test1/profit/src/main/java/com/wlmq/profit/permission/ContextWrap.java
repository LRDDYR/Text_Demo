package com.wlmq.profit.permission;


import android.app.Activity;
import android.support.v4.app.Fragment;


public class ContextWrap {
    private Activity activity;
    private Fragment fragment;
    public static ContextWrap of(Activity activity){
        return new ContextWrap(activity);
    }
    public static ContextWrap of(Fragment fragment){
        return new ContextWrap(fragment);
    }
    private ContextWrap(Activity activity) {
        this.activity = activity;
    }

    private ContextWrap(Fragment fragment) {
        this.fragment = fragment;
        this.activity=fragment.getActivity();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
