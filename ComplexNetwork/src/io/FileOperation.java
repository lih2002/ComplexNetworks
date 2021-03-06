/**
 * 
 */
package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/**
 * 
 * @author 葛新
 *
 */
public class FileOperation {

	public static final String LINE_BREAK="\r\n";	//Windows 换行符
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//new FileOperation().readLine("f:\\aslink\\arkASClique\\200807arkASClique");
		new FileOperation().readLine("D:\\微云同步盘\\182015630\\practices\\Data\\Input\\01.doc");

	}
	
	public void readLine(String fileName ){
		
		try {
			FileReader fr = new FileReader(new File(fileName));
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			//String fileOutput = "f:\\aslink\\arkASClique\\20080703arkASClique";
			String fileOutput = "D:\\微云同步盘\\182015630\\practices\\Data\\Input\\01.doc";
			FileWriter fw=null;
			
			String []lineContent;
			int lineNum=1;
			
			int [] fileF = new int[21];
			for(int j=4 ;j<=20;j++){
				fileF[j]=0;
			}
			fileF[3]=1;
			
			 fw = new FileWriter(new File(fileOutput), true);
		
			while (line != null) {
				
				lineContent = line.split(" ");				
				int kValue = Integer.valueOf(lineContent[0]).intValue();
				//fw = new FileWriter();
//				if(kValue<19) {
//					line = br.readLine();
//					continue;
//				}
				
				if(fileF[kValue]==0 ){
					fileF[kValue]=1;
					//fileOutput =  "f:\\aslink\\arkASClique\\200807"+ (kValue<10? "0"+kValue:kValue)+"arkASClique";
					fileOutput =  "D:\\微云同步盘\\182015630\\practices\\Data\\Output\\01.txt";
					 fw = new FileWriter(new File(fileOutput), true);
				}
					
				fw.write(line+"\n");
				fw.flush();
								
				line = br.readLine();
			}
			
			fr.close();
			br.close();
		
		} catch (IOException ioe) {
	//		Debug.outn(ioe.fillInStackTrace());
	//		Debug.out("IOException in save String to file");
		
		}
		
	}
	/**
	 * 将字符串保存到文件中
	 * @param filePathName
	 * @param content
	 * @param append TODO
	 * @return
	 */
	public static boolean saveStringToFile(String filePathName,  String content, boolean append ){
		try {
			//Debug.outn(filePathName);
				FileWriter fw = new FileWriter(new File(filePathName), append);

				fw.write(content);

				fw.flush();
				fw.close();
			return true;
		} catch (IOException ioe) {
			//Debug.out("IOException in save String to file");
			return false;
		}
	}
	
	public static boolean saveToFile(int[]data,String filePathName,boolean append){
		
		StringBuffer dataString =new StringBuffer();
		for(int i=0;i<data.length;i++){
			dataString.append(data[i]+"\n");
			
		}
		String content = dataString.toString();
		return saveStringToFile(filePathName,content,append);
	}
	
//	public static boolean saveToFile(Vector data,String filePathName,boolean append){
//		
//		StringBuffer dataString =new StringBuffer();
//		for(int i=0;i<data.length;i++){
//			dataString.append(data[i]+"\n");
//			
//		}
//		String content = dataString.toString();
//		return saveStringToFile(filePathName,content,append);
//	}
	
	
	
	/**
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static Vector<String> getValueFromFile(String filePath, String fileName){
		
		Vector <String> content = new Vector();
		
		try {
			FileReader fr = new FileReader(new File(filePath+fileName));
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();			
		
			while (line != null) {				
				content.add(line);								
				line = br.readLine();
			}
			
			fr.close();
			br.close();		
		} catch (IOException ioe) {
			//Debug.outn(ioe.fillInStackTrace());		
		}
		
		return content;
	}//////////
	
	public static boolean ChangeFileName(File orgFile, String newName){
		return orgFile.renameTo(new File(orgFile.getParent()+"//"+newName) );
		
	}

}
