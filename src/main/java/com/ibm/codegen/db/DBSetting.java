package com.ibm.codegen.db;

import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import com.ibm.codegen.db.parse.MySqlParse;
import com.ibm.codegen.db.parse.OracleParse;
import com.ibm.codegen.db.parse.Parse;


public class DBSetting {
	public static String dbUrl;
	public static String dbUserName;
	public static String dbPassword;
	public static String commadWord;
	
	public static String control;
	public static String data;
	public static String log;
	
	public static String encode = "UTF-8";
	public static String oracleDriverName = "oracle.jdbc.driver.OracleDriver";
	public static String mysqlDriverName = "com.mysql.jdbc.Driver";
	public static String driverClassName = oracleDriverName;
	public static int connectionMaxActive = 100;
	public static int connectionMaxWait = 1000;
	public static boolean poolPreparedStatements = true;
	public static boolean defaultAutoCommit = true;
	
	public static String oraclePrefix = "jdbc:oracle:thin:@";
	public static String mysqlPrefix = "jdbc:mysql:";
	public static String oracleSeperator = ":";
	
	public static String LogPath = "";
	public static OutputStreamWriter logWriter;
	
	public static Connection getConnection() throws SQLException{
		Connection conn = null;
		BasicDataSource dataSource = new BasicDataSource();
		if(dbUrl.indexOf("mysql") > 0){
			driverClassName = mysqlDriverName;
		}
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUserName);
		dataSource.setPassword(dbPassword);
		dataSource.setMaxActive(connectionMaxActive);
		dataSource.setMaxWait(connectionMaxWait);
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setDefaultAutoCommit(defaultAutoCommit);
		conn = dataSource.getConnection();
		return conn;
	}
	
	public static String buildOracleURL(String host,int port, String dbName){
		String url = oraclePrefix+host+oracleSeperator+port+oracleSeperator+ dbName;
		return url;
	}
	public static String buildMySQLURL(String host,int port, String dbName){
		String url = mysqlPrefix+host+oracleSeperator+port+oracleSeperator+ dbName;
		return url;
	}
	public static Parse getParse(String dbUrl){
		Parse parse = new OracleParse();
		if(dbUrl.toLowerCase().startsWith(mysqlPrefix)){
			parse = new MySqlParse();
		}
		return parse;
	}
	public static Parse getDefualtParse(){
		Parse parse = getParse(dbUrl);
		return parse;
	}
}
