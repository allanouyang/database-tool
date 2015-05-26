/**
  * 文件说明
  * @Description:扩展说明 
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.netschina.db.handler.oracle;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.netschina.db.config.PropertiesLoader;
import com.netschina.db.handler.AbstractDbCompareExportHandler;
import com.netschina.db.helper.DbCompareExportHandlerHelper;
import com.netschina.db.helper.FileWriterHelper;
import com.netschina.db.vo.Db;
import com.netschina.db.vo.Result;
import com.netschina.db.vo.ResultTableDataInfo;

/**  
 * @Author: zhouhezhen
 * @Date: 2015年5月6日 上午10:43:37 
 * @ModifyUser: zhouhezhen
 * @ModifyDate: 2015年5月6日 上午10:43:37 
 * @Version:V6.0
 */
public class OracleCompareExportJHandler extends AbstractDbCompareExportHandler{
	
	DbCompareExportHandlerHelper helper = new DbCompareExportHandlerHelper();
	FileWriterHelper fileWriter=new FileWriterHelper();
	/**
	  * 方法说明
	  * @Discription:扩展说明
	  * @Author: zhouhezhen
	  * @Date: 2015年5月6日 上午10:44:17
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月6日 上午10:44:17
	  * @see com.netschina.db.handler.DbCompareExportHandler#exportCompareDbInfo(java.util.Properties)
	  */
	public void exportCompareDbInfo() {
		Properties prop = PropertiesLoader.getProp();
		Db baseDb = helper.getBaseDbInfo(prop);	//1 获取数据库信息base Db，来源有可能是file or db
		Db goalDb = helper.getGoalDbInfo(prop); //1 获取数据库信息goal Db，来源有可能是file or db
		Result result = helper.compareDbStructure(baseDb, goalDb, null);	//2 对比数据库结构信息
		result = helper.compareDbInitData(baseDb, goalDb,result);	//3 对比数据库初始化数据信息
		fileWriter.writeCompareDbInfoResult(result, prop.getProperty("export.folder")+"\\compare-db-result.txt");	//4 输出对比结果信息
		System.out.println("可以终止");
	}

	/**
	  * 方法说明
	  * @Discription:扩展说明
	  * @Author: zhouhezhen
	  * @Date: 2015年5月6日 上午10:44:17
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月6日 上午10:44:17
	  * @see com.netschina.db.handler.DbCompareExportHandler#exportCompareCompanyInitInfo(java.util.Properties)
	  */
	public void exportCompareCompanyInitInfo() {
	}

	/**
	  * 方法说明
	  * @Discription:扩展说明
	  * @Author: zhouhezhen
	  * @Date: 2015年5月6日 上午10:44:17
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月6日 上午10:44:17
	  * @see com.netschina.db.handler.DbCompareExportHandler#exportInfoData(java.util.Properties)
	  */
	public void exportInfoData() {
	}

	/**
	  * 方法说明
	  * @Discription:扩展说明
	  * @Author: zhouhezhen
	  * @Date: 2015年5月6日 上午10:44:17
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月6日 上午10:44:17
	  * @see com.netschina.db.handler.DbCompareExportHandler#exportSql(java.util.Properties)
	  */
	public void exportSql() {
	}

}
