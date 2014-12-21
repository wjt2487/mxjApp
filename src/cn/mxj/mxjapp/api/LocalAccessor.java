package cn.mxj.mxjapp.api;

import cn.mxj.mxjapp.model.User;
import cn.mxj.mxjapp.util.db.UserDao;
import android.content.Context;

public class LocalAccessor{

	private static UserDao userDao;

	public LocalAccessor(Context context){
		userDao = new UserDao(context);
	}	

	//insert or update(if exist) user in DB
	public boolean updateUser(User user){
		boolean success = false;
		User tmpUser = userDao.getByLogname(user.getAccount());
		if(tmpUser == null){
			success = userDao.insert(user);
		}else{
			success = userDao.update(user);
		}
		return success;
	}
	
	//return null if no user saved
	public User getUser(String logname){
		if(logname == null || logname.equals("")){
			return new User();
		}else{
			User user = userDao.getByLogname(logname);
			if(user == null){
				return new User();
			}else{
				return user;
			}
		}
	}	
}
