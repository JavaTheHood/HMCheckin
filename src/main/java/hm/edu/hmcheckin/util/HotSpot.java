package hm.edu.hmcheckin.util;

public class HotSpot {

	private final int id;
	private final String address;
	private final String room;

	public HotSpot(int id, String address, String room) {
		this.id = id;
		this.address = address;
		this.room = room;
	}

	public int getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public String getRoom() {
		return room;
	}
}