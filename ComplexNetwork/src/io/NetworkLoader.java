/**
 * 
 */
package io;

import network.AbstractNetwork;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author gexin
 * load network data 
 */
public class NetworkLoader {
	

	public static boolean loadDataFromOneFile( AbstractNetwork network,String filePathName) {

		try {

			FileReader fr = new FileReader(new File(filePathName));
			BufferedReader br = new BufferedReader(fr);

			// set network ID

			String oneLine = br.readLine();
			while (oneLine != null) { // read line. one edge in one line.

				String[] stokens = oneLine.split("[\t ]");

				if (stokens.length > 1) {

					String v1 = stokens[0].trim();
					String v2 = stokens[1].trim();
					
					network.addEdge(network.createNewEdge(), network.createNewVertex(v1), 
							network.createNewVertex(v2));

				}

			}

		} catch (IOException ioe) {
			return false;
		}

		return true;
	}
	

}/////////////////////////////////////////////////////////////////////////
