/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.App;
import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.AsyncNetworkTask;
import hm.edu.hmcheckin.util.Events.InvitationsEvent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import de.greenrobot.event.EventBus;

/**
 * TODO Javadoc
 */
public class InvitationFragment extends ListFragment {

	/**
	 * TODO Javadoc
	 */
	private InvitationListAdapter listViewAdapter;

	/**
	 * TODO Javadoc
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_friend_list, null);
		listViewAdapter = new InvitationListAdapter(getActivity(), this);
		return view;
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(listViewAdapter);
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
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		listViewAdapter.getItemViewType(position);
	}

	/**
	 * TODO Javadoc
	 */
	public void confirmInvitation(String invId) {
		new AsyncNetworkTask().execute("http://moan.cs.hm.edu:8080/HmCheckIn/ConfirmInvitation?invitationId=" + invId + "&confirme=1&addReverse=1",
				App.GET_DEFAULT);
	}

	/**
	 * TODO Javadoc
	 */
	public void onEventMainThread(InvitationsEvent event) {
		listViewAdapter.clear();
		listViewAdapter.addItems(event.invitations);
	}
}
