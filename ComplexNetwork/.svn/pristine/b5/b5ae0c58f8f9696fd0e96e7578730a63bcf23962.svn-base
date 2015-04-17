package properties;

import java.util.Vector;
import edu.uci.ics.jung.graph.util.Pair;
import tools.MathTool;
import network.*;


public class Correlation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * node node correlation
	 * 
	 * @return
	 */
	public static float degreeCorrelation(AbstractNetwork net) {
		float ddCorr = 0;
		float[] d1 = new float[net.getEdgeCount()];
		float[] d2 = new float[net.getEdgeCount()];
		Vector<AbstractE> allEdge = net.getAllEdges();
		//Debug.outn("edge number "+net.getEdgeArray().size());
		for (int i = 0; i < allEdge.size(); i++) {
			AbstractE edge =  allEdge.get(i);
			Pair<AbstractV> endPoint = net.getEndpoints(edge);
			d1[i] = (float) net.getNeighborCount(endPoint.getFirst());
					///(float)(net.getEdgesNum() * 2);
			d2[i] = (float) net.getNeighborCount(endPoint.getSecond());
					/// (float)(net.getEdgesNum() * 2);
			//Debug.outn("degree degree "+d1[i]+" "+d2[i]);

		}

		ddCorr = calculateCorrelation(d1, d2);
		//Debug.outn("degree correlation " + ddCorr);

		return ddCorr;
	}
	
	/**
	 * Calculate correlation according to Newman's formula
	 * @param k1
	 * @param k2
	 * @return
	 */
	public static float calculateCorrelation(float[] k1, float[] k2) {
		
		float[] k1k2 = new float[k1.length];
		float[] k1Andk2 = new float[k1.length];
		float[] k12Andk22 = new float[k1.length];

		for (int i = 0; i < k1.length; i++) {
			k1k2[i] = k1[i] * k2[i];
			k1Andk2[i] = (k1[i] + k2[i]);
			k12Andk22[i] = k1[i] * k1[i] + k2[i] * k2[i];
		}

		float top = 4 * MathTool.average(k1k2) - MathTool.average(k1Andk2)
				* MathTool.average(k1Andk2);
		float bot = 2 * MathTool.average(k12Andk22) - MathTool.average(k1Andk2)
				* MathTool.average(k1Andk2);

		if (bot < 1E-6 && bot > -1E-6) {
			return Float.NaN;
		}

		// Debug.outn("zi "+
		// Float.toString(4*MathTool.average(k1k2)-MathTool.average(k1Andk2)*MathTool.average(k1Andk2)
		// ));
		// Debug.outn("mu "+ Float.toString(2*MathTool.average(k12Andk22)-
		// MathTool.average(k1Andk2)*MathTool.average(k1Andk2)) );

		return top / bot;
	}

}
