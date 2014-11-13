/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.App;
import hm.edu.hmcheckin.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * TODO Javadoc
 */
public class PageAdapter extends FragmentStatePagerAdapter {

	/**
	 * TODO Javadoc
	 */
	private static final String[] Content = new String[] { App.get().getString(R.string.pager_friends),
			App.get().getString(R.string.pager_invitation), App.get().getString(R.string.pager_search) };
	/**
	 * TODO Javadoc
	 */
	private static final int[] ICONS = new int[] { R.drawable.ic_social_group, R.drawable.ic_content_new_event, R.drawable.ic_social_add_person };
	/**
	 * TODO Javadoc
	 */
	private final int tabCount = Content.length;

	/**
	 * TODO Javadoc
	 */
	public PageAdapter(FragmentManager fm) {
		super(fm);
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return new FriendListFragment();
		case 1:
			return new InvitationFragment();
		case 2:
			return new FriendSearchFragment();
		default:
			return new FriendListFragment();
		}
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public int getCount() {
		return tabCount;
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		return Content[position];
	}

	/**
	 * TODO Javadoc
	 */
	public int getIconResId(int index) {
		return ICONS[index];
	}

	/**
	 * return the tab position for a given fragment id.
	 * 
	 * @param fragment
	 *            resource id
	 * @return position
	 */
	public int getFragmentPosition(int id) {
		for (int i = 0; i < Content.length; i++)
			if (Content[i].equals(App.get().getString(id)))
				return i;
		return -1;
	}

}