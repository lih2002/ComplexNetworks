/**
 * 
 */
package exception;

/**
 * 网络异常类，处理网络相关的异常
 * @author Administrator
 *
 */
public class NetworkException extends ComplexException {
	public NetworkException(String msg){
		super(msg);
	}
}
