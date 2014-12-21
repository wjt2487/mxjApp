package cn.mxj.mxjapp;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import cn.mxj.mxjapp.imageloader.*;
import cn.mxj.mxjapp.util.FileUtils;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MxjApplication extends Application {
	
	private static MxjApplication mApplication;
	/** MoocEnglish SD卡缓存路径 */
	public static final String SDCARD_BASE = Environment.getExternalStorageDirectory() + "";
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		if (Constants.Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		}

		super.onCreate();
		initImageLoader(getApplicationContext());
		//创建缓存目录
		FileUtils.createIfNoExists(SDCARD_BASE);
	}
	
	public static MxjApplication getApplication() {
		return mApplication;
	}

	public static Context getContext() {
		return mApplication;
	}

	public void destory() {
		mApplication = null;
	}

	@SuppressWarnings("deprecation")
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)  
         .denyCacheImageMultipleSizesInMemory()  
         .memoryCache(new LruMemoryCache(2 * 1024 * 1024))  
         .memoryCacheSize(2 * 1024 * 1024)  
         .discCacheSize(50 * 1024 * 1024)  
         .denyCacheImageMultipleSizesInMemory()    
         .discCacheFileNameGenerator(new Md5FileNameGenerator())    
         .tasksProcessingOrder(QueueProcessingType.LIFO)    
         .discCacheFileCount(100)  
         .writeDebugLogs()  
         .build();  
		ImageLoader.getInstance().init(config);
	}
	
}