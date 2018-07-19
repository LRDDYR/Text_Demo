package com.example.lrd.test.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lrd on 2018/1/23.
 */

public class HomeTwoBean implements MultiItemEntity {
    public static final int RIGHT = 2;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getItemType() {
        return RIGHT;
    }
}
