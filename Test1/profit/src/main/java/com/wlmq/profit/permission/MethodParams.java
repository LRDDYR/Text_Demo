package com.wlmq.profit.permission;

/**
 * Created by 裕杰 on 2016/12/6.
 */

public class MethodParams {
    PermissionUtil.AsynMethodListener listener = null;
    Object[] objects = null;

    public PermissionUtil.AsynMethodListener getListener() {
        return listener;
    }

    public void setListener(PermissionUtil.AsynMethodListener listener) {
        this.listener = listener;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }
}
