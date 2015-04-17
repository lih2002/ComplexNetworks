/**
 * 
 */
package network;

import tools.Debug;
import tools.RandomOperation;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.Pair;

import java.util.*;

/**
 * @author gexin
 * 
 */
public class TestNetwork {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestNetwork().test();

	}
	
	public static void dump(AbstractNetwork net){
		Debug.outn("N "+net.getVertexCount()+"  L "+net.getEdgeCount());
		for(AbstractV v:net.getVertices()){
			//Debug.outn(v.getID());
		}
		for(AbstractE e:net.getEdges()){
			Pair<AbstractV> p=net.getEndpoints(e);
			//Debug.outn(p.getFirst()+" - "+p.getSecond());
		}
	}
	
	public void test2(){
		
		SimpleNetwork net=new SimpleNetwork();
		
		for (int i = 0; i < 3; i++) {
			
			
			net.addEdge(Integer.toString(i),
					Integer.toString(i+1));
			
		}
		
		
		
		for (int i = 0; i < 3; i++) {					
			
			net.addEdge( Integer.toString(i),
					Integer.toString(i+2));
			
		}
		
		//net.addVertex("100");
		

	if (net.containsVertex("100")) {
			Debug.outn("contain");
		} else {
			Debug.outn("not contain");
		}
		

		for(String e:net.getEdges()){
			Debug.outn(e);
			//Debug.out(net.getEndpoints(e).getFirst()+"гнгн");
			//Debug.outn(net.getEndpoints(e).getSecond());
		}

		Debug.outn("node count " + net.getVertexCount()+
				" edge count "+ net.getEdgeCount());
		
	}

	public void test() {
		
		UndirectedSparseGraph<String,String> testNet;
		testNet=new UndirectedSparseGraph<String,String>();
		
		testNet.addVertex(Integer.toString(1));
		testNet.addVertex(Integer.toString(2));
		testNet.addVertex(Integer.toString(3));
		testNet.addVertex(Integer.toString(3));
		//Debug.outn("test net "+testNet.containsVertex("3"));
		//Debug.outn("test net "+testNet.getVertexCount());
		
		
		
		GeneralNetwork net = new GeneralNetwork();



		for (int i = 0; i < 400; i++) {
					
		
			net.addEdge(Integer.toString(i),
					Integer.toString(i+1));
			
		}		
		
		for (int i = 0; i < 4; i++) {					
			
			net.addEdge( Integer.toString(i),
					Integer.toString(i+1));			
		}
		
		//net.addVertex(net.createNewVertex("100"));
		//net.removeVertex(net.getAddedVertex("2"));

		AbstractV newNode = net.createNewVertex("1");

		if (net.containsVertex(newNode)) {
			Debug.outn("contain");
		} else {
			Debug.outn("not contain");
		}
		
	//	net.removeEdge("0", "1");
		//net.removeVertex("0");
		
		
		for(int i=0;i<10;i++){
			AbstractE e;
			e=RandomOperation.RandomSelectOne(net.getEdges());
			Debug.out(net.getEndpoints(e).getFirst()+"гнгн");
			Debug.outn(net.getEndpoints(e).getSecond());
		}
		
		
		for(AbstractE e:net.getEdges()){
			//Debug.out(net.getEndpoints(e).getFirst()+"гнгн");
			//Debug.outn(net.getEndpoints(e).getSecond());
		}

		Debug.outn("node count " + net.getVertexCount()+
				" edge count "+ net.getEdgeCount());
		String m="d";
		String n="b";
		Debug.outn(m.compareTo(n));
		
		

	}

}
