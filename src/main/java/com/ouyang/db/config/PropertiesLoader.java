package com.ouyang.db.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 * @author Ouyang
 *
 */
public class PropertiesLoader {
	private String configFileName;
	public PropertiesLoader(){
		
	}
	
	public PropertiesLoader(String configFileName){
		this.configFileName = configFileName;
	}
	
	public Properties getProp() {
		Properties properties = new Properties();   
		InputStream is = null;
		try{
			is = PropertiesLoader.class.getResourceAsStream("/config/"+this.configFileName+".properties");
			properties.load(is);
		} catch (Exception e){
			throw new RuntimeException("读取配置文件失败，请检查是否路径不对!");
			//关闭流
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return properties;
	}
}
