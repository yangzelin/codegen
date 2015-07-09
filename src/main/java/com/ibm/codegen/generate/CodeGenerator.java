package com.ibm.codegen.generate;

import java.io.File;
import java.util.Map;

/**
 * <p>Title: 代码生成器接口</p>
 * <p>Description: </p>
 * @author 杨泽林
 * @version 1.0
 * @date  2015-4-20 下午04:11:45
 */
public interface CodeGenerator {
	
	public void setTemplateDir(String tplDir);
	
	public String getTemplateDir();
	
	public File codegen(String  outDir,String encoding, Map param);
	
	/**
	 * 代码生成器主要方法
	 * @param tplDir	模版目录（template file Dir ）
	 * @param outDir	 代码输出目录
	 * @param encoding	 代码编码
	 * @param tableNames 生成表名，多个用逗号分隔
	 * @param dbUrl		  数据库URL
	 * @param dbUserName 数据库用户
	 * @param dbPassword 数据库密码
	 * @return
	 */
	public File codeGen(String tplDir, String outDir, String encoding, String tableNames, String dbUrl, String dbUserName,String dbPassword);
}
