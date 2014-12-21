package cn.mxj.mxjapp.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
	public final static String EMPTY = "";

	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 * @return 例如2011-3-24
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_DATE_PATTERN);
	}

	/**
	 * 获取当前时间 格式为yyyy-MM-dd 例如2011-07-08
	 * 
	 * @return
	 */
	public static String getDate() {
		return formatDate(new Date(), DEFAULT_DATE_PATTERN);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getDateTime() {
		return formatDate(new Date(), DEFAULT_DATETIME_PATTERN);
	}
	
	/** 
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
     * @param date String 想要格式化的日期
     * @param oldPattern String 想要格式化的日期的现有格式
     * @param newPattern String 想要格式化成什么格式
     * @return String 
     */ 
    public static String stringDatePattern(String date, String oldPattern, String newPattern) { 
        if (date == null || oldPattern == null || newPattern == null) 
            return ""; 
        SimpleDateFormat oldSimpleDateFormat = new SimpleDateFormat(oldPattern) ;        // 实例化模板对象  
        SimpleDateFormat newSimpleDateFormat = new SimpleDateFormat(newPattern) ;        // 实例化模板对象  
        Date temp = null ;  
        try{  
        	temp = oldSimpleDateFormat.parse(date) ;   // 将给定的字符串中的日期提取出来  
        }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理  
            e.printStackTrace() ;       // 打印异常信息  
        }  
        return newSimpleDateFormat.format(temp);
    } 

	/**
	 * 格式化日期时间字符串
	 * 
	 * @param date
	 * @return 例如2011-11-30 16:06:54
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, DEFAULT_DATETIME_PATTERN);
	}

	public static String join(final ArrayList<String> array, String separator) {
		StringBuffer result = new StringBuffer();
		if (array != null && array.size() > 0) {
			for (String str : array) {
				result.append(str);
				result.append(separator);
			}
			result.delete(result.length() - 1, result.length());
		}
		return result.toString();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	public static boolean isNotEmpty(String str) {
		return str != null && str.length() != 0;
	}
	
	/**
 	 * 手机号码的验证，严格验证
 	 * @param mobiles 要验证的手机号码
 	 * @return
 	 */
    public static boolean isMobileNO(String mobiles){     
        Pattern p = Pattern.compile("^((1[3|4|5|7|8][0-9]))\\d{8}$");     
        Matcher m = p.matcher(mobiles);            
        return m.matches();     
    } 
   
    /**
     * E_mail的验证
     * @param email 要验证的email
     * @return
     */
    public static boolean isEmail(String email){     
     String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(email);            
        return m.matches();     
    } 
}
