package com.ibm.codegen.db.parse;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.codegen.db.def.Column;
import com.ibm.codegen.db.def.Table;
import com.ibm.codegen.util.IOUtils;

public class OracleParse implements Parse {

	/**
	 * <p>Description:  获取所有的表名<p>
	 *
	 * @param conn
	 * @return
	 * @auth 杨泽林
	 * @date 2015-1-7 上午11:32:58
	 * @version 1.0
	 */
	@Override
	public List<String> getAllTable(Connection conn) {
		List<String> tableNames = new ArrayList<String>();
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet resultSet = dbmd.getTables(null, null, null, new String[]{"TABLE"});
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				tableNames.add(tableName);
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException("getALLTable error:"+e.getMessage());
		}
		return tableNames;
	}

	@Override
	public Table parseTable(Connection conn, String tableName) {
		Table table = new Table();
		try {
			Map<String, String> columnCommentMap = getColumnComment(conn, tableName);
			table.setTableName(tableName) ;
			String tableDesc = getTableComments(conn,tableName);
			table.setTableDesc(tableDesc);
			ResultSet databaseMetaResultSet = getTableColumnResultSet(conn, tableName);
			List<String> pkColumnNames = getPKColumnName(conn, tableName);
			while(databaseMetaResultSet.next()){
				ResultSetMetaData md = databaseMetaResultSet.getMetaData();
				Column column = new Column();
				int columnCount = md.getColumnCount();
				for(int i = 1; i <= columnCount; i++){
					String columnName = md.getColumnName(i);
//					String type = md.getColumnTypeName(i);
//					String tableName = md.getTableName(i);
//					String columClazzName = md.getColumnClassName(i);
					Object value = databaseMetaResultSet.getObject(i);

					if("TABLE_SCHEM".equals(columnName)){

					}else if("TABLE_NAME".equals(columnName)){
						column.setTableName(value.toString());
					}else if("COLUMN_NAME".equals(columnName)){
						column.setColumnName(value.toString());
						//设置主键字段
						if(pkColumnNames.contains(value.toString())){
							column.setPk(true);
						}
					}else if("TYPE_NAME".equals(columnName)){
						column.setColumnType(value.toString());
					}else if("COLUMN_SIZE".equals(columnName)){
						column.setColumnSize((BigDecimal) value);
					}

//					column.setColumnClass(columClazzName);
				}
				//字段数据库类型
				String type = column.getColumnType();
				if(type!= null && !type.startsWith("DATE")
						&&column.getColumnSize() != null && !column.getColumnSize().equals(0) ){
					type = type+"("+column.getColumnSize()+")";
				}
				//字段备注
				String columnComment = columnCommentMap.get(column.getColumnName());
				column.setColumnComment(columnComment);
				//字段Java类型
				String columnJavaType = getColumnJavaType(column);
				column.setColumnClass(columnJavaType);

				//设置主键字段
				if(column.isPk()){
					table.getPkColumns().add(column);
				}
				//添加字段
				table.getColumns().add(column);
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException("parse Table:["+tableName+"] error:"+e.getMessage());
		}

		return table;
	}

	public String getColumnJavaType(Column column){
		String javaType = String.class.getName();
		String columnTypeName = column.getColumnTypeName();
		BigDecimal columnSize= column.getColumnSize();
		if(columnTypeName.toUpperCase().startsWith("VARCHAR")){

		}else if(columnTypeName.toUpperCase().startsWith("NUMBER")){
			javaType = Long.class.getName();

		}else if(columnTypeName.toUpperCase().startsWith("DATE")||columnTypeName.toUpperCase().startsWith("TIME")){
			javaType = Date.class.getName();
		}

		return javaType;
	}

	public Map<String,String> getColumnComment(Connection conn,String tableName){
		Map<String,String> map = new HashMap<String, String>();
		String sql = "SELECT t1.COLUMN_NAME, t2.COMMENTS"+
		"  FROM user_tab_columns t1, user_col_comments t2"+
		" WHERE t1.TABLE_NAME = '"+tableName.toUpperCase()+"'"+
		"   AND t1.TABLE_NAME = t2.TABLE_NAME"+
		"   AND t1.COLUMN_NAME = t2.COLUMN_NAME";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				String columnName = rs.getString("COLUMN_NAME");
				String columnCommnet = rs.getString("COMMENTS");
				map.put(columnName, columnCommnet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			IOUtils.release(rs);
			IOUtils.release(ps);
		}

		return map;
	}

	public String getTableComments(Connection conn,String tableName){
		String tableComments = "";
		String sql = "SELECT TABLE_NAME,TABLE_TYPE,COMMENTS"+
				"  FROM user_tab_comments t"+
				" WHERE t.TABLE_NAME = '"+tableName.toUpperCase()+"'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				tableComments = rs.getString("COMMENTS");
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			IOUtils.release(rs);
			IOUtils.release(ps);
		}

		return tableComments;
	}

	public ResultSet getTableColumnResultSet(Connection conn,String tableName){
		ResultSet rs = null;
		try {
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			rs = databaseMetaData.getColumns(null, null, tableName.toUpperCase(), "%");
			//获取主键
			ResultSet rs2 = databaseMetaData.getPrimaryKeys(null, null, tableName.toUpperCase());
			while(rs2.next()){
//				System.out.println("Key:"+rs2.getString(4));
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException("get table["+tableName+"]'s columns from connection:["+conn.toString()+"] error:["+e.getMessage()+"]");
		}
		return rs;
	}

//	public String getTableComments2(Connection conn,String tableName){
//		String tableCommenets = null;
//		ResultSet rs = null;
//		try {
//			DatabaseMetaData databaseMetaData = conn.getMetaData();
//			//获取主键
//			rs = databaseMetaData.getTables(conn.getCatalog(), conn.getMetaData().getUserName().toUpperCase(), tableName.toUpperCase(),new String[]{"TABLE","REMARKS"});
//			int columnSize = rs.getMetaData().getColumnCount();
//			while(rs.next()){
//
//				for(int i =1; i<=columnSize; i++)
//				System.out.println(rs.getMetaData().getColumnName(i)+":"+rs.getString(i));
//			}
//		} catch (SQLException e) {
//			throw new IllegalArgumentException("get table["+tableName+"]'s columns from connection:["+conn.toString()+"] error:["+e.getMessage()+"]");
//		}finally{
//			IOUtils.release(rs);
//		}
//		return tableCommenets;
//	}

	@Override
	public List<String> getPKColumnName(Connection conn, String tableName) {
		List<String> pkColumnNames = new ArrayList<String>();
		String pkColumnName ="";
		String pkConstraintName = "";
		tableName = tableName != null ? tableName.trim().toUpperCase() : "";
		String sql = "select t.owner,t.constraint_name,t.column_name  from user_cons_columns t where constraint_name = (select constraint_name from user_constraints where table_name = '"+tableName+"' and constraint_type = 'P')";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				pkColumnName = rs.getString("COLUMN_NAME");
				pkConstraintName = rs.getString("CONSTRAINT_NAME");
				pkColumnNames.add(pkColumnName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			IOUtils.release(rs);
			IOUtils.release(ps);
		}
		return pkColumnNames;
	}

}
