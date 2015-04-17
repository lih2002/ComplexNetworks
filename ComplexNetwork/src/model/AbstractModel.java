/**
 * 
 */
package model;

import tools.Debug;
import tools.MathTool;
import network.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;;

/**
 * abstract network topology model
 * @author gexin
 *
 */
public abstract class AbstractModel {
	

	/**
	 * ���ݾ����ģ�ͷ����������˽ṹ��
	 * @param netID 
	 * @param nodeNum �ڵ�����
	 * @param initNodeNum TODO
	 * @return ��������
	 */
	public AbstractNetwork modelNetwork(AbstractNetwork net, int nodeNum, int initNodeNum,int initEdgeNum) {
		
		
		InitNetwork.initRandomNet(net, initNodeNum);
		
		//constructNetwork(modelNet, nodeNum - initNodeNum);

		return net;
	}
	


	/**
	 * ���ݾ���ģ�͵�ʵ�ִ�������ѡ��һ���Ѵ��ڵĽڵ�
	 * @param net �ڵ����ڵ�����
	 * @param args ����ѡ�����еĲ���
	 * @return ��ѡ�еĽڵ�
	 */
	protected AbstractV chooseOneNode(AbstractNetwork net) {
			
		AbstractV randomNode;
		//Debug.outn("begin choose "+Debug.currentTime());
		while (true) { //ֱ��ѡ��һ���ڵ�Ϊֹ

			randomNode = net.randomGetVertex();
			;//�����ѡ��һ���ڵ�,�ٸ������Ⱥ���ȷ���ýڵ��Ƿ�ѡ��
			//randomDegree = MathTool.randomInt(1, maxDegree + 1, 1)[0]; //������� 1-����ֵ �������ֵ��
			if ( preferentialChoosNode(net, randomNode) ) {
				//Debug.outn("end choose "+randomNode.getID()+" "+Debug.currentTime());
				break;
			}
		}
		//Debug.outn("AbstractModel.chooseOneNode: randomNodeIdx "+randomNodeIdx);
		return randomNode;
	}
	


	/**
	 * ������ѡ��ڵ㺯����
	 * ����ģ�������õĸ��ʺ���ȷ��ĳһ�����Ƿ�ѡ�С�
	 * @param net
	 * @param nodeIDx 
	 * @param args ����������double����
	 * @return true������ڵ㱻ѡ�У�false���ڵ�δ��ѡ��
	 */
	protected abstract boolean preferentialChoosNode(AbstractNetwork net,
			AbstractV node);

	/**
	 * ���������еĽڵ�ͱߡ�
	 * </p>����һ���½ڵ㣬��Ϊ�ýڵ㽨�����ӡ�
	 * @param net
	 * @param newNodeNum ��Ҫ�¼���ڵ������
	 */
	private void constructNetwork(AbstractNetwork net, int newNodeNum) {
		//Debug.outn("��Ҫ����Ľڵ����� "+insertNodeNum);
		
		for (int i = 0; i < newNodeNum; ) {
//			Debug.outn("construct i"+i);
			Vector<AbstractV> neighborNodes = insertNodeWithEdges(net); //��һ���������½ڵ㣬
			
			if ( neighborNodes!= null) { //�ɹ�������һ���ڵ�
				//Debug.outn("�ھ����� "+ngbNodes.length);
				Debug.outn("����ڵ����� "+(i+1)+" "+Debug.currentTime());
				
				/*�������½ڵ㣬֪ͨ�۲���*/

				//Debug.outn("in model"+net.getNetworkSize());
//				int netSize = net.getNetworkSize();
//				if( netSize%1000==0){
//					//Debug.outn("notify");
//					this.setChanged();
//					notifyObservers("e://Gexin//aslink//"+(100000+netSize/1000)+"pfpModel");
//					//notifyObservers("e://"+netSize+"pfpModel");
//				}
				i++;
			}			
		}

	}

	/**
	 * ����һ���ڵ㣬���ýڵ�ıߡ�
	 * </p>ע�⣺��һ���ɹ��������µĽڵ㡣��ͬ��ģ���в�ͬ�Ĳ�����
	 * Ҳ�п����ڴ˷�����ֻ�����˱ߡ�
	 * @param targetNode
	 * @param edge
	 * @return �����ڵ㽨�����ӵĽڵ㣬��û�в��룬����null
	 */
	protected abstract Vector<AbstractV> insertNodeWithEdges(AbstractNetwork net);
	
	
	/**
	 * ���̵߳���
	 */
	public void run(){
		
	}

	public void saveNetTopWhenNodeNumber(String filePath){
		
	}
	
	public void saveNetTopConditions(String filePath){
		
	}
	

}/////////////////////////////////////////////////////////////////////////////////////////


class ChooseNode implements Runnable{
	 
	 private static ChooseNode instance=null;
	 
	 public  boolean hasFound;
	 AbstractModel model;
	 AbstractV targetNode;
	 
	 public ChooseNode(AbstractModel model){
		 this.model  = model;
		 hasFound = false;
		 targetNode = null;
	 }
	 
	 public static ChooseNode getInstance(AbstractModel model){
		 if(instance == null){
			 return new ChooseNode(model);
		 }else{
			 return instance;
		 }
	 }
	 
	 


	protected AbstractV getTargetNode() {
		return targetNode;
	}

	protected synchronized boolean hasFound() {
		return hasFound;
	}

	protected synchronized void setHasFound(boolean hasFound) {
		this.hasFound = hasFound;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}	
	
	
}
