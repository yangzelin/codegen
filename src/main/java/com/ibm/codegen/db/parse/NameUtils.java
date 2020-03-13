package com.ibm.codegen.db.parse;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.codegen.EventMap;
import com.ibm.codegen.EvriomentConst;
import com.ibm.codegen.util.IOUtils;

public class NameUtils {
	public static Map<String, String> tableNamePrefixMap = new HashMap<String, String>();

	// 是否初始化
	private static boolean init = false;
	// 生成类名时是否跳过模块名
	private static boolean skipModuleName = false;
	// 生成类名时是模块名称是否大写, skipModuleName=true 生效
	private static boolean moduleNameUppperCase = false;

	static{
		tableNamePrefixMap.put("T_","");
		tableNamePrefixMap.put("TB_","");
		tableNamePrefixMap.put("MC_","");
	}


	public static void init(){
		if(!init){
			String tablePrefixStr =trimToEmpty(EventMap.getValue(EvriomentConst.tablePreFix));
			String[] tablePrefixs = tablePrefixStr.split(",");
			for(String tablePrefix : tablePrefixs){
				if(!isEmpty(tablePrefix)){
					tableNamePrefixMap.put(tablePrefix,"");
				}
			}
			skipModuleName = EventMap.getBoolean(EvriomentConst.skipModuleName);
			moduleNameUppperCase = EventMap.getBoolean(EvriomentConst.moduleNameUppperCase);
			init = true;
		}
	}
	
	public static String getClassName(String tableName){
		init();
		String className = "";
		String subTableName = tableName.toUpperCase();
		//最多替换第二个下换线 如T_SYS_USER
		if(tableName.indexOf("_", tableName.indexOf("_")+1)>0){
			if(skipModuleName){
				subTableName = subTableName.substring(tableName.indexOf("_", tableName.indexOf("_")+1));
			}else{
				subTableName = subTableName.substring(tableName.indexOf("_")+1);
			}
			
		//表明结构是 T_USER
		}else if(tableName.indexOf("_")>0){
			subTableName = subTableName.substring(tableName.indexOf("_")+1);
		}
//		for(Iterator<String> it =tableNamePrefixMap.keySet().iterator();it.hasNext();){
//			String prefix = it.next();
//			String replaceChar = tableNamePrefixMap.get(prefix);
//			/**
//			 *  只需要替换一次，replace 会替换表名后面的业务字段名称，如T_SYS_EXPORT_TASK ，替换T_会匹配T_SYS 前的T_SYS和 EXPORT_
//			 *  这样替换结果是：SYS_EXPORTASK不是预期结果
//			 */
////			subTableName = subTableName.replace(prefix, replaceChar);
//			subTableName = replaceOnce(subTableName,prefix, replaceChar);
//		}
		subTableName = trimToEmpty(subTableName);
		if(!isEmpty(subTableName)){
			String[] names = subTableName.split("_");
			for(int i = 0; i < names.length; i++){
				className+=otherWord(names[i]);
			}
		}
		return className;
	}
	//只替换一次
	public static String replaceOnce(String sourceStr, String macther, String replaceStr ){
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile(macther);
		Matcher m = p.matcher(sourceStr);
		if(m.find()){
			@SuppressWarnings("unused")
			String group = m.group(0);
			String value = replaceStr;
			m.appendReplacement(sb, value);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public static String getPropertyName(String columnName){
		String propertyName = "";
		columnName = trimToEmpty(columnName);
		if(!isEmpty(columnName)){
			String[] names = columnName.split("_");
			for(int i = 0; i < names.length; i++){
				if(i == 0){
					propertyName+=getFirstWord(names[i]);
				}else{
					propertyName+=otherWord(names[i]);
				}
			}
		}
		return propertyName;
	}
	public static boolean isEmpty(String str){
		return !(str != null && str.trim().length() > 0);
	}
	public static String trimToEmpty(Object str){
		return str != null ? str.toString().trim() : "";
	}
	public static String trimToEmpty(String str){
		return str != null ? str.trim() : "";
	}
	public static String trimToEmpty(String str, String defaultValue){
		return str != null ? str.trim() : defaultValue;
	}
	public static String getFirstWord(String str){
		if(str ==null ||str.length()==0){
			return str;
		}else{
			return str.substring(0,1).toLowerCase()+str.substring(1).toLowerCase();
		}
	}
	public static String otherWord(String str){
		if(str ==null ||str.length()==0){
			return str;
		}else{
			return str.substring(0,1).toUpperCase()+str.substring(1).toLowerCase();
		}
		
	}
	public static String capitalise(String str){
		if(str ==null ||str.length()==0){
			return str;
		}else{
			return str.substring(0,1).toUpperCase()+str.substring(1);
		}
		
	}
	public static String unapitalise(String str){
		if(str ==null ||str.length()==0){
			return str;
		}else{
			return str.substring(0,1).toLowerCase()+str.substring(1);
		}

	}
	public static String getRightAppend(String source, String fillWord, int length){
		StringBuffer sb = new StringBuffer(source);
		int dataLen = source.length();
		if(dataLen < length){
			for(int i = 0; i < (length - dataLen); i++){
				sb.append(fillWord);
			}
		}
		return sb.toString();
	}
}
