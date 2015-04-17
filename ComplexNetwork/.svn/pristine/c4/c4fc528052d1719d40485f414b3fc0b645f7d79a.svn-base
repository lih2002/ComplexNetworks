/**
 * 
 */
package network;

/**
 * @author gexin
 * 
 */
public class GeneralNetwork extends AbstractNetwork {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GeneralNetwork() {
		super();
	}

	@Override
	public AbstractE createNewEdge() {
		// TODO Auto-generated method stub
		return new GeneralEdge();
	}

	@Override
	public AbstractNetwork getBlankNetwork() {
		// TODO Auto-generated method stub
		return new GeneralNetwork();
	}

	@Override
	public AbstractV createNewVertex(String ID) {
		AbstractV v = getVertex(ID);
		if (v != null) {
			return v;
		} else {
			return new GeneralNode(ID);
		}
	}

}

class GeneralNode extends AbstractV {

	public GeneralNode(String name) {
		setID(name);
	}

}

class GeneralEdge extends AbstractE {

	public GeneralEdge() {

	}
}
