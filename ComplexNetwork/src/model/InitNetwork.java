/**
 * 
 */
package model;

import network.*;

/**
 * @author gexin 初始网络的拓扑 一般规模较小
 */

public class InitNetwork {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 *Randomly initiate a connected network.	 * 
	 * @param net
	 * @param vertexNum
	 */
	public static void initRandomNet(AbstractNetwork net, int vertexNum) {
		
		/*line graph, ensure connected*/
		for (int i = 0; i < vertexNum - 1; i++) {

			net.addEdge(net.createNewEdge(),
					net.createNewVertex(Integer.toString(i)),
					net.createNewVertex(Integer.toString(i + 1)));
		}

		/* random link some vertices */
		for (int k = 0; k < vertexNum; k++) {
			net.addEdge(net.createNewEdge(), net.randomGetVertex(),
					net.randomGetVertex());
		}

	}

}
