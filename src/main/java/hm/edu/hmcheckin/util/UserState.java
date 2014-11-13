/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.util;

/**
 * Spinner adapter for user state in check in activity.
 * 
 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
 * @version 1.0
 * 
 */
public class UserState extends BaseListItem {
	private final int id;
	private final String name;

	/**
	 * TODO Javadoc Ctor.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @param id
	 * @param name
	 */
	public UserState(int id, String name) {
		super(ListItemType.ITEM);
		this.id = id;
		this.name = name;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return id
	 */
	public int getId() {
		return id;
	}
}
