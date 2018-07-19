package com.example.lrd.test.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lrd on 2018/1/22.
 */

public class HomeItem implements MultiItemEntity {
    public static final int LEFT = 1;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getItemType() {
        return LEFT;
    }
}
