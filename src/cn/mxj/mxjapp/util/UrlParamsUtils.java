package cn.mxj.mxjapp.util;

import android.os.Bundle;

public class UrlParamsUtils {
    //获取请求参数
    public static Bundle getRequestParams(String outUrl,Bundle bundle) {
        if (outUrl == null || outUrl.trim().length() == 0 || outUrl.indexOf("?") < 0) {
            return bundle;
        }
        
        outUrl = outUrl.substring(outUrl.indexOf("?") + 1);
        String[] params = outUrl.split("&");
        
		for(String param : params){
			if(param.indexOf("=") <= 0){
				continue;
			}else{
				String[] paramArray = param.split("=");
				bundle.putString(paramArray[0], paramArray[1]);
			}
		}
        return bundle;
    }
    
    
    
    public static String appendRequestParam(String url, String name , String value) {
		if(url.indexOf("?") == -1){
			url += "?";
		}
		if(url.endsWith("&")){
			url = url + name + "=" + value;
		}else{
			url = url + "&" + name + "=" + value;
		}
		return url;
	}
}
