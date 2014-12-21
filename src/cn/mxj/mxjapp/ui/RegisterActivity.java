package cn.mxj.mxjapp.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cn.mxj.mxjapp.R;
import cn.mxj.mxjapp.api.LocalAccessor;
import cn.mxj.mxjapp.api.MxjApiAccessor;
import cn.mxj.mxjapp.api.MxjAsyncHttpResponseHandler;
import cn.mxj.mxjapp.api.MxjOperationResult;
import cn.mxj.mxjapp.api.MxjPreference;
import cn.mxj.mxjapp.model.User;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class RegisterActivity extends Activity implements android.view.View.OnClickListener{
	public static final int REGION_SELECT = 1;
	/**
	 * 修改
	 */
	private TextView tv_region_modify;
	/**
	 * 手机号所在地信息
	 */
	private TextView tv_region_show;
	/**
	 * 头信息
	 */
	private TextView tv_top_title;
	/**
	 * 返回
	 */
	private Button btn_title_left;
	/**
	 * 获取验证码
	 */
	private Button btn_send_code;
	/**
	 * 注册按钮
	 */
	private Button btn_register;
	/**
	 * 手机号输入框
	 */
	private EditText et_mobileNo;
	/**
	 * 手机号
	 */
	private String phone = "";
	/**
	 * 密码输入框
	 */
	private EditText passwordText;
	/**
	 * 确认密码输入框
	 */
	private EditText confirmPasswordText;
	/**
	 * 密码
	 */
	private String password = "";
	/**
	 * 确认密码
	 */
	private String confirmPassword = "";
	/**
	 * 验证码输入框
	 */
	private EditText checkCodeText;
	/**
	 * 验证码
	 */
	private String msgCheckCode = "";
	/**
	 * 验证码有效时间
	 */
	private int second = 60;
	/**
	 * 用户信息对象
	 */
	private User user = new User();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		initView();
		
	}
	/**
	 * @author wlb
	 * 初始化组件
	 */
	private void initView(){
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_title.setText("******注册-手机验证");
		
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_left.setOnClickListener(this);
		
		btn_send_code = (Button) findViewById(R.id.btn_send_code);
		btn_send_code.setOnClickListener(this);
		
		tv_region_show = (TextView) findViewById(R.id.tv_region_show);
		
		tv_region_modify = (TextView) findViewById(R.id.tv_region_modify);
		tv_region_modify.setOnClickListener(this);
		
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(this);
		
		et_mobileNo = (EditText) findViewById(R.id.et_mobileNo);
		passwordText = (EditText) findViewById(R.id.et_password);
		confirmPasswordText = (EditText) findViewById(R.id.et_confirm_password);
		checkCodeText = (EditText) findViewById(R.id.check_code);
		
		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tv_region_modify:
			showDialog(REGION_SELECT);//显示列表对话框的方法
			break;
		case R.id.btn_title_left:
			setBack();
			break;
		case R.id.btn_send_code:
			getCheckCode();
			break;
		case R.id.btn_register:
			register();
			break;
		default:
			break;
		}
	}
	/**
	 * @author wlb
	 * 验证手机格式
	 */
	private boolean checkPhone(){
		boolean b = false;
		msgCheckCode = checkCodeText.getText().toString().trim();
		password = passwordText.getText().toString().trim();
		confirmPassword = confirmPasswordText.getText().toString().trim();
 		phone = et_mobileNo.getText().toString().trim();
		Pattern pattern = null;
		Matcher matcher = null;
		pattern = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
		matcher = pattern.matcher(phone);
		b =  matcher.matches();
		if(!b){
			new AlertDialog.Builder(RegisterActivity.this)
            .setMessage("手机号填写不正确!")
            .setCancelable(false)
            .setPositiveButton("确定", null)
            .show();  
		}
		return b;
	}
	/**
	 * @author wlb
	 * 注册
	 */
	private void register(){
		if(checkPhone()){
			if(validate()){
				MxjApiAccessor.register(phone,msgCheckCode,password,new MxjAsyncHttpResponseHandler(RegisterActivity.this){
					@Override
					public void onFinish() {
					};
					@Override
					public void doSuccess(MxjOperationResult mor) {
						//保存用户登录号
			            MxjPreference.getInstance(RegisterActivity.this).putStringAndCommit("currentUserLogname", phone);
			            //更新用户信息
			            user.setAccount(phone);
			            user.setPassword(password);
			            new LocalAccessor(RegisterActivity.this).updateUser(user);  
						Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
						Bundle bundle = new Bundle();
				        bundle.putString("account", user.getAccount());
				        bundle.putString("password", user.getPassword());
				        intent.putExtras(bundle);
						startActivity(intent);
						finish();
					}
				});
			}
		}
	}
	/**
	 * @author wlb
	 * 手机号和验证码进行校验
	 */
 	private boolean validate(){
        if (phone.trim().length() == 0) {
            new AlertDialog.Builder(RegisterActivity.this)
            .setMessage("手机号不能为空!")
            .setCancelable(false)
            .setPositiveButton("确定", null)
            .show();  
            return false;
        }
        if (password.trim().length() < 6) {
            new AlertDialog.Builder(RegisterActivity.this)
            .setMessage("密码至少六位字符!")
            .setCancelable(false)
            .setPositiveButton("确定", null)
            .show();  
            return false;
        }
        if (!confirmPassword.trim().equals(password.trim())) {
            new AlertDialog.Builder(RegisterActivity.this)
            .setMessage("两次密码输入不相同!")
            .setCancelable(false)
            .setPositiveButton("确定", null)
            .show();  
            return false;
        }
        if (msgCheckCode.trim().length() == 0) {
            new AlertDialog.Builder(RegisterActivity.this)
            .setMessage("验证码不正确!")
            .setCancelable(false)
            .setPositiveButton("确定", null)
            .show();  
            return false;
        }
 		return true;
 	}
	
	/**
	 * @author wlb
	 * 获取验证码
	 */
	private void getCheckCode(){
		if(!checkPhone()){
			return;
		}
		MxjApiAccessor.getCheckCode(phone,new MxjAsyncHttpResponseHandler(RegisterActivity.this){
			@Override
			public void onFinish() {
            };
            @Override
            public void doSuccess(MxjOperationResult mor) {
            	btn_send_code.setEnabled(false);
            	msgCheckCode = mor.getStrValue();
            	second = 60;
            	new Thread(downloadImg).start();
			}
        });
	}
	/**
	 * @author wlb
	 * 倒计时
	 */
	Runnable downloadImg = new Runnable(){

		public void run() {
			// TODO Auto-generated method stub
			try {
				while(second > 0){
					Thread.sleep(1000);
					second--;
					timeHandler.sendEmptyMessage(0);
				}
				if(second == 0){
					handler.sendEmptyMessage(0);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};
	/**
	 * @author wlb
	 * 主线线程更新页面显示
	 */
	@SuppressLint("HandlerLeak")
	private Handler timeHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			btn_send_code.setText("剩余"+second+"秒");
		}
	};
	
	/**
	 * @author wlb
	 * 主线线程处理
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			btn_send_code.setText("获取验证码");
			btn_send_code.setEnabled(true);
			checkCodeExpired();
		}
	};
	/**
	 * @author wlb
	 * 验证码过期
	 */
	private void checkCodeExpired(){
		MxjApiAccessor.checkCodeExpired(phone,msgCheckCode,new MxjAsyncHttpResponseHandler(RegisterActivity.this){
        });
	}
	
	/**
	 * @author wlb
	 * 系统返回处理
	 */
	public void setBack(){
		Intent intent = new Intent(RegisterActivity.this, Login.class);
		startActivity(intent);
		RegisterActivity.this.finish();
	}
	/**
	 * @author wlb
	 * 重写了系统返回键
	 */
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
        	setBack();
        }
        return super.onKeyDown(keyCode, event);
    }
	/**
	 * @author wlb
	 * 重写了onCreateDialog方法来创建一个列表对话框
	 */
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		// TODO Auto-generated method stub
		switch(id){
		case REGION_SELECT:
			final Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("请选择所在地");
			builder.setSingleChoiceItems(//第一个参数是要显示的列表，用数组展示；第二个参数是默认选中的项的位置；
					//第三个参数是一个事件点击监听器
					new String[]{"+86中国大陆","+853中国澳门","+852中国香港","+886中国台湾"},0, 
					new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							switch(which){
							case 0:
								tv_region_show.setText("+86中国大陆");
								break;
							case 1:
								tv_region_show.setText("+853中国澳门");
								break;
							case 2:
								tv_region_show.setText("+852中国香港");
								break;
							case 3:
								tv_region_show.setText("+886中国台湾");
								break;
							}
							dismissDialog(REGION_SELECT);//想对话框关闭
						}
					});
			return builder.create();
		}
		return null;
	}
}
