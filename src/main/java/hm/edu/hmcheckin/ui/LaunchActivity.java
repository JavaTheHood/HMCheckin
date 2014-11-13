/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.App;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * TODO Javadoc
 */
public class LaunchActivity extends Activity {

	/**
	 * TODO Javadoc
	 */
	private int userId;

	/**
	 * TODO Javadoc
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userId = App.get().getPreferences().getInt("userId", App.USERID_DEFAULT);
		if (userId == App.USERID_DEFAULT) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent(this, LandingActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
