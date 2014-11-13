/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.util;

/**
 * TODO Javadoc
 */
public class Invitation {

	/**
	 * TODO Javadoc
	 */
	private String login;
	/**
	 * TODO Javadoc
	 */
	private int userId;
	/**
	 * TODO Javadoc
	 */
	private String lastName;
	/**
	 * TODO Javadoc
	 */
	private String firstName;
	/**
	 * TODO Javadoc
	 */
	private int invitationId;
	/**
	 * TODO Javadoc
	 */
	private final boolean needConfirm;

	/**
	 * TODO Javadoc
	 */
	public Invitation(String userName, int userId, String lastName,
			String firstName, int invitationId, boolean needConfirm) {
		this.login = userName;
		this.userId = userId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.invitationId = invitationId;
		this.needConfirm = needConfirm;
	}

	/**
	 * TODO Javadoc
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * TODO Javadoc
	 */
	public void setLogin(String userName) {
		this.login = userName;
	}

	/**
	 * TODO Javadoc
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * TODO Javadoc
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * TODO Javadoc
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * TODO Javadoc
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * TODO Javadoc
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * TODO Javadoc
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * TODO Javadoc
	 */
	public int getInvitationId() {
		return invitationId;
	}

	/**
	 * TODO Javadoc
	 */
	public void setInvitationId(int invitationId) {
		this.invitationId = invitationId;
	}

	/**
	 * @return the needConfirm
	 */
	public boolean isNeedConfirm() {
		return needConfirm;
	}
}
