/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.Friend;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * This class is an adapter for friends in list views.
 * 
 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
 * @version 1.0
 */
public class SearchListAdapter extends BaseAdapter {

	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	private final ArrayList<Friend> friends = new ArrayList<Friend>();
	/**
	 * TODO Javadoc
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	private final LayoutInflater inflater;

	/**
	 * Ctor.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param context
	 */
	public SearchListAdapter(Context context) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * add a firend to the list.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param friend
	 */
	public void addItem(Friend friend) {
		friends.add(friend);
		notifyDataSetChanged();
	}

	/**
	 * add a list of items to the current list.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param list
	 *            of firends
	 */
	public void addItems(ArrayList<Friend> list) {
		for (Friend f : list) {
			friends.add(f);
		}
		notifyDataSetChanged();
	}

	/**
	 * remove all elements from the list.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	public void clear() {
		friends.clear();
		notifyDataSetChanged();
	}

	/**
	 * return the number of elements in the list.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param list
	 *            length
	 */
	@Override
	public int getCount() {
		return friends.size();
	}

	/**
	 * get an item from the list by given index.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param index
	 */
	@Override
	public Friend getItem(int index) {
		return friends.get(index);
	}

	/**
	 * get an item id by given index. TODO (not implemented)
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param index
	 */
	@Override
	public long getItemId(int index) {
		return 0;
	}

	/**
	 * returns a view for the list.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 * @param position
	 * @param convertView
	 * @param parent
	 *            element
	 * @return view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_search, null);
			holder = new ViewHolder();
			holder.login = (TextView) convertView
					.findViewById(R.id.textViewLogin);
			holder.firstName = (TextView) convertView
					.findViewById(R.id.textViewFirstName);
			holder.lastName = (TextView) convertView
					.findViewById(R.id.textViewLastName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Friend f = friends.get(position);
		holder.login.setText(f.getLogin());
		holder.firstName.setText(f.getFirstname());
		holder.lastName.setText(f.getLastname());
		return convertView;
	}

	/**
	 * 
	 * Static class for TextViews receyceled in getView method.
	 * 
	 * @author <a href="mailto:irimi@hm.edu">Dominik Irimi</a>
	 */
	public static class ViewHolder {
		public TextView login;
		public TextView firstName;
		public TextView lastName;
	}

}
