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
 * AS���������������,ÿ���ౣ��һ��������ͼ������
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

		// sub-community ID ��start from 1 automatically
		// ��id������Ϊ���ŵ�Ψһ��ʶ��ֻ�ڵ�ǰ�ļ�����Ч
		// ���磺���±���Ϊ�ļ�������id�п��ܱ仯
		int subCommID = 1;
		// ���ų�Ա����
		int subCommSize = 0;

		// ���λ��ֵ�ģ���

		// ����ļ���ÿһ������
		String line = "";
		//Debug.outn(fileName);
		try {
			FileWriter fw = new FileWriter(new File(fileName), append);

			// ÿ������
			Iterator<Set<Integer>> communityIt = community.iterator();
			while (communityIt.hasNext()) {
				// ����
				Set<Integer> subCommunity = communityIt.next();
				subCommSize = subCommunity.size();

				line = communityID + " " + this.modularity + " " + subCommID
						+ " " + subCommSize + " ";
				// Debug.outn(line);
				subCommID++;

				// ����ÿ����Աid
				Iterator<Integer> members = subCommunity.iterator();
				while (members.hasNext()) {

					int memID = ((Integer) members.next()).intValue();
					// int memID = net.idx2Id(memIDx);
					line = line + memID + ",";
				}

				// ȥ�����һ������
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
