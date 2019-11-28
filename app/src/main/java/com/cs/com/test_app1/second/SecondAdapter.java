package com.cs.com.test_app1.second;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cs.com.test_app1.R;

import java.util.List;

public class SecondAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public SecondAdapter(int layoutResId, @Nullable List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
       /* String catalog = item.getFirstLetter();

        TextView catalogView = helper.getView(R.id.catalog);
        if (helper.getAdapterPosition()==getPositionForSection(catalog)) {
            catalogView.setVisibility(View.VISIBLE);
            catalogView.setText(item.getFirstLetter().toUpperCase());
        }else {
            catalogView.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.name,item.getName());*/
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        TextView catalogView = holder.getView(R.id.tv_index);
        //根据position获取首字母作为目录catalog
        User user = mData.get(position);
        String catalog = user.getFirstLetter();
//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(catalog)) {
            catalogView.setVisibility(View.VISIBLE);
            catalogView.setText(user.getFirstLetter().toUpperCase());
        } else {
            catalogView.setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_name, mData.get(position).getName());
        holder.addOnClickListener(R.id.rl_layout);
    }

    /**
     * 获取catalog首次出现位置
     */
    public int getPositionForSection(String catalog) {
        for (int i = 0; i < mData.size(); i++) {
            String sortStr = mData.get(i).getFirstLetter();
            if (catalog.equalsIgnoreCase(sortStr)) {
                return i;
            }
        }
        return -1;
    }
}
