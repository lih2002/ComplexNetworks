package network;
/**
 *sorted node sequerence 
 * @author xge
 *
 */
import java.util.*;

/**
 * 
 * @author gexin
 *
 */
public class SortedNode {
	
	Vector<String> nodeV;
	Vector<Integer> valueV;
	
	public SortedNode( ){
		nodeV=new Vector<String>();
		valueV = new Vector<Integer>();		
	}
	
	public String getNode(int index){
		return nodeV.get(index);
	}
	
	public Integer getValue(int index){
		return valueV.get(index);
	}
	
	
	
	public void put(String n, Integer v){

		//the largest , add to the end 
		nodeV.add(nodeV.size(), n);
		valueV.add(valueV.size(),v);
	}
	
	public void remove(int index){
		nodeV.remove(index);
		valueV.remove(index);
	}
	
	public void decreaseValue(int index, int value){
		int v= valueV.get(index);
		valueV.remove(index);
		int newV=v-1;
		valueV.add(index, newV);
	}
	
	public int size(){
		return valueV.size();
	}
	
	public void ascSort(){
		this.ascSort(0,nodeV.size());
	}
	
	public void dscSort(){
		this.dscSort(0,nodeV.size());
	}
	
	public void dscSort(int start, int end){
		int i,j;
		//each node
		for(i=start+1;i<end;i++){
			
			for(j=i-1;j>=start&& valueV.get(j)<valueV.get(i) ;j--){
								
			}
					
			if(j==i-1){
				continue;
			}else{				
				valueV.add(j+1, valueV.remove(i));
				nodeV.add(j+1, nodeV.remove(i));
			}
			
		}////each node
		
	}////////////////////////
	
	public void ascSort(int start, int end){
		int i,j;
		//each node
		for(i=start+1;i<end;i++){
			
			for(j=i-1;j>=start&& valueV.get(j)>valueV.get(i) ;j--){
								
			}
					
			if(j==i-1){
				continue;
			}else{				
				valueV.add(j+1, valueV.remove(i));
				nodeV.add(j+1, nodeV.remove(i));
			}
			
		}////each node
		
	}////////////////////////
	
	public String toString(){
		String s="";
		for(int i=0;i<this.nodeV.size();i++){
			s=s+nodeV.get(i)+"  value "+  valueV.get(i)+"\n";
		}
		return s;
	}
}
