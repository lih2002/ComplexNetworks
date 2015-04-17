/**
 * 
 */
package model;

import network.*;

import properties.*;
import java.util.*;

import tools.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.*;

;

/**
 * reconstruct topology such as add node, edge.
 * 
 * @author gexin
 * 
 */
public class Reconstruct {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/***
	 * 网络中加入新节点，随机连接已有的边。
	 * 
	 * @param network
	 * @param nodeNum
	 * @param nodeDegree
	 * @return
	 */
	public AbstractNetwork AddNodesWithRandomEdge(AbstractNetwork network,
			int nodeNum, int nodeDegree) {

		int startID = 50000;

		for (int i = 1; i <= nodeNum; i++) {

			AbstractV newNode = network.createNewVertex(null);
			newNode.setID(Integer.toBinaryString(startID));
			network.addVertex(newNode);

			for (int j = 0; j < nodeDegree; j++) {

				AbstractV selectedNode = network.randomGetVertex();

				network.addEdge(network.createNewEdge(),
						network.createNewVertex(""), selectedNode);
			}

			startID++;
			if (i % 10 == 0) {
				network.saveNetToFile("d://" + network.getNetID() + nodeDegree
						+ "_" + i + ".reconstruct", " ");
			}
		}

		return network;
	}

	/**
	 * given degree sequence, contruct a network with correlation close to
	 * bound. First proposed by Fernando.
	 * 
	 * @param net
	 * @param filePath
	 * @param fileName
	 * @param postfix
	 */
	public static void constructMaxCorrelationNetwork(AbstractNetwork net,
			String filePath, String fileName, String postfix) {

		int[] degreeSeq = net.getAllVertexDegrees();
		Arrays.sort(degreeSeq);

		UndirectedSparseGraph<Integer, String> jungNet = new UndirectedSparseGraph<Integer, String>();

		Debug.outn(degreeSeq, "degree seq");
		for (int firstInd = degreeSeq.length - 1; firstInd >= 0;) {
			// link node with the highest degree to the node with the second
			// highest degreee
			if (degreeSeq[firstInd] > 0) {
				// Debug.outn("degreeSeq[firstInd] "+ degreeSeq[firstInd] );
				for (int secdInd = firstInd - 1; secdInd >= 0; secdInd--) {
					// if left degree>0, link them.

					if (degreeSeq[secdInd] > 0) {
						jungNet.addEdge(firstInd + "link" + secdInd, firstInd,
								secdInd);
						degreeSeq[secdInd] = degreeSeq[secdInd] - 1;
						degreeSeq[firstInd] = degreeSeq[firstInd] - 1;

						Debug.outn(firstInd + " link " + secdInd
								+ "   left deg " + degreeSeq[firstInd] + " "
								+ degreeSeq[secdInd]);

						if (degreeSeq[firstInd] == 0) {
							break;
						}
					}
				}
				// else,to next node with higher degree
			}
			firstInd--;

		}

		// AbstractNetwork.saveNetToFile(jungNet, filePath + fileName +
		// postfix);

	}

	public static void constructMaxCorrelationNetwork2(AbstractNetwork net,
			String filePath, String fileName, String postfix) {

		int[] ascDegreeSeq = net.getAllVertexDegrees();

		int[] degreeSeq = new int[ascDegreeSeq.length];
		Arrays.sort(ascDegreeSeq);

		for (int k = 0; k < degreeSeq.length; k++) {
			degreeSeq[k] = ascDegreeSeq[degreeSeq.length - 1 - k];
		}

		SortedNode sortedNode = new SortedNode();
		for (int i = 0; i < ascDegreeSeq.length; i++) {
			sortedNode.put(Integer.toString(i), ascDegreeSeq[i]);
			// Debug.outn(i + " " + asnDegreeSeq[i]);
		}

		// Debug.outn(sortNode.toString());
		sortedNode.ascSort();
		// Debug.outn(sortNode.toString());
		sortedNode.dscSort();

		Debug.outn(sortedNode.toString());

		UndirectedSparseGraph<String, String> jungNet = new UndirectedSparseGraph<String, String>();

		// Debug.outn(degreeSeq, "degree seq");
		for (int blockIdx = 0; blockIdx < sortedNode.size() - 1;) {
			// link node with the highest degree to the node with the second
			// highest degreee
			int blockLength = sortedNode.getValue(blockIdx);
			if (degreeSeq[blockIdx] > 0) {
				// Debug.outn("degreeSeq[firstInd] "+ degreeSeq[firstInd] );
				/*-------------in block------------------- */
				for (int inBlockIdx = blockIdx; inBlockIdx <= blockIdx
						+ blockLength; inBlockIdx++) {
					// if left degree>0, link them.

					if (sortedNode.getValue(inBlockIdx) == 0) {
						continue;
					}

					if (inBlockIdx != blockIdx) {
						// reorder the degrees
						sortedNode.dscSort(inBlockIdx, blockIdx + blockLength
								+ 1);
						Debug.outn("reorder "
								+ (inBlockIdx)
								+ " "
								+ (inBlockIdx + sortedNode.getValue(inBlockIdx)));
						for (int k = 0; k < sortedNode.size(); k++) {
							if (sortedNode.getValue(k) == 0)
								continue;
							Debug.outn("node " + sortedNode.getNode(k) + " "
									+ sortedNode.getValue(k));
						}
					}

					// link in block . link node with higher degree to following
					// nodes
					int linkedIdx;
					for (linkedIdx = inBlockIdx + 1; linkedIdx < sortedNode
							.size(); linkedIdx++) {

						if (sortedNode.getValue(linkedIdx) == 0) { // no degree
							// left
							// ,next
							// following
							continue;
						}
						jungNet.addEdge(sortedNode.getNode(inBlockIdx) + "link"
								+ sortedNode.getNode(linkedIdx),
								sortedNode.getNode(inBlockIdx),
								sortedNode.getNode(linkedIdx));

						// degree-1
						sortedNode.decreaseValue(inBlockIdx, 1);
						sortedNode.decreaseValue(linkedIdx, 1);

						Debug.outn(sortedNode.getNode(inBlockIdx) + " link "
								+ sortedNode.getNode(linkedIdx)
								+ " left degree "
								+ sortedNode.getValue(inBlockIdx) + " "
								+ sortedNode.getValue(linkedIdx));

						// no degree left
						if (sortedNode.getValue(inBlockIdx) == 0) {
							break;
						}

					} // link in block

					// has linked to next block,
					if (linkedIdx >= inBlockIdx + blockLength + 1) {
						break; // break this block
					}

					// reach the end of this block,break this block
					if (inBlockIdx >= inBlockIdx + blockLength + 1) {
						break;
					}

					// sort degree seq between second index and second
					// index+degree[first index]
					// sortNode.sort(secdIdx,);

				}// /////for in block
				/*---------------in block-----------------------*/
				// else,to next node block with higher degree
			}// if (degreeSeq[blockIdx] > 0) {
			blockIdx = blockIdx + blockLength + 1;

		}
		net.saveNetToFile(filePath, fileName + postfix);

	}

	/***
	 * in use
	 * 
	 * @param keepConnected
	 * @param net
	 * @param saveInterval
	 * @param rewirePercent
	 * @param filePath
	 * @param fileName
	 * @param postfix
	 */
	public static void reserveDegreeCorrelationRewire(boolean keepConnected,
			AbstractNetwork net, int saveInterval, float rewirePercent,
			String filePath, String fileName, String postfix) {

		int saveThreshold = 1;
		if (saveInterval <= 0) {
			// saveInterval = 5;
		}

		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (postfix.equals("")) {
			postfix = ".degCorRewire";
		}

		Vector<AbstractE> allEdges = net.getAllEdges();
		int allEdgeNumber = allEdges.size();

		/**
		 * 获得所有节点度值。
		 */
		Vector<AbstractV> allNodeID = net.getAllVertex();

		// 保存 度值-节点ID 映射。即有哪些节点度值为某一值。
		Map<Integer, Vector<AbstractV>> degreeNodeMap = new HashMap<Integer, Vector<AbstractV>>();

		int[] allDegree = new int[allNodeID.size()];
		// get all the node ID
		int j = 0;
		for (AbstractV id : allNodeID) {
			allDegree[j++] = net.getNeighborCount(id);
			if (degreeNodeMap.containsKey(allDegree[j])) {
				degreeNodeMap.get(allDegree[j]).add(id);
			} else {
				Vector<AbstractV> nodes = new Vector<AbstractV>();
				nodes.add(id);
				degreeNodeMap.put(allDegree[j], nodes);
			}
		}

		/* debuy degreeNodeMap */
		// Iterator <Integer>degreeIt = degreeNodeMap.keySet().iterator();
		// while(degreeIt.hasNext()){
		// Integer d= (degreeIt.next());
		// Debug.outn(d+" "+degreeNodeMap.get(d).size());
		//
		// }
		int rewiredCount = 0;

		UndirectedSparseGraph<Integer, String> jungNet = new UndirectedSparseGraph<Integer, String>();

		// save original network
		net.saveNetToFile(filePath + fileName + ".0000" + postfix);

		/* rewire links as more as possible */
		boolean w = true;
		// int c=50;
		while (w) {

			if (rewiredCount >= rewirePercent * allEdges.size()) {
				break;
			}
			AbstractV nodeAD = null;
			AbstractV nodeBD = null;
			AbstractV nodeANgb = null;
			AbstractV nodeBNgb = null;

			// random choose two nodes with the same degrees and their neighbors

			while (true) {

				boolean findEnableNodes = false;

				int rndSameDegreeGroup = MathTool.randomInt(0,
						degreeNodeMap.size(), 1)[0];
				Object[] degreeG = degreeNodeMap.keySet().toArray();
				Vector<AbstractV> nodes = degreeNodeMap
						.get(degreeG[rndSameDegreeGroup]);
				if (nodes.size() < 2) {
					continue;
				}
				int[] twoNodes = MathTool.randomInt(0, nodes.size(), 2);
				nodeAD = nodes.get(twoNodes[0]);
				nodeBD = nodes.get(twoNodes[1]);

				// Debug.outn("random choose two node "+nodeAD+" "+nodeBD);

				// random choose neighbor from two nodes

				Vector<AbstractV> nodeANgbs = net.getNeighborVertices(nodeAD);
				Vector<AbstractV> nodeBNgbs = net.getNeighborVertices(nodeBD);

				for (int ngbI = 0; ngbI < nodeANgbs.size(); ngbI++) {
					for (int ngbJ = 0; ngbJ < nodeBNgbs.size(); ngbJ++) {
						nodeANgb = nodeANgbs.get(ngbI);
						nodeBNgb = nodeBNgbs.get(ngbJ);
						if (nodeANgb != nodeBD && nodeBNgb != nodeAD
								&& nodeANgb != nodeBNgb) {
							// Debug.outn("enable");
							findEnableNodes = true;
							break;
						}
					}

					if (findEnableNodes) {
						break;
					}
				}

				if (findEnableNodes) {
					break;
				} else {
					// Debug.outn("unenable");
					continue;
				}

				// Debug.outn("nodeA B degree "+(Integer)degreeG[rndGroup]);
			}

			boolean rewireOK = false;

			// Debug.outn(net.getNodeDegreeById(nodeAD)+" "+net.getNodeDegreeById(nodeANgb)
			// +" - "+
			// net.getNodeDegreeById(nodeBD)+" "+net.getNodeDegreeById(nodeBNgb));

			rewireOK = rewireEdgeNodes(nodeAD, nodeANgb, nodeBD, nodeBNgb,
					keepConnected, net, nodeAD, nodeBNgb, nodeANgb, nodeBD);

			// Debug.outn("/////////////////////////////////////////////////////////");

			if (rewireOK) {
				rewiredCount++;
				rewiredCount++;

				Debug.outn("rewire count " + rewiredCount);

				/* save net if condition is met. */
				float rewiredP = (float) rewiredCount / (float) allEdgeNumber;
				// Debug.outn(rewiredP+" "+saveInterval*saveThreshold);

				if ((rewiredP * 100) >= saveInterval * saveThreshold) {
					Debug.outn("reach t " + rewiredP);
					int nameSeq;

					if (saveInterval == 0) {// save each rewing
						nameSeq = rewiredCount;
					} else {
						nameSeq = saveInterval * saveThreshold;
					}
					String seq = "";
					if (nameSeq <= 9) {
						seq = ".000" + nameSeq;
					}
					if (nameSeq >= 10 && nameSeq <= 99) {
						seq = ".00" + nameSeq;
					}
					if (nameSeq >= 100 && nameSeq <= 999) {
						seq = ".0" + nameSeq;
					}
					if (nameSeq >= 1000 && nameSeq <= 9999) {
						seq = "." + nameSeq;
					}

					net.saveNetToFile(filePath + fileName + seq + postfix);
					saveThreshold++;

				}// ////save to file

			}// //////rewire ok
			else {
				Debug.outn("rewire fail ");
			}

		}// /////while
		Debug.outn("edge number: " + allEdgeNumber + " rewired number "
				+ rewiredCount);

	}// ///////////////////////////////////////////////////////////////////////

	/**
	 * in use 重连网络边，从而单调改变网络的度相关系数。
	 * 
	 * @param assortative
	 * @param net
	 * @param saveInterval
	 * @param rewirePercent
	 * @param filePath
	 * @param fileName
	 * @param postfix
	 *            保存文件后的后缀名
	 */
	public static void monotonicCorrelationRewire(boolean keepConnected,
			boolean assortative, AbstractNetwork net, int saveInterval,
			float rewirePercent, String filePath, String fileName,
			String postfix) {
		Debug.outn(rewirePercent);

		int saveThreshold = 1;
		if (saveInterval < 0) {
			saveInterval = 5;
		}

		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (assortative && postfix.equals("")) {
			postfix = ".astDCR";
		}
		if (!assortative && postfix.equals("")) {
			postfix = ".dstDCR";
		}

		Vector<String> allNodeID = net.getAllVertexIDVector();
		int[] allDegree = new int[allNodeID.size()];

		Vector<AbstractE> allEdges = net.getAllEdges();
		int allEdgeNumber = allEdges.size();
		int rewiredCount = 0;

		// UndirectedSparseGraph<Integer, String> jungNet = new
		// UndirectedSparseGraph<Integer, String>();

		// save original network

		net.saveNetToFile(filePath + fileName + ".0000" + postfix);

		/* rewire links as more as possible */
		while (true) {

			if (rewiredCount >= rewirePercent * allEdges.size()) {
				break;
			}

			/**
			 * random choose two edges
			 */

			int[] edgePair = MathTool.randomInt(0, net.getEdgeCount(), 2);
			Pair<AbstractV> newEdgeA = net.getEndpoints(allEdges
					.get(edgePair[0]));
			Pair<AbstractV> newEdgeB = net.getEndpoints(allEdges
					.get(edgePair[1]));

			AbstractE edgeA = net.createNewEdge();
			AbstractE edgeB = net.createNewEdge();

			AbstractV[] fourNodes = new AbstractV[4];
			int[] fourDegrees = new int[4];

			fourNodes[0] = newEdgeA.getFirst();
			fourNodes[1] = newEdgeA.getSecond();
			fourNodes[2] = newEdgeB.getFirst();
			fourNodes[3] = newEdgeB.getSecond();

			fourDegrees[0] = net.getNeighborCount(newEdgeA.getFirst());
			fourDegrees[1] = net.getNeighborCount(newEdgeA.getSecond());
			fourDegrees[2] = net.getNeighborCount(newEdgeB.getSecond());
			fourDegrees[3] = net.getNeighborCount(newEdgeB.getSecond());

			int[] orderIndex = new int[4];
			int maxIndex = 0;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (fourDegrees[j] > fourDegrees[maxIndex]) {
						maxIndex = j;
					}
				}
				orderIndex[i] = maxIndex;
				fourDegrees[maxIndex] = 0;
				maxIndex = 0;
			}

			// debug
			for (int k = 0; k < 4; k++) {
				// Debug.outn("order index "+orderIndex[k]+" node
				// "+fourNodes[orderIndex[k]]+"
				// degree"+fourDegrees[orderIndex[k]]);
			}
			// Debug.outn("---------------------------------");
			// Debug.outn("linkone "+edgeANodeADegree+" - "+edgeANodeBDegree);
			// Debug.outn("linktwo "+edgeBNodeCDegree+" - "+edgeBNodeDDegree);

			boolean rewireOK = false;

			if (assortative) { // rewire to assortative
				// 1-2
				if (orderIndex[0] + orderIndex[1] == 1
						|| orderIndex[0] + orderIndex[1] == 5) {

				}
				// 1-3
				if (orderIndex[0] + orderIndex[2] == 1
						|| orderIndex[0] + orderIndex[2] == 5) {
					rewireOK = rewireEdgeNodes(fourNodes[0], fourNodes[1],
							fourNodes[2], fourNodes[3], keepConnected, net,
							fourNodes[orderIndex[0]], fourNodes[orderIndex[1]],
							fourNodes[orderIndex[2]], fourNodes[orderIndex[3]]);
					// Debug.outn("1-3 rewire org edge: "+);
				}
				// 1-4
				if (orderIndex[0] + orderIndex[3] == 1
						|| orderIndex[0] + orderIndex[3] == 5) {
					rewireOK = rewireEdgeNodes(fourNodes[0], fourNodes[1],
							fourNodes[2], fourNodes[3], keepConnected, net,
							fourNodes[orderIndex[0]], fourNodes[orderIndex[1]],
							fourNodes[orderIndex[2]], fourNodes[orderIndex[3]]);

					// Debug.outn("1-4 rewire");
				}

			}

			if (!assortative) { // disassortative
				// 1-2
				if (orderIndex[0] + orderIndex[1] == 1
						|| orderIndex[0] + orderIndex[1] == 5) {
					rewireOK = rewireEdgeNodes(fourNodes[0], fourNodes[1],
							fourNodes[2], fourNodes[3], keepConnected, net,
							fourNodes[orderIndex[0]], fourNodes[orderIndex[3]],
							fourNodes[orderIndex[1]], fourNodes[orderIndex[2]]);
				}
				// 1-3
				if (orderIndex[0] + orderIndex[2] == 1
						|| orderIndex[0] + orderIndex[2] == 5) {
					rewireOK = rewireEdgeNodes(fourNodes[0], fourNodes[1],
							fourNodes[2], fourNodes[3], keepConnected, net,
							fourNodes[orderIndex[0]], fourNodes[orderIndex[3]],
							fourNodes[orderIndex[1]], fourNodes[orderIndex[2]]);
				}
				// 1-4
				if (orderIndex[0] + orderIndex[3] == 1
						|| orderIndex[0] + orderIndex[3] == 5) {
					// rewireOK=connectEdgeNodes(jungNet,fourNodes[orderIndex[0]],fourNodes[orderIndex[3]],
					// fourNodes[orderIndex[1]],fourNodes[orderIndex[2]]);
				}

			}

			// if(keepConnected && !Partitions.connected(jungNet)){
			// //Debug.outn("disconenct after rewire");
			// rewireOK=false;
			// }

			if (rewireOK) {
				// remove original edges
				// jungNet.removeEdge(edgeANodeAIdx + "-" + edgeANodeBIdx);
				// jungNet.removeEdge(edgeBNodeCIdx + "-" + edgeBNodeDIdx);

				rewiredCount++;
				rewiredCount++;

				/* save net if condition is met. */
				float rewiredP = (float) rewiredCount / (float) allEdgeNumber;
				// Debug.outn(rewiredP+" "+saveInterval*saveThreshold);

				if ((rewiredP * 100) >= saveInterval * saveThreshold) {
					Debug.outn("reach t " + rewiredP);
					int nameSeq;

					if (saveInterval == 0) {// save each rewing
						nameSeq = rewiredCount;
					} else {
						nameSeq = saveInterval * saveThreshold;
					}
					String seq = "";
					if (nameSeq <= 9) {
						seq = ".000" + nameSeq;
					}
					if (nameSeq >= 10 && nameSeq <= 99) {
						seq = ".00" + nameSeq;
					}
					if (nameSeq >= 100 && nameSeq <= 999) {
						seq = ".0" + nameSeq;
					}
					if (nameSeq >= 1000 && nameSeq <= 9999) {
						seq = "." + nameSeq;
					}

					net.saveNetToFile(filePath + fileName + seq + postfix);
					saveThreshold++;

				}// ////save to file

			}// //////rewire ok
			else {
				// Debug.outn("rewire fail");
				// recover to original net
				// remove new edges

				// add original edges

			}

		}// /////////////for() selected two edges, rewire as more as possible

		// Debug.outn("new degrees ");
		// for(int j=0;j<net.getNetworkSize();j++){
		// Debug.outn("idx "+j+" "+newNet.getNeighborCount(j));
		// }

		Debug.outn("edge number: " + allEdgeNumber + " rewired number "
				+ rewiredCount);
		// if (saveInterval == 0) {
		// //
		// saveNetToFile(newNet,net,filePath+fileName+"."+(int)((float)rewiredCount/(float)allEdgeNumber*100)+postfix);
		// } else {
		// AbstractNetwork.saveNetToFile(jungNet,
		// filePath
		// + fileName
		// + "."
		// + (int) ((float) rewiredCount
		// / (float) allEdgeNumber * 100) + postfix);
		// }

	}// //////////////////////////////////////rewire

	// public static void monotonicCorrelationRewireWithN2Check(boolean
	// assortative,
	// AbstractNetwork net, int saveInterval, float rewirePercent,
	// String filePath, String fileName, String postfix) {
	//
	// int saveThreshold = 1;
	// if (saveInterval < 0) {
	// saveInterval = 5;
	// }
	//
	// if (fileName == "") {
	// fileName = net.getNetworkID();
	// }
	// if (assortative && postfix.equals("")) {
	// postfix = ".astDCR";
	// }
	// if (!assortative && postfix.equals("")) {
	// postfix = ".dstDCR";
	// }
	//
	// int[] allNodeID = net.getNodeIdArray();
	// int[] allDegree = new int[allNodeID.length];
	// // get all the node ID
	// for (int i = 0; i < allNodeID.length; i++) {
	// allDegree[i] = net.getNodeDegreeById(allNodeID[i]);
	// }
	//
	// Vector<AbstractEdge> allEdges = net.getEdgeArray();
	// int allEdgeNumber = allEdges.size();
	// int rewiredCount = 0;
	//
	// UndirectedSparseGraph<Integer, String> jungNet = new
	// UndirectedSparseGraph<Integer, String>();
	//
	// for (int i = 0; i < allEdges.size(); i++) {
	// AbstractEdge edge = allEdges.get(i);
	// jungNet.addEdge(net.idx2Id(edge.getAIdx()) + "-"
	// + net.idx2Id(edge.getBIdx()), net.idx2Id(edge.getAIdx()),
	// net.idx2Id(edge.getBIdx()));
	// }
	//
	// // save original network
	// AbstractNetwork.saveNetToFile(jungNet, filePath + fileName + ".0000"
	// + postfix);
	//
	// /* rewire links for every pair of L , bio(L,2) */
	//
	// Object[] jungAllEdges = jungNet.getEdges().toArray();
	// // Debug.outn("all edges nun "+jungAllEdges.length);
	// for (int i = 0; i < jungAllEdges.length; i++) {
	//
	// // Debug.outn("all edges "+i+" " + (String)jungAllEdges[i] ) ;
	// }
	//
	// /* Iterate all pairs of edges */
	// for (int m = 0; m < jungAllEdges.length; m++) {
	//
	// for (int n = 0; n < jungAllEdges.length; n++) {
	//
	// if (rewiredCount >= rewirePercent * allEdges.size()) {
	// break;
	// }
	//
	// if (m == n) {
	// continue;
	// }
	//
	// //Debug.outn("m " + m + " n " + n);
	// String edgeA = (String) jungAllEdges[m];
	// String edgeB = (String) jungAllEdges[n];
	//
	// int edgeANodeAId = Integer.parseInt((edgeA.split("-")[0]));
	// int edgeANodeBId = Integer.parseInt((edgeA.split("-")[1]));
	// int edgeBNodeCId = Integer.parseInt((edgeB.split("-")[0]));
	// int edgeBNodeDId = Integer.parseInt((edgeB.split("-")[1]));
	//
	// // Debug.outn("find edgeA "+edgeANodeAId+" "+edgeANodeBId+"
	// // "+jungNet.findEdge(edgeANodeAId, edgeANodeBId));
	// // Debug.outn("find edgeB "+edgeBNodeCId+" "+edgeBNodeDId+"
	// // "+jungNet.findEdge(edgeBNodeCId, edgeBNodeDId));
	//
	// int edgeANodeADegree = jungNet.getNeighborCount(edgeANodeAId);
	// int edgeANodeBDegree = jungNet.getNeighborCount(edgeANodeBId);
	// int edgeBNodeCDegree = jungNet.getNeighborCount(edgeBNodeCId);
	// int edgeBNodeDDegree = jungNet.getNeighborCount(edgeBNodeDId);
	//
	// int[] fourNodes = new int[4];
	// int[] fourDegrees = new int[4];
	//
	// fourNodes[0] = edgeANodeAId;
	// fourNodes[1] = edgeANodeBId;
	// fourNodes[2] = edgeBNodeCId;
	// fourNodes[3] = edgeBNodeDId;
	//
	// fourDegrees[0] = edgeANodeADegree;
	// fourDegrees[1] = edgeANodeBDegree;
	// fourDegrees[2] = edgeBNodeCDegree;
	// fourDegrees[3] = edgeBNodeDDegree;
	//
	// int[] tD = new int[4];
	// for (int k = 0; k < 4; k++) {
	// tD[k] = fourDegrees[k];
	// }
	// // avoid 3 of 4 equal
	// if (tD[0] == tD[1] & tD[1] == tD[2]) {
	//
	// continue;
	// }
	// if (tD[1] == tD[2] & tD[2] == tD[3]) {
	//
	// continue;
	// }
	// if (tD[2] == tD[3] & tD[3] == tD[0]) {
	//
	// continue;
	// }
	// if (tD[0] == tD[3] & tD[0] == tD[1]) {
	//
	// continue;
	// }
	//
	// int[] orderIndex = new int[4];
	// int maxIndex = 0;
	// for (int i = 0; i < 4; i++) {
	// for (int j = 0; j < 4; j++) {
	// if (fourDegrees[j] > fourDegrees[maxIndex]) {
	// maxIndex = j;
	// }
	// }
	// orderIndex[i] = maxIndex;
	// fourDegrees[maxIndex] = 0;
	// maxIndex = 0;
	// }
	//
	// // debug
	// // for (int k = 0; k < 4; k++) {
	// // Debug.outn("order index "+orderIndex[k]+" node
	// // "+fourNodes[orderIndex[k]]+
	// // " degree "+tD[orderIndex[k]]);
	// // }
	// // Debug.outn("///////////////////////");
	// // Debug.outn("linkone "+edgeANodeADegree+" -
	// // "+edgeANodeBDegree);
	// // Debug.outn("linktwo "+edgeBNodeCDegree+" -
	// // "+edgeBNodeDDegree);
	//
	// boolean rewireOK = false;
	//
	// if (assortative) { // rewire to assortative
	//
	// // 1-2
	// if (orderIndex[0] + orderIndex[1] == 1 // the most bigger
	// // two belong to one
	// // edge
	// || orderIndex[0] + orderIndex[1] == 5) {
	// continue;
	// //
	// connectEdgeNodes(jungNet,edgeANodeAIdx,edgeBNodeCIdx,edgeANodeBIdx,edgeBNodeDIdx);
	// }
	// // 1-3
	// if (orderIndex[0] + orderIndex[2] == 1
	// || orderIndex[0] + orderIndex[2] == 5) {
	// if (tD[orderIndex[1]] == tD[orderIndex[2]]) {
	// continue;
	// }
	// rewireOK = rewireEdgeNodes(true,jungNet,
	// fourNodes[orderIndex[0]],
	// fourNodes[orderIndex[1]],
	// fourNodes[orderIndex[2]],
	// fourNodes[orderIndex[3]]);
	// }
	// // 1-4
	// if (orderIndex[0] + orderIndex[3] == 1
	// || orderIndex[0] + orderIndex[3] == 5) {
	// rewireOK = rewireEdgeNodes(true,jungNet,
	// fourNodes[orderIndex[0]],
	// fourNodes[orderIndex[1]],
	// fourNodes[orderIndex[2]],
	// fourNodes[orderIndex[3]]);
	// }
	//
	// }
	//
	// if (!assortative) { // disassortative
	// // 1-2
	// if (orderIndex[0] + orderIndex[1] == 1
	// || orderIndex[0] + orderIndex[1] == 5) {
	// rewireOK = rewireEdgeNodes(true,jungNet,
	// fourNodes[orderIndex[0]],
	// fourNodes[orderIndex[3]],
	// fourNodes[orderIndex[1]],
	// fourNodes[orderIndex[2]]);
	// }
	// // 1-3
	// if (orderIndex[0] + orderIndex[2] == 1
	// || orderIndex[0] + orderIndex[2] == 5) {
	// rewireOK = rewireEdgeNodes(true,jungNet,
	// fourNodes[orderIndex[0]],
	// fourNodes[orderIndex[3]],
	// fourNodes[orderIndex[1]],
	// fourNodes[orderIndex[2]]);
	// }
	// // 1-4
	// if (orderIndex[0] + orderIndex[3] == 1
	// || orderIndex[0] + orderIndex[3] == 5) {
	// //
	// rewireOK=connectEdgeNodes(jungNet,fourNodes[orderIndex[0]],fourNodes[orderIndex[3]],
	// // fourNodes[orderIndex[1]],fourNodes[orderIndex[2]]);
	// }
	//
	// }
	//
	// if (rewireOK) {
	// // Debug.outn("-----after rewire, edge count
	// // "+jungNet.getEdgeCount());
	// // Debug.outn("rewire OK "+" m:"+m+" n:"+n) ;
	//
	// // if rewire ok. recheck all pairs of edges
	//
	// // remove old edges
	// // Debug.outn(edgeANodeADegree+"-"+edgeANodeBDegree);
	// // Debug.outn(edgeBNodeCDegree+"-"+edgeBNodeDDegree);
	// // Debug.outn("--------------------------------");
	// jungNet.removeEdge(jungNet.findEdge(edgeANodeAId,
	// edgeANodeBId));
	// jungNet.removeEdge(jungNet.findEdge(edgeBNodeCId,
	// edgeBNodeDId));
	//
	// // Debug.outn("after remove old ,edge count
	// // "+jungNet.getEdgeCount());
	// rewiredCount++;
	// rewiredCount++;
	//
	// // update edges set
	// jungAllEdges = jungNet.getEdges().toArray();
	// m = 0;
	// n = 0;
	//
	// /* save net if condition is met. */
	// float rewiredP = (float) rewiredCount
	// / (float) allEdgeNumber;
	// // Debug.outn(rewiredP+" "+saveInterval*saveThreshold);
	//
	// // save to file if condition is met
	// if ((rewiredP * 100) >= saveInterval * saveThreshold) {
	// Debug.outn("reach t " + rewiredP);
	// int nameSeq;
	//
	// if (saveInterval == 0) {// save each rewing
	// nameSeq = rewiredCount;
	// } else {
	// nameSeq = saveInterval * saveThreshold;
	// }
	// String seq = "";
	// if (nameSeq <= 9) {
	// seq = ".000" + nameSeq;
	// }
	// if (nameSeq >= 10 && nameSeq <= 99) {
	// seq = ".00" + nameSeq;
	// }
	// if (nameSeq >= 100 && nameSeq <= 999) {
	// seq = ".0" + nameSeq;
	// }
	// if (nameSeq >= 1000 && nameSeq <= 9999) {
	// seq = "." + nameSeq;
	// }
	//
	// AbstractNetwork.saveNetToFile(jungNet, filePath
	// + fileName + seq + postfix);
	// saveThreshold++;
	//
	// }// ////save to file
	//
	// }// //////rewire ok
	//
	// }// ////for()
	// }// ///////////////for( for() )
	//
	// // Debug.outn("new degrees ");
	// // for(int j=0;j<net.getNetworkSize();j++){
	// // Debug.outn("idx "+j+" "+newNet.getNeighborCount(j));
	// // }
	//
	// Debug.outn("edge number: " + allEdgeNumber + " rewired number "
	// + rewiredCount);
	// // if (saveInterval == 0) {
	// // //
	// //
	// saveNetToFile(newNet,net,filePath+fileName+"."+(int)((float)rewiredCount/(float)allEdgeNumber*100)+postfix);
	// // } else {
	// // AbstractNetwork.saveNetToFile(jungNet,
	// // filePath
	// // + fileName
	// // + "."
	// // + (int) ((float) rewiredCount
	// // / (float) allEdgeNumber * 100) + postfix);
	// // }
	//
	// }// //////////////////////////////////////rewire

	// public static void correlationRewireLinkWithImmutableDegree(
	// boolean assortative, AbstractNetwork net, int saveInterval,
	// float rewirePercent, String filePath, String fileName,
	// String postfix) {
	//
	// int saveThreshold = 1;
	// if (saveInterval < 0) {
	// saveInterval = 5;
	// }
	//
	// if (fileName == "") {
	// fileName = net.getNetworkID();
	// }
	// if (assortative && postfix.equals("")) {
	// postfix = ".astDCR";
	// }
	// if (!assortative && postfix.equals("")) {
	// postfix = ".dstDCR";
	// }
	//
	// int[] allNodeID = net.getNodeIdArray();
	// int[] allDegree = new int[allNodeID.length];
	// // get all the node ID
	// for (int i = 0; i < allNodeID.length; i++) {
	// allDegree[i] = net.getNodeDegreeById(allNodeID[i]);
	// }
	//
	// Vector<AbstractEdge> allEdges = net.getEdgeArray();
	// int allEdgeNumber = allEdges.size();
	// int rewiredCount = 0;
	//
	// UndirectedSparseGraph<Integer, String> jungNet = new
	// UndirectedSparseGraph<Integer, String>();
	//
	// for (int i = 0; i < allEdges.size(); i++) {
	// AbstractEdge edge = allEdges.get(i);
	// jungNet.addEdge(edge.getAIdx() + "-" + edge.getBIdx(), edge
	// .getAIdx(), edge.getBIdx());
	// }
	//
	// // save original network
	// AbstractNetwork.saveNetToFile(jungNet, filePath + fileName + ".000"
	// + postfix);
	//
	// /* rewire links as more as possible */
	// while (true) {
	//
	// if (rewiredCount >= rewirePercent * allEdges.size()) {
	// break;
	// }
	//
	// /* random choose two edges */
	//
	// int[] edgePair = MathTool.randomInt(0, jungNet.getEdgeCount(), 2);
	// String newEdgeA = (String) jungNet.getEdges().toArray()[edgePair[0]];
	// String newEdgeB = (String) jungNet.getEdges().toArray()[edgePair[1]];
	//
	// AbstractEdge edgeA = net.getNewEdge();
	// AbstractEdge edgeB = net.getNewEdge();
	// edgeA.setAIdx(Integer.parseInt(newEdgeA.split("-")[0]));
	// edgeB.setAIdx(Integer.parseInt(newEdgeB.split("-")[0]));
	// edgeA.setBIdx(Integer.parseInt(newEdgeA.split("-")[1]));
	// edgeB.setBIdx(Integer.parseInt(newEdgeB.split("-")[1]));
	//
	// int edgeANodeA = edgeA.getAIdx();
	// int edgeANodeB = edgeA.getBIdx();
	// int edgeBNodeC = edgeB.getAIdx();
	// int edgeBNodeD = edgeB.getBIdx();
	//
	// int edgeANodeADegree = jungNet.getNeighborCount(edgeANodeA);
	// int edgeANodeBDegree = jungNet.getNeighborCount(edgeANodeB);
	// int edgeBNodeCDegree = jungNet.getNeighborCount(edgeBNodeC);
	// int edgeBNodeDDegree = jungNet.getNeighborCount(edgeBNodeD);
	// // Debug.outn("---------------------------------");
	// // Debug.outn("linkone "+edgeANodeADegree+" - "+edgeANodeBDegree);
	// // Debug.outn("linktwo "+edgeBNodeCDegree+" - "+edgeBNodeDDegree);
	//
	// boolean rewireOK = false;
	//
	// if (assortative) { // rewire to assortative
	// if (edgeANodeADegree > edgeANodeBDegree) {
	//
	// if (edgeBNodeCDegree > edgeBNodeDDegree) {
	// // link edgeANodeA and edgeBNodeB
	// rewireOK = rewireEdgeNodes(true,jungNet, edgeANodeA,
	// edgeBNodeC, edgeANodeB, edgeBNodeD);
	//
	// } else {
	// if (edgeBNodeCDegree < edgeBNodeDDegree) {
	// rewireOK = rewireEdgeNodes(true,jungNet, edgeANodeA,
	// edgeBNodeD, edgeANodeB, edgeBNodeC);
	// }
	// }
	//
	// } else {
	// if (edgeANodeADegree < edgeANodeBDegree) {
	// // edgeANodeADegree<edgeANodeBDegree
	//
	// if (edgeBNodeCDegree > edgeBNodeDDegree) {
	// // link edgeANodeA and edgeBNodeB
	// rewireOK = rewireEdgeNodes(true,jungNet, edgeANodeB,
	// edgeBNodeC, edgeANodeA, edgeBNodeD);
	//
	// } else {
	// if (edgeBNodeCDegree < edgeBNodeDDegree) {
	// rewireOK = rewireEdgeNodes(true,jungNet,
	// edgeANodeB, edgeBNodeD, edgeANodeA,
	// edgeBNodeC);
	// }
	// }
	// }
	//
	// }
	// }
	//
	// if (!assortative) { // disassortative
	// if (edgeANodeADegree > edgeANodeBDegree) {
	//
	// if (edgeBNodeCDegree < edgeBNodeDDegree) {
	// // link edgeANodeA and edgeBNodeB
	// rewireOK = rewireEdgeNodes(true,jungNet, edgeANodeA,
	// edgeBNodeC, edgeANodeB, edgeBNodeD);
	//
	// } else {
	// if (edgeBNodeCDegree > edgeBNodeDDegree) {
	// rewireOK = rewireEdgeNodes(true,jungNet, edgeANodeA,
	// edgeBNodeD, edgeANodeB, edgeBNodeC);
	// }
	// }
	//
	// } else {
	// if (edgeANodeADegree < edgeANodeBDegree) {
	// // edgeANodeADegree<edgeANodeBDegree
	//
	// if (edgeBNodeCDegree < edgeBNodeDDegree) {
	// // link edgeANodeA and edgeBNodeB
	// rewireOK = rewireEdgeNodes(true,jungNet, edgeANodeB,
	// edgeBNodeC, edgeANodeA, edgeBNodeD);
	//
	// } else {
	// if (edgeBNodeCDegree > edgeBNodeDDegree) {
	// rewireOK = rewireEdgeNodes(true,jungNet,
	// edgeANodeB, edgeBNodeD, edgeANodeA,
	// edgeBNodeC);
	// }
	// }
	// }
	//
	// }
	// }
	//
	// if (rewireOK) {
	// // remove old edges
	// jungNet.removeEdge(edgeANodeA + "-" + edgeANodeB);
	// jungNet.removeEdge(edgeBNodeC + "-" + edgeBNodeD);
	//
	// rewiredCount++;
	// rewiredCount++;
	//
	// /* save net if condition is met. */
	// float rewiredP = (float) rewiredCount / (float) allEdgeNumber;
	// // Debug.outn(rewiredP+" "+saveInterval*saveThreshold);
	//
	// if ((rewiredP * 100) >= saveInterval * saveThreshold) {
	// Debug.outn("reach t " + rewiredP);
	// int nameSeq;
	//
	// if (saveInterval == 0) {// save each rewing
	// nameSeq = rewiredCount;
	// } else {
	// nameSeq = saveInterval * saveThreshold;
	// }
	// String seq = "";
	// if (nameSeq <= 9) {
	// seq = ".00" + nameSeq;
	// }
	// if (nameSeq >= 10 && nameSeq <= 99) {
	// seq = ".0" + nameSeq;
	// }
	// if (nameSeq >= 100 && nameSeq <= 999) {
	// seq = "." + nameSeq;
	// }
	//
	// AbstractNetwork.saveNetToFile(jungNet, filePath + fileName
	// + seq + postfix);
	// saveThreshold++;
	//
	// }// ////save to file
	//
	// }// //////rewire ok
	//
	// }// /////////////for() selected two edges, rewire as more as possible
	//
	// // Debug.outn("new degrees ");
	// // for(int j=0;j<net.getNetworkSize();j++){
	// // Debug.outn("idx "+j+" "+newNet.getNeighborCount(j));
	// // }
	//
	// Debug.outn("edge number: " + allEdgeNumber + " rewired number "
	// + rewiredCount);
	// if (saveInterval == 0) {
	// //
	// saveNetToFile(newNet,net,filePath+fileName+"."+(int)((float)rewiredCount/(float)allEdgeNumber*100)+postfix);
	// } else {
	// AbstractNetwork.saveNetToFile(jungNet,
	// filePath
	// + fileName
	// + "."
	// + (int) ((float) rewiredCount
	// / (float) allEdgeNumber * 100) + postfix);
	// }
	//
	// }// //////////////////////////////////////rewire

	

	
	/**
	 * weaken community. decrease modularity.
	 * 
	 * @param net
	 * @param comm
	 * @param saveInterval
	 * @param rewirePercent
	 * @param filePath
	 * @param fileName
	 * @param postfix
	 */
	// public static void weakenCommunity(AbstractNetwork net,
	// AbstractCommunity comm, int saveInterval, float rewirePercent,
	// String filePath, String fileName, String postfix) {
	//
	// int saveThreshold = 1;
	// if (saveInterval < 0) {
	// saveInterval = 5;
	// }
	//
	// if (fileName == "") {
	// fileName = net.getNetworkID();
	// }
	// if (postfix.equals("")) {
	// postfix = ".wekCommunity";
	// }
	//
	// int[] allNodeID = net.getNodeIdArray();
	//
	// Vector<AbstractEdge> allEdges = net.getEdgeArray();
	// int allEdgeNumber = allEdges.size();
	// int rewiredCount = 0;
	//
	// UndirectedSparseGraph<Integer, String> jungNet = new
	// UndirectedSparseGraph<Integer, String>();
	//
	// for (int i = 0; i < allEdges.size(); i++) {
	// AbstractEdge edge = allEdges.get(i);
	// jungNet.addEdge(net.idx2Id(edge.getAIdx()) + "-"
	// + net.idx2Id(edge.getBIdx()), net.idx2Id(edge.getAIdx()),
	// net.idx2Id(edge.getBIdx()));
	// }
	//
	// // save original network
	// AbstractNetwork.saveNetToFile(jungNet, filePath + fileName + ".0000"
	// + postfix);
	//
	// /* rewire links until condition */
	//
	// while (true) {
	//
	// if (rewiredCount >= rewirePercent * allEdges.size()) {
	// break;
	// }
	//
	// boolean endSearchEdges = false;
	//
	// /* random choose two links from different community */
	//
	// // random choose community A and two nodes in it
	// int[] rndTwoNodes;
	// int chooseCommTimes = 0;
	// int searchNodesTimes = 0;
	// Set<Integer> commA=comm.randomCommunity();;
	// //boolean endChooseComm = false;
	// /*choose community many times*/
	// int edgeANodeAId=0 ;
	// int edgeANodeBId=0 ;
	// while (chooseCommTimes < comm.getCommunitySize() * 1000) {
	//
	// commA = comm.randomCommunity();
	// while (commA.size() < 2) {
	// commA = comm.randomCommunity();
	// }
	//
	// Object[] commANodes = commA.toArray();
	//
	// rndTwoNodes = MathTool.randomInt(0, commANodes.length, 2);
	// edgeANodeAId = (Integer) commANodes[rndTwoNodes[0]];
	// edgeANodeBId = (Integer) commANodes[rndTwoNodes[1]];
	// searchNodesTimes=0;
	// //search edges, untile find or reach times.
	// while (jungNet.findEdge(edgeANodeAId, edgeANodeBId) == null) {
	// rndTwoNodes = MathTool.randomInt(0, commANodes.length, 2);
	// edgeANodeAId = (Integer) commANodes[rndTwoNodes[0]];
	// edgeANodeBId = (Integer) commANodes[rndTwoNodes[1]];
	// searchNodesTimes++;
	// if (searchNodesTimes > commANodes.length * 1000) {
	// endSearchEdges = true;
	// break;
	// }
	// }
	// //has found, end choose community
	// if (!endSearchEdges) {
	// // Debug.outn("find edge in communityA " + edgeANodeAId + " "
	// // + edgeANodeBId);
	// break;
	// }
	//
	// chooseCommTimes++;
	//
	// }/////////while() choose comm
	// if(chooseCommTimes >= comm.getCommunitySize() * 1000){
	// Debug.outn( "no edges in commA, end rewire");
	// break;
	// }
	//
	// // random choose community B
	// Set<Integer> commB;
	// searchNodesTimes=0;
	// endSearchEdges=false;
	// chooseCommTimes=0;
	// int edgeBNodeAId=-1 ;
	// int edgeBNodeBId=-1 ;
	// while (chooseCommTimes < comm.getCommunitySize() * 1000) {
	//
	// commB = comm.randomCommunity();
	// while (commB.size() < 2 || commA.equals(commB)) {
	// commB = comm.randomCommunity();
	// }
	//
	// Object[] commBNodes = commB.toArray();
	// rndTwoNodes = MathTool.randomInt(0, commBNodes.length, 2);
	// edgeBNodeAId = (Integer) commBNodes[rndTwoNodes[0]];
	// edgeBNodeBId = (Integer) commBNodes[rndTwoNodes[1]];
	// searchNodesTimes = 0;
	// while (jungNet.findEdge(edgeBNodeAId, edgeBNodeBId) == null) {
	// rndTwoNodes = MathTool.randomInt(0, commBNodes.length, 2);
	// edgeBNodeAId = (Integer) commBNodes[rndTwoNodes[0]];
	// edgeBNodeBId = (Integer) commBNodes[rndTwoNodes[1]];
	// searchNodesTimes++;
	// if (searchNodesTimes > commBNodes.length * 100) {
	// endSearchEdges = true;
	// break;
	// }
	//
	// }
	// //has found, end choose community
	// if (!endSearchEdges) {
	// // Debug.outn("find edge in communityB" + edgeBNodeAId + " "
	// // + edgeBNodeBId);
	// break;
	// }
	//
	// chooseCommTimes++;
	//
	// }//////////while() choose community B
	//
	// if(chooseCommTimes >= comm.getCommunitySize() * 1000){
	// Debug.outn( "no edges in commB, end rewire");
	// break;
	// }
	//
	// /*------end random choose two links from different community*/
	//
	//
	// boolean rewireOK = rewireEdgeNodes(true,jungNet, edgeANodeAId,
	// edgeBNodeAId, edgeANodeBId, edgeBNodeBId);
	//
	// if (rewireOK) {
	// // remove old edges
	// jungNet.removeEdge(edgeANodeAId + "-" + edgeANodeBId);
	// jungNet.removeEdge(edgeANodeBId + "-" + edgeANodeAId);
	// jungNet.removeEdge(edgeBNodeAId + "-" + edgeBNodeBId);
	// jungNet.removeEdge(edgeBNodeBId + "-" + edgeBNodeAId);
	//
	// // Debug.outn("after rewire,L= " +jungNet.getEdgeCount());
	//
	// rewiredCount++;
	// rewiredCount++;
	//
	// /* save net if condition is met. */
	// float rewiredP = (float) rewiredCount / (float) allEdgeNumber;
	// // Debug.outn(rewiredP+" "+saveInterval*saveThreshold);
	//
	// if ((rewiredP * 100) >= saveInterval * saveThreshold) {
	// Debug.outn("reach t " + rewiredP);
	// int nameSeq;
	// if (saveInterval == 0) {// save each rewing
	// nameSeq = rewiredCount;
	// } else {
	// nameSeq = saveInterval * saveThreshold;
	// }
	// String seq = "";
	// if (nameSeq <= 9) {
	// seq = ".000" + nameSeq;
	// }
	// if (nameSeq >= 10 && nameSeq <= 99) {
	// seq = ".00" + nameSeq;
	// }
	// if (nameSeq >= 100 && nameSeq <= 999) {
	// seq = ".0" + nameSeq;
	// }
	// if (nameSeq >= 1000 && nameSeq <= 9999) {
	// seq = "." + nameSeq;
	// }
	//
	// AbstractNetwork.saveNetToFile(jungNet, filePath + fileName
	// + seq + postfix);
	// saveThreshold++;
	//
	// }// ////save to file
	//
	// }// //////rewire ok
	//
	// }// ////////////////while()

	// Debug.outn("edge number: " + allEdgeNumber + " rewired number "
	// + rewiredCount);
	// if (saveInterval == 0) {
	// //
	// saveNetToFile(newNet,net,filePath+fileName+"."+(int)((float)rewiredCount/(float)allEdgeNumber*100)+postfix);
	// } else {
	// // AbstractNetwork.saveNetToFile(jungNet,
	// // filePath
	// // + fileName
	// // + "."
	// // + (int) ((float) rewiredCount
	// // / (float) allEdgeNumber * 100) + postfix);
	// }
	//
	// }// ////////////////////////////////////////weaken community

	// public static void weakenCommunityWithRepartition(AbstractNetwork net,
	// AbstractCommunity comm, int saveInterval, float rewirePercent,
	// String filePath, String fileName, String postfix) {
	//
	// int saveThreshold = 1;
	// if (saveInterval < 0) {
	// saveInterval = 5;
	// }
	//
	// if (fileName == "") {
	// fileName = net.getNetworkID();
	// }
	// if (postfix.equals("")) {
	// postfix = ".wekCommunity";
	// }
	//
	// int[] allNodeID = net.getNodeIdArray();
	//
	// Vector<AbstractEdge> allEdges = net.getEdgeArray();
	// int allEdgeNumber = allEdges.size();
	// int rewiredCount = 0;
	//
	// UndirectedSparseGraph<Integer, String> jungNet = new
	// UndirectedSparseGraph<Integer, String>();
	//
	// for (int i = 0; i < allEdges.size(); i++) {
	// AbstractEdge edge = allEdges.get(i);
	// jungNet.addEdge(net.idx2Id(edge.getAIdx()) + "-"
	// + net.idx2Id(edge.getBIdx()), net.idx2Id(edge.getAIdx()),
	// net.idx2Id(edge.getBIdx()));
	// }
	//
	// // save original network
	// AbstractNetwork.saveNetToFile(jungNet, filePath + fileName + ".0000"
	// + postfix);
	//
	// /* rewire links until condition */
	//
	// while (true) {
	//
	// if (rewiredCount >= rewirePercent * allEdges.size()) {
	// break;
	// }
	//
	// boolean endSearchEdges = false;
	//
	// /* random choose two links from different communities */
	//
	// // random choose communities A and two nodes in it
	// int[] rndTwoNodes;
	// int chooseCommTimes = 0;
	// int searchNodesTimes = 0;
	// Set<Integer> commA=comm.randomCommunity();;
	// //boolean endChooseComm = false;
	// /*choose community many times*/
	// int edgeANodeAId=0 ;
	// int edgeANodeBId=0 ;
	// while (chooseCommTimes < comm.getCommunitySize() * 1000) {
	//
	// commA = comm.randomCommunity();
	// while (commA.size() < 2) {
	// commA = comm.randomCommunity();
	// }
	//
	// Object[] commANodes = commA.toArray();
	//
	// rndTwoNodes = MathTool.randomInt(0, commANodes.length, 2);
	// edgeANodeAId = (Integer) commANodes[rndTwoNodes[0]];
	// edgeANodeBId = (Integer) commANodes[rndTwoNodes[1]];
	// searchNodesTimes=0;
	// //search edges, untile find or reach times.
	// while (jungNet.findEdge(edgeANodeAId, edgeANodeBId) == null) {
	// rndTwoNodes = MathTool.randomInt(0, commANodes.length, 2);
	// edgeANodeAId = (Integer) commANodes[rndTwoNodes[0]];
	// edgeANodeBId = (Integer) commANodes[rndTwoNodes[1]];
	// searchNodesTimes++;
	// if (searchNodesTimes > commANodes.length * 1000) {
	// endSearchEdges = true;
	// break;
	// }
	// }
	// //has found, end choose community
	// if (!endSearchEdges) {
	// // Debug.outn("find edge in communityA " + edgeANodeAId + " "
	// // + edgeANodeBId);
	// break;
	// }
	//
	// chooseCommTimes++;
	//
	// }/////////while() choose comm
	// if(chooseCommTimes >= comm.getCommunitySize() * 1000){
	// Debug.outn( "no edges in commA, end rewire");
	// break;
	// }
	//
	// // random choose community B
	// Set<Integer> commB;
	// searchNodesTimes=0;
	// endSearchEdges=false;
	// chooseCommTimes=0;
	// int edgeBNodeAId=-1 ;
	// int edgeBNodeBId=-1 ;
	// while (chooseCommTimes < comm.getCommunitySize() * 1000) {
	//
	// commB = comm.randomCommunity();
	// while (commB.size() < 2 || commA.equals(commB)) {
	// commB = comm.randomCommunity();
	// }
	//
	// Object[] commBNodes = commB.toArray();
	// rndTwoNodes = MathTool.randomInt(0, commBNodes.length, 2);
	// edgeBNodeAId = (Integer) commBNodes[rndTwoNodes[0]];
	// edgeBNodeBId = (Integer) commBNodes[rndTwoNodes[1]];
	// searchNodesTimes = 0;
	// while (jungNet.findEdge(edgeBNodeAId, edgeBNodeBId) == null) {
	// rndTwoNodes = MathTool.randomInt(0, commBNodes.length, 2);
	// edgeBNodeAId = (Integer) commBNodes[rndTwoNodes[0]];
	// edgeBNodeBId = (Integer) commBNodes[rndTwoNodes[1]];
	// searchNodesTimes++;
	// if (searchNodesTimes > commBNodes.length * 100) {
	// endSearchEdges = true;
	// break;
	// }
	//
	// }
	// //has found, end choose community
	// if (!endSearchEdges) {
	// // Debug.outn("find edge in communityB" + edgeBNodeAId + " "
	// // + edgeBNodeBId);
	// break;
	// }
	//
	// chooseCommTimes++;
	//
	// }//////////while() choose community B
	//
	// if(chooseCommTimes >= comm.getCommunitySize() * 1000){
	// Debug.outn( "no edges in commB, end rewire");
	// break;
	// }
	//
	// /*------end random choose two links from different community*/
	//
	//
	// boolean rewireOK = rewireEdgeNodes(
	// true,jungNet, edgeANodeAId,
	// edgeBNodeAId, edgeANodeBId, edgeBNodeBId);
	//
	// if (rewireOK) {
	// // remove old edges
	// jungNet.removeEdge(edgeANodeAId + "-" + edgeANodeBId);
	// jungNet.removeEdge(edgeANodeBId + "-" + edgeANodeAId);
	// jungNet.removeEdge(edgeBNodeAId + "-" + edgeBNodeBId);
	// jungNet.removeEdge(edgeBNodeBId + "-" + edgeBNodeAId);
	//
	// // Debug.outn("after rewire,L= " +jungNet.getEdgeCount());
	//
	// rewiredCount++;
	// rewiredCount++;
	//
	// /* save net if condition is met. */
	// float rewiredP = (float) rewiredCount / (float) allEdgeNumber;
	// // Debug.outn(rewiredP+" "+saveInterval*saveThreshold);
	//
	// if ((rewiredP * 100) >= saveInterval * saveThreshold) {
	// Debug.outn("reach t " + rewiredP);
	// int nameSeq;
	// if (saveInterval == 0) {// save each rewing
	// nameSeq = rewiredCount;
	// } else {
	// nameSeq = saveInterval * saveThreshold;
	// }
	// String seq = "";
	// if (nameSeq <= 9) {
	// seq = ".000" + nameSeq;
	// }
	// if (nameSeq >= 10 && nameSeq <= 99) {
	// seq = ".00" + nameSeq;
	// }
	// if (nameSeq >= 100 && nameSeq <= 999) {
	// seq = ".0" + nameSeq;
	// }
	// if (nameSeq >= 1000 && nameSeq <= 9999) {
	// seq = "." + nameSeq;
	// }
	//
	// AbstractNetwork.saveNetToFile(jungNet, filePath + fileName
	// + seq + postfix);
	// saveThreshold++;
	//
	// }// ////save to file
	//
	// }// //////rewire ok
	//
	// }// ////////////////while()
	//
	// Debug.outn("edge number: " + allEdgeNumber + " rewired number "
	// + rewiredCount);
	// if (saveInterval == 0) {
	// //
	// saveNetToFile(newNet,net,filePath+fileName+"."+(int)((float)rewiredCount/(float)allEdgeNumber*100)+postfix);
	// } else {
	// // AbstractNetwork.saveNetToFile(jungNet,
	// // filePath
	// // + fileName
	// // + "."
	// // + (int) ((float) rewiredCount
	// // / (float) allEdgeNumber * 100) + postfix);
	// }
	//
	// }

	/**
	 * 
	 * @param degree
	 * @return
	 */
	private static int randomChooseDegreeAvailableNode(int[] degree) {
		Vector<Integer> availableNodeIndex = new Vector<Integer>();

		for (int i = 0; i < degree.length; i++) {
			if (degree[i] > 0) {
				availableNodeIndex.add(i);
			}
		}
		Debug.outn("available node number " + availableNodeIndex.size());
		int choosIndex = MathTool.randomInt(0, availableNodeIndex.size(), 1)[0];
		return availableNodeIndex.get(choosIndex);
	}

	/**
	 * new edge one: edgeAnodeA - edgeBNodeD new edge two: edgeAnodeB -
	 * edgeBNodeC
	 * 
	 * @param net
	 * @param edgeANodeA
	 * @param edgeBNodeD
	 * @param edgeANodeB
	 * @param edgeBNodeC
	 * @return
	 */
	private static boolean rewireEdgeNodes(AbstractV orgEdgeANodeA,
			AbstractV orgEdgeANodeB, AbstractV orgEdgeBNodeC,
			AbstractV orgEdgeBNodeD, boolean keepConnected,
			AbstractNetwork net, AbstractV edgeANodeA, AbstractV edgeBNodeD,
			AbstractV edgeANodeB, AbstractV edgeBNodeC) {
		// Debug.outn("///////////////             ///////edge num before rewire "+
		// jungNet.getEdgeCount());

		// Debug.outn("org nodeA "+orgEdgeANodeA+" nodeA Ngb "+orgEdgeANodeB
		// +" nodeB "+orgEdgeBNodeC+" nodeB ngb "+orgEdgeBNodeD);
		// Debug.outn(jungNet.findEdge(orgEdgeANodeA, orgEdgeANodeB)+
		// " "+ jungNet.findEdge(orgEdgeBNodeC, orgEdgeBNodeD));

		// Iterator edgeIt = jungNet.getEdges().iterator();
		// while(edgeIt.hasNext()){
		// Debug.outn(edgeIt.next().toString());
		// }

		// Debug.outn("new edge "+edgeANodeA+" - "+edgeBNodeD+"  "+edgeANodeB+" - "+edgeBNodeC);

		/* check common neighbor node */
		if (edgeANodeA == edgeBNodeC || edgeANodeA == edgeBNodeD
				|| edgeANodeB == edgeBNodeC || edgeANodeB == edgeBNodeD) {
			// Debug.outn("------------has common neighbor node");
			return false;
		}

		// check whether new edges exist
		AbstractE newEdgeAExists = net.findEdge(edgeANodeA, edgeBNodeD);

		AbstractE newEdgeBExists = net.findEdge(edgeANodeB, edgeBNodeC);
		if (newEdgeAExists != null || newEdgeBExists != null) {
			// Debug.outn("new edge has existed"+newEdgeAExists+" "+newEdgeBExists);
			return false;
		}

		// add new edges
		boolean addNewEdgeA = net.addEdge(net.createNewEdge(), edgeANodeA,
				edgeBNodeD);
		boolean addNewEdgeB = net.addEdge(net.createNewEdge(), edgeANodeB,
				edgeBNodeC);
		// remove original edges
		net.removeEdge(orgEdgeANodeA, orgEdgeANodeB);
		net.removeEdge(orgEdgeBNodeC, orgEdgeBNodeD);

		// Debug.outn("remove edge "+jungNet.removeEdge(
		// jungNet.findEdge(orgEdgeANodeA, orgEdgeANodeB)) );

		// Debug.outn( "remove edge "+jungNet.removeEdge(
		// jungNet.findEdge(orgEdgeBNodeC, orgEdgeBNodeD)) );

		// jungNet.removeEdge(orgEdgeANodeA + "-" + orgEdgeANodeB);
		// jungNet.removeEdge(orgEdgeBNodeC + "-" + orgEdgeBNodeD);
		// jungNet.removeEdge(orgEdgeANodeB + "-" +orgEdgeANodeA );
		// jungNet.removeEdge( orgEdgeBNodeD+ "-" + orgEdgeBNodeC);

		/* add new edges successfully */
		if (addNewEdgeA && addNewEdgeB) {

			// check connection
			if (keepConnected && !Partitions.connected(net)) {
				// Debug.outn("disconenct after rewire");
				// remove new edges
				net.removeEdge(edgeANodeA, edgeBNodeD);
				net.removeEdge(edgeANodeB, edgeBNodeC);

				// recover old edges
				net.addEdge(net.createNewEdge(), orgEdgeANodeA, orgEdgeANodeB);
				net.addEdge(net.createNewEdge(), orgEdgeBNodeC, orgEdgeBNodeD);

				// Debug.outn("edge num unconnected "+ jungNet.getEdgeCount());

				return false;
			}

			// Debug.outn("edge num rewire ok "+ jungNet.getEdgeCount());

			return true;

		} else { // add new edge fail, remove new edges.
			// Debug.outn("insert new edge fail, recover");

			// remove new edges
			net.removeEdge(edgeANodeA, edgeBNodeD);
			net.removeEdge(edgeANodeB, edgeBNodeC);

			// recover old edges
			net.addEdge(net.createNewEdge(), orgEdgeANodeA, orgEdgeANodeB);
			net.addEdge(net.createNewEdge(), orgEdgeBNodeC, orgEdgeBNodeD);

			// Debug.outn("add new edge fail ");
			return false;

			// Debug.outn("exchange fail ,recover degree
			// \n"+net.getNodeDegreeByIdx(edgeA.getAIdx())+" "+
			// net.getNodeDegreeByIdx(edgeA.getBIdx()));
			// Debug.outn(newNet.getNeighborCount(edgeB.getAIdx())+" "+
			// newNet.getNeighborCount(edgeB.getBIdx()));
		}
	}// ///////////////////////////change edge nodes

	public static String linkType(int edgeANodeADegree, int edgeANodeBDegree,
			int edgeBNodeCDegree, int edgeBNodeDDegree) {

		if (edgeANodeADegree > edgeANodeBDegree) {

			if (edgeBNodeCDegree > edgeBNodeDDegree) {

				// 1-2 3-4
				if (edgeANodeBDegree > edgeBNodeCDegree)
					return "1234";

				// 1-4 2-3
				if ((edgeANodeADegree > edgeBNodeCDegree && edgeANodeBDegree < edgeBNodeDDegree)
						|| (edgeBNodeCDegree > edgeANodeADegree && edgeBNodeDDegree < edgeANodeBDegree))
					return "1423";

				// 1-3 2-4
				if ((edgeANodeADegree > edgeBNodeCDegree
						&& edgeANodeBDegree > edgeBNodeDDegree && edgeANodeBDegree < edgeBNodeCDegree)
						|| (edgeBNodeCDegree > edgeANodeADegree
								&& edgeBNodeDDegree > edgeANodeBDegree && edgeBNodeDDegree < edgeANodeADegree))
					return "1324";

			} else {
				if (edgeBNodeCDegree < edgeBNodeDDegree) {

					// 1-2 4-3
					if (edgeANodeBDegree > edgeBNodeDDegree)
						return "1243";

					// 1-4 3-2
					if ((edgeANodeADegree > edgeBNodeDDegree && edgeANodeBDegree < edgeBNodeCDegree)
							|| (edgeBNodeDDegree > edgeANodeADegree && edgeBNodeCDegree < edgeANodeBDegree))
						return "1432";

					// 1-3 4-2
					if ((edgeANodeADegree > edgeBNodeDDegree
							&& edgeANodeBDegree > edgeBNodeCDegree && edgeANodeBDegree < edgeBNodeDDegree)
							|| (edgeBNodeDDegree > edgeANodeADegree
									&& edgeBNodeCDegree > edgeANodeBDegree && edgeBNodeCDegree < edgeANodeADegree))
						return "1342";

				}
			}

		} else {
			if (edgeANodeADegree < edgeANodeBDegree) {

				if (edgeBNodeCDegree > edgeBNodeDDegree) {

					// 2-1 3-4
					if (edgeANodeADegree > edgeBNodeCDegree)
						return "2134";

					// 4-1 2-3
					if ((edgeANodeBDegree > edgeBNodeCDegree && edgeANodeADegree < edgeBNodeDDegree)
							|| (edgeBNodeCDegree > edgeANodeBDegree && edgeBNodeDDegree < edgeANodeADegree))
						return "4123";

					// 3-1 2-4
					if ((edgeANodeBDegree > edgeBNodeCDegree
							&& edgeANodeADegree > edgeBNodeDDegree && edgeANodeADegree < edgeBNodeCDegree)
							|| (edgeBNodeCDegree > edgeANodeBDegree
									&& edgeBNodeDDegree > edgeANodeADegree && edgeBNodeDDegree < edgeANodeBDegree))
						return "3124";

				} else {
					if (edgeBNodeCDegree < edgeBNodeDDegree) {

						// 2-1 4-3
						if (edgeANodeADegree > edgeBNodeDDegree)
							return "2143";

						// 1-4 2-3
						if ((edgeANodeBDegree > edgeBNodeDDegree && edgeANodeADegree < edgeBNodeCDegree)
								|| (edgeBNodeDDegree > edgeANodeBDegree && edgeBNodeCDegree < edgeANodeADegree))
							return "4132";

						// 1-3 2-4
						if ((edgeANodeBDegree > edgeBNodeDDegree
								&& edgeANodeADegree > edgeBNodeCDegree && edgeANodeADegree < edgeBNodeDDegree)
								|| (edgeBNodeDDegree > edgeANodeBDegree
										&& edgeBNodeCDegree > edgeANodeADegree && edgeBNodeCDegree < edgeANodeBDegree))
							return "3142";
					}
				}

			}
		}

		return "others";
	}// //////////////////////////////link type

	/**
	 * delete nodes which degree is <=degree
	 * 
	 * @param net
	 * @param degree
	 */
	public static void deleteNode(AbstractNetwork net, int degree,
			String filePath, String fileName, String postfix) {

		// store node wiil be deleted
		Vector<String> nodeDel = new Vector<String>();

		Vector<String> allNodeID = net.getAllVertexIDVector();
		int[] allDegree = new int[allNodeID.size()];
		// get all the node ID
		String ID;
		for (int i = 0; i < allNodeID.size(); i++) {
			ID = allNodeID.get(i);
			allDegree[i] = net.getVertexDegree(ID);
			if (allDegree[i] <= degree) {// delete
				nodeDel.add(ID);
			}
		}

		Vector<AbstractE> allEdges = net.getAllEdges();
		int allEdgeNumber = allEdges.size();
		int rewiredCount = 0;

		int tryTimes = 10 * allEdgeNumber;// stop choose edges to rewire when
		// count reach this number

		for (int j = 0; j < nodeDel.size(); j++) {
			net.removeVertex(net.getVertex(nodeDel.get(j)));
		}

		postfix = ".del" + degree;
		fileName = net.getNetID();
		// save new network
		net.saveNetToFile(filePath + fileName + postfix);

	}

}// ////////////////////////calss

// end/////////////////////////////////////////////////

class NodeDegreeComp implements Comparator<String> {
	// private boolean rewired;
	private AbstractNetwork net;

	public NodeDegreeComp(AbstractNetwork net) {

		this.net = net;
	}

	public int compare(String o1, String o2) {
		int d1 = net.getVertexDegree(o1);
		int d2 = net.getVertexDegree(o2);
		if (d1 > d2) {
			return -1;
		}
		if (d1 < d2) {
			return 1;
		}
		if (d1 == d2) {
			return 0;
		}
		return 0;
	}

}
