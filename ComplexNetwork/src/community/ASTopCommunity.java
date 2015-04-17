/**
 * 
 */
package community;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import network.*;
import tools.*;
import properties.*;

/**
 * AS级拓扑网络的社团,每个类保存一个网络社图的数据
 * @author ge xin
 *
 */
public class ASTopCommunity extends AbstractCommunity {

	public ASTopCommunity(String communityID) {
		this.communityID = communityID;
	}

	public ASTopCommunity() {

	}
	
	public ASTopCommunity(String communityID , Set<Set<Integer>> community){
		this.communityID= communityID;
		this.community = community;
	}

	/* (non-Javadoc)
	 * @see complex.community.AbstractCommunity#clone()
	 */
	@Override
	public AbstractCommunity clone() {

		this.saveCommunityToFile("temp" + this.getCommunityID(), false);
		String file ;
		file = "temp" + this.getCommunityID();
		ASTopCommunity newCommunity = (ASTopCommunity) CommunityBuilder
				.creatCommunityFromFile(file);

		return newCommunity;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractCommunity asComm;
		String file ;
		file= "f://aslink//";
//		asComm = ASTopCommunityBuilder.loadCommunityFromFile(file);
//		Debug.outn(asComm.outputCommunityInfo());
//		asComm.saveCommunityToFile("f://200001commIDtest", false);
//
//		AbstractCommunity newComm = asComm.clone();
//		Debug.outn(newComm.outputCommunityInfo());
//		
//		AbstractCommunity randomComm;
//		String[] infile = new String[1];
//		infile[0] = "f:\\Random15Net.txt";
//		createNewEdge randomNet;
//		randomNet = ASTopNetworkBuilder.loadNetworkFromFile(infile,createNewEdge.NTYPE_NODE_WITH_ID, null);
//		
//		randomComm = CommunityTools.CNM(randomNet);
//		//Debug.outn(DegreeDistribution.CCDF(randomNet), "pdf");
//		randomComm.setModuleDegree(CommunityTools.NewmanQ(randomComm, randomNet));		
//		Debug.out(randomComm.outputCommunityInfo());

	}

	@Override
	public boolean saveCommunityToFile(String fileName, boolean append) {

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
			FileWriter fw = new FileWriter(new File(fileName), append);

			// 每个社团
			Iterator<Set<Integer>> communityIt = community.iterator();
			while (communityIt.hasNext()) {
				// 子团
				Set<Integer> subCommunity = communityIt.next();
				subCommSize = subCommunity.size();

				line = communityID + " " + this.modularity + " " + subCommID
						+ " " + subCommSize + " ";
				// Debug.outn(line);
				subCommID++;

				// 保存每个成员id
				Iterator<Integer> members = subCommunity.iterator();
				while (members.hasNext()) {

					int memID = ((Integer) members.next()).intValue();
					// int memID = net.idx2Id(memIDx);
					line = line + memID + ",";
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
			Debug.out("IOException in save communities to file");
			return false;
		}
	}

}
