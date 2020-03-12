package com.ibm.codegen.db.def;

import java.math.BigDecimal;

import com.ibm.codegen.db.parse.NameUtils;

public class Column {
	private String tableName; // 表名     TABLE_NAME
	private String columnName;// 字段名 COLUMN_NAME
	private String columnType;// 数据字段类型 如 Number Varchar2等等 TYPE_NAME
	private BigDecimal columnSize;    // 数据库字段大小 columnSize 如varchar2(32)   COLUMN_SIZE
	private String columnClass;// 数据库字段映射Java对象java.math.BigDecimal md.getColumnClassName();
	private String columnComment; //字段注释
	private boolean pk = false;//是否主键字段
	private boolean isCommonColumn = false;// 是否公共字段
	
	public boolean isPk() {
		return pk;
	}
	public void setPk(boolean pk) {
		this.pk = pk;
	}
	public String getColumnComment() {
		if(columnComment !=null){
			//替换回车换行，防止报错
			columnComment = columnComment.replaceAll("\\n", "");
		}
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getColumnTypeName(){
		String columnTypeName = columnType;
		if(columnSize != null && !columnSize.equals(0) && !"DATE".equals(columnType.toUpperCase())){
			columnTypeName +="("+columnSize+")";
		}
		return columnTypeName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public BigDecimal getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(BigDecimal columnSize) {
		this.columnSize = columnSize;
	}
	public String getColumnClass() {
		return columnClass;
	}
	public void setColumnClass(String columnClass) {
		this.columnClass = columnClass;
	}
	@Override
	public String toString() {
		return "ColumnDef [tableName=" + tableName + ", columnName="
				+ columnName + ", columnType=" + columnType + ", columnSize="
				+ columnSize + ", columnClass=" + columnClass + "]";
	}
	public String getPropertyName(){
		return NameUtils.getPropertyName(columnName);
	}
	public String getMethodName(){
		String propertyName = getPropertyName();
		return propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
	}

	public boolean isCommonColumn() {
		return isCommonColumn;
	}

	public void setCommonColumn(boolean commonColumn) {
		isCommonColumn = commonColumn;
	}
}
