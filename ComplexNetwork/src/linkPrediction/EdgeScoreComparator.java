
package linkPrediction;

import java.util.Comparator;

/**
 * �Ƚ�����
 * 
 * @author ����
 * 
 */
public class EdgeScoreComparator implements Comparator <String>{

	public int compare(String id1, String id2) {
		
		if (Integer.parseInt((String) id1) > Integer.parseInt((String) id2)) {
			return 1;
		} else if (Integer.parseInt((String) id1) < Integer
				.parseInt((String) id2)) {
			return -1;
		} else
			return 0;
	}

}
