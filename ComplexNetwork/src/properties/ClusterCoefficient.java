/**
 * 
 */
package properties;

import network.*;
import tools.*;
import java.util.*;
/**
 * @author gexin
 *Calculate the cluster coefficient of network
 */
public class ClusterCoefficient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static float linkDensity(AbstractNetwork network){
		
		float linkD=0;
		
		int N= network.getVertexCount();
		int L=network.getEdgeCount();
		//Debug.outn("N "+N+" "+L);
		// linkD = 
		linkD=(float)2*L/(float)N/(float)(N-1);
		return linkD;
	}
/**
 * Get node cluster coefficient.
 * @param network 
 * @param nodeID node ID
 * @return node cluster coefficient， </p>-1 if node doesn't exist in network.
 */
	public static float getNodeClusterCoefficient(AbstractNetwork network, AbstractV node){
		float nodeCC=0;
		if(!network.containsVertex(node)){
			Debug.outn("getNodeClusterCoefficient( ) 节点 "+node.getID()+" 在网络 "+network.getNetID()+" 中不存在");
			return -1;
		}
		
		int ngbNum = network.getNeighborCount(node);
		
		if(ngbNum==1 || ngbNum==2) return 0;
		
		
		Vector<AbstractV> ngbNodes = network.getNeighborVertices(node);
		int nodesTotal=ngbNodes.size();
		
		
		int connectNum=0;
		for(int i=0; i<nodesTotal;i++){
			for(int j = i+1;j<nodesTotal;j++){
				if(network.findEdge(ngbNodes.get(i), ngbNodes.get(j))!=null ){
					connectNum++;
				}
			}
		}
		//connectNum-=ngbNodes.length;
		nodeCC=(float)connectNum*2/(nodesTotal)/(nodesTotal-1);
		return nodeCC;
	}
	
	public static float [] getNodesClusterCoefficient(AbstractNetwork network){
		
		Vector <AbstractV> nodes = network.getAllVertex();
		float [] nodesCC = new float [nodes.size()];
		
		for(int i =0;i<nodes.size();i++){
			nodesCC[i] = getNodeClusterCoefficient(network, nodes.get(i));
		}
			
		return nodesCC;
	}
	/**
	 * Get average cluster coefficient of network.
	 * @param network
	 * @return
	 */
	public static float getNetworkClusterCoefficient(AbstractNetwork network){
		
		float [] nodesCC = getNodesClusterCoefficient(network);
		
		
		float ccSum=0;
		for(int k=0;k<nodesCC.length;k++){
			ccSum+= nodesCC[k];
		}
		
		//Debug.outn(nodesCC, " ");
			
		//Debug.outn(nodesCC.length);
		return ccSum/(nodesCC.length);
	}

}
