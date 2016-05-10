package com.hengtiansoft.tijianba.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.hengtiansoft.tijianba.model.RemoteDataManager;
import com.hengtiansoft.tijianba.net.response.Response;

public class HttpClientUtil {
  private final static String TAG = "HttpClientUtil";

  private final static int TIME_OUT = 30000;
  private static String serverURL = "http://115.29.209.135/mobileSns/";

  private static HttpClient httpClient;

  public static synchronized HttpClient getHttpClient(String server) {
    if (null == httpClient) {
      HttpParams params = new BasicHttpParams();

      ConnManagerParams.setMaxTotalConnections(params, 5000);
      ConnPerRoute perRoute = new ConnPerRouteBean(10);
      ConnManagerParams.setMaxConnectionsPerRoute(params, perRoute);

      ConnManagerParams.setTimeout(params, 1000);
      HttpConnectionParams.setConnectionTimeout(params, TIME_OUT);
      HttpConnectionParams.setSoTimeout(params, TIME_OUT);

      SchemeRegistry schReg = new SchemeRegistry();
      schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
      schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 80));

      ClientConnectionManager conManager = new ThreadSafeClientConnManager(params, schReg);

      httpClient = new DefaultHttpClient(conManager, params);
    }

    serverURL = server;
    return httpClient;
  }

  /**
   * Use http get method to visit server, obtain the response.
   * 
   * @param url
   *          web address
   * @return status code and entity the server returns
   */
  public static Response doGet(String server, String path, String authInfo, List<NameValuePair> params) {
    serverURL = server;
    String url = generateUrl(path, params);
    Response response = new Response();
    /*
     * int timeOut = Integer.valueOf(VsFootballProPerties.loadProperties(
     * mContext).getProperty("time_out"));
     */
    int timeOut = TIME_OUT;
    HttpParams httpParams = new BasicHttpParams();
    // set time out parameters
    ConnManagerParams.setMaxTotalConnections(httpParams, 5000);
    ConnPerRoute perRoute = new ConnPerRouteBean(10);
    ConnManagerParams.setMaxConnectionsPerRoute(httpParams, perRoute);
    HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
    HttpConnectionParams.setSoTimeout(httpParams, timeOut);

    DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);

    HttpGet httpGet = new HttpGet(url);
    httpGet.addHeader("Authentication-Token", authInfo);
    httpGet.addHeader("Content-Type", "application/json");
    try {
      HttpResponse httpResponse = getHttpClient(server).execute(httpGet);
      Log.i(TAG + "_responseStatusLine", httpResponse.getStatusLine().toString());
      response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
      if (response.getStatusCode() == HttpStatus.SC_OK) {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
          InputStream instream = entity.getContent();
          response.setContent(convertStreamToString(instream));
          Log.i(TAG + "_responseEntity", response.getContent());
          instream.close();
        }
      }
    } catch (IOException e) {
      Log.e(TAG, "IOException", e);

    }
  
    return response;
  }

  /**
   * @Description: Use http post method to visit server, obtain the response.
   * @param url
   *          web address
   * @param content
   *          post data, use json type
   * @return status code and entity the server returns
   * @throws NetException
   */
  public static Response doPost(String path, List<NameValuePair> params, String authInfo) throws NetException {
    serverURL = RemoteDataManager.SERVICE_NORMAL;
    String url = generateUrl(path, params);
    Response response = new Response();
    /*
     * int timeOut = Integer.valueOf(VsFootballProPerties.loadProperties(
     * mContext).getProperty("time_out"));
     */
    int timeOut = TIME_OUT;
    HttpParams httpParams = new BasicHttpParams();
    // set time out parameters
    ConnManagerParams.setMaxTotalConnections(httpParams, 5000);
    ConnPerRoute perRoute = new ConnPerRouteBean(10);
    ConnManagerParams.setMaxConnectionsPerRoute(httpParams, perRoute);
    HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
    HttpConnectionParams.setSoTimeout(httpParams, timeOut);
    try {
      HttpPost httpPost = new HttpPost(url);
      ArrayList<NameValuePair> paramNews = addParam(authInfo, params);
      httpPost.setEntity(new UrlEncodedFormEntity(paramNews, HTTP.UTF_8));
      Log.d("debug", paramNews.toString());
      HttpClient client = getHttpClient(serverURL);
      HttpResponse httpResponse = client.execute(httpPost);
      Log.i(TAG + "_responseStatusLine", httpResponse.getStatusLine().toString());
      response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
      if (response.getStatusCode() == HttpStatus.SC_OK) {
        HttpEntity entityResponse = httpResponse.getEntity();
        if (entityResponse != null) {
          InputStream instream = entityResponse.getContent();
          response.setContent(convertStreamToString(instream));
          Log.i(TAG + "_responseEntity", response.getContent());
          instream.close();

        }
      } else {
        throw new NetException("Negative response from server. " + httpResponse.getStatusLine().toString());
      }
    } catch (SocketTimeoutException e) {
      throw new NetException(e);
    }catch (IOException e) {
      Log.e(TAG, "IOException", e);
      throw new NetException(e);
    }
    
    return response;
  }

  @SuppressWarnings({ "deprecation" })
  public static Response doImagePost(String server, String path, List<NameValuePair> params, byte[] imageData,
      String imageName, String authInfo) throws NetException {
    serverURL = server;
    String url = generateUrl(path, params);
    Response response = new Response();
    /*
     * int timeOut = Integer.valueOf(VsFootballProPerties.loadProperties(
     * mContext).getProperty("time_out"));
     */
    int timeOut = TIME_OUT;
    HttpParams httpParams = new BasicHttpParams();
    // set time out parameters
    ConnManagerParams.setMaxTotalConnections(httpParams, 5000);
    ConnPerRoute perRoute = new ConnPerRouteBean(10);
    ConnManagerParams.setMaxConnectionsPerRoute(httpParams, perRoute);
    HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
    HttpConnectionParams.setSoTimeout(httpParams, timeOut);

    try {
      HttpPost httpPost = new HttpPost(url);
      ArrayList<NameValuePair> paramNews = addParam(authInfo, params);

      HttpEntity reqEntity;
      if (imageData != null) {
        reqEntity = MultipartEntityBuilder.create().addPart("headImg", new ByteArrayBody(imageData, imageName))
        // .addPart("name", new StringBody(name)).addPart("fileName", new
        // StringBody(imageName))
        // .addPart("mimeType", new StringBody("image/jpg"))
            .addPart("param", new StringBody(paramNews.get(0).getValue(), Charset.forName("UTF-8"))).build();
      } else {
        reqEntity = MultipartEntityBuilder.create()
        // .addPart("name", new StringBody(name)).addPart("fileName", new
        // StringBody(imageName))
        // .addPart("mimeType", new StringBody("image/jpg"))
            .addPart("param", new StringBody(paramNews.get(0).getValue(), Charset.forName("UTF-8"))).build();
      }
      httpPost.setEntity(reqEntity);
      // httpPost.addHeader("Authentication-Token", authInfo);
      // StringEntity stringEntity = new
      // StringEntity(params.get(0).getValue(), HTTP.UTF_8);
      // stringEntity.setContentType("application/json");
      // httpPost.setEntity(stringEntity);

      HttpClient client = getHttpClient(server);
      HttpResponse httpResponse = client.execute(httpPost);
      Log.i(TAG + "_responseStatusLine", httpResponse.getStatusLine().toString());
      response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
      if (response.getStatusCode() == HttpStatus.SC_OK) {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
          InputStream instream = entity.getContent();
          response.setContent(convertStreamToString(instream));
          Log.i(TAG + "_responseEntity", response.getContent());
          instream.close();

        }
      } else {
        throw new NetException("Negative response from server. " + httpResponse.getStatusLine().toString());
      }
    } catch (IOException e) {
      Log.e(TAG, "IOException", e);
      throw new NetException(e);
    }
    return response;
  }

  @SuppressWarnings({ "deprecation" })
  public static Response doReportPost(String server, String path, List<NameValuePair> params,
      ArrayList<byte[]> byteList, String imageName, String authInfo) throws NetException {
    serverURL = server;
    String url = generateUrl(path, params);
    Response response = new Response();
    int timeOut = TIME_OUT;
    HttpParams httpParams = new BasicHttpParams();
    ConnManagerParams.setMaxTotalConnections(httpParams, 10000);
    ConnPerRoute perRoute = new ConnPerRouteBean(10);
    ConnManagerParams.setMaxConnectionsPerRoute(httpParams, perRoute);
    HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
    HttpConnectionParams.setSoTimeout(httpParams, timeOut);
    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    try {
      HttpPost httpPost = new HttpPost(url);
      ArrayList<NameValuePair> paramNews = addParam(authInfo, params);
      HttpEntity reqEntity;
      if (byteList != null && byteList.size() > 0) {
        builder.addPart("param", new StringBody(paramNews.get(0).getValue(), Charset.forName("UTF-8"))).build();
        for (byte[] imageData : byteList) {
          builder.addPart("attachments", new ByteArrayBody(imageData, imageName));
        }
        reqEntity = builder.build();
      } else {
        reqEntity = MultipartEntityBuilder.create()
            .addPart("param", new StringBody(paramNews.get(0).getValue(), Charset.forName("UTF-8"))).build();
      }
      httpPost.setEntity(reqEntity);
      HttpClient client = getHttpClient(server);
      HttpResponse httpResponse = client.execute(httpPost);
      Log.i(TAG + "_responseStatusLine", httpResponse.getStatusLine().toString());
      response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
      if (response.getStatusCode() == HttpStatus.SC_OK) {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
          InputStream instream = entity.getContent();
          response.setContent(convertStreamToString(instream));
          Log.i(TAG + "_responseEntity", response.getContent());
          instream.close();
        }
      } else {
        throw new NetException("Negative response from server. " + httpResponse.getStatusLine().toString());
      }
    } catch (IOException e) {

      Log.e(TAG, "IOException", e);
      throw new NetException(e);
    }
    return response;
  }

  /**
   * @Description: Convert the InputStream to String.
   * @param is
   *          inputStream
   * @return string convert from the inputStream
   */
  private static String convertStreamToString(InputStream is) {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
      is.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  private static String generateUrl(String path, List<NameValuePair> params) {
    /*
     * String url = VsFootballProPerties.loadProperties(mContext).getProperty(
     * "server_url")
     */
    String url = serverURL + path;
    for (NameValuePair nvp : params) {
      String value = "";
      if (nvp.getName() != null && nvp.getValue() == null) {
        value = "";
      } else {
        value = nvp.getValue();
      }
      url = url.replace("{" + nvp.getName() + "}", value);
    }
    Log.d(TAG, "URL:" + url);
    return url;
  }

  private static ArrayList<NameValuePair> addParam(String authInfo, List<NameValuePair> params) {
    ArrayList<NameValuePair> paramNews = new ArrayList<NameValuePair>();
    if (authInfo != null && !authInfo.equals("")) {
      if (params.size() != 0) {
        paramNews.add(new BasicNameValuePair("param", "{" + authInfo
            + params.get(0).getValue().toString().replaceFirst("\\{", ",")));
      } else {
        paramNews.add(new BasicNameValuePair("param", "{" + authInfo + "}"));
      }
    } else {
      if (params.size() != 0) {
        paramNews.add(new BasicNameValuePair("param", params.get(0).getValue()));
      }
    }
    return paramNews;
  }
}
