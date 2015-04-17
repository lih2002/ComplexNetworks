/**
 * GIS tools.
 */
package tools;

/**
 * @author gexin
 * 
 */
public class GIS {

	private static double EARTH_RADIUS = 6378.137;// µØÇò°ë¾¶
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		double d=GetDistance(30.1811204101, -97.7452111244, 30.2691029532, -97.7493953705);
		System.out.print(d);

	}



	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * get distance according to latitude and longitude
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

}
