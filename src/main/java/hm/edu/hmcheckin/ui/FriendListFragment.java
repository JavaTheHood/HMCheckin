/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.Events.FriendListEvent;
import hm.edu.hmcheckin.util.Friend;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.greenrobot.event.EventBus;

/**
 * 
 * @author
 * 
 */
public class FriendListFragment extends ListFragment {

	/**
	 * TODO Javadoc
	 */
	private BaseFriendAdapter listViewAdapter;

	/**
	 * TODO Javadoc
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_friend_list, null);
		listViewAdapter = new BaseFriendAdapter(getActivity());
		return view;
	}

	/**
	 * TODO Javadoc
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			listViewAdapter.addItems((ArrayList<Friend>) savedInstanceState.getSerializable("test"));
		}
		setListAdapter(listViewAdapter);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("test", listViewAdapter.getItems());
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public void onResume() {
		super.onResume();
		EventBus.getDefault().register(this);
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public void onPause() {
		super.onPause();
		EventBus.getDefault().unregister(this);
	}

	/**
	 * TODO Javadoc
	 */
	public void onEventMainThread(FriendListEvent event) {
		listViewAdapter.clear();
		listViewAdapter.addItems(event.friends);
	}
}
