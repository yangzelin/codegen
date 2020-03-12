package com.ibm.codegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.ibm.codegen.db.DBSetting;
import com.ibm.codegen.util.TrimToArrayUtlils;

public class EventMap {
	private static Map<String,String> eventMap = new HashMap<String,String>();
	public static String defaultConfigProperties = "config.properties";
	
	static{
		loadProperties(defaultConfigProperties);
	}
	public static void loadProperties(String propFile){
		Properties pro = new Properties();
		File f =new File(propFile);
		try {
			//文件存在
			if(f.exists()){
				FileInputStream fis = new FileInputStream(f);
				pro.load(fis);
			}else{
				InputStream is = EventMap.class.getClassLoader().getResourceAsStream(propFile);
				if(is != null){
					pro.load(is);
				}else{
					System.out.println("Loading config propertis:["+f.getAbsolutePath()+"] not exist！");
				}
			}
			if(pro.size()>0){
				Map tempMap = pro;
				eventMap.putAll(tempMap);
			}
		} catch (Exception e) {
			System.out.println("Loading config propertis:["+f.getAbsolutePath()+"] failed："+e.getMessage());
		}
	}
	
	public static void put(String key,String value){
		if(eventMap == null){
			eventMap = new HashMap<String,String>(); 
		}
		eventMap.put(key, value);
	}
	public static boolean getBoolean(String key){
		boolean retVal = getBoolean(key, false);
		return retVal;
	}
	public static boolean getBoolean(String key, boolean defaultVal){
		boolean retVal = defaultVal;
		String val = getValue(key);
		if(val != null){
			retVal = val.trim().equalsIgnoreCase("true") || val.trim().equalsIgnoreCase("1");
		}
		return retVal;
	}
	public static String getValue(String key){
		String val = getValue(key,"");
		return val;
	}
	public static String getValue(String key, String defaultValue){
		String val = defaultValue;
		if(key != null){
			val = eventMap.get(key);
			if(val == null){
				val = defaultValue;
			}
		}
		return val;
	}
	//校验代码生成器参数是否正确
	public static void validConfigParam(){
		List<String> errList = new ArrayList<String>();
		String tplDir=EventMap.getValue(EvriomentConst.templateDir,"src/main/resource/template/front");
		String outDir=EventMap.getValue(EvriomentConst.outRoot,"target/out");
		String encoding = EventMap.getValue(EvriomentConst.outEncoding,"UTF-8");
		String tableNames = EventMap.getValue(EvriomentConst.tableNames,"tb_app_user");
		if(TrimToArrayUtlils.isEmpty(tplDir)){
			errList.add(EvriomentConst.templateDir);
		}
		if(TrimToArrayUtlils.isEmpty(outDir)){
			errList.add(EvriomentConst.outRoot);
		}
		if(TrimToArrayUtlils.isEmpty(encoding)){
			errList.add(EvriomentConst.outEncoding);
		}
		if(TrimToArrayUtlils.isEmpty(tableNames)){
			errList.add(EvriomentConst.tableNames);
		}
		
		//加载数据
		String dbUrl=EventMap.getValue(EvriomentConst.jdbcUrl);
		String dbUserName=EventMap.getValue(EvriomentConst.jdbcUserName);
		String dbPassword=EventMap.getValue(EvriomentConst.jdbcPassword);
		
		String basePackage = EventMap.getValue(EvriomentConst.basePackage);
		String moduleName = EventMap.getValue(EvriomentConst.moduleName);
		

		if(TrimToArrayUtlils.isEmpty(dbUrl)){
			errList.add(EvriomentConst.jdbcUrl);
		}
		if(TrimToArrayUtlils.isEmpty(dbUserName)){
			errList.add(EvriomentConst.jdbcUserName);
		}
		if(TrimToArrayUtlils.isEmpty(dbPassword)){
			errList.add(EvriomentConst.jdbcPassword);
		}
		if(TrimToArrayUtlils.isEmpty(basePackage)){
			errList.add(EvriomentConst.basePackage);
		}
		if(TrimToArrayUtlils.isEmpty(moduleName)){
			errList.add(EvriomentConst.moduleName);
		}
		
		if(errList.size()>0){
			String errMsg = "Config File param ["+TrimToArrayUtlils.joinList(errList, ", ")+"] can't be null";
			throw new IllegalArgumentException(errMsg);
		}
	}
	public static void main(String[] args) {
		System.out.println(EventMap.getValue(EvriomentConst.basePackage));
	}
}
