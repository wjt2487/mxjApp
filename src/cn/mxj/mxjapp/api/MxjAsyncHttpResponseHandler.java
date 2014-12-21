package cn.mxj.mxjapp.api;

import android.app.AlertDialog;
import android.content.Context;
import cn.mxj.mxjapp.util.JsonUtils;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class MxjAsyncHttpResponseHandler extends AsyncHttpResponseHandler {

	private Context context;

	public MxjAsyncHttpResponseHandler(Context context) {
		this.context = context;
	}

	public void onFailure(Throwable result) {
		new AlertDialog.Builder(context)
		.setMessage("网络异常，获取数据失败！")
		.setCancelable(false)
		.setPositiveButton("确定", null)
		.show();
	}

	public void onFinish() {

	}

	public void onSuccess(String result) {
		MxjOperationResult mor = JsonUtils.getObjectJSON(result,
				MxjOperationResult.class);
		if (mor.isSuccessful()) {
			doSuccess(mor);
		} else {
			new AlertDialog.Builder(context).setTitle("抹香鲸科技")
			.setCancelable(false)
			.setMessage(mor.getMsg()).setPositiveButton("确定", null)
			.show();
		}
	}
	public void doSuccess(MxjOperationResult mor) {

	}
}
