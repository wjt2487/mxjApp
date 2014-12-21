package cn.mxj.mxjapp.util.db;

import cn.mxj.mxjapp.model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {

	private DatabaseHelper dbHelper;
	// 表名
	public static final String TABLE_NAME = "user";

	public UserDao(Context context) {
		// 创建一个DatabaseHelper对象
		this.dbHelper = DatabaseHelper.getInstance(context);
	}

	/**
	 * 插入数据
	 * @return 如果成功则返回true,否则返回false
	 */
	public boolean insert(User user) {
		// 只有调用了DatabaseHelper对象的getReadableDatabase()方法，或者是getWritableDatabase()方法之后，才会创建，或打开一个数据库
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("u_account", user.getAccount());
		cv.put("u_password", user.getPassword());
		cv.put("u_secret", user.getSecret());
		cv.put("u_status", user.getStatus());
		cv.put("u_isActive", user.getIsActive());
		cv.put("u_type", user.getType());
		cv.put("u_name", user.getName());
		cv.put("u_age", user.getAge());
		cv.put("u_sex", user.getSex());
		cv.put("u_native", user.getNativePlace());
		cv.put("u_birthday", user.getBirthday());
		cv.put("u_ismarried", user.getIsMarried());
		cv.put("u_isdelete", user.getIsDelete());
		cv.put("u_remark", user.getRemark());
		// 插入数据,返回插入数据ID
		long id = db.insert(TABLE_NAME, null, cv);
		if (id != 0) {
			return true;
		}
		return false;
	}

	/**
	 * 更新数据
	 * @return 如果成功则返回true,否则返回false
	 */

	public boolean update(User user) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// Android自带的ContetValues,类似于Map,提供了put(String key, XXX value)的方法存入数据
		ContentValues cv = new ContentValues();
		cv.put("u_account", user.getAccount());
		cv.put("u_password", user.getPassword());
		cv.put("u_secret", user.getSecret());
		cv.put("u_status", user.getStatus());
		cv.put("u_isActive", user.getIsActive());
		cv.put("u_type", user.getType());
		cv.put("u_name", user.getName());
		cv.put("u_age", user.getAge());
		cv.put("u_sex", user.getSex());
		cv.put("u_native", user.getNativePlace());
		cv.put("u_birthday", user.getBirthday());
		cv.put("u_ismarried", user.getIsMarried());
		cv.put("u_isdelete", user.getIsDelete());
		cv.put("u_remark", user.getRemark());
		// 通过ContentValues更新数据表,返回更新的ID值
		int result = db.update(TABLE_NAME, cv, "u_account=?",
				new String[] { String.valueOf(user.getAccount()) });
		if (result != 0) {
			return true;
		}
		return false;

	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */

	public boolean delete(String logname) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// 删除指定ID值
		int result = db.delete(TABLE_NAME, "logname=?",
				new String[] { logname });
		if (result != 0) {
			return true;
		}
		return false;

	}

	/**
	 * 查询数据
	 * 
	 * @return 返回数据列表
	 */
	public Cursor fetchAll() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		// 查询数据表中所有字段
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null,
				null, "_id desc");
		if (cursor != null) {
			return cursor;
		}
		return null;
	}
	
	
	/**
	 * 查询数据
	 * 
	 * @return 返回数据列表
	 */
	public User getByLogname(String logname) {
		User user = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		//db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		// 查询数据表中所有字段
		Cursor cursor = db.query(TABLE_NAME, new String[] { "u_account","u_password","u_secret","u_status","u_isActive",
				"u_type","u_name","u_age","u_sex","u_native","u_birthday","u_ismarried","u_isdelete","u_remark" }, 
				"u_account=?", new String[] { logname }, null,null,null);
		if (cursor != null) {
			if(cursor.moveToNext()){
				user = new User(
						cursor.getString(cursor.getColumnIndex("u_account")), 
						cursor.getString(cursor.getColumnIndex("u_password")), 
						cursor.getString(cursor.getColumnIndex("u_secret")),  
						cursor.getInt(cursor.getColumnIndex("u_status")), 
						cursor.getInt(cursor.getColumnIndex("u_isActive")),
						cursor.getInt(cursor.getColumnIndex("u_type")), 
						cursor.getString(cursor.getColumnIndex("u_name")), 
						cursor.getInt(cursor.getColumnIndex("u_age")),  
						cursor.getInt(cursor.getColumnIndex("u_sex")),  
						cursor.getString(cursor.getColumnIndex("u_native")),  
						cursor.getString(cursor.getColumnIndex("u_birthday")), 
						cursor.getInt(cursor.getColumnIndex("u_ismarried")), 
						cursor.getInt(cursor.getColumnIndex("u_isdelete")), 
						cursor.getString(cursor.getColumnIndex("u_isActive"))); 
			}
		}
		cursor.close();
		return user;
	}
}