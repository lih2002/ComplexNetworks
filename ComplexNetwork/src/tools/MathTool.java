/**
 * 
 */
package tools;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Set;
import java.util.HashSet;

/**
 * 数学工具类
 * @author 葛新
 *
 */
public class MathTool {
	
	public static void main(String [] args){
		MathTool mt=new MathTool();
		int [] a = new int[10];
		for(int k=0;k<10;k++){
			a[k]=5;
		}
		Debug.outn(mt.numberString(4, 99));
		
	}
	
	/**
	 * 数组拷贝
	 * @param s
	 * @return
	 */
	public static int [] copyArrays(int [] s){
		int [] d= new int[s.length];
		for(int k=0;k<d.length;k++){
			d[k]=s[k];
		}
		return d;		
	}
	
	/**
	 * 生成某一个范围的若干个不相等的随机非负整数
	 * @param bot 下界，随机数可以取下界
	 * @param top 上界，随机数小于上界
	 * @param count 个数
	 * @return
	 */
	public static int [] randomInt(int bot, int top, int count){
		if(bot<0){
			Debug.outn("random() 下界必须大于等于0");
			bot=0;
		}
		if(bot>top){
			Debug.outn("random() 上界必须大于等于下界");
			top=bot;
		}
		if( count> top - bot){
			Debug.outn("random() 个数必须小于等于上下界之差");
		}
		
		int [] ret = new int[count];
		for(int i = 0;i<ret.length;i++){
			ret[i]= -2;
		}
		
		for(int i = 0 ; i<count;i++){
			int value=-1;
			while(value == -1){
				value = bot + (int)(Math.random()*(top - bot));
				//Debug.outn("value= "+value);
				for(int j=0;j<ret.length;j++){ //随机数不能重复
					if(ret[j]==value){
						value=-1;
						break;
					}
				}
			}
			ret[i]= value;
			//Debug.out(ret[i]+" ");
		}
		return ret;
	}
	
	
	/**
	 * 某一概率的事件是否发生
	 * @param probability [0,1]范围的概率
	 * @return
	 */
	public static boolean happenProbability(double probability){
		if(probability<=0) return false;
		if(probability>=1) return true;
		if( Math.random() <=probability )return true;
		else return false;
		
	}
	
	/**
	 * 两个日期之间的时间数量，日期为 年 月。
	 * @param 第一个日期
	 * @param 第二个日期
	 * 
	 */
	public static int numOfTwoDates(int firDate,int secDate){
		if(firDate>secDate){
			System.out.println("第二个日期必须大于等于第一个日期");
			return -1;
		}
		int firYear=firDate/100;
		//Debug.outn(startYear);
		int firMonth = firDate - firYear*100;
		
		int secYear=secDate/100;
		//Debug.outn(endYear);
		int secMonth = secDate - secYear*100;
		//Debug.outn(firYear+" "+firMonth+" "+secYear+" "+secMonth);
		int num = 12*(secYear-firYear)+secMonth - firMonth +1 ; 
		return num;
	}
	
	/**
	 * 整形变量分布统计，即某些变量的出现次数
	 * @return 一维数组，数组索引为变量值， 索引对应的数组值为出现次数，
	 */
	public static int []  variablePDF(int [] input){
		int maxValue = 0;
		for(int i = 0 ;i<input.length;i++){
			maxValue = (maxValue>=input[i])?  maxValue:input[i];
		}
		int [] pdf = new int[maxValue+1];
		for(int j=0;j<input.length;j++){
			pdf[input[j]]++;
		}
		return pdf;
	}
	
	public static double []  percentPDF(int [] input){
		int []pdf = variablePDF(input);
		double []ppdf  = new double[pdf.length];
		for(int i=0;i<pdf.length;i++){
			ppdf[i]=(double)pdf[i]/input.length;
		}
		return ppdf;
	}
	/**
	 * 整型变量的累积度分布,即值大于等于n的的数量
	 * @param input
	 * @return
	 */
	public static int [] variableCCDF(int [] input){
		int[]pdf=variablePDF(input);
		int []ccdf=new int [pdf.length];
		int cumulative=0;
		
		for(int i=pdf.length-1;i>=0;i--){
			cumulative+=pdf[i];
			ccdf[i]=cumulative;
		}
		return ccdf;
	}
	
	public static double [] percentCCDF(int [] input){
		int []ccdf = variableCCDF(input);
		double []pccdf  = new double[ccdf.length];
		for(int i=0;i<ccdf.length;i++){
			pccdf[i]=(double)ccdf[i]/input.length;
		}
		return pccdf;
	}
	/**
	 * 集合操作，并集
	 * @param set 若干个集合
	 * @return 集合的并集
	 */
	public static <T> Set<T> unionSet(Set<Set<T>> set){
		Set <T> ret = new HashSet<T>();
		
		Iterator <Set<T>> setIt = set.iterator();

		while (setIt.hasNext()) {
			ret.addAll(setIt.next());
		}
		return ret;
	}
	
	
	
	public static Set <String> unionSet(Set<String> setA, Set<String> setB){
		Set<Set<String>> set = new HashSet<Set<String>>();
		set.add(setA);
		set.add(setB);
		return unionSet(set);
		
	}
	/**
	 * 集合操作，交集
	 * @param set 若干个集合
	 * @return 集合的交集
	 */
	public static <T> Set <T> intersectionSet(Set <Set<T>> set){
		Set <T> unionSet = unionSet(set);
				
	    Iterator <T > unionSetIt = unionSet.iterator();
	
		while (unionSetIt.hasNext()) {
			T setElement = unionSetIt.next();
			
			Iterator <Set<T>> setIt = set.iterator();

			while (setIt.hasNext()) {
				
				if( ! setIt.next().contains(setElement) ){
					unionSetIt.remove();
					break;
				}
			}
		}
		
		return unionSet;
	}
	
	public static Set <String> intersectionSet(Set<String> setA, Set<String> setB){
		Set<Set<String>> set = new HashSet<Set<String>>();
		set.add(setA);
		set.add(setB);
		return intersectionSet(set);
		
	}
	/**
	 * 集合操作，差集
	 * @param set 若干个集合
	 * @return 集合的差集
	 */
	public static <T> Set <T> diffSet(Set <Set<T>> set){
		Set <T> diffSet = new HashSet<T>();
		
		Iterator <T > unionElementIt = unionSet(set).iterator();
	
		while (unionElementIt.hasNext()) {
			T setElement = unionElementIt.next();
			
			Iterator <Set<T>> setIt = set.iterator();

			while (setIt.hasNext()) {
				
				if( ! setIt.next().contains(setElement) ){
					diffSet.add(setElement);
					break;
				}
			}
		}
		return diffSet;
	}
	
	/**
	 * 两个集合的差集，即集合A-（A，B交集）
	 * @param A
	 * @param B
	 * @return
	 */
	public static <T> Set <T> diffSet(Set<T> A,Set<T> B ){
		Set <T> diffSet =MathTool.copySet(A);
		Set<Set<T>> set = new HashSet<Set<T>>();
		set.add(A);
		set.add(B);
		/*集合AB交集*/
		Iterator <T > intersectionNodeIt = intersectionSet(set).iterator();
	
		while (intersectionNodeIt.hasNext()) {
			T nodeID = intersectionNodeIt.next();
			if(diffSet.contains(nodeID)){
				diffSet.remove(nodeID);
			}
			
		}
		return diffSet;
	}
	/**
	 * 判断两个集合是否相等，只有集合成员完全相同时返回true。
	 * @param setOne
	 * @param setTwo
	 * @return
	 */
	public static <T> boolean equalOfTwoSets(Set<T> setOne,Set<T> setTwo){
		if(setOne.size()!=setTwo.size()){
			return false;
		}
		Iterator <T> oneIt = setOne.iterator();
		while(oneIt.hasNext()){
			if( ! setTwo.contains(oneIt.next())){
				return false;
			}
		}
		return true;
	}
	
	public static float average(int [] data){
		return (float)sum(data)/data.length;
	}
	
	public static double average(double [] data){
		double sum=0.0;
		
		for(int i=0;i<data.length;i++){
			sum+=data[i];
		}
		return sum/data.length;
	}
	
	public static int sum(int []data){
		int sum=0;
		for(int i =0;i<data.length;i++){
			sum+= data[i];
		}
		return sum;
	}
	
	public static double sum(double []data){
		int sum=0;
		for(int i =0;i<data.length;i++){
			sum+= data[i];
		}
		return sum;
	}
	/**
	 * 整数数组的几何平均值
	 * @param data
	 * @return
	 */
	public static float geometryAvg(int [] data){
		int [] pdf = MathTool.variablePDF(data);
		//Debug.outn(pdf,"pdf");
		double avg=0;
	
		for(int i = 0; i<pdf.length;i++){
			if(pdf[i]==0){
				continue;
			}
		
			avg = avg+( pdf[i]*Math.log10(i) );
			
			
		}
		//Debug.outn("avg "+avg);
		return (float)Math.pow(10,  avg/data.length );
	}
	
	public static float average(float [] data){
		float sum=0;
		for(int i =0;i<data.length;i++){
			sum+= data[i];
		}
		return sum/data.length;
	}
	
	/**
	 * get standard deivation
	 * @param data
	 * @return
	 */
	public static double stdDeviation(int [] data){
		float avgData  = MathTool.average(data);
		double sum=0;
		for(int i=0;i<data.length;i++){
			sum+= (float)(data[i]-avgData)*(data[i]-avgData);
		}
		
		return Math.sqrt(sum/data.length);
	}
	/**
	 * 返回整型数组前num个最大值及其索引
	 * @param input 输入的数组
	 * @param num 前num个最大值
	 * @return 
	 */
	public static int [][] getMaxInt(int [] input , int num){
		
		if(num>input.length){
			return null;
		}
		
		int [][]result = new int[num][2];
		
		for(int i = 0 ; i<num ;i++){
			
			int max=input[0];
			int index= 0;
			for(int j=1;j<input.length;j++){
				
				if (input[j]==Integer.MIN_VALUE)
					continue;
				
				if(max<input[j]){
					index = j;
					max = input[index];
				}
			}
		
			result[i][0]=index;
			result[i][1]= max;
			input[index]=Integer.MIN_VALUE;
		}
		
		return result;
	}
	
	public static int getMinValue(int[]data){
		int min=data[0];
		for(int i=1;i<data.length;i++){
			if(data[i]<min){
				min=data[i];
			}
		}
		return min;
	}
	
	public static double getMax(double []data){
		
		double max=data[0];
		for(int i=1;i<data.length;i++){
			if(data[i]>max){
				max=data[i];
			}
		}
		return max;		
	}
	
	public static int getMax(int []data){
		
		int max=data[0];
		for(int i=1;i<data.length;i++){
			if(data[i]>max){
				max=data[i];
			}
		}
		return max;		
	}
	
	/**
	 * 深拷贝集合
	 */
	public static<T> Set<T> copySet(Set <T> source){
		Set <T>copy = new TreeSet<T>();
		Iterator <T> it = source.iterator();
		while(it.hasNext()){
			copy.add(it.next());
		}
		return copy;
		
	}
	/**
	 * 
	 * @param set
	 * @param split 元素之间的分隔符
	 * @return
	 */
	public static<T> String setToString(Set <T>set , String split){
		String result = "";
		Iterator <T>it = set.iterator();
		while(it.hasNext()){
			result=result+split+it.next().toString();
		}
		
		return result;
	}
	/**
	 * 计算全排列，m个取出n个排列
	 * @param m
	 * @param n
	 * @return
	 */
	public static long Arrangement(int m ,int n ){
		if(n<1 || m<1 || n>m) return 0;
		
		long result=1;
		for(int j=0;j<n;j++){
			result=result * (m-j);
			//Debug.outn(result);
		}
		
		for(int i=0;i<n;i++){
			result = result/(n-i);
		}
		return result;
	}
	
	/**
	 * return the string of number in certain length.
	 * e.g. length=5, get string "00055" for number 55.
	 * @param length
	 * @param number
	 * @return
	 */
	public static String numberString(int length , int number){
		String ns=Integer.toString(number);
		double maxNumber=(Math.pow(10, length)-1);
		maxNumber=maxNumber-1;
		
		if(number==0){
			for(int s=length-1;s>0;s--){
				ns="0"+ns;
			}
			return ns;
		}
		
		
		for(int i=0;i<length;i++){
			if(number<=Math.pow(10,i+1)-1 && number>=Math.pow(10, i) ){
				for(int s=length-1;s>i;s--){
					ns="0"+ns;
				}
				break;
			}
		}
		
		return ns;
	}
	
	
}///////////////////////////////////////////////////////////
