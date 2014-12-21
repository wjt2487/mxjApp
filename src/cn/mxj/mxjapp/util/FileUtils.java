package cn.mxj.mxjapp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.mxj.mxjapp.Config;
import android.os.Environment;

public class FileUtils {
	// 得到当前外部存储设备的目录
	// /SDCARD
	private static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/";

	public static String getSDPATH() {
		return SDPATH;
	}

	public static String getMoocBaseDir() {
		if (!isFileExist(Config.MXj_CACHE_BASE)) {
			creatSDDir(Config.MXj_CACHE_BASE);
		}
		return SDPATH + Config.MXj_CACHE_BASE;
	}



	/**
	 * 在SD卡上创建文件
	 * 
	 * @throws IOException
	 */
	public static File creatSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 */
	public static File creatSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdirs();
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}
	
	/** 如果不存在就创建 */
	public static boolean createIfNoExists(String path) {
		File file = new File(path);
		boolean mk = false;
		if (!file.exists()) {
			mk = file.mkdirs();
		}
		return mk;
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 */
	public static File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			creatSDDir(path);
			file = creatSDFile(path +"/" +fileName);
			output = new FileOutputStream(file);

			byte buf[] = new byte[1024];
			do {
				int numread = input.read(buf);
				if (numread <= 0) {
					break;
				}
				output.write(buf, 0, numread);
			} while (true);

			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
}