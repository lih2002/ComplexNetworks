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
	 * 计算一条边的预测分数。
	 * @param net
	 * @param vA 节点A
	 * @param vB 节点B
	 * @param index 预测指标
	 * @return 映射：预测指标->被预测边的分数值
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
	 * 所有指标都进行计算。
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
	 * 指定对哪些指标进行预测。
	 * @param index
	 */
	public void setToBePredictedIndices(String index){
		
		if(toBePredictedIndices==null){
			toBePredictedIndices=new Vector<String>();
		}	
	
			this.toBePredictedIndices.add(index);
	
		
	}
	

}
