package cn.mxj.mxjapp.ui;

import cn.mxj.mxjapp.R;
import cn.mxj.mxjapp.api.LocalAccessor;
import cn.mxj.mxjapp.api.MxjApiAccessor;
import cn.mxj.mxjapp.api.MxjAsyncHttpResponseHandler;
import cn.mxj.mxjapp.api.MxjOperationResult;
import cn.mxj.mxjapp.api.MxjPreference;
import cn.mxj.mxjapp.model.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditPasswordActivity extends Activity implements OnClickListener{
	/**
	 * 用户名
	 */
	private String account = "";
	/**
	 * 密码
	 */
	private String password = "";
	/**
	 * 原密码输入框
	 */
	private EditText oldPasswordEdit;
	/**
	 * 原密码
	 */
	private String oldPassword;
	/**
	 * 新密码输入框
	 */
	private EditText newPasswordEdit;
	/**
	 * 新密码
	 */
	private String newPassword;
	/**
	 * 确认密码输入框
	 */
	private EditText newConfirmPasswordEdit;
	/**
	 * 确认密码
	 */
	private String newConfirmPassword;
	/**
	 * 确定按钮
	 */
	private Button btnConfirmPassword;
	/**
	 * 用户信息对象
	 */
	private User user = new User();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_password);
		
		Intent intent = getIntent();
		Bundle bundle = (Bundle) intent.getExtras();
		account = bundle.getString("account");
		password = bundle.getString("password");
		
		oldPasswordEdit = (EditText) findViewById(R.id.et_old_password);
		newPasswordEdit = (EditText) findViewById(R.id.et_new_password);
		newConfirmPasswordEdit = (EditText) findViewById(R.id.et_new_confirm_password);
		
		btnConfirmPassword = (Button) findViewById(R.id.btn_confirm_password);
		btnConfirmPassword.setOnClickListener(this);
		
		//获取用户登录号
        String currentUserLogname = MxjPreference.getInstance(this).getString("currentUserLogname", "");
        if(!currentUserLogname.equals("")){
        	//获取用户
        	user = new LocalAccessor(this).getUser(currentUserLogname);
        }
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm_password:
			editPassword();
			break;

		default:
			break;
		}
	}
	
	/**
	 * @author wlb
	 * 修改密码
	 */
	private void editPassword(){
		if(!validate()){
			return;
		}
		MxjApiAccessor.editPassword(account,oldPassword,newPassword,new MxjAsyncHttpResponseHandler(EditPasswordActivity.this){
			@Override
            public void onFinish() {
            };
			@Override
			public void doSuccess(MxjOperationResult mor) {
				user.setAccount(user.getAccount());
				user.setPassword(newPassword);
				user.setSecret(user.getSecret());
				user.setName(user.getName());
				user.setStatus(user.getStatus());
				user.setType(user.getType());
				user.setIsActive(user.getIsActive());
				//保存用户登录号
				MxjPreference.getInstance(EditPasswordActivity.this).putStringAndCommit("currentUserLogname", user.getAccount());
				//更新用户信息
				new LocalAccessor(EditPasswordActivity.this).updateUser(user);
				
				Intent intent = new Intent(EditPasswordActivity.this,MainActivity.class);
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
	
	private boolean validate(){
		oldPassword = oldPasswordEdit.getText().toString().trim();
		newPassword = newPasswordEdit.getText().toString().trim();
		newConfirmPassword = newConfirmPasswordEdit.getText().toString().trim();
		
		if(!oldPassword.equals(password)){
			new AlertDialog.Builder(EditPasswordActivity.this)
            .setMessage("原密码输入不正确!")
            .setCancelable(false)
            .setPositiveButton("确定", null)
            .show();  
            return false;
		}
		if(newPassword.length() < 6){
			new AlertDialog.Builder(EditPasswordActivity.this)
            .setMessage("密码至少六位字符!")
            .setCancelable(false)
            .setPositiveButton("确定", null)
            .show();  
            return false;
		}
		if (!newConfirmPassword.equals(newPassword)) {
			new AlertDialog.Builder(EditPasswordActivity.this)
            .setMessage("两次密码输入不相同!")
            .setCancelable(false)
            .setPositiveButton("确定", null)
            .show();  
            return false;
		}
		return true;
	}
}
