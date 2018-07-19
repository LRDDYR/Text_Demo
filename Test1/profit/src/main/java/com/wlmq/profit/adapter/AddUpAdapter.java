package com.wlmq.profit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlmq.profit.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lrd on 2017/12/15.
 */

public class AddUpAdapter extends RecyclerView.Adapter<AddUpAdapter.ViewHolder>{
    private JSONArray mData;

    public AddUpAdapter(JSONArray mData) {
        this.mData = mData;
    }

    public void resetData(JSONArray jsonArray){
        mData = jsonArray;
        notifyDataSetChanged();
    }

    public void addMore(JSONArray jsonArray){
        for (int i = 0; i < jsonArray.length(); i++) {
            mData.put(jsonArray.optJSONObject(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public AddUpAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_up_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddUpAdapter.ViewHolder holder, int position) {
        JSONObject jsonObject = mData.optJSONObject(position);
        holder.mDate.setText(jsonObject.optString("income_date"));
        holder.mEarnings.setText(jsonObject.optString("income1"));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mDate;
        TextView mEarnings;
        ViewHolder(View itemView) {
            super(itemView);
            mDate = (TextView) itemView.findViewById(R.id.item_add_date);
            mEarnings = (TextView) itemView.findViewById(R.id.item_add_earnings);
        }
    }
}
