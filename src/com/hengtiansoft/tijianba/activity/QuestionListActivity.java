package com.hengtiansoft.tijianba.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hengtiansoft.tijianba.adapter.QuestionAdapter;
import com.hengtiansoft.tijianba.adapter.QuestionAdapter.QuestionAdapterListener;
import com.hengtiansoft.tijianba.model.Question;
import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.juanliuinformation.lvningmeng.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 
 * com.hengtiansoft.tijianba.activity.QuestionListActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Dec 11, 2014 3:06:59 PM
 */
public class QuestionListActivity extends BaseActivity implements OnClickListener {
  private QuestionAdapter mQuestionAdapter;
  private ListView mQuestionListView;
  private ArrayList<Question> mQuestions = new ArrayList<Question>();
  private ImageView mUploadImageView;
  private RelativeLayout dialogLayout;
  private Bitmap mBitmap;
  private IWXAPI api;
  private static final int THUMB_SIZE = 150;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_question);
    initWXAPI();
    setView();
  }

  private void setView() {
    dialogLayout = (RelativeLayout) findViewById(R.id.dialog);
    findViewById(R.id.rl_to_get).setOnClickListener(this);
    findViewById(R.id.rl_not_get).setOnClickListener(this);
    findViewById(R.id.btn_share).setOnClickListener(this);
    findViewById(R.id.tv_share_mm).setOnClickListener(this);
    findViewById(R.id.tv_share_circle).setOnClickListener(this);
    mUploadImageView = (ImageView) findViewById(R.id.iv_upload);
    mUploadImageView.setEnabled(false);
    findViewById(R.id.rl_upload).setOnClickListener(this);

    mQuestionListView = (ListView) findViewById(R.id.lv_question);
    mQuestionAdapter = new QuestionAdapter(this, mQuestions);
    mQuestionAdapter.setListener(new QuestionAdapterListener() {

      @Override
      public void onQuestionAdapterListener() {
        mUploadImageView.setImageResource(R.drawable.btn_upload_enable);
        mUploadImageView.setEnabled(true);
      }
    });
    mQuestionListView.setAdapter(mQuestionAdapter);
    mQuestions.clear();
    Question question = new Question();
    question.setTitle("1.正常人的心跳范围是？");
    ArrayList<String> options = new ArrayList<String>();
    options.add("A.");
    options.add("B.");
    options.add("C.");
    question.setOption(options);
    mQuestions.add(question);
    Question question2 = new Question();
    question2.setTitle("1.正常人的体温范围是？");
    question2.setOption(options);
    mQuestions.add(question2);
    mQuestionAdapter.notifyDataSetChanged();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.rl_upload:
        dialogLayout.setVisibility(View.VISIBLE);
        break;
      case R.id.rl_to_get:
        Intent intent = new Intent(this, EveryDaySignInActivity.class);
        intent.putExtra("isFromQuestion", true);
        startActivity(intent);
        break;
      case R.id.rl_not_get:
        dialogLayout.setVisibility(View.GONE);
        break;
      case R.id.btn_share:
        mBitmap = takeScreenShot(QuestionListActivity.this);
        if (addSignatureToGallery(mBitmap)) {
          dialogLayout.setVisibility(View.GONE);
          findViewById(R.id.ll_share).setVisibility(View.VISIBLE);
        }
        break;
      case R.id.tv_share_circle:
        shareToWeChat(mBitmap, 1);
        break;
      case R.id.tv_share_mm:
        shareToWeChat(mBitmap, 0);
        break;
      default:
        break;
    }
  }

  public void initWXAPI() {
    api = WXAPIFactory.createWXAPI(this, RemoteDataManager.APP_ID, false);
    api.registerApp(RemoteDataManager.APP_ID);
    api = WXAPIFactory.createWXAPI(QuestionListActivity.this, RemoteDataManager.APP_ID, false);
    boolean isInstalledWeibo = api.isWXAppInstalled();

    if (!isInstalledWeibo) {
      Toast.makeText(QuestionListActivity.this, "未安装微信客户端", Toast.LENGTH_SHORT).show();
    }
    // 注册到微信
    api.registerApp(RemoteDataManager.APP_ID);
    // sendToWx();
  }

  public void shareToWeChat(Bitmap bitmap, int flag) {
    // WXImageObject imgObj = new WXImageObject(bitmap);
    // WXMediaMessage msg = new WXMediaMessage();
    // msg.mediaObject = imgObj;
    // Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE,
    // THUMB_SIZE, true);
    // bitmap.recycle();
    // msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
    // SendMessageToWX.Req req = new SendMessageToWX.Req();
    // req.transaction = buildTransaction("img");
    // req.message = msg;
    // req.scene = (flag == 0) ? SendMessageToWX.Req.WXSceneTimeline :
    // SendMessageToWX.Req.WXSceneSession;
    // api.sendReq(req);
    WXWebpageObject webpage = new WXWebpageObject();
    webpage.webpageUrl = "http://172.16.129.124/cms/mobile/shareBonus/index";
    WXMediaMessage msg = new WXMediaMessage(webpage);
    msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
    msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";

    SendMessageToWX.Req req = new SendMessageToWX.Req();
    req.transaction = buildTransaction("webpage");
    req.message = msg;
    req.scene = (flag == 0) ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
    api.sendReq(req);
  }

}
