package com.ibm.codegen.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.ibm.codegen.EventMap;
import com.ibm.codegen.EvriomentConst;
import com.ibm.codegen.db.DBSetting;
import com.ibm.codegen.db.def.Column;
import com.ibm.codegen.db.def.Table;
import com.ibm.codegen.db.parse.NameUtils;
import com.ibm.codegen.db.parse.Parse;
import com.ibm.codegen.util.CopyTemplateUtils;
import com.ibm.codegen.util.IOUtils;
import com.ibm.codegen.util.TrimToArrayUtlils;

import com.mysql.jdbc.StringUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerCodeGenerator implements CodeGenerator {
	private Configuration cfg;
	private String tplDir;

	public static void main(String[] args) {

		try {
//			Configuration  cfg = new Configuration();
//			cfg.setDirectoryForTemplateLoading(new File("template/freemark"));
//			String tplFile = "${basePackage}/${moduleName}/dao/${className}Dao.java";
//			Template t = cfg.getTemplate(tplFile);
//			System.out.println(t);
			String tplDir="template/freemark";
			String outDir="out";
			String encoding = "UTF-8";
			Map param = new HashMap();
			FreemarkerCodeGenerator  freemarkGen = new FreemarkerCodeGenerator();
			freemarkGen.setTemplateDir(tplDir);
			freemarkGen.codegen(outDir, encoding, param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	public File codegen(String outDir,String encoding,
			Map param) {
		//替换基础目录
		String basePackage =  param.get(EvriomentConst.basePackage).toString();
		String basePackageDir = basePackage.replaceAll("\\.", "/");
		param.put(EvriomentConst.basePackageDir, basePackageDir);
		codegen(tplDir,outDir,encoding,param);


		File outDirFile = new File(outDir);
		return outDirFile;
	}
	protected File codegen(String tplDir, String outDir,String encoding,
			Map param) {
		File tplFile = new File(tplDir);
		if(tplFile.exists()){
			if(tplFile.isDirectory()){
				File[] fs = tplFile.listFiles();
				for(File f : fs ){
					codegen(f.getPath(),outDir,encoding,param);
				}
			}else{
				File codeFile = codgen(tplFile,outDir,encoding,param);
				System.out.println("Gen Code--->:"+codeFile.getAbsolutePath());
			}
		}

		return tplFile;
	}


	protected File codgen(File tplFile, String outDir,String encoding, Map param) {
		//模版文件输出文件相对路径
		String tplFileRelativePath = tplFile.getPath().substring(tplFile.getPath().indexOf(tplDir)+tplDir.length()+1);
		//模版文件输出文件
		File outFile = new File(outDir,tplFileRelativePath);
		//替换目录名称和文件名
		String relOuFile = CopyTemplateUtils.replaceCharacter(outFile.getPath(), param);
		outFile = new File(relOuFile);
		//创建目录
		File pFile = outFile.getParentFile();
		if(pFile != null && !pFile.exists()){
			pFile.mkdirs();
		}

		//获取Freemarker 模版文件
		Template t = getFreemarketTemplate(tplFileRelativePath, cfg);
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outFile);
			osw = new OutputStreamWriter(fos,encoding);
			t.process(param, osw);
			osw.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.close(osw);
			IOUtils.close(fos);
		}
		return outFile;
	}

	private Template getFreemarketTemplate(String templateFile,Configuration cfg ){
		try {
			Template t = cfg.getTemplate(templateFile);
			return t;
		} catch (Exception e) {
			throw new IllegalArgumentException("cannot get Freemarker Template File:["+templateFile+"] !",e);
		}
	}

	public Configuration getCfg() {
		if(cfg == null){
			cfg = new Configuration();
		}
		return cfg;
	}

	public void setCfg(Configuration cfg) {
		this.cfg = cfg;
	}


	@Override
	public void setTemplateDir(String tplDir) {
		try {
			this.tplDir = tplDir;
			cfg = getCfg();
			//设置FreeMarker的模版文件位置
			File baseDir = new File(this.tplDir);
			if(baseDir.exists()){
				cfg.setTemplateLoader(new FileTemplateLoader(baseDir));
			}else{
				throw new IllegalArgumentException("Set Freemarker Template Template Loder:["+baseDir.getAbsolutePath()+"] not exists!");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Set Freemarker Template Template Loder:["+tplDir+"] error:["+e.getMessage()+"]!",e);
		}
	}
	@Override
	public String getTemplateDir() {
		return this.tplDir;
	}


	@Override
	public File codeGen(String tplDir, String outDir, String encoding,
			String tableNames, String dbUrl, String dbUserName,
			String dbPassword) {
		//加载数据
		// oracel 驱动路径：jdbc:oracle:thin:zelinyang:orcl";
		// mysql  驱动路径：jdbc:mysql://zelinyang:3306/invest
		DBSetting.dbUrl=dbUrl;
		DBSetting.dbUserName=dbUserName;
		DBSetting.dbPassword=dbPassword;
		Parse oracleParse = DBSetting.getDefualtParse();
		try {
			if(StringUtils.isNullOrEmpty(tableNames)){
				List<String> tableNameList = oracleParse.getAllTable(DBSetting.getConnection());
				StringBuffer tableNameBuf = new StringBuffer(64);
				for (String tableName : tableNameList) {
					tableNameBuf.append(tableName).append(",");
				}
				tableNames = tableNameBuf.toString();
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException("表名为空，获取全部表名为空！error"+e.getMessage());
		}
		File codeOuptDir = null;
		try {

			//获取表名列表
			String[] tblNamesAry = tableNames.split(",");
			String commonColumns = EventMap.getValue(EvriomentConst.commonColumns).toUpperCase();
			List<String> commonColumnNameList = Arrays.asList(commonColumns.split(","));
			String dateStr = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			for(String tableName : tblNamesAry){
				if(!TrimToArrayUtlils.isEmpty(tableName)){
					Table  table = oracleParse.parseTable(DBSetting.getConnection(), tableName);
					Map param = new HashMap();
					param.put("basePackage", EventMap.getValue(EvriomentConst.basePackage));
					param.put("moduleName", EventMap.getValue(EvriomentConst.moduleName));
					param.put("className", table.getClassName());
					param.put("functionName", table.getClassName().toLowerCase());
					param.put("table", table);
					param.put("date", dateStr);
					param.put("commonColumns", commonColumns);
					// 新增类名大小写
//					param.put("className", table.getClassName().toLowerCase());
					param.put("classNameLower", NameUtils.unapitalise(table.getClassName()));

					// 处理公共字段
					List<Column> columns =  table.getColumns();
					for(Column col : columns){
						if(commonColumnNameList.contains(col.getColumnName())){
							col.setCommonColumn(true);
						}
					}

					// 处理字段描述中换行符号
					for (Column col : columns) {
						col.setColumnComment(replaceNewlineCharacter(col.getColumnComment()));
					}

					FreemarkerCodeGenerator  freemarkGen = new FreemarkerCodeGenerator();
					freemarkGen.setTemplateDir(tplDir);
					codeOuptDir = freemarkGen.codegen(outDir, encoding, param);
					System.out.println("Gen Code Dir--->:"+codeOuptDir.getAbsolutePath());
				}
			}
		} catch (SQLException e) {
			System.out.println("codgen database connection error:"+e.getMessage());
		}
		return codeOuptDir;
	}


	private static String replaceNewlineCharacter(String str){
		String ret = str != null ? str.replaceAll("\\r","") : "";
		return ret;
	}
}
