/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.util;

import java.io.Serializable;

/**
 * A user in friendlist.
 * 
 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
 * @version 1.0
 * 
 */
public class Friend extends BaseListItem implements Serializable {
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	private int userId = -1;
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	private String firstname = "";
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	private String lastname = "";
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	private String login = "";
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	private int status = -1;
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	private int location = -1;

	/**
	 * Ctor.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param id
	 * @param login
	 *            (username)
	 * @param firstName
	 * @param lastName
	 * @param status
	 * @param location
	 */
	public Friend(int id, String login, String firstName, String lastName, int status, int location) {
		super(ListItemType.ITEM);

		this.userId = id;
		this.login = login;
		this.firstname = firstName;
		this.lastname = lastName;
		this.status = status;
		this.location = location;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	public int getLocation() {
		return location;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	public void setStatus(int status) {
		this.status = status;
	}
}
