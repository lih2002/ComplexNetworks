/**
 * 
 */
package network;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.io.*;



import java.util.TreeMap;

import community.*;
import properties.*;
import spectrum.*;
import model.*;
import tools.*;
import io.*;


import javax.swing.JFileChooser;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * Analysis the network.
 * 
 * @author ge xin
 * 
 */
/**
 * @author gexin
 *
 */
/**
 * @author gexin
 *
 */
public class NetworkAnalysis {

	private Map<String, AbstractNetwork> ID2Networks;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		NetworkAnalysis nA = new NetworkAnalysis();

//		 nA.loadMonthNetworks(200002, 200002, "d://netData//InternetAS//", "",
//		 "SkitterAsLink");
//		
//		nA.batchProcessData();

		Debug.outn("end  NetworkAnalysis main");

	}// ////////////main()

	public NetworkAnalysis() {
		this.ID2Networks = new TreeMap<String, AbstractNetwork>();
		// new NetworkIDComparator()
	}

	public AbstractNetwork getNetwork(String networkID) {
		return this.ID2Networks.get(networkID);
	}

	public void addNetwork(AbstractNetwork network) {

		// Debug.outn(network.getNetworkID());
		if (ID2Networks.containsKey(network.getNetID())) {
			Debug.outn("add network" + network.getNetID());
			return;
		} else {
			ID2Networks.put(network.getNetID(), network);
		}
	}

	public void removeNetwork(String networkID) {
		this.ID2Networks.remove(networkID);
	}

	public void removeAll() {
		this.ID2Networks.clear();
	}

	/**
	 * 
	 * @return ���������а����Ľڵ�ID�ļ���
	 */
	public Set<String> getAllNodeIDs() {

		HashSet<Set<String>> nodeSet = new HashSet<Set<String>>();

		Iterator<AbstractNetwork> netIt = this.ID2Networks.values().iterator();
		while (netIt.hasNext()) {
			nodeSet.add(netIt.next().getAllVertexIDSet());
		}
		return MathTool.unionSet(nodeSet);
	}

	/**
	 * �ڶ�������ж����ڵĽڵ㣬����������нڵ�Ľ���������Ԫ��Ϊ�ڵ�ID
	 * 
	 * @return �����ڵ�ID
	 */
	public Set<String> getIntersectionNodeIDs() {

		HashSet<Set<String>> nodeSet = new HashSet<Set<String>>();

		Iterator<AbstractNetwork> netIt = this.ID2Networks.values().iterator();
		while (netIt.hasNext()) {
			nodeSet.add(netIt.next().getAllVertexIDSet());
			// Debug.outn(netIt.next().getNetworkSize());
			Debug.outn("nodeSet.size() " + nodeSet.size());
		}
		// Debug.outn(nodeSet.size());
		return MathTool.intersectionSet(nodeSet);
	}
	
    /**
     * 
     * @param networkFiles
     * @param filePath
     * @param fileName
     * @param postfix
     * @return
     */
	public Map<Integer,Integer> getAvgDegrees(String networkFiles[],String filePath,
			String fileName, String postfix){
		Map<Integer,Integer> nodeDegrees = new TreeMap<Integer, Integer>();
		
//		for (int i = 0; i < networkFiles.length; i++) {
//			
//			AbstractNetwork generalNet = new GeneralNetwork();
//			generalNet.loadNetworkData(networkFiles[i]);
//			Set<String> allNodes = generalNet.getAllNodeID();
//			Iterator <String>allNodeIt =  allNodes.iterator();
//			while(allNodeIt.hasNext()){
//				String id = allNodeIt.next();
//				int degree= generalNet.getNeighborCount( );
//				
//				if(nodeDegrees.keySet().contains(id)){
//					int degreeSum = nodeDegrees.get(id).intValue();
//					nodeDegrees.put(id, degreeSum+degree);
//				}else{
//					nodeDegrees.put(id, degree);
//				}
//			}
//		}
//		
//		Map<Integer,Integer> lifeTime = getNodeLife(networkFiles,"","","");
//	
//		
//		StringBuffer output = new StringBuffer();
//		Iterator<Integer> nodeIt = nodeDegrees.keySet().iterator();
//		while(nodeIt.hasNext()){
//			int id = nodeIt.next();
//			output.append(id+" "+( (float)nodeDegrees.get(id)/(float)lifeTime.get(id) )+"\n");
//			//Debug.outn(id+" life "+nodeLife.get(id));
//		}
//		
//		FileOperation.saveStringToFile(filePath+fileName+postfix, output.toString(), false);
		
		return nodeDegrees;
	}
	
	/**
	 * get nodes life time in all the network. life time= number of networks the node exists
	 * @param networkFiles
	 * @return map(node id, life time)
	 */
	public Map<Integer,Integer> getNodeLife(String networkFiles[],String filePath,
			String fileName, String postfix){
		
		Map<Integer,Integer> nodeLife = new TreeMap<Integer, Integer>();
//		
//		for (int i = 0; i < networkFiles.length; i++) {
//			
//			AbstractNetwork generalNet = new GeneralNetwork();
//			generalNet.loadNetworkData(networkFiles[i]);
//			Set<Integer> allNodes = generalNet.getNodeIDs();
//			Iterator <Integer>allNodeIt =  allNodes.iterator();
//			while(allNodeIt.hasNext()){
//				int id = allNodeIt.next();
//				
//				if(nodeLife.keySet().contains(id)){
//					int life = nodeLife.get(id).intValue();
//					nodeLife.put(id, life+1);
//				}else{
//					nodeLife.put(id, 1);
//				}
//			}
//		}
//		
//		if(filePath.equals("")){
//			return nodeLife;
//		}
//		StringBuffer output = new StringBuffer();
//		Iterator<Integer> nodeIt = nodeLife.keySet().iterator();
//		while(nodeIt.hasNext()){
//			int id = nodeIt.next();
//			output.append(id+" "+nodeLife.get(id)+"\n");
//			//Debug.outn(id+" life "+nodeLife.get(id));
//		}
//		
//		FileOperation.saveStringToFile(filePath+fileName+postfix, output.toString(), false);
		
		return nodeLife;
	}///////////////////////////////////

	/**
	 * get intersection node id in all the networks
	 * 
	 * @param networkFiles
	 * @return set of common node id
	 */
	public Set<String> getIntersectionNodeIDs(String networkFiles[]) {

		Set<String> nodeIDs = new HashSet<String>();
		/* get node ids of the first network */
		GeneralNetwork generalNet = new GeneralNetwork();
		generalNet.loadNetworkData(networkFiles[0]);
		nodeIDs = generalNet.getAllVertexIDSet();

		for (int i = 1; i < networkFiles.length; i++) {
			generalNet = new GeneralNetwork();
			generalNet.loadNetworkData(networkFiles[i]);
			nodeIDs = MathTool
					.intersectionSet(nodeIDs, generalNet.getAllVertexIDSet());
			// Debug.outn(commNet.getNetworkSize());
		}
		// Debug.outn(this.getNetworkIDSet().size());

		return nodeIDs;

	}

	/**
	 * degrees of nodes that exist in all the networks
	 * @param networkFiles
	 * @param filePath
	 * @param fileName
	 * @param postfix
	 */
	public void getNodeDegreesInNetworks(String networkFiles[],
			String filePath, String fileName, String postfix) {

		// get common node ids of networks
		Set<String> intersectionNodes = getIntersectionNodeIDs(networkFiles);
		
		Debug.outn(intersectionNodes.size());

		String[] ids = new String[intersectionNodes.size()];
		Iterator<String> idIt = intersectionNodes.iterator();
		int index = 0;
		while (idIt.hasNext()) {
			ids[index++] = idIt.next();
		}
		Arrays.sort(ids);
		StringBuffer title = new StringBuffer();
		for (int k = 0; k < ids.length; k++) {
			//title.append(ids[k] + " ");
		}
		//title.append("\n");
	
//		//Debug.outn(title.length());
//		StringBuffer degrees= new StringBuffer();
//		int [][]degreeArray =new int[ids.length][networkFiles.length]; 
//		// each network	
//		for (int k = 0; k < networkFiles.length; k++) {
//			GeneralNetwork generalNet = new GeneralNetwork();
//			generalNet.loadNetworkData(networkFiles[k]);		
//		
//			// each node of one network
//			for (int i = 0; i < ids.length; i++) {	
//				generalNet.getNeighborCount(vertex);
//				
//				degreeArray[i][k] = generalNet.getNodeDegreeById(ids[i]) ;
//				//String d=""+generalNet.getNodeDegreeById(ids[i]);
//				//FileOperation.saveStringToFile(filePath+fileName+postfix, d+" ",true);
//				//degrees.append(generalNet.getNodeDegreeById(ids[i])+" ");
//				//FileOperation.saveStringToFile(filePath+fileName+postfix, "\n",true);
//					
//			}
//			//degrees.append("\n");		
//		}
//		
//		StringBuffer degreeCol = new StringBuffer();
//		
//		for(int j=0;j<ids.length;j++){
//			for(int i=0;i<networkFiles.length;i++){
//				degrees.append(degreeArray[j][i]+" ");
//			}
//			degrees.append("\n");
//		}
//		FileOperation.saveStringToFile(filePath+fileName+postfix, degrees.toString(),true);

	}

	/**
	 * get network that contains the intersection nodes with links of input networks 
	 * 
	 * @param networkFiles
	 * @return
	 */
	public void getIntersectionNet(String networkFiles[], String filePath,
			String fileName, String postfix) {

		// get common node ids of networks
		Set<String> intersectionNodes = getIntersectionNodeIDs(networkFiles);
		Debug.outn("intersectionNodes.size() " + intersectionNodes.size());

		// remove node not in the intersection nodes set
		Vector<String> removedNodes = new Vector<String>();

		for (int k = 0; k < networkFiles.length; k++) {

			removedNodes = new Vector<String>();
			GeneralNetwork generalNet = new GeneralNetwork();
			generalNet.loadNetworkData(networkFiles[k]);
			Vector<AbstractE> allEdges = generalNet.getAllEdges();

		
			/* get node not in the intersection nodes set */
			Iterator<AbstractV> jungNetNodeIt = generalNet.getVertices().iterator();
			
			while (jungNetNodeIt.hasNext()) {
				String node = jungNetNodeIt.next().getID();
				if (!intersectionNodes.contains(node)) {
					removedNodes.add(node);
				}
			}

			Debug.outn(" removedNodes.size() " + removedNodes.size());
			/* remove node not in the intersection nodes set */
			for (int j = 0; j < removedNodes.size(); j++) {
				//generalNet.removeVertex(removedNodes.get(j));
			}

			fileName = (new File(networkFiles[k])).getName().split("\\.")[0];

			generalNet.saveNetToFile(filePath + fileName
					+ postfix);

			removedNodes.clear();

		}// //////////////////////each node

	}// ///////////////////////////////////////////

	/**
	 * ��������ڵ�Ĳ
	 * 
	 * @return ��ڵ�ID����
	 */
	public Set<String> getDiffNodeIDs() {
		HashSet<Set<String>> nodeSet = new HashSet<Set<String>>();

		Iterator<AbstractNetwork> netIt = this.ID2Networks.values().iterator();
		while (netIt.hasNext()) {
			nodeSet.add(netIt.next().getAllVertexIDSet());
		}
		return MathTool.diffSet(nodeSet);
	}

	/**
	 * ��������������ڵ㣬�����������д��ڣ��������в���Ľڵ�
	 * 
	 * @param oldNet
	 *            ������
	 * @param newNet
	 *            ������
	 * @return �����ڵ�ID����
	 */
	public static Set<String> birthNodes(AbstractNetwork oldNet,
			AbstractNetwork newNet) {
		Set<String> birthNodes;
		Set<Set<String>> netSet = new HashSet<Set<String>>();
		netSet.add(oldNet.getAllVertexIDSet());
		netSet.add(newNet.getAllVertexIDSet());

		// ��������ڵ�Ľ���
		Set<String> intersectionSet = MathTool.intersectionSet(netSet);

		netSet.clear();

		netSet.add(newNet.getAllVertexIDSet());
		netSet.add(intersectionSet);
		// ������-���� = �����ڵ�
		birthNodes = MathTool.diffSet(netSet);
		return birthNodes;
	}

	/**
	 * ���������е������ڵ㣬���ھ������д��ڣ����������в����ڵĽڵ�
	 * 
	 * @param oldNet
	 *            ������
	 * @param newNet
	 *            ������
	 * @return �����ڵ�ID����
	 */
	public static Set<String> deathNodes(AbstractNetwork oldNet,
			AbstractNetwork newNet) {
		// Set<Integer> deathNodes;
		// Set <Set<Integer>> netSet = new HashSet <Set<Integer>> ();
		// netSet.add(oldNet.getNodeIDs());
		// netSet.add(newNet.getNodeIDs());
		//		
		// //��������ڵ�Ľ���
		// Set <Integer> intersectionSet = MathTool.intersectionSet(netSet);
		//		
		// netSet.clear();
		//		
		// netSet.add(oldNet.getNodeIDs());
		// netSet.add(intersectionSet);
		// //������-���� = �����ڵ�
		// deathNodes = MathTool.diffSet(netSet);

		return birthNodes(newNet, oldNet);
	}

	/**
	 * ĳ���ڵ㱻̽�⵽�����������ID�����ذ����������ڵ����飬�����ԱΪĳ���ڵ����������ID
	 * </p>
	 * ���磺�ڵ���200001�����д��ڣ���200001����Ϊ��������磬�������һ����ԱΪ"200001"
	 * </p>
	 * 
	 * @param nodeID
	 * @return
	 */
	public int[] nodeDetectedInNetwork(String nodeID) {

		Set<String> tempTime = new HashSet<String>();

		Iterator<AbstractNetwork> networkIt = this.ID2Networks.values()
				.iterator();
		while (networkIt.hasNext()) {
			AbstractNetwork net = networkIt.next();
			if (net.containVertex(nodeID)) {
				tempTime.add(net.getNetID());
			}
		}
		if (tempTime.size() == 0) {
			return null;
		} else {
			int[] life = new int[tempTime.size()];
			Iterator<String> it = tempTime.iterator();
			int step = 0;
			while (it.hasNext()) {
				String networkID = (String) it.next();
				life[step] = Integer.parseInt(networkID);
				step++;
			}
			// ��ʱ������
			Arrays.sort(life);
			return life;
		}

	}

	/**
	 * ĳ���ڵ������������е�ƽ���ȣ�����ĳ�������иýڵ㲻���ڣ�������
	 * 
	 * @param nodeId
	 * @return
	 */
	public float getAvgDegreeInAllNetworks(int nodeId) {
		int[] degrees = getNodeDegreesInAllNetworks(nodeId);
		// �����������е�ƽ����
		float tempSum = 0;
		int lifeTime = 0;
		for (int i = 0; i < degrees.length; i++) {
			if (degrees[i] != -1) {
				tempSum += degrees[i];
				lifeTime++;
			}
		}
		float avgDegree = tempSum / lifeTime;
		return avgDegree;
	}

	/**
	 * ����ĳ���ڵ����������������еĶȣ�����ĳ�������в����ڸýڵ㣬��ֵΪ-1.
	 * 
	 * @param nodeID
	 * @return
	 */
	public int[] getNodeDegreesInAllNetworks(int nodeID) {

		int[] degrees = new int[ID2Networks.size()];

		Set<String> allNetworkIDs = ID2Networks.keySet();
		int index = 0;
		Iterator<String> IDIt = allNetworkIDs.iterator();
		while (IDIt.hasNext()) {
//			degrees[index++] = ID2Networks.get(IDIt.next()).getNodeDegreeById(
//					nodeID);
		}
		return degrees;

	}

	/**
	 * ĳЩ�ڵ㼯�ϵĶ�ֵ
	 * 
	 * @param network
	 *            �ڵ���������
	 * @param nodeIDSet
	 *            �ڵ�ID����
	 * @return �ڵ��ֵ���飬���ڵ��������в����ڣ�����-1��ֵ
	 */
	public static int[] getDegreesOf(AbstractNetwork network, Set<String> nodeIDSet) {
		int[] degrees = new int[nodeIDSet.size()];
		Iterator<String> ids = nodeIDSet.iterator();
		String nodeID;
		int index = 0;
		while (ids.hasNext()) {
			nodeID = ids.next();
			int nodeDegree;
			if(!network.containVertex(nodeID)){
				nodeDegree=-1;
			}else{
				nodeDegree = network.getNeighborCount(network.getVertex(nodeID));
			}
			// Debug.outn("getAvgDegree() "+"nodeID: "+nodeID+"
			// nodeDegree"+nodeDegree);
			degrees[index] = nodeDegree;
			index++;
		}
		Arrays.sort(degrees);
		return degrees;
	}

	/**
	 * �ڵ������ʱ�䳤�ȣ����ӵ�һ�γ��ֵ����һ�γ��ֵ�ʱ����
	 * 
	 * @param nodesSet
	 * @return �ڵ�����ʱ�䳤������
	 */
	public int[] getNodeLifeLength(Set<Integer> nodesSet) {
		Iterator<Integer> nodesIt = nodesSet.iterator();
		int[] nodeLifeLength = new int[nodesSet.size()];
//		int nodeIndex = 0;
//		while (nodesIt.hasNext()) {
//			int nodeID = nodesIt.next();
//			int[] detectedInNetwork = nodeDetectedInNetwork(nodeID);
//
//			nodeLifeLength[nodeIndex] = MathTool.numOfTwoDates(
//					detectedInNetwork[0],
//					detectedInNetwork[detectedInNetwork.length - 1]);
//			nodeIndex++;
//		}
		return nodeLifeLength;
	}
	


	/**
	 * �ڵ���ظ����ִ��������������γ��ֵ����粻���ڣ������ظ�����һ�Ρ�
	 * 
	 * @param nodesSet
	 * @return
	 */
	public int[] getNodeReappearTimes(Set<String> nodesSet) {
		
		Iterator<String> nodesIt = nodesSet.iterator();
		// �ظ����ִ���
		int [] detectedTimes = new int[nodesSet.size()];
		int nodeIndex = 0;
		while (nodesIt.hasNext()) {
			String nodeID = nodesIt.next();
			int reAppearCount = 1;
			int[] detectedInNetwork = nodeDetectedInNetwork(nodeID);

			for (int netIndex = 0; netIndex < detectedInNetwork.length; netIndex++) {

				if (netIndex != 0) {
					int space = MathTool.numOfTwoDates(
							detectedInNetwork[netIndex - 1],
							detectedInNetwork[netIndex]);
					// Debug.out(space+" ");
					// ʱ��������2,���ظ�����
					if (space > 2) {
						reAppearCount++;
					}
				}
			}
			detectedTimes[nodeIndex] = reAppearCount;
			nodeIndex++;
		}

		return detectedTimes;
	}

	/**
	 * �ڵ㱻��⵽�Ĵ���
	 * 
	 * @param nodesSet
	 * @return
	 */
	public int[] getNodeDetectedTimes(Set<String> nodesSet) {
		Iterator<String> nodesIt = nodesSet.iterator();

		int[] detectedTimes = new int[nodesSet.size()];
		int nodeIndex = 0;
		while (nodesIt.hasNext()) {
			String nodeID = nodesIt.next();
			detectedTimes[nodeIndex] = nodeDetectedInNetwork(nodeID).length;
			nodeIndex++;
		}

		return detectedTimes;
	}
	

	public void sequentialD() {
//		Set<Integer> nodesSet = this.getDiffNodeIDs();
//		int[] detectedTimes = this.getNodeDetectedTimes(nodesSet);
//		int[] reappear = this.getNodeReappearTimes(nodesSet);
//		int[] lifeTime = this.getNodeLifeLength(nodesSet);
//
//		float[] nodeDegree = new float[nodesSet.size()];
//		Iterator<Integer> nodesSetIt = nodesSet.iterator();
//		int index = 0;
//		while (nodesSetIt.hasNext()) {
//			int nodeID = nodesSetIt.next();
//			Debug.outn(nodeID);
//			nodeDegree[index] = this.getAvgDegreeInAllNetworks(nodeID);
//			index++;
//		}
//		for (int i = 0; i < nodesSet.size(); i++) {
//			Debug.outn(nodeDegree[i] + "  " + detectedTimes[i] + "  "
//					+ lifeTime[i] + "  " + reappear[i] + "  "
//					+ (float) lifeTime[i] * detectedTimes[i] * reappear[i] / 12
//					/ 12 / 6);
//		}

	}
/*
	public void sequentialDegree() {

		Set<String> nodesSet = getDiffNodeIDs();

		// �ڵ�����ʱ�䣬�� Ϊ���������id���� Ϊ�ڵ�����
		int[][] nodeExistTime = new int[nodesSet.size()][this.ID2Networks
				.keySet().size()];

		int[][] nodeDegree = new int[nodesSet.size()][this.ID2Networks.keySet()
				.size()];

		// �ڵ��������ʱ�䳤�� ����������ĸ���
		int[] nodeExistCount = new int[nodesSet.size()];

		// �ڵ��������ʱ�䳤�ȣ�
		int[] nodeLifeLength = new int[nodesSet.size()];

		// �ظ����ִ���
		int[] reAppearTimes = new int[nodesSet.size()];

		// ����ʱ���
		// float[] lifeTimeRation = new float[nodesSet.size()];

		int nodeIndex = 0;
		Iterator<Integer> allNodeIt = nodesSet.iterator();
		// ÿ���ڵ�
		while (allNodeIt.hasNext()) {
			int nodeId = allNodeIt.next();
			// �ڵ����������
			int[] detectedInNetwork = nodeDetectedInNetwork(nodeId);
			nodeExistCount[nodeIndex] = detectedInNetwork.length;
			int reAppearCount = 1;
			// ÿ���ڵ���ڵ�����
			for (int netIndex = 0; netIndex < detectedInNetwork.length; netIndex++) {
				// Debug.outn(nodeIndex+" "+length);
				nodeExistTime[nodeIndex][netIndex] = detectedInNetwork[netIndex];
				if (netIndex != 0) {
					int space = MathTool.numOfTwoDates(
							detectedInNetwork[netIndex - 1],
							detectedInNetwork[netIndex]);
					// Debug.out(space+" ");
					// ʱ��������2,���ظ�����
					if (space > 2) {
						reAppearCount++;
					}
				}
				// �ڵ��
				AbstractNetwork net = ID2Networks
						.get(detectedInNetwork[netIndex]);
				nodeDegree[nodeIndex][netIndex] = net.getNodeDegreeById(nodeId);

			}
			nodeLifeLength[nodeIndex] = MathTool.numOfTwoDates(
					detectedInNetwork[0],
					detectedInNetwork[detectedInNetwork.length - 1]);
			reAppearTimes[nodeIndex] = reAppearCount;

			nodeIndex++;
		}

		// output for debug
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < nodeExistTime[i].length; j++) {
				Debug.out(nodeExistTime[i][j] + " ");
				// Debug.outn(" ");
				// Debug.out(nodeDegree[i][j]);
			}
			Debug.outn("");
			for (int j = 0; j < nodeExistTime[i].length; j++) {
				Debug.out(nodeDegree[i][j] + " ");
			}
			Debug.outn("");
			Debug.outn("����ʱ�� " + nodeExistCount[i] + " ����ʱ�� "
					+ nodeLifeLength[i] + " �ظ����ִ��� " + reAppearTimes[i]);

		}

		allNodeIt = nodesSet.iterator();
		int index = 0;
		// �������ķֲ�
		int[] existCountPDF = new int[ID2Networks.size() + 1];
		int[] lifeLengthPDF = new int[ID2Networks.size() + 1];
		int[] reAppearTimesPDF = new int[ID2Networks.size() + 1];

		float lifeCoefficient[] = new float[nodesSet.size()];

		String fileName = "f://lifeTime.txt";
		while (allNodeIt.hasNext()) {
			int nodeId = allNodeIt.next().intValue();

			// FileOperation.saveStringToFile(fileName, "�ڵ���� "+index+"
			// �ڵ�Id
			// "+nodeId+" ����ʱ�� "+nodeExistCount[index]+" ����ʱ��
			// "+nodeLifeLength[index]+" �ظ����ִ���
			// "+reAppearTimes[index]+"\n");
			existCountPDF[nodeExistCount[index]]++;
			lifeLengthPDF[nodeLifeLength[index]]++;
			reAppearTimesPDF[reAppearTimes[index]]++;
			int reApp = 0;
			if (reAppearTimes[index] == 1) {
				reApp = 1;
			} else {
				reApp = reAppearTimes[index];
			}
			float reAppRate = (reApp) / (12 - (float) nodeExistCount[index]);
			if (reAppRate > 1.0) {
				reAppRate = 1;
			}

			// lifeCoefficient[index] = (float) nodeExistCount[index]*
			// (float)nodeLifeLength[index] /12/12
			// *reAppRate ;

			lifeCoefficient[index] = ((float) nodeExistCount[index] / 12
					+ (float) nodeLifeLength[index] / 12 + reAppRate) / 3;

			index++;
		}

		// for(int i=1;i<=this.ID2Communities.size();i++){
		// Debug.outn( "����ʱ�� "+i+" ���� "+existCountPDF[i]);
		// }
		// for(int i=1;i<=this.ID2Communities.size();i++){
		// Debug.outn( "����ʱ�� "+i+" ���� "+lifeLengthPDF[i]);
		// }
		// for(int i=1;i<=this.ID2Communities.size();i++){
		// Debug.outn( "���ִ��� "+i+" ���� "+reAppearTimesPDF[i]);
		// }

		// lifeCoefficient �ֲ� 0.1Ϊ����
		int[] lifeCoefficientPDF = new int[10];
		fileName = "f://lifeTimeCoefficient.txt";
		for (int i = 0; i < nodesSet.size(); i++) {

			String toFile = lifeCoefficient[i] + " " + nodeExistCount[i] + " "
					+ nodeLifeLength[i] + " " + reAppearTimes[i] + "\n";
			FileOperation.saveStringToFile(fileName, toFile, false);
			// Debug.outn( lifeCoefficient[i] + " "+nodeExistCount[i]+"
			// "+nodeLifeLength[i] +" "+reAppearTimes[i]);
			lifeCoefficientPDF[(int) (lifeCoefficient[i] * 10)]++;
		}

		for (int i = 0; i < 10; i++) {
			Debug.outn(lifeCoefficientPDF[i]);
		}

	}// /////////// sequentialDegree()
	*/

	public void loadYearNetworks(int year, String networkPath, String prefix,
			String postfix) {

		int startDate = year * 100 + 1;
		int endDate = year * 100 + 12;
	//	loadMonthNetworks(startDate, endDate, networkPath, prefix, postfix);

	}

	
	/**
	 * ���·�Ϊ��λ������������
	 * 
	 * @param startDate
	 * @param endDate
	 * @param networkPath
	 * @param prefix
	 * @param networkPostfix
	 */
	/*
	public void loadMonthNetworks(int startDate, int endDate,
			String networkPath, String prefix, String networkPostfix) {

		String[] networkFile;

		int startYear = startDate / 100;
		// Debug.outn(startYear);
		int firstMonth = startDate - startYear * 100;

		int endYear = endDate / 100;
		// Debug.outn(endYear);
		int secondMonth = endDate - endYear * 100;

		int num = 12 * (endYear - startYear) + secondMonth - firstMonth + 1;

		networkFile = new String[num];
		// Debug.outn(num);

		String networkFileName;

		for (int j = 0; j < num; j++) {

			int month = (firstMonth + j) % 12;
			if (month == 0)
				month = 12;

			int year = startYear + (firstMonth + j - 1) / 12;

			networkFileName = networkPath
					+ Integer.toString(year * 100 + month) + networkPostfix;

			networkFile[j] = networkFileName;

			// Debug.outn(networkFileName);

			if (!networkPath.equals("")) {
				String[] networkName = new String[1];
				networkName[0] = networkFileName;
				AbstractNetwork network = ASTopNetworkBuilder
						.loadNetworkFromFile(networkName,
								INetworkStructure.NTYPE_NODE_WITH_ID, null);
				// Debug.outn(networkName[0]);
				if (network != null) {
					this.addNetwork(network);
					Debug.outn("load network " + network.getNetworkID());
				}
			}
		}
	}// // loadMonthNetworksAndCommunities
*/	
	/*
	public void loadSequenceNetworks(String path, int start, int end,
			int interval, String prefix, String postfix) {

		for (int seq = start; seq <= end; seq++) {
			AbstractNetwork network = new ASTopNetwork();
			NetworkDataLoader.loadNetworkFromFile(network, path + prefix + seq
					+ postfix);

			if (network != null) {
				this.addNetwork(network);
			}
		}
	}
	*/

	// pubic Set<String> sectionNodeID(){
	//		
	// }

	public Set<String> getNetworkIDSet() {
		return this.ID2Networks.keySet();
	}

	/*
	public void loadSelectedNetworks() {
		try {
			LoadNetworkData loadDialog = new LoadNetworkData();
			loadDialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			loadDialog.setVisible(true);
			File[] dataFiles = loadDialog.getSelectedFiles();

			for (int i = 0; i < dataFiles.length; i++) {
				GeneralNetwork commNet = new GeneralNetwork();
				NetworkDataLoader.loadNetworkFromFile(commNet, dataFiles[i]
						.toString());
				this.addNetwork(commNet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

	/*
	public void batchProcessData() {
		try {
			LoadNetworkData loadDialog = new LoadNetworkData();
			loadDialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			loadDialog.setVisible(true);
			File[] dataFiles = loadDialog.getSelectedFiles();

			for (int i = 0; i < dataFiles.length; i++) {
				GeneralNetwork commNet = new GeneralNetwork();
				NetworkDataLoader.loadNetworkFromFile(commNet, dataFiles[i]
						.toString());

				new Reconstruct().AddNodesWithRandomEdge(commNet, 500, 10);
				// RawData.formatToPajekNet(commNet,
				// "d://netData//pajekNetwork//", commNet.getNetworkID(),
				// ".net");
				// this.saveDegrees(commNet,
				// "d://netData//"+commNet.getNetworkID(), ".degree");

				// this.addNetwork(commNet);
				// new NetMatrix().saveNetworkAdjacentMatrixToFile(commNet,
				// "d://netData//networkDataAdjacentMatrix//", "",
				// ".adjacentMat");
				// new NetMatrix().saveNetworkLaplaceMatrixToFile(commNet,
				// "d://netData//networkDataLaplacMatrix//", " ",".laplacMat");
				// Debug.outn(this.getFeatures());
				Debug.outn(toString());
				// this.network2Community(commNet, "d://netData//community//",
				// "", ".community");
				// Debug.outn(this.getFeatures());
				// this.saveNetPropertiesToFile(commNet , "d://netData//",
				// commNet.getNetworkID(), ".prop");

				this.removeAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

	@Override
	public String toString() {
		String retS = "";// "�������\n";
		Iterator<String> idIt = this.ID2Networks.keySet().iterator();
		while (idIt.hasNext()) {
			String id = idIt.next();
			retS = retS + "NetworkID " + id + " NodeNum "
					+ ID2Networks.get(id).getVertexCount() + " EdgeNum "
					+ ID2Networks.get(id).getEdgeCount() + "\n";
		}
		return retS;
	}

	
	public String getFeatures() {
		String retS = "";
		Iterator<String> idIt = this.ID2Networks.keySet().iterator();
		while (idIt.hasNext()) {

			String netID = idIt.next();
			AbstractNetwork net = this.getNetwork(netID);
			retS = retS + "NetworkID " + netID + " NodeNum "
					+ ID2Networks.get(netID).getVertexCount() + " EdgeNum "
					+ ID2Networks.get(netID).getEdgeCount() + " averageDegree "
					+ MathTool.average(net.getAllVertexDegrees()) + " ClusterCoefficient "
					+ ClusterCoefficient.getNetworkClusterCoefficient(net)
					+ "\n";
		}
		return retS;
	}

	public void saveNetPropertiesToFile(AbstractNetwork net, String filePath,
			String fileName, String postfix) {
		String properties = "";
		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (postfix == "") {
			postfix = ".prop";
		}

		properties += " ID " + net.getNetID() + "\n";
		properties += " N " + net.getVertexCount() + "\n";
		properties += " L " + net.getEdgeCount() + "\n";
		properties += " E[D] " + MathTool.average(net.getAllVertexDegrees()) + "\n";
		properties += " C(G) "
				+ ClusterCoefficient.getNetworkClusterCoefficient(net) + "\n";

	    boolean connected = Partitions.isConnected(net);
		//boolean connected = true;
		properties += " connected " + connected + "\n";
		if (!connected) {
		//	Set<Integer>[] partions = Partitions.partitions(net);
		//	properties += " partionNumber " + partions.length + "\n";
		}
		;

		// String communityPathName =
		// "d:\\netData\\reconstrutNet\\community\\"+net.getNetworkID()+".community";
		// AbstractCommunity community =
		// ASTopCommunityBuilder.loadCommunityFromFile(communityPathName);
		// properties+= " modularity "+community.getModuleDegree()+"\n";
		// properties+= " communitySize "+community.getCommunitySize()+"\n";

		// int [] subCommSize = community.getSubCommSize();
		// Arrays.sort(subCommSize);
		// String subCSize= "";
		// for(int i = 0 ;i<subCommSize.length;i++){
		// subCSize = subCSize + subCommSize[i]+" ";
		// }
		// properties+= " subCommunitySize "+ subCSize + "\n";

		FileOperation.saveStringToFile(filePath + fileName + postfix,
				properties, false);
	}

	/**
	 * ͳ���Ѽ�������Ľڵ㼯��������
	 * </p>
	 * ���нڵ�����
	 * </p>
	 * �����ڵ�����
	 * </p>
	 * ��ڵ�����
	 * 
	 * @return
	 */
	public String nodesSetInfo() {

		String info = "���нڵ����� " + this.getAllNodeIDs().size()
				+ " �����ڵ����� " + this.getIntersectionNodeIDs().size()
				+ " ��ڵ����� " + this.getDiffNodeIDs().size() + "\n";
		// Debug.outn(info);
		return info;

	}

	/**
	 * ����С��ֵ�ڵ���ھӽڵ�Ķȷֲ������������
	 * 
	 */
	/*
	public int[] lowDegreeNgbDegree(AbstractNetwork net, int degree) {
		
		Iterator it = net.getNodeIDs().iterator();
		Set<Integer> ngbNodeIds = new HashSet();

		// ������нڵ��ھӽڵ�ļ��ϣ��������ظ��ڵ�
		while (it.hasNext()) {
			int nodeId = (Integer) it.next();
			if (net.getNodeDegreeById(nodeId) == degree) {
				ngbNodeIds.addAll(net.getNeighborsSetById(nodeId));
			}
		}

		Iterator ngbNodeIt = ngbNodeIds.iterator();
		int[] ngbDegrees = new int[ngbNodeIds.size()];
		int index = 0;
		while (ngbNodeIt.hasNext()) {
			int nodeId = (Integer) ngbNodeIt.next();
			ngbDegrees[index++] = net.getNodeDegreeById(nodeId);
		}

		return ngbDegrees;

	}
*/
	/**
	 * save network degrees to file
	 * 
	 * @param net
	 */
	public void saveDegrees(AbstractNetwork net, String filePath,
			String fileName, String postfix) {

		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (postfix == "") {
			postfix = ".degree";
		}
		int[] degrees = net.getAllVertexDegrees();
		String filePathName = filePath + fileName + postfix;

		String degreesString = "";
		for (int i = 0; i < degrees.length; i++) {
			degreesString = degreesString + Integer.toString(degrees[i]) + "\n";
		}
		FileOperation.saveStringToFile(filePathName, degreesString, false);
	}

	/*
	public double lowDegreeNgbCoefficient(AbstractNetwork net, int degree) {
		
		Iterator <AbstractNode> allNode = net.getVertices()).iterator();
		Set<String> ngbNodeIds = new HashSet<String>();

		float degreesSum = 0;
		float ngbDegreesSum = 0;

		// ������нڵ��ھӽڵ�ļ��ϣ��������ظ��ڵ�
		while (allNode.hasNext()) {
			AbstractNode node = allNode.next();
			
			int d=net.getNeighborCount(node);

			// ���нڵ�Ķ�ֵ֮��
			degreesSum += d;

			// ���ڵ��ֵ��������Ķ�ֵ����øýڵ���ھӽڵ�
			if (d == degree) {
				ngbNodeIds.addAll(net.getneig);
			}
		}

		Iterator ngbNodeIt = ngbNodeIds.iterator();
		int[] ngbDegrees = new int[ngbNodeIds.size()];
		int index = 0;
		while (ngbNodeIt.hasNext()) {
			int nodeId = (Integer) ngbNodeIt.next();
			ngbDegreesSum += net.getNodeDegreeById(nodeId);
		}

		float result = (ngbDegreesSum / ngbNodeIds.size())
				/ (degreesSum / net.getNetworkSize());

		return result;
	}
*/
	/**
	 * ������������Ż��ֲ����浽�ļ����ļ�����
	 * ǰ׺+����ID������ʱ�����£�����200801��+��׺ ��ɡ�
	 * 
	 * @param net
	 *            ��������
	 * @param outputPath
	 *            �����ļ�·��
	 * @param prefix
	 *            �ļ���ǰ׺
	 * @param postfix
	 *            �ļ�����׺
	 */
	public AbstractCommunity network2Community(AbstractNetwork net, String outputPath,
			String fileName, String postfix) {
		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (postfix == "") {
			postfix = ".community";
		}
		AbstractCommunity comm;
		// Debug.outn("net id"+net.getNetworkID());
		comm = CommunityTools.CNMdecomposition(net);

		Debug.outn(net.getNetID() + "\t"+ net.getVertexCount() + "\t" + net.getEdgeCount()
				+ "\t" + (float) comm.getModularity()
				+ "\t" + comm.getCommunitySize());

		// comm.setModuleDegree(CommunityTools.NewmanQ(comm, net));
		if(!outputPath.equals("")){
			comm.saveCommunityToFile(outputPath + fileName + postfix, true);
		}
		
		return comm;
	}

//	public void toCommunity(String outputPath, String commPrefix,
//			String commPostfix) {
//		Iterator<String> networkIt = this.ID2Networks.keySet().iterator();
//		while (networkIt.hasNext()) {
//			AbstractNetwork net = this.ID2Networks.get(networkIt.next());
//			this.network2Community(net, outputPath, commPrefix, commPostfix);
//		}
//	}

	/**
	 * ͳ���Ѽ��������һЩ��������
	 * </p>
	 * �����ʽΪ�� ����ID ����1���� ����1ֵ ����2���� ����2ֵ ������
	 * 
	 * @param net
	 * @param split
	 *            ���Լ�ķָ�����Ĭ����һ���ո�
	 * @return
	 */
	public String networkProp(AbstractNetwork net, String split) {

		if (split.equals("")) {
			split = " ";
		}

		String props = "";
		StringBuffer prop = new StringBuffer();
		prop.append("netID" + split + net.getNetID() + split); // ����ID
		prop.append("size" + split + net.getVertexCount() + split); // �����С�����ڵ�����
		prop.append("edgesNumber" + split + net.getEdgeCount() + split);// ������
		prop.append("averageDegree" + split + MathTool.average(net.getAllVertexDegrees()) + split);// ƽ����
	//	prop.append("maxDegree" + split +  + split);// ����ֵ

		// prop.append("\n");
		return prop.toString();
	}
	

	public static void convertAdjacentMatrix(String networkFiles[],
			String titlePostfix, String savePath, String fileName,
			String postfix) {

	
		for (int i = 0; i < networkFiles.length; i++) {
			GeneralNetwork commNet = new GeneralNetwork();
			commNet.loadNetworkData( networkFiles[i]);
			// this.addNetwork(commNet);
			NetMatrix.saveNetworkAdjacentMatrixToFile(commNet, savePath, fileName,
					postfix);
			// new NetMatrix().saveNetworkLaplaceMatrixToFile(commNet,
			// "d://netData//networkDataLaplacMatrix//", ".laplacMat");
			// Debug.outn(this.getFeatures());
			// Debug.outn(toString());
			// this.network2Community(commNet, "d://netData//community//", "",
			// ".community");
			// Debug.outn(this.getFeatures());
			// this.saveNetPropertiesToFile(commNet , "d://netData//",
			// commNet.getNetworkID(), ".prop");
		}

	}

	
	public void getValueToOneFile(String[] files, String titlePostfix,
			String filePath, String fileName) {

		Vector<Vector<String>> data = new Vector<Vector<String>>();

		for (int i = 0; i < files.length; i++) {
			data.add(FileOperation.getValueFromFile(files[i], ""));
		}
		// get the max number of values
		int maxNum = 0;
		for (int i = 0; i < data.size(); i++) {
			if (maxNum <= data.get(i).size()) {
				maxNum = data.get(i).size();
			}
		}

		StringBuffer output = new StringBuffer();

		// column title
		for (int i = 0; i < files.length; i++) {
			// String [] netName=new File(files[i]).getName().split("\\.");

			// output.append(netName[0]+titlePostfix+",");
		}
		// output.append("\n");

		for (int index = 0; index < maxNum; index++) {
			for (int i = 0; i < files.length; i++) {
				Vector<String> oneData = data.get(i);
				if (oneData.size() - 1 < index) {
					output.append("-1");
				} else {
					output.append(oneData.get(index));
				}
				output.append(",");
			}
			output.append("\n");
		}

		// Debug.outn(output.toString());

		FileOperation.saveStringToFile(filePath + fileName, output.toString(),
				false);

	}

	public static void getLaplacMatrix(String networkFiles[], String savePath,
			String fileName, String postfix) {

		NetMatrix netMat = new NetMatrix();

		for (int i = 0; i < networkFiles.length; i++) {
			GeneralNetwork commNet = new GeneralNetwork();
			commNet.loadNetworkData( networkFiles[i]);
			// this.addNetwork(commNet);
			netMat.saveNetworkLaplacianMatrixToFile(commNet, savePath,
					fileName, postfix);
			// Debug.outn(this.getFeatures());
			// Debug.outn(toString());
			// this.network2Community(commNet, "d://netData//community//", "",
			// ".community");
			// Debug.outn(this.getFeatures());
			// this.saveNetPropertiesToFile(commNet , "d://netData//",
			// commNet.getNetworkID(), ".prop");
		}

	}

	public static void getNormalizedLaplacMatrix(String networkFiles[],
			String savePath, String fileName, String postfix) {

		NetMatrix netMat = new NetMatrix();

		for (int i = 0; i < networkFiles.length; i++) {
			GeneralNetwork commNet = new GeneralNetwork();
			commNet.loadNetworkData( networkFiles[i]);

			// this.addNetwork(commNet);
			netMat.saveNormalizedLaplacianMatrixToFile(commNet, savePath,
					fileName, postfix);

		}

	}

}// /////////////////////////////////////////////////////////////////////////////