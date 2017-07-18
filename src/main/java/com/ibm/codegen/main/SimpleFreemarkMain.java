package com.ibm.codegen.main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.ibm.codegen.generate.FreemarkerCodeGenerator;
import com.ibm.codegen.util.TrimToArrayUtlils;

public class SimpleFreemarkMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File codeOuptDir = null;
		try {
			String tplDir = "template/tlog2";
			String outDir = "target/out";
			String tableNames = "PdDept,PdSection,PdType,PdArticle,PdClass,PdBrand,PdProduct,PdVendor,PdPayMethod";
			
			
			// 有默认值的参数
			String basePackage = "com.ibm.tlog.modules";
			String moduleName = "product";
			String encoding = "UTF-8";
			
			//获取表名列表
			String[] tblNamesAry = tableNames.split(",");
			for(String tableName : tblNamesAry){
				if(!TrimToArrayUtlils.isEmpty(tableName)){
					
					
					Map param = new HashMap();
					param.put("basePackage", basePackage);
					param.put("moduleName", moduleName);
					param.put("className", getClassName(tableName));
					param.put("functionName", getClassName(tableName).toLowerCase());
					
					FreemarkerCodeGenerator  freemarkGen = new FreemarkerCodeGenerator();
					freemarkGen.setTemplateDir(tplDir);
					codeOuptDir = freemarkGen.codegen(outDir, encoding, param);
					System.out.println("Gen Code Dir--->:"+codeOuptDir.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			System.out.println("codgen database connection error:"+e.getMessage());
		}
	}
	
	private static String getClassName(String str){
		String className = "";
		if(!TrimToArrayUtlils.isEmpty(str)){
			String[] ary = str.split("_");
			for(int i =0 ; i < ary.length; i++){
				String s = ary[i];
				if(i == 0){
					className += s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
				}
			}
			
		}
		
		return className;
	}

}
