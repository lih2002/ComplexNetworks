package io;

import network.*;
import java.util.*;
import edu.uci.ics.jung.graph.util.Pair;;


public class NetworkToFile {
	
	public static void saveToFile(AbstractNetwork net, String filePathName) {
		
		Collection<AbstractE> allEdges = net.getEdges();
		Iterator<AbstractE> edgesIt = allEdges.iterator();
		StringBuffer outputLink = new StringBuffer();
		while (edgesIt.hasNext()) {
			AbstractE newEdge = edgesIt.next();
			Pair<AbstractV> endPoints =net.getEndpoints(newEdge);

			outputLink.append(endPoints.getFirst() + " ");
			outputLink.append(endPoints.getSecond() + "\n");
		}

		FileOperation.saveStringToFile(filePathName, outputLink.toString(),
				false);
	}

}
