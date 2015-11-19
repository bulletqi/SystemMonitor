package com.newegg.monitor.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	private static final Logger LOGGER = Logger.getLogger(PropertiesUtil.class);

	private Properties props;
	private static PropertiesUtil util;
	
	public PropertiesUtil(){
		File file = null;
		try {
			String path = PropertiesUtil.class.getClassLoader().getResource("").getPath();
			file = new File(path + "/config.properties");
		} catch (Exception e) {}
		try {
			if(file == null || !file.exists()){
				String path = System.getProperty("user.dir");
				file = new File(path + "/../config/config.properties");
			}
		}catch (Exception e) {}
		if(file== null || !file.exists()){
			LOGGER.error("no config files");
			System.out.println(-1);
		}
		readProperties(file);
	}
	
	public static PropertiesUtil getInstence(){
		if(util == null){
			util = new PropertiesUtil();
		}
		return util;
	}
	
	private void readProperties(File file) {
		try {
			props = new Properties();
			FileInputStream fis =new FileInputStream(file);
			props.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取某个属性
	 */
	public String getProperty(String key){
		return props.getProperty(key);
	}
}

