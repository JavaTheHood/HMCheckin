/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.App;
import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.AsyncNetworkTask;
import hm.edu.hmcheckin.util.Events.SearchEvent;
import hm.edu.hmcheckin.util.Friend;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import de.greenrobot.event.EventBus;

/**
 * TODO Javadoc
 */
public class FriendSearchFragment extends ListFragment {

	/**
	 * TODO Javadoc
	 */
	private int userId;
	/**
	 * TODO Javadoc
	 */
	private EditText userName;
	/**
	 * TODO Javadoc
	 */
	private SearchListAdapter listViewAdapter;

	/**
	 * TODO Javadoc
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_friend_search, null);
		listViewAdapter = new SearchListAdapter(getActivity());
		init(view);
		return view;
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		userId = App.get().getPreferences().getInt("userId", App.USERID_DEFAULT);
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
		Friend friend = listViewAdapter.getItem(position);
		// TODO: Ui strings vom dialog nach recources
		showDialog("Einladung", friend.getFirstname() + " einladen?", friend);
	}

	/**
	 * TODO Javadoc
	 */
	public void init(final View view) {
		userName = (EditText) view.findViewById(R.id.editText_search_username);
		Button searchBtn = (Button) view.findViewById(R.id.button_search_friend);
		searchBtn.setOnClickListener(new OnClickListener() {
			/**
			 * TODO Javadoc
			 */
			@Override
			public void onClick(View v) {
				/* close keyboard */
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(((EditText) view.findViewById(R.id.editText_search_username)).getWindowToken(), 0);

				/* get user input */
				EditText userNameET = (EditText) view.findViewById(R.id.editText_search_username);
				String input = userNameET.getText().toString();
				if (input.length() > 0)
					search();
				else
					Toast.makeText(FriendSearchFragment.this.getActivity(), "Suchfeld ausfüllen!", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * TODO Javadoc
	 */
	public void onEventMainThread(SearchEvent event) {
		listViewAdapter.clear();
		if (event.friends.size() > 0) {
			listViewAdapter.addItems(event.friends);
		} else {
			Toast.makeText(getActivity(), getString(R.string.search_nothingFound), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * init friend seach request
	 */
	private void search() {
		listViewAdapter.clear();
		String input = userName.getText().toString();
		new AsyncNetworkTask().execute("http://moan.cs.hm.edu:8080/HmCheckIn/FindFriend?suche=" + input.trim(), App.GET_SEARCH);
	}

	/**
	 * Abstract DialogInterface.onClickListener class, implements
	 * View.onClickListener.
	 */
	public static abstract class ParameterizedDialogOnClickListener implements DialogInterface.OnClickListener {
		private final Friend friend;

		/**
		 * Ctor.
		 * 
		 * @param friend
		 */
		public ParameterizedDialogOnClickListener(Friend f) {
			friend = f;
		}

		/**
		 * Default onClick method.
		 * 
		 * @param dialog
		 * @param which
		 */
		@Override
		public void onClick(DialogInterface dialog, int which) {
			onClick(dialog, which, friend);
		}

		/**
		 * Custom onClick method signature.
		 * 
		 * @param dialog
		 * @param which
		 * @param friend
		 */
		abstract void onClick(DialogInterface dialog, int which, Friend friend);
	};

	/**
	 * Create Dialog for invitation confirmation.
	 * 
	 * @param title
	 * @param message
	 * @param friend
	 */
	public void showDialog(String title, String message, Friend friend) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage(message).setCancelable(false).setNegativeButton("Nein", null)
				.setPositiveButton("Ja", new ParameterizedDialogOnClickListener(friend) {

					/**
					 * Accept invitation button click listener.
					 * 
					 * @param dialog
					 * @param which
					 * @param friend
					 */
					@Override
					void onClick(DialogInterface dialog, int which, Friend friend) {
						new AsyncNetworkTask().execute(
								"http://moan.cs.hm.edu:8080/HmCheckIn/SendInvitation?userid=" + userId + "&friendid=" + friend.getUserId(),
								App.GET_DEFAULT);
						Toast.makeText(getActivity(), getString(R.string.search_sentInv), Toast.LENGTH_SHORT).show();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

}
