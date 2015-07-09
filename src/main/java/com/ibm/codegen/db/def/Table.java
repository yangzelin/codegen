package com.ibm.codegen.db.def;

import java.util.ArrayList;
import java.util.List;

import com.ibm.codegen.db.parse.NameUtils;

public class Table {
	private String tableName;
	private String tableDesc;
	private List<Column> pkColumns = new ArrayList<Column>();
	private List<Column> columns = new ArrayList<Column>();
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableDesc() {
		return tableDesc;
	}
	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}
	public List<Column> getPkColumns() {
		return pkColumns;
	}
	public void setPkColumns(List<Column> pkColumns) {
		this.pkColumns = pkColumns;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public String getPropertyNames(){
		String propertyNames = "";
		if(columns.size() > 0){
			for(int i = 0; i < columns.size();i++){
				Column col = columns.get(i);
				if(col != null){
					propertyNames +=col.getPropertyName()+",";
				}
			}
			propertyNames.subSequence(0, propertyNames.length()-1);
		}
		
		return propertyNames;
	}
	public boolean contains(String propertyName){
		if(columns.size() > 0){
			for(int i = 0; i < columns.size();i++){
				Column col = columns.get(i);
				if(col != null && col.getPropertyName().equals(propertyName)){
					return true;
				}
			}
		}
		return false;
	}
	public String getClassName(){
		return NameUtils.getClassName(tableName);
	}
}
