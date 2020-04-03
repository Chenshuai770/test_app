package com.cs.com.test_app1.second;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cs.com.test_app1.R;
import com.gjiazhe.wavesidebar.WaveSideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecondActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private List<User> mData=new ArrayList<>();
    private SecondAdapter adapter;
    private WaveSideBar sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        initData();
    }

    private void initData() {

      
        mData.add(new User("亳州")); // 亳[bó]属于不常见的二级汉字
        mData.add(new User("大娃"));
        mData.add(new User("二娃"));
        mData.add(new User("三娃"));
        mData.add(new User("四娃"));
        mData.add(new User("五娃"));
        mData.add(new User("六娃"));
        mData.add(new User("七娃"));
        mData.add(new User("喜羊羊"));
        mData.add(new User("美羊羊"));
        mData.add(new User("懒羊羊"));
        mData.add(new User("沸羊羊"));
        mData.add(new User("暖羊羊"));
        mData.add(new User("慢羊羊"));
        mData.add(new User("灰太狼"));
        mData.add(new User("红太狼"));
        mData.add(new User("孙悟空"));
        mData.add(new User("黑猫警长"));
        mData.add(new User("舒克"));
        mData.add(new User("贝塔"));
        mData.add(new User("海尔"));
        mData.add(new User("阿凡提"));
        mData.add(new User("邋遢大王"));
        mData.add(new User("哪吒"));
        mData.add(new User("没头脑"));
        mData.add(new User("不高兴"));
        mData.add(new User("蓝皮鼠"));
        mData.add(new User("大脸猫"));
        mData.add(new User("大头儿子"));
        mData.add(new User("小头爸爸"));
        mData.add(new User("蓝猫"));
        mData.add(new User("淘气"));
        mData.add(new User("叶峰"));
        mData.add(new User("楚天歌"));
        mData.add(new User("江流儿"));
        mData.add(new User("Tom"));
        mData.add(new User("Jerry"));
        mData.add(new User("12345"));
        mData.add(new User("54321"));
        mData.add(new User("_(:з」∠)_"));
        mData.add(new User("……%￥#￥%#"));

        Collections.sort(mData); // 对mData进行排序，需要让User实现Comparable接口重写compareTo方法

        adapter.notifyDataSetChanged();


    }



    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.rlv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SecondAdapter(R.layout.item_contacts,mData);
        mRecyclerView.setAdapter(adapter);

        sideBar = (WaveSideBar) findViewById(R.id.side_bar);
        sideBar.setIndexItems("A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "#");

        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < mData.size(); i++) {
                    if (index.equalsIgnoreCase(mData.get(i).getFirstLetter())) {
                        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(SecondActivity.this, mData.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
