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
	 * 根据具体的模型返回网络拓扑结构。
	 * @param netID 
	 * @param nodeNum 节点数量
	 * @param initNodeNum TODO
	 * @return 网络拓扑
	 */
	public AbstractNetwork modelNetwork(AbstractNetwork net, int nodeNum, int initNodeNum,int initEdgeNum) {
		
		
		InitNetwork.initRandomNet(net, initNodeNum);
		
		//constructNetwork(modelNet, nodeNum - initNodeNum);

		return net;
	}
	


	/**
	 * 根据具体模型的实现从网络中选择一个已存在的节点
	 * @param net 节点所在的网络
	 * @param args 优先选择函数中的参数
	 * @return 被选中的节点
	 */
	protected AbstractV chooseOneNode(AbstractNetwork net) {
			
		AbstractV randomNode;
		//Debug.outn("begin choose "+Debug.currentTime());
		while (true) { //直至选出一个节点为止

			randomNode = net.randomGetVertex();
			;//先随机选择一个节点,再根据优先函数确定该节点是否被选中
			//randomDegree = MathTool.randomInt(1, maxDegree + 1, 1)[0]; //随机产生 1-最大度值 的随机度值。
			if ( preferentialChoosNode(net, randomNode) ) {
				//Debug.outn("end choose "+randomNode.getID()+" "+Debug.currentTime());
				break;
			}
		}
		//Debug.outn("AbstractModel.chooseOneNode: randomNodeIdx "+randomNodeIdx);
		return randomNode;
	}
	


	/**
	 * 度优先选择节点函数。
	 * 根据模型所采用的概率函数确定某一个点是否被选中。
	 * @param net
	 * @param nodeIDx 
	 * @param args 函数参数，double数组
	 * @return true，如果节点被选中，false，节点未被选中
	 */
	protected abstract boolean preferentialChoosNode(AbstractNetwork net,
			AbstractV node);

	/**
	 * 构建网络中的节点和边。
	 * </p>插入一个新节点，并为该节点建立连接。
	 * @param net
	 * @param newNodeNum 需要新加入节点的数量
	 */
	private void constructNetwork(AbstractNetwork net, int newNodeNum) {
		//Debug.outn("需要插入的节点数量 "+insertNodeNum);
		
		for (int i = 0; i < newNodeNum; ) {
//			Debug.outn("construct i"+i);
			Vector<AbstractV> neighborNodes = insertNodeWithEdges(net); //不一定插入了新节点，
			
			if ( neighborNodes!= null) { //成功插入了一个节点
				//Debug.outn("邻居数量 "+ngbNodes.length);
				Debug.outn("插入节点数量 "+(i+1)+" "+Debug.currentTime());
				
				/*插入了新节点，通知观察者*/

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
	 * 插入一个节点，及该节点的边。
	 * </p>注意：不一定成功插入了新的节点。不同的模型有不同的操作。
	 * 也有可能在此方法内只插入了边。
	 * @param targetNode
	 * @param edge
	 * @return 与插入节点建立连接的节点，若没有插入，返回null
	 */
	protected abstract Vector<AbstractV> insertNodeWithEdges(AbstractNetwork net);
	
	
	/**
	 * 多线程调用
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
