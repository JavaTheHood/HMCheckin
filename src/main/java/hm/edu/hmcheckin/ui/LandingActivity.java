/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.App;
import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.AsyncNetworkTask;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

/**
 * TODO Javadoc
 */
public class LandingActivity extends FragmentActivity implements ActionBar.TabListener {

	/**
	 * TODO Javadoc
	 */
	private PageAdapter pageAdapter;
	/**
	 * TODO Javadoc
	 */
	private ViewPager viewPager;
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
		setContentView(R.layout.activity_landing);

		userId = App.get().getPreferences().getInt("userId", App.USERID_DEFAULT);
		pageAdapter = new PageAdapter(getSupportFragmentManager());

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(pageAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < pageAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab().setText(pageAdapter.getPageTitle(i)).setTabListener(this).setIcon(pageAdapter.getIconResId(i)));
		}
		refresh();
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.call_wifilist_activity:
			/* call CheckIn activity */
			intent = new Intent(this, CheckInActivity.class);
			startActivity(intent);
			return true;
		case R.id.menu_refresh:
			refresh();
			return true;
		case R.id.menu_logout:
			logout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
		// TODO Auto-generated method stub
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
		// TODO Auto-generated method stub
	}

	/**
	 * TODO Javadoc
	 */
	public void refresh() {
		new AsyncNetworkTask().execute("http://moan.cs.hm.edu:8080/HmCheckIn/UserGetFriends?userId=" + userId, App.GET_FRIENDS);
		new AsyncNetworkTask().execute("http://moan.cs.hm.edu:8080/HmCheckIn/PullInvitations?userId=" + userId, App.GET_INVITATIONS);
	}

	public void logout() {
		App.get().getPreferences().edit().putInt("userId", App.USERID_DEFAULT).commit();
		Intent intent = new Intent(this, LaunchActivity.class);
		startActivity(intent);
		finish();
	}
}