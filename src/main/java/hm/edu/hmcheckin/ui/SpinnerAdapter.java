/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.ListItemType;
import hm.edu.hmcheckin.util.UserState;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Apdater class for a spinner providing a list of user states.
 * 
 * @author <a href="irimi@hm.edu">Dominik Irimi</a>
 * @version 1.0
 * 
 */
public class SpinnerAdapter extends BaseAdapter {
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a TODO Javadoc
	 */
	private final Context context;
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a TODO Javadoc
	 */
	private final Spinner stateSpinner;
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a TODO Javadoc
	 */
	private final ArrayList<UserState> stateList = new ArrayList<UserState>();
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a TODO Javadoc
	 */
	private final LayoutInflater inflater;

	/**
	 * Ctor. TODO Javadoc
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a
	 * @param context
	 * @param spinner
	 */
	SpinnerAdapter(Context con, Spinner spinner) {
		this.context = con;
		this.stateSpinner = spinner;
		inflater = (LayoutInflater) con
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// stateSpinner.setEnabled(false);
		addItem(new UserState(-1, "wird geladen..."));
	}

	/**
	 * add a new item to the list.
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a
	 * @param state
	 */
	public void addItem(UserState state) {
		stateList.add(state);
		notifyDataSetChanged();
	}

	/**
	 * add a list of new states to.
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a
	 * @param lsit
	 *            of user states
	 */
	public void addItems(ArrayList<UserState> list) {
		for (UserState s : list)
			if (!stateList.contains(s))
				stateList.add(s);
		notifyDataSetChanged();
	}

	/**
	 * remove all items from list
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a
	 */
	public void clearList() {
		stateList.clear();
		notifyDataSetChanged();
	}

	/**
	 * get list size.
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a
	 * @return length
	 */
	@Override
	public int getCount() {
		return stateList.size();
	}

	/**
	 * get item by id.
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a
	 * @param position
	 * @return user state object
	 */
	@Override
	public UserState getItem(int pos) {
		return stateList.get(pos);
	}

	/**
	 * get list item.
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a
	 * @param position
	 * @return item id
	 */
	@Override
	public long getItemId(int pos) {
		UserState item = stateList.get(pos);
		return item.getId();
	}

	/**
	 * returns a new list view element.
	 * 
	 * @author <a href="irimi@hm.edu">Dominik Irimi</a
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null)
			v = inflater.inflate(R.layout.row_userstate, null);

		UserState state = getItem(position);

		/* get item typ */
		if (state.getType() == ListItemType.ITEM) {
			v = inflater.inflate(R.layout.row_wifilist, null);

			/* set item label */
			if (state != null) {
				TextView stateNameView = (TextView) v
						.findViewById(R.id.wifi_item_headline);

				String stateName = state.getName();
				stateNameView.setText(stateName);
			}
		}
		return v;
	}
}
