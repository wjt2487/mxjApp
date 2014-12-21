package cn.mxj.mxjapp.api;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MxjRequestClient {
    private static AsyncHttpClient client = new AsyncHttpClient();
    
    private static AsyncHttpResponseHandler defaultAsyncHttpResponseHandler = new AsyncHttpResponseHandler() {
        public void onFailure(Throwable result) {
        };
        public void onFinish() {
        };
        public void onSuccess(String result) { 
        };
    };
    
    private static JsonHttpResponseHandler defaultJsonHttpResponseHandler = new JsonHttpResponseHandler() {
        public void onFailure(Throwable result) {
        };
        public void onFinish() {
        };
        public void onSuccess(JSONArray result) { 
        };
        public void onSuccess(JSONObject result) { 
        };
    };
    
    static{
        client.setTimeout(30000);   //设置链接超时，如果不设置，默认为10s
    }

    //用一个完整url获取一个string对象
    public static void get(String url,AsyncHttpResponseHandler responseHandler) {
    	if(responseHandler == null){
    		responseHandler = defaultAsyncHttpResponseHandler;
    	}
        client.get(url, responseHandler);
    }
    
    //url里面带参数
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    	if(responseHandler == null){
    		responseHandler = defaultAsyncHttpResponseHandler;
    	}
        client.get(url, params, responseHandler);
    }
    
    //不带参数，获取json对象或者数组
    public static void get(String url,JsonHttpResponseHandler responseHandler) {
    	if(responseHandler == null){
    		responseHandler = defaultJsonHttpResponseHandler;
    	}
        client.get(url, responseHandler);
    }
    
    //带参数，获取json对象或者数组
    public static void get(String url,RequestParams params,JsonHttpResponseHandler responseHandler) {
    	if(responseHandler == null){
    		responseHandler = defaultJsonHttpResponseHandler;
    	}
        client.get(url, params,responseHandler);
    }
    
    //下载数据使用，会返回byte数据
    public static void get(String url, BinaryHttpResponseHandler bHandler) {
        client.get(url, bHandler);
    }
    
    //post请求
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    	if(responseHandler == null){
    		responseHandler = defaultAsyncHttpResponseHandler;
    	}
        client.post(url, params, responseHandler);
    }
    
    //返回AsyncHttpClient
    public static AsyncHttpClient getClient(){
        return client;
    }
}
