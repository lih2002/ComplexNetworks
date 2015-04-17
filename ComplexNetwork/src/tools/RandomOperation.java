package tools;

import java.util.*;

/**
 * 
 * @author gexin
 *
 */
public class RandomOperation {

	
	
	/**
	 * @param <T>
	 * @param collection
	 * @param rate
	 * @return
	 */
	public static <T> Set<T> randomSelectSome(Collection<T> collection,float rate){
	
		return randomSelectSome(collection, (int)(collection.size()*rate));
	}
	
	/**
	 * 
	 * @param <T>
	 * @param collection 
	 * @param count the number of elements selected.
	 * @return
	 */
	public static <T> Set<T> randomSelectSome(Collection<T> collection,int count){
		
		Set<T> result=new HashSet<T>();
		
		int []indexs=randomInt(0, collection.size(), count);
		int indexCount=0;
		int elementCount=0;
		for(T element:collection){
			if(indexCount==count){
				break;
			}
			if(elementCount==indexs[indexCount]){
				result.add(element);
				indexCount++;
			}
			
			elementCount++;
		}	
		
		return result;
	}
	
	
	
	
	/**
	 * @param <T>
	 * @param collection
	 * @return
	 */
	public  static <T> T RandomSelectOne(Collection<T> collection){
		T result=null;
		int index=randomIndexIn(collection.size());
		int count=0;
	
		for(T element:collection){
			if(count==index){
				result= element;
				break;
			}
			count++;
		}
		return result;
	}

	/**
	 * �������Ԫ��˳��
	 * @param vector
	 */
	public static <T> void randomDisarrange(Collection <T> vector) {
	
	}

	
	/**
	 * random index in [0,range)
	 * @param range
	 * @return
	 */
	 public  static int randomIndexIn(int range){
		 return randomInt(0,range,1)[0];
	 }
	
	/**
	 * ����ĳһ����Χ�����ɸ�����ȵ�����Ǹ�����
	 * @param bot �½磬���������ȡ�½�
	 * @param top �Ͻ磬�����С���Ͻ�
	 * @param count ����
	 * @return
	 */
	public static int [] randomInt(int bot, int top, int count){
		if(bot<0){
			Debug.outn("random() �½������ڵ���0");
			bot=0;
		}
		if(bot>top){
			Debug.outn("random() �Ͻ������ڵ����½�");
			top=bot;
		}
		if( count> top - bot){
			Debug.outn("random() ��������С�ڵ������½�֮��");
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
				for(int j=0;j<ret.length;j++){ //����������ظ�
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
	

}
