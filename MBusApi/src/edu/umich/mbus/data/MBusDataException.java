/**
 * 
 */
package edu.umich.mbus.data;

/**
 * Base exception type of MBus data exception.
 * @author gopalkri
 */
public class MBusDataException extends Exception {

	/**
	 * Eclipse says I should create this.
	 */
	private static final long serialVersionUID = -4854189516802075400L;

	public MBusDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public MBusDataException(String message) {
		super(message);
	}
}
