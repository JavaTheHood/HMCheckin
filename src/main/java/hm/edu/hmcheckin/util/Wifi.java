/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.util;

/**
 * Container class of a single wifi access point.
 * 
 * @author Acer TODO
 * @version 1.0
 * 
 */
public class Wifi extends BaseListItem {
	private final String ssId, bssId;
	private final int signalLevel;
	private String loc_addr, loc_room;
	private int locationId;

	/**
	 * Ctor.
	 * 
	 * @param ssId
	 *            access points SSID
	 * @param bssId
	 *            access points MAC address
	 * @param locationId
	 *            db wifisaccesspoints table id
	 * @param level
	 *            signal stength
	 * @param addr
	 * @param room
	 *            TODO
	 * @throws Exception
	 *             params missing
	 * @author TODO
	 */
	public Wifi(String ssId, String bssId, int locId, int level, String addr,
			String room) throws Exception {
		super(ListItemType.ITEM);
		//
		// if(ssId.length()<1 || bssId.length()<1)
		// throw new Exception("arguments missing");

		this.ssId = ssId;
		this.bssId = bssId;
		this.locationId = locId;
		this.signalLevel = level;
		this.loc_addr = addr;
		this.loc_room = room;
	}

	/**
	 * Equals. TODO Description
	 * 
	 * @return true if given Wifi and this are same.
	 * @author TODO
	 */
	@Override
	public boolean equals(Object that) {
		if (that == null)
			return false;
		if (this.getClass() != that.getClass())
			return false;
		Wifi other = (Wifi) that;
		if (!getBsId().equals(other.getBsId()))
			return false;
		if (getLocationId() != (other.getLocationId()))
			return false;
		return true;
	}

	/**
	 * Setter. TODO descripton
	 * 
	 * @param loc_addr
	 * @author TODO
	 */
	public void setLoc_addr(String loc_addr) {
		this.loc_addr = loc_addr;
	}

	/**
	 * Setter. TODO description
	 * 
	 * @param loc_room
	 * @author TODO
	 */
	public void setLoc_room(String loc_room) {
		this.loc_room = loc_room;
	}

	/**
	 * Getter. TODO descripton
	 * 
	 * @return address
	 * @author TODO
	 */
	public String getLoc_addr() {
		return loc_addr;
	}

	/**
	 * Getter.
	 * 
	 * @return room
	 * @author TODO
	 */
	public String getLoc_room() {
		return loc_room;
	}

	/**
	 * Getter.
	 * 
	 * @return wifi ssid
	 * @author TODO
	 */
	public String getSsid() {
		return ssId;
	}

	/**
	 * Getter.
	 * 
	 * @return wifi ssid
	 * @author TODO
	 */
	public String getBsId() {
		return bssId;
	}

	/**
	 * TODO
	 * 
	 * @return
	 * @author TODO
	 */
	public int getLocationId() {
		return locationId;
	}

	/**
	 * TODO
	 * 
	 * @param locationId
	 *            TODO
	 * @author TODO
	 */
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	/**
	 * Getter. TODO
	 * 
	 * @return wifi signal level
	 * @author TODO
	 */
	public int getSignalLevel() {
		return signalLevel;
	}
}
