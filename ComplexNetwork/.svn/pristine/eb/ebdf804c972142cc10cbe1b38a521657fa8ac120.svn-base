/**
 * 
 */
package properties;

import network.*;
import tools.*;
import java.util.*;
import edu.uci.ics.jung.algorithms.shortestpath.*;
import edu.uci.ics.jung.graph.*;;


/**
 * The shortest path of network.
 * @author ge xin
 */
public class ShortestPath {
	
	
	/**
	 * 
	 */
	public ShortestPath() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 网络中所有节点最短路径长度平均值
	 * @param net
	 * @return
	 */
	public double getNetworkShortestPath(AbstractNetwork net) {
 
		double sp = 0;
		
		
			

		return sp;
	}


	
	
	public float getAverageNetworkShortestPath(AbstractNetwork net){
		return 0;
	}
	
	public static int[] getHopCount(AbstractNetwork net){
		Vector<Integer> allHopCount = new Vector<Integer>();
				
		Iterator<AbstractV> allNodesItor=net.getVertices().iterator();
		
		DijkstraShortestPath DjkShorestPath = new DijkstraShortestPath(net);
		
		while(allNodesItor.hasNext()){
			AbstractV nodeA= allNodesItor.next();
			
			Iterator<AbstractV> allNodesItorB=net.getVertices().iterator();
			while(allNodesItorB.hasNext()){
				AbstractV nodeB=allNodesItorB.next();
				int hopCount = DjkShorestPath.getDistance(nodeA, nodeB).intValue();
				allHopCount.add(hopCount);
			}
		}
		
		int [] hopCount = new int[allHopCount.size()];
		for(int k=0;k<allHopCount.size();k++){
			hopCount[k]=allHopCount.get(k);
		}
		
		return hopCount;
		
	}
	
	/**
	 * 
	 * @param net
	 * @return
	 */
	public static float[] getAvgAndReciprocalHopCount(AbstractNetwork net){
		
		float []retHopCount=new float[2];
		if(!Partitions.isConnected(net)){ //for disconnected network, hopcount = -1.
			retHopCount[0]=-1;
		}
		
		float sumHopCount=0 ;
		float sumReciprocalHopCount=0;
		
		
		//int[] allNodeID = net.getNodeIdArray();
		Vector <AbstractV> allNodes= net.getAllVertex(); 
		
		DijkstraShortestPath DjkShorestPath = new DijkstraShortestPath(net);
		int count=0;
		
		for(int i=0;i<allNodes.size();i++){
			for(int j=i+1;j<allNodes.size();j++){		
				Number dis = DjkShorestPath.getDistance(allNodes.get(i), allNodes.get(j) );
				if(dis==null){
					
				}else{
					int hopCount = dis.intValue();
					sumHopCount+=hopCount;
					sumReciprocalHopCount+=1/(float)hopCount;
				}
				count++;
			}
		}
		
		if(retHopCount[0]!=-1){
			retHopCount[0]=sumHopCount/(float)count;
		}
		retHopCount[1]=sumReciprocalHopCount/count;
				
		return retHopCount;
		
	}
	
	 
		public static float reciprocalHopcount(AbstractNetwork net){
			float avgHopCount=0 ;
			
						
			Vector <AbstractV> allNodes= net.getAllVertex(); 
		
			DijkstraShortestPath DjkShorestPath = new DijkstraShortestPath(net);
			int count=0;
			float revsHops=0;
			for(int i=0;i<allNodes.size();i++){
				for(int j=i+1;j<allNodes.size();j++){
					
					if(DjkShorestPath.getDistance( allNodes.get(i), allNodes.get(j) )==null){
						break;
					}
					int distance = DjkShorestPath.getDistance( allNodes.get(i), allNodes.get(j) ).intValue();
					
					revsHops+=(float)1/(float)distance;
				//	Debug.outn(1/distance);
					count++;
				}
			}
					
			return revsHops/count;
			
		}
	
	public List<AbstractV> getPath(AbstractNetwork net , AbstractV nodeA, AbstractV nodeB){
		DijkstraShortestPath <AbstractV, AbstractV>DjkShorestPath = new DijkstraShortestPath(net );
		return DjkShorestPath.getPath(nodeA, nodeB);
	}
	
	

}
