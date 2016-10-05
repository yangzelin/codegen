package com.ibm.codegen.main;

import com.ibm.codegen.EventMap;
import com.ibm.codegen.EvriomentConst;
import com.ibm.codegen.generate.CodeGenerator;
import com.ibm.codegen.generate.FreemarkerCodeGenerator;

public class FreemarkerMain {

	/**
	 * <p>Description:  <p>
	 * @param args
	 * @auth 杨泽林
	 * @date 2015-4-21 上午09:26:01
	 * @version 1.0
	 */
	public static void main(String[] args) {
		String tplDir=EventMap.getValue(EvriomentConst.templateDir,"src/main/resource/template/front");
		String outDir=EventMap.getValue(EvriomentConst.outRoot,"target/out");
		String encoding = EventMap.getValue(EvriomentConst.outEncoding,"UTF-8");
		String tableNames = EventMap.getValue(EvriomentConst.tableNames,"tb_app_user");
		
		
		
		//加载数据
		// oracel 驱动路径：jdbc:oracle:thin:zelinyang:orcl";
		// mysql  驱动路径：jdbc:mysql://zelinyang:3306/invest
		// sqlserver  驱动路径：jdbc:sqlserver://localhost:1433;DatabaseName=charts
		String dbUrl=EventMap.getValue(EvriomentConst.jdbcUrl,"jdbc:oracle:thin:zelinyang:orcl");
		String dbUserName=EventMap.getValue(EvriomentConst.jdbcUserName,"dev_opt");
		String dbPassword=EventMap.getValue(EvriomentConst.jdbcPassword,"dev_opt");
		
		//校验配置文件中参数
		EventMap.validConfigParam();
		
		CodeGenerator codeGen = new FreemarkerCodeGenerator();
		codeGen.codeGen(tplDir, outDir, encoding, tableNames, dbUrl, dbUserName, dbPassword);
	}

}
