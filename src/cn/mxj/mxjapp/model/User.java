package cn.mxj.mxjapp.model;

public class User {

	/**
	 * 主键标志
	 */
	private int id;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 明码
	 */
	private String secret;
	/**
	 * 登录状态
	 */
	private int status;
	/**
	 * 激活状态
	 */
	private int isActive;
	/**
	 * 用户类型
	 */
	private int type;
	/**
	 * 昵称
	 */
	private String name;
	/**
	 * 年龄
	 */
	private int age;
	/**
	 * 性别
	 */
	private int sex;
	/**
	 * 籍贯
	 */
	private String nativePlace;
	/**
	 * 生日
	 */
	private String birthday;
	/**
	 * 是否结婚
	 */
	private int isMarried;
	/**
	 * 是否删除
	 */
	private int isDelete;
	/**
	 * 备注
	 */
	private String remark;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public int getIsMarried() {
		return isMarried;
	}
	public void setIsMarried(int isMarried) {
		this.isMarried = isMarried;
	}
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public User(){
		
	}
	public User(String account, String password, String secret,int status,int isActive,int type,String name,
			int age,int sex,String nativePlace,String birthday,int isMarried,int isDelete,String remark ) {
		super();
		this.account = account;
		this.password = password;
		this.secret = secret;
		this.status = status;
		this.isActive = isActive;
		this.type = type;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.nativePlace = nativePlace;
		this.birthday = birthday;
		this.isMarried = isMarried;
		this.isDelete = isDelete;
		this.remark = remark;
	}
}
