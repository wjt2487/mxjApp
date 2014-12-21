package cn.mxj.mxjapp.ui;

import cn.mxj.mxjapp.R;
import cn.mxj.mxjapp.api.MxjPreference;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	/**
	 * 修改密码按钮
	 */
	private Button editPasswordButton;
	/**
	 * 用户名
	 */
	private String account = "";
	/**
	 * 密码
	 */
	private String password = "";
	/**
	 * 个人中心按钮
	 */
	private Button personalCenterButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editPasswordButton = (Button) findViewById(R.id.btn_edit_password);
		editPasswordButton.setOnClickListener(this);
		personalCenterButton = (Button) findViewById(R.id.btn_personal_center);
		personalCenterButton.setOnClickListener(this);
		
		Intent intent = getIntent();
		Bundle bundle = (Bundle) intent.getExtras();
		account = bundle.getString("account");
		password = bundle.getString("password");
		//第三方登录，不需要修改密码
		if(!"".equals(account)){
			editPasswordButton.setVisibility(0);
			personalCenterButton.setVisibility(0);
		}else{
			editPasswordButton.setVisibility(4);
			personalCenterButton.setVisibility(4);
		}
	}
	/**
	 * @author wlb
	 * 重写click事件
	 */
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btn_edit_password:
			editPassword();
			break;
		case R.id.btn_personal_center:
			personalCenter();
		default:
			break;
		}
	}
	private void personalCenter(){
		Intent intent = new Intent(MainActivity.this,PersonalCenterActivity.class);
		startActivity(intent);
	}
	/**
	 * @author wlb
	 * 修改密码
	 */
	private void editPassword(){
		Intent intent = new Intent(MainActivity.this,EditPasswordActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("account", account);
		bundle.putString("password", password);
	    intent.putExtras(bundle);
		startActivity(intent);
		//销毁activity
		finish();
	}
	/**
	 * @author wlb
	 * 重写系统返回键
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
	 * 返回
	 */
	public void setBack(){
		new AlertDialog.Builder(this)
		.setTitle("退出")
        .setMessage("亲，真的要走吗？再看会吧")
        .setCancelable(false)
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainActivity.this.finish();
				System.exit(0);
			}
		})
        .setNegativeButton("取消",null)
        .show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * add()方法的四个参数，依次是：
		 * 1、组别，如果不分组的话就写Menu.NONE,
		 * 2、Id，这个很重要，Android根据这个Id来确定不同的菜单
		 * 3、顺序，那个菜单现在在前面由这个参数的大小决定
		 * 4、文本，菜单的显示文本
		 */
		menu.add(Menu.NONE, Menu.FIRST + 1, 1, "切换账户");
		menu.add(Menu.NONE, Menu.FIRST + 2, 2, "退出");
		return true;
	}
	
	//菜单项被选择事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			finish();
			MxjPreference.getInstance(MainActivity.this).putStringAndCommit("currentUserLogname", "");
			Intent login = new Intent(MainActivity.this,Login.class);
		    startActivity(login);
			break;
		case Menu.FIRST + 2:
			setBack();
			break;
		}
		return false;
	}
}
