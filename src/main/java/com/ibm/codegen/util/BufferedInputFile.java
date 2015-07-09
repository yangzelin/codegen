package com.ibm.codegen.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
public class BufferedInputFile {
	
	public static String read(String filename) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		StringBuilder sb = new StringBuilder();
		while((s = in.readLine())!= null){
			sb.append(s);
		}
		in.close();
		return sb.toString();
	}
	public static void createDirs(String fileName){
		File f = new File(fileName);
		File parent = f.getParentFile();
		if(parent != null) {
			parent.mkdirs();
		}
	}
	public static void copyfile(String inputname,String outputname) throws IOException{
		createDirs(outputname);
		InputStream in = new FileInputStream(inputname);
		OutputStream out = new FileOutputStream(outputname);
		byte[] buffer = new byte[1024];
		int read = 0;
		while((read = in.read(buffer))!= -1){
			out.write(buffer,0,read);
		}
		in.close();
		out.close();
	}
	public static void copyDir(String inputname,String outputname) throws IOException{
		(new File(outputname)).mkdirs();
		File[] file=(new File(inputname)).listFiles();
		for(int i=0;i<file.length;i++){
			if(file[i].isFile()){
				file[i].toString();
				FileInputStream input=new FileInputStream(file[i]);
				//mkdir if destination does not exist
				File outtest = new File(outputname);
				if (!outtest.exists()){
					outtest.mkdir();
				}
				FileOutputStream output=new FileOutputStream(outputname+"/"+(file[i].getName()).toString());
				byte[] b=new byte[1024*5];
				int len;
				while((len=input.read(b))!=-1){
					output.write(b,0,len);
				}
				output.flush();
				output.close();
				input.close();
			}else if (file[i].isDirectory()){
				//System.out.print(file[i].getAbsolutePath()+ "/n" + file[i].getName());
				System.out.println("copy file:"+file[i].toString()+ "-->" + outputname + "//" + file[i].getName());
				copyDir(file[i].toString(),outputname+ "//" + file[i].getName());
			}
		}
	}
	public static void main(String[] args)throws IOException{
		//System.out.print(read("BOOTEX.LOG"));
		//copyDir("mysrc","des2");
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println(df.format(new Date()));
		//System.out.print(date.getMonth()+"/n");
		//System.out.print(date.getDate()+ "/n");
		//System.out.print(date.toGMTString()+"/n");
	}
}