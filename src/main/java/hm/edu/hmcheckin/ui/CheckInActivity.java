/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.App;
import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.AsyncHttpClient;
import hm.edu.hmcheckin.util.AsyncHttpClient.AsyncHttpListener;
import hm.edu.hmcheckin.util.BaseListItem;
import hm.edu.hmcheckin.util.ParserKeys;
import hm.edu.hmcheckin.util.ResponseParser;
import hm.edu.hmcheckin.util.UserState;
import hm.edu.hmcheckin.util.Wifi;
import hm.edu.hmcheckin.util.WifiConnector;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Provides a list of selectable locations based on available wifi networks.
 * 
 * @author <a href="podolski@hm.edu">Dominik Irimi</a>
 * @version 1.0
 * 
 */
public class CheckInActivity extends ListActivity implements AsyncHttpListener {
	/**
	 * TODO Javadoc
	 */
	private App appState;
	/**
	 * TODO Javadoc
	 */
	private int userId;
	/**
	 * TODO Javadoc
	 */
	private WifiListAdapter wifiListAdapter;
	/**
	 * TODO Javadoc
	 */
	private SpinnerAdapter spinnerAdapter;
	/**
	 * TODO Javadoc
	 */
	private WifiConnector wifiConn;
	/**
	 * TODO Javadoc
	 */
	private final ArrayList<Wifi> locationList = new ArrayList<Wifi>();
	/**
	 * TODO Javadoc
	 */
	private final int wifiSignMax = 0, wifiSignMin = 100;

	/**
	 * Once called on activity creation.
	 * 
	 * @param saved
	 *            instance state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_in);

		appState = (App) this.getApplication();

		/* get user id */
		SharedPreferences prefs = appState.getPreferences();
		userId = prefs.getInt("userId", App.USERID_DEFAULT);

		wifiListAdapter = new WifiListAdapter(this);

		/* request wifi */
		wifiConn = new WifiConnector(this);

		/* populate wifi list */
		updateWifiList();

		getListView().setAdapter(wifiListAdapter);

		/* populate user state spinner */
		Spinner stateSpinner = (Spinner) findViewById(R.id.state_spinner);
		spinnerAdapter = new SpinnerAdapter(this, stateSpinner);
		stateSpinner.setAdapter(spinnerAdapter);

		/* state spinner on click listener */
		stateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			/**
			 * state spinner on click handler.
			 * 
			 * @param parentView
			 * @param selectedView
			 * @param position
			 * @param id
			 */
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItem, int position, long id) {
				UserState state = spinnerAdapter.getItem(position);
				int stateId = state.getId();
				setUserState(stateId);
			}

			/**
			 * callback for no selected item. (not implemented)
			 * 
			 * @param view
			 */
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		new AsyncHttpClient(this)
				.execute("http://moan.cs.hm.edu:8080/HmCheckIn/UsersGetStateList");

	}

	/**
	 * Called once if menu called first.
	 * 
	 * @param menu
	 * @return true
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.check_in, menu);
		return true;
	}

	/**
	 * callback method for menu item selected
	 * 
	 * @param item
	 * @return success
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.Wifi_scan_btn:
			updateWifiList();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * ListView item click listener.
	 * 
	 * @param list
	 * @param view
	 * @param position
	 * @param id
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		int locId = ((Wifi) wifiListAdapter.getItem(position)).getLocationId();
		String room = ((Wifi) wifiListAdapter.getItem(position)).getLoc_room();

		Log.d("CheckIn at", (room != null ? room : "unbekannt") + " | " + locId); // TODO
																					// schoenere
																					// Benachrichtigung

		// set new user position at server, if location known
		if (locId > -1)
			new AsyncHttpClient(this)
					.execute("http://moan.cs.hm.edu:8080/HmCheckIn/UserSetLocation?userId="
							+ userId + "&location=" + locId);
	}

	/**
	 * Callback event listener for GetLocation servlet request.
	 * 
	 * @param result
	 */
	@Override
	public void onAsyncTaskComplete(String result) {
		boolean success;

		// parse response
		HashMap<String, ?> responseMap = new ResponseParser(result).parse();
		ParserKeys key = (ParserKeys) responseMap.get("parser");
		switch (key) {
		case GetLocation:
			ArrayList<Wifi> networkList = (ArrayList<Wifi>) responseMap
					.get("wifiList");

			// update list item location
			for (Wifi w : networkList) {
				String addr = w.getLoc_addr(), room = w.getLoc_room();
				int locId = w.getLocationId();

				if (locId == -1) {
					// remove unknown wifi from list view
					locationList.remove(w);
				} else {
					setWifiLocation(w.getSsid(), w.getBsId(), locId, addr, room);
				}
			}

			//
			// int goodWifiCounter = 0, signLimit = 70;
			// while(goodWifiCounter <= locationList.size() && signLimit > 10){
			// signLimit -= 10;
			//
			// //count well accessable wifis
			// for(Wifi w : locationList){
			// int sig = w.getSignalLevel();
			// if(sig > signLimit)
			// goodWifiCounter++;
			// Log.w("Signallevel", w.getSsid()+"   "+w.getSignalLevel()+"");
			//
			// }
			// }
			// Log.i("goodWifiCounter: ", goodWifiCounter+"");

			//
			ArrayList<Wifi> tmpWifiList = new ArrayList<Wifi>();
			for (Wifi w : locationList)
				// if(w.getSignalLevel() >= signLimit)
				tmpWifiList.add(w);

			try {
				// add new data entry
				tmpWifiList.add(new Wifi("Ich bin woanders!", "null", -1, -1,
						null, "Mein Aufenthaltsort ist nicht in der Liste"));
				tmpWifiList.add(new Wifi("Ich bin offline", "null", 0, -1,
						null, "anderen nicht anzeigen wo ich bin"));

				wifiListAdapter.setWifiList(tmpWifiList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case UserSetState:
			success = (Boolean) responseMap.get("success");
			Toast.makeText(this, "CheckIn - Status: " + success,
					Toast.LENGTH_LONG).show();
			break;
		case UserSetLocation:
			success = (Boolean) responseMap.get("success");
			Toast.makeText(this, "CheckIn - Location: " + success,
					Toast.LENGTH_LONG).show();
			break;
		case UsersGetStateList:
			success = (Boolean) responseMap.get("success");
			ArrayList<UserState> stateList = (ArrayList<UserState>) responseMap
					.get("data");

			spinnerAdapter.clearList();
			spinnerAdapter.addItems(stateList);
			// stateSpinner.setEnabled(true);
		default:
			break;
		}
	}

	/**
	 * Scan for wifis and update list view.
	 */
	private void updateWifiList() {
		ArrayList<Wifi> networkList = wifiConn.getnetworkList();

		locationList.clear();
		locationList.addAll(networkList);

		/* populate wifi list */
		// HTTP POST
		String[] query = new String[100];
		query[0] = "http://moan.cs.hm.edu:8080/HmCheckIn/GetLocation?";
		query[1] = "1"; // http Method -> POST

		// add request data
		wifiListAdapter.clearList();
		try {
			JSONArray list = new JSONArray();
			for (int i = 0; i < networkList.size(); i++) {

				wifiListAdapter.addItems(networkList);
				list.put(new JSONObject().put("ssid",
						networkList.get(i).getSsid() + "").put("bsid",
						networkList.get(i).getBsId() + ""));
			}

			query[2] = new JSONObject().put("list", list).toString();

		} catch (JSONException e) {
			e.printStackTrace();
		}

		// request wifi location from server
		new AsyncHttpClient(this).execute(query);
	}

	/**
	 * Set the location of wifi network.
	 * 
	 * @param bsid
	 *            access point ssid
	 * @param bsid
	 *            access point mac address
	 * @param locationId
	 *            room id
	 * @param addr
	 *            address
	 * @param room
	 *            description
	 */
	private void setWifiLocation(String ssid, String bsid, int locationId,
			String addr, String room) {
		for (BaseListItem item : locationList) {
			Wifi net = (Wifi) item;
			if (net.getBsId().equals(bsid)) {
				net.setLoc_addr(addr);
				net.setLoc_room(room);
				net.setLocationId(locationId);
				break;
			}
		}
	}

	/**
	 * set new user state from spinner selection to db.
	 * 
	 * @param stateId
	 */
	private void setUserState(int stateId) {
		new AsyncHttpClient(this)
				.execute("http://moan.cs.hm.edu:8080/HmCheckIn/UserSetState?userId="
						+ userId + "&state=" + stateId);
	}

}
