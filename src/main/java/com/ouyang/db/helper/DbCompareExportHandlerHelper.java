package com.ouyang.db.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.ouyang.db.vo.Db;
import com.ouyang.db.vo.DbTable;
import com.ouyang.db.vo.Result;
import com.ouyang.db.vo.ResultTable;

/**
 * 辅助DbCompareExportHandler这个类来处理计算逻辑
 * @author Ouyang
 *
 */
public class DbCompareExportHandlerHelper {
	FileHelper fileHelper = new FileHelper();

	public Db getBaseDbInfo(Properties prop){
		if("file".equals(prop.getProperty("base.source.type"))){
			return getDbInfoFromFile(prop.getProperty("import.folder"), "base.dat");
		} else if("db".equals((String)prop.getProperty("base.source.type"))){
			return getDbInfoFromDb(prop,"base");
		}
		throw new RuntimeException("配置文件compare-db-info.properties没有配置属性base.source.type或者配置不对！");
	}
	

	public Db getGoalDbInfo(Properties prop) {
		// TODO Auto-generated method stub
		if("file".equals(prop.getProperty("goal.source.type"))){
			return getDbInfoFromFile(prop.getProperty("import.folder"), "goal.dat");
		} else if("db".equals((String)prop.getProperty("base.source.type"))){
			return getDbInfoFromDb(prop,"goal");
		}
		throw new RuntimeException("配置文件compare-db-info.properties没有配置属性base.source.type或者配置不对！");
	}
	
	public Result compareDbStructure(Db baseDb, Db goalDb, Result result){
		if(result == null) {
			result = new Result();
		}
		List<ResultTable> moreResultTables = new ArrayList<ResultTable>();
		List<ResultTable> lessResultTables = new ArrayList<ResultTable>();
		List<ResultTable> modifyResultTables = new ArrayList<ResultTable>();
		compareMoreAndModifyTable(baseDb, goalDb, moreResultTables, modifyResultTables);
		compareLessTable(baseDb, goalDb, lessResultTables);
		result.setMoreResultTables(moreResultTables);
		result.setLessResultTables(lessResultTables);
		result.setModifyResultTables(modifyResultTables);
		return result;
	}
	
	private void compareLessTable(Db baseDb, Db goalDb,
			List<ResultTable> lessResultTables) {
		// TODO Auto-generated method stub
		List<DbTable> baseTables = baseDb.getTables();
		Map<String, DbTable> mapGoalTables = goalDb.getMapTable();
		String tempName = null;
		for(DbTable baseTable : baseTables){
			tempName = baseTable.getName();
			if(!mapGoalTables.containsKey(tempName)) {
				ResultTable resultTable = new ResultTable();
				lessResultTables.add(resultTable);
				resultTable.setName(baseTable.getName());
				resultTable.setComment(baseTable.getComment());
			}
		}
	}


	private void compareMoreAndModifyTable(Db baseDb, Db goalDb,
			List<ResultTable> moreResultTables,
			List<ResultTable> modifyResultTables) {
		// TODO Auto-generated method stub
		List<DbTable> goalTables = goalDb.getTables();
		Map<String, DbTable> mapBaseTables = baseDb.getMapTable();
		String tempName = null;
		for(DbTable goalTable : goalTables){
			tempName = goalTable.getName();
			if(mapBaseTables.containsKey(tempName)){
				DbTable baseTable = mapBaseTables.get(tempName);
				ResultTable modifyTableResult = compareModifyTable(baseTable, goalTable);
				if(modifyTableResult != null){
					modifyResultTables.add(modifyTableResult);
				}
			} else {
				ResultTable resultTable = new ResultTable();
				moreResultTables.add(resultTable);
				resultTable.setName(goalTable.getName());
				resultTable.setComment(goalTable.getComment());
			}
		}
	}


	private ResultTable compareModifyTable(DbTable baseTable, DbTable goalTable) {
		// TODO Auto-generated method stub
		throw new RuntimeException("未实现");
	}


	public Result compareDbInitData(Db baseDb, Db goalDb, Result result){
		throw new RuntimeException("未实现");
	}
	
	public void writeCompareDbInfoResult(Result result, String filePath){
		
		throw new RuntimeException("未实现");
	}

	private Db getDbInfoFromDb(Properties prop,String dbType) {
		// TODO Auto-generated method stub
		throw new RuntimeException("未实现");
	}

	private Db getDbInfoFromFile(String folder, String fileName) {
		// TODO Auto-generated method stub
		return (Db)fileHelper.readFileForObject(folder+"/"+fileName);
	}
	
}
