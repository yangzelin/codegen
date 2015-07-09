package com.ibm.codegen.db.parse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.ibm.codegen.db.def.Column;
import com.ibm.codegen.db.def.Table;

/**
 * <p>Title: 解析器 </p>
 * <p>Description: </p>
 * @author 杨泽林
 * @version 1.0
 * @date  2015-1-7 上午10:52:19
 */
public interface Parse {

	/**
	 * <p>Description:  获取解析表，获取表的对象<p>
	 * @param conn
	 * @param tableName
	 * @return
	 * @auth 杨泽林
	 * @date 2015-1-7 上午11:32:58
	 * @version 1.0
	 */
	public Table parseTable(Connection conn, String tableName);
	
	/**
	 * <p>Description:  获取解析表，获取表的主键字段<p>
	 * @param conn
	 * @param tableName
	 * @return
	 * @auth 杨泽林
	 * @date 2015-1-7 上午11:32:58
	 * @version 1.0
	 */
	public List<String> getPKColumnName(Connection conn, String tableName);
	
	/**
	 * 
	 * <p>Description:  获取字段注解 <p>
	 * @param conn
	 * @param tableName
	 * @return
	 * @auth 杨泽林
	 * @date 2015-1-7 上午11:32:16
	 * @version 1.0
	 */
	public Map<String, String> getColumnComment(Connection conn, String tableName);
	
	/**
	 * 
	 * <p>Description: 获取表的全部字段结果集 <p>
	 * @param conn
	 * @param tableName
	 * @return
	 * @auth 杨泽林
	 * @date 2015-1-7 上午11:32:37
	 * @version 1.0
	 */
	public ResultSet getTableColumnResultSet(Connection conn,String tableName);
	
	/**
	 * 
	 * <p>Description:获取字段的Java类型  <p>
	 * @param column
	 * @return
	 * @auth 杨泽林
	 * @date 2015-7-9 上午08:29:56
	 * @version 1.0
	 */
	public String getColumnJavaType(Column column);
}
