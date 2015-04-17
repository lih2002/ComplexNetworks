/**
 * 
 */
package network;

import java.util.Vector;
import tools.*;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Iterator;
import edu.uci.ics.jung.graph.util.*;

/**
 * community network. convert one community to one network. sub community as one
 * node, links between sub communities as link. node of converted network
 * contain two properties: inside links number. inside nodes number.
 * 
 * @author gexin
 * 
 */
public class CommunityNetwork extends AbstractNetwork {

	private int originalNodeNum;
	private int originalEdgeNum;

	/*
	 * (non-Javadoc)
	 * 
	 * @see complex.network.AbstractNetwork#clone()
	 */
	@Override
	public AbstractNetwork clone() {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see complex.network.AbstractNetwork#cloneOneBlankNetwork()
	 */
	@Override
	public AbstractNetwork cloneOneBlankNetwork() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		float[] k1 = { 0.3f, 0.2f, 0.3f, 0.2f };
		float[] k2 = { 0.25f, 0.21428572f, 0.25f, 0.2857143f };
		float c = new CommunityNetwork().calculateCorrelation(k1, k2);
		Debug.outn(c);

	}

	/**
	 * get total link number in original network that generates community net.
	 * </p>= sum(inside link number + link weight)
	 * 
	 * @return
	 */
	public int getEdgeCountOfOriginalNetwork() {
		int linkN = 0;

		Vector<AbstractV> allNodes = this.getAllVertex();

		// append link inside sub-community
		for (AbstractV eachNode : allNodes) {
			linkN += ((CommunityNetNode) eachNode).getInsideLinkNum();
		}

		// append link between sub-communities
		Vector<AbstractE> allEdge = this.getAllEdges();
		for (int j = 0; j < allEdge.size(); j++) {
			linkN += allEdge.get(j).getWeight();
		}
		return linkN;
	}

	/**
	 * get total node number in original network that generates community net.
	 * 
	 * @return
	 */
	public int getNodeCountOfOriginalNetwork() {
		int count = 0;

		// append link inside sub-community
		for (AbstractV eachNode : this.getAllVertex()) {
			count += ((CommunityNetNode) eachNode).getInsideNodeNum();
		}
		return count;
	}

	public int getOriginalNodeNum() {
		return originalNodeNum;
	}

	public void setOriginalNodeNum(int originalNodeNum) {
		this.originalNodeNum = originalNodeNum;
	}

	public int getOriginalEdgeNum() {
		return originalEdgeNum;
	}

	public void setOriginalEdgeNum(int originalEdgeNum) {
		this.originalEdgeNum = originalEdgeNum;
	}

	/**
	 * link weight correlation for one node
	 * 
	 * @return
	 */
	public float linkWeightCorrelation() {

		float orgVar = this.getStandVarLinkWeight();
		float randVar = this.getReshuffledVarianceLinkWeight(10000);
		// Debug.outn("link wieght correlation "+orgVar+"  "+randVar);

		// Debug.outn(randVar);
		return orgVar / randVar;

	}

	/**
	 * get link normalized weigths around specified node.
	 * 
	 * @param inputNode
	 * @return
	 */
	public float[] getLinkWeightsAroundNode(AbstractV inputNode) {

		float[] weight = new float[this.getNeighborCount(inputNode)];
		int k = 0;
		// get weight of each edge links inputNode and its neighbor.
		for (AbstractV eachNode : this.getNeighbors(inputNode)) {
			weight[k++] = this.findEdge(inputNode, eachNode).getWeight();
		}

		return weight;
	}

	/**
	 * 
	 * @return
	 */
	public float getStandVarLinkWeight() {

		float sumOfVar = 0;

		for (AbstractV eachNode : getAllVertex()) {
			sumOfVar += Math.sqrt(getVarianceLinkWeigthAroundNode(eachNode));
		}

		return sumOfVar / this.getVertexCount();
	}

	/**
	 * variance of link weights around specified node.
	 * 
	 * @param inputNode
	 * @return
	 */
	public float getVarianceLinkWeigthAroundNode(AbstractV inputNode) {

		float varLW = 0;
		float[] ngbWeight = getLinkWeightsAroundNode(inputNode);
		float avgWeight = MathTool.average(ngbWeight);
		for (int i = 0; i < ngbWeight.length; i++) {
			varLW += (ngbWeight[i] - avgWeight) * (ngbWeight[i] - avgWeight);
		}
		// Debug.outn("var around node "+nodeID+" "+varLW);
		return varLW;
	}

	/**
	 * 
	 * @return
	 */
	public double[] getAllLinkWeights() {

		Iterator<AbstractE> edgeIt = this.getEdges().iterator();

		double[] weights = new double[this.getEdgeCount()];
		int index = 0;
		while (edgeIt.hasNext()) {
			weights[index++] = edgeIt.next().getWeight();
		}
		return weights;
	}

	/**
	 * randomize the distribution of weight and get variance.
	 * 
	 * @param repeatTime
	 * @return
	 */
	public float getReshuffledVarianceLinkWeight(int repeatTime) {

		Vector<AbstractE> allEdges = this.getAllEdges();

		/* map edge--edge weight */
		HashMap<AbstractE, Float> edgeWeightMap = new HashMap<AbstractE, Float>();
		Vector<Float> allWeights = new Vector<Float>();
		Vector<Float> usedWeights = new Vector<Float>();

		// get all weights
		for (int p = 0; p < allEdges.size(); p++) {
			AbstractE edge = allEdges.get(p);
			allWeights.add((float) edge.getWeight());
		}

		float sumOfRepeatVar = 0;// sum of standard var of network for repeat
									// reshuffle

		/**
		 * reshuffle weights for all the edges. many times.
		 */
		for (int i = 0; i < repeatTime; i++) {

			for (int k = 0; k < allEdges.size(); k++) {
				AbstractE edge = allEdges.get(k);
				int randomIndex = MathTool.randomInt(0, allWeights.size(), 1)[0];
				// set new weight for edge
				edgeWeightMap.put(edge, allWeights.get(randomIndex));

				usedWeights.add(allWeights.get(randomIndex));
				allWeights.remove(randomIndex);
			}

			// each node, entire network.
			float sumStdVar = 0;// sum of all standard var in one network
			for (AbstractV eachNode : this.getVertices()) {

				float var = 0;

				float[] linkWeights = new float[this.getNeighborCount(eachNode)];

				// each link connects this node
				int count = 0;
				for (AbstractV eachNgb : getNeighbors(eachNode)) {
					linkWeights[count++] = edgeWeightMap.get(this.findEdge(
							eachNode, eachNgb));
				}
				float avgWeight = MathTool.average(linkWeights);

				// var
				count = 0;
				for (AbstractV eachNgb : getNeighbors(eachNode)) {
					var += (linkWeights[count] - avgWeight)
							* (linkWeights[count] - avgWeight);
					count++;
				}

				sumStdVar += Math.sqrt(var);
				// Debug.outn("each node std var "+Math.sqrt(var));

			}// /// each node

			sumOfRepeatVar += sumStdVar / this.getVertexCount();

			Vector<Float> temp;
			temp = allWeights;
			allWeights = usedWeights;
			usedWeights = temp;
		}// //////////////repeat

		// Debug.outn("sum of repeat var "+sumOfRepeatVar);

		return sumOfRepeatVar / repeatTime;
	}

	/**
	 * 
	 * @return
	 */
	public float nodeLinkWLinkW() {
		float corr = 0;
		float[] nodeLinkWeights = new float[this.getVertexCount()];
		float[] linkW = new float[this.getVertexCount()];

		int count = 0;
		for (AbstractV eachNode : this.getVertices()) {
			nodeLinkWeights[count] = ((CommunityNetNode) eachNode)
					.getInsideLinkNum() / (float) this.getOriginalEdgeNum();

			linkW[count] = MathTool.average(getLinkWeightsAroundNode(eachNode))
					/ (float) MathTool.sum(this.getAllLinkWeights());
			count++;
		}

		corr = this.calculateCorrelation(nodeLinkWeights, linkW);
		float sumNLW = 0;
		float sumLW = 0;
		for (int i = 0; i < linkW.length; i++) {
			sumNLW += nodeLinkWeights[i];
			sumLW += linkW[i];
			// Debug.outn("nodelinkW "+nodeLinkWeights[i]+" linkW "+linkW[i]);
		}
		// Debug.outn("sumNLW "+sumNLW+" sumLW "+sumLW);

		return corr;
	}

	/**
	 * 
	 * @return
	 */
	public float nodeNodeWLinkW() {
		int netSize = this.getVertexCount();
		float corr = 0;
		float[] nodeNodeWeights = new float[netSize];
		float[] nodeLinkW = new float[netSize];

		int count = 0;
		for (AbstractV eachNode : this.getVertices()) {
			nodeNodeWeights[count] = ((CommunityNetNode) eachNode)
					.getInsideNodeNum() / (float) this.getOriginalNodeNum();

			nodeLinkW[count] = MathTool
					.average(getLinkWeightsAroundNode(eachNode))
					/ (float) MathTool.sum(getAllLinkWeights());

			count++;
		}

		corr = this.calculateCorrelation(nodeNodeWeights, nodeLinkW);
		return corr;
	}

	/**
	 * * node node weight and node link weight on one node.
	 * 
	 * @return
	 */
	public float nodeNodeWNodeLinkWCorrelation() {
		int netSize = this.getVertexCount();
		float corr = 0;
		float[] nodeNodeW = new float[netSize];
		float[] nodeLinkW = new float[netSize];

		int count = 0;
		for (AbstractV eachNode : this.getVertices()) {
			CommunityNetNode node = (CommunityNetNode) eachNode;
			// Debug.outn(" NNW NLW "+node.getInsideNodeNum()+"  "+node.getInsideLinkNum());
			nodeNodeW[count] = (float) node.getInsideNodeNum()
					/ (float) this.getOriginalNodeNum();
			nodeLinkW[count] = (float) node.getInsideLinkNum()
					/ (float) this.getOriginalEdgeNum();
			count++;
			// Debug.outn(nodeNodeW[i]+" "+nodeLinkW[i]);
		}

		corr = this.calculateCorrelation(nodeNodeW, nodeLinkW);
		return corr;

	}

	/**
	 * 
	 * @return
	 */
	public float nodeNodeWNodeLinkWBetweenEdgeCorrelation(){
		float ddCorr = 0;
		
		int edgeNum=this.getEdgeCount();
		float[] nodeNodeW = new float[2*edgeNum];
		float[] nodeLinkW = new float[2*edgeNum];
		
		int count=0;
		for(AbstractE eachE:this.getEdges()){
			CommunityNetEdge edge =(CommunityNetEdge) eachE;	
			Pair<AbstractV> pair = this.getEndpoints(edge);
			
			CommunityNetNode nodeA =(CommunityNetNode) pair.getFirst();
			CommunityNetNode nodeB = (CommunityNetNode)pair.getSecond();
			
			nodeNodeW[count] = (float) nodeA.getInsideNodeNum()
				/ this.getOriginalNodeNum();
			nodeLinkW[count] = (float) nodeB.getInsideLinkNum()
				/ this.getOriginalEdgeNum();
	
			nodeNodeW[count+1] = (float) nodeB.getInsideNodeNum()
				/ this.getOriginalNodeNum();
			nodeLinkW[count+1] = (float) nodeA.getInsideLinkNum()
				/ this.getOriginalEdgeNum();
			
			count++;
			
		}

		ddCorr=this.calculateCorrelation(nodeNodeW, nodeLinkW);
		//Debug.outn(ddCorr);
		
		return ddCorr;
	}

	/**
	 * 
	 * @return
	 */
	public float degreeLingkWeightCorrelation() { // ///////////////
		float corr = 0;
		int nodeNum= this.getVertexCount();
		float[] degrees = new float[nodeNum];
		float[] linkW = new float[nodeNum];

		
		int count = 0;
		for (AbstractV eachNode : this.getVertices()) {
			degrees[count] = (float) this.getNeighborCount(eachNode)
			/ (float) this.getEdgeCount() / 2;

			linkW[count] = MathTool.average(getLinkWeightsAroundNode(eachNode))
				/ (float) MathTool.sum(getAllLinkWeights());
			
			count++;
		}
		
		corr = this.calculateCorrelation(degrees, linkW);
		return corr;
	}

	/**
	 * 
	 * @return
	 */
	public float degreeNodeNodeWInOneNodeCorrelation() {

		float corr = 0;
		int nodeNum= this.getVertexCount();
		float[] degrees = new float[nodeNum];
		float[] degree = new float[nodeNum];
		float[] nodeNodeW = new float[nodeNum];
		
			
		int count = 0;
		for (AbstractV eachNode : this.getVertices()) {
			degree[count] = (float) this.getNeighborCount(eachNode)
			/ (float) this.getEdgeCount() / 2;

			nodeNodeW[count] = (float) ((CommunityNetNode)eachNode)
			.getInsideNodeNum() / (float) this.getOriginalNodeNum();
			count++;
		}		
		
		corr = this.calculateCorrelation(degree, nodeNodeW);
		Debug.outn(degree, " d");
		Debug.outn(nodeNodeW, " nnw");
		Debug.outn("d nnw " + corr);
		return corr;

	}

	/**
	 * 
	 * @return
	 */
	public float degreeNodeNodeWBetweenEdgeCorrelation() {
		float ddCorr = 0;
		int edgeNum=this.getEdgeCount();

		float[] degree = new float[2 * edgeNum];
		float[] nodeNodeW = new float[2 * edgeNum];
		
		
		Vector<AbstractE> allEdge = this.getAllEdges();
		for (int i = 0; i < allEdge.size(); i = i + 2) {

			CommunityNetEdge edge = (CommunityNetEdge) allEdge.get(i);
			Pair<AbstractV> pair=this.getEndpoints(allEdge.get(i));
			CommunityNetNode nodeA = (CommunityNetNode)pair.getFirst();
			CommunityNetNode nodeB = (CommunityNetNode)pair.getSecond();


			degree[i] = (float) this.getNeighborCount(nodeA)
					/ this.getEdgeCount() * 2;
			nodeNodeW[i] = (float) nodeB.getInsideNodeNum()
					/ this.getOriginalNodeNum();

			degree[i + 1] = (float) this.getNeighborCount(nodeB)
					/ this.getEdgeCount() * 2;
			nodeNodeW[i + 1] = (float) nodeA.getInsideLinkNum()
					/ this.getOriginalNodeNum();
			// Debug.outn("degree degree "+d1[i]+" "+d2[i]);

		}

		ddCorr = this.calculateCorrelation(degree, nodeNodeW);
		// Debug.outn(ddCorr);

		return ddCorr;
	}

	/**
	 * 
	 * @return
	 */
	public float degreeNodeLinkWInOneNodeCorrelation() {

		float corr = 0;
		int nodeNum= this.getVertexCount();
		int edgeNum=this.getEdgeCount();
		float[] degree = new float[nodeNum];
		float[] nodeLinkW = new float[nodeNum];
		
		int count = 0;
		for (AbstractV eachNode : this.getVertices()) {
			degree[count] = (float) this.getNeighborCount(eachNode)
				/ (float) edgeNum / 2;

			nodeLinkW[count] = (float) ((CommunityNetNode) eachNode)
				.getInsideLinkNum() / (float) this.getOriginalEdgeNum();
		}
		count++;
		
		corr = this.calculateCorrelation(degree, nodeLinkW);
		return corr;
	}

	/**
	 * degree - node link weight correlation. An edge contains two pairs of
	 * degree-node link weight
	 * 
	 * @return
	 */
	public float degreeNodeLinkWBetweenEdgeCorrelation() {

		float ddCorr = 0;
		int edgeNum=this.getEdgeCount();
		
		float[] degree = new float[2 * edgeNum];
		float[] nodeLinkW = new float[2 * edgeNum];
		
		Vector<AbstractE> allEdge = this.getAllEdges();
		for (int i = 0; i < allEdge.size(); i = i + 2) {

			Pair<AbstractV> pair=this.getEndpoints(allEdge.get(i));
			CommunityNetNode nodeA = (CommunityNetNode)pair.getFirst();
			CommunityNetNode nodeB = (CommunityNetNode)pair.getSecond();

			degree[i] = (float) this.getNeighborCount(nodeA)
					/ edgeNum * 2;
			nodeLinkW[i] = (float) nodeB.getInsideLinkNum()
					/ edgeNum;

			degree[i + 1] = (float) this.getNeighborCount(nodeB)
					/ edgeNum * 2;
			nodeLinkW[i + 1] = (float) nodeA.getInsideLinkNum()
					/ this.getOriginalEdgeNum();
			// Debug.outn("degree degree "+d1[i]+" "+d2[i]);

		}
		
		ddCorr = this.calculateCorrelation(degree, nodeLinkW);
		// Debug.outn(ddCorr);

		return ddCorr;
	}

	/**
	 * 
	 * @return
	 */
	public float nodeLinkWCorrelation() {
		float corr = 0;
		int edgeNum=this.getEdgeCount();
		Vector<AbstractE> allEdge = this.getAllEdges();
		float[] nodeALinkW = new float[edgeNum];
		float[] nodeBLinkW = new float[edgeNum];

		for (int i = 0; i < allEdge.size(); i++) {
			
		
			Pair<AbstractV> pair=this.getEndpoints(allEdge.get(i));
			CommunityNetNode nodeA = (CommunityNetNode)pair.getFirst();
			CommunityNetNode nodeB = (CommunityNetNode)pair.getSecond();
		
			nodeALinkW[i] = nodeA.getInsideLinkNum();
			nodeBLinkW[i] = nodeB.getInsideLinkNum();
		}
		corr = this.calculateCorrelation(nodeALinkW, nodeBLinkW);
		// Debug.outn("nodeLinkWeight correlation " + corr);
		return corr;
	}

	/**
	 * node node weight between two nodes.
	 * 
	 * @return
	 */
	public float nodeNodeWCorrelation() {
		float corr = 0;
		Vector<AbstractE> allEdge = this.getAllEdges();
		float[] nodeALinkW = new float[allEdge.size()];
		float[] nodeBLinkW = new float[allEdge.size()];

		for (int i = 0; i < allEdge.size(); i++) {
			
			Pair<AbstractV> pair=this.getEndpoints(allEdge.get(i));
			CommunityNetNode nodeA = (CommunityNetNode)pair.getFirst();
			CommunityNetNode nodeB = (CommunityNetNode)pair.getSecond();
			
			nodeALinkW[i] = nodeA.getInsideNodeNum();
			nodeBLinkW[i] = nodeB.getInsideNodeNum();
		}
		corr = this.calculateCorrelation(nodeALinkW, nodeBLinkW);
		// Debug.outn("nodeNodeWeight correlation " + corr);

		return corr;
	}

	/**
	 * node node correlation between two nodes
	 * 
	 * @return
	 */
	public float degreeCorrelation() {
		float ddCorr = 0;
		int edgeNum=this.getEdgeCount();

		
		float[] d1 = new float[edgeNum];
		float[] d2 = new float[edgeNum];
		
		
		Vector<AbstractE> allEdge = this.getAllEdges();
		for (int i = 0; i < allEdge.size(); i++) {
			Pair<AbstractV> pair=this.getEndpoints(allEdge.get(i));
			CommunityNetNode nodeA = (CommunityNetNode)pair.getFirst();
			CommunityNetNode nodeB = (CommunityNetNode)pair.getSecond();

			d1[i] = (float) this.getNeighborCount(nodeA)
					/ edgeNum / 2;
			d2[i] = (float)this.getNeighborCount(nodeB) 
					/ edgeNum/ 2;
			// Debug.outn("degree degree "+d1[i]+" "+d2[i]);

		}

		ddCorr = this.calculateCorrelation(d1, d2);
		// Debug.outn("degree correlation " + ddCorr);

		return ddCorr;
	}

	public void getNNWNLW() {
		nodeNodeWNodeLinkWCorrelation();
	}

	/**
	 * 
	 * @param columnName
	 * @return
	 */
	public String getAllCorrelation(boolean columnName) {
		StringBuffer corr = new StringBuffer();
		if (columnName) {
			corr.append("netID\t" + "commN\t" + "commL\t" + "orgN\t" + "orgL\t"
					+ "intraL\t" + "inerL\t" + "Degree\t" + "LW\t" + "NNW\t"
					+ "NLW\t" + "NNW_NLW\t" + "Degree_LW\t" + "Degree_NLW\t"
					+ "Degree_NNW\t" + "NNW_LW\t" + "NLW_LW\t" + "\n");
		}
		corr.append(this.getNetID() + "\t");

		corr.append(this.getVertexCount() + "\t");
		corr.append(this.getEdgeCount() + "\t");
		corr.append(this.getOriginalNodeNum() + "\t");
		corr.append(this.getOriginalEdgeNum() + "\t");

		int allIntraLinkNum = this.getAllIntraLinkNumber();
		corr.append((float) allIntraLinkNum / (float) this.getOriginalEdgeNum()
				+ "\t");
		corr.append((float) (getOriginalEdgeNum() - allIntraLinkNum)
				/ (float) getOriginalEdgeNum() + "\t");

		corr.append(this.degreeCorrelation() + "\t");
		corr.append(this.linkWeightCorrelation() + "\t");
		corr.append(this.nodeNodeWCorrelation() + "\t");
		corr.append(this.nodeLinkWCorrelation() + "\t");

		corr.append(this.nodeNodeWNodeLinkWCorrelation() + "\t");

		corr.append(this.degreeLingkWeightCorrelation() + "\t");
		corr.append(this.degreeNodeLinkWInOneNodeCorrelation() + "\t");
		corr.append(this.degreeNodeNodeWInOneNodeCorrelation() + "\t");

		corr.append(this.nodeNodeWLinkW() + "\t");
		corr.append(this.nodeLinkWLinkW());
		corr.append("\n");
		// Debug.outn(corr.toString());
		return corr.toString();

	}

	/**
	 * 
	 * @param k1
	 * @param k2
	 * @return
	 */
	public float calculateCorrelation(float[] k1, float[] k2) {
		float c = 0;
		float[] k1k2 = new float[k1.length];
		float[] k1Andk2 = new float[k1.length];
		float[] k12Andk22 = new float[k1.length];

		for (int i = 0; i < k1.length; i++) {
			k1k2[i] = k1[i] * k2[i];
			k1Andk2[i] = (k1[i] + k2[i]);
			k12Andk22[i] = k1[i] * k1[i] + k2[i] * k2[i];
		}

		float top = 4 * MathTool.average(k1k2) - MathTool.average(k1Andk2)
				* MathTool.average(k1Andk2);
		float bot = 2 * MathTool.average(k12Andk22) - MathTool.average(k1Andk2)
				* MathTool.average(k1Andk2);

		if (bot < 1E-6 && bot > -1E-6) {
			return Float.NaN;
		}

		// Debug.outn("zi "+
		// Float.toString(4*MathTool.average(k1k2)-MathTool.average(k1Andk2)*MathTool.average(k1Andk2)
		// ));
		// Debug.outn("mu "+ Float.toString(2*MathTool.average(k12Andk22)-
		// MathTool.average(k1Andk2)*MathTool.average(k1Andk2)) );

		return top / bot;
	}

	public int getAllIntraLinkNumber() {
		int number = 0;
		Vector<AbstractE> allEdge = this.getAllEdges();
		for (int i = 0; i < allEdge.size(); i++) {
			number += allEdge.get(i).getWeight();
		}
		return number;

	}

	public int getNodeNodeWeight(AbstractV node) {

		return ((CommunityNetNode)node)
				.getInsideNodeNum();
	}

	public int getNodeLinkWeight(AbstractV node) {
		// TODO Auto-generated method stub
		return ((CommunityNetNode) node)
				.getInsideLinkNum();
	}

	
	public float[][] getNNW_NLW() {
		
		int nodeNum=getVertexCount();
		float[][] nnw_nlw = new float[nodeNum][2];
		
		int count=0;
		
		for (AbstractV eachNode:this.getVertices()) {
		
			nnw_nlw[count][0] = ((CommunityNetNode)eachNode).getInsideNodeNum()
					/ (float) this.originalNodeNum;
			nnw_nlw[count][1] = ((CommunityNetNode)eachNode).getInsideLinkNum()
					/ (float) this.originalEdgeNum;
			count++;

		}
		return nnw_nlw;
	}

	/**
	 * 
	 * @return
	 */
	public int[] getSubCommunitySize() {
		int nodeNum=getVertexCount();
		
		int[] commSize = new int[nodeNum];
		
		int count=0;
		
		for (AbstractV eachNode:this.getVertices()) {
			commSize[count] = ((CommunityNetNode) eachNode)
					.getInsideNodeNum();
			count++;
		}

		Arrays.sort(commSize);
		return commSize;

	}

	@Override
	public AbstractV createNewVertex(String vertexID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractNetwork getBlankNetwork() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractE createNewEdge() {
		// TODO Auto-generated method stub
		return null;
	}

}// ///////////////////////////////////////////////////////////////////////////////////

/**
 * common network nodes
 * 
 * @author ge xin
 * 
 */
class CommunityNetNode extends AbstractV {

	private int insideLinkNum;
	private int insideNodeNum;
	private String id;

	public CommunityNetNode() {
		super();
	}

	public CommunityNetNode(String id) {

		this.id=id;
	}

	@Override
	public AbstractV clone() {
		AbstractV newNode = new CommunityNetNode();

	
		return newNode;
	}

	public int getInsideLinkNum() {
		return insideLinkNum;
	}

	public void setInsideLinkNum(int insideLinkNum) {
		this.insideLinkNum = insideLinkNum;
	}

	public int getInsideNodeNum() {
		return insideNodeNum;
	}

	public void setInsideNodeNum(int insideNodeNum) {
		this.insideNodeNum = insideNodeNum;
	}

}// //////////////////////CommunityTopNode

/**
 * common network edge
 * 
 * @author ge xin
 * 
 */
class CommunityNetEdge extends AbstractE {

	@Override
	public AbstractE clone() {
		AbstractE newEdge = new CommunityNetEdge();

		newEdge.weight = this.weight;
		return newEdge;
	}

}
