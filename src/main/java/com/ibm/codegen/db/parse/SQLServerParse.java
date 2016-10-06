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

public class SQLServerParse implements Parse {

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
//					System.out.println(columnName+":"+value);
					
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
						column.setColumnSize(new BigDecimal(value.toString()));
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
			javaType = String.class.getName();
		}else if(columnTypeName.toUpperCase().startsWith("CHAR")
				|| columnTypeName.toUpperCase().startsWith("TEXT")
				|| columnTypeName.toUpperCase().startsWith("VARCHAR")
				|| columnTypeName.toUpperCase().startsWith("NVARCHAR")
				|| columnTypeName.toUpperCase().startsWith("NCHAR")
				|| columnTypeName.toUpperCase().startsWith("CHAR")){
			javaType = String.class.getName();
		}else if(columnTypeName.toUpperCase().startsWith("INT")
				||columnTypeName.toUpperCase().startsWith("BIT")
				||columnTypeName.toUpperCase().startsWith("SMALLINT")
				||columnTypeName.toUpperCase().startsWith("TINYINT")){
			javaType = Integer.class.getName();
		}else if(columnTypeName.toUpperCase().startsWith("FLOAT")){
			javaType = Float.class.getName();
		}else if(columnTypeName.toUpperCase().startsWith("DECIMAL")){
			javaType = BigDecimal.class.getName();
		}else if(columnTypeName.toUpperCase().startsWith("BIGINT")){
			javaType = Long.class.getName();
		}else if(columnTypeName.toUpperCase().startsWith("NUMBER")){
			javaType = Long.class.getName();
		}else if(columnTypeName.toUpperCase().startsWith("DATE")
				||columnTypeName.toUpperCase().startsWith("TIME")
				||columnTypeName.toUpperCase().startsWith("DATETIME")
				||columnTypeName.toUpperCase().startsWith("TIMESTAMP")
				||columnTypeName.toUpperCase().startsWith("SMALLDATETIME")
				||columnTypeName.toUpperCase().startsWith("SMALLDATETIME")
				||columnTypeName.toUpperCase().startsWith("SMALLDATETIME")
				){
			javaType = Date.class.getName();
		}
		
		return javaType;
	}
	
	public Map<String,String> getColumnComment(Connection conn,String tableName){
		Map<String,String> map = new HashMap<String, String>();
		String sql = "SELECT "+
		"  a.column_id AS No, "+
		"  a.name AS COLUMN_NAME, "+
		"  cast(isnull(g.[value],'-') as varchar(1000)) AS COMMENTS "+
		"FROM "+
		"  sys.columns a left join sys.extended_properties g "+
		" on (a.object_id = g.major_id AND g.minor_id = a.column_id) "+
		"WHERE "+
		"  object_id = "+
		"    (SELECT object_id FROM sys.tables WHERE name = '"+tableName.toUpperCase()+"')";
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

	@Override
	public List<String> getPKColumnName(Connection conn, String tableName) {
		List<String> pkColumnNames = new ArrayList<String>();
		String pkColumnName ="";
		String pkConstraintName = "";
		tableName = tableName != null ? tableName.trim().toUpperCase() : "";
		String sql = "SELECT TABLE_NAME, COLUMN_NAME,CONSTRAINT_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME='"+tableName.toUpperCase()+"'";
		
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

	@Override
	public String getTableComments(Connection conn, String tableName) {
		String tableComments = "";
		String sql = "select top 1000  "+
		"  ROW_NUMBER() OVER (ORDER BY a.object_id) AS No,   "+
		"  a.name AS TABLE_NAME,  "+
		"  cast(isnull(g.[value],'-') as varchar(1000)) AS COMMENTS "+
		"from  "+
		"  sys.tables a left join sys.extended_properties g  "+
		"  on (a.object_id = g.major_id AND g.minor_id = 0) "+
		"  where a.name = '"+tableName.toUpperCase()+"'";
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

}
