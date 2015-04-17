/**
 * 
 */
package exception;

/**
 * 网络数据未初始化异常。
 * 该异常在网络内数据被使用却没有初始化数据时被抛出。
 * @author 葛新
 *
 */
public class NullNetworkDataException extends NetworkException {

	public NullNetworkDataException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
}
