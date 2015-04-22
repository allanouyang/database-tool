package com.ouyang.db.handler.mysql;

import java.util.Properties;

import com.ouyang.db.handler.AbstractDbCompareExportHandler;
import com.ouyang.db.helper.DbCompareExportHandlerHelper;
import com.ouyang.db.vo.Db;
import com.ouyang.db.vo.Result;

public class MysqlCompareExportHandler extends AbstractDbCompareExportHandler {

	DbCompareExportHandlerHelper helper = new DbCompareExportHandlerHelper();
	
	public void exportCompareDbInfo(Properties prop) {
		// TODO Auto-generated method stub
		Db baseDb = helper.getBaseDbInfo(prop);	//1 获取数据库信息base Db，来源有可能是file or db
		Db goalDb = helper.getGoalDbInfo(prop); //1 获取数据库信息goal Db，来源有可能是file or db
		Result result = helper.compareDbStructure(baseDb, goalDb, null);	//2 对比数据库结构信息
		result = helper.compareDbInitData(baseDb, goalDb, result);	//3 对比数据库初始化数据信息
		helper.writeCompareDbInfoResult(result, prop.getProperty("export.folder")+"/compare-db-result.txt");	//4 输出对比结果信息
	}
	


	public void exportCompareCompanyInitInfo(Properties prop) {
		// TODO Auto-generated method stub

	}

	public void exportInfoData(Properties prop) {
		// TODO Auto-generated method stub

	}

	public void exportSql(Properties prop) {
		// TODO Auto-generated method stub

	}

}
