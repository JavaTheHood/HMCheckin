/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class parses all kind of server response data from Json into object.
 * 
 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
 * @version 1.0
 * 
 */
public class ResponseParser {

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 */
	private final String response;
	/**
	 * TODO Javadoc
	 */
	private JSONObject json;

	/**
	 * Ctor.
	 * 
	 * @param response
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 */
	public ResponseParser(String response) {
		this.response = response;
	}

	/**
	 * select correct parser method.
	 * 
	 * @return a hashmap holding response data
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 */
	public HashMap<String, ?> parse() {
		try {
			json = new JSONObject(response);
			String parser = json.getString("parser");
			ParserKeys key = ParserKeys.valueOf(parser);

			switch (key) {
			case UserLogin:
				return parseLogin();
			case UserRegister:
				return parseRegister();
			case UserGetFriends:
				return parseUserGetFriends();
			case GetLocation:
				return parseGetLocation();
			case UserSetState:
				return parseUserSetState();
			case FindFriend:
				return parseFindFriend();
			case SendInvitation:
				return parseSendInvitation();
			case PullInvitations:
				return parsePullInvitations();
			case UserSetLocation:
				return parseUserSetLocation();
			case UsersGetStateList:
				return parseUsersGetStateList();
			case ConfirmInvitation:
				return parseConfirmInvitation();
			case GetRoomDescription:
				return parseGetRoomDescription();
			default:
				return null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * parse UserLogin servlet response.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return a hashmap holding response data
	 */
	private HashMap<String, ?> parseLogin() {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		try {
			json = new JSONObject(response);
			JSONObject data = json.getJSONObject("data");
			hash.put("success", json.getBoolean("success"));
			hash.put("userId", data.getInt("userId"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return hash;
	}

	/**
	 * parse UserRegister servlet response.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return a hashmap holding response data
	 */
	private HashMap<String, ?> parseRegister() {
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		try {
			json = new JSONObject(response);
			responseMap.put("success", json.getBoolean("success"));
			responseMap.put("message", json.getString("message"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseMap;
	}

	/**
	 * parse GetLocation servlet response.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return a hashmap holding response data
	 */
	private HashMap<String, ?> parseGetLocation() {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		ArrayList<Wifi> wifiList = new ArrayList<Wifi>();

		try {
			json = new JSONObject(response.replaceAll("'", "\""));
			boolean success = json.getBoolean("success");

			if (success) {
				// String debug = json.getString("debug"); // debug.replace("'",
				// "\"");

				String httpMethod = json.getString("httpMethod");

				/* known access points */
				JSONArray addressList = json.getJSONArray("addressList");
				for (int i = 0; i < addressList.length(); i++) {
					JSONObject addressEntry = addressList.getJSONObject(i);

					String addr = addressEntry.getString("address");
					JSONArray rooms = addressEntry.getJSONArray("roomList");

					/* get rooms at same address */
					for (int j = 0; j < rooms.length(); j++) {
						JSONObject roomObj = rooms.getJSONObject(j);
						String ssid = roomObj.getString("apId");
						String room = roomObj.getString("room");
						int locId = roomObj.getInt("id");

						String bssid = null;
						if (httpMethod.equals("post"))
							bssid = roomObj.getString("bsid");

						wifiList.add(new Wifi(ssid, bssid.equals("null") ? null
								: bssid, locId, -1, addr, room));
					}
				}

				/* unkonwn access points */
				JSONArray unknownList = json.getJSONArray("unknownList");
				for (int j = 0; j < unknownList.length(); j++) {
					JSONObject roomObj = unknownList.getJSONObject(j);
					String ssid = roomObj.getString("ssid");
					String bsid = null;

					if (httpMethod.equals("post"))
						bsid = roomObj.getString("bsid");

					wifiList.add(new Wifi(ssid, bsid, -1, -1, null, null));
				}
			}

			hash.put("success", success);
			hash.put("parser", ParserKeys.GetLocation);
			hash.put("wifiList", wifiList);

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}

	/**
	 * parses reponse data from UserSetLocation servlet requests.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return a hashmap holding response data
	 */
	private HashMap<String, ?> parseUserSetLocation() {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		try {
			json = new JSONObject(response);
			hash.put("success", json.getBoolean("success"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		hash.put("parser", ParserKeys.UserSetLocation);
		return hash;
	}

	/**
	 * parse UserSetState servlet response.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return a hashmap holding response data
	 */
	private HashMap<String, ?> parseUserSetState() {
		HashMap<String, Object> hash = new HashMap<String, Object>();

		try {
			json = new JSONObject(response);
			hash.put("success", json.getBoolean("success"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		hash.put("parser", ParserKeys.UserSetState);
		return hash;
	}

	/**
	 * parses reponse data from FindFriend servlet requests.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return a hashmap holding response data
	 */
	private HashMap<String, ?> parseFindFriend() {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		try {
			json = new JSONObject(response);
			hash.put("success", json.getBoolean("success"));
			hash.put("data", json.get("data"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		hash.put("parser", ParserKeys.FindFriend);
		return hash;
	}

	/**
	 * parses reponse data from sendInvitation servlet requests.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return a hashmap holding response data
	 */
	private HashMap<String, ?> parseSendInvitation() {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		try {
			json = new JSONObject(response);
			hash.put("success", json.getBoolean("success"));
			hash.put("data", json.getJSONObject("data"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		hash.put("parser", ParserKeys.SendInvitation);
		return hash;
	}

	/**
	 * parses reponse data from PullInvitations servlet requests.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return a hashmap holding response data
	 */
	private HashMap<String, ?> parsePullInvitations() {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		try {
			json = new JSONObject(response);
			hash.put("success", json.getBoolean("success"));
			hash.put("data", json.getJSONObject("data"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		hash.put("parser", ParserKeys.PullInvitations);
		return hash;
	}

	/**
	 * parses reponse data from UserGetFriends servlet requests.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return a hashmap holding response data
	 */
	private HashMap<String, ?> parseUserGetFriends() {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		try {
			json = new JSONObject(response);
			hash.put("success", json.getBoolean("success"));
			if (json.has("friendlist"))
				hash.put("friendlist", json.getJSONArray("friendlist"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		hash.put("parser", ParserKeys.UserGetFriends);
		return hash;
	}

	/**
	 * parses reponse data from GetUserStateList servlet requests.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return a hashmap holding response data
	 */
	private HashMap<String, ?> parseUsersGetStateList() {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		try {
			json = new JSONObject(response);
			hash.put("success", json.getBoolean("success"));

			ArrayList<UserState> userStates = new ArrayList<UserState>();
			JSONArray data;
			json = new JSONObject(response);
			data = json.getJSONArray("data");
			for (int i = 0; i < data.length(); i++) {
				JSONObject state = data.getJSONObject(i);
				int id = state.getInt("id");
				String name = state.getString("name");
				userStates.add(new UserState(id, name));
			}

			hash.put("data", userStates);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		hash.put("parser", ParserKeys.UsersGetStateList);
		return hash;
	}

	/**
	 * parses response data from ConfirmeInvitation.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return response hashmap
	 */
	private HashMap<String, ?> parseConfirmInvitation() {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		try {
			json = new JSONObject(response);
			hash.put("success", json.getBoolean("success"));
			hash.put("message", json.getString("message"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}

	/**
	 * parses response data from GetRoomDescription.
	 * 
	 * @author <a href="mailto:irimi@hm.ed">Dominik Irimi</a>
	 * @return response hashmap
	 */
	private HashMap<String, ?> parseGetRoomDescription() {
		HashMap<String, Object> hash = new HashMap<String, Object>();
		hash.put("parser", ParserKeys.GetRoomDescription);
		try {
			json = new JSONObject(response);
			boolean success = json.getBoolean("success");
			hash.put("success", success);
			if (success) {
				ArrayList<Wifi> objList = new ArrayList<Wifi>();
				JSONArray list = json.getJSONArray("data");
				for (int i = 0; i < list.length(); i++) {
					JSONObject obj = list.getJSONObject(i);
					String address = obj.getString("address");
					String room = obj.getString("room");
					int locationId = obj.getInt("id");
					String apId = obj.getString("apId");

					objList.add(new Wifi(apId, null, locationId, -1, address,
							room));
				}
				hash.put("data", objList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}

}
