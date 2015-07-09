package com.ibm.codegen;

import java.util.HashMap;
import java.util.Map;

public class EvriomentConst {
	//系统
	public static final String className = "className";
	//工程基本包名
	public static final String basePackage = "basePackage";
	//工程基本包目录
	public static final String basePackageDir = "basePackageDir";
	//工程模块
	public static final String moduleName = "moduleName";
	
	//表明前缀，生成代码是去掉,多个用";"分割
	public static final String tablePreFix = "tablePrefix";
	
	//代码模版目录
	public static final String templateDir = "tplDir";
	//代码输入目录
	public static final String outRoot = "outRoot";
	//代码输出编码
	public static final String outEncoding = "outEncoding";
	//需要生成代码的表，多个用","分割
	public static final String tableNames = "tableNames";
	
	//jdbc 配置
	public static final String jdbcUrl = "jdbc.url";
	public static final String jdbcDriver = "jdbc.driver";
	
	public static final String jdbcUserName = "jdbc.username";
	public static final String jdbcPassword = "jdbc.password";
	public static final String jdbcSchema = "jdbc.schema";
	
	
	//数据库类和Java类型映射Map
	public static Map<Class,Class> dbTypeConvertJavaTypeMap  = new HashMap<Class, Class>();
	static{
		dbTypeConvertJavaTypeMap.put(java.sql.Timestamp.class, java.util.Date.class);
		dbTypeConvertJavaTypeMap.put(java.sql.Date.class, java.util.Date.class);
		dbTypeConvertJavaTypeMap.put(java.sql.Time.class, java.util.Date.class);
		dbTypeConvertJavaTypeMap.put(java.lang.Byte.class, java.lang.Integer.class);
		dbTypeConvertJavaTypeMap.put(java.lang.Short.class, java.lang.Integer.class);
		dbTypeConvertJavaTypeMap.put(java.math.BigDecimal.class, java.math.BigDecimal.class);
		dbTypeConvertJavaTypeMap.put(java.sql.Clob.class, java.lang.String.class);
	}
	
}
