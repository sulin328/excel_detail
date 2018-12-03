package com.sulin.excel.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 常量
 */
public class CommonConstants {
	public static String propertyFile = "config.properties";
	public static String contextPath;
	public static String staticServer;
	public static String uploadImageServer;
	public static String staticImage;
	public static String projectName;
	public static final String MYDOMAIN;
	
	static{
		System.out.println("加载配置文件！");
		Properties properties = new Properties();
		// 使用ClassLoader加载properties配置文件生成对应的输入流
		InputStream in = CommonConstants.class.getClassLoader().getResourceAsStream("config/"+propertyFile);
		// 使用properties对象加载输入流
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		contextPath = properties.getProperty("contextPath");
		staticServer = properties.getProperty("contextPath");
		uploadImageServer = properties.getProperty("contextPath");
		staticImage = properties.getProperty("contextPath");
		projectName = properties.getProperty("projectName");
		MYDOMAIN = properties.getProperty("mydomain");
	}


	/** 邮箱正则表达式 */
	public static String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	/** 电话号码正则表达式 */
	public static String telRegex = "^1[0-9]{10}$";
	/** 后台用户登录名正则表达式 */
	public static String loginRegex = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{6,20}$";
	/** 图片验证码Session的K */
	public static final String RAND_CODE = "COMMON_RAND_CODE";
}
