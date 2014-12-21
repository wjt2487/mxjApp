package cn.mxj.mxjapp.ui;

import org.json.JSONObject;

import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import cn.mxj.mxjapp.R;
import cn.mxj.mxjapp.api.MxjApiAccessor;
import cn.mxj.mxjapp.api.MxjAsyncHttpResponseHandler;
import cn.mxj.mxjapp.api.MxjOperationResult;
import cn.mxj.mxjapp.api.MxjPreference;
import cn.mxj.mxjapp.api.LocalAccessor;
import cn.mxj.mxjapp.model.User;
import cn.mxj.mxjapp.util.JsonUtils;
import cn.mxj.mxjapp.util.NetworkUtil;
import cn.mxj.mxjapp.util.StringUtils;
import cn.mxj.mxjapp.util.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener{

	/**
	 * 登录按钮
	 */
	private Button loginButton;
	/**
	 *注册按钮
	 */
	private Button registerButton;
	/**
	 * 用户名输入框
	 */
	private EditText accountInput;
	/**
	 * 密码输入框
	 */
	private EditText passwordInput;
	/**
	 * 验证码输入框
	 */
	private EditText checkCodeInput;
	/**
	 * 验证码
	 */
	private String checkCode;
	/**
	 * 生成验证码
	 */
	private String createCheckCode;
	
	/**
	 * 生成验证码
	 */
	private TextView checkCodeTextView;
	/**
	 * 用户名
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户信息对象
	 */
	private User user = new User();
	/**
	 * QQ登录
	 */
	private Button qQButton;
	public static QQAuth mQQAuth;
	private Tencent mTencent;
	
	/**
	 * 当前时间
	 */
	private long exitTime = 0;
	/**
	 * QQ登录的测试ID为222222
	 */
	public static String mAppid = "222222";
	/**
	 * 提示信息组件
	 */
	private ProgressDialog progressDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//获取用户登录号
        String currentUserLogname = MxjPreference.getInstance(this).getString("currentUserLogname", "");
        if(!currentUserLogname.equals("")){
        	//获取用户
        	user = new LocalAccessor(this).getUser(currentUserLogname);
        }
        
		loginButton = (Button) findViewById(R.id.login_button);
		registerButton = (Button) findViewById(R.id.register_button);
		qQButton = (Button) findViewById(R.id.qq_button);
		accountInput = (EditText) findViewById(R.id.account_edit);
		passwordInput = (EditText) findViewById(R.id.password_edit);
		checkCodeInput = (EditText) findViewById(R.id.check_code_edit);
		checkCodeTextView = (TextView) findViewById(R.id.check_code_text);
		
		//注册监听事件   
		loginButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
		qQButton.setOnClickListener(this);
		
		final Context context = Login.this;
		final Context ctxContext = context.getApplicationContext();
		mQQAuth = QQAuth.createInstance(mAppid, ctxContext);
		mTencent = Tencent.createInstance(mAppid, Login.this);
		
		//自动填写用户名和密码
		if (StringUtils.isNotEmpty(user.getAccount()) && StringUtils.isNotEmpty(user.getPassword())) {
        	accountInput.setText(user.getAccount());
       	 	passwordInput.setText(user.getPassword());
       	 	//自动登录
       	 	autoLogin();
        }else{
        	createCheckCode();
        }
	}
	@Override
	public void onClick(View v) {
		Context context = v.getContext();
		Animation shake = AnimationUtils.loadAnimation(context,
				R.anim.shake);
		switch (v.getId()) {
		case R.id.login_button:
			login();
			break;
		case R.id.register_button:
			register();
			break;
		case R.id.qq_button:
			onClickLogin();
			v.startAnimation(shake);
			break;
		default:
			break;
		}
	}
	/**
	 * @author wlb
	 * QQ 登录
	 */
	private void onClickLogin() {
		if (!mQQAuth.isSessionValid()) {
			IUiListener listener = new BaseUiListener() {
				@Override
				protected void doComplete(JSONObject values) {
				}
			};
			mTencent.loginWithOEM(this, "all", listener,"10000144","10000144","xxxx");
		} else {
			mQQAuth.logout(this);
		}
	}
	/**
	 * @author wlb
	 * 实现回调 IUiListener
	 */
	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			Intent intent = new Intent(Login.this,MainActivity.class);
			Bundle bundle = new Bundle();
            bundle.putString("account", "");
            bundle.putString("password", "");
            intent.putExtras(bundle);
			startActivity(intent);
			//销毁activity
			finish();
			doComplete((JSONObject)response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Util.toastMessage(Login.this, "错误信息:" + e.errorDetail);
			Util.dismissDialog();
		}

		@Override
		public void onCancel() {
			Util.toastMessage(Login.this, "取消QQ登录");
			Util.dismissDialog();
		}
	}
	/**
	 * @author wlb
	 * 验证码判断
	 */
	private boolean checkCode(){
		checkCode = checkCodeInput.getText().toString();
		if(checkCode.equals(createCheckCode)){
			return true;
		}else{
			new AlertDialog.Builder(Login.this)
            .setMessage("验证码不正确!")
            .setCancelable(false)
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            	@Override
            	public void onClick(DialogInterface dialog, int which) {
            		createCheckCode();
            	}
            }).show(); 
		return false;
		}
	}
	/**
	 * @author wlb
	 * 生成验证码
	 */
	private void createCheckCode(){
		createCheckCode = (int)(Math.random()*9000+1000)+"";
		checkCodeTextView.setText(createCheckCode);
	}
	/**
	 * @author wlb
	 * 自动登录
	 */
	private void autoLogin(){
		account = accountInput.getText().toString().trim();
        password = passwordInput.getText().toString().trim();
      //判断是否有网络
      	if(NetworkUtil.isNetworkAvailable(Login.this) || NetworkUtil.ping()){
      		if(validate()){
      			verify();
      		}
        }else{
            Toast.makeText(Login.this, "当前网络不可用，请检查网络连接！", Toast.LENGTH_SHORT).show();
       }
	}
	/**
	 * @author wlb
	 * 点击登录
	 */
	private void login(){
		account = accountInput.getText().toString().trim();
        password = passwordInput.getText().toString().trim();
      //判断是否有网络
      	if(NetworkUtil.isNetworkAvailable(Login.this) || NetworkUtil.ping()){
      		if(validate()){
      			if(checkCode()){
      				verify();
      			}
      		}
        }else{
            Toast.makeText(Login.this, "当前网络不可用，请检查网络连接！", Toast.LENGTH_SHORT).show();
       }
	}
	/**
	 * @author wlb
	 * 对用户输入的用户名、密码进行校验
	 */
 	private boolean validate(){
        if (account.length() == 0) {
            new AlertDialog.Builder(Login.this)
            .setMessage("用户名不能为空!")
            .setCancelable(false)
            .setPositiveButton("确定", null)
             .show();  
            return false;
        }
        if (password.length() == 0) {
            new AlertDialog.Builder(Login.this)
            .setMessage("密码不能为空!")
            .setCancelable(false)
            .setPositiveButton("确定", null)
            .show();  
            return false;
        }
 		return true;
 	}
 	/**
	 * @author wlb
	 * 数据验证
	 */
 	private void verify(){
 		progressDialog = getProgressDialog("请稍等...","数据加载中...");
		progressDialog.show();
 		MxjApiAccessor.verify(account,password,new MxjAsyncHttpResponseHandler(Login.this){
 			@Override
            public void onFinish() {
 				progressDialog.dismiss();
            };
            @Override
            public void doSuccess(MxjOperationResult mor) {
				User userInfo = JsonUtils.getObjectJSON(mor.getStrValue(),User.class);
				user.setAccount(userInfo.getAccount());
				user.setPassword(password);
				user.setSecret(userInfo.getSecret());
				user.setStatus(userInfo.getStatus());
				user.setIsActive(userInfo.getIsActive());
				user.setType(userInfo.getType());
				user.setName(userInfo.getName());
				user.setAge(userInfo.getAge());
				user.setSex(userInfo.getSex());
				user.setNativePlace(userInfo.getNativePlace());
				user.setBirthday(userInfo.getBirthday());
				user.setIsMarried(userInfo.getIsMarried());
				user.setIsDelete(userInfo.getIsDelete());
				user.setRemark(userInfo.getRemark());
				//保存用户登录号
				MxjPreference.getInstance(Login.this).putStringAndCommit("currentUserLogname", user.getAccount());
				//更新用户信息
				new LocalAccessor(Login.this).updateUser(user);  
				Intent intent = new Intent(Login.this,MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("account", user.getAccount());
				bundle.putString("password", user.getPassword());
				intent.putExtras(bundle);
   			   	startActivity(intent);
   			   	//销毁activity
   			   	finish();
			 }
        });
 	}
	/**
	 * @author wlb
	 * 注册
	 */
	private void register(){
		//判断是否有网络
		if(NetworkUtil.isNetworkAvailable(Login.this) || NetworkUtil.ping()){
        	Intent intent = new Intent(Login.this,RegisterActivity.class);
			startActivity(intent);
			//销毁activity
			finish();
        }else{
        	Toast.makeText(Login.this, "当前网络不可用，请检查网络连接！", Toast.LENGTH_SHORT).show();
        }
	}
	
	//重新系统返回键
    @Override   
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
    	if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
    		//退出程序
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);  
    } 
    
    //获得响应返回键进度条
	public ProgressDialog getProgressDialog(String title, String msg) {
		ProgressDialog progressDialog = new ProgressDialog(Login.this);
		progressDialog.setIndeterminate(true);
		progressDialog.setTitle(title);
		progressDialog.setMessage(msg);
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);
		return progressDialog;
	}
}
