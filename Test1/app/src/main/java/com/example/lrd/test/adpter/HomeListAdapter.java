package com.example.lrd.test.adpter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lrd.R;
import com.example.lrd.test.entity.MultipleItem;

import java.util.List;

/**
 * Created by lrd on 2018/1/23.
 */

public class HomeListAdapter extends BaseMultiItemQuickAdapter<MultipleItem,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeListAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.LEFT, R.layout.test_item_brva_layout);
        addItemType(MultipleItem.RIGHT, R.layout.test_header_right_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {

        switch (helper.getItemViewType()) {
            case MultipleItem.LEFT:
                helper.setText(R.id.test_item_brva_text,item.getName());
//                helper.setImageUrl(R.id.tv, item.getContent());
                break;
            case MultipleItem.RIGHT:
                helper.setText(R.id.test_item_rightText,item.getPassword());
                break;
        }
    }
}
