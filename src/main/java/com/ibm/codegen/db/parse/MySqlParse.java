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

public class MySqlParse implements Parse {

	@Override
	public Table parseTable(Connection conn, String tableName) {
		Table table = new Table();
		try {
			Map<String, String> columnCommentMap = getColumnComment(conn, tableName);
			table.setTableName(tableName) ;
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

	@Override
	public List<String> getPKColumnName(Connection conn, String tableName) {
		List<String> pkColumnNames = new ArrayList<String>();
		ResultSet rs = null;
		try {
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			//获取主键
			rs = databaseMetaData.getPrimaryKeys(null,null, tableName.toUpperCase());
			while(rs.next()){
				pkColumnNames.add(rs.getString(4));
//				System.out.println("Key:"+rs2.getString(4));
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException("get table["+tableName+"]'s columns from connection:["+conn.toString()+"] error:["+e.getMessage()+"]");
		}
		return pkColumnNames;
	}

	@Override
	public Map<String, String> getColumnComment(Connection conn,
			String tableName) {
		Map<String,String> map = new HashMap<String, String>();
		String sql = "select table_name,column_name,ordinal_position,data_type,column_type,column_comment from information_schema.COLUMNS t where t.table_name = '"+tableName.toUpperCase()+"'";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				String columnName = rs.getString("COLUMN_NAME");
				String columnCommnet = rs.getString("COLUMN_COMMENT");
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

	@Override
	public ResultSet getTableColumnResultSet(Connection conn, String tableName) {
		ResultSet rs = null;
		try {
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			rs = databaseMetaData.getColumns(null, null, tableName.toUpperCase(), "%");
		} catch (SQLException e) {
			throw new IllegalArgumentException("get table["+tableName+"]'s columns from connection:["+conn.toString()+"] error:["+e.getMessage()+"]");
		}
		return rs;
	}

	@Override
	public String getColumnJavaType(Column column) {
		String javaType = String.class.getName();
		String columnTypeName = column.getColumnTypeName();
		BigDecimal columnSize= column.getColumnSize();
		if(columnTypeName.toUpperCase().startsWith("VARCHAR")){
			
		}else if(columnTypeName.toUpperCase().startsWith("NUMBER")||columnTypeName.toUpperCase().startsWith("INT")||columnTypeName.toUpperCase().startsWith("BIGINT")){
			javaType = Long.class.getName();
			
		}else if(columnTypeName.toUpperCase().startsWith("DATE")||columnTypeName.toUpperCase().startsWith("TIME")){
			javaType = Date.class.getName();
		}
		
		return javaType;
	}

}
