/**
 * 
 */
package hm.edu.hmcheckin.util;

import hm.edu.hmcheckin.App;
import hm.edu.hmcheckin.util.Events.FriendListEvent;
import hm.edu.hmcheckin.util.Events.InvitationsEvent;
import hm.edu.hmcheckin.util.Events.NetworkTaskFailedEvent;
import hm.edu.hmcheckin.util.Events.SearchEvent;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * 
 * @author TODO
 * 
 */
public class ResponseParserv2 {

	/**
	 * TODO Javadoc
	 */
	public static final String TAG_DATA = "data";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_INV_RECEIVED = "peopleWaitingForMyConfirmation";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_INV_OPEN = "iAmWaitingForPeoplesConfirmation";

	/**
	 * TODO Javadoc
	 */
	public static final String TAG_USERNAME = "username";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_USERID = "userId";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_LASTNAME = "lastname";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_FIRSTNAME = "firstname";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_INVITATIONID = "invitationId";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_ID = "id";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_NAME = "name";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_SUCCESS = "success";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_MESSAGE = "message";

	/**
	 * TODO Javadoc
	 */
	public static final String TAG_FRIENDLIST = "friendlist";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_LOCATION = "location";
	/**
	 * TODO Javadoc
	 */
	public static final String TAG_STATE = "state";
	public static final String TAG_ADDRESS = "address";
	public static final String TAG_ROOM = "room";
	/**
	 * TODO Javadoc
	 */
	private JSONObject json;
	/**
	 * TODO Javadoc
	 */
	private final String response;
	/**
	 * TODO Javadoc
	 */
	private final EventBus eventbus;

	/**
	 * TODO Javadoc
	 * 
	 * @param response
	 */
	public ResponseParserv2(String response) {
		this.response = response;
		eventbus = EventBus.getDefault();
	}

	/**
	 * TODO Javadoc
	 * 
	 * @return
	 */
	public ArrayList<Friend> parseFriendSearch() {
		ArrayList<Friend> friends = new ArrayList<Friend>();
		JSONArray friendsRec;
		try {
			json = new JSONObject(response);
			boolean success = json.getBoolean(TAG_SUCCESS);
			if (success) {
				friendsRec = json.getJSONArray(TAG_DATA);
				for (int i = 0; i < friendsRec.length(); i++) {
					JSONObject friend = friendsRec.getJSONObject(i);
					String userName = friend.getString(TAG_NAME);
					String lastName = friend.getString(TAG_LASTNAME);
					String firstName = friend.getString(TAG_FIRSTNAME);
					int userId = friend.getInt(TAG_ID);
					friends.add(new Friend(userId, userName, firstName, lastName, 0, 0));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			eventbus.post(new NetworkTaskFailedEvent());
			return null;
		}
		eventbus.post(new SearchEvent(friends));
		return friends;
	}

	public ArrayList<HotSpot> parseHotSpots() {
		ArrayList<HotSpot> hotspots = new ArrayList<HotSpot>();
		JSONArray data;
		try {
			json = new JSONObject(response);
			boolean success = json.getBoolean(TAG_SUCCESS);
			if (success) {
				data = json.getJSONArray(TAG_DATA);
				for (int i = 0; i < data.length(); i++) {
					JSONObject hotspot = data.getJSONObject(i);
					int id = hotspot.getInt(TAG_ID);
					String address = hotspot.getString(TAG_ADDRESS);
					String room = hotspot.getString(TAG_ROOM);
					hotspots.add(new HotSpot(id, address, room));
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		SQLiteHelper dbHelper = App.get().getDbHelper();
		dbHelper.addHotSpots(hotspots);
		App.get().getPreferences().edit().putBoolean(App.ACCESS_POINT_LOADED, true).commit();
		return hotspots;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @return
	 */
	public ArrayList<Friend> parseFriendList() {
		ArrayList<Friend> friends = new ArrayList<Friend>();
		JSONArray friendsRec;
		try {
			json = new JSONObject(response);
			boolean success = json.getBoolean(TAG_SUCCESS);
			if (success) {
				friendsRec = json.getJSONArray(TAG_FRIENDLIST);
				for (int i = 0; i < friendsRec.length(); i++) {
					JSONObject friend = friendsRec.getJSONObject(i);
					String userName = friend.getString(TAG_USERNAME);
					String lastName = friend.getString(TAG_LASTNAME);
					String firstName = friend.getString(TAG_FIRSTNAME);
					int userId = friend.getInt(TAG_USERID);
					int location = friend.getInt(TAG_LOCATION);
					int state = friend.getInt(TAG_STATE);
					friends.add(new Friend(userId, userName, firstName, lastName, state, location));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			eventbus.post(new NetworkTaskFailedEvent());
			return null;
		}
		eventbus.post(new FriendListEvent(friends));
		return friends;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @return
	 */
	public ArrayList<Invitation> parsePullInvitations() {
		ArrayList<Invitation> invitations = new ArrayList<Invitation>();
		JSONObject data;
		JSONArray invReceived;
		try {
			json = new JSONObject(response);
			boolean success = json.getBoolean(TAG_SUCCESS);
			if (success) {
				data = json.getJSONObject(TAG_DATA);
				invReceived = data.getJSONArray(TAG_INV_RECEIVED);
				for (int i = 0; i < invReceived.length(); i++) {
					JSONObject inv = invReceived.getJSONObject(i);
					String userName = inv.getString(TAG_USERNAME);
					String lastName = inv.getString(TAG_LASTNAME);
					String firstName = inv.getString(TAG_FIRSTNAME);
					int invitationId = inv.getInt(TAG_INVITATIONID);
					int userId = inv.getInt(TAG_USERID);
					invitations.add(new Invitation(userName, userId, lastName, firstName, invitationId, true));
				}
				invReceived = data.getJSONArray(TAG_INV_OPEN);
				for (int i = 0; i < invReceived.length(); i++) {
					JSONObject inv = invReceived.getJSONObject(i);
					String userName = inv.getString(TAG_USERNAME);
					String lastName = inv.getString(TAG_LASTNAME);
					String firstName = inv.getString(TAG_FIRSTNAME);
					int invitationId = inv.getInt(TAG_INVITATIONID);
					int userId = inv.getInt(TAG_USERID);
					invitations.add(new Invitation(userName, userId, lastName, firstName, invitationId, false));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			eventbus.post(new NetworkTaskFailedEvent());
			return null;
		}
		eventbus.post(new InvitationsEvent(invitations));
		return invitations;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @return
	 */
	public ArrayList<UserState> parseUserState() {
		ArrayList<UserState> userStates = new ArrayList<UserState>();
		JSONArray data;
		try {
			json = new JSONObject(response);
			boolean success = json.getBoolean(TAG_SUCCESS);
			if (success) {
				data = json.getJSONArray(TAG_DATA);
				for (int i = 0; i < data.length(); i++) {
					JSONObject state = data.getJSONObject(i);
					int id = state.getInt(TAG_ID);
					String name = state.getString(TAG_NAME);
					userStates.add(new UserState(id, name));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return userStates;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @return
	 */
	public boolean parseLogin() {
		boolean success = false;
		JSONObject data;
		try {
			json = new JSONObject(response);
			success = json.getBoolean(TAG_SUCCESS);
			if (success) {
				data = json.getJSONObject(TAG_DATA);
				int userId = data.getInt(TAG_USERID);
				App.get().getPreferences().edit().putInt(TAG_USERID, userId).commit();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * TODO Javadoc
	 * 
	 * @return
	 */
	public RegisterResponse parseRegister() {
		RegisterResponse r;
		try {
			json = new JSONObject(response);
			boolean success = json.getBoolean(TAG_SUCCESS);
			String message = json.getString(TAG_MESSAGE);
			r = new RegisterResponse(success, message);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return r;
	}
}
