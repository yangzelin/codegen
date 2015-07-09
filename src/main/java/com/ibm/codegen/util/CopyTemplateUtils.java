package com.ibm.codegen.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title: 模版文件Copy</p>
 * <p>Description: </p>
 * @author 杨泽林
 * @version 1.0
 * @date  2015-4-20 下午03:55:15
 */
public class CopyTemplateUtils {
	
	/**
	 * 
	 * <p>Description:  <p>
	 * @param templateDir
	 * @param outDir
	 * @param params
	 * @throws IOException
	 * @auth 杨泽林
	 * @date 2015-4-20 下午03:55:43
	 * @version 1.0
	 */
	public static void copyTempelates(String templateDir, String outDir, Map<String, String> params) {
		try {
			copyTemplates(templateDir,outDir);
			replaceFileHoldCharacter(outDir,params);
		} catch (IOException e) {
			throw new IllegalArgumentException("copy template dir:["+templateDir+"] to outDir:["+outDir+"] params:["+params+"] failed!",e);
		}
	}
	
	/**
	 * 
	 * <p>Description:  <p>
	 * @param templateDir
	 * @param outDir
	 * @throws IOException
	 * @auth 杨泽林
	 * @date 2015-4-20 下午03:56:02
	 * @version 1.0
	 */
	public static void copyTemplates(String templateDir, String outDir) throws IOException{
		if(new File(templateDir).isDirectory()){
			BufferedInputFile.copyDir(templateDir, outDir);
		}else{
			BufferedInputFile.copyfile(templateDir, outDir);
		}
	}
	//替换文件占位符号
	public static void replaceFileHoldCharacter(String outDir, Map<String, String> holderParam){
		File f = new File(outDir);
		if(f.exists()){
			String fileName = f.getName();
			String newFilename = replaceCharacter(fileName,holderParam);
			if(f.isDirectory()){
				File[] fs = f.listFiles();
				for(File file : fs){
					replaceFileHoldCharacter(file.getPath(),holderParam);
				}
			}
			//如果文件名中有占位符
			if(!newFilename.equals(fileName)){
				//文件重命名
				File rnameFile = new File(f.getParent(),newFilename);
				//创建重命名文件父目录
				File rnameParentFile = rnameFile.getParentFile();
				if(rnameParentFile != null && !rnameParentFile.exists()){
					rnameParentFile.mkdirs();
				}
				//重命名文件存在删除该文件
				if(rnameFile.exists()){
					rnameFile.delete();
				}
				System.out.println("rename file flag["+f.renameTo(rnameFile)+"]: "+f.getAbsolutePath()+" -->"+rnameFile.getAbsolutePath()+"");
			}
		}
	}
	public static String replaceCharacter(String str,Map<String, String> params ){
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("\\$\\{(\\w+)\\}");
		Matcher m = p.matcher(str);
		while(m.find()){
			String key = m.group(1);
			String val = params.get(key) !=null ? params.get(key).toString() :"";
			m.appendReplacement(sb, val);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	public static List<Map<String,Object>> readFreemarkTempelate(String templateDir, Map<String,String> params){
		List<Map<String,Object>> templateList = new ArrayList<Map<String,Object>>();
		Map<File,String> fileContentMap = readTemplate(null,new File(templateDir));
		
		return templateList;
	}

	
	public static Map<File,String> readTemplate(Map<File,String> fileContentMap, File f){
		if(fileContentMap == null){
			fileContentMap = new HashMap<File,String>();
		}
		if(f.exists()){
			if(f.isFile()){
				//模版内容
				String fileContent = TrimToArrayUtlils.readFileAsString(f);
				fileContentMap.put(f, fileContent);
			}else if(f.isDirectory()){
				File[] fs = f.listFiles();
				for(File f2 : fs){
					readTemplate(fileContentMap, f2);
				}
			}
		}
		
		return fileContentMap;
	}
	
	public static void main(String[] args) {
		try {
			Map<String,String> params = new HashMap<String,String>();
			String templateDir = "template/freemark/${basePackage}/${moduleName}/dao/${className}Dao.java";
			String outDir = "out/${basePackage}/${moduleName}/dao/${className}Dao.java";
			params.put("basePackage", "com.gmcc");
			params.put("moduleName", "sys");
			params.put("className", "OdOrders");
			params.put("", "");
			copyTempelates(templateDir,outDir, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
