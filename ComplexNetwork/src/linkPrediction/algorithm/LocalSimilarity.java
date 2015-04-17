package linkPrediction.algorithm;

import tools.Sets;

import java.util.*;

import network.*;
import tools.*;

public class LocalSimilarity extends AbstractPredictAlgorithm {

	
	public LocalSimilarity(){
		
		toBePredictedIndices=new Vector<String>();
		
		this.indices=new Vector<String>();
		String [] in={"CN","Salton","Jaccard","Sorenson","HubPromoted","HubDepressed","LHN","PA","AA","RA"};
		for(String i:in){
			indices.add(i);
			toBePredictedIndices.add(i);
		}
		
		
		
	}
	
	


	@Override
	public float [] computeLinkScore(AbstractNetwork net,
			AbstractV vA, AbstractV vB) {
		
		float []scoreResult=new float[toBePredictedIndices.size()];

		if (vA.equals(vB)) {

		}
		
		Map<String ,Float> scoreMap=new HashMap<String, Float>();

		float CN = 0;
		float Salton = 0;
		float Jaccard = 0;
		float Sorenson = 0;
		float HubPromoted = 0;
		float HubDepressed = 0;
		float LHN = 0;
		float PA = 0;
		float AA = 0;
		float RA = 0;

		Collection<AbstractV> vANgbs = net.getNeighbors(vA);
		Collection<AbstractV> vBNgbs = net.getNeighbors(vB);
		Set<AbstractV> unionNgbs = new HashSet<AbstractV>();
		Set<AbstractV> intersectionNgbs = new HashSet<AbstractV>();

		boolean firstInANgb = true;
		for (AbstractV ANgb : vANgbs) {

			unionNgbs.add(ANgb);

			for (AbstractV BNgb : vBNgbs) {

				if (ANgb.equals(BNgb)) {
					intersectionNgbs.add(ANgb);
				}

				if (firstInANgb) {
					unionNgbs.add(BNgb);

				}

			}
			firstInANgb = false;
		}

		// union of A's and B's neighbors
		//Debug.outn("并集 "+unionNgbs.size());
		//Debug.outn("交集 "+intersectionNgbs.size());
		float kA = vANgbs.size();
		float kB = vBNgbs.size();

		/* degrees of intersection neighbors */
		float[] ngbDegrees = new float[intersectionNgbs.size()];
		int i = 0;
		for (AbstractV v : intersectionNgbs) {
			ngbDegrees[i] = net.getNeighborCount(v);
			i++;
		}

		/* compute score for different indices */
		
		CN = intersectionNgbs.size();
		//Debug.outn("CN index "+CN);

		Salton = intersectionNgbs.size() / (float) (Math.sqrt(kA * kB));

		Jaccard = (float)intersectionNgbs.size() / (float)unionNgbs.size();

		Sorenson = 2 * intersectionNgbs.size() / (kA + kB);

		HubPromoted = 2 * intersectionNgbs.size() / (kA > kB ? kB : kA);
		

		HubDepressed = 2 * intersectionNgbs.size() / (kA > kB ? kA : kB);

		LHN = intersectionNgbs.size() / (kA * kB);

		PA = kA * kB;

		AA = 0;
		for (float degree : ngbDegrees) {
			AA = AA + (1 / (float) Math.log(degree));
		}

		RA = 0;
		for (float degree : ngbDegrees) {
			RA = RA + 1 / degree;
		}
		
		scoreMap.put("CN", CN);
		scoreMap.put("Salton", Salton);
		scoreMap.put("Jaccard", Jaccard);
		scoreMap.put("Sorenson", Sorenson);
		scoreMap.put("HubPromoted", HubPromoted);
		scoreMap.put("HubDepressed", HubDepressed);
		scoreMap.put("LHN", LHN);
		scoreMap.put("PA", PA);
		scoreMap.put("AA", AA);
		scoreMap.put("RA", RA);
		
		int j=0;
		for(String index:toBePredictedIndices)
		{
			scoreResult[j]=scoreMap.get(index);
					j++;
		}	

		return scoreResult;
	}




	@Override
	public float computeLinkScore(AbstractNetwork net, AbstractV vA, AbstractV vB,
			String index) {
		// TODO Auto-generated method stub
		return 0;
	}





}

// ///////////////////////////////////////////////////////////////////////////
