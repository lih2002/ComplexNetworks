/**
 * 
 */
package community;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;

//import ui.file.LoadNetworkData;

import properties.*;
import network.*;
import tools.*;
import io.FileOperation;

/**
 * Analysis of network and community
 * 
 * @author ge xin
 * 
 */
public class CommunityAnalysis {

	private Map<String, AbstractCommunity> ID2Communities;

	private NetworkAnalysis networkAnalysis;

	public CommunityAnalysis() {
		networkAnalysis = new NetworkAnalysis();
		NetworkIDComparator comp = new NetworkIDComparator();
		ID2Communities = new TreeMap<String, AbstractCommunity>();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CommunityAnalysis commA = new CommunityAnalysis();

		// commA.evoInDegree();
//		String commPath = "e:\\Gexin\\aslink\\arkComm2\\";
//		String netPath = "e:\\Gexin\\aslink\\formatArkASLink2\\";
//		String commPostfix = "arkComm";
//		String netPostfix = "ArkAsLink";
//		
//		
//		commPath= "e:\\Gexin\\aslink\\model\\";
//		netPath= "e:\\Gexin\\aslink\\model\\";
//		commPostfix = "pfpModelComm";
//		netPostfix = "pfpModel";

	//	commA.loadMonthCommAndNetwork(200801, 200812, commPath, "",
	//			commPostfix, netPath, " ", netPostfix);
		// commA.getAndSaveSubCommDegree("e:\\Gexin\\aslink\\arkComm2\\subCommDegree\\",
		// "subCommDegree");
//		commA.subCommNodeProperty("e:\\Gexin\\aslink\\arkComm2\\subComm\\", "",
//				"subCommProperty");
		
	//	Debug.outn(commA.getSubCommProperty());
		// Debug.outn(commA.getSubCommSize());
		// Debug.outn(commA.getSubCommSizeCCDF());
		
//		commPath= "e:\\Gexin\\aslink\\model\\";
//		netPath= "e:\\Gexin\\aslink\\model\\";
//		commPostfix = "pfpModelComm";
//		netPostfix = "pfpModel";
//		
//		commA.loadMonthCommAndNetwork(201001, 201001, commPath, "",
//				commPostfix, netPath, " ", netPostfix);
//		Debug.outn( commA.commInformation());
		
		new CommunityAnalysis().batchProcessData();
		

	}
	
	public void batchProcessData(){
		try {
			LoadNetworkData loadDialog = new LoadNetworkData();
			loadDialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			loadDialog.setVisible(true);
			File [] dataFiles = loadDialog.getSelectedFiles();
			
			String postfix[];
			if(dataFiles==null){
				return;
			}
			for(int i=0;i<dataFiles.length;i++){
				
				//load data according to the postfix of file.
				
				postfix = (dataFiles[i].getName()).split("\\.");
				Debug.outn(dataFiles[i].getName());
				//Debug.outn(postfix.length);
				//load network data and community data
				if( postfix[postfix.length-1].equals("largestPartitionNet") ){
								
					
					//load community data if exists.
					AbstractCommunity community = CommunityBuilder.creatCommunityFromFile(dataFiles[i].toString()+".community");
					if(community==null){
						Debug.outn("need community data");
						continue;
					}
					this.addCommunity(community);
					
					//load network data
					Debug.outn("load network: "+dataFiles[i].getName());
					GeneralNetwork commNet = new GeneralNetwork();
					NetworkDataLoader.loadNetworkFromFile( commNet , dataFiles[i].toString());
					//Debug.outn(commNet.getNetworkID());
					this.networkAnalysis.addNetwork(commNet);
					//Debug.outn("netID "+this.networkAnalysis.getNetwork(commNet.getNetworkID()).getNetworkID());
				}					
				
							
				Debug.outn( commInformation() );
				
				this.communityToNetwork("d:\\netData\\", "", ".communityNet");
				
				this.removeAll();
				this.networkAnalysis.removeAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 节点社团内度比例演化。即节点在社团内的邻居节点数量/节点度
	 * 
	 * @return
	 */
	public String evoInDegree() {
		String result = "";
		NetworkAnalysis nA = new NetworkAnalysis();

		for (int i = 0; i <= 7; i++) {
			nA.loadMonthNetworks(200001 + i * 100, 200012 + i * 100,
					"F:\\aslink\\month\\", "",
					"skitter_as_graph.undirected.merge");
			Iterator<String> netIt = nA.getNetworkIDSet().iterator();

			this.loadMonthCommunities(200001, 200712,
					"F:\\aslink\\monthCommID\\", "", "commID");

			while (netIt.hasNext()) {
				String netID = netIt.next();
				AbstractNetwork net = nA.getNetwork(netID);
				AbstractCommunity comm = this.getCommunity(netID);

				Iterator<Integer> nodeIDIt = net.getNodeIDs().iterator();

				int sumDegree = 0;
				int sumInDegree = 0;
				while (nodeIDIt.hasNext()) {
					int nodeID = nodeIDIt.next();

					int inDegree = comm.getNeighborsInSubComm(net, nodeID)
							.size();
					int degree = net.getNodeDegreeById(nodeID);
					sumDegree += degree;
					sumInDegree += inDegree;
				}

				Debug.outn(netID + "\t" + (float) sumInDegree / sumDegree);

			}

			nA.removeAll();
			this.removeAll();
		}

		return result;

	}

	public String commInformation() {
		String info = "";
		Iterator<String> commIt = this.ID2Communities.keySet().iterator();
		while (commIt.hasNext()) {
			String commID = commIt.next();
			AbstractCommunity comm = this.getCommunity(commID);
			info = info + "communityID " + commID + "\t" + " nodeNum "
					+ comm.getNodesNum() + "\t" + " subComm "
					+ comm.getSubCommSize().length + "\t" + " modularity "
					+ comm.getModularity() + "\n";
		}
		return info;
	}

	/**
	 * 
	 * @return
	 */
	public String getSubCommProperty() {
		
		StringBuffer subCommProperty=new StringBuffer();
		Iterator<String> commIt = this.ID2Communities.keySet().iterator();
		while (commIt.hasNext()) {
			
			String commID = commIt.next();
			
			
			
			AbstractCommunity comm = this.getCommunity(commID);
			AbstractNetwork net = this.networkAnalysis.getNetwork(commID);
			
			Iterator <Set<Integer>> subCommIt = comm.community.iterator();
			int subCommIndex=0;
			/* each sub community */
			while(subCommIt.hasNext()){
				Set<Integer> subComm = subCommIt.next();
				subCommProperty.append(" commID "+commID);
				subCommProperty.append(" subCommIndex "+subCommIndex++);
				subCommProperty.append(" subCommSize " + subComm.size());
				subCommProperty.append(" subCommDegree "+ comm.getNeighborsOutSubComm(net, subComm).size());
				
				subCommProperty.append("\n");
			}	
			
			Debug.outn("community "+commID + " over");
			
		}/////////////////each community
		return subCommProperty.toString();
	}

	public double[] getSubCommSizeCCDF() {

		String info = "";
		int totalCommNum = 0;
		Iterator<AbstractCommunity> commIt = this.ID2Communities.values()
				.iterator();
		while (commIt.hasNext()) {
			totalCommNum += commIt.next().getCommunitySize();
		}
		int[] commSizeArray = new int[totalCommNum];
		Iterator<String> commIDIt = this.ID2Communities.keySet().iterator();
		int index = 0;
		while (commIDIt.hasNext()) {
			String commID = commIDIt.next();
			AbstractCommunity comm = this.getCommunity(commID);
			int[] subCommSize = comm.getSubCommSize();
			for (int i = 0; i < subCommSize.length; i++) {
				commSizeArray[index] = subCommSize[i];
				index++;
			}

		}
		double[] pccdf = MathTool.percentCCDF(commSizeArray);
		Debug.outn(pccdf, "pccdf");
		return pccdf;

	}

	public AbstractCommunity getCommunity(String communityID) {
		return this.ID2Communities.get(communityID);
	}

	public void addCommunity(AbstractCommunity community) {
		if (ID2Communities.containsKey(community.getCommunityID())) {
			return;
		} else {
			ID2Communities.put(community.getCommunityID(), community);
		}
	}

	public void removeCommunity(String communityID) {
		ID2Communities.remove(communityID);
	}

	/**
	 * 
	 * attach preference of nodes in sub community
	 */
	public void subCommNodeProperty(String filePath, String prefix, String postfix) {
		Iterator<String> commIt = this.ID2Communities.keySet().iterator();

		/* every community */
		while (commIt.hasNext()) {

			String commID = commIt.next();
			Debug.outn("/////////////////community ID " + commID
					+ "///////////////////");

			AbstractCommunity comm = this.ID2Communities.get(commID);
			//Debug.outn(comm.getAllNodeID().size());
			AbstractNetwork net = this.networkAnalysis.getNetwork(commID);
			Iterator<Set<Integer>> subCommIt = comm.getCommunity().iterator();
			int subCommIndex = 1;

			String fileName = filePath + prefix + commID + postfix;
			StringBuffer outputData = new StringBuffer();

			/* every sub community */
			while (subCommIt.hasNext()) {

				Set<Integer> subComm = subCommIt.next();

				Debug.outn("-------subCommIndex " + subCommIndex + " size "
						+ subComm.size());

				Iterator<Integer> subCommNodeIt = subComm.iterator();
				/* every node in sub community */
				while (subCommNodeIt.hasNext()) {
					int nodeID = subCommNodeIt.next();
					// exclude nodeID with 1 degree

					if (net.getNodeDegreeById(nodeID) == 1) {
						continue;
					}

					// neighbour nodes outsite the sub communiyt where nodeID
					// exists
					Set<Integer> ngbOutSubComm = comm.getNeighborsOutSubComm(
							net, nodeID);

					Map<Set<Integer>, Set<Integer>> outCommLink = comm
							.getSubCommMembers(ngbOutSubComm);

					// Debug.out(" 节点ID "+ nodeID+" 度
					// "+net.getNodeDegreeById(nodeID)+
					// " 外度 "+ ngbOutSubComm.size() + " 连接到子团的数量
					// "+outCommLink.size());
					outputData.append(" 节点ID " + nodeID + " 度 "
							+ net.getNodeDegreeById(nodeID) + " 外度 "
							+ ngbOutSubComm.size() + " 所在子团编号 " + subCommIndex
							+ " 所在子团大小 " + subComm.size() + " 连接到子团的数量 "
							+ outCommLink.size());
					Iterator<Set<Integer>> outCommLinkIt = outCommLink.keySet()
							.iterator();

					int[] linkedSubCommSize = new int[outCommLink.size()];
					int index = 0;
					while (outCommLinkIt.hasNext()) {
						Set<Integer> linkedSubComm = outCommLinkIt.next();
						linkedSubCommSize[index] = linkedSubComm.size();
						// Debug.out(" 大小 "+linkedSubComm.size()+" 包含邻居数量
						// "+outCommLink.get(linkedSubComm).size());
					}

					outputData.append(" 平均大小 "
							+ MathTool.average(linkedSubCommSize));

					outputData.append("\n");

				}// every node in sub Community

				subCommIndex++;

			}// ///every sub comm

			FileOperation.saveStringToFile(fileName, outputData.toString(), false);
			Debug.out(outputData);

			Debug.outn("-------------------------------");

		}// ////////////////////////////////////every comm
	}

	/**
	 * 加载以月份为单位的社团和网络数据
	 * 
	 * @param startDate
	 * @param endDate
	 * @param commPath
	 * @param commPrefix
	 * @param commPostfix
	 * @param netPath
	 * @param netPrefix
	 * @param netPostfix
	 */
	public void loadMonthCommAndNetwork(int startDate, int endDate,
			String commPath, String commPrefix, String commPostfix,
			String netPath, String netPrefix, String netPostfix) {
		this.loadMonthCommunities(startDate, endDate, commPath, commPrefix,
				commPostfix);
		this.networkAnalysis.loadMonthNetworks(startDate, endDate, netPath,
				netPrefix, netPostfix);
	}
	
	public void loadSequenceCommAndNetwork(){
		
	}

	/**
	 * 以月份为单位加载若干个网络和社团划分,实际上是网络和其对应的社团划分。
	 * 
	 * @param startDate
	 * @param endDate
	 */
	public void loadMonthCommunities(int startDate, int endDate,
			String commPath, String prefix, String commPostfix) {
		String[] communityFile;

		int startYear = startDate / 100;
		// Debug.outn(startYear);
		int firstMonth = startDate - startYear * 100;

		int endYear = endDate / 100;
		// Debug.outn(endYear);
		int secondMonth = endDate - endYear * 100;

		int num = 12 * (endYear - startYear) + secondMonth - firstMonth + 1;
		communityFile = new String[num];

		String commFileName;

		for (int j = 0; j < num; j++) {

			int month = (firstMonth + j) % 12;
			if (month == 0)
				month = 12;

			int year = startYear + (firstMonth + j - 1) / 12;
			commFileName = commPath + Integer.toString(year * 100 + month)
					+ commPostfix;

			communityFile[j] = commFileName;

			// Debug.outn(commFileName);
			// Debug.outn(networkFileName);
			if (!commPath.equals("")) {
				AbstractCommunity community;
				String commFile;
				commFile = commFileName;
				community = CommunityBuilder
						.creatCommunityFromFile(commFile);
				this.addCommunity(community);
			}

		}////////////

	}// // loadMonthNetworksAndCommunities
	
	public void loadSequenceCommunity(String path,
			int startSeq,int endSeq,int interval ,String prefix, String commPostfix){
		for(int seq= startSeq;seq<=endSeq;seq++){
			AbstractCommunity community;
			community = CommunityBuilder.creatCommunityFromFile(path+prefix+seq+commPostfix);
			this.addCommunity(community);
		}
	}

	public void removeAll() {
		this.ID2Communities.clear();
	}

	/**
	 * community ID that have been loaded
	 * 
	 * @return community ID array
	 */
	public String[] getLoadedCommunitiesID() {
		Iterator<String> idIt = this.ID2Communities.keySet().iterator();
		String[] ids = new String[this.ID2Communities.size()];
		int index = 0;
		while (idIt.hasNext()) {
			ids[index++] = idIt.next();
			Debug.outn("community id: " + ids[index - 1]);
		}
		return ids;
	}

	public void getSubDegree(String netID, String commPath, String commPrefix,
			String commPostfix, String netPath, String netPrefix,
			String netPostfix) {

	}

	/**
	 * 社团中每个子团内节点的度值。
	 * 
	 * @param filePath
	 *            保存到的文件
	 * @param namePostfix
	 *            文件名后缀
	 */
	public void getAndSaveSubCommDegree(String filePath, String namePostfix) {
		String[] commIDs = this.getLoadedCommunitiesID();

		/* 每一个社团 */
		for (int i = 0; i < commIDs.length; i++) {
			StringBuffer subD = new StringBuffer();
			int[][] subCommD = getSubCommDegree(commIDs[i]);

			for (int m = 0; m < subCommD.length; m++) {
				for (int j = 0; j < subCommD[m].length; j++) {
					// Debug.out();
					subD.append(subCommD[m][j] + " ");
				}
				// Debug.outn("");
				subD.append("\n");
			}
			FileOperation.saveStringToFile(filePath + commIDs[i] + namePostfix,
					subD.toString(), false);
			// Debug.outn(subD);
		}// /////////每一个社团

	}
	
	/**
	 * convert community to network
	 * @param community communities
	 * @param orgNet original network
	 * @param savePath
	 * @param fileName
	 * @param postfix
	 * format: 
	 * comA comB linkNum linkNumInComA linkNumInComB NodeNumInComA NodeNumInComB
	 */
	public static void communityToNetwork(AbstractCommunity community ,AbstractNetwork orgNet, String savePath , String fileName, String postfix){
		
		if(fileName==""){
				fileName= community.getCommunityID();
		}
		if(postfix==""){
			postfix=".comtNet";
		}
		
		StringBuffer outputNet = new StringBuffer();
		
		//save community to vector
		Vector<Set<String>> communityV = new Vector<Set<String>>(community.getCommunitySize());
		Iterator<Set<String>> subCommunityIt = community.getCommunity().iterator();
		
		while(subCommunityIt.hasNext()){
			communityV.add(subCommunityIt.next());
		}				
		
		/* get edge number inside one sub community*/
		int []edgesInsideSubComm = new int[community.getCommunitySize()];
		for(int k=0;k<communityV.size();k++){
			edgesInsideSubComm[k] =community.getEdgeNumInsideSubComm( communityV.get(k),orgNet);
		}
		
		/*get edge number between sub communities*/
		for(int i=0;i<communityV.size();i++){
			
			Set<String> subCommI = communityV.get(i);
			
			
			for(int j=0;j<communityV.size();j++){
				if(i>=j){
					continue;
				}
				Set<String> subCommJ = communityV.get(j);
				
				int edgeBetweenIJ = community.getEdgeNumBetweenSubComm(subCommI, subCommJ, orgNet);
				if(edgeBetweenIJ==0){
					continue;
				}
				outputNet.append( i+" "+j+" "+edgeBetweenIJ+ " "
						+edgesInsideSubComm[i]+" "+ edgesInsideSubComm[j]+" "
						 +subCommI.size()+" "+subCommJ.size());
				
				outputNet.append("\n");
			
			}//////////////////for j
			
			Debug.outn("community over "+i);
			
		}//////////////////////////////for i
		
		FileOperation.saveStringToFile(savePath+fileName+postfix, outputNet.toString(), false);
		
	}
	
	public static int[][] communityProp(AbstractCommunity community ,AbstractNetwork orgNet, String savePath , String fileName, String postfix,boolean append){
		Iterator<Set<String>> communities = community.getCommunity().iterator();
		int [][] sumCommunityDegree = new int[community.getCommunitySize()][2];
		int [] commSize=new int[community.getCommunitySize()];
		int i=0;		
		
		while(communities.hasNext()){
			
			Set <String> oneCommunity = communities.next();
			//commSize[i]=oneCommunity.size();
			Iterator <String> nodeIt= oneCommunity.iterator();
			sumCommunityDegree[i][1]=oneCommunity.size();
			while(nodeIt.hasNext()){
				sumCommunityDegree[i][0]=sumCommunityDegree[i][0]+orgNet.getNeighborCount(orgNet.getVertex(nodeIt.next()));
			}
			i++;
		}	
		//Arrays.sort(sumCommunityDegree);
		
		//FileOperation.saveToFile(sumCommunityDegree,savePath+fileName+postfix , append);
		
		return sumCommunityDegree;
		
	}
	
	
	/**
	 * 
	 * convert community to network,sub community as node, links between sub communities as links.
	 */
	public void communityToNetwork(String savePath, String fileName, String postfix){
			
		
			Iterator commIDIt = this.ID2Communities.keySet().iterator();
			
			AbstractNetwork net ;
			String commID;
			
			
			while(commIDIt.hasNext()){
				StringBuffer outputNet = new StringBuffer();
				commID = (String)commIDIt.next();
								
				//need the network data
				//Debug.outn("net num "+ this.networkAnalysis.getNetworkIDSet().size() );
				net = this.networkAnalysis.getNetwork(commID);
				//Debug.outn(net.getNetworkID());
				if(net==null){
					Debug.outn("need network data: "+commID);
					continue;
				}				
					
				AbstractCommunity community = this.getCommunity(commID);
				
				//save community to vector
				Vector<Set<String>> communityV = new Vector<Set<String>>(community.getCommunitySize());
				Iterator<Set<String>> subCommunityIt = community.getCommunity().iterator();
				
				while(subCommunityIt.hasNext()){
					communityV.add(subCommunityIt.next());
				}				
				
				/* get edge number inside one sub community*/
				int []edgesInsideSubComm = new int[community.getCommunitySize()];
				for(int k=0;k<communityV.size();k++){
					edgesInsideSubComm[k] =community.getEdgeNumInsideSubComm( communityV.get(k),net);
				}
				
				
				for(int i=0;i<communityV.size();i++){
					
					Set<String> subCommI = communityV.get(i);
					
					
					for(int j=0;j<communityV.size();j++){
						if(i>=j){
							continue;
						}
						Set<String> subCommJ = communityV.get(j);
						
						int edgeBetweenIJ = community.getEdgeNumBetweenSubComm(subCommI, subCommJ, net);
						if(edgeBetweenIJ==0){
							continue;
						}
						outputNet.append( i+" "+j+" "+edgeBetweenIJ+ " "
								+edgesInsideSubComm[i]+" "+ edgesInsideSubComm[j]+" "
								 +subCommI.size()+" "+subCommJ.size());
						
						outputNet.append("\n");
					
					}//////////////////for j
					
				}//////////////////////////////for i
				
				FileOperation.saveStringToFile(savePath+commID+postfix, outputNet.toString(), false);
				
											
						
//				/* each sub community pair */	
//				Iterator<Set<Integer>> subCommunityAIt = community.getCommunity().iterator();
//				int indexA=1;
//				
//				//each sub community A 
//				while(subCommunityAIt.hasNext()){
//					
//					Set<Integer> subCommA = subCommunityAIt.next();					
//					
//					Iterator<Set<Integer>> subCommunityBIt = community.getCommunity().iterator();
//					int indexB=1;
//					//each sub community B
//					while(subCommunityBIt.hasNext()){
//						
//						Set<Integer> subCommB = subCommunityBIt.next();
//						//exclude self connect
//						if(indexA==indexB){
//							//Debug.outn(subCommA.size()+" "+subCommB.size());
//							indexB++;
//							continue;
//						}
//						
//						int edgeBetweenAB = community.getEdgeNumBetweenSubComm(subCommA, subCommB, net);
//						if( edgeBetweenAB>0){
//							Debug.outn(indexA+" "+indexB+" "+edgeBetweenAB+ " "
//									+edgesInsideSubComm[indexA]+" "+ edgesInsideSubComm[indexB]);
//							
//							outputNet.append( indexA+" "+indexB+" "+edgeBetweenAB+ " "
//									+edgesInsideSubComm[indexA]+" "+ edgesInsideSubComm[indexB]+" "
//									 +subCommA.size()+" "+subCommB.size());
//							
//							outputNet.append("\n");
//						}
//						indexB++;
//					}					
//					//Debug.outn("sub Comm size: "+subComm.size()+" edge Num inside "+edgeNumInside);
//					
//					indexA++;
//				}
				
				
				
			}/////////////end while();
			
	}

	/**
	 * nodes' degree in sub communiteis.
	 * 
	 * @param commID
	 *            community ID
	 * @return 2d integer arrays of nodes in sub communiteis.
	 *         </p>
	 *         row:every sub community.
	 *         </p>
	 *         column:degrees.
	 */
	public int[][] getSubCommDegree(String commID) {
		// Debug.outn(commID);
		AbstractCommunity comm = this.getCommunity(commID);
		AbstractNetwork net = networkAnalysis.getNetwork(commID);
		// comm.getAllNodeID();
		// Debug.outn(net.getNodeIdArray(),"netid");
		Map<Set<Integer>, Vector<Integer>> subDegree = comm
				.getSubCommDegrees(net);
		int subCommCount = subDegree.size();
		int maxSubCommSize = 0;

		Iterator<Set<Integer>> subCommIt = subDegree.keySet().iterator();

		while (subCommIt.hasNext()) {
			int subCommSize = subDegree.get(subCommIt.next()).size();
			if (subCommSize > maxSubCommSize) {
				maxSubCommSize = subCommSize;
			}
		}
		// Debug.outn("max sub comm size "+maxSubCommSize);

		int[][] subCommD = new int[maxSubCommSize][subCommCount];
		for (int i = 0; i < subCommD.length; i++) {
			for (int j = 0; j < subCommD[i].length; j++) {
				subCommD[i][j] = 0;
			}
		}

		subCommIt = subDegree.keySet().iterator();
		int columnIndex = 0;
		while (subCommIt.hasNext()) {
			Vector<Integer> subD = subDegree.get(subCommIt.next());
			for (int i = 0; i < subD.size(); i++) {
				subCommD[i][columnIndex] = subD.get(i);
				// Debug.outn(i+" "+ columnIndex +" "+subD.get(i));
			}

			columnIndex++;
		}
		return subCommD;
	}

	/**
	 * 子团相似度，即两个子团中相同节点数量与所有节点数量的比值
	 * 
	 * @param subCommOne
	 * @param subCommTwo
	 * @return 0-1值。
	 */
	public static float similarityOfSubComm(Set<Integer> subCommOne,
			Set<Integer> subCommTwo) {
		float similarity = 0;
		Set<Set<Integer>> set = new HashSet<Set<Integer>>();
		set.add(subCommOne);
		set.add(subCommTwo);
		similarity = (float) MathTool.intersectionSet(set).size()
				/ (float) MathTool.unionSet(set).size();
		return similarity;
	}

	/**
	 * 从社团中找出最相似的子团
	 * 
	 * @param subComm
	 *            子团
	 * @param comm
	 *            社团
	 * @return 最相似的子团
	 */
	public Set<Integer> getMostSimilaritySubComm(Set<Integer> inputSubComm,
			AbstractCommunity comm) {
		Set<Integer> retSet = new HashSet<Integer>();
		float tempSimi = 0;
		Iterator<Set<Integer>> commIt = comm.getCommunity().iterator();

		while (commIt.hasNext()) {
			Set<Integer> subComm = commIt.next();
			float simi = similarityOfSubComm(inputSubComm, subComm);
			// Debug.outn(simi);
			if (simi > tempSimi) {
				retSet = subComm;
				tempSimi = simi;
			}
		}
		Debug.out(" 最大相似度 " + tempSimi);

		return retSet;
	}

	/**
	 * 社团相似度，两个社团的相似度。
	 * </p>
	 * 社团相似度综合考虑社团大小，子团大小，子团相似度
	 * 
	 * @param cOne
	 * @param cTwo
	 * @return 1，若两个社团完全一样。0，若两个社团完全不一样。
	 */
	public static float similarityOfCommunity(AbstractCommunity commOne,
			AbstractCommunity commTwo) {
		float similarity = 0;
		return similarity;
	}

	/**
	 * 从社团中删除若干个指定的节点
	 * 
	 * @param comm
	 *            社团
	 * @param nodeSet
	 *            要删除的节点ID集合
	 * @return 被删除的节点ID集合
	 */
	public static Set<Integer> deleteNodes(AbstractCommunity comm,
			Set<Integer> nodeSet) {
		Set<Integer> deletedNode = new HashSet<Integer>();

		Iterator<Integer> nodeSetIt = nodeSet.iterator();
		while (nodeSetIt.hasNext()) {
			Integer nodeID = nodeSetIt.next();
		
			if (comm.deleteNode(nodeID)) { // 成功移除
				deletedNode.add(nodeID);
			}
		}

		return deletedNode;
	}

	public String toString() {
		String ret = null;
		return ret;
	}
	
	public static String evolveAnalysis(AbstractNetwork net,AbstractCommunity comm, Set<Integer> birthNodes,
			String path,String name,boolean append){
		
			StringBuffer output = new StringBuffer();
		
			Iterator<Integer> nodeIt = birthNodes.iterator();
		while(nodeIt.hasNext()){
			int nodeID = nodeIt.next();
			int degree = net.getNodeDegreeById(nodeID);
			
			//sub comm contains this node
			Set<Integer> subComm=comm.getSubCommMembers(nodeID);
			//Set<Integer> subComm = comm.getNeighborsInSubComm(net, nodeID);
			
			int subCommSize = subComm.size();
			
			int []degrees = NetworkAnalysis.getDegreesOf(net, subComm);
			int degreeSumOfSubComm = MathTool.sum(degrees);
			Set<Integer> ngbInsideSubComm =comm.getNeighborsInSubComm(net, nodeID);
			Set<Integer> ngbOutsideSubComm =comm.getNeighborsOutSubComm(net, nodeID);
			output.append(degree+" "+subCommSize+" "+degreeSumOfSubComm+" "+ngbInsideSubComm.size()+
			" "+ ngbOutsideSubComm.size()+"\n");
			//Debug.outn();			
			
		}
		FileOperation.saveStringToFile(path+name, output.toString(), true);
		return output.toString();
		
		
	}

}
