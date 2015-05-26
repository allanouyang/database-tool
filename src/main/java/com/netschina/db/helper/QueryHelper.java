/**
  * 文件说明
  * @Description:扩展说明 
  * @Copyright: 2015 dreamtech.com.cn Inc. All right reserved
  * @Version: V6.0
  */
package com.netschina.db.helper;

import java.sql.Connection;

import com.netschina.db.vo.Db;

/**  
 * @Author: zhouhezhen
 * @Date: 2015年5月7日 上午9:03:06 
 * @ModifyUser: zhouhezhen
 * @ModifyDate: 2015年5月7日 上午9:03:06 
 * @Version:V6.0
 */
public interface QueryHelper {

	public Db getDbInfoFromDb(Connection conn, String dbName, String defaultCompanyId,String str) throws Exception ;
}
