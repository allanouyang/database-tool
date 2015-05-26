/**
  * 文件说明
  * @Description:扩展说明 
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.netschina.db.util;

import java.util.Properties;

import com.netschina.db.config.PropertiesLoader;
import com.netschina.db.handler.DbCompareExportHandler;
import com.netschina.db.handler.mysql.MysqlCompareExportHandler;
import com.netschina.db.handler.oracle.OracleCompareExportJHandler;
import com.netschina.db.helper.MySqlQueryHelper;
import com.netschina.db.helper.QueryHelper;

/**  
 * 对象工厂工具
 * @Author: zhouhezhen
 * @Date: 2015年5月7日 上午9:04:02 
 * @ModifyUser: zhouhezhen
 * @ModifyDate: 2015年5月7日 上午9:04:02 
 * @Version:V6.0
 */
public class BeanFactoryUtil {

	private static DbCompareExportHandler exportHandler = null;
	private static QueryHelper queryHelper = null;
	
	/**
	 * 获取QueryHelper实例
	 * @return
	 */
	public static QueryHelper getQueryHelper(){
		if(queryHelper != null){
			return queryHelper;
		}
		Properties prop = PropertiesLoader.getProp();
		//获取数据库类型
		String dbType = prop.getProperty("db.type");
		if("mysql".equals(dbType)){
			queryHelper = new MySqlQueryHelper();
		} else if("oracle".equals(dbType)){
			throw new RuntimeException("不支持的数据库配置类型！");
		} else {
			throw new RuntimeException("不支持的数据库配置类型！");
		}
		return queryHelper;
	}
	
	/**
	 * 获取DbCompareExportHandler实例
	 * @return
	 */
	public static DbCompareExportHandler getDbCompareExportHandler(){
		if(exportHandler != null){
			return exportHandler;
		}
		Properties prop = PropertiesLoader.getProp();
		String dbType = prop.getProperty("db.type");
		if("mysql".equals(dbType)){
			exportHandler = new MysqlCompareExportHandler();
		} else if("oracle".equals(dbType)){
			exportHandler = new OracleCompareExportJHandler();
		} else {
			throw new RuntimeException("不支持的数据库配置类型！");
		}
		return exportHandler;
	}
}
