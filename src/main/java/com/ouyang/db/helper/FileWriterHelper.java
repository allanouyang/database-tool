/**
  * 文件说明
  * @Description:扩展说明 
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.ouyang.db.helper;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ouyang.db.util.DbUtil;
import com.ouyang.db.vo.Db;
import com.ouyang.db.vo.Result;
import com.ouyang.db.vo.ResultColumn;
import com.ouyang.db.vo.ResultDataSql;
import com.ouyang.db.vo.ResultProperty;
import com.ouyang.db.vo.ResultSql;
import com.ouyang.db.vo.ResultTable;
import com.ouyang.db.vo.ResultTableRecordDataInfo;
import com.ouyang.db.vo.ResultTableDataInfo;
import com.ouyang.db.vo.ResultTableSql;

/**  
 * @Author: zhouhezhen
 * @Date: 2015年5月7日 上午10:00:27 
 * @ModifyUser: zhouhezhen
 * @ModifyDate: 2015年5月7日 上午10:00:27 
 * @Version:V6.0
 */
public class FileWriterHelper {
	/**
	 * 
	  * 方法说明
	  * @Discription:表结构对比输出到txt文件
	  * @Author: zhouhezhen
	  * @Date: 2015年5月7日 上午10:04:59
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月7日 上午10:04:59
	 */
	public void writeCompareDbInfoResult(Result result, String filePath) {
		List<ResultTable> moreResultTables = result.getMoreResultTables();
		List<ResultTable> lessResultTables = result.getLessResultTables();
		List<ResultTable> modifyResultTables = result.getModifyResultTables();
		List<ResultTableDataInfo> resultTableInfos = result.getResultTableInfos();
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath, false);
			if((moreResultTables!=null && moreResultTables.size()>0) 
					|| (lessResultTables!=null && lessResultTables.size()>0) 
					|| (modifyResultTables!=null && modifyResultTables.size()>0)){
				fw.write("====================表结构变化如下========================\r\n");
			} else {
				fw.write("====================表结构无任何变化========================\r\n");
			}
			if(moreResultTables!=null && moreResultTables.size()>0){
				fw.write("==========多了表==============\r\n");
				for(ResultTable table: moreResultTables){
					fw.write("表："+table.getName()+"\t注释："+table.getComment()+"\r\n");
				}
			}
			if(lessResultTables!=null && lessResultTables.size()>0){
				fw.write("==========少了表==============\r\n");
				for(ResultTable table: lessResultTables){
					fw.write("表："+table.getName()+"\t注释："+table.getComment()+"\r\n");
				}
			}
			if(modifyResultTables!=null && modifyResultTables.size()>0){
				fw.write("==========修改了表==============\r\n");
				for(ResultTable table: modifyResultTables){
					fw.write("表："+table.getName()+"\t注释："+table.getComment()+"\t");
					List<ResultProperty> resultPropertys = table.getResultPropertys();
					if(resultPropertys != null){
						for(ResultProperty resultProperty : resultPropertys){
							fw.write(resultProperty.getName()+"\t新值："+resultProperty.getNewValue()+"\t旧值："+resultProperty.getOldValue());
						}
					}
					List<ResultColumn> moreResultColumns = table.getMoreResultColumns();
					if(moreResultColumns != null && moreResultColumns.size() > 0){
						fw.write("\r\n  多了：\r\n");
						for(ResultColumn resultColumn : moreResultColumns){
							fw.write("    字段："+resultColumn.getName());
						}
						fw.write("\r\n");
					}
					List<ResultColumn> lessResultColumns = table.getLessResultColumns();
					if(lessResultColumns != null && lessResultColumns.size() > 0){
						fw.write("\r\n  少了：\r\n");
						for(ResultColumn resultColumn : lessResultColumns){
							fw.write("    字段："+resultColumn.getName());
						}
						fw.write("\r\n");
					}
					List<ResultColumn> modifyResultColumns = table.getModifyResultColumns();
					if(modifyResultColumns != null && modifyResultColumns.size() > 0){
						fw.write("\r\n  修改：\r\n");
						for(ResultColumn resultColumn : modifyResultColumns){
							fw.write("    字段："+resultColumn.getName());
							resultPropertys = resultColumn.getResultPropertys();
							for(ResultProperty resultProperty: resultPropertys){
								fw.write("\t"+resultProperty.getName()+"\t新值:"+resultProperty.getNewValue()+"\t旧值:"+resultProperty.getOldValue()+"\r\n");
							}
						}
					}
				}
			}
			if(resultTableInfos != null && resultTableInfos.size() > 0){
				fw.write("====================初始化表数据变化如下========================\r\n");
				for(ResultTableDataInfo tableDataInfo: resultTableInfos) {
					fw.write("表："+tableDataInfo.getName()+"\t注释："+tableDataInfo.getComment()+"\r\n");
					List<ResultTableRecordDataInfo> moreRecords = tableDataInfo.getMoreRecords();
					List<String> fields = tableDataInfo.getFields();
					if(moreRecords!=null && moreRecords.size()>0){
						fw.write("  多了记录：\r\n");
						for(ResultTableRecordDataInfo record: moreRecords){
							Map<String, String> mapField = record.getMapField();
							for(String field : fields){
								fw.write("\t"+field+": "+mapField.get(field));
							}
							fw.write("\r\n");
						}
					}
					List<ResultTableRecordDataInfo> lessRecords = tableDataInfo.getLessRecords();
					if(lessRecords!=null && lessRecords.size()>0){
						fw.write("  少了记录：\r\n");
						for(ResultTableRecordDataInfo record: lessRecords){
							Map<String, String> mapField = record.getMapField();
							for(String field : fields){
								fw.write("\t"+field+": "+mapField.get(field));
							}
							fw.write("\r\n");
						}
					}
					List<ResultTableRecordDataInfo> modifyRecords = tableDataInfo.getModifyRecords();
					if(modifyRecords!=null && modifyRecords.size()>0){
						fw.write("  修改了记录：\r\n");
						for(ResultTableRecordDataInfo record: modifyRecords){
							List<ResultProperty> resultPropertys = record.getResultPropertys();
							for(ResultProperty resultProperty : resultPropertys){
								fw.write("    字段："+resultProperty.getName()+"\t新值："+resultProperty.getNewValue()+"\t旧值："+resultProperty.getOldValue());
							}
							fw.write("\r\n");
						}
					}
				}
			} else {
				fw.write("====================初始化表数据无任何变化========================\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	  * 方法说明
	  * @Discription:writeInitCompanyIdInfoResult
	  * @param tableInfoList
	  * @param string
	  * @return void
	  * @Author: zhouhezhen
	  * @Date: 2015年5月7日 上午10:06:42
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月7日 上午10:06:42
	 */
	public void writeInitCompanyIdInfoResult(
			List<ResultTableDataInfo> tableInfoList, String string) {
		try {
			FileWriter fw = new FileWriter(string, false);
			if (null != tableInfoList && tableInfoList.size() > 0) {
				fw.write("goal库总共有" + tableInfoList.size()
						+ "张表存在初始化数据,分别如下:" + "\r\n");
				for (ResultTableDataInfo tableInfos : tableInfoList) {
					fw.write("表名:" + tableInfos.getName() + "\r\n");
					List<ResultTableRecordDataInfo> tableColumnInfoList = tableInfos
							.getDetailList();
					for (ResultTableRecordDataInfo tableColumnInfo : tableColumnInfoList) {
//						String[] str = tableColumnInfo.getColumnName();
//						for (int k = 0; k < str.length; k++) {
//							fw.write(str[k] + "   ");
//						}
//						fw.write("\r\n");
					}
				}
			}
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException("txt文件写入异常!");
		}

	}
	
	
	/**
	  * 方法说明
	  * @Discription:扩展说明
	  * @param resultDataSql
	  * @param string
	  * @return void
	  * @Author: zhouhezhen
	  * @Date: 2015年5月5日 下午4:32:43
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月5日 下午4:32:43 
	  */
	public void writeExportDbSql(Result result, String filePath) {
		try{
			FileWriter baseSqlfw = new FileWriter(filePath+"\\base-table.sql", false);
			FileWriter goalSqlfw = new FileWriter(filePath+"\\goal-table.sql", false);
			FileWriter initDatafw = new FileWriter(filePath+"\\base-init-data.sql", false);
			FileWriter goalDatafw = new FileWriter(filePath+"\\goal-init-data.sql", false);
			if(null != result.getBaseTableSql() && result.getBaseTableSql().size() > 0){
				baseSqlfw.write("====================baseDb初始化表如下========================\r\n");
				for (ResultTableSql resultTableSql : result.getBaseTableSql()) {
					baseSqlfw.write(resultTableSql.getName()+"表Sql语句：\r\n");
					baseSqlfw.write(resultTableSql.getSql()+"\r\n\n");
				}
				baseSqlfw.write("===================END=============================="+"\r\n");
			}else {
				baseSqlfw.write("====================没有初始化表========================\r\n");
			}
			baseSqlfw.close();
			if(null != result.getGoalTableSql() && result.getGoalTableSql().size() > 0){
				goalSqlfw.write("====================goalDb初始化表如下========================\r\n");
				for (ResultTableSql resultTableSql : result.getGoalTableSql()) {
					goalSqlfw.write(resultTableSql.getName()+"表Sql语句：\r\n");
					goalSqlfw.write(resultTableSql.getSql()+"\r\n\n");
				}
				goalSqlfw.write("===================END=============================="+"\r\n");
			}else {
				goalSqlfw.write("====================没有初始化表========================\r\n");
			}
			goalSqlfw.close();
			if(null != result.getBaseInitSql() && result.getBaseInitSql().size() > 0){
				
				for (ResultDataSql resultInfos : result.getBaseInitSql()) {
					initDatafw.write("base库中的数据:"+resultInfos.getName()+"=============================\r\n");
					for (int i = 0; i < resultInfos.getDataSql().length; i++) {
						String[] str = resultInfos.getDataSql();
							initDatafw.write(str[i]+"   ");
						initDatafw.write("\r\n");
					}
					initDatafw.write("\r\n");
				}
				initDatafw.write("===================END=============================="+"\r\n");
			}else{
				initDatafw.write("===================END=============================="+"\r\n");
			}
			initDatafw.close();
			if(null != result.getGoalInitSql() && result.getGoalInitSql().size() > 0){
				
				for (ResultDataSql resultInfos : result.getGoalInitSql()) {
					goalDatafw.write("goal库中的数据:"+resultInfos.getName()+"=============================\r\n");
					for (int i = 0; i < resultInfos.getDataSql().length; i++) {
						String[] str = resultInfos.getDataSql();
						goalDatafw.write(str[i]+"   ");
						goalDatafw.write("\r\n");
					}
					goalDatafw.write("\r\n");
				}
				goalDatafw.write("===================END=============================="+"\r\n");
			}else{
				goalDatafw.write("===================END=============================="+"\r\n");
			}
			}catch(Exception e){
				throw new RuntimeException("txt文件写入异常!");
			}
		
		
	}

	public void writeInfoData(Db baseDb, String string) {
		try {
			FileOutputStream fos
			= new FileOutputStream(string);
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			oos.writeObject(baseDb);
			System.out.println("序列化完毕");
			oos.close();
		} catch (IOException e) {
			throw new RuntimeException("dat文件写入异常!");
		}
		
	}
}
