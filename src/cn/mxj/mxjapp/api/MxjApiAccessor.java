package cn.mxj.mxjapp.api;

import cn.mxj.mxjapp.Config;
import cn.mxj.mxjapp.model.User;
import com.loopj.android.http.RequestParams;

/**
 * 接口功能 api
 * @author root
 *
 */
public class MxjApiAccessor {
	
	public static User user;
	private static String apiSite = Config.MXJ_SITE;
	
	/**
	 * 验证用户
	 * @return
	 * @
	 */
	public static void verify(String account,String password,MxjAsyncHttpResponseHandler httpResponseHandler){
        String url = apiSite + "/api/verify.do";
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("password", password);
        MxjRequestClient.get(url, params,httpResponseHandler);
    }
	
	/**
	 * 获取验证码
	 * @return
	 * @
	 */
	public static void getCheckCode(String phone,MxjAsyncHttpResponseHandler httpResponseHandler){
        String url = apiSite + "/api/getCheckCode.do";
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        MxjRequestClient.get(url, params,httpResponseHandler);
    }
	
	/**
	 * 用户注册
	 * @return
	 * @
	 */
	public static void register(String phone,String msgCheckCode,String password,MxjAsyncHttpResponseHandler httpResponseHandler){
        String url = apiSite + "/api/register.do";
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("msgCheckCode", msgCheckCode);
        params.put("password", password);
        MxjRequestClient.get(url, params,httpResponseHandler);
    }
	
	/**
	 * 验证码过期
	 * @return
	 * @
	 */
	public static void checkCodeExpired(String phone,String msgCheckCode,MxjAsyncHttpResponseHandler httpResponseHandler){
        String url = apiSite + "/api/checkCodeExpired.do";
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("msgCheckCode", msgCheckCode);
        MxjRequestClient.get(url, params,httpResponseHandler);
    }
	
	/**
	 * 修改密码
	 * @return
	 * @
	 */
	public static void editPassword(String account,String oldPassword,String newPassword,MxjAsyncHttpResponseHandler httpResponseHandler){
        String url = apiSite + "/api/editPassword.do";
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("oldPassword", oldPassword);
        params.put("newPassword", newPassword);
        MxjRequestClient.get(url, params,httpResponseHandler);
    }
}



