package com.hengtiansoft.tijianba.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.hengtiansoft.tijianba.alipay.Result;
import com.hengtiansoft.tijianba.alipay.SignUtils;

/**
 * 
 * com.hengtiansoft.tijianba.activity.PayActivity
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Jan 9, 2015 4:36:46 PM
 */
public class PayActivity extends BaseActivity {
  private String url, orderNo, description;
  private int money;
  // , bonusMoney;
  public static final String PARTNER = "2088711560875893";
  public static final String SELLER = "zhifu@juanliu.net";
  public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK/TveRwoj3gNvGCNOph9rx0zc/WigLIOzYHCk8qMKFd/QAAc5TUIwriOCKc5mbHrApbwXyIYPpiOkuBu8su/a36Qac0WGsy6eywVFG3Cnsu3RqZf88uvR2aNSLPXvR1tglkMyRPdGWwzlikVX8yfxeqzk1s0Jqw3lIX/g7heL6pAgMBAAECgYEAi9TBeCN7Y+okx/zdSGmQaGAcLeZY682CCX2f3UWYVz0AMDgrs3jiRY2TRzOwqJyzXamLPxbWyUnFQrdyOiys4ZZm8a6JVB3MyxVgWtKG3dKhL8TleV5SEZblJ/O4O6QDAXK9dX1BEDkTlwFZRduYObS4m3URnpaiSHiSx3cm+GECQQDVWAD1XurysX+NBUGqBPXz8M3bhK++iJayBqBxyv/FVXrgwyfaHeYmNyLIZ3/zEIdxiWWR2vgsroNic5EHXmzDAkEA0vty+tL62itqan4YuH30Yqdx7HCexXTF4/GwGXD8wz6yoriFiqRuM8WJnAlPbDyEItw5y2nZLEgskX4TFLKgIwJAU4m4JlxaZ1m1dkS+p5J1tGVwViqonlThnSgXHze720tTaDtCmZfnjMM+LXbJlSW9w2lniitRzj0vWRejjF0e/QJAEHxneqP7yAp2cUXTJq9o5kCRTVlrfqBmSi57hTFn/tWqXb6vaKr6lX8NBq1PkHSWGqc8uOFXf/dPzDqewRJIIwJBAMwnxIWclbj33n5KnmlLFRbixdb1C//voP6OhVBj5l2qIyVXTYDSK2lockFbG0bFVMpdPMk/PNrIqeq4uVK0DKk=";
  private static final int SDK_PAY_FLAG = 1;
  private static final int SDK_CHECK_FLAG = 2;

  private Handler mHandler = new Handler() {
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case SDK_PAY_FLAG: {
          Result resultObj = new Result((String) msg.obj);
          String resultStatus = resultObj.resultStatus;

          // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
          if (TextUtils.equals(resultStatus, "9000")) {
            Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PayActivity.this, PaySucceedActivity.class);
            intent.putExtra("orderNo", orderNo);
            intent.putExtra("paySuccess", true);
            startActivity(intent);
            finish();
          } else {
            // 判断resultStatus 为非“9000”则代表可能支付失败
            // “8000”
            // 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
            if (TextUtils.equals(resultStatus, "8000")) {
              Toast.makeText(PayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
              finish();
            } else {
              Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
              finish();
            }

          }
          break;
        }
        case SDK_CHECK_FLAG: {

          if ((Boolean) msg.obj) {
            pay();
          } else {
            Toast.makeText(PayActivity.this, "请安装支付宝", Toast.LENGTH_SHORT).show();
            finish();
          }
          break;
        }
        default:
          break;
      }
    };
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    money = getIntent().getIntExtra("money", 0);
    description = getIntent().getStringExtra("description");
    url = getIntent().getStringExtra("url");
    orderNo = getIntent().getStringExtra("orderNo");
    // bonusMoney = getIntent().getIntExtra("bonusMoney", 0);
    pay();
  }

  
  /**
   * call alipay sdk pay. 调用SDK支付
   * 
   */
  public void pay() {
    String orderInfo = getOrderInfo("绿柠檬-" + description, description, money+"");
    String sign = sign(orderInfo);
    try {
      // 仅需对sign 做URL编码
      sign = URLEncoder.encode(sign, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

    Runnable payRunnable = new Runnable() {

      @Override
      public void run() {
        // 构造PayTask 对象
        PayTask alipay = new PayTask(PayActivity.this);
        // 调用支付接口
        String result = alipay.pay(payInfo);

        Message msg = new Message();
        msg.what = SDK_PAY_FLAG;
        msg.obj = result;
        mHandler.sendMessage(msg);
      }
    };

    Thread payThread = new Thread(payRunnable);
    payThread.start();
  }

  /**
   * check whether the device has authentication alipay account.
   * 查询终端设备是否存在支付宝认证账户
   * 
   */
  public void check() {
    Runnable checkRunnable = new Runnable() {

      @Override
      public void run() {
        PayTask payTask = new PayTask(PayActivity.this);
        boolean isExist = payTask.checkAccountIfExist();

        Message msg = new Message();
        msg.what = SDK_CHECK_FLAG;
        msg.obj = isExist;
        mHandler.sendMessage(msg);
      }
    };

    Thread checkThread = new Thread(checkRunnable);
    checkThread.start();

  }

  /**
   * get the sdk version. 获取SDK版本号
   * 
   */
  public void getSDKVersion() {
    PayTask payTask = new PayTask(this);
    String version = payTask.getVersion();
    Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
  }

  /**
   * create the order info. 创建订单信息
   * 
   */
  public String getOrderInfo(String subject, String body, String price) {
    // 合作者身份ID
    String orderInfo = "partner=" + "\"" + PARTNER + "\"";

    // 卖家支付宝账号
    orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

    // 商户网站唯一订单号
    orderInfo += "&out_trade_no=" + "\"" + orderNo + "\"";

    // 商品名称
    orderInfo += "&subject=" + "\"" + subject + "\"";

    // 商品详情
    orderInfo += "&body=" + "\"" + body + "\"";

    // 商品金额
    orderInfo += "&total_fee=" + "\"" + price + "\"";

    // 服务器异步通知页面路径
    orderInfo += "&notify_url=" + "\"" + url + "\"";

    // 接口名称， 固定值
    orderInfo += "&service=\"mobile.securitypay.pay\"";

    // 支付类型， 固定值
    orderInfo += "&payment_type=\"1\"";

    // 参数编码， 固定值
    orderInfo += "&_input_charset=\"utf-8\"";

    // 设置未付款交易的超时时间
    // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
    // 取值范围：1m～15d。
    // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
    // 该参数数值不接受小数点，如1.5h，可转换为90m。
    orderInfo += "&it_b_pay=\"30m\"";

    // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
    orderInfo += "&return_url=\"m.alipay.com\"";

    // 调用银行卡支付，需配置此参数，参与签名， 固定值
    // orderInfo += "&paymethod=\"expressGateway\"";

    return orderInfo;
  }

  // /**
  // * get the out_trade_no for an order. 获取外部订单号
  // *
  // */
  // public String getOutTradeNo() {
  // SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
  // Locale.getDefault());
  // Date date = new Date();
  // String key = format.format(date);
  //
  // Random r = new Random();
  // key = key + r.nextInt();
  // key = key.substring(0, 15);
  // return key;
  // }

  /**
   * sign the order info. 对订单信息进行签名
   * 
   * @param content
   *          待签名订单信息
   */
  public String sign(String content) {
    return SignUtils.sign(content, RSA_PRIVATE);
  }

  /**
   * get the sign type we use. 获取签名方式
   * 
   */
  public String getSignType() {
    return "sign_type=\"RSA\"";
  }
}
