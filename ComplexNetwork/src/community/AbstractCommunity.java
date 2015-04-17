/**
 * 
 */
package community;

import network.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import tools.*;

/**
 * 抽象社团，保存社团基本信息。
 * abstract community.
 * @author gexin
 *
 */
public abstract class AbstractCommunity {

	protected String communityID = null;

	
	//模块度
	protected double modularity;

	//网络社团，集合的成员为集合，子集合的成员为网络节点Id
	protected Set<Set<String>> community = null;

	/**
	 * 测试类用
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * 克隆一个网络社团，深拷贝
	 */
	public abstract AbstractCommunity clone();

	/**
	 * 将某一次的网络划分的社团及其特征保存到文本文件
	 * </p>
	 * 格式：
	 * </p>
	 * 社团标识 模块度 子团序号 成员数量 成员1ID,成员2ID ···
	 * </p>
	 * 存在冗余，但是为了数据库处理方便
	 * 
	 */
	public  boolean saveCommunityToFile(String filePathName,
			boolean append){

		// sub-community ID ，start from 1 automatically
		// 该id不能作为子团的唯一标识，只在当前文件中有效
		// 例如：重新保存为文件后，子团id有可能变化
		int subCommID = 1;
		// 子团成员数量
		int subCommSize = 0;

		// 本次划分的模块度

		// 输出文件的每一行内容
		String line = "";
		//Debug.outn(fileName);
		try {
			FileWriter fw = new FileWriter(new File(filePathName), append);

			// 每个社团
			Iterator<Set<String>> communityIt = community.iterator();
			while (communityIt.hasNext()) {
				// 子团
				Set<String> subCommunity = communityIt.next();
				subCommSize = subCommunity.size();

				line = communityID + " " + this.modularity + " " + subCommID
						+ " " + subCommSize + " ";
				// Debug.outn(line);
				subCommID++;

				// 保存每个成员id
				for(String member:subCommunity){
					line = line + member + ",";
				}
								
				// 去掉最后一个逗号
				line = line.substring(0, line.length() - 1) + "\r\n";

				fw.write(line);
				line = "";
			}// try

			fw.flush();
			fw.close();
			return true;

		} catch (IOException ioe) {
			Debug.out("IOException in save communities to file\n"+ioe.toString());
			return false;
		}
	}
	
	
	/**
	 *import community structure from source data. 
	 * @param source
	 * @return
	 */
	public boolean importCommunity(Collection<Set<AbstractV>> source){
		if(this.community==null){
			this.community= new HashSet<Set<String>>();
		}
		
		if(this.community.size()>0){
			community.clear();
		}
		
		Set<String> subC;
		for(Set<AbstractV> subComm:source){
			subC=new HashSet<String>();
			for(AbstractV member:subComm){
				subC.add(member.getID());
			}
			community.add(subC);
		}
		
		return true;
	}
	
	/**
	 * 移除指定的一个节点，若子团只包含一个指定的节点，则子团也被移除
	 * @param nodeID 指定节点ID
	 * @return true ，成功移除，false，未移除
	 */
	protected boolean deleteNode(String nodeID){
		Iterator<Set<String>> commIt = community.iterator();
	
		for(Set<String> subComm:community){
			for(String member:subComm){
				if(member.equals(nodeID)){
					subComm.remove(member);
					if(subComm.size()==0){
						community.remove(subComm);
					}
					return true;
				}
			}
		}		
		
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public String getCommunityInfo() {
		
		String info = "";
		if (this.community == null) {
			info = "错误：社团数据还没有初始化";
			Debug.outn(info);
			return info;
		}
		// Debug.outn(comSize);		

		/**
		 * 所有节点数量
		 */
		int totalNodeNum = 0;
		for(Set<String> subCommunity:community){
			totalNodeNum += subCommunity.size();
		}	
		
		/**
		 * 第一行，基本信息
		 */
		info = info + "网络ID " + this.communityID + " 所有节点数量 " + totalNodeNum
				+ " 子团数量 " + community.size() + " 模块度 " + this.modularity
				+ "\n";
		
		
		/**
		 *输出 每个子社团信息。
		 */		
		
		info = info + "子团序号" + " 成员数量\n";
		int subCommunityIDx = 1;
		
		for(Set<String> subCommunity:community){
			
			info = info + subCommunityIDx + " " + subCommunity.size() + " ";
			subCommunityIDx++;
			
			// 输出子团内的每一个节点ID
			for(String member:subCommunity){
				info = info + member + ",";
			}
			
			info = info + "\n";
		}		
		
		return info;
	}

	public String getCommunityID() {
		return communityID;
	}
	
	/**
	 * 社团大小，即社团内子团数量
	 * @return
	 */
	public int getCommunitySize() {
		return this.community.size();
	}
	
	/**
	 * 每个子团大小，即每个子团内节点数量。
	 * @return 数组，每个元素为一个子团内节点数量。
	 */
	public int [] getSubCommsSizes(){
		int [] size = new int[this.community.size()];
		
		int i=0;
		for(Set<String> subComm:community){
			size[i++]=subComm.size();
		}

		return size;
	}
	
	/**
	 * the edges number in the same sub community
	 * @param subComm
	 * @return
	 */
	public int getEdgeNumInsideSubComm(Set<String> subComm , AbstractNetwork net){
	
		int edgeNum=0;
		
		
		return edgeNum;
	}
	
	/**
	 * 两个子团之间的连接数量。
	 * @param subCommA
	 * @param subCommB
	 * @param net
	 * @return
	 */
	public int getEdgeNumBetweenSubComm(Set<String> subCommA , Set<String> subCommB , AbstractNetwork net){
		
		int edgeNum = 0;
		
		Vector <AbstractE> edgeVector = net.getAllEdges();
		
		for(int i=0;i<edgeVector.size();i++){
			
	
			
		}
	
		
		return edgeNum;
	}

	
	/**
	 * 某一节点所在子团，包括该节点
	 * @param nodeID
	 * @return 节点所在子团成员节点ID集合，null 如果社团不存在该节点
	 */
	public Set<String> getSubCommMembers(String nodeID) {
		
		Iterator<Set<String>> subC = this.community.iterator();
		while (subC.hasNext()) {
			Set<String> subCommunity = subC.next();
			if (subCommunity.contains(nodeID)) {
				return MathTool.copySet(subCommunity);
			}
		}
		return null;
	}
	
	/**
	 * 某些节点所在子团。
	 * @param nodes 节点ID集合
	 * @return 映射：节点所在子团集合--在同一子团节点集合。
	 */
	public Map< Set<String> , Set<String> > getSubCommMembers(Set<String> nodes){
		
		Map< Set<String>,Set<String> > subCommsMap = 
			new TreeMap< Set<String>,Set<String> >(new SizeCompator());
		
		Iterator <String> nodeIDsIt = nodes.iterator();
		while(nodeIDsIt.hasNext()){ //每一个目标节点
			String nodeID = nodeIDsIt.next();
			Set<String> subComm = getSubCommMembers(nodeID); // 节点所在子团
			if(subCommsMap.containsKey(subComm)){ // 若所在子团已保存在Map中
				subCommsMap.get(subComm).add(nodeID);
			}else{
				Set<String> snodes = new TreeSet<String>();
				snodes.add(nodeID);
				subCommsMap.put(subComm,snodes );
			}
		}		
		return subCommsMap;
	}
	
	public Map< Set<String> , Set<String> > getSubCommOutLink(Set<String> nodeIDs){
		
		Map< Set<String>,Set<String> > subCommsMap = 
			new TreeMap< Set<String>,Set<String> >(new SizeCompator());
		
		return subCommsMap;
	}
	
	public boolean inTheSameCommunity(int nodeA, int nodeB){
		
		Iterator <Set<String>> subCommIt = this.getCommunity().iterator();
		while(subCommIt.hasNext()){
			Set<String>  subComm = subCommIt.next();
			if(subComm.contains(nodeA)&& subComm.contains(nodeB)){
					return true;
					
			}
			
		}
		return false;
	}
	
	/**
	 * whether two nodes belong to one sub community
	 * @param nodeA
	 * @param nodeB
	 * @return
	 */
	public int nodesInCommunityIndex(String nodeA){
		
		Iterator <Set<String>> subCommIt = this.getCommunity().iterator();
		int index=0;
		boolean finded=false;
		while(subCommIt.hasNext()){
			Set<String>  subComm = subCommIt.next();
			if(subComm.contains(nodeA)){
					finded=true;
					break;
			}
			index++;
		}
		if(finded){
			return index;
		}else{
			return -1;
		}
	}

	/**
	 * 节点在所在子团内的邻居节点
	 * @param nodeID
	 * @param 节点所在网络
	 * @return 节点在子团内部的邻居节点ID 集合
	 */
	public Set<String> getNeighborsInSubComm(AbstractNetwork net, String nodeID) {
		
		if (!net.containVertex(nodeID)) {
			return null;
		}
		
		//节点所在的子团
		Set<String> subComm = this.getSubCommMembers(nodeID);
		//节点的所有邻居节点
		Set<String> neighborNode = new HashSet<String>();
		
		for(AbstractV node:net.getNeighbors(net.getVertex(nodeID) )){
			neighborNode.add(node.getID());
		}

		
		Set <Set<String>> twoSets = new HashSet <Set<String>> ();
		twoSets.add(subComm);
		twoSets.add(neighborNode);
		
		//返回交集节点，即子团内的邻居节点
		return MathTool.intersectionSet(twoSets);
	}
	
	/**
	 * 节点在所在子团外部的邻居节点
	 * @param net
	 * @param nodeID
	 * @return
	 */
	public Set<String> getNeighborsOutSubComm(AbstractNetwork net, String nodeID) {
		
		if (!net.containVertex(nodeID)) {
			return null;
		}
		
		//节点所在的子团
		Set<String> subComm = this.getSubCommMembers(nodeID);
		//节点的所有邻居节点
		Set<String> neighborNode = new HashSet<String>();
		for(AbstractV node:net.getNeighborVertices(net.getVertex(nodeID) )){
			neighborNode.add(node.getID());
		}
	
		
		//MathTool.diffSet(set)
		//返回差集节点，即子团外部的邻居节点
		return MathTool.diffSet(neighborNode, subComm);
	}
	
	/**
	 * nodes out of one certain sub comm
	 * @param net.
	 * @param subComm oen subComm of community.
	 * @return set of node id out of sub comm.
	 */
	public Set<String> getNeighborsOutSubComm(AbstractNetwork net, Set<String> subComm){
		Set <String> ngbNodesSet = new HashSet<String>();
		Iterator <String> subCommIt = subComm.iterator();
		while(subCommIt.hasNext()){
			ngbNodesSet.addAll( getNeighborsOutSubComm(net, subCommIt.next()) );
		}
		return ngbNodesSet;
	}
	 
	/**
	 * 返回每个子团内节点度
	 * @param net
	 * @return
	 */
	public int[] getSubCommDegrees(AbstractNetwork net, Set<String> subComm){
		int []degrees = new int[subComm.size()];
		
		Iterator <String> subCommIt = subComm.iterator();
		int index=0;
		while(subCommIt.hasNext()){
			degrees[index]=net.getNeighborCount(net.getVertex(subCommIt.next() ) );
			index++;
		}
		Arrays.sort(degrees);
		
		return degrees;
	}
	/**
	 * 返回所有子团内节点的度
	 * @param net
	 * @return 子团-节点度 映射。子团按其大小排序。度按大小排序
	 */
	public Map< Set<String>, Vector<Integer> > getSubCommDegrees(AbstractNetwork net){
		Map<Set<String>,Vector<Integer>> degree= 
			new TreeMap<Set<String>,Vector<Integer>>(new SizeCompator());
		Iterator <Set<String>> subCommIt = this.community.iterator();
		while(subCommIt.hasNext()){
			Set<String> subComm = subCommIt.next();
			int [] subD = this.getSubCommDegrees(net,subComm );
			Vector <Integer> d = new Vector<Integer>();
			for(int i =0;i<subD.length;i++){
				d.add(subD[i]);
			}
			degree.put(subComm, d);			
		}
		return degree;
	}
	
	/**
	 * 模块度
	 * @return
	 */
	public double getModularity() {
		return modularity;
	}
	
	public void setModularity(double modularity){
		this.modularity = modularity;
	}
	
	public void setCommunityID(String communityID){
		this.communityID=communityID;
	}

	public final Set<Set<String>> getCommunity() {
		return community;
	}
	/**
	 * 社团内所有节点数量
	 */
	public int getNodesNum(){
		int nodesNum =0;
		int []subNodesNum = this.getSubCommsSizes();
		for(int i=0;i<subNodesNum.length;i++){
			nodesNum+=subNodesNum[i];
		}
		return nodesNum;
	}	
	
	
	/**
	 * 社团中所有节点ID集合
	 * @return
	 */
	public Set<String> getAllNodeID(){
		Set <String> allNodeIDs = new TreeSet<String>();
		
		for(Set<String> subComm:community){
			
		}
		
		Iterator<Set<String>> subC = this.community.iterator();

		while (subC.hasNext()) {
			allNodeIDs.addAll(subC.next());
			//Debug.outn(subC.next(),"subc");
		}
	//	Debug.outn(allNodeIDs,"test");
		return allNodeIDs;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<String> randomCommunity(){
		int []rndCommunity = MathTool.randomInt(0, this.community.size(), 1);
		final Set<String> subComm = (Set<String>)(community.toArray()[rndCommunity[0]]);
		return subComm;
	}
	
//	public int[] randomTwoNodesInCommunity( ){
//		int [] ret=new int[3];
//		int []rndCommunity = MathTool.randomInt(0, this.community.size(), 2);
//		//Set<Integer> subCommunity = Set<Integer>
//		Set<Integer> subCommA = (Set)(community.toArray()[rndCommunity[0]]);
//		Set<Integer> subCommB = (Set)(community.toArray()[rndCommunity[1]]);
//
//		
////		if(subComm.size()>=2){
////			ret[0]=rndCommunity[0];
////		}
//		
//		
//		
//		
//		
//	}
	
	/**
	 * 
	 * @param subA
	 * @param subB
	 * @return float[][] array ,different between subA ,subB. 
	 * </p>[0][0]indicates the type: 1 A=B; 0 A null B ; -1 A is B's sub Set. B is A's sub Set. 
	 * [0][1] indicates the degree:between 0-1
	 */
	public float[][] compareSubCommunity(Set<String>subA , Set<String>subB){
		float diff[][]=new float[1][2];
		
		// set A and B are totally different
		if((MathTool.intersectionSet(subA,subB)).size()==0 ){
			return diff;
		}
		//set A and B are identical
		if(subA.size()==subB.size()){
			if( MathTool.unionSet(subA,subB).size()==subA.size() ){
				return diff;
			}
		}
		//A < B
		if(subA.size()<subB.size()){
			
				
		}
		//A > B
		if(subB.size()>subB.size()){
			
		}
		
		return diff;
	}

}///////////////////////// AbstractCommunity

/**
 * 根据集合内元素数量的多少排序
 * @author gexin
 *
 */
class SizeCompator <T>  implements Comparator<Set<T>>{

	public int compare(Set<T> o1, Set<T> o2) {
		
		return o1.size()-o2.size();
	}
	
}
