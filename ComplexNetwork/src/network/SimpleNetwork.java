/**
 * 
 */
package network;

import edu.uci.ics.jung.graph.*;

import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import io.FileOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import tools.Debug;
import tools.MathTool;

/**
 * @author gexin
 * 
 */
public class SimpleNetwork extends UndirectedSparseGraph<String, String> {

	protected static String netID;

	public String getNetworkID() {
		return netID;
	}

	public void setNetID(String netID) {
		SimpleNetwork.netID = netID;
	}

	/**
	 * add edge linking node A and node B
	 * 
	 * @param e
	 * @param nA
	 * @param nB
	 * @return
	 */
	public boolean addEdge(String nA,String nB){
		
		addEdge(nA+"-"+nB, nA,nB );
		
		return false;
	}

	public boolean saveNetToFile(String savePath) {
		return saveNetToFile(savePath, "", "");

	}

	public boolean saveNetToFile(String savePath, String saveName) {
		return saveNetToFile(savePath, saveName, "");
	}

	public boolean saveNetToFile(String savePath, String saveName,
			String linkSplit) {

		if (saveName.equals("")) {
			saveName = this.getNetworkID();
		}

		if (linkSplit.equals("")) {

			linkSplit = "\t";

		}

		String filePathName = savePath + saveName;
		try {

			FileWriter fw = new FileWriter(new File(filePathName));
			Vector<String> allEdges = getAllEdges();

			for (String edge : allEdges) {

				String nodeA = this.getEndpoints(edge).getFirst();
				String nodeB = this.getEndpoints(edge).getSecond();

				fw.write(nodeA + linkSplit + nodeB + FileOperation.LINE_BREAK);

			}

			fw.flush();
			fw.close();

		} catch (IOException ioe) {

			return false;
		}

		return true;
	}

	public boolean loadNetworkData(String filePathName) {
		// file name as network ID

		return loadNetworkData(filePathName, "");
	}

	public boolean loadNetworkData(String filePathName, String netID) {

		FileReader fr;
		BufferedReader br;

		// file name as network ID
		if (netID.equals("")) {
			netID = (new File(filePathName)).getName();
		}

		try {

			fr = new FileReader(new File(filePathName));
			br = new BufferedReader(fr);

			String oneLine = br.readLine();

			// Debug.outn(oneLine);

			while (oneLine != null) { // read line. one edge in one line.

				String[] stokens = oneLine.split("[\t ]");

				if (stokens.length > 1) {

					String v1 = stokens[0].trim();
					String v2 = stokens[1].trim();

					addEdge(v1 + "-" + v2, v1, v2);
					// Debug.outn(oneLine);

				}
				oneLine = br.readLine();
			}

			fr.close();
			br.close();

		} catch (IOException ioe) {

			return false;
		}

		return true;
	}

	public String getRandomNode() {
		int random = MathTool.randomInt(0, getVertexCount(), 1)[0];
		Iterator<String> nodes = getVertices().iterator();
		String node = null;
		while (nodes.hasNext()) {
			node = nodes.next();
			if (random == 0) {
				break;
			} else {
				random--;
			}
		}

		return node;
	}

	public Vector<String> getAllNodes() {

		Vector<String> nodes = new Vector<String>();

		Iterator<String> allNodesItor = this.getVertices().iterator();
		while (allNodesItor.hasNext()) {
			nodes.add(allNodesItor.next());
		}
		return nodes;
	}

	public String getVertex(String node) {
		String findedNode = null;

		for (String n : getVertices()) {
			if (n.equals(node)) {
				findedNode = n;
				break;
			}
		}

		return findedNode;
	}

	public boolean containLink(String nodeA, String nodeB) {
		boolean r = false;

		for (String ngb : this.getNeighbors(nodeA)) {
			if (ngb.equals(nodeB))
				r = true;
		}
		return r;
	}

	public Vector<String> getAllEdges() {
		Vector<String> nodes = new Vector<String>();

		Iterator<String> allEdgeItor = this.getEdges().iterator();
		while (allEdgeItor.hasNext()) {
			nodes.add(allEdgeItor.next());
		}
		return nodes;
	}

	public Vector<String> getNeighborVertices(String node) {
		Vector<String> nodes = new Vector<String>();

		Iterator<String> allNodesItor = this.getNeighbors(node).iterator();
		while (allNodesItor.hasNext()) {
			nodes.add(allNodesItor.next());
		}

		return nodes;
	}

	/*
	 * 所有节点的度
	 */
	public int[] getAllVertexDegrees() {
		int[] degrees = new int[this.getVertexCount()];
		Iterator<String> allNodeIter = getVertices().iterator();
		int i = 0;
		while (allNodeIter.hasNext()) {
			degrees[i] = this.getNeighborCount(allNodeIter.next());
			i++;
		}

		return degrees;

	}

	public Set<String> getVertexWithDegree(int degree) {

		Iterator<String> vIt = getVertices().iterator();
		Set<String> vertices = new HashSet<String>();

		while (vIt.hasNext()) {
			String v = vIt.next();
			if (getNeighborCount(v) == degree) {
				vertices.add(v);
			}
		}

		return vertices;

	}

	@Override
	public String toString() {
		return "AbstractNetwork [getEdges()=" + getEdges() + ", getVertices()="
				+ getVertices() + ", getEdgeCount()=" + getEdgeCount()
				+ ", getVertexCount()=" + getVertexCount()
				+ ", getDefaultEdgeType()=" + getDefaultEdgeType()
				+ ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
