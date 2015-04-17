package linkPrediction;

import edu.uci.ics.jung.graph.util.*;

import linkPrediction.algorithm.*;
import network.*;

import java.util.*;
import java.text.DecimalFormat;

import tools.Debug;
import tools.MathTool;
import tools.RandomOperation;

/**
 * @author gexin
 * 
 */
public class LinkPrediction {

	boolean debug = true;

	// private Map<AbstractE, Map<PredictionIndices, Float>> probeEdgesScores =
	// null;
	// private Map<AbstractE, Map<PredictionIndices, Float>> noneEdgesScores =
	// null;

	private float probeEdgePercentage = 0.1f;
	
	private AbstractNetwork predictedNet = null;
	
	
	private Vector<Pair<AbstractV>> noneExistLinks = null;
	/*
	 * �Բ����ڱߵ�Ԥ�⣬ map key:Ԥ��ָ�꣬ value:���в��Լ��ߵ�Ԥ�����ֵ
	 */
	private Map<String, Vector<Float>> noneExistLinksScores = null;
	

	private Vector<Pair<AbstractV>> probeLinks = null;
	/**
	 * ����Ԥ�����ֵ�� keyΪԤ���㷨��ָ�꣬��ӦvalueΪ���㷨�õ��ı�Ԥ��߷���ֵ��
	 */
	private Map<String, Vector<Float>> probeLinksScores = null;

	

	private Vector<Set<AbstractE>> probeGroupVector = null;

	// Set<PredictedEdge> probeEdges = null;

	public LinkPrediction() {

	}

	/**
	 * ���Լ�������顣ÿ����Լ�֮���޽����� *
	 * 
	 * @param groupNumber
	 *            ����������
	 */
	public void groupProbeLinks(AbstractNetwork net, int groupNumber) {

		if (probeGroupVector == null) {
			probeGroupVector = new Vector<Set<AbstractE>>();
		} else {
			probeGroupVector.clear();
		}

		for (int k = 0; k < groupNumber; k++) {
			probeGroupVector.add(new HashSet<AbstractE>());
		}

		Vector<AbstractE> allEdges = predictedNet.getAllEdges();
		for (int i = 0; i < allEdges.size(); i++) {

		}

		// for each edge random divide one edge into one probe set.
		for (AbstractE e : predictedNet.getEdges()) {

			Pair<AbstractV> p = predictedNet.getEndpoints(e);
			int randomSetIndex = RandomOperation.randomIndexIn(groupNumber);

			// probeGroupVector.get(randomSetIndex).add(p);

		}

		/* for debug */

		if (debug) {
			Debug.outn("probe set vector size " + probeGroupVector.size());
			int total = 0;
			for (Set<AbstractE> pe : probeGroupVector) {
				total += pe.size();
				Debug.outn("probe set size : " + pe.size());
			}
			Debug.outn("total probe e count : " + total);
		}

	}

	/**
	 * �ҳ������ڵıߡ�
	 */
	public Vector<Pair<AbstractV>> classifyNoneExistLinks(AbstractNetwork net) {
	
		Vector<Pair<AbstractV>> nonLinks = new Vector<Pair<AbstractV>>();
	
		int vertexCount = net.getVertexCount();
		int edgeCount = net.getEdgeCount();

		// ������none�ߣ�����ȫͼ��
		if (edgeCount == (vertexCount * (vertexCount - 1)) / 2) {
			Debug.outn("��ȫͼ");
			return nonLinks;
		}

		Vector<AbstractV> allVertex = net.getAllVertex();
		AbstractV vA;
		AbstractV vB;
		for (int a = 0; a < allVertex.size(); a++) {
			vA = allVertex.get(a);
			for (int b = a + 1; b < allVertex.size(); b++) {
				vB = allVertex.get(b);
				if (a != b) {
					if (!net.containEdge(vA, vB) && !vA.equals(vB)) {

						nonLinks.add(new Pair(vA, vB));
					}
				}
			}
		}
		
		return nonLinks;
	}

	/**
	 * ������������Ƴ�һ�������ı߼�����Լ��� *
	 * 
	 * @param net
	 * @param percent
	 *            ���Լ���ռ���бߵı���
	 * @param type
	 */
	public Vector<Pair<AbstractV>> classifyProbeLinks(AbstractNetwork net,float percent) {

		if (percent > 1) {
			percent = 1;
		}
		if (percent < 0) {
			percent = 0;
		}
	
		Vector<Pair<AbstractV>>	pLinks = new Vector<Pair<AbstractV>>();
	
		/**
		 * ���Լ��бߵ�������
		 */
		int edgeCount = net.getEdgeCount();
		int probeEdgeCount = (int) (edgeCount * percent);

		/**
		 * random select edge into probe set
		 */
		int k=1;
		Vector<AbstractE> allEdges = net.getAllEdges();
		for (int i = 0; i < probeEdgeCount; i++) {
			//���ѡ��һ����
			AbstractE e = allEdges.get(RandomOperation.randomIndexIn(allEdges
					.size()));

			Pair<AbstractV> p = net.getEndpoints(e);

			if (pLinks.contains(p)) {// ������Ѿ�������Լ� 
				i--; 
			} else {
				if ( (float) i / probeEdgeCount *10 >k ) {
					Debug.outn("���ֲ��Լ�������� " + (float) i / probeEdgeCount);
					k++;
				}
				pLinks.add(p);
			}
		}
		

		/**
		 * �Ƴ������еĲ��Լ��ϱ�
		 */
		this.removeProbeLinks(net,pLinks);
		
		return pLinks;


	}// //////////////////////

	private void clearOldResultRecord() {

	}

	public float getProbeEdgePercentage() {
		return probeEdgePercentage;
	}

	public void predictNetwork(AbstractNetwork net,
			 AbstractPredictAlgorithm algorithm) {
		
		if (predictedNet == null) {
			predictedNet = net;

		} else {
			if (!predictedNet.getNetID().equals(net.getNetID())) {
				predictedNet = net;
			}
		}
		

	}

	/**
	 * ��·Ԥ�⡣
	 * 
	 * @param net
	 *            ��Ԥ������
	 * @param algorithm
	 *            Ԥ���㷨
	 */
	public Map<String, Vector<Float>> predictProbeLinks(AbstractNetwork net,Vector<Pair<AbstractV>> probeLinks,
			AbstractPredictAlgorithm algorithm) {		

		Map<String, Vector<Float>> pLinkScors = new HashMap<String, Vector<Float>>();
		
		/**
		 * ������Լ��бߵ�Ԥ�����
		 */
		Vector<String> predictedIndex = algorithm.getPredictedIndices(); // Ҫ����Ԥ������ָ��

		for (Pair<AbstractV> pair : probeLinks) { /* ��ÿ�����Լ��еı� */

			// ���ݲ�ͬ��ָ�����ñߵ�Ԥ�����������ÿ��ָ���Ԥ�����ֵ
			float[] scores = algorithm.computeLinkScore(net,
					pair.getFirst(), pair.getSecond());

			// ����ָ�걣��Ԥ�����
			int index = 0;
			for (String i : predictedIndex) {
				if (pLinkScors.get(i) == null) { // �㷨ָ���Ƿ��Ѿ�����
					pLinkScors.put(i, new Vector<Float>());
				}

				pLinkScors.get(i).add(scores[index]);
				index++;

			}

		}
		
		return pLinkScors;

	}// //////////////////////predictProbeLInks()//////////////////////////////

	/**
	 * �Ƴ��������Ѿ�ѡΪ���Լ��ıߡ� ע�⣺�����Ѿ������仯��
	 * 
	 * @param net
	 */
	private void removeProbeLinks(AbstractNetwork net,Vector<Pair<AbstractV>> pLinks) {

		/* remove probe edges from original network */
		int i = 1;
		int count=0;
		int size = pLinks.size();
		for (Pair<AbstractV> pair : pLinks) {
			net.removeEdge(net.findEdge(pair.getFirst(), pair.getSecond()));
			count++;
			if (debug) {
				if ((float) count /size*10>i) {
					Debug.outn("�Ƴ�������"+size+"�����Լ��ߣ������ " + (float) i / size);
					i++;
				}
			}
		}

	}
	
	

	/***
	 * Ԥ�ⲻ���ڱ߷�����
	 * 
	 * @param algorithm
	 *            �����Ԥ���㷨
	 */
	public Map<String,Vector<Float>> predictNoneExistLinks(AbstractNetwork net,Vector<Pair<AbstractV>> nonLinks,AbstractPredictAlgorithm algorithm) {
		
		Map<String,Vector<Float>> nonLinksScores =new HashMap<String,Vector<Float>> ();	
		
		Vector<String> predictedIndex = algorithm.getPredictedIndices(); // Ҫ����Ԥ������ָ��
		
		/* ÿ���ߣ�����Ԥ����� */
		for (Pair<AbstractV> link : nonLinks) {

			float[] scores = algorithm.computeLinkScore(net,
					link.getFirst(), link.getSecond());
			
			// ����ָ�걣��Ԥ�����
			int index = 0;
			for (String i : predictedIndex) {
				if (nonLinksScores.get(i) == null) { // �㷨ָ���Ƿ��Ѿ�����
					nonLinksScores.put(i, new Vector<Float>());
				}

				nonLinksScores.get(i).add(scores[index]);
				index++;

			}

		}////////ÿ����
		
		return nonLinksScores;
	}

	/**
	 * ����Ԥ���㷨��ȷ�ȡ�
	 * 
	 * @return
	 */
	public float quantifyAccuracy() {
		float result = 0.0f;

		return result;

	}

	/**
	 * ���ѡ��һ�������ڵıߡ�
	 * 
	 * @return
	 */
	public Pair<AbstractV> randomChooseNoneEdge() {
		int vertexCount = predictedNet.getVertexCount();
		int edgeCount = predictedNet.getEdgeCount();

		// ������none�ߣ�����ȫͼ��
		if (edgeCount == (vertexCount * (vertexCount - 1)) / 2) {
			Debug.outn("��ȫͼ");
			return null;
		}

		while (true) {
			Vector<AbstractV> allVertex = predictedNet.getAllVertex();

			AbstractV vA = RandomOperation.RandomSelectOne(allVertex);
			AbstractV vB = RandomOperation.RandomSelectOne(allVertex);
			if (!predictedNet.containEdge(vA, vB) && !vA.equals(vB)) {
				// Debug.outn("find ");
				return new Pair(vA, vB);
			}

			else {
				continue;
			}
		}

	}
	
	/**
	 * ��ȷ�� ��׼����Ԥ������ǰL��Ԥ�����Ԥ��׼ȷ�ı�����
	 * @param index
	 * @return
	 */
	public float precision(AbstractNetwork net, String algorithmIndex,float topPercent){
			
		int L = (int)(net.getEdgeCount()*topPercent);
		
		float result=0.0f;
		
		int existCount=0;//Ԥ��׼ȷ������
		
		float[] noneExistLinks= new float[noneExistLinksScores.get(algorithmIndex).size()];
		float[] probeLinks= new float[probeLinksScores.get(algorithmIndex).size()];
		
		int m=0;
		for(float s:noneExistLinksScores.get(algorithmIndex)){
			noneExistLinks[m]=s;
			m++;
		}
		
		int n=0;
		for(float s:probeLinksScores.get(algorithmIndex)){
			probeLinks[n]=s;
			n++;
		}				
	  
		//��С��������
		Arrays.sort(noneExistLinks);
		Arrays.sort(probeLinks);
		
		int probeI=probeLinks.length-1;
		int noneExistI=noneExistLinks.length-1;		
		for(int i=0;i<L;i++){
			
			Debug.outn("i "+i+"  "+probeLinks[probeI] +"  "+noneExistLinks[noneExistI] );
			
			if(probeLinks[probeI] >= noneExistLinks[noneExistI] ){ //Ԥ��׼ȷ
				existCount++;
				probeI--;
				if(probeI==0) break;
			}else{
				noneExistI--;
				if(noneExistI==0) break;
			}
		}
		
		result = (float)existCount/(float)L;
		
		Debug.outn("precision "+ result);
		return result;
		
	}

	/**
	 * AUC ��׼����Ԥ���㷨.
	 * 
	 * @param probeLinksScores
	 *            ���Լ���Ԥ�����
	 * @param noneExistLinksScores
	 *            �����ڱߵ�Ԥ�����
	 * @param compareCount
	 *            �Ƚϴ���
	 * @return
	 */
	public float AUC(String index, int compareCount) {

		int equalCount = 0;
		int biggerCount = 0;

		if (noneExistLinksScores == null) {
			return -1;
		}

		if (probeLinksScores == null) {
			return -1;
		}

		;
		
		//�ظ�
		for (int i = compareCount; i > 0; i--) {

			float nS = noneExistLinksScores.get(index).get(
					RandomOperation.randomIndexIn(noneExistLinksScores.size()));
			
			float pS = probeLinksScores.get(index).get(
					RandomOperation.randomIndexIn(probeLinksScores.size()));
			//Debug.outn("ns " + nS + "   PS " + pS);

			if (pS > nS) {
				biggerCount++;
			}
			if (pS == nS) {
				equalCount++;
			}
		}

		return (0.5f * equalCount + biggerCount) / compareCount;

	}

	/**
	 * �����Լ�����ӵ������С�
	 */
	private void recoveryNetwork() {
		for (Pair<AbstractV> pair : probeLinks) {
			// ���������в�����
			if (predictedNet.findEdge(pair.getFirst(), pair.getSecond()) == null) {
				predictedNet.addEdge(predictedNet.createNewEdge(),
						pair.getFirst(), pair.getSecond());
			}
		}
	}

	public void setProbeEdgePercentage(float probeEdgePercentage) {
		this.probeEdgePercentage = probeEdgePercentage;
	}

	// test for this class
	public void test(AbstractNetwork net) {
		net.setNetID("test net");
		//
		Debug.outn("L " + net.getEdgeCount() + "  N " + net.getVertexCount());
		Debug.outn("remove probe links");
		Debug.outn("L " + net.getEdgeCount() + "  N " + net.getVertexCount());

		LocalSimilarity ls = new LocalSimilarity();
	
		this.noneExistLinks=classifyNoneExistLinks(net);
		this.probeLinks= this.classifyProbeLinks(net, 0.1f);
		

		this.probeLinksScores=predictProbeLinks(net,probeLinks, ls);

		this.noneExistLinksScores = predictNoneExistLinks(net,noneExistLinks,ls);

		Debug.outn("�����ڱߵ����� " + this.noneExistLinks.size());

		//Debug.outn("probe links");
		for (String index : probeLinksScores.keySet()) {
			//Debug.outn(index + "///////////" + probeLinksScores.get(index).toString());
		}

		//Debug.outn("none exist links");
		for (String index : noneExistLinksScores.keySet()) {
			//Debug.outn(index + "///////////" + noneExistLinksScores.get(index).toString());
		}
		
		
		for(String index:probeLinksScores.keySet()){
			
			float r = AUC(index, this.probeLinks.size() * 10);
			DecimalFormat decimalFormat=new DecimalFormat("0.0000");//���췽�����ַ���ʽ�������С������2λ,����0����.
			String p=decimalFormat.format((double)r);//format ���ص����ַ���
			Debug.outn(p+"   "+index+"ָ��" );
		}		
		
		this.precision(net,"AA", 0.1f);

	}

	public <T> boolean pairEquals(Pair<T> pA, Pair<T> pB) {
		if (pA.getFirst().equals(pB.getSecond())) {
			return true;
		}

		if (pB.getFirst().equals(pA.getSecond())) {
			return true;
		}

		return false;
	}

}// ////////////////////////////////////////////////////////////

class PredictedEdge {

	Float score = null;

	AbstractV vA = null;
	AbstractV vB = null;

	public PredictedEdge(AbstractV vB, AbstractV vA) {
		this.vA = vA;
		this.vB = vB;

	}

	public boolean equals(PredictedEdge obj) {
		// TODO Auto-generated method stub
		return this.getID().equals(obj.getID());
	}

	public String getID() {
		if (vA.getID().compareTo(vB.getID()) > 0) {
			return vA.getID() + vB.getID();
		} else {
			return vB.getID() + vA.getID();
		}
	}

	public Float getScore() {
		return score;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.getID().hashCode();
	}

	public void setScore(Float score) {
		this.score = score;
	}

}

class PredictedEdgeComparator implements Comparator<PredictedEdge> {

	@Override
	public int compare(PredictedEdge eA, PredictedEdge eB) {

		if (eA.getScore() >= eB.getScore()) {
			return 1;
		} else {
			return -1;
		}

	}

}

/**
 * 
 * type of edge for prediction.</p> T, the edge for train. </p> P, the edge for
 * probe. </p> N,the edge doesn't exist in original network.
 */
enum PredictedEdgeType {
	N, // the edge doesn' exist in original network, // the edge for train
	P, // the edge for probe
	T
}
