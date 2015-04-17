/**
 * 
 */
package community;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import tools.*;


/**
 * 
 * @author gexin
 *
 */
public class CommunityBuilder {
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static AbstractCommunity  creatCommunityFromFile(String fileName) {
		AbstractCommunity community = new ASTopCommunity();
		try {
				community.community = new HashSet<Set<String>>();
				// Debug.outn("已读取社团文件 "+fileName[fi]);
				
				FileReader fr = new FileReader(new File(fileName));
				BufferedReader br = new BufferedReader(fr);

				String line = br.readLine();
				
				while (line != null) { // 每行为一个社团划分中的子社团
					String[] columns = line.split(" ");
					community.communityID = columns[0];
					community.modularity = Double.parseDouble(columns[1]);
		

					// 子团中的成员，标识为节点ID
					// 节点ID相同的子团属于同一网络
					String[] members = columns[4].split(",");

					// Debug.outn("load community() members size
					// "+members.length);
					
					HashSet <String> subCommunity = new HashSet <String> ();
					for (int i = 0; i < members.length; i++) {
						// 加入每一个节点ID
						subCommunity.add( members[i] );
					}

					// Debug.outn("load community() memberSet size
					// "+membersSet.size());
					// 将该行数据对应的子团加入到整个社团划分中
					
					community.community.add(subCommunity);
			
					line = br.readLine();
				}// 处理行
				br.close();
				fr.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return community;

	}
	
	
	/**
	 * 从文件读取社团。
	 * @param community
	 * @param filePath
	 * @param fileName
	 * @param postfix
	 */
	public static void loadCommunityFromFile(AbstractCommunity community,String filePath,String fileName,String postfix){
		try {
			community.community = new HashSet<Set<String>>();
			// Debug.outn("已读取社团文件 "+fileName[fi]);
			
			FileReader fr = new FileReader(new File(filePath+fileName+postfix));
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			
			while (line != null) { // 每行为一个社团划分中的子社团
				String[] columns = line.split(" ");
				community.communityID = columns[0];
				community.modularity = Double.parseDouble(columns[1]);
	

				// 子团中的成员，标识为节点ID
				// 节点ID相同的子团属于同一网络
				String[] members = columns[4].split(",");

				// Debug.outn("load community() members size
				// "+members.length);
				
				HashSet <String> subCommunity = new HashSet <String> ();
				for (int i = 0; i < members.length; i++) {
					// 加入每一个节点ID
					subCommunity.add((members[i]));
				}

				// Debug.outn("load community() memberSet size
				// "+membersSet.size());
				// 将该行数据对应的子团加入到整个社团划分中
				
				community.community.add(subCommunity);
		
				line = br.readLine();
			}// 处理行
			br.close();
			fr.close();
		
		} catch (IOException e) {
			e.printStackTrace();		
		}
		
	}///////////////////////////////////
	
	/**
	 * 
	 * @param community
	 * @param filePath
	 * @param fileName
	 * @param postfix
	 */
	public static void loadCommunityFromOtherFile(AbstractCommunity community,String filePath,String fileName,String postfix){
		try {
			community.community = new HashSet<Set<String>>();
			// Debug.outn("已读取社团文件 "+fileName[fi]);
			
			FileReader fr = new FileReader(new File(filePath+fileName+postfix));
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			
			while (line != null) { // 每行为一个社团划分中的子社团
				if(line.trim().equals("")) {
					line=br.readLine();
					continue;
				}
				String[] columns = line.split(" ");
				//community.communityID = columns[0];
				//community.modularity = Double.parseDouble(columns[1]);
	

				// 子团中的成员，标识为节点ID
				// 节点ID相同的子团属于同一网络
				String[] members = columns[2].split(",");

				// Debug.outn("load community() members size
				// "+members.length);
				
				HashSet <String> subCommunity = new HashSet <String> ();
				for (int i = 0; i < members.length; i++) {
					// 加入每一个节点ID
					try{ subCommunity.add( members[i] );
					}catch(Exception e){
						continue;
					}
				}

				// Debug.outn("load community() memberSet size
				// "+membersSet.size());
				// 将该行数据对应的子团加入到整个社团划分中
				
				community.community.add(subCommunity);
		
				line = br.readLine();
			}// 处理行
			br.close();
			fr.close();
		
		} catch (IOException e) {
			e.printStackTrace();		
		}
		
	}///////////////////////////////////
	
	/**
	 * 
	 * @param community
	 * @param filePathName
	 */
	public static void loadCommunityFromOtherFile2(AbstractCommunity community,String filePathName){
		
		try {
			community.community = new HashSet<Set<String>>();
			//Debug.outn(filePathName);
			
			File inputFile = new File(filePathName);
			
			FileReader fr = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(fr);
			String commID=inputFile.getName().split("-")[0];
			community.setCommunityID(commID+".adj.largestPartitionNet");
			
			/*get modularity info*/
			File infoFile = new File(commID.trim()+"-fc_first_run.info");
			FileReader infoReader = new FileReader(inputFile.getParent()+"//"+infoFile);
			BufferedReader infoBf = new BufferedReader(infoReader);
			double modularity=0.0;
			String infoL = infoBf.readLine();
			while(infoL!=null){
				//Debug.outn(infoL);
				if(infoL.substring(0, 4).equals("MAXQ")){
					modularity = Double.valueOf(infoL.split(":")[1]);
					break;
				}
				infoL = infoBf.readLine();
			}
			community.setModularity(modularity);
			
			String line = br.readLine();
			
			HashSet <String> subCommunity= new HashSet <String> ();
			
			while (line != null) { //
				
				//Debug.outn(line);
				// new sub community
				if(line.charAt(0)=='G'){
					subCommunity = new HashSet <String> ();
					community.community.add(subCommunity);
				}else{			
					subCommunity.add( line );
				}			
		
				line = br.readLine();
			}///////
			br.close();
			fr.close();
		
		} catch (IOException e) {
			e.printStackTrace();		
		}
	}
	
}////////////////////////////////////////////////////////////////////////////////////
