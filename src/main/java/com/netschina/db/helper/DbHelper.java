package com.netschina.db.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 处理与连接及数据库信息相关帮助类
 * @author Ouyang
 *
 */
@Deprecated
public class DbHelper {
	
	public static Connection getDbConnection(Properties prop){
		String dbDriver = (String)prop.get("db.driver");
		String dbUrl = (String)prop.get("db.url");
		String username = (String)prop.get("db.user");
		String password = (String)prop.get("db.password");
		return getConnection(dbDriver,dbUrl,username,password);
	}
	public static Connection getBaseDbConnection(Properties prop){
		String dbDriver = (String)prop.get("db.driver");
		String dbUrl = (String)prop.get("base.db.url");
		String username = (String)prop.get("base.db.user");
		String password = (String)prop.get("base.db.password");
		return getConnection(dbDriver,dbUrl,username,password);
	}
	public static Connection getGoalDbConnection(Properties prop){
		String dbDriver = (String)prop.get("db.driver");
		String dbUrl = (String)prop.get("goal.db.url");
		String username = (String)prop.get("goal.db.user");
		String password = (String)prop.get("goal.db.password");
		return getConnection(dbDriver,dbUrl,username,password);
	}
	//获得db数据库连接
	private static Connection getConnection(String driver, String url, String user, String pwd){
		Connection conn = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
