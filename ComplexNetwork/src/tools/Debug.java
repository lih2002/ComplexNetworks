/**
 * Created on 2007-4-3
 *
 * @status 
 */
package tools;

import java.io.*;
import java.util.*;
import java.text.*;

/**
 * @author 葛新
 *
 */
public class Debug {
	
	public static void main(String [] args){
		Debug.outn(Debug.currentTime());
	}

	public static final String LINE_BREAK="\r\n";	//Windows 换行符
	
	public static void out(Object o){
		System.out.print(o);
	}
	public static void outn(Object o){
		System.out.println(o);
	}
	
	public static FileWriter file_start(String filename){
		try{
			return new FileWriter(new File(filename));
		}catch(IOException ioe){
			return null;
		}
	}
	
	
	public static void outn(int [] data ,String exp){
		Debug.outn(exp+" 元素数量："+data.length);
		for(int i= 0;i<data.length;i++){
			Debug.outn(i+" "+data[i]);
		}
		Debug.outn("-----------------");
	}
	
	public static void outn(float [] data,String exp){
		Debug.outn(exp+" 元素数量："+data.length);
		for(int i= 0;i<data.length;i++){
			Debug.outn(data[i]);
		}
		Debug.outn("-----------------");
	}
	
	public static void outn(double [] data){
		
		for(int i= 0;i<data.length;i++){
			Debug.outn(data[i]);
		}
	
	}
	
	public static void outn(double [] data,String exp){
		Debug.outn(exp+" 元素数量："+data.length);
		for(int i= 0;i<data.length;i++){
			Debug.outn(data[i]);
		}
		Debug.outn("-----------------");
	}
	
	public static void outn(String [] data, String exp){
		Debug.outn(exp+" 元素数量："+data.length);
		for(int i=0;i<data.length ; i++){
			Debug.outn(data[i]);
		}
		Debug.outn("------------------");
	}
	
	public static void outn(Set data , String  exp){
		Debug.outn(exp+" 元素数量："+data.size());
		Iterator it = data.iterator();
		int index=1;
		while(it.hasNext()){
			Debug.outn(index+" "+it.next());
			index++;
		}
		Debug.outn("-------------------");
	}
	
	/**
	 * 得到系统的当前时间，格式为：2008-12-15 08:52:03
	 * @return
	 */
	
	public static String currentTime(){
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " " + "hh:mm:ss");
		return tempDate.format(new java.util.Date());
 	}
	
	public static void file_out(FileWriter fw,String o){
		try{
			fw.write(o);
		}catch(IOException ioe){}
	}
	public static void file_outn(FileWriter fw,String o){
		file_out(fw,o+"\r\n");
	}
	public static void file_end(FileWriter fw){
		try{
			fw.flush();
			fw.close();
		}catch(IOException ioe){}
	}
	
}
