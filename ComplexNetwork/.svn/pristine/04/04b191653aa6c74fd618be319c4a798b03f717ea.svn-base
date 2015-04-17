package properties;
import network.*;
import tools.*;

/**
 * @author ge xin
 *
 */
public class DegreeDistribution {
	
	/**
	 * 计算度分布,即各个度值节点数量占总节点数量的百分比
	 * @return
	 */
	public static double[] PDF(AbstractNetwork net){
		
		//float []pdf=new float[net.getMaxDegree()+1];
//		int[]degrees=net.getAllDegrees();
//		for(int i=0;i<degrees.length;i++)pdf[degrees[i]]++;
//		for(int i=0;i<pdf.length;i++){
//			pdf[i]/=degrees.length;
//		}
		
		int [] dPdf = MathTool.variablePDF(net.getAllVertexDegrees());
//		int nullZeroNum = 0;
//		for(int i=0;i<dPdf.length;i++){ //共有多少个不同的非零度值
//			if(dPdf[i]!=0) i++;
//		}
//		double [][]pdf = new double[nullZeroNum][2];
		int nodeNum = net.getVertexCount();
		double []pdf = new double[dPdf.length];
		for(int i=0;i<pdf.length;i++){
			pdf[i]=(double)dPdf[i]/(double)nodeNum;
		}		
		return pdf;
	}
	
	public static int[][] numPDF(AbstractNetwork net){
		int [] dPdf = MathTool.variablePDF(net.getAllVertexDegrees());
		int nullZeroNum = 0;
		for(int i=0;i<dPdf.length;i++){ //共有多少个不同的非零度值
			if(dPdf[i]!=0) nullZeroNum++;
		}
		int [][] numPdf = new int[nullZeroNum][2];

		//int nodeNum = net.getVertexCount();
		
		int index=0;
		for(int i=0;i<dPdf.length;i++){
			if(dPdf[i]!=0){
				numPdf[index][0] = i;
				numPdf[index][1] = dPdf[i];
				index++;
			}
		}
					
		return numPdf ;
	}
	
	public static double[][]percentPDF(AbstractNetwork net){
		int [][] numPDF = numPDF(net);
		int nodeNum = net.getVertexCount();
		double [][] pPDF = new double[numPDF.length][2];
		for(int i=0;i<numPDF.length;i++){
			pPDF[i][0] = numPDF[i][0];
			pPDF[i][1]=(double)numPDF[i][1]/nodeNum;
		}
		return pPDF;
	}
	
	/**
	 * Cumulative degree distribution.
	 * @param net
	 * @return
	 */
	public static double [] CCDF(AbstractNetwork net){
		double []pdf=PDF(net);
		double[]ccdf=new double [pdf.length];
		double cumulative=0;
		
		for(int i=pdf.length-1;i>=0;i--){
			cumulative+=pdf[i];
			ccdf[i]=cumulative;
		}
		return ccdf;
	}
	/**
	 * Cut off of degree distribution.
	 * </p>
	 * @param net
	 * @return
	 */
	public static double ExponentialCutoff(AbstractNetwork net){
		int [] dPdf = MathTool.variablePDF( net.getAllVertexDegrees() );
		
		//度值相同节点数量 相同的数量
		int []numPdf = MathTool.variablePDF(dPdf);
		for(int i =0;i<numPdf.length;i++){
			if(numPdf [i]>=1)
				numPdf [i]--;
		}
	
		//Debug.outn()
		double cutOff=( (double)MathTool.sum(numPdf)-numPdf[0] )/(double)numPDF(net).length; ;
		//Debug.outn(MathTool.sum(numPdf)-numPdf[0]+" "+numPDF(net).length);
		//Debug.outn( cutOff);
		
		return cutOff;
	}

}
