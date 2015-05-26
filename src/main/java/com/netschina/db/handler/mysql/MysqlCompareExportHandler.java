package com.netschina.db.handler.mysql;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.netschina.db.config.PropertiesLoader;
import com.netschina.db.handler.AbstractDbCompareExportHandler;
import com.netschina.db.helper.DbCompareExportHandlerHelper;
import com.netschina.db.helper.FileWriterHelper;
import com.netschina.db.helper.MySqlQueryHelper;
import com.netschina.db.vo.Db;
import com.netschina.db.vo.Result;
import com.netschina.db.vo.ResultDataSql;
import com.netschina.db.vo.ResultSql;
import com.netschina.db.vo.ResultTableDataInfo;
import com.netschina.db.vo.ResultTableSql;

public class MysqlCompareExportHandler extends AbstractDbCompareExportHandler {

	DbCompareExportHandlerHelper helper = new DbCompareExportHandlerHelper();
	FileWriterHelper fileWriter=new FileWriterHelper();
	
	/**
	 * 
	  * 对比数据库信息(表结构与初始化数据)并导出
	  * @Discription:库间对比信息
	  * @Author: zhouhezhen
	  * @Date: 2015年5月6日 上午9:47:13
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月6日 上午9:47:13
	  * @see com.netschina.db.handler.DbCompareExportHandler#exportCompareDbInfo(java.util.Properties)
	 */
	public void exportCompareDbInfo() {
		Properties prop = PropertiesLoader.getProp();
		Db baseDb = helper.getBaseDbInfo(prop);	//1 获取数据库信息base Db，来源有可能是file or db
		Db goalDb = helper.getGoalDbInfo(prop); //1 获取数据库信息goal Db，来源有可能是file or db
		Result result = helper.compareDbStructure(baseDb, goalDb, null);	//2 对比数据库结构信息
		result= helper.compareDbInitData(baseDb, goalDb,result);	//3 对比数据库初始化数据信息
		fileWriter.writeCompareDbInfoResult(result, prop.getProperty("export.folder")+"\\compare-db-result.txt");	//4 输出对比结果信息
	}
	

	/**
	 * 
	  * 方法说明
	  * @Discription:库内公司对比信息
	  * @Author: zhouhezhen
	  * @Date: 2015年5月6日 上午9:50:21
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月6日 上午9:50:21
	  * @see com.netschina.db.handler.DbCompareExportHandler#exportCompareCompanyInitInfo(java.util.Properties)
	 */
	public void exportCompareCompanyInitInfo() {
		Properties prop = PropertiesLoader.getProp();
//		MySqlQueryHelper queryHelpler = new MySqlQueryHelper();
		Db goalDefaultDb = helper.getGoalDefaultAndNewDbInfo(prop,prop.getProperty("goal.db.default.company.id"));	//1 获取数据库信息goal Db，来源有可能是file or db
		Db baseNewDb = helper.getGoalDefaultAndNewDbInfo(prop,prop.getProperty("goal.db.new.company.id"));
		Result result = helper.compareDbStructure(goalDefaultDb,baseNewDb,null);
		//库间默认公司初始数据对比
		result= helper.compareDbInitData(goalDefaultDb, baseNewDb,result);	//3 对比数据库初始化数据信息
//		List<ResultTableDataInfo> tableInfoList =queryHelpler.getInitCompanyId(resultDb,(String)prop.get("goal.db.name"),prop,"zmkj");
//		fileWriter.writeInitCompanyIdInfoResult(tableInfoList,prop.getProperty("export.folder")+"\\compare-company-init-result.txt");
		fileWriter.writeCompareDbInfoResult(result, prop.getProperty("export.folder")+"\\compare-company-init-result.txt");
	}

	public void exportInfoData() {
		// TODO Auto-generated method stub
		Properties prop = PropertiesLoader.getProp();
		Db baseDb = helper.getBaseDbInfo(prop);	//1 获取数据库信息base Db，来源有可能是file or db
//		Db goalDb = helper.getGoalDbInfo(prop); //1 获取数据库信息goal Db，来源有可能是file or db
		fileWriter.writeInfoData(baseDb, prop.getProperty("export.folder")+"\\base.dat");
	}
	
	/**
	 * 
	  * 方法说明
	  * @Discription:导出初始化表结构与数据
	  * @Author: zhouhezhen
	  * @Date: 2015年5月6日 上午9:50:48
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月6日 上午9:50:48
	  * @see com.netschina.db.handler.DbCompareExportHandler#exportSql(java.util.Properties)
	 */
	public void exportSql() {
		// TODO Auto-generated method stub
		Properties prop = PropertiesLoader.getProp();
		Db baseDb = helper.getBaseDbInfo(prop);	//1 获取数据库信息base Db，来源有可能是file or db
		Db goalDb = helper.getGoalDbInfo(prop); //1 获取数据库信息goal Db，来源有可能是file or db
		Result result = new Result();
		List<ResultTableSql> baseTableSql = helper.getSqlList(baseDb.getTableDataInfos(),prop,"base");
		List<ResultTableSql> goalTableSql = helper.getSqlList(goalDb.getTableDataInfos(),prop,"goal");
		List<ResultDataSql> baseInitSql =helper.getInitData(baseDb.getTableDataInfos(),prop,"base");
		List<ResultDataSql> goalInitSql =helper.getInitData(goalDb.getTableDataInfos(),prop,"goal");
		result.setBaseTableSql(baseTableSql);
		result.setGoalTableSql(goalTableSql);
		result.setBaseInitSql(baseInitSql);
		result.setGoalInitSql(goalInitSql);
		System.out.println(baseTableSql.size());
		fileWriter.writeExportDbSql(result,(String)prop.get("export.folder"));
	}

}
