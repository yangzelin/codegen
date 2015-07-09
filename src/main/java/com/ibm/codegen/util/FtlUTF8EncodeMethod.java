package com.ibm.codegen.util;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class FtlUTF8EncodeMethod implements TemplateMethodModel {

	@Override
	public Object exec(List arg) throws TemplateModelException {
		//
		if(arg != null && arg.size() > 0){
			String str = (String) arg.get(0);
			String retVal = str;
			retVal = UTF8EnCode.GBK2Unicode(str);
			return retVal;
		}
		return "";
	}

}
