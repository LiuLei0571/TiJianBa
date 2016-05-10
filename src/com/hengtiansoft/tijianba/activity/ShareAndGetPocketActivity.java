
package com.hengtiansoft.tijianba.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengtiansoft.tijianba.net.response.SharedWebInfoResult.SharedWebInfor;
import com.hengtiansoft.tijianba.net.response.SharedWedInfoListener;
import com.juanliuinformation.lvningmeng.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

public class ShareAndGetPocketActivity extends BaseActivity implements OnClickListener {
    private RelativeLayout mRlytBtn;
    private RelativeLayout mRlytInclude;
    private RelativeLayout mRlytIncludeBtn;
    private TextView mTvFriend, mTvWeiXing, mTvContent;
    private String url;
    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_and_get_pocket);
        initWXAPI();
        mTvContent = (TextView) findViewById(R.id.tv_share_and_get);
        mRlytBtn = (RelativeLayout) findViewById(R.id.rlyt_dialog_share);
        mRlytBtn.setOnClickListener(this);
        mRlytInclude = (RelativeLayout) findViewById(R.id.rlyt_dialog_share_all);
        mRlytIncludeBtn = (RelativeLayout) findViewById(R.id.rlyt_dialog_share_btn);
        mRlytIncludeBtn.setOnClickListener(this);
        mTvFriend = (TextView) findViewById(R.id.tv_share_mm);
        mTvWeiXing = (TextView) findViewById(R.id.tv_share_circle);
        mTvFriend.setOnClickListener(this);
        mTvWeiXing.setOnClickListener(this);
        getWebInfo();
    }

    private void getWebInfo() {
        remoteDataManager.sharedWedInfoListener = new SharedWedInfoListener() {

            @Override
            public void onSuccess(SharedWebInfor sharedWebInfor) {
                dismissProgressDialog();
                if (sharedWebInfor != null) {
                    content = sharedWebInfor.getContent();
                    url = sharedWebInfor.getBonusURL();
                    title = sharedWebInfor.getTitle();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mTvContent.setText(content);
                        }
                    });
                } else {
                    handleError("请重新尝试获取信息");
                }
            }

            @Override
            public void onError(String errorCode, String errorMessage) {
                handleError(errorMessage);
            }
        };
        if (validateInternet()) {
            showProgressDialog("分享得红包", "获取信息中");
            remoteDataManager.getSharedWebInfo();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlyt_dialog_share:
                mRlytInclude.setVisibility(View.VISIBLE);
                break;
            case R.id.rlyt_dialog_share_btn:
                mRlytInclude.setVisibility(View.GONE);
                break;
            case R.id.tv_share_mm:
                shareWebToWeChat(0);
                break;
            case R.id.tv_share_circle:
                shareWebToWeChat(1);
                break;
            default:
                break;
        }
    }

    public void shareWebToWeChat(int flag) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = content;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = (flag == 0) ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

}
