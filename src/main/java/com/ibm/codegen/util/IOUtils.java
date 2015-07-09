package com.ibm.codegen.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class IOUtils {
	/**
	 * 
	 * <p>
	 * Description: 关闭IO流
	 * </p>
	 * @param iostreamObj  IO流对象
	 * @author 杨泽林
	 * @date 2012-8-9
	 */
	public static void close(Object obj){
		try {
			closeIOStream(obj);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	/**
	 * 
	 * <p>
	 * Description: 关闭数据库
	 * </p>
	 * @param iostreamObj  IO流对象
	 * @author 杨泽林
	 * @date 2012-8-9
	 */
	public static void release(Object obj){
		try {
			releaseDataBaseObject(obj);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	/**
	 * 释放数据库流
	 * @param obj
	 * @author yangzelin 2012-10-23 下午03:25:29
	 */
	public static void releaseDataBaseObject(Object obj){
		try{
			if(obj instanceof Connection){
				Connection conn = (Connection)obj;
				if(conn != null && !conn.isClosed()){
					conn.close();
				}
			}else if(obj instanceof PreparedStatement){
				PreparedStatement stmt = (PreparedStatement)obj;
				if(stmt != null ){
					stmt.close();
				}	
			}else if(obj instanceof Statement){
				Statement stmt = (Statement)obj;
				if(stmt != null){
					stmt.close();
				}
			}else if(obj instanceof ResultSet){
				ResultSet rs = (ResultSet)obj;
				if(rs != null){
					rs.close();
				}
			}
		}catch(Exception e){
			throw new IllegalArgumentException("close database conneciton error:"+getStackTrace(e));
		}
	}
	
	public static void release(Statement stmt,Connection con){
		try{
			if(stmt!=null)stmt.close();
			if(con!=null)con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <p>
	 * Description: 关闭IO流
	 * </p>
	 * @param iostreamObj  IO流对象
	 * @author 杨泽林
	 * @date 2012-8-9
	 */
	public static void closeIOStream(Object iostreamObj){
		 
		try {
			if(iostreamObj == null){
				return ;
			}else if(iostreamObj instanceof InputStream){
				((InputStream) iostreamObj).close();
			}else if(iostreamObj instanceof OutputStream){
				((OutputStream) iostreamObj).close();
			}else if(iostreamObj instanceof InputStreamReader){
				((InputStreamReader) iostreamObj).close();
			}else if(iostreamObj instanceof OutputStreamWriter){
				((OutputStreamWriter) iostreamObj).close();
			}else if(iostreamObj instanceof Reader){
				((Reader) iostreamObj).close();
			}else if(iostreamObj instanceof Writer){
				((Writer) iostreamObj).close();
			}else{
				throw new IllegalArgumentException("obj is not iostream, it can not close!");
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("close iostream error:"+getStackTrace(e));
		}
		
	}
	public static String getStackTrace(Throwable e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	
	public static void main(String[] args) {
		try {
			File f = new File("src/commo/utils/1.txt");
			PrintStream ps = new PrintStream(f);
			ps.print("111111111111");
			closeIOStream(ps);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
