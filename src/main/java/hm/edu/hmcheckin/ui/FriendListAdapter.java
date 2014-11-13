/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.AsyncHttpClient;
import hm.edu.hmcheckin.util.AsyncHttpClient.AsyncHttpListener;
import hm.edu.hmcheckin.util.BaseListItem;
import hm.edu.hmcheckin.util.Friend;
import hm.edu.hmcheckin.util.ParserKeys;
import hm.edu.hmcheckin.util.ResponseParser;
import hm.edu.hmcheckin.util.Wifi;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Activity which presents a list of friends, and options to add new ones.
 */
public class FriendListAdapter extends BaseAdapter implements AsyncHttpListener {

	/**
	 * TODO Javadoc
	 */
	private final ArrayList<BaseListItem> friendList = new ArrayList<BaseListItem>();
	/**
	 * TODO Javadoc
	 */
	private final HashMap<Integer, String> userStateList,
	/**
	 * TODO Javadoc
	 */
	roomList;
	/**
	 * TODO Javadoc
	 */
	private final LayoutInflater inflater;
	/**
	 * TODO Javadoc
	 */
	private ListView listView;

	/**
	 * Ctor.
	 * 
	 * @param context
	 */
	public FriendListAdapter(Context context) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		userStateList = new HashMap<Integer, String>();
		roomList = new HashMap<Integer, String>();
	}

	/**
	 * Set list view.
	 * 
	 * @param listView
	 */
	public void setListView(ListView v) {
		this.listView = v;
	}

	/**
	 * TODO Javadoc
	 */
	public void addItem(Friend friend) {
		friendList.add(friend);
		notifyDataSetChanged();
	}

	/**
	 * TODO Javadoc
	 */
	public void addItems(ArrayList<BaseListItem> list) {
		for (BaseListItem i : list) {
			friendList.add(i);
		}
		notifyDataSetChanged();
	}

	/**
	 * TODO Javadoc
	 */
	public void clear() {
		friendList.clear();
		notifyDataSetChanged();
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public int getCount() {
		return friendList.size();
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public Friend getItem(int index) {
		return (Friend) friendList.get(index);
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView messageView;

		if (v == null)
			v = inflater.inflate(R.layout.row_friendslist, null);

		Friend friend = (Friend) friendList.get(position);

		switch (friend.getType()) {
		case ITEM:
			v = inflater.inflate(R.layout.row_friendslist, null);

			/* set list item labels */
			if (friend != null) {
				TextView nameView = (TextView) v
						.findViewById(R.id.textView_friendName);
				TextView locationView = (TextView) v
						.findViewById(R.id.textView_friendLocation);
				TextView statusView = (TextView) v
						.findViewById(R.id.textView_friendStatus);

				nameView.setText(friend.getLogin());

				int location = friend.getLocation();
				String locationText = "";
				if (roomList.get(location) != null)
					locationText = roomList.get(location);
				else
					new AsyncHttpClient(this)
							.execute("http://moan.cs.hm.edu:8080/HmCheckIn/GetRoomDescription?locationId="
									+ location);

				/* get user state as text */
				String stateText = friend.getStatus() + "";
				int stateKey = friend.getStatus();
				if (userStateList.containsKey(stateKey))
					stateText = userStateList.get(stateKey);
				else {
					// TODO request missing states from db

					stateText = "unbekannt";
				}

				if (locationText.length() == 0) {
					locationText = location + "";
				}

				statusView.setText("Status: " + stateText);
				locationView.setText("ist gerade in Raum: " + locationText);
			}
			break;
		case SEARCHRESULTITEM:
			v = inflater.inflate(R.layout.row_friend_search_result, null);

			/* set list item labels */
			TextView username = (TextView) v
					.findViewById(R.id.textView_username);
			TextView firstname = (TextView) v
					.findViewById(R.id.textView_firstname);
			TextView lastname = (TextView) v
					.findViewById(R.id.textView_lastname);

			username.setText(friend.getLogin());
			firstname.setText(friend.getFirstname());
			lastname.setText(friend.getLastname());
			break;
		case OPENINVITATION:
			v = inflater.inflate(R.layout.friendlist_invitation_item, null);
			TextView firstname2 = (TextView) v
					.findViewById(R.id.textView_firstname);
			// TextView lastname2 = (TextView)
			// v.findViewById(R.id.textView_lastname);
			TextView message = (TextView) v.findViewById(R.id.textView_message);

			firstname2.setText(friend.getFirstname() + " "
					+ friend.getLastname());
			// lastname2.setText(friend.getLastname());
			message.setText(friend.getStatus());
			break;
		case HEADER:
			v = inflater.inflate(R.layout.row_list_seperator, null);
			messageView = (TextView) v.findViewById(R.id.editText_seperator);
			messageView.setText(friend.getLogin());
			break;
		case NOTIFICATION:
			v = inflater.inflate(R.layout.row_list_seperator, null);
			messageView = (TextView) v.findViewById(R.id.editText_seperator);
			messageView.setText(friend.getLogin());
			break;
		default:
			break;
		}
		return v;
	}

	/**
	 * Gives the name of a user state from it's id.
	 * 
	 * @param state
	 *            id
	 * @return state name or null of its not in the list
	 */
	public String getStateName(int id) {
		if (userStateList.containsKey(id))
			return userStateList.get(id);
		else
			return null;
	}

	/**
	 * Add a new user state.
	 * 
	 * @param state
	 *            id
	 * @param state
	 *            name
	 */
	public void addState(int id, String name) {
		userStateList.put(id, name);
		notifyDataSetChanged();
	}

	/**
	 * Returns the size of the user state list.
	 * 
	 * @return size of the list
	 */
	public int getStateListSize() {
		return userStateList.size();
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public void onAsyncTaskComplete(String result) {
		// parse response
		HashMap<String, ?> responseMap = new ResponseParser(result).parse();

		try {
			ParserKeys key = (ParserKeys) responseMap.get("parser");
			switch (key) {
			case GetRoomDescription:
				ArrayList<Wifi> wifiList = (ArrayList<Wifi>) responseMap
						.get("data");
				for (Wifi w : wifiList)
					roomList.put(w.getLocationId(), w.getLoc_room());
				updateLocationOnViews();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update location text in list view items.
	 */
	private void updateLocationOnViews() {
		for (int i = 0; i < listView.getCount(); i++) {
			ViewGroup row = (ViewGroup) listView.getChildAt(i);
			TextView locationView = (TextView) row
					.findViewById(R.id.textView_friendLocation);
			TextView userNameView = (TextView) row
					.findViewById(R.id.textView_friendName);

			String locationText = "";
			for (BaseListItem b : friendList) {
				Friend f = (Friend) b;
				if (f.getFirstname().equals(userNameView.getText())) {
					locationText = roomList.get(f.getLocation());
					break;
				}
			}

			if (locationText != null)
				locationView.setText("ist gerade in Raum: " + locationText);
		}
	}
}
