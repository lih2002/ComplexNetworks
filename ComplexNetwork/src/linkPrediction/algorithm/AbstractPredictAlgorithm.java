package linkPrediction.algorithm;
import java.util.HashMap;
import java.util.Collection;


import java.util.Map;
import java.util.Vector;

import network.*;

public abstract class AbstractPredictAlgorithm{
	
	Map<String,Float> scores=new HashMap<String,Float>();
	Vector<String> indices;
	Vector<String> toBePredictedIndices;

	/**
	 * ����һ���ߵ�Ԥ�������
	 * @param net
	 * @param vA �ڵ�A
	 * @param vB �ڵ�B
	 * @param index Ԥ��ָ��
	 * @return ӳ�䣺Ԥ��ָ��->��Ԥ��ߵķ���ֵ
	 */
	public  float [] computeLinkScore(AbstractNetwork net, AbstractV vA,AbstractV vB){
		float [] score=new float[toBePredictedIndices.size()];
		int k=0;
		for(String index:toBePredictedIndices){
			score[k]=computeLinkScore(net,vA,vB,index);
			k++;
		}
		
		return score;
	};

	public abstract float computeLinkScore(AbstractNetwork net, AbstractV vA, AbstractV vB,
			String index);
	 
	public  void computeLinkScore(AbstractNetwork net, Collection links){
		
	};
	
	public Vector<String> getPredictedIndices(){
		return toBePredictedIndices;
	}
	
	/** 
	 * ����ָ�궼���м��㡣
	 */
	public void setToBePredictedIndices( ){
		if(indices==null){
			return;
		}
		
		for(String index:indices){
			setToBePredictedIndices(index);
		}
		
	}
	
	public void removePredictedIndices(String index){
		toBePredictedIndices.remove(index);
	}
	
	/**
	 * ָ������Щָ�����Ԥ�⡣
	 * @param index
	 */
	public void setToBePredictedIndices(String index){
		
		if(toBePredictedIndices==null){
			toBePredictedIndices=new Vector<String>();
		}	
	
			this.toBePredictedIndices.add(index);
	
		
	}
	

}
