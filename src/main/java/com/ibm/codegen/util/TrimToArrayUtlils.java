package com.ibm.codegen.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * <p>Title: 读取文件,把一行数据，按制定分割符分割为数组，再放到List中，工具类</p>
 * <p>Description: </p>
 * @author yangzelin
 * @version 1.0
 * @date Apr 26, 2012 4:19:16 PM
 */
public class TrimToArrayUtlils {
	
	@SuppressWarnings("rawtypes")
	public static String joinList(List list, String joinChar){
		StringBuffer sb = new StringBuffer(128);
		if(list != null && list.size() > 0){
			for(int i=0; i< list.size();i++){
				String val = getVal(list.get(i));
				sb.append(val);
				if(i < list.size()-1){
					sb.append(joinChar);
				}
			}
		}
		
		return sb.toString();
	}
	@SuppressWarnings("rawtypes")
	public static String join(Collection colection, String joinChar){
		StringBuffer sb = new StringBuffer(128);
		if(colection != null && colection.size() > 0){
			int i = 0;
			for(Iterator it = colection.iterator();it.hasNext();){
				sb.append(it.next());
				if(i < colection.size()-1){
					sb.append(joinChar);
				}
				i++;
			}
		}
		
		return sb.toString();
	}
	
	public static String getVal(Object obj){
		return obj != null ? obj.toString() : null;
	}
	public static boolean isEmpty(String str){
		return !(str != null && str.trim().length() > 0);
	}
	
	public static String trimToEmpty(String str){
		return str != null ? str.trim() : "";
	}
	public static String trimToEmpty(String str, String defaultValue){
		return str != null ? str.trim() : defaultValue;
	}
	public static String[] trimToArray(String[] sAry, int len){
		String[] disAry = new String[len];
		
		int offset = 0;
		for(int i = 0; i < len; i++){
			if(i < sAry.length){
				String val = null;
				while((i+offset < sAry.length) 
						&& isEmpty(val= trimToEmpty(sAry[i+offset]))){
					offset++;
				}
				disAry[i] = val;
			}else{
				disAry[i] = "";
			}
		}
		
		return disAry;
	}
	
	public static String readFileAsString(String filePath){
		File f = new File(filePath);
		return readFileAsString(f);
	}
	
	public static String readFileAsString(File f){
		StringBuffer sb = new StringBuffer(128);
		FileInputStream fis = null;
		InputStreamReader ir = null;
		BufferedReader br = null;
		try {
			if(!f.exists()){
				throw new IllegalArgumentException("File :["+f.getAbsolutePath()+"] is Not Exist!");
			}
			
			fis = new FileInputStream(f);
			ir = new InputStreamReader(fis);
			br = new BufferedReader(ir);
			char[] cAry = new char[1024];
			int len = 0;
			while((len = br.read(cAry)) != -1){
				sb.append(cAry, 0 , len);
			}
			
		} catch (IOException e) {
			throw new IllegalArgumentException("Read file as string error:"+getStackTrace(e));
		}finally{
			close(br);
			close(ir);
			close(fis);
		}
		
		return sb.toString();
	}
	/**
	 * 获取异常堆栈字符串<br>
	 * <p>
	 * <strong>Note:</strong> 把异常堆栈打印成字符串.
	 * 
	 * @param e
	 *            异常
	 * @return 异常堆栈字符串
	 */
	public static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
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
			throw new IllegalArgumentException("close iostream error:"+Tools.getStackTrace(e));
		}
		
	}
	
	public static ArrayList<String> readFileAsNoSplitList(String filePath){
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			File f = new File(filePath);
			if(!f.exists()){
				throw new IllegalArgumentException("File :["+f.getAbsolutePath()+"] is Not Exist!");
			}
			
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader ir = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(ir);
			String line = null;
//			int lineNum = 0;
			while((line = br.readLine()) != null){
//				lineNum++;
				if(!isEmpty(line)){
					list.add(trimToEmpty(line));
				}
				
			}
			
			br.close();
			ir.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static ArrayList<String[]> readFileAsList(String filePath){
		File f = new File(filePath);
		return readFileAsList(f, "\\s", 20);
	}
	
	public static ArrayList<String[]> readFileAsList(String filePath,String seperatorChar){
		File f = new File(filePath);
		return readFileAsList(f, seperatorChar, 20);
	}
	
	public static ArrayList<String[]> readFileAsList(String filePath, String seperatorChar, int arrayLen){
		File f = new File(filePath);
		return readFileAsList(f, seperatorChar, arrayLen);
	}
	
	public static ArrayList<String[]> readFileAsList(File f, String seperatorChar, int arrayLen){
		ArrayList<String[]> list = new ArrayList<String[]>();
		FileInputStream fis = null;
		InputStreamReader ir = null;
		BufferedReader br = null;
		try {
			if(!f.exists()){
				throw new IllegalArgumentException("File :["+f.getAbsolutePath()+"] is Not Exist!");
			}
			
			fis = new FileInputStream(f);
			ir = new InputStreamReader(fis);
			br = new BufferedReader(ir);
			String line = null;
//			int lineNum = 0;
			while((line = br.readLine()) != null){
//				lineNum++;
				if(!isEmpty(line)){
					String[] sAry = line.split(seperatorChar);
//					System.out.println(lineNum+":"+line);
					sAry = trimToArray(sAry, arrayLen);
					list.add(sAry);
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close(br);
			close(ir);
			close(fis);
		}
		
		return list;
	}

	public static String appendArrayToString(String[] strArray, int beingIndex){
		StringBuffer sBuf = new StringBuffer();
		if(beingIndex < strArray.length){
			for(int i = beingIndex; i < strArray.length;i++){
				String str = strArray[i];
				if(!isEmpty(str)){
					sBuf.append(str);
				}
			}
		}
		
		return sBuf.toString();
	}
	public static String appendArrayToString(String[] strArray, String appendChar,int beingIndex){
		StringBuffer sBuf = new StringBuffer();
		if(beingIndex < strArray.length){
			for(int i = beingIndex; i < strArray.length;i++){
				String str = strArray[i];
				if(!isEmpty(str)){
					sBuf.append(str).append(appendChar);
				}
			}
		}
		
		return sBuf.toString();
	}
	
	public static String getMethodName(String nodeName){
		String propertyName = "";
		String[] sAry = nodeName.split("_");
		propertyName = intalise(sAry[0]);
		if(sAry.length>1){
			for(int i = 1; i < sAry.length;i++){
				propertyName +=intalise(sAry[i]);
			}
		}
		
		return propertyName;
	}
	public static String getPropertyClassName(String nodeName){
		String propertyName = "";
		String[] sAry = nodeName.split("_");
		propertyName = intalise(sAry[0]);
		if(sAry.length>1){
			for(int i = 1; i < sAry.length;i++){
				propertyName +=intalise(sAry[i]);
			}
		}
		
		return propertyName;
	}
	public static String getPropertyName(String nodeName){
		String propertyName = "";
		String[] sAry = nodeName.split("_");
		propertyName = capitalise(sAry[0]);
		if(sAry.length>1){
			for(int i = 1; i < sAry.length;i++){
				propertyName +=intalise(sAry[i]);
			}
		}
		
		return propertyName;
	}
	
	
	public static String capitalise(String str){
		return str.substring(0,1).toLowerCase()+str.substring(1,str.length()).toLowerCase();
	}
	public static String intalise(String str){
		return str.substring(0,1).toUpperCase()+str.substring(1,str.length()).toLowerCase();
	}
	
	/**
	 * @param args
	 * @return void
	 * @author 云平台研发中心 杨泽林 Apr 20, 2012
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String fileName = "properties/data.txt";
		String fileName = "utils/utils/TrimToArrayUtlils.java";
		
		List<String[]> list =  readFileAsList(fileName);
		System.out.println(list.get(0)[0]);
		System.out.println();
	}

}
