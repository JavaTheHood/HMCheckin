/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin;

import hm.edu.hmcheckin.util.AsyncNetworkTask;
import hm.edu.hmcheckin.util.SQLiteHelper;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * TODO Javadoc
 */
public class App extends Application {

	/**
	 * TODO Javadoc
	 */
	public static final String GET_FRIENDS = "get_friends";
	/**
	 * TODO Javadoc
	 */
	public static final String GET_INVITATIONS = "get_invitations";
	/**
	 * TODO Javadoc
	 */
	public static final String GET_SEARCH = "get_search";
	/**
	 * TODO Javadoc
	 */
	public static final String GET_DEFAULT = "get_default";
	public static final String GET_HOTSPOTS = "get_hotspots";
	public static final String ACCESS_POINT_LOADED = "accesspointsloaded";

	/**
	 * TODO Javadoc
	 */
	public static final int USERID_DEFAULT = 0;

	/**
	 * TODO Javadoc
	 */
	private static App instance;
	/**
	 * TODO Javadoc
	 */
	private SharedPreferences preferences;
	private SQLiteHelper dbHelper;

	/**
	 * TODO Javadoc
	 */
	public static App get() {
		if (instance == null) {
			throw new RuntimeException("Internal error: app is uninitialized");
		}
		return instance;
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		if (instance != null) {
			Log.w("HMCheckin", "App initialized more than once - are we in a unit test?");
		}
		instance = this;

		preferences = getSharedPreferences("HMCheckin", Context.MODE_PRIVATE);
		dbHelper = new SQLiteHelper(this);
		if (!preferences.getBoolean(ACCESS_POINT_LOADED, false)) {
			new AsyncNetworkTask().execute("http://moan.cs.hm.edu:8080/HmCheckIn/GetAccessPointList", GET_HOTSPOTS);
		}
	}

	/**
	 * TODO Javadoc
	 */
	public SharedPreferences getPreferences() {
		return preferences;
	}

	public SQLiteHelper getDbHelper() {
		return dbHelper;
	}
}
