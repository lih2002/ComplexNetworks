package linkPrediction;

import java.util.*;

import tools.RandomOperation;

/**
 * assess accuracy of result of link prediction.
 * @author gexin
 *
 */
public class AccuracyQuantify {

	/*
	 * public static float AUC(Map<PredictedEdge,Map<PredictionIndices,Float>>
	 * probeScores, Map<PredictedEdge,Map<PredictionIndices,Float>> noneScores,
	 * int compareCount) {
	 * 
	 * int n = compareCount;
	 * 
	 * int equalCount = 0; int biggerCount = 0;
	 * 
	 * for (int i = compareCount; i > 0; i++) { PredictedEdge
	 * noneE=RandomOperation.RandomSelectOne(noneScores.keySet()); float
	 * noneEScore= noneScores.get(noneE). float pS =
	 * RandomOperation.RandomSelectOne(lp.probeEdgeResult) .getScore(); if (pS >
	 * nS) { biggerCount++; } if (pS == nS) { equalCount++; } }
	 * 
	 * return (0.5f * equalCount + biggerCount) / n;
	 * 
	 * }
	 */

	public static float AUC(Vector<Float> probeScores, Vector<Float> noneScores) {
		int size = probeScores.size() > noneScores.size() ? probeScores.size()
				: noneScores.size();
		return AUC(probeScores, noneScores, size * 3);

	}
	
	
	
	
	
	
	/**
	 * AUC 标准评价预测算法
	 * @param probeLinksScores 测试集的预测分数
	 * @param noneExistLinksScores 不存在边的预测分数
	 * @param compareCount 比较次数
	 * @return
	 */
	public static float AUC(Vector<Float> probeLinksScores, Vector<Float> noneExistLinksScores,
			int compareCount) {

		int equalCount = 0;
		int biggerCount = 0;

		for (int i = compareCount; i > 0; i--) {
			float nS = RandomOperation.RandomSelectOne(noneExistLinksScores);

			float pS = RandomOperation.RandomSelectOne(probeLinksScores);
			if (pS > nS) {
				biggerCount++;
			}
			if (pS == nS) {
				equalCount++;
			}
		}

		return (0.5f * equalCount + biggerCount) / compareCount;

	}
}
