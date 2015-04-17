package properties;

/**
 * Created on 2007-5-22
 *
 * @status 
 */

import java.util.*;
import network.*;
import tools.*;
import edu.uci.ics.jung.graph.*;
import io.FileOperation;

/**
 * partition of network which is not connected.
 * @author ge xin
 *
 */
public class Partitions {
	
	
	
	
	/**
	 * whether the network is connected.
	 * @param network
	 * @return
	 */
	public static boolean isConnected(AbstractNetwork network){
		Set <AbstractV>nodes=new HashSet <AbstractV> ();
		Queue <AbstractV> bfirst=new LinkedList <AbstractV>();	//采用广度优先算法
		AbstractV firstNode= network.randomGetVertex();
		
		nodes.add(firstNode);
		bfirst.offer(firstNode);
		while(bfirst.size()>0){
			AbstractV removed = bfirst.remove();
			
			Vector <AbstractV>ngbNodes=network.getNeighborVertices(removed);
			//int[] nbrs=network.getNeighborsArray(cidx);
			for(int i=0;i<ngbNodes.size();i++)
				if(!nodes.contains(ngbNodes.get(i))){
					nodes.add(ngbNodes.get(i));
					bfirst.offer(ngbNodes.get(i));	//插入队列
				}
		}//while
		
		if(nodes.size()==network.getVertexCount())
			return true;
		else
			return false;
	}
	
	public static boolean connected(Graph<AbstractV,AbstractE> net){
		Set <AbstractV>nodes=new HashSet <AbstractV> ();
		Queue <AbstractV> bfirst=new LinkedList <AbstractV>();
		
		AbstractV fNode = net.getVertices().iterator().next();
		
		nodes.add(fNode);
		bfirst.offer(fNode);
		while(bfirst.size()>0){
			AbstractV cidx=((AbstractV)bfirst.remove());
			
			Object [] nbrs=net.getNeighbors(cidx).toArray();
			
			for(int i=0;i<nbrs.length;i++)
				if(!nodes.contains(nbrs[i])){
					nodes.add((AbstractV)nbrs[i]);
					bfirst.offer((AbstractV)nbrs[i]);	//插入队列
				}
		}//while
		
		if(nodes.size()==net.getVertexCount())
			return true;
		else
			return false;
		
	}
	
	/**
	 * 得到网络区块信息
	 * @param modelNet
	 * @return 区块集合数组，数组长度为区块个数，每一个集合为当前区块所包含的节点
	 */
	public static Vector<Vector<AbstractV>> getPartitions(AbstractNetwork network){
		Vector <Vector<AbstractV>>result = new Vector<Vector<AbstractV>>();
		
		//List <AbstractNode>allNodes=new LinkedList<AbstractNode>();
		Vector <AbstractV> allNodes=network.getAllVertex();
		
		
		while(allNodes.size()>0){
			
			AbstractV seed=(AbstractV)allNodes.get(0);		//种子节点
			
			Vector <AbstractV>nodes=new Vector<AbstractV>();
			Queue <AbstractV> bfirst=new LinkedList<AbstractV>();		//仍采用广度优先算法
			
			nodes.add(seed);
			bfirst.offer(seed);
			allNodes.remove(seed);
			while(!bfirst.isEmpty()){
				AbstractV removed=bfirst.remove();
				Vector<AbstractV> nbrs=network.getNeighborVertices(removed);
				for(int i=0;i<nbrs.size();i++)
					if(!nodes.contains( nbrs.get(i) ) ){
						nodes.add(nbrs.get(i));
						bfirst.offer(nbrs.get(i));	//插入队列
						
						if(allNodes.contains(nbrs.get(i)))
							allNodes.remove(nbrs.get(i));
						else{
							//big error ...
						}
					}
			}//while(!bfirst.isEmpty())
			
			//nodes为当前区块的集合
			result.add(nodes);			
		}//while(allnodes.size()>0)
		

		
		
		return result;
	}	
	
	/**
	 * get the partition that contains the most nodes.
	 * @param network
	 * @return
	 */
	public static Vector <AbstractV> getLargestPartition(AbstractNetwork network){
		
		// connected network , the largest partitions is the original network
		if( isConnected(network) ) {
			return network.getAllVertex();
		}
		
		
		Vector<Vector<AbstractV>> partitions = getPartitions(network);
		int largestIndex=0;
		for(int i =0;i<partitions.size();i++){
			if( partitions.get(i).size()> partitions.get(largestIndex).size() ){
				largestIndex = i;
			}
		}
		
		return partitions.get(largestIndex);
	}///////////////////////
	
	/**
	 * get the largest partition of network and save partition to new network.
	 * @param network
	 * @param savePath
	 * @param fileName
	 * @param postfix
	 */
	public static void largestPartitionsToNetwork(AbstractNetwork network , String savePath, String fileName, String postfix){
		
		if(fileName==""){
			fileName = network.getNetID();
		}
		if(postfix==""){
			postfix=".largestPartitionNet";
		}
		Vector<AbstractV> partition= getLargestPartition(network);
		StringBuffer newNet = new StringBuffer();
		
		
		Set<AbstractV> hasOutput = new HashSet<AbstractV>();
		
		
		
		
		for(int i=0;i<partition.size();i++){
			
			AbstractV node =partition.get(i);
			hasOutput.add(node);
			Vector<AbstractV> ngbNodes = network.getNeighborVertices(node);
		
			for(int k=0;k<ngbNodes.size();k++){
				AbstractV ngb=ngbNodes.get(k);
				if( hasOutput.contains(ngbNodes) ) continue;
				newNet.append(node.getID()+" "+ngbNodes.get(k).getID()+"\n");
			}
		}
		
		FileOperation.saveStringToFile(savePath+"//"+fileName+postfix, newNet.toString(), false);
		
	}
	
}//////////////////////////////////////////////////////////////////////////////////
