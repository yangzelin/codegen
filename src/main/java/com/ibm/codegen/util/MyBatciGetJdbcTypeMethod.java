package com.ibm.codegen.util;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class MyBatciGetJdbcTypeMethod implements TemplateMethodModel {

	@Override
	public Object exec(List arg) throws TemplateModelException {
		//
		if(arg != null && arg.size() > 0){
			String str = (String) arg.get(0).toString().trim().toUpperCase();
			String retVal = str;
			if("NUMBER".equals(str)){
				retVal = "DECIMAL";
			}else if("VARCHAR2".equals(str)){
				retVal = "VARCHAR";
			}
			return retVal;
		}
		return "";
	}

}
