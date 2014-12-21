package cn.mxj.mxjapp.api;

import java.io.Serializable;

/**
 * 操作API结果的包装类，主要用于封装 android api 的调用结果
 * 
 * @author zj
 * 
 */
public class MxjOperationResult implements Serializable {

	private static final long serialVersionUID = -7766798484847419692L;

	public final static MxjOperationResult SUCCESS = new MxjOperationResult(true,
			"操作成功！");

	public final static MxjOperationResult FAILED = new MxjOperationResult(false,
			"操作失败，请重试！");

	public final static MxjOperationResult SYS_EXCEPTION = new MxjOperationResult(
			false, "发生系统错误，访问失败！");

	public final static MxjOperationResult ILLEGAL_USER = new MxjOperationResult(
			false, "您的身份未通过验证，访问失败！");

	public MxjOperationResult(boolean successful, String msg) {
		this.successful = successful;
		this.msg = msg;
	}
	
	public MxjOperationResult(boolean successful,int status, String msg) {
		this.successful = successful;
		this.msg = msg;
		this.status = status;
	}

	@Override
	public MxjOperationResult clone() {
		MxjOperationResult out = new MxjOperationResult(this.successful, this.msg);
		out.id = this.id;
		out.longId = this.longId;
		out.strId = this.strId;
		out.intValue = this.intValue;
		out.strValue = this.strValue;
		out.data = this.data;
		return out;
	}
	//封装是否成功
	private boolean successful;
	
	private int status;//0正常1session过期2账号在其他地方登录
	
	//封装提示信息
	private String msg;
	
	private String data;

	private int id;

	private long longId;

	private String strId;

	private int intValue;
	//封装结果
	private String strValue;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIntValue() {
		return this.intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public long getLongId() {
		return this.longId;
	}

	public void setLongId(long longId) {
		this.longId = longId;
	}

	public String getStrId() {
		return this.strId;
	}

	public void setStrId(String strId) {
		this.strId = strId;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStrValue() {
		return this.strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public boolean isSuccessful() {
		return this.successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MoocOperationResult [successful=" + successful + ", msg=" + msg
				+ ", data=" + data + ", id=" + id + ", longId=" + longId
				+ ", strId=" + strId + ", intValue=" + intValue + ", strValue="
				+ strValue + "]";
	}
}
