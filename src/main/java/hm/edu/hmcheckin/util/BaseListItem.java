/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.util;

/**
 * Base class for all list view item Classes.
 * 
 * @author <a herf="irimi@hm.edu">Dominik Irimi</a>
 * @version 1.0
 * 
 */
public class BaseListItem {
	/**
	 * TODO Javadoc
	 */
	private ListItemType type;

	/**
	 * Ctor.
	 * 
	 * @param Type
	 *            of a list view item
	 */
	public BaseListItem(ListItemType t) {
		this.type = t;
	}

	/**
	 * TODO Javadoc
	 */
	public ListItemType getType() {
		return type;
	}

	/**
	 * TODO Javadoc
	 */
	public void setType(ListItemType type) {
		this.type = type;
	}

}
