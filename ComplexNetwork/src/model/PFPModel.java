/**
 * 
 */
package model;

import java.util.Observable;

import network.AbstractE;
import network.AbstractNetwork;
import network.AbstractV;
import tools.*;

import java.util.Vector;;

/**
 * PFP net model.
 * (1) with probability p[0,1], a new node is attached to
 * 	one host node, and at the same time one new internal link
 *	appears between the host node and a peer node; 
 *</p>(2)
 *</p>
 * @author ����
 *
 */
public class PFPModel extends AbstractModel {
	
	private double probP;// ����q��[0,1]
	private double probQ;// ����p��[0,1-p]
	private double delta; // 
	
	protected synchronized double getP() {
		return probP;
	}

	protected synchronized void setP(double p) {
		this.probP = p;
	}

	protected synchronized double getQ() {
		return probQ;
	}

	protected synchronized void setQ(double q) {
		this.probQ = q;
	}

	protected synchronized double getDelta() {
		return delta;
	}

	protected synchronized void setDelta(double delta) {
		this.delta = delta;
	}

	/**
	 * @param p
	 * @param q
	 */
	public PFPModel(double p, double q , double delta) {
		super();
		this.delta= delta;
		this.probP = p;
		this.probQ = q;
	}

	/* (non-Javadoc)
	 * @see complex.model.AbstractModel#createNodeEdges(complex.network.AbstractNetwork, double[])
	 */
	@Override
	public Vector<AbstractV> insertNodeWithEdges(AbstractNetwork net) {

		double p = this.probP;
		double q = this.probQ;
		if (q > 1 - p) { // q����С�ڵ���1-p;
			//return null;
		}
		
		Vector<AbstractV> linkedV=new Vector<AbstractV>();

		/*�Ƚ����½ڵ�*/
		AbstractV newNode = net.createNewVertex(null);
		net.addVertex(newNode);
		//Debug.outn(net.getNetworkSize());

		double randomAction = Math.random();
		//randomAction = 0.2;

		/*���ݲ�ͬ�ĸ��ʽ�����*/
		if (randomAction <= p) { ///�Ը���p
			//Debug.outn("����p "+p);
			int[] ngbNode = new int[1];//�½ڵ���һ���ڵ㽨����

			/*�����Ⱥ���ѡ��һ���ڵ����¼���ڵ㽨���ⲿ��*/
			
			AbstractE newEdge = net.createNewEdge();
			AbstractV prefNode = chooseOneNode(net);//�������ȸ���ѡ���ھӽڵ�		
			net.addEdge(newEdge	, newNode, prefNode);
			//Debug.outn("prefNode"+prefNode);

			/*�¼���ڵ���ھӽڵ����������нڵ㽨��һ���ڲ���*/			
			AbstractE internalNewEdge = net.createNewEdge();
						
			for (int i = 0; i < 1; i++) {
				boolean insertOK=net.addEdge(internalNewEdge, prefNode, chooseOneNode(net));
				//����ģ��ѡ���ھӽڵ�				
				if (insertOK) { //�����ڲ��ߣ��п��ܸñ��Ѿ����ڣ�ȷ�������߳ɹ����������ظ���
					i++;
					
				} else {//����ѡ���ھ�
					//Debug.outn(i);
					continue;
				}
			}

			//Debug.outn("PFPModel.insertNodeWithEdges: nweNode.AIdx BIdx "+newEdge.AIdx+" "+newEdge.BIdx);
			net.addEdge(newEdge, newNode, prefNode);
		//�����½ڵ�ı�

			//ngbNode[0] = prefNode.getID();
			linkedV.add(prefNode);

			return linkedV;

		} else {
			if (randomAction <= p + q) { //�Ը���q
				//Debug.outn("����q "+q);
				int[] ngbNodes = new int[1];//��һ���ڵ㽨����

				/*�����Ⱥ���ѡ��һ���ڵ����¼���ڵ㽨���ⲿ��*/
				AbstractE newEdge = net.createNewEdge();
				AbstractV prefNode = chooseOneNode(net);				

				/*�¼���ڵ���ھӽڵ����������нڵ㽨�������ڲ���*/
				//int ngbNodeIdx = newEdge.BIdx;

				/*�����ھ�����������*/
				for (int j = 0; j < 2;) {

					AbstractE internalEdge = net.createNewEdge();
					//internalEdge.setNodeA(net,prefNode);					
					//internalEdge.setNodeB(net,);//����ģ��ѡ���ھӽڵ�
					//Debug.outn("PFP model insertNodeWithEdges() new Edge "+prefNode.getIdx()+" "+ internalEdge.getBIdx());

					if (net.addEdge(internalEdge, prefNode, chooseOneNode(net))) { //ȷ�������߳ɹ����������ظ���

						//Debug.outn("�½ڵ� "+j+" "+newNode.idx+"�ھ� "+newEdge.BIdx);
						; /* �п��ܳ��������ӻ��ظ��ı� */
						// Debug.outn("insert "+j);						
						j++;
					} else {
						continue;
					}
				}//////////////�����				

				//net.insertEdge(newEdge);//�����½ڵ�ı�
				net.addEdge(newEdge, newNode, newNode);

				linkedV.add(prefNode);

				return linkedV;

			} else { //�Ը���1-p-q
				//Debug.outn("����1-p-q "+(1-p-q));
				
				int[] ngbNodes = new int[2];//��2���ڵ㽨����

				/*�����ھ�����������*/
				for (int j = 0; j < 2;) {

					AbstractE newEdge = net.createNewEdge();
					
					AbstractV prefNode = chooseOneNode(net);
					
					
					//Debug.outn("new Edge "+newEdge.AIdx+" "+ newEdge.BIdx);
					;
					if (net.addEdge(newEdge, newNode, prefNode)) { //ȷ�������߳ɹ����������ظ���

						//Debug.outn("�½ڵ� "+j+" "+newNode.idx+"�ھ� "+newEdge.BIdx);
						; /* �п��ܳ��������ӻ��ظ��ı� */
						//Debug.outn("insert "+j);
						linkedV.add( prefNode );
						j++;
					} else {
						continue;
					}
				}//////////////�����

				return linkedV;

			}////////�Ը���1-p-q
		}

	}///////////////////////////////////////////////////

	/* (non-Javadoc)
	 * @see complex.model.AbstractModel#degreePrefAttachFunction(complex.network.AbstractNetwork, int, double[])
	 */
	public boolean preferentialChoosNode(AbstractNetwork net, AbstractV node) {
		
		if (net.getVertex(node)==null) { //���ڵ㲻����
			//Debug.outn("PFPModel degreePrefChoosed node "+node.getID()+" doesn't exist!");
			return false;
		}
		//double p = 0.0; //Ŀ��ڵ㱻ѡ�е����ȸ���

		//int [] degrees = net.getAllDegrees() ;
		int nodeDegree = net.getNeighborCount(node);
		
		int maxDegree = MathTool.getMax(net.getAllVertexDegrees());
		//int[] degrees = net.getAllDegrees();

		//int randomDegree = MathTool.randomInt(1, maxDegree + 1, 1)[0]; //������� 1-����ֵ �������ֵ��

		/*�ڵ�ȵļ�Ȩ��*/
//		double degreeSum = 0.0;
//		for (int j : degrees) {
//			degreeSum = degreeSum
//					+ Math.pow(degrees[j], 1 + delta * Math.log10(degrees[j]));
//		}
		if (MathTool.randomInt( 1, (int) Math.pow(maxDegree, 1 + delta
				* Math.log10(maxDegree)), 1)[0] < Math.pow(nodeDegree, 1
				+ delta * Math.log10(nodeDegree))) {
			//Debug.outn("degreePrefAttachFunction 1 "+Debug.currentTime());
			return true;
		} else {
			//Debug.outn("degreePrefAttachFunction 0 "+Debug.currentTime());
			return false;
		}

	}//////////////////////////////////////


	public AbstractNetwork getTopNetwork(String netID, int nodeNum,
			double[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	

}