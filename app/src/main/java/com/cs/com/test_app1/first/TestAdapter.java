package com.cs.com.test_app1.first;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cs.com.test_app1.R;

import java.util.List;

/**
 * Create by Chenshuai
 * Date 2019/11/19/019 17:32
 * Descripton
 */
public class TestAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public TestAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_message,item);

    }
}
