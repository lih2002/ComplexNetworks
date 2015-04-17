/**
 * 
 */
package community;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;
import network.*;
import properties.*;
import spectrum.NetMatrix;
import tools.*;

import java.util.TreeSet;
import java.util.Map;
import java.util.TreeMap;

import edu.uci.ics.jung.algorithms.cluster.EdgeBetweennessClusterer;
import edu.uci.ics.jung.algorithms.cluster.VoltageClusterer;
import edu.uci.ics.jung.algorithms.cluster.BicomponentClusterer;
import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.*;

/**
 * ���Ŵ����ߣ��ṩ���ŷֽ��㷨
 * 
 * @author ge xin
 * 
 */
public class CommunityTools {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] networkName = new String[1];
		networkName[0] = "e:\\Gexin\\aslink\\formatArkASLink\\200807ArkAsLink";
		// networkName[0] = "e:\\Gexin\\aslink\\201011.txt";
		// networkName[0] = "f:\\aslink\\test.txt";
		//AbstractNetwork net = ASTopNetworkBuilder.loadNetworkFromFile(
		//		networkName, INetworkStructure.NTYPE_NODE_WITH_ID, null);
		// Debug.out(network.getNetworkSize());
		// Debug.outn(network.getNodeIdArray(), "node id");
		// net.dump();
		String outputPath = "e:\\Gexin\\aslink\\";
		//new CommunityTools().kCliquePeroclation(net, 19, 19, outputPath, "",
		//		"arkASClique");
		// int []nodeId = net.getNodeIdArray();
		// for(int i = 0;i<nodeId.length;i++){
		// for(int j=i+1;j<nodeId.length;j++){
		// if(net.findEdgeByNodesID(nodeId[i], nodeId[j])!=null){
		// Debug.outn(nodeId[i]+" "+nodeId[j] );
		// }
		// }
		// }

	}

	/**
	 * An algorithm for computing clusters (community structure) in graphs based
	 * on edge betweenness. The betweenness of an edge is defined as the extent
	 * to which that edge lies along shortest paths between all pairs of nodes.
	 * This algorithm works by iteratively following the 2 step process: Compute
	 * edge betweenness for all edges in current graph Remove edge with highest
	 * betweenness Running time is: O(kmn) where k is the number of edges to
	 * remove, m is the total number of edges, and n is the total number of
	 * vertices. For very sparse graphs the running time is closer to O(kn^2)
	 * and for graphs with strong community structure, the complexity is even
	 * lower. This algorithm is a slight modification of the algorithm discussed
	 * below in that the number of edges to be removed is parameterized.
	 * 
	 * @param comm
	 * @param network
	 */
	public static void EdgeBetweenness(AbstractCommunity comm,
			AbstractNetwork net, String path, String name, String postfix) {

		Vector<AbstractE> allEdges = net.getAllEdges();


		int removedEdgeNum = 10;// net.getEdgesNum()/40;
		EdgeBetweennessClusterer<AbstractV, AbstractE> eBetCluster = new EdgeBetweennessClusterer<AbstractV, AbstractE>(
				removedEdgeNum);
		
		Set<Set<AbstractV>> commResult = eBetCluster.transform(net);
		
		comm.importCommunity(commResult);

		Debug.outn("edge betweenness partition end");
		comm.communityID = net.getNetID();
		comm.setModularity(CommunityTools.NewmanModularity(comm, net));
		// Debug.outn()
		if (name.equals("")) {
			name = net.getNetID();
		}
		if (postfix.equals("")) {
			postfix = ".comm";
		}
		comm.saveCommunityToFile(path + name + postfix, false);

	}

	/**
	 * 
	 * @param comm
	 * @param net
	 * @param path
	 * @param name
	 * @param postfix
	 */
	public static void VoltageClusterer(AbstractCommunity comm,
			AbstractNetwork net, String path, String name, String postfix) {

		Vector<AbstractE> allEdges = net.getAllEdges();


		VoltageClusterer<AbstractV, AbstractE> voltCluster = new VoltageClusterer<AbstractV, AbstractE>(
				net, 9);	
		
		
		// comm.community=(Set)voltCluster.cluster(9);
		if (comm.community == null) {
			comm.community = new HashSet<Set<String>>();
		}
		
		comm.importCommunity(voltCluster.cluster(80));
				
		comm.communityID = net.getNetID();
		comm.setModularity(CommunityTools.NewmanModularity(comm, net));
		// Debug.outn()

		if (name.equals("")) {
			name = net.getNetID();
		}
		if (postfix.equals("")) {
			postfix = ".comm";
		}
		comm.saveCommunityToFile(path + name + postfix, false);

	}

	/**
	 * 
	 * @param comm
	 * @param net
	 * @param path
	 * @param name
	 * @param postfix
	 */
	public static void BicomponentClusterer(AbstractCommunity comm,
			AbstractNetwork net, String path, String name, String postfix) {


		BicomponentClusterer<AbstractV, AbstractE> bicompCluster = new BicomponentClusterer<AbstractV, AbstractE>();

		comm.importCommunity(bicompCluster.transform(net));	
	

		// Debug.outn()

		if (name.equals("")) {
			name = net.getNetID();
		}
		if (postfix.equals("")) {
			postfix = ".bicomp.comm";
		}
		comm.communityID = net.getNetID();
		comm.saveCommunityToFile(path + name + postfix, false);

	}

	
	public static void WeakComponentClusterer(AbstractCommunity comm,
			AbstractNetwork net, String path, String name, String postfix) {


		WeakComponentClusterer<AbstractV, AbstractE> weakCompCluster = new WeakComponentClusterer<AbstractV, AbstractE>();

		comm.importCommunity( weakCompCluster.transform(net));

		// Debug.outn()

		if (name.equals("")) {
			name = net.getNetID();
		}
		if (postfix.equals("")) {
			postfix = ".weakComp.comm";
		}
		comm.communityID = net.getNetID();
		comm.saveCommunityToFile(path + name + postfix, false);

	}

	
	/**
	 * �������缰�����ż������Ż��ֵ�ģ���
	 * 
	 * @param comm
	 *            ���ֺ������
	 * @param network
	 *            ���Ŷ�Ӧ������
	 * @return
	 */
	public static float NewmanModularity(AbstractCommunity comm,
			AbstractNetwork network) {
		/* */

		int nodeNum = 0; // ���нڵ�����
		Iterator<Set<String>> commIt = comm.getCommunity().iterator();
		while (commIt.hasNext()) {
			Set<String> subCommSet = commIt.next();
			nodeNum += subCommSet.size();
		}

		// Debug.outn("total="+total);

		int[] subCommArray = new int[nodeNum]; // ��������
		int cidx = 0;
		commIt = comm.getCommunity().iterator();
		
		
		
		// Debug.outn("~~~~~~~~~~~~~");
		while (commIt.hasNext()) {
			Set<String> subComm = commIt.next();
			Iterator<String> nodeIt = subComm.iterator();
			while (nodeIt.hasNext()) {
				int ii = network.id2Idx(nodeIt.next());
				// Debug.outn(ii);
				subCommArray[ii] = cidx;
			}
			// Debug.outn("-------------------");
			cidx++;
		}

		return NewmanQ(subCommArray, comm.getCommunity().size(), network);
	}

	public static float NewmanModularity(Vector<Vector<Integer>> comm,
			UndirectedSparseGraph network) {
		/* */
		float Q = 0;
		int edgeNum = network.getEdgeCount();
		int nodeNum = network.getVertexCount();

		for (int i = 0; i < comm.size(); i++) {
			Vector<Integer> subComm = comm.get(i);
			for (int m = 0; m < subComm.size(); m++) {
				int nodeA = subComm.get(m);
				for (int n = 0; n < subComm.size(); n++) {
					int nodeB = subComm.get(n);

					int degreeA = network.getNeighborCount(nodeA);
					int degreeB = network.getNeighborCount(nodeB);
					int adj = network.isNeighbor(nodeA, nodeB) ? 1 : 0;

					Q += adj - degreeA * degreeB / (2 * edgeNum);
					if (m == n)
						Q = 0;

				}
			}
		}

		return Q / (2 * edgeNum);

	}

	
	/**
	 * ���㵱ǰ�������ŷ��෽ʽ��Newmanģ�黯ϵ��Q
	 * 
	 * @param parts
	 *            ��������ŷ��෽ʽ�����ű�Ŵ�0��ʼ
	 * @param partNumber
	 *            ���ŵĸ���
	 * @param network
	 * @return ģ�黯ϵ��Q
	 */
	public static float NewmanQ(int[] parts, int partNumber,
			AbstractNetwork network) {

		/* sub community matrix */
		double[][] e = new double[partNumber][partNumber];
		for (int i = 0; i < e.length; i++)
			for (int j = 0; j < e[i].length; j++)
				e[i][j] = 0;

		Vector<AbstractE> edges = network.getAllEdges();
		for (int i = 0; i < edges.size(); i++) {
			
			
			
			int pa = parts[edges.get(i).getAIdx()];
			int pb = parts[edges.get(i).getBIdx()];

			e[pa][pb]++;
			if (pa != pb) {
				e[pb][pa]++;
			}
		}// for.....

		for (int i = 0; i < e.length; i++) {
			for (int j = 0; j < e[i].length; j++) {
				// Debug.out(e[i][j]+" ");
				e[i][j] /= edges.size();
			}
			// Debug.outn("");
		}

		double[] a = new double[partNumber];

		for (int i = 0; i < a.length; i++) {
			a[i] = 0;
			for (int j = 0; j < e[i].length; j++) {
				if (i == j) {
					a[i] += e[i][j];
				} else {
					a[i] += e[i][j] / 2;
				}
			}
		}// for....

		float Q = 0;
		for (int i = 0; i < e.length; i++) {
			// Debug.outn("e_ij "+e[i][i]+" a_i " + a[i] );
			// Debug.outn((e[i][i] - a[i] * a[i]));
			Q += (e[i][i] - a[i] * a[i]);
		}

		// Debug.outn("modularity ");
		for (int m = 0; m < e.length; m++) {
			for (int n = 0; n < e.length; n++) {
				// Debug.out(e[m][n]+" ");
			}
			// Debug.outn("\n");
		}

		return Q;

	}// ////////NewmanQ
	
	
	public static float NewmanQ(AbstractCommunity comm,
			AbstractNetwork network) {
		
		int partNumber= comm.getCommunitySize();
		
		/* sub community matrix */
		double[][] e = new double[partNumber][partNumber];
		for (int i = 0; i < e.length; i++)
			for (int j = 0; j < e[i].length; j++)
				e[i][j] = 0;

		Vector<AbstractE> edges = network.getAllEdges();
		for (int i = 0; i < edges.size(); i++) {
			
			
			
			int pa = parts[edges.get(i).getAIdx()];
			int pb = parts[edges.get(i).getBIdx()];

			e[pa][pb]++;
			if (pa != pb) {
				e[pb][pa]++;
			}
		}// for.....

		for (int i = 0; i < e.length; i++) {
			for (int j = 0; j < e[i].length; j++) {
				// Debug.out(e[i][j]+" ");
				e[i][j] /= edges.size();
			}
			// Debug.outn("");
		}

		double[] a = new double[partNumber];

		for (int i = 0; i < a.length; i++) {
			a[i] = 0;
			for (int j = 0; j < e[i].length; j++) {
				if (i == j) {
					a[i] += e[i][j];
				} else {
					a[i] += e[i][j] / 2;
				}
			}
		}// for....

		float Q = 0;
		for (int i = 0; i < e.length; i++) {
			// Debug.outn("e_ij "+e[i][i]+" a_i " + a[i] );
			// Debug.outn((e[i][i] - a[i] * a[i]));
			Q += (e[i][i] - a[i] * a[i]);
		}

		// Debug.outn("modularity ");
		for (int m = 0; m < e.length; m++) {
			for (int n = 0; n < e.length; n++) {
				// Debug.out(e[m][n]+" ");
			}
			// Debug.outn("\n");
		}

		return Q;

	}// ////////NewmanQ

	
	public double ModuleDegree(Set<Set<Integer>> community,
			AbstractNetwork network) {
		double MDegree = 0;

		int nodeNum = 0; // ���нڵ�����
		Iterator<Set<Integer>> commIt = community.iterator();
		while (commIt.hasNext()) {
			Set<Integer> subCommSet = commIt.next();
			nodeNum += subCommSet.size();
		}

		// Debug.outn("total="+total);

		int[] subCommArray = new int[nodeNum]; // ��������
		int cidx = 0;
		commIt = community.iterator();
		// Debug.outn("~~~~~~~~~~~~~");
		while (commIt.hasNext()) {
			Set<Integer> part = commIt.next();
			Iterator<Integer> ito = part.iterator();
			while (ito.hasNext()) {
				int ii = ((Integer) ito.next()).intValue();
				// Debug.outn(ii);
				subCommArray[ii] = cidx;
			}
			// Debug.outn("-------------------");
			cidx++;
		}

		return MDegree;
	}

	/**
	 * �����ϵļ��ϱ�ʾΪ���飬�������ʾԭ��Ա�����ڵļ���
	 * 
	 * @param comm
	 * @return
	 */
	public static int[] Set2Array(Set<Set<Integer>> comm) {
		int totalNodeNum = 0; // ���нڵ�����
		Iterator<Set<Integer>> commIt = comm.iterator();
		while (commIt.hasNext()) {
			Set<Integer> subCommSet = commIt.next();
			totalNodeNum += subCommSet.size();
		}

		// Debug.outn("total="+total);

		int[] subCommArray = new int[totalNodeNum]; // ��������
		int partIndex = 0;
		commIt = comm.iterator();
		// Debug.outn("~~~~~~~~~~~~~");
		while (commIt.hasNext()) {
			Set<Integer> part = commIt.next();
			Iterator<Integer> ito = part.iterator();
			while (ito.hasNext()) {
				int ii = ((Integer) ito.next()).intValue();
				// Debug.outn(ii);
				subCommArray[ii] = partIndex;
			}
			// Debug.outn("-------------------");
			partIndex++;
		}
		return subCommArray;
	}

	/**
	 * ��ȡ�������ͨ���� ÿ����ͨ�����ʾΪһ�����ϣ���ԱΪ�ڵ��Idx�� ���м��ϰ����ڷ��ؼ����� ����ͨ��Set2Array����ת��������
	 * (Note:���������Partitions���еĺ���һ����
	 * 
	 * @param net
	 * @return
	 */
	public static Set<Set<Integer>> partitions(AbstractNetwork net) {
		Set<Set<Integer>> ret = new HashSet<Set<Integer>>();

		LinkedList<Integer> allNode = new LinkedList<Integer>();
		for (int i = 0; i < net.getNetworkSize(); i++)
			allNode.add(i);

		while (!allNode.isEmpty()) {
			Set<Integer> newNodesSet = new HashSet<Integer>();
			LinkedList<Integer> stack = new LinkedList<Integer>();

			int seed = ((Integer) allNode.remove()).intValue();
			newNodesSet.add(seed);
			stack.add(seed);

			while (!stack.isEmpty()) {
				int n = ((Integer) stack.remove()).intValue();
				int[] nbrs = net.getNeighborsArray(n);
				for (int i = 0; i < nbrs.length; i++)
					if (allNode.contains(nbrs[i])) {
						newNodesSet.add(nbrs[i]);
						stack.add(nbrs[i]);
						allNode.remove(new Integer(nbrs[i]));
					}
			}// while...
			ret.add(newNodesSet);
		}// while...
		return ret;
	}

	/**
	 * ���ŷֽ⣬CNM�㷨 </p> ��Դ:��Finding community structure in very large
	 * networks����phys.rev. 2004
	 * 
	 * @param inputNet
	 * @return ���ϵ�Ԫ��Ϊ���ϣ���ÿһ�����ϴ���һ����������ԱΪ��Ӧ�ڵ�ID
	 */
	public static AbstractCommunity CNMdecomposition(AbstractNetwork inputNet) {

		// Debug.outn("is decomposing newwork" + inputNet.getNetworkID() +
		// " ������");
		// java����֧�ַ������飬�˴�������ʱȥ������ T_T!��
		Set<Integer>[] community = new Set[inputNet.getNetworkSize()];
		for (int i = 0; i < community.length; i++) {
			community[i] = new HashSet<Integer>();
			community[i].add(i);
		}

		double NEWMANQ = 0;

		double[] H = new double[inputNet.getNetworkSize()];
		int[] Hidx = new int[inputNet.getNetworkSize()];

		double[] a = new double[inputNet.getNetworkSize()];

		AbstractNetwork blankNet = inputNet.getBlankNetwork(inputNet
				.getNetworkSize(), AbstractNetwork.NTYPE_UNDIRECTED);
		// Debug.outn("has created blank network");

		int totalEdges = inputNet.getEdgesNum();
		int netSize = inputNet.getNetworkSize();

		for (int i = 0; i < netSize; i++) { // ��ʼ��������
			int[] nbrs = inputNet.getNeighborsArray(i);
			for (int ni = 0; ni < nbrs.length; ni++) {
				if (nbrs[ni] > i) {
					int idi = i;
					int idj = nbrs[ni];

					int ki = inputNet.getNodeDegreeByIdx(idi);
					int kj = inputNet.getNodeDegreeByIdx(idj);
					double deltaQ = 1.0 / (2.0 * totalEdges) - (ki * kj)
							/ (4.0 * totalEdges * totalEdges);

					blankNet
							.setEdge(nbrs[ni], i, deltaQ, inputNet.getNewEdge());
				}//
			}
		}// /for.......

		// Debug.outn("initialize blank network");

		for (int i = 0; i < netSize; i++)
			a[i] = blankNet.getNodeDegreeByIdx(i) / (2.0 * totalEdges); // initialize
																		// a

		for (int i = 0; i < netSize; i++) { // initialize H
			int[] nbrs = blankNet.getNeighborsArray(i);
			if (nbrs.length == 0)
				continue;

			double maxQ = 0;
			int maxidx = nbrs[0];

			for (int ni = 0; ni < nbrs.length; ni++) {
				double tmpQ = blankNet.getWeight(i, nbrs[ni]);
				if (tmpQ > maxQ) {
					maxQ = tmpQ;
					maxidx = nbrs[ni];
				}
			}
			H[i] = maxQ;
			Hidx[i] = maxidx;
		}// for...... //initialize H

		// Debug.outn("�ѳ�ʼ��H");

		while (true) {

			double maxH = 0;
			int maxHidx = 0;

			// �Ȳ�������H
			for (int i = 0; i < H.length; i++)
				if (H[i] > maxH) {
					maxH = H[i];
					maxHidx = i;
				}
			if (maxH == 0)
				break; // ���ж�H��Ϊ��ֵ����������

			NEWMANQ += maxH;

			int idi = maxHidx;
			int idj = Hidx[idi];

			// �ϲ�i��j
			community[idj].addAll(community[idi]);
			community[idi] = null; // ɾ��i

			Set<Integer> updateH = new HashSet<Integer>();

			Set<Integer> nbrs_i = blankNet.getNeighborsSet(idi);
			Set<Integer> nbrs_j = blankNet.getNeighborsSet(idj);

			Iterator<Integer> it = nbrs_j.iterator();
			while (it.hasNext()) {
				int idk = it.next().intValue();
				if (idk == idj)
					continue;
				if (nbrs_i.contains(idk)) { // kͬʱ��i��j����
					blankNet.setEdge(idj, idk, blankNet.getWeight(idj, idk)
							+ blankNet.getWeight(idi, idk), inputNet
							.getNewEdge());
					nbrs_i.remove(idk);
				} else { // kֻ��j����
					blankNet.setEdge(idj, idk, blankNet.getWeight(idj, idk) - 2
							* a[idi] * a[idk], inputNet.getNewEdge());
				}
				updateH.add(idk);
			}// ////////

			it = nbrs_i.iterator();
			while (it.hasNext()) {
				int idk = ((Integer) it.next()).intValue();
				if (idk == idj)
					continue;
				// kֻ��i����������j����
				blankNet.setEdge(idj, idk, blankNet.getWeight(idi, idk) - 2
						* a[idj] * a[idk], inputNet.getNewEdge());
				updateH.add(idk);
			}// ////while..

			int[] nbrs = blankNet.getNeighborsArray(idi);
			for (int ni = 0; ni < nbrs.length; ni++) {
				blankNet.setEdge(idi, nbrs[ni], 0, inputNet.getNewEdge()); // ɾ��i��һ��Ԫ��
			}

			updateH.add(idi);
			updateH.add(idj);

			it = updateH.iterator(); // ����H
			while (it.hasNext()) {
				int id = ((Integer) it.next()).intValue();

				double maxQ = 0;
				int maxidx = 0;

				nbrs = blankNet.getNeighborsArray(id);
				if (nbrs.length == 0) {
					H[id] = 0;
					continue;
				}// if(nbrs.length==0)

				for (int ni = 0; ni < nbrs.length; ni++) {
					double tmpQ = blankNet.getWeight(id, nbrs[ni]);
					if (tmpQ > maxQ) {
						maxQ = tmpQ;
						maxidx = nbrs[ni];
					}
				}

				H[id] = maxQ;
				Hidx[id] = maxidx;
			}// while(it.hasNext()) //UpdateH

			a[idj] = a[idi] + a[idj]; // ����a
			a[idi] = 0;
		}// while(true)

		// Debug.outn("while()");

		/* �Խڵ�ID�������� */
		HashSet<Set<Integer>> communities = new HashSet<Set<Integer>>();
		for (int i = 0; i < community.length; i++) {
			if (community[i] != null) {
				Set<Integer> subComm = new TreeSet<Integer>();
				Iterator<Integer> subCommIt = community[i].iterator();
				while (subCommIt.hasNext()) {
					subComm.add(inputNet.idx2Id(subCommIt.next()));
				}
				communities.add(subComm);
			}
		}

		AbstractCommunity gelCommunity = new ASTopCommunity(inputNet
				.getNetID(), communities);
		double moduleDegree = NetMatrix
				.matrixModularity(inputNet, gelCommunity);

		gelCommunity.setModularity(moduleDegree);
		// Debug.outn("network " + inputNet.getNetworkID() + " separate over");
		return gelCommunity;
	}// ////////////////////////////////////////////////////

	/**
	 * ��ȡGN���ѣ��õ���ģ�������
	 * 
	 * @param nt
	 * @param nmQ
	 * @param cmmc
	 * @return
	 */
	public static void GN_decomposition_optimized(AbstractNetwork nt,
			double[] nmQ, int[] cmmc) {
		int level = nmQ.length;
		AbstractNetwork net = nt.clone();
		LinkBetweenness[] lball = LinkBetweenness.sortedBetweenness(nt);
		int step = lball.length / level + 1;

		for (int i = 0; i < level; i++) {
			for (int j = 0; (j < step && j < lball.length); j++)
				// ��ɾ��������ı�
				net.setEdge(lball[j].a_idx, lball[j].b_idx, 0, nt.getNewEdge());
			Set<Set<Integer>> part = partitions(net);
			int[] comm = Set2Array(part); // ���㵱ǰ�õ������Ż���

			cmmc[i] = part.size(); // �õ������ŵĸ���
			nmQ[i] = NewmanQ(comm, part.size(), nt); // ģ���Q

			lball = LinkBetweenness.sortedBetweenness(net);
		}
	}

	public static void GN_decomposition(AbstractNetwork nt, double[] nmQ,
			int[] cmmc) {
		int level = nmQ.length;
		AbstractNetwork net = nt.clone();
		int step = nt.getEdgesNum() / level + 1;

		for (int i = 0; i < level; i++) {
			for (int j = 0; j < step; j++) { // ��ɾ��������ı�
				AbstractNetwork nbet = LinkBetweenness.calculate(net);
				Vector<AbstractE> lbets = nbet.getEdgeArray();
				if (lbets.size() == 0)
					break;
				int maxi = max_betweenness(lbets);
				net.setEdge(lbets.get(maxi).getAIdx(), lbets.get(maxi)
						.getBIdx(), 0, nt.getNewEdge());
			}
			Set<Set<Integer>> part = partitions(net);
			int[] comm = Set2Array(part); // ���㵱ǰ�õ������Ż���

			cmmc[i] = part.size(); // �õ������ŵĸ���
			nmQ[i] = NewmanQ(comm, part.size(), nt); // ģ���Q
		}
	}

	/**
	 * ����GN�㷨�õ����ŷֽ�
	 * 
	 * @param inputNet
	 * @param preferedQ
	 * @param level
	 * @return
	 */
	public static Set<Set<Integer>> GN_decomposition(AbstractNetwork inputNet,
			double preferedQ, int level) {
		AbstractNetwork net = inputNet.clone();
		int step = inputNet.getEdgesNum() / level + 1;

		for (int i = 0; i < level; i++) {
			for (int j = 0; j < step; j++) { // ��ɾ��������ı�
				AbstractNetwork nbet = LinkBetweenness.calculate(net);
				Vector<AbstractE> lbets = nbet.getEdgeArray();
				if (lbets.size() == 0)
					break;
				int maxi = max_betweenness(lbets);

				net.setEdge(lbets.get(maxi).getAIdx(), lbets.get(maxi)
						.getBIdx(), 0, net.getNewEdge());
			}
			Set<Set<Integer>> part = partitions(net);
			int[] comm = Set2Array(part); // ���㵱ǰ�õ������Ż���

			if (NewmanQ(comm, part.size(), inputNet) >= preferedQ) // �Ѿ�����Ԥ���趨��Qֵ������
				return part;
		}
		return null;
	}

	static int max_betweenness(Vector<AbstractE> lbets) {
		int maxi = 0;
		double maxb = 0;
		for (int i = 0; i < lbets.size(); i++)
			if (lbets.get(i).getWeight() > maxb) {
				maxi = i;
				maxb = lbets.get(i).getWeight();
			}
		return maxi;
	}

	public void kCliquePeroclation(AbstractNetwork inputNet, String outputPath) {

		int maxK = inputNet.getMaxDegree();
		this.kCliquePeroclation(inputNet, 3, maxK, outputPath, "",
				"ArkASClique");
	}

	/**
	 * Ѱ����ϵ�����浽�ļ����ļ���Ϊ�� ǰ׺+����ID+��׺</p> k-��ϵ���ŷֽ�,�ݹ�����㷨 </p> �ο�����:Uncovering the
	 * overlapping community structure of complex networks in nature and society
	 * 
	 * @param inputNet
	 *            ҪѰ����ϵ������
	 * @param kFrom
	 *            k����ʼֵ
	 * @param kTo
	 *            k����ֵֹ
	 * @param outputPath
	 *            ��ϵ�ļ��ı���·������ϵ�����ļ���������ID.clique
	 * @param prefix
	 *            �����ļ�����ǰ׺
	 * @param postfix
	 *            �����ļ����ĺ�׺
	 */
	public void kCliquePeroclation(AbstractNetwork inputNet, int kFrom,
			int kTo, String outputPath, String prefix, String postfix) {

		if (kFrom < 3) {
			kFrom = 3;
		}
		if (kTo == 0) { // ���Ŀ�����ϵ
			kTo = inputNet.getMaxDegree();
		}
		if (kFrom > kTo) {
			kFrom = kTo;
		}

		/* �������k��ϵ��Ӧ��������k���ڵ�Ķ�ֵ���ڵ���k-1�� */

		int[] existK = MathTool.variableCCDF(inputNet.getAllDegrees());
		// Debug.outn(existK, "�ȷֲ�");
		/* �������ܴ�����ϵ�Ķ�ֵ�����ڵ�ֵ ��0�� */
		for (int j = existK.length - 1; j >= 0; j--) {
			if (j == 2)
				break; // ��Ѱ��2��ϵ
			if (existK[j - 1] < j)
				existK[j] = 0;
		}
		Debug.outn(existK, "���ܴ��ڵ�k��ϵ");

		Map<Integer, Integer> kClique = new TreeMap<Integer, Integer>();
		for (int index = kFrom; index <= kTo; index++) {

			if (existK[index] == 0) { // �Ѿ��жϳ������ܴ���kValue ��ϵ��������Ѱ�ң�������һ��kValue
				continue;
			}

			// Map ����Kֵ������ϵ����
			kClique.put(index, new Integer(0));
		}

		AbstractNetwork net = inputNet.clone();

		int[] nodeIds = net.getNodeIdArray();
		int[] nodesDegrees = new int[nodeIds.length];
		for (int i = 0; i < nodeIds.length; i++) {
			nodesDegrees[i] = inputNet.getNodeDegreeById(nodeIds[i]);
		}

		Set<Integer> setA = null;
		Set<Integer> setB = null;

		FileWriter fWriter = null;

		/* Ѱ�Ҳ�ͬK��ϵ */
		for (int kValue = kFrom; kValue <= kTo; kValue++) {

			if (existK[kValue] == 0) { // �Ѿ��жϳ������ܴ���kValue ��ϵ��������Ѱ�ң�������һ��kValue
				Debug.outn("������ " + kValue + " ��ϵ");
				continue;
			}

			Debug
					.outn(kValue
							+ " ��ϵѰ�ҿ�ʼ=================================================");
			Debug.outn("���нڵ� " + nodeIds.length + " ��");

			/* ��ϵ����ļ����� */
			String cliqueFileName = outputPath + prefix
					+ inputNet.getNetID()
					+ (kValue >= 10 ? kValue : ("0" + kValue)) + postfix;

			/* ÿ��k��ϵ���ݱ��浽һ���ļ� */
			try {
				fWriter = new FileWriter(new File(cliqueFileName), true);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				Debug.out("IOException in save String to file "
						+ cliqueFileName);
			}

			for (int j = 0; j < nodeIds.length; j++) { // Ѱ��ÿһ���ڵ��k��ϵ

				int nodeId = nodeIds[j];

				if (nodesDegrees[j] < kValue - 1) { // ���ڵ�Ķ�ֵС��
													// k-1���򲻿��ܴ��ڰ����ýڵ��k��ϵ
					net.deleteNodeById(nodeId); // 
					continue;
				}

				// Debug.outn(nodeId);
				/* ��ʼ������A��ֻ�����ڵ�v,Ѱ��v������k��ϵ */
				setA = new TreeSet<Integer>();
				setA.add(nodeId);

				setB = net.getNeighborsSetById(nodeId); // ��ʼ������B�������ڵ�v���ھӽڵ�

				// Debug.outn(setA , "setA");
				// Debug.outn(setB , "setB");
				findCP(net, setA, setB, kValue, kClique, fWriter); // Ѱ��v������kValue��ϵ

				if (j % 10 == 0) {
					Debug.outn(kValue + " ��ϵ����� " + j + " ���ڵ��Ѱ��");
				}

				net.deleteNodeById(nodeId); // ɾ����Ѱ�ҹ���ϵ�Ľڵ㼰�����ӱ�
				// Debug.outn(net.getNetworkSize());

			}// /////////////////////ÿһ���ڵ�

			Debug
					.outn(kValue
							+ " ��ϵѰ�����=================================================");

			net = null; // �����ͷ��ڴ�
			net = inputNet.clone(); // ԭ�����ѱ��޸ģ�����һ���µġ�

			/* ��û��k��ϵ�� ��k+1��ϵҲ�����ڣ�û�б�Ҫ��Ѱ�� k+n ��ϵ */
			if (kClique.get(kValue).intValue() == 0) {
				break;
			}

			System.gc(); // �������գ�������ȫ�����û���

			try {
				fWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}// /////////////////////// Ѱ�Ҳ�ͬ��Kֵ��ϵ

		// �����ϵ��Ϣ
		Debug.outn("����" + " " + inputNet.getNetID());
		Iterator<Integer> kIt = kClique.keySet().iterator();
		int k;
		while (kIt.hasNext()) {
			k = kIt.next();
			Debug.outn(k + " " + kClique.get(k).intValue());
		}

		Debug.outn("������ϵѰ�����");

	}// /////////////////////////////////////

	/**
	 * Ѱ��ĳһ���ڵ������k��ϵ
	 * 
	 * @param net
	 * @param setFromA
	 *            ������ʼԴ�ڵ�ļ���
	 * @param setB
	 *            Դ�ڵ���ھӽڵ㼯��
	 * @param k
	 *            ��ϵֵ
	 * @param kClique
	 *            ����k��ϵ
	 * @param fWriter
	 */
	private void findCP(AbstractNetwork net, Set<Integer> setFromA,
			Set<Integer> setB, int k, Map<Integer, Integer> kClique,
			FileWriter fWriter) {
		// Debug.outn("//////////////////////����findCP����/////////////////");
		if (setB.size() == 0 || setFromA.size() == k) {
			// Debug.outn("��findCP����");
			return;
		}

		// Debug.outn(setA,"setA");
		// Debug.outn(setB , "setB");

		Set<Integer> setACopy = MathTool.copySet(setFromA);
		Set<Integer> setBCopy = MathTool.copySet(setB);

		Iterator<Integer> nodeBIt = setB.iterator();

		int nodeB = (Integer) nodeBIt.next();

		// while(nodeBIt.hasNext()){

		setFromA.add(nodeB); // �ƶ�����B��һ���ڵ㵽����A
		setB.remove(nodeB);

		// Debug.outn("�ƶ�B�еĽڵ㵽A "+nodeB);

		setBCopy.remove(nodeB);// ��¼����B�ƶ���һ���ڵ��ļ���״̬

		/* ɾ������B�в��뼯��A�����нڵ������Ľڵ� */
		Iterator<Integer> setBIt = setB.iterator();
		Iterator<Integer> setAIt = null;

		while (setBIt.hasNext()) { // ����B�е�ÿһ���ڵ�
			int nodeInB = setBIt.next();
			setAIt = setFromA.iterator();
			while (setAIt.hasNext()) { // ����A�е�ÿһ���ڵ�
				int nodeInA = setAIt.next();

				if (net.findEdgeByNodesID(nodeInA, nodeInB) == null) { // �ڵ���A�е�ĳһ���ڵ㲻����
					setBIt.remove(); // ɾ����������A�����нڵ����� �����Ľڵ�
					break; // ֻҪ��һ�������ӾͲ�����Ҫ����
				}
			}
		}

		setBIt = null;

		// Debug.outn("ɾ��B�в���A�����нڵ������Ľڵ��");
		// Debug.outn(setA,"setA");
		// Debug.outn(setB , "setB");

		// ����B��Ϊ�գ� ����ǰһ��
		if (setFromA.size() < k & setB.isEmpty()) {
			// Debug.outn("����B��Ϊ�ռ�");
			// findCP(net, setACopy, setBCopy, k, kClique, fWriter);
			return;

		}

		// ����A��BΪ����һ���ϴ���ϵ�е��Ӽ�������ǰһ��
		// if (true) {
		// // return;
		// }

		// �õ�����ϵ,����ǰһ��
		if (setFromA.size() == k) {
			// Debug.outn(setA , "�õ�����ϵ!!!!!!");

			// ����k��ϵ����
			int num = kClique.get(k).intValue() + 1;
			kClique.put(k, num);

			/* ��ϵ���浽�ļ� */
			try {
				fWriter.write(k + " " + MathTool.setToString(setFromA, " ")
						+ "\n");
				fWriter.flush();
			} catch (IOException ioe) {
				Debug.out("IOException in save String to file");
			}

			// Debug.outn(" ���ҵ���ϵ������ "+kClique.get(k).size());
			// findCP(net, setACopy, setBCopy, k, kClique, fWriter);
			return;

		}

		// Debug.outn("����Ѱ��");
		findCP(net, setACopy, setBCopy, k, kClique, fWriter);

		findCP(net, setFromA, setB, k, kClique, fWriter);

		// Debug.outn("��findCP����");
		return;

		// }

	}// //////////// findCP()

	/**
	 * ���ļ���ת����ϵ���ݣ�����ĳЩ��ϵ���ݹ��󣬽����¶������
	 * 
	 * @param cliqueFileName
	 */
	public void loadClique(String cliqueFileName) {

		try {
			FileReader fr = new FileReader(new File(cliqueFileName));
			BufferedReader br = new BufferedReader(fr);
		} catch (Exception e) {
			Debug.outn(e.toString());
		}
	}

	public void output(AbstractCommunity community) {
		// community.getCommunity();
	}

	/**
	 * 
	 */
	public static void convertCommnityData(String inputPath, String inputName,
			String postfix, String outputPath, String outputName,String outputPostfix) {
		
		//Debug.outn(inputPath);
		//Debug.outn(inputName);
		
		GeneralCommunity gCom = new GeneralCommunity();
		//Debug.outn(0);
		CommunityBuilder.loadCommunityFromOtherFile(gCom, inputPath,"","");
		//Debug.outn(filePath);
		
	//	Debug.outn(1);
		
		GeneralNetwork net=  new GeneralNetwork();
		String netName = inputName.substring(0, inputName.length()-3);
		NetworkDataLoader.loadNetworkFromFile(net,inputPath.substring(0,inputPath.length()-3) );
	//	Debug.outn(2);
		gCom.setCommunityID(netName);		
		
	//	Debug.outn("compute Q");
	//	Debug.outn( gCom.getAllNodeID().size() );
	//	Debug.outn(net.getNetworkSize());
		int[] allNode= net.getNodeIdArray();
		Set<Integer> commNode= gCom.getAllNodeID();
		for(int i=0;i<allNode.length;i++){
		  if(	!commNode.contains(allNode[i]) ){
			//  Debug.outn(i+" "+allNode[i]);
		  }
		}
	
		
		gCom.modularity=NetMatrix.matrixModularity(net,gCom);
		outputPostfix= ".HE.Community";
		gCom.saveCommunityToFile(outputPath+netName+outputPostfix, false);
		
		Debug.outn(net.getNetID()+" "+gCom.modularity);

	}
	
	public static void convertCommnityDataMRW(String inputPath, String inputName,
			String postfix, String outputPath, String outputName,String outputPostfix) {
		
		GeneralCommunity gCom = new GeneralCommunity();
		
		Map<String, Set<Integer>> comMap = new HashMap<String, Set<Integer> >();
		
		FileReader fr;
		BufferedReader br;
		try {
			
			// Debug.outn(linkFiles.length);
			fr = new FileReader(new File(inputPath));
			br = new BufferedReader(fr);
			String line = br.readLine();
			
			int subCommID=0;
			HashSet<Integer> subComm=null;
			while (line != null) { // ����ÿһ����Ϣ
				if(line.contains("#")){ 
					line=br.readLine();
					continue;				
				}				
								
				String[] stokens = line.split(" ");
				//Debug.outn(stokens[2]);
				String node = stokens[2].substring(1, stokens[2].length()-1);
			
				//Debug.outn(node);
				Integer nodeID=  Integer.valueOf(node );				
							
				
				
				if( gCom.community.size() == 0){ // the first sub community
					subComm = new HashSet<Integer>();
					subCommID = Integer.valueOf( stokens[0].split(":")[0] );
					gCom.community.add(subComm);
				}
				
				if( gCom.community.size()>0  ){
					int nextSubCommID = Integer.valueOf( stokens[0].split(":")[0] ); // get new sub commID
					if(nextSubCommID!=subCommID){ //new sub community
						//Debug.outn(subCommID+" "+nextSubCommID);
						subComm = new HashSet<Integer>();
						subCommID=nextSubCommID;
						gCom.community.add(subComm);
					}					
				}
				
			//	Debug.outn(subCommID);
				
				subComm.add(nodeID);
				
				
//				if(comMap.containsKey(subCommID)){
//					comMap.get(subCommID).add(nodeID);
//				}
				
				line = br.readLine();
			}///////////each line
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
//		Debug.outn(gCom.community.size());
		outputName= (inputName+postfix).replaceAll("tree", "MRW.community");
		GeneralNetwork net=  new GeneralNetwork();
		String netName = inputName.substring(0, inputName.length()-5);
		NetworkDataLoader.loadNetworkFromFile(net,inputPath.substring(0,inputPath.length()-5) );
	//	Debug.outn(2);
		gCom.setCommunityID(netName);	
		gCom.modularity=NetMatrix.matrixModularity(net,gCom);
		gCom.saveCommunityToFile(outputPath+netName+outputPostfix, false);
		
		Debug.outn(net.getNetID()+" "+gCom.modularity);


	}

}// /////////////////////////////////////////////////////////////////
