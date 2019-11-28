package com.cs.com.test_app1.first;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.cs.com.test_app1.R;
import com.cs.com.test_app1.second.ScreenZentUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.NO_ID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIvVoice;
    private EditText mEtInputMessage;
    private ImageView mIvMore;
    private LinearLayout mLlPanelContent;
    private LinearLayout mLlRootEmojiPanel;
    private static final String KEY_SOFT_KEYBOARD_HEIGHT = "SoftKeyboardHeight";
    private static final String EMOJIKEYBOARD = "EmojiKeyboard";
    private static final int SOFT_KEYBOARD_HEIGHT_DEFAULT = 654;//首选项不存在的时候,默认的一个值
    private SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerView;
    private InputMethodManager inputMethodManager;//键盘管理器
    private Handler handler = new Handler();
    private List<String> mData = new ArrayList<>();
    private RelativeLayout mRlPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        sharedPreferences = getSharedPreferences(EMOJIKEYBOARD, Context.MODE_PRIVATE);
        mIvVoice = (ImageView) findViewById(R.id.iv_voice);
        mRecyclerView = (RecyclerView) findViewById(R.id.rlv_list);
        mEtInputMessage = (EditText) findViewById(R.id.et_inputMessage);
        mIvMore = (ImageView) findViewById(R.id.iv_more);
        mLlPanelContent = (LinearLayout) findViewById(R.id.ll_panel_content);
        mLlRootEmojiPanel = (LinearLayout) findViewById(R.id.ll_rootEmojiPanel);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < 30; i++) {
            mData.add(i + "");
        }
        TestAdapter adapter = new TestAdapter(R.layout.item_message, mData);
        mRecyclerView.setAdapter(adapter);


        mIvVoice.setOnClickListener(this);

        mEtInputMessage.setOnTouchListener(new View.OnTouchListener() {//todo et可见并触摸,如果表情可见则忽略
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mLlRootEmojiPanel.isShown()) {
                    lockContentViewHeight();
                    hideEmojiPanel(true);
                    unlockContentViewHeight();
                }
                return false;
            }
        });

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {//todo 如果开着表情,则关闭,如果是键盘,关闭
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mLlRootEmojiPanel.isShown()) {
                        hideEmojiPanel(false);
                    } else if (KeyboardUtils.isSoftInputVisible(MainActivity.this)) {
                        hideSoftKeyboard();
                    }
                }
                return false;
            }
        });

        mIvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("MainActivity", "1:" + 1);
                if (mLlRootEmojiPanel.isShown()) {
                    Log.d("MainActivity", "1:" + 1);
                    lockContentViewHeight();
                    hideEmojiPanel(true);
                    unlockContentViewHeight();
                } else {
                    Log.d("MainActivity", "2:" + 1);
                    if (KeyboardUtils.isSoftInputVisible(MainActivity.this)) {
                        lockContentViewHeight();
                        showEmojiPanel();
                        unlockContentViewHeight();
                    } else {
                        showEmojiPanel();
                    }


                }
            }
        });


        /**
         * 如果之前没有保存过键盘高度值
         * 则在进入Activity时自动打开键盘，并把高度值保存下来
         */
        if (!sharedPreferences.contains(KEY_SOFT_KEYBOARD_HEIGHT)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showSoftKeyboard(true);
                }
            }, 200);
        }
        mRlPhoto = (RelativeLayout) findViewById(R.id.rlPhoto);
        mRlPhoto.setOnClickListener(this);
    }


//    /**
//     * 判断是否显示了键盘
//     */
//    private boolean isSoftKeyboardShown() {
//        return getSoftKeyboardHeight() != 0;
//    }

    /**
     * 隐藏键盘
     */
    private void hideSoftKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(mEtInputMessage.getWindowToken(), 0);
    }


    /**
     * 获取本地存储的键盘高度值或者是返回默认值
     */
    private int getSoftKeyboardHeightLocalValue() {
        return sharedPreferences.getInt(KEY_SOFT_KEYBOARD_HEIGHT, SOFT_KEYBOARD_HEIGHT_DEFAULT);
    }

    /**
     * 显示表情面板
     */
    private void showEmojiPanel() {
        int softKeyboardHeight = getSoftKeyboardHeight();
        if (softKeyboardHeight <= 0) {
            softKeyboardHeight = getSoftKeyboardHeightLocalValue();
        } else {
            hideSoftKeyboard();
        }
        mLlRootEmojiPanel.getLayoutParams().height = softKeyboardHeight;
        mLlRootEmojiPanel.setVisibility(View.VISIBLE);

    }

    /**
     * 隐藏表情面板，同时指定是否随后开启键盘
     */
    private void hideEmojiPanel(boolean showSoftKeyboard) {
        if (mLlRootEmojiPanel.isShown()) {
            mLlRootEmojiPanel.setVisibility(View.GONE);
            if (showSoftKeyboard) {
                showSoftKeyboard(false);
            }

        }

    }

    /**
     * 令编辑框获取焦点并显示键盘
     */
    private void showSoftKeyboard(boolean saveSoftKeyboardHeight) {
        mEtInputMessage.requestFocus();//获取焦点后才能让下面方面能用
        inputMethodManager.showSoftInput(mEtInputMessage, 0);
        if (saveSoftKeyboardHeight) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSoftKeyboardHeight();
                }
            }, 200);
        }
    }


    /**
     * 锁定内容View以防止跳闪
     */
    private void lockContentViewHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
        layoutParams.height = mRecyclerView.getHeight();
        layoutParams.weight = 0;
    }

    /**
     * 释放锁定的内容View
     */
    private void unlockContentViewHeight() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) mRecyclerView.getLayoutParams()).weight = 1;
            }
        }, 200);
    }

    @Override
    public void onBackPressed() {
        if (!interceptBackPress()) {
            super.onBackPressed();
        }
    }


    /**
     * 当点击返回键时需要先隐藏表情面板
     */
    public boolean interceptBackPress() {
        if (mLlRootEmojiPanel.isShown()) {
            hideEmojiPanel(false);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_voice:

                if (mLlRootEmojiPanel.isShown()) {
                    hideEmojiPanel(false);
                } else if (KeyboardUtils.isSoftInputVisible(MainActivity.this)) {
                    hideSoftKeyboard();
                }
                break;

            case R.id.rlPhoto:
                Toast.makeText(this, "photo", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    /**
     * 获取键盘的高度
     */
    private int getSoftKeyboardHeight() {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //屏幕当前可见高度，不包括状态栏
        int displayHeight = rect.bottom - rect.top;
        //屏幕可用高度
        int availableHeight = ScreenZentUtils.getAvailableScreenHeight(this);
        //用于计算键盘高度
        int softInputHeight = availableHeight - displayHeight - ScreenZentUtils.getStatusBarHeight(this);

        if (isNavigationBarExist(this)) {
            softInputHeight = availableHeight - displayHeight - ScreenZentUtils.getStatusBarHeight(this);
        } else {
            softInputHeight = availableHeight - displayHeight - ScreenZentUtils.getStatusBarHeight(this) + ScreenZentUtils.getVirtualBarHeight(this);
        }

        Log.e("TAG-di", displayHeight + "");
        Log.e("TAG-av", availableHeight + "");
        Log.e("TAG-so", softInputHeight + "");

        if (softInputHeight > 0) {
            // 因为考虑到用户可能会主动调整键盘高度，所以只能是每次获取到键盘高度时都将其存储起来
            sharedPreferences.edit().putInt(KEY_SOFT_KEYBOARD_HEIGHT, softInputHeight).apply();
        }
        return softInputHeight;
    }


    // 该方法需要在View完全被绘制出来之后调用，否则判断不了
    //在比如 onWindowFocusChanged（）方法中可以得到正确的结果
    //有虚拟键盘,需要加虚拟键盘高度,没有则不加
    public static boolean isNavigationBarExist(@NonNull Activity activity) {
        ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
        if (vp != null) {
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).getContext().getPackageName();
                if (vp.getChildAt(i).getId() != NO_ID && "navigationBarBackground".equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                    return true;
                }
            }
        }
        return false;
    }


}
