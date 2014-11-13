/**
 * TODO Javadoc
 */
package hm.edu.hmcheckin.ui;

import hm.edu.hmcheckin.App;
import hm.edu.hmcheckin.R;
import hm.edu.hmcheckin.util.Friend;
import hm.edu.hmcheckin.util.SQLiteHelper;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * TODO Javadoc
 */
public class BaseFriendAdapter extends BaseAdapter {

	/**
	 * TODO Javadoc
	 */
	private final ArrayList<Friend> friends = new ArrayList<Friend>();
	/**
	 * TODO Javadoc
	 */
	private final LayoutInflater inflater;
	private final SQLiteHelper dbHelper;
	private final Context context;

	/**
	 * TODO Javadoc
	 */
	public BaseFriendAdapter(Context context) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dbHelper = App.get().getDbHelper();
		this.context = context;
	}

	/**
	 * TODO Javadoc
	 */
	public void addItem(Friend friend) {
		friends.add(friend);
		notifyDataSetChanged();
	}

	/**
	 * TODO Javadoc
	 */
	public void addItems(ArrayList<Friend> list) {
		for (Friend f : list) {
			friends.add(f);
		}
		notifyDataSetChanged();
	}

	/**
	 * TODO Javadoc
	 */
	public void clear() {
		friends.clear();
		notifyDataSetChanged();
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public int getCount() {
		return friends.size();
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public Friend getItem(int index) {
		return friends.get(index);
	}

	public ArrayList<Friend> getItems() {
		return friends;
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * TODO Javadoc
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_friends, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.textViewName);
			holder.location = (TextView) convertView.findViewById(R.id.textViewLocation);
			holder.state = (TextView) convertView.findViewById(R.id.textViewState);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Friend f = friends.get(position);
		holder.name.setText(f.getFirstname() + " " + f.getLastname());
		if (f.getLocation() > 0 && App.get().getPreferences().getBoolean(App.ACCESS_POINT_LOADED, false)) {
			holder.location.setText(dbHelper.getHotSpot(f.getLocation()).getRoom());
		} else {
			holder.location.setText(context.getString(R.string.state_offline));
		}
		holder.state.setText(String.valueOf(f.getStatus()));
		return convertView;
	}

	/**
	 * TODO Javadoc
	 */
	public static class ViewHolder {
		public TextView name;
		public TextView location;
		public TextView state;
	}

}
