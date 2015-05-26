package com.netschina.db.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.netschina.db.util.DbUtil;
import com.netschina.db.vo.Db;
import com.netschina.db.vo.DbColumn;
import com.netschina.db.vo.DbTable;
import com.netschina.db.vo.DbTableDataInfo;
import com.netschina.db.vo.DbTableRecordDataInfo;
import com.netschina.db.vo.Result;
import com.netschina.db.vo.ResultColumn;
import com.netschina.db.vo.ResultTable;
import com.netschina.db.vo.ResultTableDataInfo;
import com.netschina.db.vo.ResultTableRecordDataInfo;

/**
 * 处理查询和包装信息类
 * 
 * @author Ouyang
 * 
 */
public class MySqlQueryHelper implements QueryHelper {

	// 查询数据库表
	public final static String QUERY_MYSQLDB_TABLE_NAME_SQL = "SELECT table_name AS tablename, table_comment AS tablecomment FROM "
			+ "information_schema.tables WHERE table_schema= ?";// 数据库实例'saasdbtest'
	// 查询数据库表字段
	public final static String QUERY_MYSQLDB_TABLE_STRUCTURE = "SELECT table_name,"
			+ "column_name,data_type,"
			+ "column_type,column_key,"
			+ "is_nullable, column_comment "
			+ "FROM "
			+ "information_schema.columns " + "WHERE table_schema = ? AND table_name = ?";// 数据库实例名称'saasdbtest'
	public final static String QUERY_MYSQLDB_DEFAULT_TABLE_STRUCTURE = "SELECT table_name,"
			+ "column_name,data_type,"
			+ "column_type,column_key,"
			+ "is_nullable, column_comment "
			+ "FROM "
			+ "information_schema.columns " + "WHERE table_schema = ? AND table_name = ? AND column_name='COMPANY_ID'";// 数据库实例名称'saasdbtest'
	//查询有initflag字段的表名及表注释
	public final static String QUERY_ALL_INIT_FLAG_TABLE = "SELECT table_name AS tableName, table_comment AS comment FROM information_schema.tables "
			+ "WHERE table_schema= ? AND TABLE_NAME IN (SELECT TABLE_NAME as tableName FROM information_schema.columns  "
			+ "where TABLE_SCHEMA=? and COLUMN_NAME=?)";
	public final static String QUERY_ALL_INIT_FLAG_TABLE_PK = "SELECT TABLE_NAME as tableName, COLUMN_NAME as columnName from information_schema.columns where COLUMN_KEY=? "
			+ "and TABLE_NAME in (SELECT TABLE_NAME FROM information_schema.columns  where TABLE_SCHEMA=? and COLUMN_NAME=?) order by TABLE_NAME, COLUMN_NAME";

	/**
	 * 方法说明
	 * @Discription:获取数据库信息base or goal Db
	 * @Author: zhouhezhen
	 * @Date: 2015年5月6日 上午9:57:47
	 * @ModifyUser：zhouhezhen
	 * @ModifyDate: 2015年5月6日 上午9:57:47
	 */
	public Db getDbInfoFromDb(Connection conn, String dbName, String defaultCompanyId,String str) throws Exception {
		Db db = new Db();
		getDbTableIntoDb(db, conn, dbName,str);
		getDbTableDataInfoIntoDb(db, conn, dbName, defaultCompanyId,str);
		conn.close();
		return db;
	}
	
	/**
	 * 
	  * 方法说明
	  * @Discription:获取数据库表信息
	  * @Author: zhouhezhen
	  * @Date: 2015年5月15日 上午8:47:31
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月15日 上午8:47:31
	 */
	private void getDbTableDataInfoIntoDb(Db db, Connection conn, String dbName, String defaultCompanyId,String str) throws Exception {
		//表及其对应的信息
		Map<String, DbTableDataInfo> mapTableDataInfo = new HashMap<String, DbTableDataInfo>(); 
		  //表信息集合
		db.setMapTableDataInfo(mapTableDataInfo);
		List<DbTableDataInfo> tableDataInfos = new ArrayList<DbTableDataInfo>(); 
		db.setTableDataInfos(tableDataInfos);
		Map<String, List<String>> mapTablePks = getInitFlagTablePk(conn, dbName);
		db.setMapTablePks(mapTablePks);
		
		PreparedStatement ps = conn.prepareStatement(QUERY_ALL_INIT_FLAG_TABLE);
		ps.setFetchSize(100);
		ps.setString(1, dbName);
		ps.setString(2, dbName);
		ps.setString(3, "INIT_FLAG");
		ResultSet rs = ps.executeQuery();
		String tableName = null;
		while (rs.next()) {
			try{
				DbTableDataInfo table = new DbTableDataInfo();
				tableName = rs.getString("tableName");
				table.setName(tableName);
				table.setComment(rs.getString("comment"));
				//根据init_flag和company字段查询所有的表
				PreparedStatement dataPs = conn.prepareStatement("select * from "+tableName+" where init_flag=? and company_id=?");
				dataPs.setFetchSize(10);
				dataPs.setString(1, "Y");
				dataPs.setString(2, defaultCompanyId);
				ResultSet dataRs = dataPs.executeQuery();
				ResultSetMetaData rsmd = dataPs.getMetaData();
				//字段集合
				List<String> fields = new ArrayList<String>();
				table.setFields(fields);
				int count = rsmd.getColumnCount();
				for(int i=0; i<count; i++){
					if(str.length()>0){
						if(!"COMPANY_ID".equals(rsmd.getColumnName(i+1))){
							fields.add(rsmd.getColumnName(i+1));
						}else{
						}
					}else{
						fields.add(rsmd.getColumnName(i+1));
					}
				}
				
				transferRecordResultSet(dataRs, table, fields, mapTablePks.get(tableName));
				
				dataRs.close();
				dataPs.close();
				tableDataInfos.add(table);
				mapTableDataInfo.put(tableName, table);
			} catch (Exception e){
				System.out.println("表名："+tableName+" 没有company_id字段..............................");
				//e.printStackTrace();
			}
		}
		rs.close();
		ps.close();
	}
	
	/**
	 * 
	  * 方法说明
	  * @Discription:获取含有InitFlag字段的表主键
	  * @return Map<String,List<String>>
	  * @Author: zhouhezhen
	  * @Date: 2015年5月15日 上午9:02:20
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月15日 上午9:02:20
	 */
	private Map<String, List<String>> getInitFlagTablePk(Connection conn, String dbName) throws Exception {
		PreparedStatement ps = conn.prepareStatement(QUERY_ALL_INIT_FLAG_TABLE_PK);
		ps.setFetchSize(100);
		ps.setString(1, "PRI");
		ps.setString(2, dbName);
		ps.setString(3, "INIT_FLAG");
		ResultSet rs = ps.executeQuery();
		Map<String, List<String>> mapTablePks = new HashMap<String, List<String>>();
		while(rs.next()){
			String tableName = rs.getString("tableName");
			String columnName = rs.getString("columnName");
			List<String> pks = mapTablePks.get(tableName);
			if(pks == null){
				pks = new ArrayList<String>();
				mapTablePks.put(tableName, pks);
			}
			pks.add(columnName);
		}
		return mapTablePks;
	}
	
	/**
	 * 
	  * 方法说明
	  * @Discription:获取表中记录,用key来标识每条记录
	  * @Author: zhouhezhen
	  * @Date: 2015年5月15日 下午1:04:13
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月15日 下午1:04:13
	 */
	private void transferRecordResultSet(ResultSet dataRs,
			DbTableDataInfo table, List<String> fields, List<String> pks) throws Exception {
		List<DbTableRecordDataInfo> records = new ArrayList<DbTableRecordDataInfo>();
		Map<String, DbTableRecordDataInfo> mapRecord = new HashMap<String, DbTableRecordDataInfo>();
		table.setRecords(records);
		table.setMapRecord(mapRecord);
		while(dataRs.next()){
			DbTableRecordDataInfo record = new DbTableRecordDataInfo();
			Map<String, String> mapField = new HashMap<String, String>();
			for(String field: fields){
				mapField.put(field, dataRs.getString(field));
			}
			record.setMapField(mapField);
			records.add(record);
			String key = "";
			//遍历所有主键，将所有主键拼接作为key,key如果为null，则将所有字段拼接作为key
			if(pks != null){
				for(String pk : pks){
					key += mapField.get(pk) + "_";
				}
			} else {
				for(String field: fields){
					key += mapField.get(field) + "_";
				}
			}
			mapRecord.put(key, record);
		}
	}
	
	
	/**
	 * 
	  * 方法说明
	  * @Discription:获取所有表
	  * @Author: zhouhezhen
	  * @Date: 2015年5月14日 下午6:20:48
	  * @ModifyUser：zhouhezhen
	  * @ModifyDate: 2015年5月14日 下午6:20:48
	 */
	private void getDbTableIntoDb(Db db, Connection conn, String dbName,String str) throws Exception {
		String table_sql =QUERY_MYSQLDB_TABLE_NAME_SQL;
		String column_sql = null;
//		if(str.length()>0){
//			System.out.println(1122);
//			column_sql=QUERY_MYSQLDB_DEFAULT_TABLE_STRUCTURE;
//		}else{
			column_sql = QUERY_MYSQLDB_TABLE_STRUCTURE;
//		}
		 //对应表与表信息
		System.out.println(dbName);
		Map<String, DbTable> mapTable = new HashMap<String, DbTable>();
		//表信息集合
		List<DbTable> dbTable = new ArrayList<DbTable>();		
		db.setMapTable(mapTable);
		db.setTables(dbTable);
		String tableName = null;
		String tableComment = null;
		PreparedStatement ps = conn.prepareStatement(table_sql);
		ps.setFetchSize(100);
		ps.setString(1, dbName);
		ResultSet rs = ps.executeQuery();
		//获取sql查询的属性列
		String[] columnNames = null;		
		while (rs.next()) {
			DbTable table = new DbTable();
			tableName = rs.getString("tablename");
			table.setName(tableName);
			tableComment = rs.getString("tablecomment");
			table.setComment(tableComment);
			PreparedStatement columnPs = conn.prepareStatement(column_sql);
			columnPs.setFetchSize(100);
			columnPs.setString(1, dbName);
			columnPs.setString(2, tableName);
			ResultSet columnRs = columnPs.executeQuery();
			if(columnNames == null){
				//得到结果集（columnPs）的结构信息	
				ResultSetMetaData rsmd = columnPs.getMetaData(); 
				int count = rsmd.getColumnCount(); 
				columnNames = new String[count];
				for(int i=0; i<count; i++){
					columnNames[i] = rsmd.getColumnName(i+1);
				}
			}
			table = tranferColumnResultSet(columnRs, table, columnNames);
			columnRs.close();
			columnPs.close();
			dbTable.add(table);
			mapTable.put(tableName, table);
		}
		rs.close();
		ps.close();
	}

	/**
	 * 
	 * 方法说明
	 * 
	 * @Discription:取得表字段
	 * @param rs
	 * @param table
	 * @throws SQLException
	 * @return DbTable
	 * @Author: zhouhezhen
	 * @Date: 2015年5月6日 上午9:58:55
	 * @ModifyUser：zhouhezhen
	 * @ModifyDate: 2015年5月6日 上午9:58:55
	 */
	private DbTable tranferColumnResultSet(ResultSet rs, DbTable table, String[] columnNames)
			throws SQLException {
		Map<String, DbColumn> mapDbC = new HashMap<String, DbColumn>();
		List<DbColumn> columnList = new ArrayList<DbColumn>();
		while (rs.next()) {
			DbColumn dbColumn = new DbColumn();
			for(int i=0; i<columnNames.length; i++){
				dbColumn.add(columnNames[i], rs.getString(columnNames[i]));
			}
			columnList.add(dbColumn);
			dbColumn.setName(rs.getString("COLUMN_NAME"));
			mapDbC.put(dbColumn.getName(), dbColumn);
		}
		table.setColumns(columnList);
		table.setMapColumn(mapDbC);
		return table;
	}

//	public Result getResultGoalDbTable(Db goalDefaultDb,Db baseDefaultDb,Result resultDb) {
//		if (resultDb == null) {
//			resultDb = new Result();
//		}
//		List<ResultTable> identicalResultTable = new ArrayList<ResultTable>();
////		for (DbTable tables : goalDb.getTables()) {
//			DbColumn column = tables.getMapColumn().get("COMPANY_ID");
//			if (null != column) {
//				ResultTable resultTable = new ResultTable();
//				List<ResultColumn> resultColumnList = new ArrayList<ResultColumn>();
//				for (DbColumn col : tables.getColumns()) {
//					ResultColumn resultColumn = new ResultColumn();
//					resultColumn.setMapProperty(col.getMapProperty());
////					resultColumn.setComment(columns.getName());
////					resultColumn.setDataType(columns.getDataType());
////					resultColumn.setIsNull(columns.getIsNull());
////					resultColumn.setIsPK(columns.getIsPK());
////					resultColumn.setMaxLength(columns.getMaxLength());
////					resultColumn.setName(columns.getName());
//					resultColumnList.add(resultColumn);
//				}
//				resultTable.setName(tables.getName());
////				resultTable.setIdenticalResultColumns(resultColumnList);
//				identicalResultTable.add(resultTable);
//			}
//		}
////		resultDb.setIdenticalResultTable(identicalResultTable);
//		return resultDb;
//	}

	public List<ResultTableDataInfo> getInitCompanyId(Result resultDb,
			String goalDbName, Properties prop, String companyId) {
		List<ResultTableDataInfo> tableInfoList = new ArrayList<ResultTableDataInfo>();
		Connection conn = DbUtil.getGoalDbConnection(prop);
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
//			for (ResultTable resultTable : resultDb.getIdenticalResultTable()) {
//				String sql = "select * from " + resultTable.getName()
//						+ " WHERE 1=1 AND COMPANY_ID = '" + companyId + "'";
//				rs = st.executeQuery(sql);
//				ResultTableDataInfo tableInfo = new ResultTableDataInfo();
//				List<ResultTableRecordDataInfo> columnInfoList = new ArrayList<ResultTableRecordDataInfo>();
//				while (rs.next()) {
//					ResultTableRecordDataInfo columnInfo = new ResultTableRecordDataInfo();
//					String[] str = new String[resultTable
//							.getIdenticalResultColumns().size()];
//					for (int i = 0; i < str.length; i++) {
//						str[i] = rs.getString(i + 1);
//					}
//					columnInfo.setColumnName(str);
//					columnInfoList.add(columnInfo);
//				}
//				tableInfo.setName(resultTable.getName());
//				tableInfo.setDetailList(columnInfoList);
//				tableInfoList.add(tableInfo);
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (Exception e) {

			}
		}
		return tableInfoList;
	}
}
