/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.util;

import java.util.ArrayList;

/**
 * TODO Javadoc
 */
public class Events {

	/**
	 * TODO Javadoc
	 */
	public static class NetworkTaskFailedEvent {

	}

	/**
	 * TODO Javadoc
	 */
	public static class FriendListEvent {
		/**
		 * TODO Javadoc
		 */
		public final ArrayList<Friend> friends;

		/**
		 * TODO Javadoc
		 */
		public FriendListEvent(ArrayList<Friend> friends) {
			this.friends = friends;
		}
	}

	/**
	 * TODO Javadoc
	 */
	public static class InvitationsEvent {

		/**
		 * TODO Javadoc
		 */
		public final ArrayList<Invitation> invitations;

		/**
		 * TODO Javadoc
		 */
		public InvitationsEvent(ArrayList<Invitation> invitations) {
			this.invitations = invitations;
		}
	}

	/**
	 * TODO Javadoc
	 */
	public static class SearchEvent {
		/**
		 * TODO Javadoc
		 */
		public final ArrayList<Friend> friends;

		/**
		 * TODO Javadoc
		 */
		public SearchEvent(ArrayList<Friend> friends) {
			this.friends = friends;
		}
	}

	public static class HotSpotEvent {
		public final ArrayList<HotSpot> hotspots;

		public HotSpotEvent(ArrayList<HotSpot> hotspots) {
			this.hotspots = hotspots;
		}
	}
}
