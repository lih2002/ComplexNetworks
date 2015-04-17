package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import network.*;
import community.*;
import io.*;
import application.immuine.*;
import java.util.Vector;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.Pair;
/**
 * 处理CAIDA项目原始探测数据
 * 
 * @author 葛新
 * 
 */
public class CAIDARawData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CAIDARawData raw = new CAIDARawData();
		raw.ArkAS("D:\\微云同步盘\\182015630\\practices\\Data\\Input\\01.net",
				"D:\\微云同步盘\\182015630\\practices\\Data\\Output\\01.txt");

	}

	/**
	 * convert IPV6 address node to number label node
	 * 
	 * @param filePath
	 * @param outputPath
	 */
	public static void convertIPV6(String filePath, String IPV6Files, String outputPath) {
		File file = new File(filePath);

		Map<String, Integer> nodeMap = new HashMap<String, Integer>();

		FileReader fr;
		BufferedReader br;
		try {

			// Debug.outn(linkFiles.length);
			fr = new FileReader(new File(filePath));
			br = new BufferedReader(fr);
			
			String nodeA;
			String nodeB;
			String line = br.readLine();
			//Debug.outn(file.getName());
			
			String fileName = "20"+file.getName().split("\\.")[0]+".adj";
			
			while (line != null) { // 读入每一行信息
				String[] stokens = line.split("[\t ]");
				nodeA = stokens[0];
				nodeB= stokens[1];
				//Debug.out(nodeA+" -- "+nodeB);
				
				if(!nodeMap.containsKey(nodeA)){					
					nodeMap.put(nodeA, nodeMap.size()+1);					
				}
				if(!nodeMap.containsKey(nodeB)){
					nodeMap.put(nodeB, nodeMap.size()+1);					
				}
				
				//Debug.outn(nodeMap.size());
				String numberLink=new String()  ;
				numberLink+= nodeMap.get(nodeA).toString() +"\t";
				numberLink+= nodeMap.get(nodeB).toString() ;
				
				numberLink+="\n";
				
				FileOperation.saveStringToFile(outputPath+fileName, numberLink, true);
				line = br.readLine();
			}///////////each line
			
			
		

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ArkAS(String filePath, String outputPath) {
		File file = new File(filePath);

		/* 先分析都有哪些日期的数据，再处理 */
		String[] asLinkFiles = file.list();
		// Debug.outn(asLinkFiles.length);

		String dataID;
		Set<Integer> yearSet = new TreeSet<Integer>();
		/* 共有哪些年的数据 */
		for (int i = 0; i < asLinkFiles.length; i++) { // 每一个文件

			if (new File(asLinkFiles[i]).isDirectory()) { // 不处理子目录
				continue;
			}
			// Debug.outn(asLinkFiles[i]);
			dataID = this.arkDataNameToNetID(asLinkFiles[i]);
			if (dataID != "") {
				yearSet.add(Integer.valueOf(dataID.substring(0, 4)));
			}
		}
		
		Map<Integer, Set<Integer>> linkMap = new TreeMap<Integer, Set<Integer>>(); // 保存一个月的连接数据
		Iterator<Integer> yearIt = yearSet.iterator();

		while (yearIt.hasNext()) {
			int year = yearIt.next();
			formatMonthData(filePath, year, asLinkFiles, linkMap, outputPath);
		}

		Iterator<Integer> keyIt = linkMap.keySet().iterator();
		while (keyIt.hasNext()) {
			int as1 = keyIt.next();
			/*
			Debug
					.outn(as1 + "--"
							+ MathTool.setToString(linkMap.get(as1), " "));
			*/
		}

	}// ///////////////////////ArkAS

	/**
	 * 以月份为单位读取as连接数据
	 * 
	 * @param linkFiles
	 */
	public void formatMonthData(String path, int year, String[] linkFiles,
			Map<Integer, Set<Integer>> link, String outputPath) {
		try {

			for (int month = 1; month <= 12; month++) { // 遍历每个月的数据，从1月到12月，
				FileReader fr;
				BufferedReader br;
				// Debug.outn(linkFiles.length);
				for (int fi = 0; fi < linkFiles.length; fi++) { // 循环处理每一个输入文件

					String netID = Integer.toString(year)
							+ (month < 10 ? "0" + Integer.toString(month)
									: month);
					// Debug.outn("netID "+netID);
					if (netID.equals(arkDataNameToNetID(linkFiles[fi]))) { // 如果存在该月的数据，即处理
						// Debug.outn(netID);

						fr = new FileReader(new File(path + "\\"
								+ linkFiles[fi]));
						br = new BufferedReader(fr);

						String line = br.readLine();
						while (line != null) { // 读入每一行信息

							// Debug.outn(line);

							String[] stokens = line.split("[\t ]");
							// Debug.outn(stokens[0]);
							if (!stokens[0].trim().equals("D")
									|| stokens.length <= 1) { // 只处理直接连接
								line = br.readLine(); // 读下一行
								continue;
							}

							// Debug.outn(stokens[1]+" "+stokens[2]);

							if (stokens[1].contains("_")
									|| stokens[2].contains("_")) { // 不包含private
								// AS
								// Debug.outn(stokens[1] + " " + stokens[2]);
								line = br.readLine(); // 读下一行
								continue;
							}

							Integer AS1;
							Integer AS2;
							try { // 在将字符串转化为字符时有可能出现异常
								AS1 = Integer.valueOf(stokens[1].trim());
								AS2 = Integer.valueOf(stokens[2].trim());
							} catch (Exception e) {
								// e.printStackTrace();
								line = br.readLine(); // 读下一行
								continue;
							}

							/* 若 已经保存AS1 AS2 的无向链接 */
							if (link.containsKey(AS1)
									&& link.get(AS1).contains(AS2)
									|| link.containsKey(AS2)
									&& link.get(AS2).contains(AS1)) {

								line = br.readLine(); // 读下一行
								continue;
							}

							/* 保存AS1 AS2 的无向连接 */
							if (link.containsKey(AS1)) {
								link.get(AS1).add(AS2);
							} else {
								if (link.containsKey(AS2)) {
									link.get(AS2).add(AS1);
								} else {
									Set<Integer> ngb = new TreeSet<Integer>();
									ngb.add(AS2);
									link.put(AS1, ngb);
								}
							}

							line = br.readLine(); // 读下一行
						}// 每一行

						fr.close();
						br.close();
					}// ////////if 存在某一月的数据
				}// ////////////////////每一个输入文件

				/* 保存到文件 */
				if (link.size() > 0) {
					String fileName = outputPath
							+ "\\"
							+ year
							+ (month < 10 ? "0" + Integer.toString(month)
									: month) + "ArkAsLink";
					this.ASLinkToFile(new File(fileName), link);
					Debug.outn("已处理完 " + year + " 年 " + month + " 月数据");
				}

				link.clear();// 清空，保存下一个月的数据
			}// /////////////////////////////////////for every month

		} catch (Exception e) {
			Debug.outn(e);
		}

	}// ///////////////// loadMonthData()

	/**
	 * 根据文件名称得到网络ID，从文件名中提取日期（年月）作为网络ID
	 * 
	 * @param fileName
	 * @return
	 */
	public String arkDataNameToNetID(String fileName) {
		String[] namePiece = fileName.split("[.]");
		// Debug.outn(namePiece[namePiece.length - 1]);
		// Debug.outn("namePiece.length "+namePiece.length);
		if (namePiece.length < 2)
			return "";
		String dataID = (namePiece[namePiece.length - 2]).substring(0, 6);// 提取年月
		// Debug.outn("fileNameToNetID "+dataID);
		return dataID;
	}

	public void ASLinkToFile(File file, Map<Integer, Set<Integer>> link) {

		StringBuffer linkPair = new StringBuffer();
		Iterator<Integer> keyIt = link.keySet().iterator();
		Iterator<Integer> asIt;
		int as1, as2;
		while (keyIt.hasNext()) {
			as1 = keyIt.next();
			asIt = link.get(as1).iterator();
			while (asIt.hasNext()) {
				as2 = asIt.next();
				linkPair.append(as1 + "\t" + as2 + "\n");
			}

		}

		try {
			FileWriter fw = new FileWriter(file, true);

			fw.write(linkPair.toString());

			fw.flush();
			fw.close();

		} catch (IOException ioe) {
			Debug.out("IOException in save String to file");
		}

	}// /////////////////

	public static void formatToPajekNet(AbstractNetwork network,
			String filePath, String fileName, String postfix) {

		if (fileName == "") {
			fileName = network.getNetID();
		}
		if (postfix == "") {
			postfix = ".net";
		}
		StringBuffer netData = new StringBuffer();
		netData.append("*Vertices " + network.getVertexCount() + "\r");

		int maxDegree = MathTool.getMax( network.getAllVertexDegrees());
		
		int count=0;
		for (AbstractV node: network.getAllVertex()) {
			double nodeD = network.getNeighborCount(node);
			double nodeSize = 1 + Math.pow(nodeD, 1.4) / maxDegree;
			netData.append(count++ + 1 + " \"" + node.getID() + "\"" + " 0 "
					// netData.append(i + 1 + " \"" +
					// network.getNodeDegreeByIdx(i) + "\"" + " 0 "
					+ "0 " + "0 " + " x_fact " + nodeSize + " y_fact "
					+ nodeSize + "\r");
		}

		netData.append("*Edges" + "\r");
		
		for (AbstractE edge:network.getAllEdges()) {
			Pair <AbstractV> pair=network.getEndpoints(edge);
			netData.append(pair.getFirst().getID() + " " + (pair.getSecond().getID()) + "\r");
		}
		// Debug.outn(netData.toString());
		FileOperation.saveStringToFile(filePath + fileName + postfix, netData
				.toString(), false);

	}

	public static void formatToPajekNet(SpreadingNetwork immuNet,
			String filePath, String fileName, String postfix) {

		if (fileName == "") {
			// fileName = immuNet.getNetworkID();
		}
		if (postfix == "") {
			postfix = ".net";
		}

		StringBuffer netData = new StringBuffer();
		netData.append("*Vertices " + immuNet.getVertexCount() + "\r");

		/* node size scale is proportional to node degree */
		int maxDegree = MathTool.getMax(immuNet.getAllVertexDegrees());

		String nodeColor = "";
		int count=0;
		for (AbstractV node:immuNet.getAllVertex()){

			// node color
			if (immuNet.getNodeStat(node) == immuNet.NODE_STATE_INFECTIOUS) {
				nodeColor = "Black";
			}
			if (immuNet.getNodeStat(node) == immuNet.NODE_STATE_SUSCEPTIBLE) {
				nodeColor = "White";
			}
			if (immuNet.getNodeStat(node) == immuNet.NODE_STATE_VACCINED) {
				nodeColor = "Green";
			}

			double nodeD = immuNet.getNeighborCount(node);
			double nodeSize = 1 + Math.pow(nodeD, 1.4) / maxDegree;
			netData.append(count++ + 1 + " \"" + immuNet.getNeighborCount(node) + "\""
					+ " 0 " + "0 " + "0 " + " x_fact " + nodeSize + " y_fact "
					+ nodeSize + " ic " + nodeColor + "\r");
		}

		netData.append("*Edges" + "\r");
		
		for (AbstractE edge:immuNet.getAllEdges()) {
			Pair <AbstractV> pair=immuNet.getEndpoints(edge);
			netData.append(pair.getFirst().getID() + " " + (pair.getSecond().getID()) + "\r");
		}
		// Debug.outn(netData.toString());
		FileOperation.saveStringToFile(filePath + fileName + postfix, netData
				.toString(), false);

	}

	/**
	 * convert community network to Pajek format
	 * 
	 * @param commNet
	 * @param filePath
	 * @param fileName
	 * @param postfix
	 */
	public static void formatToPajekNet(CommunityNetwork commNet,
			String filePath, String fileName, String postfix) {
	
		if (fileName == "") {
			fileName = commNet.getNetID();
		}
		if (postfix == "") {
			postfix = ".net";
		}
		
		int nodeNum=commNet.getVertexCount();

		StringBuffer netData = new StringBuffer();
		netData.append("*Vertices " + nodeNum + "\r");

		// int maxDegree = commNet.getMaxDegree();

		/* node node weight */
		double avgNodeNodeWeight = 0;
		double maxNodeNodeWeight = 0;
		
		
		int[] nodeNodeWeight = new int[nodeNum];
		int k=0;
		for(AbstractV eachNode:commNet.getVertices()){
			nodeNodeWeight[k++]=commNet.getNodeNodeWeight(eachNode);
		}
		
	
		int count=0;
		for(AbstractV eachNode:commNet.getVertices()){
			nodeNodeWeight[count] = commNet.getNodeNodeWeight(eachNode);
			if (nodeNodeWeight[count] > maxNodeNodeWeight) {
				maxNodeNodeWeight = nodeNodeWeight[k];
			}
			count++;
		}
		
		avgNodeNodeWeight = MathTool.average(nodeNodeWeight);

		/* node link weight */;
		int[] nodeLinkWeight = new int[nodeNum];
		count=0;
		for(AbstractV eachNode:commNet.getVertices()){
			nodeLinkWeight[count] = commNet
			.getNodeLinkWeight(eachNode);
			count++;
		}
			

		String nodeColor = "";
		for (int i = 0; i < commNet.getVertexCount(); i++) {

			if (commNet.getVertexCount() < PajekColor.length) {
				nodeColor = PajekColor[commNet.idx2Id(i)];
			}
			double nodeD = commNet.getNodeDegreeById(commNet.idx2Id(i));

			// double nodeSize = 1 + Math.pow(nodeD, 1.4) / maxDegree;
			// node size is proportional to node node weight
			double nodeSize = 1 + Math.pow(nodeNodeWeight[i], 1.2)
					/ maxNodeNodeWeight;

			netData.append((i + 1) + " \"" + "N:" + nodeNodeWeight[i] + " L:"
					+ nodeLinkWeight[i] + "\"" + " 0 " + "0 " + "0 "
					+ " x_fact " + nodeSize + " y_fact " + nodeSize + " ic "
					+ nodeColor + "\r");
		}
		
		
		count=0;
		for(AbstractV eachNode:commNet.getVertices()){
			if (commNet.getVertexCount() < PajekColor.length) {
				nodeColor = PajekColor[commNet.idx2Id(i)];
			}
			double nodeD = commNet.getNodeDegreeById(commNet.idx2Id(i));

			// double nodeSize = 1 + Math.pow(nodeD, 1.4) / maxDegree;
			// node size is proportional to node node weight
			double nodeSize = 1 + Math.pow(nodeNodeWeight[count], 1.2)
					/ maxNodeNodeWeight;

			netData.append((count + 1) + " \"" + "N:" + nodeNodeWeight[count] + " L:"
					+ nodeLinkWeight[count] + "\"" + " 0 " + "0 " + "0 "
					+ " x_fact " + nodeSize + " y_fact " + nodeSize + " ic "
					+ nodeColor + "\r");
			count++;
		}
		

		/* output edge */
		netData.append("*Edges" + "\r");
		Vector<AbstractE> edges = commNet.getAllEdges();

		double[] allWeight = new double[edges.size()];
		double maxEdgeW = 0;
		for (int i = 0; i < edges.size(); i++) {
			AbstractE e = edges.get(i);
			allWeight[i] = e.getWeight();
			if (allWeight[i] > maxEdgeW) {
				maxEdgeW = allWeight[i];
			}
		}

		double avgWeight = MathTool.average(allWeight);
		for (int j = 0; j < edges.size(); j++) {
			AbstractE e = edges.get(j);
		
			double edgeWidth = 1 + Math.pow(allWeight[j], 1.4) / maxEdgeW;
			netData.append((e.AIdx + 1) + " " + (e.BIdx + 1) + " " + edgeWidth
					+ " c " + "black" + " l " + "\"" + allWeight[j] + "\""
					+ "\r");
		}
		// Debug.outn(netData.toString());
		FileOperation.saveStringToFile(filePath + fileName + postfix, netData
				.toString(), false);

	}

	static String[] PajekColor = new String[] { "GreenYellow", "Fuchsia",
			"JungleGreen", "Lavender", "Dandelion", "OliveGreen", "Violet",
			"Gray75", "Red", "Mahogany", "CornflowerBlue", "ForestGreen",
			"Brown", "Goldenrod", "Peach", "YellowOrange", "LSkyBlue",
			"Orange", "RawSienna", "BurntOrange", "MidnightBlue",
			"Bittersweet", "RedOrange", "White", "Gray15", "BrickRed",
			"OrangeRed", "Salmon", "CarnationPink", "Magenta", "Rhodamine",
			"Gray80", "Mulberry", "RedViolet", "LightPurple", "Orchid",
			"DarkOrchid", "Purple", "Plum", "WildStrawberry", "RoyalPurple",
			"BlueViolet", "Periwinkle", "CadetBlue", "RoyalBlue", "Blue",
			"Cerulean", "ProcessBlue", "SkyBlue", "Turquoise", "TealBlue",
			"Aquamarine", "Gray95", "BlueGreen", "Emerald", "Green",
			"PineGreen", "LimeGreen", "YellowGreen", "SpringGreen", "Apricot",
			"Sepia", "Tan", "Gray", "Black", "LightYellow", "LightCyan",
			"LightMagenta", "LightGreen", "LightOrange", "Canary",
			"LFadedGreen", "Pink", "Gray05", "Gray10", "Gray20", "Melon",
			"Gray25", "Gray30", "Gray35", "Gray40", "Gray45", "Gray55",
			"Thistle", "Gray60", "Gray65", "Gray70", "Yellow", "NavyBlue",
			"Gray85", "Gray90", };

	
	public static void formatToPajekNet(GeneralCommunity commNet,
			AbstractNetwork network, String filePath, String fileName,
			String postfix) {

		boolean coloable = false;
		if (commNet.getCommunitySize() <= PajekColor.length) {
			coloable = true;
		}

		if (fileName == "") {
			fileName = network.getNetID();
		}
		if (postfix == "") {
			postfix = ".net";
		}

		StringBuffer netData = new StringBuffer();
		netData.append("*Vertices " + network.getVertexCount() + "\r");

		int maxDegree =MathTool.getMax(network.getAllVertexDegrees()) ;
		String nodeColor = "";
		
		for(AbstractV node:network.getVertices()){
			
		}
		
		
		for (int i = 0; i < network.getVertexCount(); i++) {

			if (coloable) {
				int nodeInCommIndex = commNet.nodesInCommunityIndex(network
						.idx2Id(i));
				if (nodeInCommIndex != -1) {
					nodeColor = " ic " + PajekColor[nodeInCommIndex];
				}
			}

			double nodeD = network.getNodeDegreeById(network.idx2Id(i));
			double nodeSize = 1 + Math.pow(nodeD, 1.4) / maxDegree;
			// nodeSize = 1;
			// Debug.outn(nodeSize);
			netData.append(i + 1 + " \"" + network.idx2Id(i) + "\"" + " 0 "
					+ "0 " + "0 " + " x_fact " + nodeSize + " y_fact "
					+ nodeSize + " " + nodeColor + "\r");
		}

		/* output edges */
		netData.append("*Edges" + "\r");
		Vector<AbstractE> edges = network.getAllEdges();

		double[] allWeight = new double[edges.size()];
		for (int i = 0; i < edges.size(); i++) {
			AbstractE e = edges.get(i);
			allWeight[i] = e.getWeight();
		}

		String edgeColor = "";
		double avgWeight = MathTool.average(allWeight);
		for (int j = 0; j < edges.size(); j++) {
			/* random color for edge */

			edgeColor = PajekColor[MathTool.randomInt(0, PajekColor.length, 1)[0]];

			AbstractE e = edges.get(j);
			double edgeWidth = (e.getWeight() / avgWeight);
			netData.append((e.AIdx + 1) + " " + (e.BIdx + 1) + " " + edgeWidth
					+ " c " + edgeColor + "\r");
		}
		// Debug.outn(netData.toString());
		FileOperation.saveStringToFile(filePath + fileName + postfix, netData
				.toString(), false);

	}// ///////////////////////////////////////////////////////////////



}
