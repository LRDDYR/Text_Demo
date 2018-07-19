package com.example.lrd.test.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lrd on 2018/1/23.
 */

public class MultipleItem implements MultiItemEntity {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    private int itemType;
    private String name;
    private String password;

    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}