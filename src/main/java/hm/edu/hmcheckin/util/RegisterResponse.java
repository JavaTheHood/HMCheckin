/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.util;

/**
 * TODO Javadoc
 */
public class RegisterResponse {

	/**
	 * TODO Javadoc
	 */
	private boolean success;
	/**
	 * TODO Javadoc
	 */
	private String message;

	/**
	 * TODO Javadoc
	 */
	public RegisterResponse(boolean success, String message) {
		this.setSuccess(success);
		this.setMessage(message);
	}

	/**
	 * TODO Javadoc
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * TODO Javadoc
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * TODO Javadoc
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * TODO Javadoc
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

}
