/**
 * 
 */
package spectrum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import tools.*;
import network.*;
import community.*;
import io.FileOperation;
//import cern.colt.matrix.*;
//import cern.colt.matrix.impl.*;
//import cern.colt.matrix.linalg.*;

/**
 * @author ge xin Matrix for network.
 * 
 */
public class NetMatrix {

	/**
	 * @param args
	 *            just for debug
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NetMatrix netM = new NetMatrix();
		netM.test();
	}

	public void test() {
		String infile = "d:\\netdata\\200001SkitterAsLink";
		// infile = "d:\\tt.txt";
	//	AbstractNetwork net = new ASTopNetwork();
	//	NetworkDataLoader.loadNetworkFromFile(net, infile);

	//	Debug.outn(net.getNetworkSize());

	//	this.saveNetworkLaplacianMatrixToFile(net, "d:\\netMatrix\\", "",
	//			"laplacMat");
		// DoubleMatrix1D spectrum = this.computeAlgConnectivity(netM);
		// for(int i = 0;i< spectrum.size();i++){
		// Debug.outn(spectrum.get(i));
		// }

		Debug.outn("end");
	}

	/**
	 * Get the network matrix from network object.
	 * </p>
	 * If the size of netowrk exceeds certain valure(maybe 2000 less or more),
	 * </p>
	 * it produces 'out of memory error'.
	 * 
	 * @param Abstract
	 *            network
	 * @return network matrix
	 */
//	public DoubleMatrix2D getNetworkMatrix(AbstractNetwork net) {
//		// Debug.outn("matrix start");
//		int netSize = net.getNetworkSize();
//		DenseDoubleMatrix2D laplacian = new DenseDoubleMatrix2D(netSize,
//				netSize);
//
//		for (int i = 0; i < netSize; i++) {
//			for (int j = 0; j < netSize; j++) {
//				double value = 0;
//
//				if (i == j) {
//					value = net.getNodeDegreeByIdx(i); // degree;
//				} else if (net.findEdge(i, j) != null) {
//					value = -1;
//				} else
//					value = 0;
//				laplacian.setQuick(i, j, value);
//			}
//		}
//		return laplacian;
//	}
	
	public static void saveNetworkSparseNormalizedLaplacianMatrixToFile(AbstractNetwork net,
			String filePath, String fileName, String postfix) {
		
		FileOperation fileOpt = new FileOperation();

		int netSize = net.getVertexCount();
		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (postfix == "") {
			postfix = ".NLapMat";
		}
		
		StringBuffer columnValue = new StringBuffer();
		columnValue.append("");

		Iterator<AbstractE> allEdges = net.getAllEdges().iterator();
		
		for(AbstractE edge:net.getEdges()){
			
		}
		
		
		int edgeCount=1;
		while(allEdges.hasNext()){
			AbstractE oneEdge=allEdges.next();
			if (edgeCount % 100 == 0) {
				Debug.outn("output edges number " + edgeCount);
			}
			
			int iDeg = net.getNeighborCount( net.getEndpoints(oneEdge).getFirst() );
			int jDeg = net.getNeighborCount( net.getEndpoints(oneEdge).getSecond() );
			
			float value = -1 / ((float) Math.sqrt(iDeg * jDeg));
			
			columnValue.append((allEdges.get(i).AIdx + 1) + "\t"
					+ (allEdges.get(i).BIdx + 1) + "\t" +value + "\n");
			columnValue.append((allEdges.get(i).BIdx + 1) + "\t"
					+ (allEdges.get(i).AIdx + 1) + "\t" + value + "\n");
			
		}
		
		
		
		for (int i = 0; i < allEdges.size(); i++) {
			if (i % 100 == 0) {
				Debug.outn("output edges number " + i);
			}
			
			int iDeg = net.getNodeDegreeByIdx(allEdges.get(i).AIdx);
			int jDeg = net.getNodeDegreeByIdx(allEdges.get(i).BIdx);

			float value = -1 / ((float) Math.sqrt(iDeg * jDeg));
			columnValue.append((allEdges.get(i).AIdx + 1) + "\t"
					+ (allEdges.get(i).BIdx + 1) + "\t" +value + "\n");
			columnValue.append((allEdges.get(i).BIdx + 1) + "\t"
					+ (allEdges.get(i).AIdx + 1) + "\t" + value + "\n");
		}

		for (int i = 0; i < netSize; i++) {

			//int degree = net.getNodeDegreeByIdx(i); // degree;

			columnValue.append((i+1) + "\t" + (i+1) + "\t" + 1+"\n");

		}

		fileOpt.saveStringToFile(filePath + fileName + postfix, columnValue
				.toString(), true);
		columnValue.delete(0, columnValue.length());
		
		
	}//////////////////////////////////////////////

	public static void saveNetworkSparseLaplacianMatrixToFile(AbstractNetwork net,
			String filePath, String fileName, String postfix) {
		// Debug.outn("transform net "+net.getNetworkID()+" ```");
		FileOperation fileOpt = new FileOperation();

		int netSize = net.getNetworkSize();
		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (postfix == "") {
			postfix = ".lapMat";
		}

		StringBuffer columnValue = new StringBuffer();
		columnValue.append("");

		Vector<AbstractE> allEdges = net.getEdgeArray();
		
		for (int i = 0; i < allEdges.size(); i++) {
			if (i % 100 == 0) {
				Debug.outn("output edges number " + i);
			}
			columnValue.append((allEdges.get(i).AIdx + 1) + "\t"
					+ (allEdges.get(i).BIdx + 1) + "\t" + "-1" + "\n");
			columnValue.append((allEdges.get(i).BIdx + 1) + "\t"
					+ (allEdges.get(i).AIdx + 1) + "\t" + "-1" + "\n");
		}

		for (int i = 0; i < netSize; i++) {

			int degree = net.getNodeDegreeByIdx(i); // degree;

			columnValue.append((i+1) + "\t" + (i+1) + "\t" + degree+"\n");

		}

		fileOpt.saveStringToFile(filePath + fileName + postfix, columnValue
				.toString(), true);
		columnValue.delete(0, columnValue.length());

	}

	/**
	 * Save the network laplacian matrix into file. Avoiding the 'out of memory
	 * error' problem.
	 * 
	 * @param net
	 * @param filePathName
	 */
	public void saveNetworkLaplacianMatrixToFile(AbstractNetwork net,
			String filePath, String fileName, String postfix) {
		// Debug.outn("transform net "+net.getNetworkID()+" ```");
		FileOperation fileOpt = new FileOperation();

		int netSize = net.getNetworkSize();
		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (postfix == "") {
			postfix = ".lapMat";
		}

		StringBuffer oneRow = new StringBuffer();
		oneRow.append("");
		int value;

		for (int i = 0; i < netSize; i++) {
			value = 0;
			for (int j = 0; j < netSize; j++) {

				if (i == j) {
					value = net.getNodeDegreeByIdx(i); // degree;
				} else if (net.findEdge(i, j) != null) {
					value = -1;
				} else
					value = 0;
				oneRow.append(value);
				oneRow.append(" ");
			}
			oneRow.append("\r\n");
			if (i % 100 == 0) {
				Debug.outn(net.getNetID() + " line " + i);
			}
			fileOpt.saveStringToFile(filePath + fileName + postfix, oneRow
					.toString(), true);
			oneRow.delete(0, oneRow.length());
		}
	}
	
	/**
	 * community to matrix. column:community. If node is in community, element=1,else 0.
	 * @param community
	 * @param filePath
	 * @param fileName
	 * @param postfix
	 */
	public static void saveCommunityMatrixToFile(GeneralCommunity community,
			String filePath, String fileName, String postfix ){
		
		if (fileName == "") {
			fileName = community.getCommunityID();
		}
		if (postfix == "") {
			postfix = ".modMat.commMat";
		}
		
		int communityNumber = community.getCommunitySize();
		Object[] allID = community.getAllNodeID().toArray();
		int [] allIDs= new int[allID.length];
		for(int i=0;i<allID.length;i++){
			allIDs[i]=((Integer)allID[i]).intValue();
		}
		Arrays.sort(allIDs);
		//Debug.outn(allIDs,"all id");
		
		int[][] matrix=new int[allIDs.length][communityNumber];
		
		Iterator<Set<Integer>> subCommIt =community.getCommunity().iterator();
		int commIndex=0;
		while(subCommIt.hasNext()){
			//in one community
			Set<Integer> subComm = subCommIt.next();
			//Debug.outn(subComm.toArray()[0]);
			for(int j=0;j<allIDs.length;j++){
				if(subComm.contains(allIDs[j])){
					matrix[j][commIndex]=1;
				}else{
					matrix[j][commIndex]=0;
				}
			}
			commIndex++;
		}
		StringBuffer matrixStr = new StringBuffer();
		
		for(int n=0;n<allIDs.length;n++){
			for(int m=0;m<communityNumber;m++){
					matrixStr.append(matrix[n][m]+" ");
			}
			matrixStr.append("\n");
		}
		
		FileOperation.saveStringToFile(filePath+fileName+postfix, matrixStr.toString(), false);
		
	}
	
	/**
	 * get modularity matrix of network
	 * modularity matrix: M = Adjacency matrix - d*d'/2L 
	 * @param net
	 * @param filePath
	 * @param fileName
	 * @param postfix
	 */
	public static void saveModularityMatrixToFile(AbstractNetwork net,AbstractCommunity comm,
			String filePath, String fileName, String postfix) {
		// Debug.outn("transform net "+net.getNetworkID()+" ```");
		FileOperation fileOpt = new FileOperation();

		int netSize = net.getNetworkSize();
		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (postfix == "") {
			postfix = ".modMat";
		}
		
		int L = net.getEdgesNum();

		StringBuffer oneRow = new StringBuffer();
		oneRow.append("");
		float element;

		double modularity=0;
		
		int[]allIDs = net.getNodeIdArray();
		Arrays.sort(allIDs);
		
		Debug.outn(allIDs," allID");
		
		for (int i = 0; i < allIDs.length; i++) {
			element = 0;
			
			for (int j = 0; j < allIDs.length; j++) {
				
				int iID = allIDs[i];
				int jID = allIDs[j];				
				
				int s=0;
				if( comm.getSubCommMembers(iID).contains(jID) ){
					
					Debug.outn(iID+" "+jID);
					s=1;
				}else{
					
				}
				
				int iDeg = net.getNodeDegreeById(iID);
				int jDeg = net.getNodeDegreeById(jID);
				
				if (i == j) {
					element =(0-(float)iDeg*jDeg/2/L); // degree;
					//element =0;
				} else if (net.findEdge(net.id2Idx(iID), net.id2Idx(jID)) != null) {
					
					//Debug.outn(" "+iID+" - "+jID+" degree "+iDeg+" "+jDeg);
										
					element = (1-(float)iDeg*jDeg/2/L);
					
					//Debug.outn("mod ele "+element);
					
				} else{
					//bug.outn(" "+iID+"   "+jID+" degree "+iDeg+" "+jDeg);
					
					element =( 0-(float)iDeg*jDeg/2/L);
					
					//Debug.outn("modmarity mat e  "+element);	
					
				}
				
				modularity+=element*s;
				oneRow.append(element);
				oneRow.append(" ");
			
			}//////column 		
			
			oneRow.append("\r\n");
			
			if (i % 100 == 0) {
				Debug.outn(net.getNetID() + " line " + i);
			}
			fileOpt.saveStringToFile(filePath + fileName + postfix, oneRow
					.toString(), true);
			oneRow.delete(0, oneRow.length());
			
		}///////////each node,row
		
		modularity=modularity/2/L;
		Debug.outn("modularity "+modularity);
	}
	
		
	public static double matrixModularity(AbstractNetwork net,AbstractCommunity comm) {
		// Debug.outn("transform net "+net.getNetworkID()+" ```");
		FileOperation fileOpt = new FileOperation();

		int netSize = net.getNetworkSize();
		
		
		int L = net.getEdgesNum();	
		//Debug.outn("N "+netSize+" L "+L);
		float element=0;

		double modularity=0;
		
		int[]allIDs = net.getNodeIdArray();
		Arrays.sort(allIDs);
		int commIndex=0;
		Iterator<Set<Integer>> subComm = comm.getCommunity().iterator();
		while(subComm.hasNext()){
			//Debug.outn(commIndex++);
			Object[] commNodes = subComm.next().toArray();
			//Debug.outn("a sub comm size "+commNodes.length);
			for(int p=0;p<commNodes.length;p++){
				element=0;
				for(int q=0;q<commNodes.length;q++){
					
					int iID = (Integer)commNodes[p];
					int jID = (Integer)commNodes[q];
					
					int iDeg = net.getNodeDegreeById(iID);
					int jDeg = net.getNodeDegreeById(jID);
					
					if (net.findEdge(net.id2Idx(iID), net.id2Idx(jID)) != null) {
						
						//Debug.outn(" "+iID+" - "+jID+" degree "+iDeg+" "+jDeg);
						if(p==q){
							continue;
							//element = (0-(float)iDeg*jDeg/2/L);
						}else{
							element = (1-(float)iDeg*jDeg/2/L);
							
						}
						//Debug.outn("mod ele "+element);
						
					}else{
						if(p==q)
							continue;
						element = (0-(float)iDeg*jDeg/2/L);
					}
					
					//Debug.outn("node "+iID+"-"+jID+" "+element);
					modularity+=element;
					
				}
				
			}		
			
		}//while
		
		modularity=modularity/2/L;
		//Debug.outn(modularity);
		return modularity;
		
	}

	public void saveNormalizedLaplacianMatrixToFile(AbstractNetwork net,
			String filePath, String fileName, String postfix) {
		// Debug.outn("transform net "+net.getNetworkID()+" ```");
		FileOperation fileOpt = new FileOperation();

		int netSize = net.getNetworkSize();
		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (postfix == "") {
			postfix = ".NLapMat";
		}

		StringBuffer oneRow = new StringBuffer();
		oneRow.append("");
		float value;

		for (int i = 0; i < netSize; i++) {
			value = 0;
			for (int j = 0; j < netSize; j++) {

				if (i == j) {
					value = 1; //
				} else if (net.findEdge(i, j) != null) {
					int iDeg = net.getNodeDegreeByIdx(i);
					int jDeg = net.getNodeDegreeByIdx(j);

					value = -1 / ((float) Math.sqrt(iDeg * jDeg));
				} else
					value = 0;
				oneRow.append(value);
				oneRow.append(" ");
			}
			oneRow.append("\r\n");
			if (i % 100 == 0) {
				Debug.outn(net.getNetID() + " line " + i);
			}
			fileOpt.saveStringToFile(filePath + fileName + postfix, oneRow
					.toString(), true);
			oneRow.delete(0, oneRow.length());
		}
	}

	public static void saveNetworkSparseAdjacentMatrix(AbstractNetwork net,
			String filePath, String fileName, String postfix) {
		FileOperation fileOpt = new FileOperation();

		int netSize = net.getNetworkSize();

		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (postfix == "") {
			postfix = ".adjMat";
		}

		Vector<AbstractE> allEdges = net.getEdgeArray();
		StringBuffer link = new StringBuffer();
		for (int i = 0; i < allEdges.size(); i++) {
			if (i % 100 == 0) {
				Debug.outn("output edges number " + i);
			}
			link.append((allEdges.get(i).AIdx + 1) + "\t"
					+ (allEdges.get(i).BIdx + 1) + "\t" + 1 + "\n");
			link.append((allEdges.get(i).BIdx + 1) + "\t"
					+ (allEdges.get(i).AIdx + 1) + "\t" + 1 + "\n");
		}

		fileOpt.saveStringToFile(filePath + fileName + postfix,
				link.toString(), true);

	}

	/**
	 * Save the network adjacent matrix into file. Avoiding the 'out of memory
	 * error' problem.
	 * 
	 * @param net
	 * @param fileName
	 *            TODO
	 * @param filePathName
	 */
	public static void saveNetworkAdjacentMatrixToFile(AbstractNetwork net,
			String filePath, String fileName, String postfix) {
		// Debug.outn("transform net "+net.getNetworkID()+" ```");
		FileOperation fileOpt = new FileOperation();

		int netSize = net.getNetworkSize();

		if (fileName == "") {
			fileName = net.getNetID();
		}
		if (postfix == "") {
			postfix = ".adjMat";
		}

		StringBuffer oneRow = new StringBuffer();
		oneRow.append("");
		int value;

		for (int i = 0; i < netSize; i++) {
			value = 0;
			for (int j = 0; j < netSize; j++) {

				if (net.findEdge(i, j) != null) {
					value = 1;
				} else
					value = 0;
				oneRow.append(value);
				oneRow.append(" ");
			}
			oneRow.append("\r\n");
			if (i % 100 == 0) {
				Debug.outn(net.getNetID() + " line " + i);
			}
			fileOpt.saveStringToFile(filePath + fileName + postfix, oneRow
					.toString(), true);
			oneRow.delete(0, oneRow.length());
		}
	}

//	public DoubleMatrix1D computeAlgConnectivity(DoubleMatrix2D netMatrix) {
//		// Compute the spectrum
//		EigenvalueDecomposition e = new EigenvalueDecomposition(netMatrix);
//		// Sort the eigenvalues
//		DoubleMatrix1D eigenValue = e.getRealEigenvalues().viewSorted();
//		return eigenValue;
//		// Get the second smallest eigenvalue of Q
//		// double algConnectivity = eigenValue.get(1);
//		// Get the largest eigenvalue of Q
//		// double spectralDiameter = eigenValue.get(eigenValue.size()-1);
//	}

//	public DoubleMatrix1D computeAlgConnectivity(AbstractNetwork network) {
//		return this.computeAlgConnectivity(this.getNetworkMatrix(network));
//	}

	public static double getLargestEigenValue(String filePathName,boolean absoluteValue) {

		String lE = "";
		Vector <Double> eigV = new Vector<Double>();
		try {
			FileReader fr = new FileReader(new File(filePathName));
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			String evStr = "";
			while (line != null) {
				Double ev = Double.valueOf(evStr = line.trim());
				if(absoluteValue && ev<0){
					ev=-ev;
				}
				eigV.add(ev);
				line = br.readLine();
				break;
			}// 处理行
			;
			br.close();
			fr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		double []eigArray = new double[eigV.size()];
		for(int j=0;j<eigV.size();j++){
			eigArray[j]=eigV.get(j);
		}
		
		

		return MathTool.getMax(eigArray);
	}
	
	public static double [] getSortedEigenValue(String filePathName,boolean absoluteValue) {

		String lE = "";
		Vector <Double> eigV = new Vector<Double>();
		try {
			FileReader fr = new FileReader(new File(filePathName));
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			String evStr = "";
			while (line != null) {
				Double ev = Double.valueOf(evStr = line.trim());
				if(absoluteValue && ev<0){
					ev=-ev;
				}
				eigV.add(ev);
				line = br.readLine();
	
			}// 处理行
			;
			br.close();
			fr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		double []eigArray = new double[eigV.size()];
		for(int j=0;j<eigV.size();j++){
			eigArray[j]=eigV.get(j);
		}
		Arrays.sort(eigArray);
		

		return eigArray;
	}
	
	public static double getNormalizedEffectiveResistance(String filePathName) {
		
		double [] sortedEigV = NetMatrix.getSortedEigenValue(filePathName,true);
		
		if( sortedEigV[1]==0 ){ // network is disconncted, return 0 directly.
			return 0;
		}
		
		double effResistance=0;
		for(int i=1;i<sortedEigV.length;i++){
			effResistance+= 1/sortedEigV[i];
		}
		
		return effResistance*2/(sortedEigV.length-1);
		
				
	}
	
	
	public static double getEffectiveResistance(String filePathName) {

		double effResistance=0;
		try {
			FileReader fr = new FileReader(new File(filePathName));
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			String maxEV = "";
			while (line != null) {
				//Debug.outn(Double.valueOf(line));
				double ev= Double.valueOf(line).doubleValue();
				if(ev==0){
					return 0;
				}
//				if(ev==0 ){
//					line = br.readLine();
//					continue;
//				}
				effResistance+= 1/ev;
				Debug.outn(ev+" "+ effResistance);
				line = br.readLine();
				
			}// 处理行
			
			br.close(); 
			fr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return effResistance;
	}

}
