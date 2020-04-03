package com.cs.com.test_app1.three;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cs.com.test_app1.R;
import com.cs.com.test_app1.first.TestAdapter;
import com.cs.com.test_app1.util.ChatUiHelper;
import com.cs.com.test_app1.util.LogUtil;
import com.cs.com.test_app1.wodget.IndicatorView;
import com.cs.com.test_app1.wodget.RecordButton;
import com.cs.com.test_app1.wodget.WrapContentHeightViewPager;
import com.deadline.statebutton.StateButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ThreeActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRvChat;
    private ImageView mIvAudio;
    private EditText mEtContent;
    private RecordButton mBtnAudio;
    private ImageView mIvEmo;
    private ImageView mIvAdd;
    private StateButton mBtnSend;
    private LinearLayout mLlContent;
    private WrapContentHeightViewPager mVpEmoji;
    private IndicatorView mIndEmoji;
    private LinearLayout mLlEmotion;
    private ImageView mIvPhoto;
    private RelativeLayout mRlPhoto;
    private ImageView mIvVideo;
    private RelativeLayout mRlVideo;
    private ImageView mIvFile;
    private RelativeLayout mRlFile;
    private ImageView mIvLocation;
    private RelativeLayout mRlLocation;
    private LinearLayout mLlPanelContent;
    private RelativeLayout mRlBottomLayout;
    private LinearLayout mLlAdd;
    private List<String> mData=new ArrayList<>();
    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        initView();
    }

    private void initView() {
        mRvChat = (RecyclerView) findViewById(R.id.rv_chat_list);
        mIvAudio = (ImageView) findViewById(R.id.ivAudio);//录音图片
        mEtContent = (EditText) findViewById(R.id.et_content);
        mBtnAudio = (RecordButton) findViewById(R.id.btnAudio);//录音按钮
        mIvEmo = (ImageView) findViewById(R.id.ivEmo);//笑脸
        mIvAdd = (ImageView) findViewById(R.id.ivAdd);//加号
        mBtnSend = (StateButton) findViewById(R.id.btn_send);//发送按钮
        mLlContent = (LinearLayout) findViewById(R.id.llContent);//是 Listview和输入框布局
        mVpEmoji = (WrapContentHeightViewPager) findViewById(R.id.vp_emoji);//viewpage
        mIndEmoji = (IndicatorView) findViewById(R.id.ind_emoji);//下划线
        mLlEmotion = (LinearLayout) findViewById(R.id.rlEmotion);//表情的布局
        mLlAdd = (LinearLayout) findViewById(R.id.llAdd);//添加布局

        mIvPhoto = (ImageView) findViewById(R.id.ivPhoto);
        mRlPhoto = (RelativeLayout) findViewById(R.id.rlPhoto);
        mIvVideo = (ImageView) findViewById(R.id.ivVideo);
        mRlVideo = (RelativeLayout) findViewById(R.id.rlVideo);
        mIvFile = (ImageView) findViewById(R.id.ivFile);
        mRlFile = (RelativeLayout) findViewById(R.id.rlFile);
        mIvLocation = (ImageView) findViewById(R.id.ivLocation);
        mRlLocation = (RelativeLayout) findViewById(R.id.rlLocation);

        mRlBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);//表情,添加底部布局

        mBtnAudio.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);

        for (int i = 0; i < 30; i++) {
            mData.add(i + "");
        }
        mRvChat.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TestAdapter(R.layout.item_message, mData);
        mRvChat.setAdapter(adapter);
        initChatUi();


    }

    @SuppressLint("ClickableViewAccessibility")
    private void initChatUi() {
        //mBtnAudio
        final ChatUiHelper mUiHelper= ChatUiHelper.with(this);
        mUiHelper.bindContentLayout(mLlContent)
                .bindttToSendButton(mBtnSend)
                .bindEditText(mEtContent)
                .bindBottomLayout(mRlBottomLayout)
                .bindEmojiLayout(mLlEmotion)
                .bindAddLayout(mLlAdd)
                .bindToAddButton(mIvAdd)
                .bindToEmojiButton(mIvEmo)
                .bindAudioBtn(mBtnAudio)
                .bindAudioIv(mIvAudio)
                .bindEmojiData();
        //底部布局弹出,聊天列表上滑
        mRvChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mRvChat.post(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter.getItemCount() > 0) {
                                mRvChat.smoothScrollToPosition(adapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
        //点击空白区域关闭键盘
        mRvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mUiHelper.hideBottomLayout(false);
                mUiHelper.hideSoftInput();
                mEtContent.clearFocus();
                mIvEmo.setImageResource(R.mipmap.ic_emoji);
                return false;
            }
        });
        //
        ((RecordButton) mBtnAudio).setOnFinishedRecordListener(new RecordButton.OnFinishedRecordListener() {
            @Override
            public void onFinishedRecord(String audioPath, int time) {
                LogUtil.d("录音结束回调");
                File file = new File(audioPath);
                if (file.exists()) {
                    //sendAudioMessage(audioPath,time);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAudio:

                break;
            case R.id.btn_send:
                mData.add(mEtContent.getText().toString());
                adapter.notifyDataSetChanged();
                mRvChat.scrollToPosition(adapter.getItemCount() - 1);
                mEtContent.setText("");
                break;
        }
    }






}
